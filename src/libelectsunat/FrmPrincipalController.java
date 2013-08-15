/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private ComboBox<?> cmbFormato;
    @FXML
    private ComboBox<?> cmbHoja;
    @FXML
    private Label label;
    @FXML
    private TableView<?> tbTabla;
    @FXML
    private TextField txtArchivo;
    @FXML
    private TextField txtDestino;

    @FXML
    void btnGenerarAction(ActionEvent event) {
    }

    @FXML
    void btnMostrarAction(ActionEvent event) {
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

            File archivo = fileChooser.showOpenDialog(null);
            String ruta = "";
            if (archivo != null) {
                ruta = archivo.getPath();
                Dialogs.showInformationDialog(null, ruta,
                        "Information Dialog", "RUTA");

                //obtener matriz de dicho archivo
                ExcelControl excelCont = new ExcelControl();
                String[][] matriz = excelCont.getExcelArray(archivo);
                
                //test
                for(int i=0; i<matriz.length;i++){
                    
                    String cad = Cadena.combine(matriz[i], "|");
                    System.out.println(cad);
                
                }
                
                
                
            }
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }





    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // stage = (Stage)btnGenerar.getScene().getWindow();
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
