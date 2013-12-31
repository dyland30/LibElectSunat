/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat.control;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author dcastillo
 */
public class ExcelControl {

    public String[][] getExcelArray(File archivo) throws Exception {

        String[][] matriz = null;

        Workbook wb = null;
        NPOIFSFileSystem npoifs = null;
        OPCPackage pkg = null;
        
        try {
            
            try{
                npoifs = new NPOIFSFileSystem(archivo);
                wb = WorkbookFactory.create(npoifs);
                
            } catch(OfficeXmlFileException ofe){
                pkg = OPCPackage.open(archivo);
                wb = WorkbookFactory.create(pkg);
            
            }
            
            //determinar el tamaño de la matriz basandonos en la primera fila
            Sheet hoja = wb.getSheetAt(0);
            int filaInicio = hoja.getFirstRowNum();
            int colInicio = hoja.getRow(hoja.getFirstRowNum()).getFirstCellNum();
            

            int cantCols = (hoja.getRow(hoja.getFirstRowNum()).getLastCellNum() - hoja.getRow(hoja.getFirstRowNum()).getFirstCellNum());
            int cantRows = (hoja.getLastRowNum() - hoja.getFirstRowNum()) + 1;

            System.out.println("Cant Filas: " + cantRows);
            System.out.println("Cant Columnas: " + cantCols);

            System.out.println("Fila Inicio: " + filaInicio);
            System.out.println("Columna Inicio: " + colInicio);

            //creamos el array
            matriz = new String[cantRows][cantCols];

            //Iterator rows = hoja.rowIterator ();

            for (int i = 0; i < cantRows; i++) {
                Row fila = hoja.getRow(i + filaInicio);
                if (fila != null) {
                    for (int j = 0; j < cantCols; j++) {
                        Cell celda = fila.getCell(j + colInicio);
                        if (celda != null) {
                            String valor = "";
                            if (celda.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

                                Boolean bool = celda.getBooleanCellValue();
                                if (bool) {
                                    valor = "true";
                                } else {
                                    valor = "false";
                                }
                            }
                            if (celda.getCellType() == Cell.CELL_TYPE_NUMERIC) {

                                Double val = celda.getNumericCellValue();
                                valor = val.toString();

                            }
                            if (celda.getCellType() == Cell.CELL_TYPE_STRING) {
                                valor = celda.getStringCellValue();
                            }
                            if (celda.getCellType() == Cell.CELL_TYPE_FORMULA) {
                                try {
                                    Double val = celda.getNumericCellValue();
                                    valor = val.toString();
                                } catch (Exception ex) {
                                    valor = celda.getStringCellValue();
                                }

                            }

                            matriz[i][j] = valor;
                        }

                    }
                }


            }

            /*while(rows.hasNext()){
             Row fila = (Row)rows.next();
             Iterator cells = fila.cellIterator();
                
             while(cells.hasNext()){
             Cell celda = (Cell)cells.next();
             matriz[i][j]=celda.getStringCellValue();
             j++;
             }
             i++;
             }
             */

        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(ExcelControl.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        } finally{
            if (npoifs != null) { npoifs.close(); }
            if (pkg != null) { pkg.close(); }
        
        }

        return matriz;

    }
}
