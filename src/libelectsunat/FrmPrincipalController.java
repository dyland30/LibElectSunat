/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat;

import java.io.File;
import java.io.FileInputStream;
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
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import libelectsunat.control.Cadena;
import libelectsunat.control.ExcelControl;
import libelectsunat.control.XmlControl;
import libelectsunat.entidades.Campo;
import libelectsunat.entidades.FormatoSunat;
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
    private TableView tbTabla;
    @FXML
    private TextField txtArchivo;
    @FXML
    private TextField txtDestino;
    
    private File archivo;
    private String[][] matriz;
    private ObservableList<FormatoSunat> lsFormatos;
    private List<Campo> lsCampos;
    private FormatoSunat formatoSeleccionado;
    @FXML
    void btnGenerarAction(ActionEvent event) {
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
        } catch (Exception ex) 
        {
            Dialogs.showErrorDialog(null, ex.getMessage(),
                        "Se ha encontrado el siguiente error", "Error");
            Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnSeleccionarDestinoAction(ActionEvent event) {
    }

    @FXML
    void btnSeleccionarExcelAction(ActionEvent event) {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XLS", "*.xls"),
                    new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));
            fileChooser.setTitle("Seleccionar Archivo Excel");

            archivo = fileChooser.showOpenDialog(null);
            String ruta = "";
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

            }
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    void leerArchivo() throws Exception{
        if(txtArchivo.getText()!=null && txtArchivo.getText().length()>0){
                archivo = new File(txtArchivo.getText());
            }
       
        
        if (archivo != null){
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
    
    void cargarCampos(){
        lsCampos = new ArrayList<>();
        formatoSeleccionado = cmbFormato.getValue();
        if(formatoSeleccionado!=null){
            XmlControl xmlControl = new XmlControl();
            lsCampos = xmlControl.obtenerCampos(formatoSeleccionado.getArchivoFormato());
            for(Campo camp : lsCampos){
                System.out.println(" " +camp.getPosicion() + " "+camp.getDescripcion());
            
            }
            
        }
    
    }
    void validarDatos() throws Exception{
        //verificar que los campos esten cargados
        if(lsCampos.isEmpty()){
            throw new Exception("Debe seleccionar un formato!");
        }
            // recorrer matriz
            for(int i=1; i<matriz.length; i++){
                for(int j=0; j<matriz[0].length; j++){
                    Campo camp = buscarCampo(j+1);
                    if (camp==null){
                        throw new Exception("El formato no coincide con el archivo excel!");
                    
                    }
                    matriz[i][j]= Cadena.formatearCelda(matriz[i][j], camp);
                    
                }
            }
        
    }
    
    private Campo buscarCampo(int posicion){
        Campo camp=null;
        for(Campo c : lsCampos){
            if(c.getPosicion()==posicion){
                camp=c;
                break;
            }
        }
        return camp;
    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // stage = (Stage)btnGenerar.getScene().getWindow();
        //obtener propiedades y cargar combos
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("resources/config.properties"));
            String rutaXml = prop.getProperty("rutaFormatosXml");
            XmlControl xmlControl = new XmlControl();
            lsFormatos = xmlControl.obtenerFormatos(rutaXml);
            cmbFormato.setItems(lsFormatos);


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
