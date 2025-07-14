
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Roll;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RollDAOImplementation implements RollDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result GetALL() {
        Result result = new Result();
        try {
            result.correct = jdbcTemplate.execute("{CALL GetRollesSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    Roll roll = new Roll();
                    roll.setIdRoll(resultSet.getInt("IdRoll"));
                    roll.setNombre(resultSet.getString("Nombre"));
                    result.objects.add(roll);
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
