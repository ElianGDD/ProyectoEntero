package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Municipio;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements MunicipioDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result GetALLMunicipio() {

        Result result = new Result();

        try {
            int procesoCorrecto = jdbcTemplate.execute("{CALL GetALLMunicipio(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    Municipio municipio = new Municipio();

                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("Nombre"));

                    result.objects.add(municipio);

                }

                return 1;
            });

            if (procesoCorrecto == 1) {
                result.correct = true;

            }

        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        };

        return result;

    }

    @Override
    public Result GetMunicipioByIdEstado(int IdEstado) {

        Result result = new Result();

        try {
            result.object = jdbcTemplate.execute("{CALL GetMunicipioBYIdEstado(?,?)}", (CallableStatementCallback < Boolean >) callableStatement -> {
                
                callableStatement.setInt(1, IdEstado);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("Nombre"));
                    
                    result.objects.add(municipio);
                }
                

                return true;
            });

        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }

        return result;

    }

}
