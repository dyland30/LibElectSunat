/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat.control;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import libelectsunat.entidades.Campo;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/**
 *
 * @author dcastillo
 */
public class Cadena {

    public static String join(Collection<?> s, String delimiter) {


        StringBuilder builder = new StringBuilder();
        Iterator<?> iter = s.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next());
            if (!iter.hasNext()) {
                break;
            }
            builder.append(delimiter);
        }
        return builder.toString();
    }

    public static String combine(String[] s, String glue) {
        int k = s.length;
        if (k == 0) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        out.append(s[0]);
        for (int x = 1; x < k; ++x) {
            out.append(glue).append(s[x]);
        }
        return out.toString();
    }
    public static String formatearNumero(String patron, Double numero){
        String output ="";
        if(patron!=null && patron.length()>0){
            DecimalFormat myFormatter = new DecimalFormat(patron);
            output = myFormatter.format(numero);
        } else{
            output = numero.toString();
        }
        return output;
    
    }
    public static String formatearCelda(String cadena, Campo campo){
        String cadFormateada = "";
        String formatoFecha = "dd/MM/yyyy";
        if(campo.getTipoDato()!=null && campo.getFormato()!=null && campo.getTipoDato().equals("fecha") && campo.getFormato().trim().length()>0){
            formatoFecha = campo.getFormato().trim();
        
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatoFecha);
        
        switch (campo.getTipoDato().trim().toLowerCase()) {
            case "numerico":
                Double valorNum;
                try{
                    valorNum = Double.parseDouble(cadena);
                }catch(Exception ex){
                    //poner valor por defecto si existe
                    valorNum = 0.0D;
                }
                if(valorNum==0.0D && campo.getValorDefecto().trim().length()>0){
                    cadFormateada = campo.getValorDefecto().trim();
                }else{
                    cadFormateada = formatearNumero(campo.getFormato(), valorNum);                
                }
               
                break;
            case "alfanumerico":
                if(cadena!=null && cadena.trim().length()>0){
                    cadFormateada = cadena;
                } else{
                    // verificar valor por defecto 
                    if(campo.getValorDefecto().trim().length()>0){
                        cadFormateada = campo.getValorDefecto();
                    }
                }
                
                break;
            case "fecha":
                // debe poder determinar si el campo es fecha o texto
                Double numFecha;
                try{
                    numFecha = Double.parseDouble(cadena);
                }catch(Exception ex){
                    //poner valor por defecto si existe
                    numFecha = 0.0D;
                }
                // solo para windows, en mac cambiar el segundo parametro por true
                if(numFecha>0 ){
                   Date fecha = HSSFDateUtil.getJavaDate(numFecha, false);
                   cadFormateada = dateFormat.format(fecha);
                } else{
                    //se trata de una fecha en formato texto
                    cadFormateada = cadena;
                
                }
                break;
        
        }
        // verificar que no exceda el tamaño limite 
        if(campo.getLongitud()>0){
            if(cadFormateada.length()>campo.getLongitud()){
                cadFormateada = cadFormateada.substring(0, campo.getLongitud()-1);
                
            }
        }
        
        return cadFormateada;
    
    }
}
