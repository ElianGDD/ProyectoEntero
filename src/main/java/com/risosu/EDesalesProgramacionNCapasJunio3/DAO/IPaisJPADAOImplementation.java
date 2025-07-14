/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Pais;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class IPaisJPADAOImplementation implements IPaisJPADAO{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAllPaisJPA() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try {
            TypedQuery<Pais> paisesQuery = entityManager.createQuery("FROM Pais ORDER BY IdPais ASC", Pais.class);
            List<Pais> paises = paisesQuery.getResultList();
            for(Pais paisJPA : paises){
                Pais pais = new Pais();
                pais.setIdPais(paisJPA.getIdPais());
                pais.setNombre(paisJPA.getNombre());
                result.objects.add(pais);
                
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
