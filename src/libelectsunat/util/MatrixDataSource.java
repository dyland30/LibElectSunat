package libelectsunat.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import org.javafxdata.datasources.provider.DataSource;
import org.javafxdata.datasources.provider.ListWrapper;
import org.javafxdata.datasources.provider.TableWrapper;

/**
 *
 * @author johan
 */
public class MatrixDataSource extends DataSource<String[]> implements ListWrapper, TableWrapper {

   
    private List<String> selectedColumnNames = new LinkedList<>();
   

    
    public MatrixDataSource(String[][] datos) {
        if(datos!=null && datos.length >0){
             for(int i=0; i<datos.length; i++){
                 if(i==0){
                     selectedColumnNames.addAll(Arrays.asList(datos[0]));
                      
                 }else{
                     add(datos[i]);
                 }
            }
        }
       
    }

    @Override
    protected void buildColumns() {
        for (final String columnName : selectedColumnNames) {
            TableColumn<String[], String> tc = new TableColumn<>(columnName);

            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> cdf) {
                    String[] myRow = cdf.getValue();
                    return (ObservableValue<String>) new ReadOnlyStringWrapper(getMyElement(myRow, columnName));
                }
            });
            addColumn(columnName, tc);
        }
    }

    private String getMyElement(String[] myRow, String columnName) {
        String valor = "";
        if(selectedColumnNames!=null && selectedColumnNames.size()>0){
            int columnIndex = selectedColumnNames.indexOf(columnName);
            valor =(String) myRow[columnIndex];
        }
        return valor;
    }

  
}
