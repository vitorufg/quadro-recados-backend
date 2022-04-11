package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Recado;

@Repository
public interface RecadoRepository extends JpaRepository<Recado, Long> { }