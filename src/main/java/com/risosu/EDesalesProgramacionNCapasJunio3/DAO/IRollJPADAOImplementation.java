
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Roll;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IRollJPADAOImplementation implements IRollJPADAO{
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try {
            
            TypedQuery<Roll> rolesQuery = entityManager.createQuery("FROM Roll ORDER BY IdRoll ASC", Roll.class);

            List<Roll> roles = rolesQuery.getResultList();
            
            
            for(Roll rolesJPA : roles){
                Roll roll = new Roll();
                roll.setIdRoll(rolesJPA.getIdRoll());
                roll.setNombre(rolesJPA.getNombre());
                
                result.objects.add(roll);
            
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
