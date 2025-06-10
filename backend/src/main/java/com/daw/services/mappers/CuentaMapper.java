package com.daw.services.mappers;

import java.util.ArrayList;
import java.util.List;

import com.daw.persistence.entities.Cuenta;
import com.daw.persistence.entities.Detalle;
import com.daw.services.dto.CuentaDto;
import com.daw.services.dto.DetalleOutputDto;

public class CuentaMapper {
	public static CuentaDto toDto(Cuenta cuenta) {
		CuentaDto dto = new CuentaDto();
		
		dto.setId(cuenta.getId());
		dto.setFecha(cuenta.getFecha());
		dto.setTotal(cuenta.getTotal());
		dto.setMetodoPago(cuenta.getMetodoPago());
		dto.setUsuario(cuenta.getUsuario().getNombre());
		dto.setEstado(cuenta.getEstado());
		dto.setCliente(cuenta.getCliente());
		
		List<DetalleOutputDto> productos = new ArrayList<DetalleOutputDto>();
		for(Detalle d : cuenta.getDetalle()) {
			productos.add(DetalleMapper.toDto(d));
		}
		dto.setProductos(productos);
		
		return dto;
	}
}
