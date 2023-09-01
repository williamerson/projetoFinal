package br.com.senai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senai.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

}
