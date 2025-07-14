/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Colonia;
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
public class IColoniaJPADAOImplementation implements IColoniaJPADAO{
    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetColoniaByMunicipio(int IdMunicipio) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {
            
            TypedQuery<Colonia> coloniaQuery = entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio = :idmunicipio",Colonia.class);
            coloniaQuery.setParameter("idmunicipio", IdMunicipio);
            List<Colonia> colonias = coloniaQuery.getResultList();
            
            for(Colonia coloniaJPA : colonias){
                com.risosu.EDesalesProgramacionNCapasJunio3.ML.Colonia colonia = new com.risosu.EDesalesProgramacionNCapasJunio3.ML.Colonia();
                
                colonia.setIdColonia(coloniaJPA.getIdColonia());
                colonia.setNombre(coloniaJPA.getNombre());
                colonia.setCodigoPostal(coloniaJPA.getCodigoPostal());
                
                result.objects.add(colonia);
            }
            
            result.correct = true;
            
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }
        
        return result;
    }
    
}
