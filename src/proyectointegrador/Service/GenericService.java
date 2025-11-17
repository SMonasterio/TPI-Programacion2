package proyectointegrador.Service;

import java.util.List;

/**
 * Interfaz genérica para servicios
 * @param <T> Tipo de entidad
 */
public interface GenericService<T> {
    
    /**
     * Inserta una nueva entidad
     * @param entidad Entidad a insertar
     * @return Entidad insertada con ID generado
     * @throws Exception Si ocurre un error
     */
    T insertar(T entidad) throws Exception;
    
    /**
     * Actualiza una entidad existente
     * @param entidad Entidad a actualizar
     * @throws Exception Si ocurre un error
     */
    void actualizar(T entidad) throws Exception;
    
    /**
     * Elimina lógicamente una entidad por su ID
     * @param id ID de la entidad a eliminar
     * @throws Exception Si ocurre un error
     */
    void eliminar(Long id) throws Exception;
    
    /**
     * Obtiene una entidad por su ID
     * @param id ID de la entidad
     * @return Entidad encontrada o null
     * @throws Exception Si ocurre un error
     */
    T getById(Long id) throws Exception;
    
    /**
     * Obtiene todas las entidades no eliminadas
     * @return Lista de entidades
     * @throws Exception Si ocurre un error
     */
    List<T> getAll() throws Exception;
}

