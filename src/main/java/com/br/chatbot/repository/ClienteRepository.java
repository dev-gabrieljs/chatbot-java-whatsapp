package com.br.chatbot.repository;

import com.br.chatbot.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByMatricula(String matricula);
}
