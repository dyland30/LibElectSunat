/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat.entidades;

/**
 *
 * @author Daniel
 */
public class IndicadorOperacion {
    private String codigo;
    private String descripcion;

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    @Override
    public String toString(){
        return this.descripcion;
    
    }
    
}
