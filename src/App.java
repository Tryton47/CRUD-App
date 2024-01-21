import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Catatan {
    private String judul;
    private String isi;

    public Catatan(String judul, String isi) {
        this.judul = judul;
        this.isi = isi;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }
}

class CatatanApp {
    private List<Catatan> catatanList = new ArrayList<>();

    public void tambahCatatan(String judul, String isi) {
        Catatan catatan = new Catatan(judul, isi);
        catatanList.add(catatan);

        // Menghentikan eksekusi tambahCatatan jika input tidak valid
        if (judul.isEmpty() || isi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Judul dan isi tidak boleh kosong mas bro !!!.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public List<Catatan> getCatatanList() {
        return catatanList;
    }

    public void hapusCatatan(int index) {
        if (index >= 0 && index < catatanList.size()) {
            catatanList.remove(index);
        }
    }
}

class CatatanGUI extends JFrame {
    private CatatanApp catatanApp;
    private DefaultListModel<String> listModel;
    private JTextField judulField;
    private JTextField isiArea;
    private JList<String> catatanList;
    private JLabel jumlahCatatanLabel;

    public CatatanGUI(CatatanApp catatanApp) {
        this.catatanApp = catatanApp;

        setTitle("Catatan Kita");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setApplicationIcon();  // Set ikon aplikasi
        initComponents();
    }

    private void setApplicationIcon() {
        try {
            // Mengambil gambar ikon dari file
            ImageIcon icon = new ImageIcon("icon.catatan.png");

            // Menambahkan ikon aplikasi
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // Tambahkan penanganan kesalahan sesuai kebutuhan
            e.printStackTrace();
        }
    }

    private void initComponents() {
        judulField = new JTextField();
        isiArea = new JTextField();
        catatanList = new JList<>();
        listModel = new DefaultListModel<>();

        // Set text and background color for "Tambah" button
        JButton tambahButton = new JButton("Tambah");
        tambahButton.setForeground(Color.white);
        tambahButton.setBackground(new Color(0, 204, 0));

        // Set text and background color for "Hapus" button
        JButton hapusButton = new JButton("Hapus"); 
        hapusButton.setForeground(Color.white);
        hapusButton.setBackground(new Color(255, 78, 78));


        // Set layout
        setLayout(new BorderLayout());

        // Panel input catatan
        JPanel inputPanel = new JPanel(new GridLayout(3,1,2,5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Mengubah ukuran font judul :
        JLabel labelJudul = new JLabel("  Judul :");
        labelJudul.setFont(new Font("Times New Roman", Font.BOLD, 18));
        labelJudul.setForeground(Color.WHITE);
        inputPanel.add(labelJudul);
        inputPanel.add(judulField);

        // Mengubah ukuran font isi :
        JLabel labelIsi = new JLabel("  Isi   :");
        labelIsi.setFont(new Font("Times New Roman", Font.BOLD, 18));
        labelIsi.setForeground(Color.WHITE);
        inputPanel.add(labelIsi);
        inputPanel.add(isiArea);

        inputPanel.setBackground(new Color(19,172,246));

        // Panel daftar catatan
        JPanel listPanel = new JPanel(new BorderLayout());

        jumlahCatatanLabel = new JLabel("   Catatan Terdaftar : 0");
        listPanel.add(jumlahCatatanLabel, BorderLayout.NORTH);
        //listPanel.add(new JLabel("  Catatan Terdaftar :" + catatanApp.getCatatanList().size()), BorderLayout.NORTH);
        listPanel.add(new JScrollPane(catatanList), BorderLayout. CENTER);
        listPanel.setBackground(new Color(216,200,200));
        isiArea.setForeground(Color.black);
        isiArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT); 
        
        // Mengatur warna latar belakang dan teks deskripsiArea judul dan isi
        isiArea.setBackground(Color.white);
        isiArea.setForeground(Color.black);

        // Menambahkan panel input dan daftar catatan ke frame
        add(inputPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panelBawah.add(tambahButton);
        panelBawah.add(hapusButton);

        add(panelBawah, BorderLayout.SOUTH);

        // Menambahkan listener untuk tombol tambah
        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String judul = judulField.getText();
                String isi = isiArea.getText();
                catatanApp.tambahCatatan(judul, isi);
                refreshCatatanList();
                clearInputFields();
            }
        });

        // Menambahkan listener untuk tombol hapus
        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int selectedIndex = catatanList.getSelectedIndex();
                catatanApp.hapusCatatan(selectedIndex);
                refreshCatatanList();
            }
        });

        // Menambahkan listener untuk memperbarui isi daftar catatan
        catatanList.addListSelectionListener(e -> {
            int selectedIndex = catatanList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Catatan selectedCatatan = catatanApp.getCatatanList().get(selectedIndex);
                judulField.setText(selectedCatatan.getJudul());
                isiArea.setText(selectedCatatan.getIsi());
            }
        });
    }

    private void refreshCatatanList() 
    {
        listModel.clear();
        for (Catatan catatan : catatanApp.getCatatanList()) {
            listModel.addElement(catatan.getJudul());
        }
        catatanList.setModel(listModel);

        int jumlahCatatan = catatanApp.getCatatanList().size();
        jumlahCatatanLabel.setText("Jumlah Catatan: " + jumlahCatatan);
    }

    private void clearInputFields() {
        judulField.setText("");
        isiArea.setText("");
    }
}

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CatatanApp catatanApp = new CatatanApp();
                CatatanGUI catatanGUI = new CatatanGUI(catatanApp);
                catatanGUI.setVisible(true);
            }
        });
    }
}