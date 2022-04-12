package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Recado;
import com.example.demo.repository.RecadoRepository;
import com.example.demo.responses.Response;

// A anotação @RestController permite definir um controller com características API REST;
@RestController
public class RecadoController {
	// A anotação @Autowired delega ao Spring Boot a inicialização do objeto;
    @Autowired
    private RecadoRepository recadoRepository;
    // A anotação @RequestMapping permite definir uma rota
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/recados", method = RequestMethod.GET)
    public List<Recado> Get() {
        return recadoRepository.findAll();
    }
    
    // @PathVariable indica que o valor da variável virá de uma informação da rota;
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/recados/{id}", method = RequestMethod.GET)
    public ResponseEntity<Recado> GetById(@PathVariable(value = "id") long id)
    {
    	// https://docs.oracle.com/javase/9/docs/api/java/util/Optional.html (desde v 1.8)
    	// findById espera um retorno do tipo Optional<Recado>
        Optional<Recado> recado = recadoRepository.findById(id);
        if(recado.isPresent())
            return new ResponseEntity<Recado>(recado.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // @RequestBody indica que o valor do objeto virá do corpo da requisição e 
    //              consegue mapear os dados vindos em JSON para os atributos da classe;
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/recados/", method =  RequestMethod.POST)
    public ResponseEntity<Response<Recado>> Post(@Valid @RequestBody Recado recado, BindingResult result)
    {
    	Response<Recado> response = new Response<Recado>();    	
    	if (result.hasErrors()) {
    		result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
    		return ResponseEntity.badRequest().body(response);
    	}
    	recadoRepository.save(recado);
    	response.setData(recado);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/recados/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<Response<Recado>> Put(@PathVariable(value = "id") long id, @Valid @RequestBody 
    											Recado newRecado, BindingResult result)
    {
        Optional<Recado> oldRecado = recadoRepository.findById(id);
    	Response<Recado> response = new Response<Recado>();    	
    	if (result.hasErrors()) {
    		result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
    		return ResponseEntity.badRequest().body(response);
    	}        	        
        if(oldRecado.isPresent()){
            Recado recado = oldRecado.get();
            recado.setAutor(newRecado.getAutor());
            recado.setRecado(newRecado.getRecado());
            recado.setData(newRecado.getData());
            response.setData(recado);
            recadoRepository.save(recado);
            return ResponseEntity.ok(response);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/recados/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<Recado> recado = recadoRepository.findById(id);
        if(recado.isPresent()){
            recadoRepository.delete(recado.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }    
    
}	