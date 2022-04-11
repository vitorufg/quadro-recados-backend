package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;



@Entity
//public class Pessoa
public class Recado
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String autor;
    
    @Column(nullable = false)
    private String recado;
    
    @Column(nullable = false)
    private Date data;

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    @NotEmpty(message="O nome do autor deverá ser informado!")
    @Length(max=100, message="O nome do autor deverá ter até 100 caracteres")
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    @NotEmpty(message="O recado deverá ser informado!")
    @Length(min=1, max=200, message="O recado poderá ter até 200 caracteres")
    public String getRecado() {
        return recado;
    }

    public void setRecado(String recado) {
        this.recado = recado;
    }
    
    @NotNull(message="A data deverá ser informada!")
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    
    
    
}