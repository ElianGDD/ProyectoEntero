
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;


public interface MunicipioDAO {
    
   Result GetALLMunicipio();
   Result GetMunicipioByIdEstado(int IdEstado);
    
}
