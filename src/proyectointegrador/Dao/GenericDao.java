/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectointegrador.Dao;

/**
 *
 * @author sofim
 */
import java.sql.Connection;
import java.util.List;

public interface GenericDao<T> {

    void crear(T entidad, Connection conn) throws Exception;

    T leer(Long id, Connection conn) throws Exception;

    List<T> leerTodos(Connection conn) throws Exception;

    void actualizar(T entidad, Connection conn) throws Exception;

    void eliminar(Long id, Connection conn) throws Exception; // baja lógica
}
