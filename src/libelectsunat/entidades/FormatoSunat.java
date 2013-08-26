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
    private String nombre;
    private String archivoFormato;

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
    
}
