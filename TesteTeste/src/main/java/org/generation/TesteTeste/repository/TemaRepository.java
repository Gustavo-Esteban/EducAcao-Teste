package org.generation.TesteTeste.repository;

import java.util.List;

import org.generation.TesteTeste.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemaRepository extends JpaRepository<Tema, Long>
{
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao);
}
