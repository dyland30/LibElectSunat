/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat.entidades;

/**
 *
 * @author dcastillo
 */
public class FormatoSunat {
    private String codigo;
    private String nombre;
    private String archivoFormato;
    private String estructuraNombre;
    

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the archivoFormato
     */
    public String getArchivoFormato() {
        return archivoFormato;
    }

    /**
     * @param archivoFormato the archivoFormato to set
     */
    public void setArchivoFormato(String archivoFormato) {
        this.archivoFormato = archivoFormato;
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }

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
     * @return the estructuraNombre
     */
    public String getEstructuraNombre() {
        return estructuraNombre;
    }

    /**
     * @param estructuraNombre the estructuraNombre to set
     */
    public void setEstructuraNombre(String estructuraNombre) {
        this.estructuraNombre = estructuraNombre;
    }
    
}
