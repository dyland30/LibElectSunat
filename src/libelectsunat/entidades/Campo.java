/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat.entidades;

/**
 *
 * @author dcastillo
 */
public class Campo {
    private int posicion;
    private String descripcion;
    private String tipoDato; // NUMERICO, ALFANUMERICO, FECHA
    private int longitud;
    private Boolean longitudExacta;
    private String formato; // ###.##, 000.00, dd/MM/yyyy, yyyyMM00, etc
    private Boolean requerido;
    private String valorDefecto;
    

    /**
     * @return the posicion
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * @param posicion the posicion to set
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    /**
     * @return the tipoDato
     */
    public String getTipoDato() {
        return tipoDato;
    }

    /**
     * @param tipoDato the tipoDato to set
     */
    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    /**
     * @return the longitud
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the longitudExacta
     */
    public Boolean getLongitudExacta() {
        return longitudExacta;
    }

    /**
     * @param longitudExacta the longitudExacta to set
     */
    public void setLongitudExacta(Boolean longitudExacta) {
        this.longitudExacta = longitudExacta;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * @return the requerido
     */
    public Boolean getRequerido() {
        return requerido;
    }

    /**
     * @param requerido the requerido to set
     */
    public void setRequerido(Boolean requerido) {
        this.requerido = requerido;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the valorDefecto
     */
    public String getValorDefecto() {
        return valorDefecto;
    }

    /**
     * @param valorDefecto the valorDefecto to set
     */
    public void setValorDefecto(String valorDefecto) {
        this.valorDefecto = valorDefecto;
    }
    
    
    
}
