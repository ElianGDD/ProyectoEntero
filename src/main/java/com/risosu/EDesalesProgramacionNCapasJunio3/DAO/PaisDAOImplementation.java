package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Pais;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PaisDAOImplementation implements PaisDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAllPais() {
        
        Result result = new Result();
        
        try{
        
            int procesoCorrecto = jdbcTemplate.execute("{CALL GetALLPais(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("Nombre"));

                    result.objects.add(pais);

                }
                return 1; // termine satisfactoriamente

            });

            if (procesoCorrecto == 1) {
                result.correct = true;
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
       
    return result;
    }
    
}
