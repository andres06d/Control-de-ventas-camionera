
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultTreeCellEditor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author luisn
 */
public class Ventana extends javax.swing.JFrame {

    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel model2 = new DefaultTableModel();
    boolean activador[] = new boolean[10];

    public Ventana() {
        initComponents();
        for (int i = 0; i < activador.length; i++) {
            activador[i] = true;
        }
        Llenar_combo_consulta2("personas", "nombre", "cedula", socio_cedula);
        Llenar_combo_consulta2("personas", "nombre", "cedula", trabajador_cedula);
        Llenar_combo_consulta2("personas", "nombre", "cedula", cliente_cedula);
        Llenar_combo_consulta1("vehiculo", "placa", prestar_servicio_placa);
        Llenar_combo_consulta1("cliente", "idcliente", prestar_servicio_id_cliente);
        Llenar_combo_consulta1("trabajador", "idtrabajador", prestar_servicio_id_trabajador);
        Llenar_combo_consulta2("servicio", "nombre", "codigoser", prestar_servicio_codigo_servicio);
        Llenar_combo_consulta1("socio",  "idsocio",contribucion_id_socio );
        restricciones_inserccion();

    }

    public void restricciones_inserccion() {
        // text_tamaño_tipo( JTextField texto, int tamaño, boolean numero, boolean letras) 

        text_tamaño_tipo(registrar_persona_cedula, 10, true, false);
        text_tamaño_tipo(registrar_persona_nombre, 40, false, true);
        text_tamaño_tipo(registrar_persona_apellido, 40, false, true);
        text_tamaño_tipo(registrar_persona_telefono, 9, true, false);
        text_tamaño_tipo(socio_contribucion, 40, true, false);
        text_tamaño_tipo(socio_id, 10, true, false);
        text_tamaño_tipo(trabajador_sueldo, 40, true, false);
        text_tamaño_tipo(trabajador_id, 10, true, false);
        text_tamaño_tipo(servicio_precio, 40, true, false);
        text_tamaño_tipo(servicio_codigo, 10, true, false);
        text_tamaño_tipo(servicio_nombre, 19, false, true);
        text_tamaño_tipo(vehiculo_placa, 6, false, false);
        text_tamaño_tipo(vehiculo_tipo, 19, false, false);
        text_tamaño_tipo(vehiculo_marca, 19, false, false);
        text_tamaño_tipo(egresos_nombre, 19, false, true);
        text_tamaño_tipo(egresos_monto, 40, true, false);
        text_tamaño_tipo(egresos_numero, 10, true, false);
        text_tamaño_tipo(cliente_vehiculo, 19, false, false);
        text_tamaño_tipo(cliente_id_cliente, 10, true, false);
        text_tamaño_tipo(prestar_servicio_numero, 10, true, false);
        text_tamaño_tipo(contribucion_nombre, 19, false, true);
        text_tamaño_tipo(contribucion_monto, 40, true, false);
        text_tamaño_tipo(contribucion_numero, 11, true, false);

    }

    public void eliminar_columnas_filas(DefaultTableModel modelo) {
        while (modelo.getRowCount() >= 0 && ((modelo.getRowCount() - 1) >= 0)) {

            modelo.removeRow(modelo.getRowCount() - 1);

        }

    }

    public void eliminar_columnas_tabla(DefaultTableModel modelo) {

        for (int i = 0; i < modelo.getColumnCount(); i++) {
            modelo.setColumnCount(i);
        }
    }

    public void mensaje(String a) {
        JOptionPane.showMessageDialog(null, a);
    }

    public void limpiarTexfield(JTextField... textfiel) {
        for (int i = 0; i < textfiel.length; i++) {
            textfiel[i].setText("");
        }
    }

    public void LimpiarJComboBox(JComboBox<String>... combo) {
        for (int i = 0; i < combo.length; i++) {
            combo[i].setSelectedIndex(0);
        }

    }

    public void limpiarJCheckBox(JCheckBox... checkBox) {
        for (int i = 0; i < checkBox.length; i++) {
            checkBox[i].setSelected(false);
        }

    }

    public void Llenar_combo_consulta1(String tabla, String columna, JComboBox combo) {

        combo.removeAllItems();
        combo.addItem("Seleccionar");

        try {

            Connection cn = Conexion.conectar();

            PreparedStatement pst = cn.prepareStatement("select " + columna + " from " + tabla + " order by " + tabla + " . " + columna + " asc");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                combo.addItem(rs.getString(1));

            }
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al llenar tabla." + e);
            JOptionPane.showMessageDialog(null, "Error al mostrar información, ¡Contacte al administrador!");
        }

    }

    public void Llenar_combo_consulta2(String tabla, String columna, String columna_2, JComboBox combo) {

        combo.removeAllItems();
        combo.addItem("Seleccionar");

        try {

            Connection cn = Conexion.conectar();

            PreparedStatement pst = cn.prepareStatement("select " + columna + "," + columna_2 + " from " + tabla + " order by " + tabla + " . " + columna_2 + " asc");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                combo.addItem(rs.getString(1) + " : " + rs.getString(2));

            }
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al llenar tabla." + e);
            JOptionPane.showMessageDialog(null, "Error al mostrar información, ¡Contacte al administrador!");
        }

    }

    public int numerofilastabla(String tabla) {
        Connection cn = Conexion.conectar();

        PreparedStatement pst;

        int numero = 0;
        try {
            pst = cn.prepareStatement("SHOW COLUMNS FROM " + tabla + ";");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                //System.out.println(rs.getString(1));
                numero++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numero;
    }

    public void llenartabla(ResultSet resultado, DefaultTableModel modelo, JTable tabla, JScrollPane panel_scrol, String tabl) throws SQLException {
        eliminar_columnas_filas(modelo);
        eliminar_columnas_tabla(modelo);
        int nombre_columnas = numerofilastabla(tabl);
        String Vnombre_columnas[] = nombre_columnas(tabl);

        for (int i = 0; i < nombre_columnas; i++) {
            modelo.addColumn(Vnombre_columnas[i]);

        }

        tabla = new JTable(modelo);

        panel_scrol.setViewportView(tabla);

        while (resultado.next()) {
            Object[] fila = new Object[nombre_columnas];

            for (int i = 0; i < nombre_columnas; i++) {
                fila[i] = resultado.getObject(i + 1);

            }
            modelo.addRow(fila);
        }

    }

    public ResultSet consultar_tabla_completa(String tabla, Connection cn) throws SQLException {

        PreparedStatement pst = cn.prepareStatement("select* from " + tabla);

        ResultSet rs = pst.executeQuery();

        return rs;

    }

    public String[] nombre_columnas(String tabla) throws SQLException {
        int hola = numerofilastabla(tabla);
        String hl[] = new String[hola];
        int i = 0;
        Connection cn = Conexion.conectar();
        PreparedStatement pst = cn.prepareStatement("desc " + tabla);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            hl[i] = rs.getString(1).toUpperCase();
            System.out.println("Nombre de columna: " + hl[i]);
            i++;
        }
        return hl;
    }

    public boolean activador(JPanel panel) {
        System.out.println("___________________________________________");
        String hola = "";

        int componentes_totales = 0;
        int componentes_verdaderos = 0;

        for (Component component : panel.getComponents()) {

            hola = component.getClass().toString();

            //System.out.println(hola + " comparacion" + hola.equals("class javax.swing.JTextField"));
            if (hola.equals("class javax.swing.JTextField")) {
                // System.out.println("estado texfiel: " + VerificarText((JTextField) component));
                componentes_totales++;

                if (VerificarText((JTextField) component)) {
                    componentes_verdaderos++;

                }
            }
            if (hola.equals("class javax.swing.JComboBox")) {
                // System.out.println("estado comno: " + VerificarCombo((JComboBox<String>) component));
                componentes_totales++;

                if (VerificarCombo((JComboBox<String>) component)) {
                    componentes_verdaderos++;

                };

            }

        }
        boolean local = (componentes_verdaderos == componentes_totales);
        if (!local) {
            mensaje("Le hace falta por rellenar o seleccionar campos");

        }
        return local;
    }

    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);

            return true;
        } catch (NumberFormatException nfe) {

            return false;
        }
    }

    public boolean VerificarCombo(JComboBox<String> combo) {
        boolean uno = false;
        if (combo.getSelectedIndex() > 0) {
            uno = true;
        }
        System.out.println("combo es: " + uno);
        return uno;
    }

    public boolean VerificarText(JTextField text) {

        boolean estado = false;

        if (!(text.getText().trim().equals(""))) {
            estado = true;
        }
        System.out.println("es: " + estado);

        return estado;

    }

    public String OdtenerFecha(JComboBox<String> año, JComboBox<String> mes, JComboBox<String> dia) {

        return año.getSelectedItem().toString() + "-"
                + mes.getSelectedItem().toString() + "-"
                + dia.getSelectedItem().toString();
    }

    public void text_tamaño_tipo(JTextField texto, int tamaño, boolean numero, boolean letras) {

        texto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                int key = evt.getKeyChar();

                if (texto.getText().length() >= tamaño) {
                    evt.consume();
                }
                if (numero) {
                    boolean numeros = key >= 48 && key <= 57;
                    if (!numeros) {
                        evt.consume();
                    }
                }
                if (letras) {
                    boolean mayusculas = key >= 65 && key <= 90;
                    boolean minusculas = key >= 97 && key <= 122;
                    boolean espacio = key == 32;
                    boolean ñ = key == 241;

                    if (!(minusculas || mayusculas || espacio || ñ)) {
                        evt.consume();
                    }
                }
            }
        });

    }

    public void limpiar_panel(JPanel panel, boolean textfiel, boolean combobox) {
        String hola = "";

        for (Component component : panel.getComponents()) {

            hola = component.getClass().toString();

            if (hola.equals("class javax.swing.JTextField")) {
                if (VerificarText((JTextField) component)) {
                    limpiarTexfield((JTextField) component);
                }
            }
            if (hola.equals("class javax.swing.JComboBox")) {
                if (VerificarCombo((JComboBox<String>) component)) {
                    LimpiarJComboBox((JComboBox<String>) component);
                };

            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        registrar = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        Personas = new javax.swing.JPanel();
        registrar_persona_dia = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        registrar_persona_mes = new javax.swing.JComboBox<>();
        registrar_persona_año = new javax.swing.JComboBox<>();
        registrar_persona_nombre = new javax.swing.JTextField();
        registrar_persona_cedula = new javax.swing.JTextField();
        registrar_persona_apellido = new javax.swing.JTextField();
        registrar_persona_telefono = new javax.swing.JTextField();
        registrar_persona_boton = new javax.swing.JButton();
        Socio = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        socio_contribucion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        socio_id = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        socio_guardar = new javax.swing.JButton();
        socio_cedula = new javax.swing.JComboBox<>();
        Trabajador = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        trabajador_sueldo = new javax.swing.JTextField();
        trabajador_id = new javax.swing.JTextField();
        trabajador_guardar = new javax.swing.JButton();
        trabajador_cedula = new javax.swing.JComboBox<>();
        Servicio = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        servicio_precio = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        servicio_dia = new javax.swing.JComboBox<>();
        servicio_mes = new javax.swing.JComboBox<>();
        servicio_año = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        servicio_nombre = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        servicio_codigo = new javax.swing.JTextField();
        servicio_guardar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        servicio_descripcion = new javax.swing.JTextArea();
        Vehiculo = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        vehiculo_placa = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        vehiculo_tipo = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        vehiculo_marca = new javax.swing.JTextField();
        vehiculo_año = new javax.swing.JComboBox<>();
        vehiculo_guardar = new javax.swing.JButton();
        Egresos = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        egresos_nombre = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        egresos_numero = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        egresos_monto = new javax.swing.JTextField();
        egresos_dia = new javax.swing.JComboBox<>();
        egresos_mes = new javax.swing.JComboBox<>();
        egresos_año = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        egresos_guardar = new javax.swing.JButton();
        Cliente = new javax.swing.JPanel();
        cliente_vehiculo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cliente_id_cliente = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cliente_guardar = new javax.swing.JButton();
        cliente_cedula = new javax.swing.JComboBox<>();
        Visualizar = new javax.swing.JPanel();
        visualizar_seleccionar = new javax.swing.JComboBox<>();
        visualizar_buscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        visualizar_tabla = new javax.swing.JTable();
        Prestar_Servicio = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        prestar_servicio_dia = new javax.swing.JComboBox<>();
        prestar_servicio_mes = new javax.swing.JComboBox<>();
        prestar_servicio_año = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        prestar_servicio_numero = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        prestar_servicio_id_cliente = new javax.swing.JComboBox<>();
        prestar_servicio_placa = new javax.swing.JComboBox<>();
        prestar_servicio_id_trabajador = new javax.swing.JComboBox<>();
        prestar_servicio_codigo_servicio = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        Contribucion = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        contribucion_dia = new javax.swing.JComboBox<>();
        contribucion_mes = new javax.swing.JComboBox<>();
        contribucion_año = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        contribucion_numero = new javax.swing.JTextField();
        contribucion_monto = new javax.swing.JTextField();
        contribucion_nombre = new javax.swing.JTextField();
        contribucion_id_socio = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        registrar_persona_dia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Día", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        jLabel1.setText("Fecha de Nacimiento:");

        jLabel2.setText("Cédula:");

        jLabel3.setText("Nombre:");

        jLabel4.setText("Apellido:");

        jLabel5.setText("Teléfono:");

        registrar_persona_mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        registrar_persona_año.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Año", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", " " }));

        registrar_persona_cedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrar_persona_cedulaActionPerformed(evt);
            }
        });

        registrar_persona_boton.setText("Registrar");
        registrar_persona_boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrar_persona_botonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PersonasLayout = new javax.swing.GroupLayout(Personas);
        Personas.setLayout(PersonasLayout);
        PersonasLayout.setHorizontalGroup(
            PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonasLayout.createSequentialGroup()
                .addGroup(PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PersonasLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(registrar_persona_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(registrar_persona_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(registrar_persona_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PersonasLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(registrar_persona_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PersonasLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(registrar_persona_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PersonasLayout.createSequentialGroup()
                                .addGroup(PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(registrar_persona_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(registrar_persona_apellido, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(PersonasLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(registrar_persona_boton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PersonasLayout.setVerticalGroup(
            PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registrar_persona_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(registrar_persona_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registrar_persona_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(registrar_persona_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(registrar_persona_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(registrar_persona_apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(registrar_persona_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(registrar_persona_boton)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Personas", Personas);

        jLabel9.setText("Contribución:");

        socio_contribucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                socio_contribucionActionPerformed(evt);
            }
        });

        jLabel10.setText("Id del Socio:");

        socio_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                socio_idActionPerformed(evt);
            }
        });

        jLabel11.setText("Cédula:");

        socio_guardar.setText("Guardar");
        socio_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                socio_guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SocioLayout = new javax.swing.GroupLayout(Socio);
        Socio.setLayout(SocioLayout);
        SocioLayout.setHorizontalGroup(
            SocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SocioLayout.createSequentialGroup()
                .addGroup(SocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SocioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(SocioLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(socio_contribucion, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SocioLayout.createSequentialGroup()
                                .addGroup(SocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(SocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(socio_id, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(socio_cedula, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(SocioLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(socio_guardar)))
                .addContainerGap(386, Short.MAX_VALUE))
        );
        SocioLayout.setVerticalGroup(
            SocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SocioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(socio_contribucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(SocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(socio_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(SocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(socio_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(socio_guardar)
                .addContainerGap(191, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Socio", Socio);

        jLabel12.setText("Sueldo:");

        jLabel13.setText("Id del Trabajador:");

        jLabel14.setText("Cédula:");

        trabajador_guardar.setText("Guardar");
        trabajador_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trabajador_guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TrabajadorLayout = new javax.swing.GroupLayout(Trabajador);
        Trabajador.setLayout(TrabajadorLayout);
        TrabajadorLayout.setHorizontalGroup(
            TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TrabajadorLayout.createSequentialGroup()
                .addGroup(TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TrabajadorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(trabajador_sueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(trabajador_id, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(trabajador_cedula, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(TrabajadorLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(trabajador_guardar)))
                .addContainerGap(361, Short.MAX_VALUE))
        );
        TrabajadorLayout.setVerticalGroup(
            TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TrabajadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(trabajador_sueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(trabajador_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(trabajador_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(trabajador_guardar)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Trabajador", Trabajador);

        jLabel15.setText("Precio:");

        jLabel16.setText("Descripción:");

        jLabel17.setText("Fecha:");

        servicio_dia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Día", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        servicio_mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        servicio_año.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Año", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", " " }));

        jLabel18.setText("Codigo del Servicio:");

        jLabel19.setText("Nombre:");

        servicio_guardar.setText("Guardar");
        servicio_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servicio_guardarActionPerformed(evt);
            }
        });

        servicio_descripcion.setColumns(20);
        servicio_descripcion.setRows(5);
        servicio_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                servicio_descripcionKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(servicio_descripcion);

        javax.swing.GroupLayout ServicioLayout = new javax.swing.GroupLayout(Servicio);
        Servicio.setLayout(ServicioLayout);
        ServicioLayout.setHorizontalGroup(
            ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServicioLayout.createSequentialGroup()
                .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ServicioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ServicioLayout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addGap(18, 18, 18)
                                    .addComponent(servicio_nombre)
                                    .addGap(10, 10, 10))
                                .addGroup(ServicioLayout.createSequentialGroup()
                                    .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel15))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(ServicioLayout.createSequentialGroup()
                                            .addComponent(servicio_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(servicio_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(servicio_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(servicio_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(ServicioLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(servicio_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(ServicioLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(servicio_guardar)))
                .addContainerGap(300, Short.MAX_VALUE))
        );
        ServicioLayout.setVerticalGroup(
            ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(servicio_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(servicio_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(servicio_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(servicio_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(servicio_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(servicio_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(servicio_guardar)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Servicio", Servicio);

        jLabel20.setText("Placa:");

        jLabel21.setText("Tipo:");

        jLabel22.setText("Modelo:");

        jLabel23.setText("Marca:");

        vehiculo_año.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Año", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022" }));

        vehiculo_guardar.setText("Guardar");
        vehiculo_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vehiculo_guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout VehiculoLayout = new javax.swing.GroupLayout(Vehiculo);
        Vehiculo.setLayout(VehiculoLayout);
        VehiculoLayout.setHorizontalGroup(
            VehiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VehiculoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VehiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(VehiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(VehiculoLayout.createSequentialGroup()
                            .addComponent(jLabel20)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                            .addComponent(vehiculo_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(VehiculoLayout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(vehiculo_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(VehiculoLayout.createSequentialGroup()
                            .addComponent(jLabel22)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(vehiculo_año, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(2, 2, 2)))
                    .addGroup(VehiculoLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addGroup(VehiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(VehiculoLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(vehiculo_guardar))
                            .addComponent(vehiculo_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(425, Short.MAX_VALUE))
        );
        VehiculoLayout.setVerticalGroup(
            VehiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VehiculoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VehiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(vehiculo_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(VehiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(vehiculo_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(VehiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(vehiculo_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(VehiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(vehiculo_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vehiculo_guardar)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Vehiculo", Vehiculo);

        jLabel24.setText("Nombre:");

        jLabel25.setText("Monto:");

        jLabel26.setText("Número:");

        egresos_dia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Día", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        egresos_mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        egresos_año.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Año", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", " " }));
        egresos_año.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                egresos_añoActionPerformed(evt);
            }
        });

        jLabel27.setText("Fecha:");

        egresos_guardar.setText("Guardar");
        egresos_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                egresos_guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EgresosLayout = new javax.swing.GroupLayout(Egresos);
        Egresos.setLayout(EgresosLayout);
        EgresosLayout.setHorizontalGroup(
            EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EgresosLayout.createSequentialGroup()
                .addGroup(EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EgresosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EgresosLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(egresos_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EgresosLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(egresos_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EgresosLayout.createSequentialGroup()
                                .addGroup(EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel27))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(EgresosLayout.createSequentialGroup()
                                        .addComponent(egresos_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(egresos_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(egresos_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(egresos_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(EgresosLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(egresos_guardar)))
                .addContainerGap(318, Short.MAX_VALUE))
        );
        EgresosLayout.setVerticalGroup(
            EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EgresosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(egresos_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(egresos_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(egresos_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(EgresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(egresos_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(egresos_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(egresos_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addComponent(egresos_guardar)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Egresos", Egresos);

        jLabel6.setText("Vehículo:");

        jLabel7.setText("Id del Cliente:");

        jLabel8.setText("Cédula:");

        cliente_guardar.setText("Guardar");
        cliente_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cliente_guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ClienteLayout = new javax.swing.GroupLayout(Cliente);
        Cliente.setLayout(ClienteLayout);
        ClienteLayout.setHorizontalGroup(
            ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClienteLayout.createSequentialGroup()
                .addGroup(ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ClienteLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addComponent(cliente_vehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ClienteLayout.createSequentialGroup()
                                .addGroup(ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cliente_id_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(cliente_cedula, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(ClienteLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(cliente_guardar)))
                .addContainerGap(406, Short.MAX_VALUE))
        );
        ClienteLayout.setVerticalGroup(
            ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cliente_vehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cliente_id_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cliente_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cliente_guardar)
                .addContainerGap(198, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Cliente", Cliente);

        javax.swing.GroupLayout registrarLayout = new javax.swing.GroupLayout(registrar);
        registrar.setLayout(registrarLayout);
        registrarLayout.setHorizontalGroup(
            registrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        registrarLayout.setVerticalGroup(
            registrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        jTabbedPane1.addTab("Registrar", registrar);

        visualizar_seleccionar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Tabla", "Cliente", "Contribucion", "Egresos", "Personas", "Servicio", "Servicioprestado", "Socio", "Trabajador", "Vehiculo" }));

        visualizar_buscar.setText("Buscar");
        visualizar_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visualizar_buscarActionPerformed(evt);
            }
        });

        visualizar_tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(visualizar_tabla);

        javax.swing.GroupLayout VisualizarLayout = new javax.swing.GroupLayout(Visualizar);
        Visualizar.setLayout(VisualizarLayout);
        VisualizarLayout.setHorizontalGroup(
            VisualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VisualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VisualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                    .addGroup(VisualizarLayout.createSequentialGroup()
                        .addComponent(visualizar_seleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(visualizar_buscar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        VisualizarLayout.setVerticalGroup(
            VisualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VisualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VisualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(visualizar_seleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(visualizar_buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Visualizar", Visualizar);

        jLabel28.setText("Fecha:");

        prestar_servicio_dia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Día", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        prestar_servicio_mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        prestar_servicio_año.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Año", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", " " }));

        jLabel29.setText("Placa:");

        jLabel30.setText("Id del Cliente:");

        jLabel31.setText("Id del Trabajador:");

        jLabel32.setText("Codigo del Servicio:");

        jLabel33.setText("Número:");

        prestar_servicio_placa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prestar_servicio_placaActionPerformed(evt);
            }
        });

        jButton1.setText("Prestar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Prestar_ServicioLayout = new javax.swing.GroupLayout(Prestar_Servicio);
        Prestar_Servicio.setLayout(Prestar_ServicioLayout);
        Prestar_ServicioLayout.setHorizontalGroup(
            Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Prestar_ServicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Prestar_ServicioLayout.createSequentialGroup()
                        .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Prestar_ServicioLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(Prestar_ServicioLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(9, 9, 9)))
                        .addGap(71, 71, 71)
                        .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prestar_servicio_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Prestar_ServicioLayout.createSequentialGroup()
                                .addComponent(prestar_servicio_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(prestar_servicio_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(prestar_servicio_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Prestar_ServicioLayout.createSequentialGroup()
                            .addComponent(jLabel30)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(prestar_servicio_id_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(Prestar_ServicioLayout.createSequentialGroup()
                            .addComponent(jLabel31)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(prestar_servicio_id_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Prestar_ServicioLayout.createSequentialGroup()
                            .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel32)
                                .addComponent(jLabel33))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(prestar_servicio_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(prestar_servicio_codigo_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jButton1))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        Prestar_ServicioLayout.setVerticalGroup(
            Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Prestar_ServicioLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(prestar_servicio_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prestar_servicio_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prestar_servicio_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(prestar_servicio_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(prestar_servicio_id_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(prestar_servicio_id_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(prestar_servicio_codigo_servicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Prestar_ServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(prestar_servicio_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Prestar Servicio", Prestar_Servicio);

        jLabel34.setText("Fecha:");

        contribucion_dia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Día", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        contribucion_mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        contribucion_año.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Año", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", " " }));

        jLabel35.setText("Nombre:");

        jLabel36.setText("Monto:");

        jLabel37.setText("Id del Socio:");

        jLabel38.setText("Número:");

        jButton2.setText("Contribuir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ContribucionLayout = new javax.swing.GroupLayout(Contribucion);
        Contribucion.setLayout(ContribucionLayout);
        ContribucionLayout.setHorizontalGroup(
            ContribucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContribucionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContribucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContribucionLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contribucion_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(contribucion_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(contribucion_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ContribucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ContribucionLayout.createSequentialGroup()
                            .addComponent(jLabel35)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(contribucion_nombre))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ContribucionLayout.createSequentialGroup()
                            .addComponent(jLabel36)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(contribucion_monto))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ContribucionLayout.createSequentialGroup()
                            .addComponent(jLabel38)
                            .addGap(18, 18, 18)
                            .addComponent(contribucion_numero))
                        .addGroup(ContribucionLayout.createSequentialGroup()
                            .addComponent(jLabel37)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(contribucion_id_socio, 0, 120, Short.MAX_VALUE)))
                    .addComponent(jButton2))
                .addContainerGap(319, Short.MAX_VALUE))
        );
        ContribucionLayout.setVerticalGroup(
            ContribucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContribucionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContribucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(contribucion_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contribucion_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contribucion_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContribucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(contribucion_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ContribucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(contribucion_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ContribucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(contribucion_id_socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ContribucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(contribucion_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(163, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Contribución", Contribucion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registrar_persona_botonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrar_persona_botonActionPerformed

        if (activador(Personas)) {
            int filaafectada = 0;
            String cuatro = registrar_persona_apellido.getText();
            String dos = registrar_persona_cedula.getText();
            String uno = OdtenerFecha(registrar_persona_año, registrar_persona_mes, registrar_persona_dia);
            String tres = registrar_persona_nombre.getText();
            String cinco = registrar_persona_telefono.getText();
            Connection cn = Conexion.conectar();
            System.out.println("insert into personas values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "','" + cinco + "')");

            try {
                Statement insertar = cn.createStatement();
                filaafectada = insertar.executeUpdate("insert into personas values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "','" + cinco + "')");
                limpiar_panel(Personas, true, true);
            } catch (SQLException ex) {

                if (ex.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '" + dos + "' for key 'PRIMARY'")) {
                    mensaje("Cedula repetida por favor digite una cedula distinta");

                } else {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Error , ¡Contacte al administrador!");
                }

            }
        }

    }//GEN-LAST:event_registrar_persona_botonActionPerformed

    private void cliente_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cliente_guardarActionPerformed
        if (activador(Cliente)) {
            int filaafectada = 0;
            String uno = cliente_vehiculo.getText();
            String dos = cliente_id_cliente.getText();
            String tres = numero(cliente_cedula.getSelectedItem().toString());
            Connection cn = Conexion.conectar();
            System.out.println("insert into cliente values('" + uno + "', '" + dos + "', '" + tres + "')");

            try {

                Statement insertar = cn.createStatement();
                filaafectada = insertar.executeUpdate("insert into cliente values('" + uno + "', '" + dos + "', '" + tres + "')");
                limpiar_panel(Cliente, true, true);
            } catch (SQLException ex) {

                if (ex.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '" + dos + "' for key 'PRIMARY'")) {
                    mensaje("ID cliente repetido por favor digite una ID cliente distinto");

                } else {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Error , ¡Contacte al administrador!");
                }

            }
        }
    }//GEN-LAST:event_cliente_guardarActionPerformed

    private void socio_contribucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_socio_contribucionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_socio_contribucionActionPerformed

    private void socio_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_socio_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_socio_idActionPerformed

    private void registrar_persona_cedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrar_persona_cedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registrar_persona_cedulaActionPerformed

    private void egresos_añoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_egresos_añoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_egresos_añoActionPerformed
    public String numero(String cadena) {
        char[] cadena_div = cadena.toCharArray();
        String n = "";
        for (int i = 0; i < cadena_div.length; i++) {
            if (Character.isDigit(cadena_div[i])) {
                n += cadena_div[i];
            }
        }
        return n;
    }
    private void socio_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_socio_guardarActionPerformed
        if (activador(Socio)) {
            int filaafectada = 0;
            String uno = socio_contribucion.getText();
            String dos = socio_id.getText();
            String tres = numero(socio_cedula.getSelectedItem().toString());
            Connection cn = Conexion.conectar();
            System.out.println("insert into socio values('" + uno + "', '" + dos + "', '" + tres + "')");

            try {
                Statement insertar = cn.createStatement();
                filaafectada = insertar.executeUpdate("insert into socio values('" + uno + "', '" + dos + "', '" + tres + "')");
                limpiar_panel(Socio, true, true);
            } catch (SQLException ex) {

                if (ex.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '" + dos + "' for key 'PRIMARY'")) {
                    mensaje("ID socio repetido por favor digite una ID socio distinto");

                } else {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Error , ¡Contacte al administrador!");
                }

            }
        }    }//GEN-LAST:event_socio_guardarActionPerformed

    private void trabajador_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trabajador_guardarActionPerformed
        if (activador(Trabajador)) {
            int filaafectada = 0;
            String uno = trabajador_sueldo.getText();
            String dos = trabajador_id.getText();
            String tres = numero(trabajador_cedula.getSelectedItem().toString());
            Connection cn = Conexion.conectar();
            System.out.println("insert into socio values('" + uno + "', '" + dos + "', '" + tres + "')");

            try {
                Statement insertar = cn.createStatement();
                filaafectada = insertar.executeUpdate("insert into trabajador values('" + uno + "', '" + dos + "', '" + tres + "')");
                limpiar_panel(Trabajador, true, true);
            } catch (SQLException ex) {

                if (ex.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '" + dos + "' for key 'PRIMARY'")) {
                    mensaje("ID trabajador repetido por favor digite una ID trabajador distinto");

                } else {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Error , ¡Contacte al administrador!");
                }

            }
        }    }//GEN-LAST:event_trabajador_guardarActionPerformed

    private void servicio_descripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_servicio_descripcionKeyTyped
        int key = evt.getKeyChar();
        boolean mayusculas = key >= 65 && key <= 90;
        boolean minusculas = key >= 97 && key <= 122;
        boolean espacio = key == 32;
        boolean ñ = key == 241;

        if (!(minusculas || mayusculas || espacio || ñ)) {
            evt.consume();
        }    }//GEN-LAST:event_servicio_descripcionKeyTyped

    private void servicio_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servicio_guardarActionPerformed
        if (activador(Servicio)) {
            int filaafectada = 0;

            String uno = servicio_precio.getText();
            String dos = servicio_descripcion.getText();
            String tres = OdtenerFecha(servicio_año, servicio_mes, servicio_dia);
            String cuatro = servicio_codigo.getText();
            String cinco = servicio_nombre.getText();

            Connection cn = Conexion.conectar();
            System.out.println("insert into servicio values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "','" + cinco + "')");

            try {
                Statement insertar = cn.createStatement();
                filaafectada = insertar.executeUpdate("insert into servicio values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "','" + cinco + "')");
                limpiar_panel(Servicio, true, true);
                servicio_descripcion.setText("");
            } catch (SQLException ex) {

                if (ex.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '" + cuatro + "' for key 'PRIMARY'")) {
                    mensaje("Codigo de servicio repetida por favor digite un codigo de servicio distinto");

                } else {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Error , ¡Contacte al administrador!");
                }

            }
        }    }//GEN-LAST:event_servicio_guardarActionPerformed

    private void vehiculo_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vehiculo_guardarActionPerformed
        if (activador(Vehiculo)) {
            int filaafectada = 0;
            String uno = vehiculo_placa.getText();
            String dos = vehiculo_tipo.getText();
            String tres = vehiculo_año.getSelectedItem().toString();
            String cuatro = vehiculo_marca.getText();
            Connection cn = Conexion.conectar();
            System.out.println("insert into vehiculo values('" + uno + "', '" + dos + "', '" + tres + "', '" + cuatro + "')");

            try {
                Statement insertar = cn.createStatement();
                filaafectada = insertar.executeUpdate("insert into vehiculo values('" + uno + "', '" + dos + "', '" + tres + "', '" + cuatro + "')");
                limpiar_panel(Vehiculo, true, true);
            } catch (SQLException ex) {

                if (ex.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '" + uno + "' for key 'PRIMARY'")) {
                    mensaje("Placa repetido por favor digite una Placa distinta");

                } else {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Error , ¡Contacte al administrador!");
                }

            }
        }
    }//GEN-LAST:event_vehiculo_guardarActionPerformed

    private void egresos_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_egresos_guardarActionPerformed
        if (activador(Egresos)) {
            int filaafectada = 0;

            String uno = egresos_nombre.getText();
            String dos = egresos_monto.getText();
            String tres = egresos_numero.getText();
            String cuatro = OdtenerFecha(egresos_año, egresos_mes, egresos_dia);

            Connection cn = Conexion.conectar();
            System.out.println("insert into egresos values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "')");

            try {
                Statement insertar = cn.createStatement();
                filaafectada = insertar.executeUpdate("insert into egresos values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "')");
                limpiar_panel(Egresos, true, true);

            } catch (SQLException ex) {

                if (ex.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '" + tres + "' for key 'PRIMARY'")) {
                    mensaje("Numero repetido por favor digite un numero distinto");

                } else {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Error , ¡Contacte al administrador!");
                }

            }
        }
    }//GEN-LAST:event_egresos_guardarActionPerformed

    private void visualizar_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualizar_buscarActionPerformed

        if (activador(Visualizar)) {
            try {

                String seleccion = visualizar_seleccionar.getSelectedItem().toString().toLowerCase();
                int numero = visualizar_seleccionar.getSelectedIndex();
                ResultSet rs = consultar_tabla_completa(seleccion, Conexion.conectar());
                llenartabla(rs, model, visualizar_tabla, jScrollPane2, seleccion);
                Conexion.conectar().close();

            } catch (SQLException ex) {
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_visualizar_buscarActionPerformed

    private void prestar_servicio_placaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prestar_servicio_placaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prestar_servicio_placaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (activador(Prestar_Servicio)) {
            int filaafectada = 0;

            String uno = OdtenerFecha(prestar_servicio_año, prestar_servicio_mes, prestar_servicio_dia);
            String dos = prestar_servicio_placa.getSelectedItem().toString();
            String tres = prestar_servicio_id_cliente.getSelectedItem().toString();
            String cuatro = prestar_servicio_id_trabajador.getSelectedItem().toString();
            String cinco = numero(prestar_servicio_codigo_servicio.getSelectedItem().toString());
            String seis = prestar_servicio_numero.getText();

            Connection cn = Conexion.conectar();
            System.out.println("insert into servicioprestado values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "','" + cinco + "','" + seis + "')");

            try {
                Statement insertar = cn.createStatement();
                filaafectada = insertar.executeUpdate("insert into servicioprestado values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "','" + cinco + "','" + seis + "')");
                limpiar_panel(Prestar_Servicio, true, true);

            } catch (SQLException ex) {

                if (ex.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '" + seis + "' for key 'PRIMARY'")) {
                    mensaje("Codigo de servicio prestado repetido por favor digite un codigo de servicio prestado distinto");

                } else {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Error , ¡Contacte al administrador!");
                }

            }
        }     }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
   if (activador(Contribucion)) {
            int filaafectada = 0;
            String uno = OdtenerFecha(contribucion_año, contribucion_mes, contribucion_dia);
            String dos = contribucion_nombre.getText();
            String tres =contribucion_monto.getText();
            String cuatro= contribucion_id_socio.getSelectedItem().toString();
            String cinco = contribucion_numero.getText();
            Connection cn = Conexion.conectar();
            System.out.println("insert into contribucion values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "','" + cinco + "')");

            try {
                Statement insertar = cn.createStatement();
                filaafectada = insertar.executeUpdate("insert into contribucion values('" + uno + "', '" + dos + "', '" + tres + "','" + cuatro + "','" + cinco + "')");
                limpiar_panel(Personas, true, true);
            } catch (SQLException ex) {

                if (ex.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '" + cinco + "' for key 'PRIMARY'")) {
                    mensaje("Numero repetida por favor digite un numero distinto");

                } else {
                    System.err.println(ex);
                    JOptionPane.showMessageDialog(null, "Error , ¡Contacte al administrador!");
                }

            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cliente;
    private javax.swing.JPanel Contribucion;
    private javax.swing.JPanel Egresos;
    private javax.swing.JPanel Personas;
    private javax.swing.JPanel Prestar_Servicio;
    private javax.swing.JPanel Servicio;
    private javax.swing.JPanel Socio;
    private javax.swing.JPanel Trabajador;
    private javax.swing.JPanel Vehiculo;
    private javax.swing.JPanel Visualizar;
    private javax.swing.JComboBox<String> cliente_cedula;
    private javax.swing.JButton cliente_guardar;
    private javax.swing.JTextField cliente_id_cliente;
    private javax.swing.JTextField cliente_vehiculo;
    private javax.swing.JComboBox<String> contribucion_año;
    private javax.swing.JComboBox<String> contribucion_dia;
    private javax.swing.JComboBox<String> contribucion_id_socio;
    private javax.swing.JComboBox<String> contribucion_mes;
    private javax.swing.JTextField contribucion_monto;
    private javax.swing.JTextField contribucion_nombre;
    private javax.swing.JTextField contribucion_numero;
    private javax.swing.JComboBox<String> egresos_año;
    private javax.swing.JComboBox<String> egresos_dia;
    private javax.swing.JButton egresos_guardar;
    private javax.swing.JComboBox<String> egresos_mes;
    private javax.swing.JTextField egresos_monto;
    private javax.swing.JTextField egresos_nombre;
    private javax.swing.JTextField egresos_numero;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JComboBox<String> prestar_servicio_año;
    private javax.swing.JComboBox<String> prestar_servicio_codigo_servicio;
    private javax.swing.JComboBox<String> prestar_servicio_dia;
    private javax.swing.JComboBox<String> prestar_servicio_id_cliente;
    private javax.swing.JComboBox<String> prestar_servicio_id_trabajador;
    private javax.swing.JComboBox<String> prestar_servicio_mes;
    private javax.swing.JTextField prestar_servicio_numero;
    private javax.swing.JComboBox<String> prestar_servicio_placa;
    private javax.swing.JPanel registrar;
    private javax.swing.JTextField registrar_persona_apellido;
    private javax.swing.JComboBox<String> registrar_persona_año;
    private javax.swing.JButton registrar_persona_boton;
    private javax.swing.JTextField registrar_persona_cedula;
    private javax.swing.JComboBox<String> registrar_persona_dia;
    private javax.swing.JComboBox<String> registrar_persona_mes;
    private javax.swing.JTextField registrar_persona_nombre;
    private javax.swing.JTextField registrar_persona_telefono;
    private javax.swing.JComboBox<String> servicio_año;
    private javax.swing.JTextField servicio_codigo;
    private javax.swing.JTextArea servicio_descripcion;
    private javax.swing.JComboBox<String> servicio_dia;
    private javax.swing.JButton servicio_guardar;
    private javax.swing.JComboBox<String> servicio_mes;
    private javax.swing.JTextField servicio_nombre;
    private javax.swing.JTextField servicio_precio;
    private javax.swing.JComboBox<String> socio_cedula;
    private javax.swing.JTextField socio_contribucion;
    private javax.swing.JButton socio_guardar;
    private javax.swing.JTextField socio_id;
    private javax.swing.JComboBox<String> trabajador_cedula;
    private javax.swing.JButton trabajador_guardar;
    private javax.swing.JTextField trabajador_id;
    private javax.swing.JTextField trabajador_sueldo;
    private javax.swing.JComboBox<String> vehiculo_año;
    private javax.swing.JButton vehiculo_guardar;
    private javax.swing.JTextField vehiculo_marca;
    private javax.swing.JTextField vehiculo_placa;
    private javax.swing.JTextField vehiculo_tipo;
    private javax.swing.JButton visualizar_buscar;
    private javax.swing.JComboBox<String> visualizar_seleccionar;
    private javax.swing.JTable visualizar_tabla;
    // End of variables declaration//GEN-END:variables
}
