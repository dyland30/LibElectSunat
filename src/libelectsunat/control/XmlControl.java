/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import libelectsunat.entidades.FormatoSunat;
import javax.xml.parsers.*;
import libelectsunat.entidades.Campo;
import libelectsunat.entidades.IndicadorOperacion;
import libelectsunat.entidades.Moneda;
import libelectsunat.entidades.Oportunidad;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author dcastillo
 */
public class XmlControl {

    public ObservableList<FormatoSunat> obtenerFormatos(String rutaXml) {
        ObservableList<FormatoSunat> ls = FXCollections.observableArrayList();
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(rutaXml);

            NodeList nodosFormatos = doc.getElementsByTagName("formato");
            int totalNodos = nodosFormatos.getLength();
            for (int i = 0; i < totalNodos; i++) {
                Node nd = nodosFormatos.item(i);
                //nd.getAttributes().getNamedItem("")
                FormatoSunat fsunat = new FormatoSunat();
                fsunat.setNombre(nd.getAttributes().getNamedItem("nombre").getNodeValue());
                fsunat.setArchivoFormato(nd.getAttributes().getNamedItem("archivoFormato").getNodeValue());
                fsunat.setCodigo(nd.getAttributes().getNamedItem("codigo").getNodeValue());
                fsunat.setEstructuraNombre(nd.getAttributes().getNamedItem("estructuraNombre").getNodeValue());
                ls.add(fsunat);

            }
        } catch (ParserConfigurationException | SAXException pce) {
            System.out.println(pce.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return ls;

    }
    
    public ObservableList<Moneda> obtenerMonedas(String rutaXml) {
        ObservableList<Moneda> ls = FXCollections.observableArrayList();
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(rutaXml);

            NodeList nodos = doc.getElementsByTagName("moneda");
            int totalNodos = nodos.getLength();
            for (int i = 0; i < totalNodos; i++) {
                Node nd = nodos.item(i);
                //nd.getAttributes().getNamedItem("")
                Moneda ent = new Moneda();
                ent.setCodigo(nd.getAttributes().getNamedItem("codigo").getNodeValue());
                ent.setDescripcion(nd.getAttributes().getNamedItem("descripcion").getNodeValue());
                ls.add(ent);
            }
        } catch (ParserConfigurationException | SAXException pce) {
            System.out.println(pce.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return ls;

    }
    
    public ObservableList<IndicadorOperacion> obtenerIndicadorOperaciones(String rutaXml) {
        ObservableList<IndicadorOperacion> ls = FXCollections.observableArrayList();
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(rutaXml);

            NodeList nodos = doc.getElementsByTagName("indOperacion");
            int totalNodos = nodos.getLength();
            for (int i = 0; i < totalNodos; i++) {
                Node nd = nodos.item(i);
                //nd.getAttributes().getNamedItem("")
                IndicadorOperacion ent = new IndicadorOperacion();
                ent.setCodigo(nd.getAttributes().getNamedItem("codigo").getNodeValue());
                ent.setDescripcion(nd.getAttributes().getNamedItem("descripcion").getNodeValue());
                ls.add(ent);
            }
        } catch (ParserConfigurationException | SAXException pce) {
            System.out.println(pce.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return ls;

    }
    
    public ObservableList<Oportunidad> obtenerOportunidadesPresentacion(String rutaXml) {
        ObservableList<Oportunidad> ls = FXCollections.observableArrayList();
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(rutaXml);

            NodeList nodos = doc.getElementsByTagName("oportunidad");
            int totalNodos = nodos.getLength();
            for (int i = 0; i < totalNodos; i++) {
                Node nd = nodos.item(i);
                //nd.getAttributes().getNamedItem("")
                Oportunidad ent = new Oportunidad();
                ent.setCodigo(nd.getAttributes().getNamedItem("codigo").getNodeValue());
                ent.setDescripcion(nd.getAttributes().getNamedItem("descripcion").getNodeValue());
                ls.add(ent);
            }
        } catch (ParserConfigurationException | SAXException pce) {
            System.out.println(pce.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return ls;

    }


    public List<Campo> obtenerCampos(String rutaXml) {

        List<Campo> ls = new ArrayList<>();
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(rutaXml);

            NodeList nodosCols = doc.getElementsByTagName("col");
            int totalNodos = nodosCols.getLength();
            for (int i = 0; i < totalNodos; i++) {
                Node nd = nodosCols.item(i);
                //nd.getAttributes().getNamedItem("")
                Campo campo = new Campo();
                // obtener posicion
                String strPpos = nd.getAttributes().getNamedItem("pos").getNodeValue();
                campo.setPosicion(Integer.parseInt(strPpos));
                NodeList nodosHijos = nd.getChildNodes();
                if (nodosHijos != null && nodosHijos.getLength() > 0) {
                    for (int j = 0; j < nodosHijos.getLength(); j++) {
                        Node nodo = nodosHijos.item(j);
                        switch (nodo.getNodeName().toLowerCase().trim()) {
                            case "descripcion":
                                campo.setDescripcion(nodo.getTextContent());
                                break;
                            case "tipodato":
                                campo.setTipoDato(nodo.getTextContent());
                                break;
                            case "longitud":
                                int lon = 0;
                                if (nodo.getTextContent() != null && nodo.getTextContent().length() > 0) {
                                    lon = Integer.parseInt(nodo.getTextContent());
                                }
                                campo.setLongitud(lon);
                                break;
                            case "longitudexacta":
                                Boolean valor = false;
                                if (nodo.getTextContent() != null && nodo.getTextContent().length() > 0) {
                                    if (nodo.getTextContent().trim().toLowerCase().equals("si")) {
                                        valor = true;
                                    }
                                }
                                campo.setLongitudExacta(valor);
                                break;
                            case "formato":
                                campo.setFormato(nodo.getTextContent());
                                break;
                            case "requerido":
                                Boolean val = false;
                                if (nodo.getTextContent() != null && nodo.getTextContent().length() > 0) {
                                    if (nodo.getTextContent().trim().toLowerCase().equals("si")) {
                                        val = true;
                                    }
                                }
                                campo.setRequerido(val);
                                break;
                            case "valordefecto":
                                campo.setValorDefecto(nodo.getTextContent());
                                break;
                        }
                    }
                }

                ls.add(campo);

            }
        } catch (ParserConfigurationException | SAXException pce) {
            System.out.println(pce.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return ls;

    }
}
