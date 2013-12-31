/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import libelectsunat.control.Cadena;
import libelectsunat.control.ExcelControl;
import libelectsunat.control.XmlControl;
import libelectsunat.entidades.Campo;
import libelectsunat.entidades.FormatoSunat;
import libelectsunat.entidades.IndicadorOperacion;
import libelectsunat.entidades.Moneda;
import libelectsunat.entidades.Oportunidad;
import libelectsunat.util.MatrixDataSource;

/**
 *
 * @author dcastillo
 */
public class FrmPrincipalController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnMostrar;
    @FXML
    private Button btnSeleccionarDestino;
    @FXML
    private Button btnSeleccionarExcel;
    @FXML
    private ComboBox<FormatoSunat> cmbFormato;
    @FXML
    private ComboBox<?> cmbHoja;
    @FXML
    private Label label;
    @FXML
    private ComboBox<IndicadorOperacion> cmbIndOperacion;

    @FXML
    private ComboBox<Moneda> cmbMoneda;
    
    @FXML
    private ComboBox<Oportunidad> cmbOportunidad;
     
    @FXML
    private TableView tbTabla;
    @FXML
    private TextField txtArchivo;
    @FXML
    private TextField txtDestino;
    
    @FXML
    private TextField txtDia;

    @FXML
    private TextField txtMensaje;
     
    @FXML
    private TextField txtMes;

    @FXML
    private TextField txtRuc;

    @FXML
    private TextField txtYear;
    
    private File archivo;
    private String[][] matriz;
    private ObservableList<FormatoSunat> lsFormatos;
    private ObservableList<Moneda> lsMonedas;
    private ObservableList<IndicadorOperacion> lsIndicadorOperaciones;
    private ObservableList<Oportunidad> lsOportunidades;
    
    private List<Campo> lsCampos;
    private FormatoSunat formatoSeleccionado;
    private String nombreArchivoTexto;
    private String  NEW_LINE = "\r\n";
    @FXML
    void btnGenerarAction(ActionEvent event) {
        try{
            
        cargarCampos();
        leerArchivo();
        validarDatos();
        generarNombreArchivoTexto();
        generarArchivoTexto();
        
        mostrarMensaje("INFO", "Se generó el archivo "+nombreArchivoTexto+ " correctamente");
       // Dialogs.showInformationDialog(null, "Se generó el archivo "+nombreArchivoTexto+ " correctamente");
        
        } catch (Exception ex) {
            mostrarMensaje("ERR", "ERROR: "+ex.getMessage());
          //  Dialogs.showErrorDialog(null, ex.getMessage(),
          //          "Se ha encontrado el siguiente error");
            Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void cmbFormatoAction(ActionEvent event) {
        cargarCampos();
    }

    @FXML
    void btnMostrarAction(ActionEvent event) {
        try {
            leerArchivo();
            validarDatos();
            poblarTabla();
        } catch (Exception ex) {
            mostrarMensaje("ERR", "ERROR: "+ex.getMessage());
            
           // Dialogs.showErrorDialog(null, ex.getMessage(),
           //         "Se ha encontrado el siguiente error", "Error");
            Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnSeleccionarDestinoAction(ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Seleccionar Carpeta Destino");
        File carpeta = dirChooser.showDialog(null);
        if (carpeta != null) {
            txtDestino.setText(carpeta.getAbsolutePath());

        }

    }

    @FXML
    void btnSeleccionarExcelAction(ActionEvent event) {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("EXCEL", "*.xls*"));
            fileChooser.setTitle("Seleccionar Archivo Excel");

            archivo = fileChooser.showOpenDialog(null);
            String ruta;
            if (archivo != null) {
                ruta = archivo.getPath();


                txtArchivo.setText(ruta);
                //test
              /*  for(int i=0; i<matriz.length;i++){
                    
                 String cad = Cadena.combine(matriz[i], "|");
                 System.out.println(cad);
                
                 }
                 */
                leerArchivo();
                poblarTabla();
                txtMensaje.setVisible(false);
                
            }
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void leerArchivo() throws Exception {
       
        if (txtArchivo.getText() != null && txtArchivo.getText().length() > 0) {
            archivo = new File(txtArchivo.getText());
           
        }


        if (archivo != null) {
            ExcelControl excelCont = new ExcelControl();
            matriz = excelCont.getExcelArray(archivo);

        }
        

    }

    void poblarTabla() throws Exception {
        // columnas

        if (matriz != null && matriz.length > 0 && matriz[0].length > 0) {

            int cantColumnas = matriz[0].length;
            int cantFilas = matriz.length;

            MatrixDataSource ds = new MatrixDataSource(matriz);
            //limpiar tabla
            if (tbTabla.getColumns() != null && tbTabla.getColumns().size() > 0) {
                tbTabla.setItems(null);
                tbTabla.getColumns().clear();
            }
            tbTabla.setItems(ds.getData());
            tbTabla.getColumns().addAll(ds.getColumns());

        }

    }

    void cargarCampos() {
        lsCampos = new ArrayList<>();
        formatoSeleccionado = cmbFormato.getValue();
        if (formatoSeleccionado != null) {
            XmlControl xmlControl = new XmlControl();
            lsCampos = xmlControl.obtenerCampos(formatoSeleccionado.getArchivoFormato());
            for (Campo camp : lsCampos) {
                System.out.println(" " + camp.getPosicion() + " " + camp.getDescripcion());

            }

        }

    }

    void validarDatos() throws Exception {
        //verificar que los campos esten cargados
        if (lsCampos.isEmpty()) {
            throw new Exception("Debe seleccionar un formato!");
        }
        // recorrer matriz
        for (int i = 1; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                Campo camp = buscarCampo(j + 1);
                if (camp == null) {
                    throw new Exception("El formato no coincide con el archivo excel!");
                }
                matriz[i][j] = Cadena.formatearCelda(matriz[i][j], camp);
            }
        }
    }

    private Campo buscarCampo(int posicion) {
        Campo camp = null;
        for (Campo c : lsCampos) {
            if (c.getPosicion() == posicion) {
                camp = c;
                break;
            }
        }
        return camp;

    }
    void mostrarMensaje(String tipo, String mensaje){
        if(tipo.equals("INFO")){
            txtMensaje.setStyle("-fx-background-color: lightgreen;" +
                                "-fx-font-weight: bold;" +
                                "-fx-text-fill: green;" +
                                "-fx-font-size: 15px;");
            
        } else{
            txtMensaje.setStyle("-fx-background-color: pink;" +
                                "-fx-font-weight: bold;" +
                                "-fx-text-fill: red;" +
                                "-fx-font-size: 15px;");
        }
        txtMensaje.setText(mensaje);
        txtMensaje.setVisible(true);
        
    }

    void generarNombreArchivoTexto() throws Exception {
        if(formatoSeleccionado!=null){
            String ruc = txtRuc.getText();
            String dia = txtDia.getText();
            
            String mes = txtMes.getText();
            String year = txtYear.getText();
            Moneda mon = cmbMoneda.getValue();
            IndicadorOperacion indOp = cmbIndOperacion.getValue();
            Oportunidad oport = cmbOportunidad.getValue();
            String patron = formatoSeleccionado.getEstructuraNombre();
            
            String indInf ="0";
            if (matriz != null && matriz.length > 0 && matriz[0].length > 0) indInf ="1";
             
            
            if(ruc==null || ruc.trim().length()==0) throw new Exception("DEBE INGRESAR UN NÚMERO DE RUC");
            
            
            if(dia==null || dia.trim().length()==0) dia = "00";
            if(mes==null || mes.trim().length()==0) mes = "00";
            if(year==null || year.trim().length()==0) year = "0000";
            
            if(mon==null) {
                mon = new Moneda();
                mon.setCodigo("1");
            }
            if(oport==null){
                oport = new Oportunidad();
                oport.setCodigo("00");
            }
            if(indOp==null){
                indOp = new IndicadorOperacion();
                indOp.setCodigo("1");
                
            }
            
            nombreArchivoTexto=patron.replaceFirst("RRRRRRRRRRR", ruc).replaceFirst("AAAA", year).replaceFirst("MM", mes).replaceFirst("DD", dia)
                    .replaceFirst("LLLLLL", formatoSeleccionado.getCodigo()).replaceFirst("CC", oport.getCodigo()).replaceFirst("O", indOp.getCodigo()).replaceFirst("I", indInf)
                    .replaceFirst("M", mon.getCodigo());
            
        
        } 
        
    }

    void generarArchivoTexto() {
        if(matriz ==null || matriz.length==0){
            return;
        }
        
        Writer out = null;
        try {
            
            File archivoTexto = new File(txtDestino.getText() + "/"+nombreArchivoTexto);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoTexto), "windows-1252"));
            //recorremos el array
            for(int i=1; i<matriz.length;i++){
                    
                 String cad = Cadena.combine(matriz[i], "|");
                 out.write(cad+"|");
                 out.write(NEW_LINE);
                 
            }
            
            out.flush();


        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(out!=null) out.close();
                
            } catch (IOException ex) {
                Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // stage = (Stage)btnGenerar.getScene().getWindow();
        //obtener propiedades y cargar combos
        Properties prop = new Properties();
        try {
          //  ((Stage)btnGenerar.getScene().getWindow()).setTitle("Generador de Libros Electrónicos");
            prop.load(new FileInputStream("resources/config.properties"));
            String rutaXml = prop.getProperty("rutaFormatosXml");
            XmlControl xmlControl = new XmlControl();
            lsFormatos = xmlControl.obtenerFormatos(rutaXml);
            lsMonedas = xmlControl.obtenerMonedas(rutaXml);
            lsIndicadorOperaciones = xmlControl.obtenerIndicadorOperaciones(rutaXml);
            lsOportunidades = xmlControl.obtenerOportunidadesPresentacion(rutaXml);
            cmbFormato.setItems(lsFormatos);
            cmbMoneda.setItems(lsMonedas);
            cmbIndOperacion.setItems(lsIndicadorOperaciones);
            cmbOportunidad.setItems(lsOportunidades);
            
            

        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void initialize() {
        assert btnGenerar != null : "fx:id=\"btnGenerar\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";
        assert btnMostrar != null : "fx:id=\"btnMostrar\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";
        assert btnSeleccionarDestino != null : "fx:id=\"btnSeleccionarDestino\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";
        assert btnSeleccionarExcel != null : "fx:id=\"btnSeleccionarExcel\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";
        assert cmbFormato != null : "fx:id=\"cmbFormato\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";
        assert cmbHoja != null : "fx:id=\"cmbHoja\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";
        assert tbTabla != null : "fx:id=\"tbTabla\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";
        assert txtArchivo != null : "fx:id=\"txtArchivo\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";
        assert txtDestino != null : "fx:id=\"txtDestino\" was not injected: check your FXML file 'FrmPrincipal.fxml'.";


    }
}
