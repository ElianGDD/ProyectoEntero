/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Estado;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.UsuarioDireccion;

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
public class IEstadoJPADAOImplementation implements IEstadoJPADAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetEstadoByIdPais(int IdPais) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            TypedQuery<Estado> estadosQuetry = entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idpais", Estado.class);
            estadosQuetry.setParameter("idpais", IdPais);
            List<Estado> estados = estadosQuetry.getResultList();
            
            for (Estado estadoJPA : estados ) {
                com.risosu.EDesalesProgramacionNCapasJunio3.ML.Estado estadoML = new com.risosu.EDesalesProgramacionNCapasJunio3.ML.Estado();
                
                estadoML.setIdEstado(estadoJPA.getIdEstado());
                estadoML.setNombre(estadoJPA.getNombre());
                
                
                
                result.objects.add(estadoML);
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
