package com.daw.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.CuentaCrudRepository;
import com.daw.persistence.crud.DetalleCrudRepository;
import com.daw.persistence.crud.ProductoCrudRepository;
import com.daw.persistence.entities.Cuenta;
import com.daw.persistence.entities.Detalle;
import com.daw.persistence.entities.Estado;
import com.daw.persistence.entities.MetodoPago;
import com.daw.persistence.entities.Producto;
import com.daw.persistence.entities.Rol;
import com.daw.services.dto.CuentaDto;
import com.daw.services.dto.DetalleInputDto;
import com.daw.services.dto.DetalleOutputDto;
import com.daw.services.mappers.CuentaMapper;
import com.daw.services.mappers.DetalleMapper;

@Service
public class CuentaService {
	@Autowired
	private DetalleCrudRepository detalleCrudRepository;
	@Autowired
	private ProductoCrudRepository productoCrudRepository;
	
	@Autowired
	private CuentaCrudRepository cuentaCrudRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public List<CuentaDto> findAll(){
		List<CuentaDto> cuentasDTO = new ArrayList<CuentaDto>();
		
		for(Cuenta c: this.cuentaCrudRepository.findAll()) {
		cuentasDTO.add(CuentaMapper.toDto(c));
		}
		return cuentasDTO;
	}
	
	public CuentaDto findById(int idCuenta) {
		return CuentaMapper.toDto(this.cuentaCrudRepository.findById(idCuenta).get());
	}
	
	public Optional<Cuenta> findByEntity(int idCuenta){
		return this.cuentaCrudRepository.findById(idCuenta);
	}
	
	public boolean existsCuenta(int idCuenta) {
		return this.cuentaCrudRepository.existsById(idCuenta);
	}
	
	public CuentaDto create(Cuenta cuenta) {
		cuenta.setFecha(LocalDateTime.now());
		cuenta.setTotal(0.0);
		cuenta.setEstado(Estado.ABIERTA);
		cuenta.setMetodoPago(MetodoPago.SIN_PAGAR);
	    System.out.println("Método de pago antes de guardar: " + cuenta.getMetodoPago());
		cuenta = this.cuentaCrudRepository.save(cuenta);
		
		cuenta.setUsuario(this.usuarioService.findByID(cuenta.getIdUsuario()).get());
		cuenta.setDetalle(new ArrayList<Detalle>());
		return CuentaMapper.toDto(cuenta);
	}
	
	public Cuenta update(Cuenta cuenta) {
		return this.cuentaCrudRepository.save(cuenta);
	}
	
	public boolean deleteCuenta(int idCuenta) {
		boolean result = false;
		if(this.cuentaCrudRepository.existsById(idCuenta)) {
			this.cuentaCrudRepository.deleteById(idCuenta);
			result = true;
		}
		
		return result;
	}
	public void actualizarSubtotal(int idCuenta) {
	    Optional<Cuenta> Cuenta = this.cuentaCrudRepository.findById(idCuenta);
	    if (Cuenta.isPresent()) {
	        Cuenta cuenta = Cuenta.get();
	        double total = 0.0;
	        for (Detalle d : cuenta.getDetalle()) {
	            total += d.getSubtotal();
	        }
	        cuenta.setTotal(total);
	        this.cuentaCrudRepository.save(cuenta);
	    }
	    // Si la cuenta no existe, simplemente no hace nada (o podrías lanzar una excepción si lo prefieres)
	}

	public CuentaDto addProducto(DetalleInputDto detalleInputDto) {
	    Cuenta cuenta = cuentaCrudRepository.findById(detalleInputDto.getIdCuenta()).get();
	    Producto producto = productoCrudRepository.findById(detalleInputDto.getIdProducto()).get();
	    if (producto.getStock() <= 0) {
	        throw new IllegalStateException("No hay stock disponible para este producto.");
	    }
	    Optional<Detalle> detalle = detalleCrudRepository.findByIdCuentaAndIdProducto(cuenta.getId(), producto.getId());

	    if (detalle.isPresent()) {
	        Detalle detalleExistente = detalle.get();
	        int nuevaCantidad = detalleExistente.getCantidad() + detalleInputDto.getCantidad();
	        detalleExistente.setCantidad(nuevaCantidad);
	        detalleExistente.setSubtotal(producto.getPrecio() * nuevaCantidad);
	        detalleCrudRepository.save(detalleExistente);
	    } else {
	        Detalle detalleNuevo = DetalleMapper.toEntity(detalleInputDto);
	        detalleNuevo.setProducto(producto);
	        detalleNuevo.setSubtotal(producto.getPrecio() * detalleInputDto.getCantidad());
	        detalleCrudRepository.save(detalleNuevo);
	    }

	    actualizarSubtotal(detalleInputDto.getIdCuenta());
	    actualizarStock(detalleInputDto.getIdProducto(), -1);

	    return CuentaMapper.toDto(cuenta);
	}
	
	public CuentaDto cerrarCuenta(int idCuenta, String metodoPago) {
		Cuenta cuenta = cuentaCrudRepository.findById(idCuenta).get();
		cuenta.setEstado(Estado.CERRADA);
		metodoPago = metodoPago.replace("\"", "").trim();
		cuenta.setMetodoPago(MetodoPago.valueOf(metodoPago));
		
		cuentaCrudRepository.save(cuenta);
		
		return CuentaMapper.toDto(cuenta);
	}
	
	private List<CuentaDto> getCuentasPorEstadoHoy(Estado estado, Rol rol){
     	LocalDate hoy = LocalDate.now();
        LocalDateTime inicio = hoy.atTime(6,0);
        LocalDateTime fin = hoy.plusDays(1).atTime(5, 59);

        List<Cuenta> cuentas = cuentaCrudRepository.findByEstadoAndFechaBetweenAndUsuarioRol(estado, inicio, fin,rol);
        return cuentas.stream()
                      .map(CuentaMapper::toDto)
                      .collect(Collectors.toList());
	}
	
	public List<CuentaDto> getCuentasAbiertasHoy(Rol rol){
		return getCuentasPorEstadoHoy(Estado.ABIERTA, rol);
	}
	
	public List<CuentaDto> getCuentasCerradasHoy(Rol rol){
		return getCuentasPorEstadoHoy(Estado.CERRADA, rol);
	}

	public List<CuentaDto> getCuentasCerradasHoyRoot(){
     	LocalDate hoy = LocalDate.now();
        LocalDateTime inicio = hoy.atTime(6,0);
        LocalDateTime fin = hoy.plusDays(1).atTime(5, 59);

        List<Cuenta> cuentas = cuentaCrudRepository.findByEstadoAndFechaBetween(Estado.CERRADA, inicio, fin);
        return cuentas.stream()
                      .map(CuentaMapper::toDto)
                      .collect(Collectors.toList());
	}
	public List<CuentaDto> getCuentasAbiertasHoyRoot(){
     	LocalDate hoy = LocalDate.now();
        LocalDateTime inicio = hoy.atTime(6,0);
        LocalDateTime fin = hoy.plusDays(1).atTime(5, 59);

        List<Cuenta> cuentas = cuentaCrudRepository.findByEstadoAndFechaBetween(Estado.ABIERTA, inicio, fin);
        return cuentas.stream()
                      .map(CuentaMapper::toDto)
                      .collect(Collectors.toList());
	}
	public void actualizarStock(int idProducto, int cantidad) {
	    Optional<Producto> optionalProducto = productoCrudRepository.findById(idProducto);
	    if (optionalProducto.isPresent()) {
	        Producto producto = optionalProducto.get();
	        producto.setStock(producto.getStock() + cantidad); // cantidad puede ser positiva o negativa
	        productoCrudRepository.save(producto);
	    }
	}
	
    // Restar cantidad de producto de la cuenta (y eliminar si llega a 0)
    public CuentaDto restarCantidadProducto(DetalleInputDto detalleInputDto) {
        Cuenta cuenta = cuentaCrudRepository.findById(detalleInputDto.getIdCuenta())
            .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        Producto producto = productoCrudRepository.findById(detalleInputDto.getIdProducto())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Optional<Detalle> detalleOpt = detalleCrudRepository.findByIdCuentaAndIdProducto(
            cuenta.getId(), producto.getId());


        if (detalleOpt.isPresent()) {
            Detalle detalle = detalleOpt.get();
            int cantidadActual = detalle.getCantidad();
            int cantidadARestar = detalleInputDto.getCantidad();

            if (cantidadARestar >= cantidadActual) {
                detalleCrudRepository.delete(detalle);
            } else {
                int nuevaCantidad = cantidadActual - cantidadARestar;
                detalle.setCantidad(nuevaCantidad);
                detalle.setSubtotal(producto.getPrecio() * nuevaCantidad);
                detalleCrudRepository.save(detalle);
            }

            actualizarStock(producto.getId(), cantidadARestar); // Devuelves stock
            actualizarSubtotal(cuenta.getId());
        } else {
            // Producto no está en la cuenta, puedes lanzar excepción o ignorar
            // throw new IllegalArgumentException("El producto no está en la cuenta.");
        }

        return CuentaMapper.toDto(cuentaCrudRepository.findById(cuenta.getId()).get());
    }

    // Eliminar completamente un producto de la cuenta
    public CuentaDto removeProducto(DetalleInputDto detalleInputDto) {
        Cuenta cuenta = cuentaCrudRepository.findById(detalleInputDto.getIdCuenta())
            .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        Producto producto = productoCrudRepository.findById(detalleInputDto.getIdProducto())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Optional<Detalle> detalleOpt = detalleCrudRepository.findByIdCuentaAndIdProducto(
            cuenta.getId(), producto.getId());

        if (detalleOpt.isPresent()) {
            Detalle detalle = detalleOpt.get();
            int cantidadEliminada = detalle.getCantidad();
            detalleCrudRepository.delete(detalle);

            actualizarStock(producto.getId(), cantidadEliminada); // Devuelves todo el stock
            actualizarSubtotal(cuenta.getId());
        } else {
            // Producto no está en la cuenta, puedes lanzar excepción o ignorar
        }

        return CuentaMapper.toDto(cuentaCrudRepository.findById(cuenta.getId()).get());
    }

}
