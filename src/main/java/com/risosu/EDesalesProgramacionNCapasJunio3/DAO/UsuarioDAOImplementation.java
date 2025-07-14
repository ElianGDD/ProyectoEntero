package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.UsuarioDireccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Colonia;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Estado;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Municipio;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Pais;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Roll;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository

public class UsuarioDAOImplementation implements UsuarioDAO {

    @Autowired // conexión a base de datos 
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {

        Result result = new Result();

        try {

            int procesoCorrecto = jdbcTemplate.execute("{CALL GetAllSP(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                int idAlumnoPrevio = 0; // sirve para guardar el id previo por si existe ya un usuario en la lista

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    idAlumnoPrevio = resultSet.getInt("IdUsuario");

                    if (!result.objects.isEmpty() && idAlumnoPrevio == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {

                        /*aqui solo agrego direccion*/
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                    } else {

                        /*aqui agrego alumno y direccion*/
                        UsuarioDireccion alumnoDireccion = new UsuarioDireccion();
                        alumnoDireccion.Usuario = new Usuario();
                        alumnoDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        alumnoDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                        alumnoDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        alumnoDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        alumnoDireccion.Usuario.setEmail(resultSet.getString("Email"));
                        alumnoDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                        alumnoDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                        alumnoDireccion.Usuario.setImagenPerfil(resultSet.getString("ImagenPerfil"));

                        alumnoDireccion.Usuario.Roll = new Roll();
                        alumnoDireccion.Usuario.Roll.setIdRoll(resultSet.getInt("IdRoll"));
                        alumnoDireccion.Usuario.Roll.setNombre(resultSet.getString("NombreRoll"));

                        alumnoDireccion.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        alumnoDireccion.Direcciones.add(direccion);

                        result.objects.add(alumnoDireccion);

                    }

                }

                return 1; // termine satisfactoriamente

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
    public Result Add(UsuarioDireccion alumnoDireccion) {
        Result result = new Result();
        try {
            int rowAffectted = jdbcTemplate.execute("{CALL ADDUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, alumnoDireccion.Usuario.getNombre());
                callableStatement.setDate(2, new java.sql.Date(alumnoDireccion.Usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(3, alumnoDireccion.Usuario.getUserName());
                callableStatement.setString(4, alumnoDireccion.Usuario.getApellidoPaterno());
                callableStatement.setString(5, alumnoDireccion.Usuario.getApellidoMaterno());
                callableStatement.setString(6, alumnoDireccion.Usuario.getPassword());
                callableStatement.setString(7, String.valueOf(alumnoDireccion.Usuario.getSexo()));
                callableStatement.setString(8, alumnoDireccion.Usuario.getTelefono());
                callableStatement.setString(9, alumnoDireccion.Usuario.getCelular());
                callableStatement.setString(10, alumnoDireccion.Usuario.getImagenPerfil());
                callableStatement.setString(11, alumnoDireccion.Usuario.getCurp());
                callableStatement.setString(12, alumnoDireccion.Usuario.getEmail());
                callableStatement.setString(13, alumnoDireccion.Direccion.getCalle());
                callableStatement.setString(14, alumnoDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(15, alumnoDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(16, alumnoDireccion.Direccion.Colonia.getIdColonia());

                return callableStatement.executeUpdate();
            });

            if (rowAffectted == 1) {
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
    public Result GetDatosAlumnoPDByIdAlumno(int IdUsuario) {
        Result result = new Result();
        try {
            result.correct = jdbcTemplate.execute("{CALL GetDatosUsuarioDireccionById(?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.registerOutParameter(3, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(2);
                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(3);

                UsuarioDireccion alumnoDireccion = new UsuarioDireccion();

                if (resultSetUsuario.next()) {
                    alumnoDireccion.Usuario = new Usuario();
                    alumnoDireccion.Usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    alumnoDireccion.Usuario.setNombre(resultSetUsuario.getString("Nombre"));
                    alumnoDireccion.Usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    alumnoDireccion.Usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    alumnoDireccion.Usuario.setEmail(resultSetUsuario.getString("Email"));
                    alumnoDireccion.Usuario.setImagenPerfil(resultSetUsuario.getString("ImagenPerfil"));

                }
                alumnoDireccion.Direcciones = new ArrayList<>();

                while (resultSetDireccion.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDireccion.getString("Calle"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));

                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSetDireccion.getInt("IdColonia"));

                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSetDireccion.getInt("IdMunicipio"));

                    alumnoDireccion.Direcciones.add(direccion);

                }
                result.object = alumnoDireccion;

                return true;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }

        return result;
    }

    @Override
    public Result GetDatosBasicosUsuarioByIdUsuario(int IdUsuario) {
        Result result = new Result();
        try {
            result.correct = jdbcTemplate.execute("{CALL GetDatosBasicUsuarioByID(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                Usuario usuario = new Usuario();
                if (resultSet.next()) {
                    usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuario.setNombre(resultSet.getString("Nombre"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.setImagenPerfil(resultSet.getString("ImagenPerfil"));

                }
                result.object = usuario;

                return true;
            });

        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;

        }

        return result;
    }

    @Override
    public Result UpdateAlumnoDatosBasicos(UsuarioDireccion alumnoDireccion) {
        Result result = new Result();
        try {
            int rowAffected = jdbcTemplate.execute("{CALL UPDATEDatosUsuarioBasicosPD(?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, alumnoDireccion.Usuario.getIdUsuario());
                callableStatement.setString(2, alumnoDireccion.Usuario.getNombre());
                callableStatement.setDate(3, new java.sql.Date(alumnoDireccion.Usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(4, alumnoDireccion.Usuario.getUserName());
                callableStatement.setString(5, alumnoDireccion.Usuario.getApellidoPaterno());
                callableStatement.setString(6, alumnoDireccion.Usuario.getApellidoMaterno());
                callableStatement.setString(7, alumnoDireccion.Usuario.getPassword());
                callableStatement.setString(8, String.valueOf(alumnoDireccion.Usuario.getSexo()));
                callableStatement.setString(9, alumnoDireccion.Usuario.getTelefono());
                callableStatement.setString(10, alumnoDireccion.Usuario.getCelular());
                callableStatement.setString(11, alumnoDireccion.Usuario.getCurp());
                callableStatement.setString(12, alumnoDireccion.Usuario.getEmail());
                callableStatement.setString(13, alumnoDireccion.Usuario.getImagenPerfil());

                return callableStatement.executeUpdate();
            });
            if (rowAffected == 0) {
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
    public Result UpdateAlumnoDatosDireccion(UsuarioDireccion alumnoDireccion) {
        Result result = new Result();
        try {
            int rowAffected = jdbcTemplate.execute("{CALL UPDATEDireccionUsuario(?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, alumnoDireccion.Direccion.getIdDireccion());
                callableStatement.setString(2, alumnoDireccion.Direccion.getCalle());
                callableStatement.setString(3, alumnoDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(4, alumnoDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(5, alumnoDireccion.Direccion.Colonia.getIdColonia());

                return callableStatement.executeUpdate();
            });

            if (rowAffected == 1) {
                result.correct = true;

            }

        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;

        }

        return result;
    }

    @Override
    public Result AddNuevaDireccionByIdUsuario(UsuarioDireccion alumnoDireccion) {
        Result result = new Result();
        try {
            int rowAffected = jdbcTemplate.execute("{CALL ADDNuevaDireccionUsuario(?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, alumnoDireccion.Direccion.getCalle());
                callableStatement.setString(2, alumnoDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(3, alumnoDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(4, alumnoDireccion.Direccion.Colonia.getIdColonia());
                callableStatement.setInt(5, alumnoDireccion.Usuario.getIdUsuario());

                return callableStatement.executeUpdate();
            });
            if (rowAffected == 1) {
                result.correct = true;
            }

        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetDatosPorNombre(Usuario usuario) {
        Result result = new Result();
        try {
            result.correct = jdbcTemplate.execute("{CALL GetNombrePorLetras(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                int idAlumnoPrevio = 0;
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setString(4, usuario.Roll.getNombre());
                callableStatement.registerOutParameter(5, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    idAlumnoPrevio = resultSet.getInt("idUsuario");

                    if (!result.objects.isEmpty() && idAlumnoPrevio == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {

                        /*aqui solo agrego direccion*/
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                    } else {

                        /*aqui agrego alumno y direccion*/
                        UsuarioDireccion alumnoDireccion = new UsuarioDireccion();
                        alumnoDireccion.Usuario = new Usuario();
                        alumnoDireccion.Usuario.setIdUsuario(resultSet.getInt("idUsuario"));
                        alumnoDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                        alumnoDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        alumnoDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        alumnoDireccion.Usuario.setEmail(resultSet.getString("Email"));
                        alumnoDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                        alumnoDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                        alumnoDireccion.Usuario.setImagenPerfil(resultSet.getString("ImagenPerfil"));

                        alumnoDireccion.Usuario.Roll = new Roll();
                        alumnoDireccion.Usuario.Roll.setIdRoll(resultSet.getInt("IdRoll"));
                        alumnoDireccion.Usuario.Roll.setNombre(resultSet.getString("NombreRoll"));

                        alumnoDireccion.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        alumnoDireccion.Direcciones.add(direccion);

                        result.objects.add(alumnoDireccion);

                    }

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

    @Override
    public Result Add(List<UsuarioDireccion> usuariosDireccion) {
        Result result = new Result();
        for (UsuarioDireccion usuarioDireccion : usuariosDireccion) {
            this.Add(usuarioDireccion);
        }
        result.correct = true;
        return result;
    }

}
