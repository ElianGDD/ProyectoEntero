
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioJPARepositoryDAO extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByUserName(String userName);
}
