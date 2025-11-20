package proyectointegrador.Service;

import java.sql.Connection;
import java.util.List;
import proyectointegrador.Config.DatabaseConnection;
import proyectointegrador.Dao.EscrituraNotarialDao;
import proyectointegrador.Dao.PropiedadDao;
import proyectointegrador.Entities.EscrituraNotarial;
import proyectointegrador.Entities.Propiedad;

public class PropiedadService implements GenericService<Propiedad> {

    private final PropiedadDao propiedadDao = new PropiedadDao();
    private final EscrituraNotarialDao escrituraDao = new EscrituraNotarialDao();

    @Override
    public Propiedad insertar(Propiedad propiedad) throws Exception {
        validarPropiedad(propiedad);

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            if (propiedad.getEscrituraNotarial() != null) {
                EscrituraNotarial escritura = propiedad.getEscrituraNotarial();
                validarEscritura(escritura);

                if (escritura.getId() == null) {
                    escritura.setEliminado(false);
                    escrituraDao.crear(escritura, conn);
                }
            }

            propiedad.setEliminado(false);
            propiedadDao.crear(propiedad, conn);

            conn.commit();
            return propiedad;

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new Exception("Error al insertar propiedad: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public void actualizar(Propiedad propiedad) throws Exception {
        validarPropiedad(propiedad);

        if (propiedad.getId() == null) {
            throw new IllegalArgumentException("El ID de la propiedad es requerido para actualizar");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            Propiedad existente = propiedadDao.leer(propiedad.getId(), conn);
            if (existente == null) {
                throw new IllegalArgumentException("No se encontró la propiedad con ID: " + propiedad.getId());
            }

            if (propiedad.getEscrituraNotarial() != null) {
                EscrituraNotarial escritura = propiedad.getEscrituraNotarial();
                validarEscritura(escritura);

                if (escritura.getId() == null) {
                    escritura.setEliminado(false);
                    escrituraDao.crear(escritura, conn);
                } else {
                    escrituraDao.actualizar(escritura, conn);
                }
            }

            propiedadDao.actualizar(propiedad, conn);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new Exception("Error al actualizar propiedad: " + e.getMessage(), e);
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

            Propiedad propiedad = propiedadDao.leer(id, conn);
            if (propiedad == null) {
                throw new IllegalArgumentException("No se encontró la propiedad con ID: " + id);
            }

            propiedadDao.eliminar(id, conn);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new Exception("Error al eliminar propiedad: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public Propiedad getById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("El ID es requerido");
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            return propiedadDao.leer(id, conn);
        }
    }

    @Override
    public List<Propiedad> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return propiedadDao.leerTodos(conn);
        }
    }

    public Propiedad buscarPorPadronCatastral(String padron) throws Exception {
        if (padron == null || padron.trim().isEmpty()) {
            throw new IllegalArgumentException("El padrón catastral es requerido");
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            return propiedadDao.buscarPorPadronCatastral(padron.trim().toUpperCase(), conn);
        }
    }

    private void validarPropiedad(Propiedad propiedad) {
        if (propiedad == null) {
            throw new IllegalArgumentException("La propiedad no puede ser nula");
        }
        if (propiedad.getPadronCatastral() == null || propiedad.getPadronCatastral().trim().isEmpty()) {
            throw new IllegalArgumentException("El padrón catastral es obligatorio");
        }
        if (propiedad.getDireccion() == null || propiedad.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        if (propiedad.getSuperficieM2() == null || propiedad.getSuperficieM2().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La superficie debe ser mayor a cero");
        }
        if (propiedad.getDestino() == null) {
            throw new IllegalArgumentException("El destino es obligatorio");
        }
        if (propiedad.getAntiguedad() == null || propiedad.getAntiguedad() < 0) {
            throw new IllegalArgumentException("La antigüedad debe ser mayor o igual a cero");
        }
    }

    /**
     * Valida los datos de una escritura notarial
     */
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
