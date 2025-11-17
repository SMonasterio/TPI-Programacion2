/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointegrador.Entities;

/**
 *
 * @author sofim
 */
import java.time.LocalDate;

public class EscrituraNotarial extends Base {

    private Long id;
    private Boolean eliminado = Boolean.FALSE;

    private String nroEscritura;
    private LocalDate fecha;
    private String notaria;
    private String tomo;
    private String folio;
    private String observaciones;

    // Constructor vacío
    public EscrituraNotarial() {
    }

    // Constructor completo
    public EscrituraNotarial(Long id, Boolean eliminado, String nroEscritura, LocalDate fecha,
            String notaria, String tomo, String folio, String observaciones) {
        this.id = id;
        this.eliminado = eliminado != null ? eliminado : Boolean.FALSE;
        this.nroEscritura = nroEscritura;
        this.fecha = fecha;
        this.notaria = notaria;
        this.tomo = tomo;
        this.folio = folio;
        this.observaciones = observaciones;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEliminado() {
        return eliminado != null ? eliminado : Boolean.FALSE;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado != null ? eliminado : Boolean.FALSE;
    }

    public String getNroEscritura() {
        return nroEscritura;
    }

    public void setNroEscritura(String nroEscritura) {
        this.nroEscritura = nroEscritura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNotaria() {
        return notaria;
    }

    public void setNotaria(String notaria) {
        this.notaria = notaria;
    }

    public String getTomo() {
        return tomo;
    }

    public void setTomo(String tomo) {
        this.tomo = tomo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "EscrituraNotarial{"
                + "id=" + id
                + ", eliminado=" + eliminado
                + ", nroEscritura='" + nroEscritura + '\''
                + ", fecha=" + fecha
                + ", notaria='" + notaria + '\''
                + ", tomo='" + tomo + '\''
                + ", folio='" + folio + '\''
                + ", observaciones='" + observaciones + '\''
                + '}';
    }
}
