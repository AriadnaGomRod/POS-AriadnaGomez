package Modelo;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import javax.swing.JTable;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 * Clase encargada de generar reportes en archivos CSV.
 * Utiliza tablas JTable para obtener la información.
 */
public class Reportes {
// Método para exportar productos con stock bajo
    // Abre una ventana para elegir dónde guardar el archivo
    public void exportarStockBajo(JTable tabla) {
        // Selecciona ubicación, escribe encabezados y datos
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Guardar Reporte de Stock Bajo");
    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

        File archivo = new File(chooser.getSelectedFile().toString() + "_StockBajo.csv");
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {

            for (int i = 0; i < tabla.getColumnCount(); i++) {
                bw.write(tabla.getColumnName(i) + (i == tabla.getColumnCount() - 1 ? "" : ","));
            }
            bw.newLine();
            for (int i = 0; i < tabla.getRowCount(); i++) {
                for (int j = 0; j < tabla.getColumnCount(); j++) {
                    bw.write(String.valueOf(tabla.getValueAt(i, j)) + (j == tabla.getColumnCount() - 1 ? "" : ","));
                }
                bw.newLine();
            }
            JOptionPane.showMessageDialog(null, "¡Reporte de Stock Bajo generado con éxito!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al exportar productos: " + e.getMessage());
        }
    }
    }
      // Genera un reporte general con stock y ventas
    public void exportarReporteCompleto(JTable tablaStock, JTable tablaVentas) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Reporte General");
        
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File archivo = new File(chooser.getSelectedFile().toString() + "_Reporte_Completo.csv");
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                
                bw.write("REPORTE DE PRODUCTOS CON STOCK BAJO");
                bw.newLine();
                escribirTabla(bw, tablaStock);
                
                bw.newLine();
                bw.write("---------------------------------------");
                bw.newLine();
                
                bw.write("REPORTE DE VENTAS TOTALES");
                bw.newLine();
                escribirTabla(bw, tablaVentas);

                JOptionPane.showMessageDialog(null, "¡Reporte Completo generado con éxito!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
            }
        }
    }

    // Método auxiliar para escribir cualquier tabla
    private void escribirTabla(BufferedWriter bw, JTable tabla) throws Exception {

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            bw.write(tabla.getColumnName(i) + (i == tabla.getColumnCount() - 1 ? "" : ","));
        }
        bw.newLine();


        for (int i = 0; i < tabla.getRowCount(); i++) {
            for (int j = 0; j < tabla.getColumnCount(); j++) {
                Object valor = tabla.getValueAt(i, j);
                bw.write(String.valueOf(valor != null ? valor : "") + (j == tabla.getColumnCount() - 1 ? "" : ","));
            }
            bw.newLine();
        }
    }
       // Genera reporte individual por empleado
    public void exportarReporteEmpleado(JTable tabla, String empleado, String fecha, String turno, String totalVendido, String Ventas) {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Guardar Reporte del Empleado");
    
    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        File archivo = new File(chooser.getSelectedFile().toString() + "_ReporteEmpleado.csv");
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            // Escribir encabezado informativo del turno
            bw.write("REPORTE DE TURNO"); bw.newLine();
            bw.write("Empleado:," + empleado); bw.newLine();
            bw.write("Fecha:," + fecha); bw.newLine();
            bw.write("Turno:," + turno); bw.newLine();
            bw.write("Total Vendido en Turno:,$" + totalVendido); bw.newLine();
            bw.write("Ventas Totales:,$" + totalVendido); bw.newLine();
            bw.newLine();
            
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                bw.write(tabla.getColumnName(i) + (i == tabla.getColumnCount() - 1 ? "" : ","));
            }
            bw.newLine();


            for (int i = 0; i < tabla.getRowCount(); i++) {
                for (int j = 0; j < tabla.getColumnCount(); j++) {
                    bw.write(String.valueOf(tabla.getValueAt(i, j)) + (j == tabla.getColumnCount() - 1 ? "" : ","));
                }
                bw.newLine();
            }

            JOptionPane.showMessageDialog(null, "Reporte del empleado generado.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
}
