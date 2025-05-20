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
import com.daw.services.dto.CuentaDto;
import com.daw.services.dto.DetalleInputDto;
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
	public void actualizarSubtotal(int idCuenta){
		Cuenta cuenta = this.cuentaCrudRepository.findById(idCuenta).get();
		double Total = 0.0;
	    // Asegúrate de que los detalles están cargados
	    for (Detalle d : cuenta.getDetalle()) {
	        Total += d.getSubtotal();
	    }
	    cuenta.setTotal(Total);
	    this.cuentaCrudRepository.save(cuenta);
	}
	public CuentaDto addProducto(DetalleInputDto detalleInputDto) {
	    Cuenta cuenta = cuentaCrudRepository.findById(detalleInputDto.getIdCuenta()).get();
	    Producto producto = productoCrudRepository.findById(detalleInputDto.getIdProducto()).get();
	    Optional<Detalle> detalle = detalleCrudRepository.findByIdCuentaAndIdProducto(cuenta.getId(), producto.getId());
	    if (cuenta.getEstado() == Estado.CERRADA) {
	        throw new IllegalStateException("No se pueden añadir productos a una cuenta cerrada.");
	    }
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
	    actualizarStock(detalleInputDto.getIdProducto());

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
	
	private List<CuentaDto> getCuentasPorEstadoHoy(Estado estado){
     	LocalDate hoy = LocalDate.now();
        LocalDateTime inicio = hoy.atTime(6,0);
        LocalDateTime fin = hoy.plusDays(1).atTime(5, 59);

        List<Cuenta> cuentas = cuentaCrudRepository.findByEstadoAndFechaBetween(
            estado, inicio, fin
        );
        return cuentas.stream()
                      .map(CuentaMapper::toDto)
                      .collect(Collectors.toList());
	}
	
	public List<CuentaDto> getCuentasAbiertasHoy(){
		return getCuentasPorEstadoHoy(Estado.ABIERTA);
	}
	
	public List<CuentaDto> getCuentasCerradasHoy(){
		return getCuentasPorEstadoHoy(Estado.CERRADA);
	}
	
	public void actualizarStock(int idProducto) {
		Producto producto = productoCrudRepository.findById(idProducto).get();
		producto.setStock(producto.getStock() - 1);
		productoCrudRepository.save(producto);
	}
}
