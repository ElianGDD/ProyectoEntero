/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;

/**
 *
 * @author Alien 13
 */
public interface EstadoDAO {
    
    Result GetALLEstado();
    
    Result GetEstadoByIdPais(int IdPais);
    
}
