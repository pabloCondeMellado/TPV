package com.daw.persistence.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle")
@Getter
@Setter
@NoArgsConstructor
public class Detalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "id_cuenta")
	private Integer idCuenta;
	@Column(name = "id_producto")
	private Integer idProducto;
	
	private Integer cantidad;
	@Column(columnDefinition = "DOUBLE(6,2)")
	private Double subtotal;
	
	@ManyToOne
	@JoinColumn(name = "id_cuenta", referencedColumnName = "id", insertable = false, updatable = false)
	private Cuenta cuenta;
	
	@ManyToOne
	@JoinColumn(name = "id_producto", referencedColumnName = "id", insertable = false, updatable = false)
	private Producto producto;
	
}
