package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.application.Platform;
import java.util.Optional;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Pengguna;

public class PenggunaViewController implements Initializable {
    
    private PenggunaController penggunaController;
    private ObservableList<Pengguna> penggunaList;
    @FXML private TextField txtNama;
    @FXML private TextField txtNim;
    @FXML private TextField txtNoHp;
    @FXML private TextField txtEmail;
    @FXML private RadioButton rbLaki;
    @FXML private RadioButton rbPerempuan;
    @FXML private TableView<Pengguna> tblPengguna;
    @FXML private TableColumn<Pengguna, String> colNama;
    @FXML private TableColumn<Pengguna, String> colNim;
    @FXML private TableColumn<Pengguna, String> colNoHp;
    @FXML private TableColumn<Pengguna, String> colEmail;
    @FXML private TableColumn<Pengguna, String> colJenisKelamin;
    @FXML private Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("PenggunaViewController diinisialisasi");
        
        penggunaController = new PenggunaController();
        penggunaList = FXCollections.observableArrayList();
        
        // Inisialisasi kolom tabel
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaLengkap"));
        colNim.setCellValueFactory(new PropertyValueFactory<>("nim"));
        colNoHp.setCellValueFactory(new PropertyValueFactory<>("noHp"));
        
        // Inisialisasi RadioButton
        ToggleGroup genderGroup = new ToggleGroup();
        rbLaki.setToggleGroup(genderGroup);
        rbPerempuan.setToggleGroup(genderGroup);
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colJenisKelamin.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        
        // Setup context menu
        setupContextMenu();
        
        // Load data dari database
        loadDataPengguna();
    }
    
    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Hapus");
        
        // Set styles
        editItem.setStyle("-fx-text-fill: #2c3e50; -fx-padding: 5 15 5 10;");
        deleteItem.setStyle("-fx-text-fill: #e74c3c; -fx-padding: 5 15 5 10;");
        
        // Add icons
        editItem.setGraphic(new Label("✏️"));
        deleteItem.setGraphic(new Label("🗑️"));
        
        // Add actions
        editItem.setOnAction(e -> handleEdit());
        deleteItem.setOnAction(e -> handleDelete());
        
        contextMenu.getItems().addAll(editItem, deleteItem);
        
        // Set context menu on table row
        tblPengguna.setRowFactory(tv -> {
            TableRow<Pengguna> row = new TableRow<>();
            
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    tblPengguna.getSelectionModel().select(row.getIndex());
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            
            return row;
        });
    }
    
    private void handleEdit() {
        Pengguna selected = tblPengguna.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Load data to form
            txtNama.setText(selected.getNamaLengkap());
            txtNim.setText(selected.getNim());
            txtNoHp.setText(selected.getNoHp());
            txtEmail.setText(selected.getEmail());
            
            if (selected.getJenisKelamin().equalsIgnoreCase("Laki-laki")) {
                rbLaki.setSelected(true);
            } else {
                rbPerempuan.setSelected(true);
            }
        }
    }
    
    private void handleDelete() {
        Pengguna selected = tblPengguna.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Hapus");
            alert.setHeaderText(null);
            alert.setContentText("Apakah Anda yakin ingin menghapus pengguna " + 
                               selected.getNamaLengkap() + "?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // Delete from database
                if (penggunaController.hapusPengguna(selected.getId())) {
                    showSuccess("Data berhasil dihapus");
                    loadDataPengguna();
                } else {
                    showError("Gagal menghapus data");
                }
            }
        }
    }

    @FXML
    private void handleSimpan() {
        try {
            // Validasi input
            if (txtNama.getText().isEmpty() || txtNim.getText().isEmpty()) {
                lblStatus.setText("Nama dan NIM harus diisi!");
                return;
            }
            
            // Dapatkan nilai dari form
            String nama = txtNama.getText().trim();
            String nim = txtNim.getText().trim();
            String noHp = txtNoHp.getText().trim();
            String email = txtEmail.getText().trim();
            String jenisKelamin = rbLaki.isSelected() ? 
                               rbLaki.getUserData().toString() : 
                               rbPerempuan.getUserData().toString();
            
            // Buat objek Pengguna baru
            Pengguna pengguna = new Pengguna(nama, nim, noHp, email, jenisKelamin);
            
            // Simpan ke database
            if (penggunaController.tambahPengguna(pengguna)) {
                showSuccess("Data berhasil disimpan: " + nama);
                clearForm();
                loadDataPengguna();
            } else {
                showError("Gagal menyimpan data ke database");
            }
            
        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void clearForm() {
        txtNama.clear();
        txtNim.clear();
        txtNoHp.clear();
        txtEmail.clear();
        rbLaki.setSelected(true);
    }
    
    // Method untuk memuat data dari database
    private void loadDataPengguna() {
        try {
            penggunaList.clear();
            penggunaList.addAll(penggunaController.getAllPengguna());
            tblPengguna.setItems(penggunaList);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Gagal memuat data: " + e.getMessage());
        }
    }
    
    private void showError(String message) {
        lblStatus.setStyle("-fx-text-fill: red;");
        lblStatus.setText(message);
        
        // Auto-hide after 3 seconds
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> lblStatus.setText(""));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private void showSuccess(String message) {
        lblStatus.setStyle("-fx-text-fill: green;");
        lblStatus.setText(message);
        
        // Auto-hide after 3 seconds
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> lblStatus.setText(""));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}