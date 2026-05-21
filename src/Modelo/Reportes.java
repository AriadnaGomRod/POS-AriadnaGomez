package Modelo;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
    public void exportarReporteEmpleado(JTable tabla, String empleado, String fecha, String turno, String total) {

    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Guardar reporte empleado");
    chooser.setSelectedFile(new File("Reporte_Turno_Empleado.xls"));

    if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
        return;
    }

    File archivo = chooser.getSelectedFile();

    if (!archivo.getName().toLowerCase().endsWith(".xls")) {
        archivo = new File(archivo.getAbsolutePath() + ".xls");
    }
    
    try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
            new FileOutputStream(archivo), "UTF-8"))) {

        pw.println("<html><head><meta charset='UTF-8'>");
        pw.println("<style>");
        pw.println("body{font-family:Calibri,Arial;}");
        pw.println("h1{background:#ff9fbd;text-align:center;padding:12px;}");
        pw.println(".info{background:#ccffcc;padding:8px;margin-bottom:15px;font-weight:bold;}");
        pw.println("table{border-collapse:collapse;width:100%;}");
        pw.println("th{background:#4f81bd;color:white;border:1px solid #000;padding:6px;}");
        pw.println("td{border:1px solid #999;padding:5px;}");
        pw.println("</style></head><body>");

        pw.println("<h1>REPORTE DE TURNO DEL EMPLEADO</h1>");

        pw.println("<div class='info'>");
        pw.println("Empleado: " + empleado + "<br>");
        pw.println("Fecha: " + fecha + "<br>");
        pw.println("Turno: " + turno + "<br>");
        pw.println("Total vendido: " + total);
        pw.println("</div>");

        pw.println("<table>");
        pw.println("<tr>");
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            pw.println("<th>" + tabla.getColumnName(i) + "</th>");
        }
        pw.println("</tr>");

        for (int fila = 0; fila < tabla.getRowCount(); fila++) {
            pw.println("<tr>");
            for (int col = 0; col < tabla.getColumnCount(); col++) {
                Object valor = tabla.getValueAt(fila, col);
                pw.println("<td>" + (valor != null ? valor.toString() : "") + "</td>");
            }
            pw.println("</tr>");
        }

        pw.println("</table>");
        pw.println("</body></html>");

        JOptionPane.showMessageDialog(null, "Reporte exportado correctamente.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al exportar: " + e.getMessage());
    }
}
}
