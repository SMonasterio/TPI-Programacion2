package proyectointegrador.Service;

import java.sql.Connection;
import java.util.List;
import proyectointegrador.Config.DatabaseConnection;
import proyectointegrador.Dao.EscrituraNotarialDao;
import proyectointegrador.Entities.EscrituraNotarial;

public class EscrituraNotarialService implements GenericService<EscrituraNotarial> {

    private final EscrituraNotarialDao escrituraDao = new EscrituraNotarialDao();

    @Override
    public EscrituraNotarial insertar(EscrituraNotarial escritura) throws Exception {
        validarEscritura(escritura);
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            escritura.setEliminado(false);
            escrituraDao.crear(escritura, conn);

            conn.commit();
            return escritura;

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new Exception("Error al insertar escritura notarial: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public void actualizar(EscrituraNotarial escritura) throws Exception {
        validarEscritura(escritura);
        
        if (escritura.getId() == null) {
            throw new IllegalArgumentException("El ID de la escritura es requerido para actualizar");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            EscrituraNotarial existente = escrituraDao.leer(escritura.getId(), conn);
            if (existente == null) {
                throw new IllegalArgumentException("No se encontró la escritura con ID: " + escritura.getId());
            }

            escrituraDao.actualizar(escritura, conn);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new Exception("Error al actualizar escritura notarial: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("El ID es requerido");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            EscrituraNotarial escritura = escrituraDao.leer(id, conn);
            if (escritura == null) {
                throw new IllegalArgumentException("No se encontró la escritura con ID: " + id);
            }

            escrituraDao.eliminar(id, conn);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new Exception("Error al eliminar escritura notarial: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public EscrituraNotarial getById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("El ID es requerido");
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            return escrituraDao.leer(id, conn);
        }
    }

    @Override
    public List<EscrituraNotarial> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return escrituraDao.leerTodos(conn);
        }
    }


    public EscrituraNotarial buscarPorNroEscritura(String nroEscritura) throws Exception {
        if (nroEscritura == null || nroEscritura.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de escritura es requerido");
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            return escrituraDao.buscarPorNroEscritura(nroEscritura.trim().toUpperCase(), conn);
        }
    }

    private void validarEscritura(EscrituraNotarial escritura) {
        if (escritura == null) {
            throw new IllegalArgumentException("La escritura notarial no puede ser nula");
        }
        if (escritura.getNroEscritura() == null || escritura.getNroEscritura().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de escritura es obligatorio");
        }
        if (escritura.getFecha() == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        if (escritura.getNotaria() == null || escritura.getNotaria().trim().isEmpty()) {
            throw new IllegalArgumentException("La notaría es obligatoria");
        }
    }
}

