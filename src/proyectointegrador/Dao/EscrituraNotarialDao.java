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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import proyectointegrador.Entities.EscrituraNotarial;

public class EscrituraNotarialDao implements GenericDao<EscrituraNotarial> {

    @Override
    public void crear(EscrituraNotarial e, Connection conn) throws Exception {
        String sql = """
            INSERT INTO escritura_notarial
            (eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones)
            VALUES (?,?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBoolean(1, e.getEliminado());
            ps.setString(2, e.getNroEscritura());
            ps.setDate(3, Date.valueOf(e.getFecha()));
            ps.setString(4, e.getNotaria());
            ps.setString(5, e.getTomo());
            ps.setString(6, e.getFolio());
            ps.setString(7, e.getObservaciones());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public EscrituraNotarial leer(Long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM escritura_notarial WHERE id = ? AND eliminado = false";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<EscrituraNotarial> leerTodos(Connection conn) throws Exception {
        List<EscrituraNotarial> lista = new ArrayList<>();

        String sql = "SELECT * FROM escritura_notarial WHERE eliminado = false";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    @Override
    public void actualizar(EscrituraNotarial e, Connection conn) throws Exception {
        String sql = """
            UPDATE escritura_notarial
            SET nro_escritura=?, fecha=?, notaria=?, tomo=?, folio=?, observaciones=?
            WHERE id=? AND eliminado=false
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNroEscritura());
            ps.setDate(2, Date.valueOf(e.getFecha()));
            ps.setString(3, e.getNotaria());
            ps.setString(4, e.getTomo());
            ps.setString(5, e.getFolio());
            ps.setString(6, e.getObservaciones());
            ps.setLong(7, e.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id, Connection conn) throws Exception {
        String sql = "UPDATE escritura_notarial SET eliminado=true WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public EscrituraNotarial buscarPorNroEscritura(String nro, Connection conn) throws Exception {
        String sql = "SELECT * FROM escritura_notarial WHERE nro_escritura=? AND eliminado=false";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nro);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    private EscrituraNotarial mapRow(ResultSet rs) throws Exception {
        EscrituraNotarial e = new EscrituraNotarial();

        e.setId(rs.getLong("id"));
        e.setEliminado(rs.getBoolean("eliminado"));
        e.setNroEscritura(rs.getString("nro_escritura"));
        e.setFecha(rs.getDate("fecha").toLocalDate());
        e.setNotaria(rs.getString("notaria"));
        e.setTomo(rs.getString("tomo"));
        e.setFolio(rs.getString("folio"));
        e.setObservaciones(rs.getString("observaciones"));

        return e;
    }

}
