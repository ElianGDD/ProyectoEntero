package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.UsuarioDireccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import java.util.List;


public interface UsuarioDAO  {
    
     Result GetAll();

     Result Add(UsuarioDireccion alumnoDireccion);
     
     Result GetDatosAlumnoPDByIdAlumno(int IdUsuario);
     
     Result GetDatosBasicosUsuarioByIdUsuario(int IdUsuario);
     
     Result UpdateAlumnoDatosBasicos(UsuarioDireccion alumnoDireccion);
     
     Result UpdateAlumnoDatosDireccion(UsuarioDireccion alumnoDireccion);
     
     Result AddNuevaDireccionByIdUsuario(UsuarioDireccion alumnoDireccion);
     
     Result GetDatosPorNombre(Usuario usuario);
     
     Result Add(List<UsuarioDireccion> usuariosDireccion);
     
}
