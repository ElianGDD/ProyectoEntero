package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Estado;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements EstadoDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result GetALLEstado() {

        Result result = new Result();

        try {
            int procesoCorrecto = jdbcTemplate.execute("{CALL GetALLEstado(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    Estado estado = new Estado();

                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));

                    result.objects.add(estado);
                }

                return 1;
            });

            if (procesoCorrecto == 1) {

                result.correct = true;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetEstadoByIdPais(int IdPais) {

        Result result = new Result();

        try {
            result.object = jdbcTemplate.execute("{CALL GetEstadoBYIdPais(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                
                callableStatement.setInt(1, IdPais);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                
                callableStatement.execute();
                ResultSet resultset = (ResultSet) callableStatement.getObject(2);
                result.objects = new ArrayList<>();
                
                while(resultset.next()){
                    Estado estado = new Estado();
                    
                    estado.setIdEstado(resultset.getInt("IdEstado"));
                    estado.setNombre(resultset.getString("Nombre"));
                    
                    result.objects.add(estado);
                    
                }
                
                
                return true;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        };

        return result;
    }

}
