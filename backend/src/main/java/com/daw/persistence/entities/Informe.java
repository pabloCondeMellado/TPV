package com.daw.persistence.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "informe")
@Getter
@Setter
@NoArgsConstructor
public class Informe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(columnDefinition = "DATETIME")
	private LocalDateTime fecha;
	@Column(name = "total_ventas", columnDefinition = "DOUBLE(10,2)")
	private Double totalVentas;
	
	@Column(name = "detalles_productos", columnDefinition = "TEXT")
	private String detalleProductos;

	@Column(name = "ventas_efectivo")
	private Integer ventasEfectivo;
	@Column(name="total_efectivo", columnDefinition = "DOUBLE(10,2)")
	private Double totalEfectivo;
	
	@Column(name = "ventas_tarjeta")
	private Integer ventasTarjeta;
	@Column(name="total_tarjeta", columnDefinition = "DOUBLE(10,2)")
	private Double totalTarjeta;
	
	@Column(name = "ventas_ticket")
	private Integer ventasTicket;
	@Column(name="total_ticket", columnDefinition = "DOUBLE(10,2)")
	private Double totalTicket;
	
}
