/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointegrador.Entities;

/**
 *
 * @author sofim
 */
import java.math.BigDecimal;

public class Propiedad extends Base {

    private Long id;
    private Boolean eliminado = Boolean.FALSE;

    private String padronCatastral;
    private String direccion;
    private BigDecimal superficieM2;
    private DestinoEnum destino;
    private Integer antiguedad;

    private EscrituraNotarial escrituraNotarial;

    public Propiedad() {
    }

    public Propiedad(Long id, Boolean eliminado, String padronCatastral, String direccion,
            BigDecimal superficieM2, DestinoEnum destino, Integer antiguedad,
            EscrituraNotarial escrituraNotarial) {
        this.id = id;
        this.eliminado = eliminado != null ? eliminado : Boolean.FALSE;
        this.padronCatastral = padronCatastral;
        this.direccion = direccion;
        this.superficieM2 = superficieM2;
        this.destino = destino;
        this.antiguedad = antiguedad;
        this.escrituraNotarial = escrituraNotarial;
    }

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

    public String getPadronCatastral() {
        return padronCatastral;
    }

    public void setPadronCatastral(String padronCatastral) {
        this.padronCatastral = padronCatastral;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigDecimal getSuperficieM2() {
        return superficieM2;
    }

    public void setSuperficieM2(BigDecimal superficieM2) {
        this.superficieM2 = superficieM2;
    }

    public DestinoEnum getDestino() {
        return destino;
    }

    public void setDestino(DestinoEnum destino) {
        this.destino = destino;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }

    public EscrituraNotarial getEscrituraNotarial() {
        return escrituraNotarial;
    }

    public void setEscrituraNotarial(EscrituraNotarial escrituraNotarial) {
        this.escrituraNotarial = escrituraNotarial;
    }

    @Override
    public String toString() {
        return "Propiedad{"
                + "id=" + id
                + ", eliminado=" + eliminado
                + ", padronCatastral='" + padronCatastral + '\''
                + ", direccion='" + direccion + '\''
                + ", superficieM2=" + superficieM2
                + ", destino=" + destino
                + ", antiguedad=" + antiguedad
                + ", escrituraNotarial=" + (escrituraNotarial != null ? escrituraNotarial.getId() : null)
                + '}';
    }
}
