/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat.control;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import libelectsunat.entidades.FormatoSunat;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
/**
 *
 * @author dcastillo
 */
public class XmlControl {
    public ObservableList<FormatoSunat> obtenerFormatos(String rutaXml){
        ObservableList<FormatoSunat> ls = FXCollections.observableArrayList();
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try{
         DocumentBuilder db = dbf.newDocumentBuilder();
         doc = db.parse(rutaXml);
         
         NodeList nodosFormatos = doc.getElementsByTagName("formato");
         int totalNodos = nodosFormatos.getLength();
         for(int i=0; i<totalNodos;i++){
             Node nd = nodosFormatos.item(i);
             //nd.getAttributes().getNamedItem("")
             FormatoSunat fsunat = new FormatoSunat();
             fsunat.setNombre(nd.getAttributes().getNamedItem("nombre").getNodeValue());
             fsunat.setArchivoFormato(nd.getAttributes().getNamedItem("archivoFormato").getNodeValue());
             ls.add(fsunat);
         
         }
        } catch (ParserConfigurationException | SAXException pce) {
            System.out.println(pce.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return ls;
    
    }
}
