package Interfaz;
/**
 * Panel encargado del proceso de ventas.
 */
import Dao.DetalleVentaDAO;
import Dao.ProductoDAO;
import Dao.VentaDAO;
import Modelo.DetalleVenta;
import Modelo.Producto;
import Modelo.Venta;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Modelo.Empleado;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author arigo
 */
public class PanelVentas extends javax.swing.JPanel {
Empleado usuarioActual;
ProductoDAO pDao = new ProductoDAO();
VentaDAO vDao = new VentaDAO();
DetalleVentaDAO dvDao = new DetalleVentaDAO();

DefaultTableModel modeloVentas;
double totalPagar = 0.00;
private boolean actualizandoCombo = false;
     /**
     * Constructor del panel.
     * Inicializa componentes y carga productos.
     */
  public PanelVentas(Empleado usuario) {
    initComponents();

    this.usuarioActual = usuario;
    modeloVentas = (DefaultTableModel) jTable1.getModel();
    modeloVentas.setRowCount(0);

    lblTotal.setText("0.00");

    llenarComboProductos();
    activarAutocompletadoProducto();
    activarBusquedaPorCodigo();
}
    /**
     * Llena el combo con productos disponibles.
     */
    private void activarAutocompletadoProducto() {
    JTextField editor = (JTextField) VentasCBProd.getEditor().getEditorComponent();

    editor.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {

            int tecla = e.getKeyCode();

            if (tecla == java.awt.event.KeyEvent.VK_ENTER) {
                seleccionarProductoDelCombo();
                return;
            }

            if (tecla == java.awt.event.KeyEvent.VK_DOWN ||
                tecla == java.awt.event.KeyEvent.VK_UP) {
                return;
            }

            String texto = editor.getText().trim();

            actualizandoCombo = true;
            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();

            if (!texto.isEmpty()) {
                for (Producto p : pDao.listar()) {
                    if (p.getNomProd().toLowerCase().contains(texto.toLowerCase())) {
                        modelo.addElement(p.getNomProd());
                    }
                }

                VentasCBProd.setModel(modelo);

                if (modelo.getSize() > 0) {
                    VentasCBProd.setSelectedIndex(0);
                    editor.setText(texto);
                    editor.setCaretPosition(texto.length());
                    VentasCBProd.showPopup();

                    Producto p = pDao.buscarPorNombre(modelo.getElementAt(0));
                    if (p != null) {
                        VentasCod.setText(p.getCodigoBarras());
                        VentasPrecio.setText(String.valueOf(p.getPrecio()));
                    }

                } else {
                    editor.setText(texto);
                    VentasCod.setText("");
                    VentasPrecio.setText("");
                    VentasCBProd.hidePopup();
                }

            } else {
                VentasCBProd.setModel(modelo);
                VentasCod.setText("");
                VentasPrecio.setText("");
                VentasCBProd.hidePopup();
            }

            actualizandoCombo = false;
        }
    });
}
    private void seleccionarProductoDelCombo() {
    if (VentasCBProd.getSelectedItem() == null) {
        if (VentasCBProd.getItemCount() == 1) {
            VentasCBProd.setSelectedIndex(0);
        } else {
            return;
        }
    }

    String nombre = VentasCBProd.getSelectedItem().toString().trim();

    Producto p = pDao.buscarPorNombre(nombre);

    if (p != null) {
        cargarProductoEnCampos(p);
        VentaCantidad.requestFocus();
    }
}
    private void cargarProductoEnCampos(Producto p) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        actualizandoCombo = true;

        VentasCBProd.setSelectedItem(p.getNomProd());

        if (!VentasCod.getText().trim().equals(p.getCodigoBarras())) {
            VentasCod.setText(p.getCodigoBarras());
        }

        VentasPrecio.setText(String.valueOf(p.getPrecio()));

        actualizandoCombo = false;
    });
}
    private void activarBusquedaPorCodigo() {
    VentasCod.getDocument().addDocumentListener(new DocumentListener() {
        public void insertUpdate(DocumentEvent e) {
            buscarProductoPorCodigo();
        }

        public void removeUpdate(DocumentEvent e) {
            buscarProductoPorCodigo();
        }

        public void changedUpdate(DocumentEvent e) {
            buscarProductoPorCodigo();
        }
    });
}
    private void buscarProductoPorCodigo() {
    if (actualizandoCombo) {
        return;
    }

    String codigo = VentasCod.getText().trim();

    if (codigo.isEmpty()) {
        return;
    }

    Producto p = pDao.buscarPorCodigo(codigo);

    if (p != null) {
        cargarProductoEnCampos(p);
    }
}
    private void llenarComboProductos() {
    actualizandoCombo = true;

    VentasCBProd.removeAllItems();

    for (Producto p : pDao.listar()) {
        VentasCBProd.addItem(p.getNomProd());
    }

    VentasCBProd.setSelectedItem("");
    actualizandoCombo = false;
}
    private void calcularTotal() {
    totalPagar = 0.00;

    for (int i = 0; i < jTable1.getRowCount(); i++) {
        Object valor = jTable1.getValueAt(i, 4);

        if (valor != null && !valor.toString().isEmpty()) {
            totalPagar += Double.parseDouble(valor.toString());
        }
    }

    lblTotal.setText(String.format("%.2f", totalPagar));
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        VentasBTAgregarPro = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        VentasCod = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        VentasCBProd = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        VentaCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        VentasPrecio = new javax.swing.JTextField();
        VentasBTEliminarProd = new javax.swing.JButton();
        VentasBTCanVenta = new javax.swing.JButton();
        VentasBTFinVenta = new javax.swing.JButton();
        VentaBTTicket = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(105, 229, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 231));

        VentasBTAgregarPro.setBackground(new java.awt.Color(197, 255, 135));
        VentasBTAgregarPro.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        VentasBTAgregarPro.setText("Agregar Producto");
        VentasBTAgregarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentasBTAgregarProActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(105, 229, 255));

        jLabel1.setBackground(new java.awt.Color(102, 204, 255));
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setText("VENTAS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Producto", "Cantidad", "Precio Un", "Subtotal"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setText("Código:");

        VentasCod.setBackground(new java.awt.Color(218, 245, 245));
        VentasCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentasCodActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setText("Producto:");

        VentasCBProd.setBackground(new java.awt.Color(218, 245, 245));
        VentasCBProd.setEditable(true);
        VentasCBProd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        VentasCBProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentasCBProdActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setText("Cantidad:");

        VentaCantidad.setBackground(new java.awt.Color(218, 245, 245));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel5.setText("Precio:");

        VentasPrecio.setBackground(new java.awt.Color(218, 245, 245));

        VentasBTEliminarProd.setBackground(new java.awt.Color(255, 102, 102));
        VentasBTEliminarProd.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        VentasBTEliminarProd.setText("Eliminar Producto");
        VentasBTEliminarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentasBTEliminarProdActionPerformed(evt);
            }
        });

        VentasBTCanVenta.setBackground(new java.awt.Color(255, 102, 102));
        VentasBTCanVenta.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        VentasBTCanVenta.setText("Cancelar Venta");
        VentasBTCanVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentasBTCanVentaActionPerformed(evt);
            }
        });

        VentasBTFinVenta.setBackground(new java.awt.Color(105, 229, 255));
        VentasBTFinVenta.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        VentasBTFinVenta.setText("Finalizar Venta");
        VentasBTFinVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentasBTFinVentaActionPerformed(evt);
            }
        });

        VentaBTTicket.setBackground(new java.awt.Color(255, 238, 111));
        VentaBTTicket.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        VentaBTTicket.setText("Generar ticket");
        VentaBTTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentaBTTicketActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 231));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 231));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        lblTotal.setText("jLabel6");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel6.setText("Total:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(VentasBTCanVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(VentasBTFinVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(61, 61, 61)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(VentaBTTicket)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(VentasCod, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(VentasCBProd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(VentaCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(VentasPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(VentasBTAgregarPro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(VentasBTEliminarProd))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
                .addGap(23, 23, 23))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(VentasCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(VentasCBProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(VentaCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(VentasPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(VentasBTAgregarPro)
                            .addComponent(VentasBTEliminarProd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(VentasBTCanVenta)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(VentaBTTicket)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(VentasBTFinVenta)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents
     /**
     * Agrega producto a la tabla de venta.
     */
    private void VentasCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentasCodActionPerformed
 
    }//GEN-LAST:event_VentasCodActionPerformed

    private void VentasBTAgregarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentasBTAgregarProActionPerformed
    if (VentasCBProd.getSelectedItem() == null || VentasCBProd.getSelectedItem().toString().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Selecciona un producto");
        return;
    }
    if (VentaCantidad.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Ingresa la cantidad");
        return;
    }
    int cant;
    try {
        cant = Integer.parseInt(VentaCantidad.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero");
        return;
    }
    if (cant <= 0) {
        JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a cero");
        return;
    }
    String nombre = VentasCBProd.getSelectedItem().toString().trim();
    Producto p = pDao.buscarPorNombre(nombre);
    if (p == null) {
        JOptionPane.showMessageDialog(null, "Producto no encontrado");
        return;
    }
    int cantidadYaAgregada = obtenerCantidadEnTabla(p.getCodigoBarras());
    if ((cantidadYaAgregada + cant) > p.getStock()) {
        JOptionPane.showMessageDialog(null, 
            "Stock insuficiente. Disponible: " + (p.getStock() - cantidadYaAgregada));
        return;
    }
    double precio = (cant >= 10) ? p.getPrecioMayoreo() : p.getPrecio();
    double subtotal = cant * precio;

    Object[] fila = new Object[5];
    fila[0] = p.getCodigoBarras();
    fila[1] = p.getNomProd();
    fila[2] = cant;
    fila[3] = precio;
    fila[4] = subtotal;

    modeloVentas.insertRow(0, fila);
    calcularTotal();
    VentaCantidad.setText("");
    VentasCod.setText("");
    VentasPrecio.setText("");
    VentasCBProd.setSelectedItem("");
    VentasCBProd.requestFocus();
    }//GEN-LAST:event_VentasBTAgregarProActionPerformed
    private void VentasBTEliminarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentasBTEliminarProdActionPerformed
           // Borra fila seleccionada
        modeloVentas = (DefaultTableModel) jTable1.getModel();
    if (jTable1.getSelectedRow() != -1) {
        modeloVentas.removeRow(jTable1.getSelectedRow());
        calcularTotal();
    } else {
        JOptionPane.showMessageDialog(null, "Selecciona una fila de la tabla para eliminar");
    }
    }//GEN-LAST:event_VentasBTEliminarProdActionPerformed
    private void VentasBTCanVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentasBTCanVentaActionPerformed
    modeloVentas = (DefaultTableModel) jTable1.getModel();
    modeloVentas.setRowCount(0);
    VentaCantidad.setText("");
    VentasPrecio.setText("");
    VentasCBProd.setSelectedItem("");
    VentasCod.setText("");
    totalPagar = 0.00;
    lblTotal.setText("0.00"); 
    JOptionPane.showMessageDialog(null, "Venta cancelada");
    }//GEN-LAST:event_VentasBTCanVentaActionPerformed
    private void VentasBTFinVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentasBTFinVentaActionPerformed
    if (jTable1.getRowCount() > 0) {
        Venta v = new Venta();
        v.setTotal(totalPagar);
        v.setIdEmpleado(usuarioActual.getIdEmpleado());
        int idVenta = vDao.guardarVenta(v);
        
        if (idVenta != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String nombreProd = jTable1.getValueAt(i, 1).toString();
                int cant = Integer.parseInt(jTable1.getValueAt(i, 2).toString());
                double precio = Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                double subtotal = Double.parseDouble(jTable1.getValueAt(i, 4).toString());

                Producto p = pDao.buscarPorNombre(nombreProd);

        if (p == null) {
             JOptionPane.showMessageDialog(null, "Producto no encontrado: " + nombreProd);
                continue;
                }
                DetalleVenta dv = new DetalleVenta();
                dv.setIdVenta(idVenta);
                dv.setIdProducto(p.getIdProducto());
                dv.setCantidad(cant);
                dv.setPrecioUnitario(precio);
                dv.setSubtotal(subtotal);
                dvDao.registrarDetalle(dv);
                
                pDao.descontarStock(cant, p.getIdProducto());
            }
            JOptionPane.showMessageDialog(null, "Venta guardada en la base de datos. Ahora puede generar el ticket.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "La tabla está vacía.");
    }
        // Guarda venta, detalle y descuenta stock

    }//GEN-LAST:event_VentasBTFinVentaActionPerformed
    private void VentaBTTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentaBTTicketActionPerformed
        if (jTable1.getRowCount() > 0) {

        String ticketHTML = "<html><div style='width: 200px; font-family: monospace;'>" +
            "<h2 style='text-align: center; margin-bottom: 0;'>LA ESCUELITA</h2>" +
            "<p style='text-align: center; margin-top: 0; font-size: 8pt;'>Papelería y Regalos</p>" +
            "<hr style='border-top: 1px dashed black;'>" +
            "<table style='width: 100%; font-size: 9pt;'>" +
            "<tr><th style='text-align: left;'>Cant</th><th style='text-align: left;'>Prod</th><th style='text-align: right;'>Sub</th></tr>";

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String cant = jTable1.getValueAt(i, 2).toString();
            String prod = jTable1.getValueAt(i, 1).toString();
            String subt = jTable1.getValueAt(i, 4).toString();

            String prodCorto = (prod.length() > 15) ? prod.substring(0, 15) + "." : prod;
            
            ticketHTML += "<tr>" +
                "<td>" + cant + "</td>" +
                "<td>" + prodCorto + "</td>" +
                "<td style='text-align: right;'>$" + subt + "</td>" +
                "</tr>";
        }

        ticketHTML += "</table>" +
            "<hr style='border-top: 1px dashed black;'>" +
            "<p style='text-align: right; font-weight: bold;'>TOTAL: $" + totalPagar + "</p>" +
            "<p style='text-align: center; font-size: 8pt;'>¡Gracias por su compra!<br>Tlapa de Comonfort, Gro.</p>" +
            "</div></html>";

        JLabel lblTicket = new JLabel(ticketHTML);
        JOptionPane.showMessageDialog(null, lblTicket, "Ticket de Venta", JOptionPane.PLAIN_MESSAGE);
        limpiarVentaCompleta();
        
    } else {
        JOptionPane.showMessageDialog(null, "No hay productos para generar el ticket.");
    }
    }//GEN-LAST:event_VentaBTTicketActionPerformed
    private void VentasCBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentasCBProdActionPerformed
    if (actualizandoCombo || VentasCBProd.getSelectedItem() == null) {
        return;
    }
    String nombre = VentasCBProd.getSelectedItem().toString().trim();
    if (nombre.isEmpty()) {
        return;
    }
    Producto p = pDao.buscarPorNombre(nombre);
    if (p != null) {
        cargarProductoEnCampos(p);
    }
    }//GEN-LAST:event_VentasCBProdActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton VentaBTTicket;
    private javax.swing.JTextField VentaCantidad;
    private javax.swing.JButton VentasBTAgregarPro;
    private javax.swing.JButton VentasBTCanVenta;
    private javax.swing.JButton VentasBTEliminarProd;
    private javax.swing.JButton VentasBTFinVenta;
    private javax.swing.JComboBox<String> VentasCBProd;
    private javax.swing.JTextField VentasCod;
    private javax.swing.JTextField VentasPrecio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables
 private int obtenerCantidadEnTabla(String codigoBarras) {
    int cantidad = 0;

    for (int i = 0; i < jTable1.getRowCount(); i++) {
        Object codigoValor = jTable1.getValueAt(i, 0);
        Object cantValor = jTable1.getValueAt(i, 2);

        if (codigoValor != null && cantValor != null) {
            String codigoTabla = codigoValor.toString();

            if (codigoTabla.equals(codigoBarras)) {
                cantidad += Integer.parseInt(cantValor.toString());
            }
        }
    }

    return cantidad;
}
   private void limpiarVentaCompleta() {

    modeloVentas = (DefaultTableModel) jTable1.getModel();
    modeloVentas.setRowCount(0);

    VentaCantidad.setText("");
    VentasPrecio.setText("");
    VentasCod.setText("");

    VentasCBProd.setSelectedItem("");

    totalPagar = 0.00;
    lblTotal.setText("0.00");

    VentasCBProd.requestFocus();
}
}

