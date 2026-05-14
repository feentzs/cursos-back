package br.com.senai.cursosBackend.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findAllByAtivoTrue();

    Optional<Curso> findByIdAndAtivoTrue(Long id);

    boolean existsByNomeAndAtivoTrue(String nome);
}
