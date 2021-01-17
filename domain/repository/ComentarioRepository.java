package com.brunosimm.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunosimm.osworks.api.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
