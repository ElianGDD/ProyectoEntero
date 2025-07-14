
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;

public interface ColoniaDAO {
    
    Result GetALLColonia();
    Result GetColoniaByMunicipio(int IdMunicipio);
    
}
