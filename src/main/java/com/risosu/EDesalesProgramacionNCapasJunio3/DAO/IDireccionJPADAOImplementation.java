/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Colonia;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class IDireccionJPADAOImplementation implements IDireccionJPADAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetDireccionCMEPByIdUsuarioJPA(int IdUsuario) {
        Result result = new Result();
        result.object = new ArrayList<>();
        
        try {
            TypedQuery<Direccion> direccionQuery = entityManager.createQuery("FROM Direccion ",Direccion.class);
            direccionQuery.setParameter("idpais", IdUsuario);
            Direccion direccionJPA = direccionQuery.getSingleResult();
            
            Direccion direccion = new Direccion();
            direccion.setIdDireccion(direccionJPA.getIdDireccion());
            direccion.setCalle(direccionJPA.getCalle());
            direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
            direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
            direccion.Colonia = new Colonia();
            direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
            
            
            result.correct = true;
            
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
            
        }
        
        return result;

    }
    
    

}
