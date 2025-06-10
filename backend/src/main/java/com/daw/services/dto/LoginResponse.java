package com.daw.services.dto;

import com.daw.persistence.entities.Rol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
	private Integer id;
	private String nombre;
	private Rol rol;
	private String token;
}
