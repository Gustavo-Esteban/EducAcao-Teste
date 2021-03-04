package org.generation.TesteTeste.repository;

import java.util.List;

import org.generation.TesteTeste.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
	
	public List<Usuario> findAllByEmailContainingIgnoreCase( String emailS);
}
