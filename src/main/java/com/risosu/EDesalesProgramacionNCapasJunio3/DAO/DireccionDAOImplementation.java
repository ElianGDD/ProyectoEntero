package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Colonia;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Estado;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Municipio;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Pais;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.UsuarioDireccion;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alien 13
 */
@Repository
public class DireccionDAOImplementation implements DireccionDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result GetDireccionCMEPByIdUsuario(int IdUsuario) {
        Result result = new Result();

        try {
            result.correct = jdbcTemplate.execute("{CALL GetDatosDireccion(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                
                if(resultSet.next()){
                    usuarioDireccion.Direccion = new Direccion();
                    
                    usuarioDireccion.Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    usuarioDireccion.Direccion.setCalle(resultSet.getString("Calle"));
                    usuarioDireccion.Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    usuarioDireccion.Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
//                    usuarioDireccion.Usuario = new Usuario();
//                    usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuarioDireccion.Direccion.Colonia = new Colonia();
                    usuarioDireccion.Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    usuarioDireccion.Direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                    usuarioDireccion.Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    usuarioDireccion.Direccion.Colonia.Municipio = new Municipio();
                    usuarioDireccion.Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    usuarioDireccion.Direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                    usuarioDireccion.Direccion.Colonia.Municipio.Estado = new Estado();
                    usuarioDireccion.Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                    usuarioDireccion.Direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                    usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                    usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                    
                    
                }
                result.object = usuarioDireccion;
                
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
