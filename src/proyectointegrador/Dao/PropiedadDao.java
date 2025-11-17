/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectointegrador.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import proyectointegrador.Entities.DestinoEnum;
import proyectointegrador.Entities.EscrituraNotarial;
import proyectointegrador.Entities.Propiedad;

/**
 *
 * @author sofim
 */
public class PropiedadDao implements GenericDao<Propiedad> {

    @Override
    public void crear(Propiedad p, Connection conn) throws Exception {
        String sql = """
            INSERT INTO propiedad
            (eliminado, padron_catastral, direccion, superficie_m2, destino, antiguedad, escritura_id)
            VALUES (?,?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBoolean(1, p.getEliminado() != null ? p.getEliminado() : false);
            ps.setString(2, p.getPadronCatastral());
            ps.setString(3, p.getDireccion());
            ps.setBigDecimal(4, p.getSuperficieM2());
            ps.setString(5, p.getDestino() != null ? p.getDestino().name() : null);
            ps.setInt(6, p.getAntiguedad());
            
            // Si tiene escritura asociada, usar su ID, sino null
            if (p.getEscrituraNotarial() != null && p.getEscrituraNotarial().getId() != null) {
                ps.setLong(7, p.getEscrituraNotarial().getId());
            } else {
                ps.setNull(7, java.sql.Types.BIGINT);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Propiedad leer(Long id, Connection conn) throws Exception {
        String sql = """
            SELECT p.*, e.id as escritura_id, e.eliminado as escritura_eliminado,
                   e.nro_escritura, e.fecha, e.notaria, e.tomo, e.folio, e.observaciones
            FROM propiedad p
            LEFT JOIN escritura_notarial e ON p.escritura_id = e.id
            WHERE p.id = ? AND p.eliminado = false
        """;

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
    public List<Propiedad> leerTodos(Connection conn) throws Exception {
        List<Propiedad> lista = new ArrayList<>();

        String sql = """
            SELECT p.*, e.id as escritura_id, e.eliminado as escritura_eliminado,
                   e.nro_escritura, e.fecha, e.notaria, e.tomo, e.folio, e.observaciones
            FROM propiedad p
            LEFT JOIN escritura_notarial e ON p.escritura_id = e.id
            WHERE p.eliminado = false
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    @Override
    public void actualizar(Propiedad p, Connection conn) throws Exception {
        String sql = """
            UPDATE propiedad
            SET padron_catastral=?, direccion=?, superficie_m2=?, destino=?, antiguedad=?, escritura_id=?
            WHERE id=? AND eliminado=false
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getPadronCatastral());
            ps.setString(2, p.getDireccion());
            ps.setBigDecimal(3, p.getSuperficieM2());
            ps.setString(4, p.getDestino() != null ? p.getDestino().name() : null);
            ps.setInt(5, p.getAntiguedad());
            
            // Si tiene escritura asociada, usar su ID, sino null
            if (p.getEscrituraNotarial() != null && p.getEscrituraNotarial().getId() != null) {
                ps.setLong(6, p.getEscrituraNotarial().getId());
            } else {
                ps.setNull(6, java.sql.Types.BIGINT);
            }
            
            ps.setLong(7, p.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id, Connection conn) throws Exception {
        String sql = "UPDATE propiedad SET eliminado=true WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Busca una propiedad por su padrón catastral
     */
    public Propiedad buscarPorPadronCatastral(String padron, Connection conn) throws Exception {
        String sql = """
            SELECT p.*, e.id as escritura_id, e.eliminado as escritura_eliminado,
                   e.nro_escritura, e.fecha, e.notaria, e.tomo, e.folio, e.observaciones
            FROM propiedad p
            LEFT JOIN escritura_notarial e ON p.escritura_id = e.id
            WHERE p.padron_catastral=? AND p.eliminado=false
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, padron);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    private Propiedad mapRow(ResultSet rs) throws Exception {
        Propiedad p = new Propiedad();

        p.setId(rs.getLong("id"));
        p.setEliminado(rs.getBoolean("eliminado"));
        p.setPadronCatastral(rs.getString("padron_catastral"));
        p.setDireccion(rs.getString("direccion"));
        p.setSuperficieM2(rs.getBigDecimal("superficie_m2"));
        
        String destinoStr = rs.getString("destino");
        if (destinoStr != null) {
            p.setDestino(DestinoEnum.valueOf(destinoStr));
        }
        
        p.setAntiguedad(rs.getInt("antiguedad"));

        // Mapear escritura notarial si existe
        Long escrituraId = rs.getLong("escritura_id");
        if (!rs.wasNull() && escrituraId != null) {
            EscrituraNotarial e = new EscrituraNotarial();
            e.setId(escrituraId);
            e.setEliminado(rs.getBoolean("escritura_eliminado"));
            e.setNroEscritura(rs.getString("nro_escritura"));
            if (rs.getDate("fecha") != null) {
                e.setFecha(rs.getDate("fecha").toLocalDate());
            }
            e.setNotaria(rs.getString("notaria"));
            e.setTomo(rs.getString("tomo"));
            e.setFolio(rs.getString("folio"));
            e.setObservaciones(rs.getString("observaciones"));
            p.setEscrituraNotarial(e);
        }

        return p;
    }
}
