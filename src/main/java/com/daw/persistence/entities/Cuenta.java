package com.daw.persistence.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
@NoArgsConstructor
public class Cuenta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "id_usuario")
	private Integer idUsuario;
	@Column(columnDefinition = "VARCHAR(30)")
	private String cliente;
	@Column(name = "metodo_pago")
	private MetodoPago metodoPago;
	@Column(columnDefinition = "DATETIME")
	private LocalDateTime fecha;
	
	private Estado estado;
	@Column(columnDefinition = "DOUBLE(6,2)")
	private Double total;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
	private Usuario usuario;
	 
	@OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Detalle> detalle;
}
