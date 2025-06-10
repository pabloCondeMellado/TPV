package com.daw.services;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PassWordHashGenerator {
	public static void main(String[] args) {
		PasswordEncoder encoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String Password = "123";
		String hash = encoder.encode(Password);
		System.out.println(hash);
	}
}
