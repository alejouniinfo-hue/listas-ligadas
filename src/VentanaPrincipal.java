import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

    private ListaLigada lista = new ListaLigada();
    private DefaultListModel<String> modelo = new DefaultListModel<>();
    private JList<String> jList = new JList<>(modelo);

    private JComboBox<Nota> comboNota = new JComboBox<>(Nota.values());
    private JComboBox<Figura> comboFigura = new JComboBox<>(Figura.values());
    private JTextField txtOctava = new JTextField();

    public VentanaPrincipal() {
        JOptionPane.showMessageDialog(this,
    "🎵 BIENVENIDO AL EDITOR DE MELODÍAS BY ALEJANDRO DELGADO\n\n" +

    "Para que una nota suene es necesario agregar los 3 siguientes componentes :\n" +
    "- Nota\n- Figura\n- Octava válida\n\n" +

    "🔹 FUNCIONES:\n" +
    "Agregar: añade una nota\n" +
    "Eliminar: borra la nota seleccionada\n" +
    "Modificar: cambia la nota seleccionada\n" +
    "Guardar: guarda en archivo JSON\n" +
    "Cargar: carga desde JSON\n" +
    "Reproducir: reproduce la melodía\n" +
    "Cargar Cucaracha: ejemplo de melodía\n\n" +

    "Disfruta creando música 🎶",

    "Bienvenido",
    JOptionPane.INFORMATION_MESSAGE
);

        setTitle("Editor de Melodías");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔹 PANEL SUPERIOR
        JPanel panelTop = new JPanel(new GridLayout(3, 2));

        panelTop.add(new JLabel("Nota:"));
        panelTop.add(comboNota);

        panelTop.add(new JLabel("Figura:"));
        panelTop.add(comboFigura);

        panelTop.add(new JLabel("Octava:"));
        panelTop.add(txtOctava);

        add(panelTop, BorderLayout.NORTH);

        // 🔹 LISTA
        add(new JScrollPane(jList), BorderLayout.CENTER);

        // 🔹 BOTONES
        JPanel panelBotones = new JPanel(new GridLayout(2, 4));

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCargar = new JButton("Cargar");
        JButton btnReproducir = new JButton("Reproducir");
        JButton btnCucaracha = new JButton("Cargar Cucaracha");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCargar);
        panelBotones.add(btnReproducir);
        panelBotones.add(btnCucaracha);

        add(panelBotones, BorderLayout.SOUTH);

      
        //  EVENTOS
 

        // ✔ AGREGAR
        btnAgregar.addActionListener(e -> {
        try {

        //  validar campo vacío o placeholder
            if (txtOctava.getText().isEmpty() ||
            txtOctava.getText().equals("Ingrese la octava...")) {

                JOptionPane.showMessageDialog(this,
                "Debe ingresar una octava válida (1 - 8)");
                return;
        }

        int octava = Integer.parseInt(txtOctava.getText());

        //  VALIDACIÓN IMPORTANTE
        if (octava < 1 || octava > 8) {
            JOptionPane.showMessageDialog(this,
                "La octava debe estar entre 1 y 8");
            return;
        }

        Nota nota = (Nota) comboNota.getSelectedItem();
        Figura figura = (Figura) comboFigura.getSelectedItem();

        NotaMusical nueva = new NotaMusical(nota, figura, octava);

        lista.agregar(nueva);
        modelo.addElement(formatoTexto(nueva));

        } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
            "Ingrese solo números en la octava");
        }
    });

        // ✔ ELIMINAR
        btnEliminar.addActionListener(e -> {
            int index = jList.getSelectedIndex();

            if (index != -1) {
                lista.eliminar(index);
                modelo.remove(index);
            }
        });

        // ✔ MODIFICAR
        btnModificar.addActionListener(e -> {
            int index = jList.getSelectedIndex();

            if (index != -1) {
                try {
                    Nota nota = (Nota) comboNota.getSelectedItem();
                    Figura figura = (Figura) comboFigura.getSelectedItem();
                    int octava = Integer.parseInt(txtOctava.getText());

                    NotaMusical nueva = new NotaMusical(nota, figura, octava);

                    lista.eliminar(index);
                    lista.agregar(nueva);

                    modelo.set(index, formatoTexto(nueva));

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al modificar");
                }
            }
        });

        // ✔ GUARDAR JSON
        btnGuardar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                JsonUtil.guardar(lista.toArrayList(),
                        chooser.getSelectedFile().getAbsolutePath());
            }
        });

        // ✔ CARGAR JSON
        btnCargar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

                ArrayList<NotaMusical> datos =
                        JsonUtil.cargar(chooser.getSelectedFile().getAbsolutePath());

                lista.cargarDesdeArray(datos);

                modelo.clear();
                for (NotaMusical n : datos) {
                    modelo.addElement(formatoTexto(n));
                }
            }
        });

        // ✔ REPRODUCIR
        btnReproducir.addActionListener(e -> {
            if (lista.getCabeza() == null) {
                JOptionPane.showMessageDialog(this, "No hay notas");
                return;
            }

            new Thread(() -> {
                ReproductorAudioMIDI.reproducirLista(lista);
            }).start();
        });

        // ✔ CARGAR CUCARACHA
        btnCucaracha.addActionListener(e -> {

            lista = new ListaLigada();
            modelo.clear();

            NotaMusical[] cucaracha = {
                new NotaMusical(Nota.DO, Figura.NEGRA, 4),
                new NotaMusical(Nota.DO, Figura.NEGRA, 4),
                new NotaMusical(Nota.DO, Figura.NEGRA, 4),
                new NotaMusical(Nota.FA, Figura.NEGRA, 4),
                new NotaMusical(Nota.LA, Figura.NEGRA, 4),

                new NotaMusical(Nota.DO, Figura.NEGRA, 4),
                new NotaMusical(Nota.DO, Figura.NEGRA, 4),
                new NotaMusical(Nota.DO, Figura.NEGRA, 4),
                new NotaMusical(Nota.FA, Figura.NEGRA, 4),
                new NotaMusical(Nota.LA, Figura.NEGRA, 4),

                new NotaMusical(Nota.FA, Figura.NEGRA, 4),
                new NotaMusical(Nota.FA, Figura.NEGRA, 4),
                new NotaMusical(Nota.MI, Figura.NEGRA, 4),
                new NotaMusical(Nota.MI, Figura.NEGRA, 4),
                new NotaMusical(Nota.RE, Figura.NEGRA, 4),
                new NotaMusical(Nota.RE, Figura.NEGRA, 4),

                new NotaMusical(Nota.DO, Figura.BLANCA, 4)
            };

            for (NotaMusical n : cucaracha) {
                lista.agregar(n);
                modelo.addElement(formatoTexto(n));
            }
        });
    }

    private String formatoTexto(NotaMusical n) {
        return n.getNota() + " - " + n.getFigura() + " - Octava " + n.getOctava();
    }
    
}