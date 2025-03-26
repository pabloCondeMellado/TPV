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
	@Column(name = "total_ventas", columnDefinition = "DOUBLE(6,2)")
	private Double totalVentas;
	@Column(name = "cantidad_ventas", columnDefinition = "TEXT")
	private String CantidadVentas;
	@Column(name = "ventas_efectivo")
	private Integer ventasEfectivo;
	@Column(name = "ventas_tarjeta")
	private Integer ventasTarjeta;
	@Column(name = "ventas_ticket")
	private Integer ventasTicket;
	
}
