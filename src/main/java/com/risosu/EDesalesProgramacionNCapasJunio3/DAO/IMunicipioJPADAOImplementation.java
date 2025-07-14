/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Municipio;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alien 13
 */
@Repository
public class IMunicipioJPADAOImplementation implements IMunicipioJPADAO{
    
    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetMunicipioByIdEstado(int IdEstado) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try {
            
            TypedQuery<Municipio> municipioQuery = entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idestado",Municipio.class);
            municipioQuery.setParameter("idestado", IdEstado);
            List<Municipio> municipios = municipioQuery.getResultList();
            
            for(Municipio municipioJPA : municipios){
                com.risosu.EDesalesProgramacionNCapasJunio3.ML.Municipio municipio = new com.risosu.EDesalesProgramacionNCapasJunio3.ML.Municipio();
                municipio.setIdMunicipio(municipioJPA.getIdMunicipio());
                municipio.setNombre(municipioJPA.getNombre());
                
                result.objects.add(municipio);
            }
            
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    
    
}
