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
import model.MataKuliah;

public class MataKuliahViewController implements Initializable {
    
    @FXML private TextField txtKode;
    @FXML private TextField txtNama;
    @FXML private Spinner<Integer> spnSks;
    @FXML private TableView<MataKuliah> tblMataKuliah;
    @FXML private TableColumn<MataKuliah, String> colKode;
    @FXML private TableColumn<MataKuliah, String> colNama;
    @FXML private TableColumn<MataKuliah, Integer> colSks;
    @FXML private Label lblStatus;
    
    private MataKuliahController matakuliahController;
    private ObservableList<MataKuliah> matakuliahList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        matakuliahController = new MataKuliahController();
        matakuliahList = FXCollections.observableArrayList();
        
        // Inisialisasi spinner SKS (1-6)
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6, 2);
        spnSks.setValueFactory(valueFactory);
        
        // Inisialisasi kolom tabel
        colKode.setCellValueFactory(new PropertyValueFactory<>("kodeMatakuliah"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaMatakuliah"));
        colSks.setCellValueFactory(new PropertyValueFactory<>("sks"));
        
        // Setup context menu
        setupContextMenu();
        
        // Memuat data mata kuliah
        loadDataMataKuliah();
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
        tblMataKuliah.setRowFactory(tv -> {
            TableRow<MataKuliah> row = new TableRow<>();
            
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    tblMataKuliah.getSelectionModel().select(row.getIndex());
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            
            return row;
        });
    }
    
    private void handleEdit() {
        MataKuliah selected = tblMataKuliah.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Load data to form
            txtKode.setText(selected.getKodeMatakuliah());
            txtNama.setText(selected.getNamaMatakuliah());
            spnSks.getValueFactory().setValue(selected.getSks());
            
            // Change button text to "Update"
            // You might want to add a separate update button or handle this differently
        }
    }
    
    private void handleDelete() {
        MataKuliah selected = tblMataKuliah.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Hapus");
            alert.setHeaderText(null);
            alert.setContentText("Apakah Anda yakin ingin menghapus mata kuliah " + 
                               selected.getNamaMatakuliah() + "?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // Delete from database
                if (matakuliahController.hapusMataKuliah(selected.getId())) {
                    showSuccess("Data berhasil dihapus");
                    loadDataMataKuliah();
                } else {
                    showError("Gagal menghapus data");
                }
            }
        }
    }
    
    @FXML
    private void handleSimpan() {
        String kode = txtKode.getText().trim();
        String nama = txtNama.getText().trim();
        int sks = spnSks.getValue();
        
        if (kode.isEmpty() || nama.isEmpty()) {
            showError("Kode dan nama mata kuliah tidak boleh kosong!");
            return;
        }
        
        MataKuliah matakuliah = new MataKuliah(kode, nama, sks);
        
        if (matakuliahController.tambahMataKuliah(matakuliah)) {
            showSuccess("Data mata kuliah berhasil disimpan!");
            clearForm();
            loadDataMataKuliah();
        } else {
            showError("Gagal menyimpan data mata kuliah!");
        }
    }
    
    private void loadDataMataKuliah() {
        matakuliahList.clear();
        matakuliahList.addAll(matakuliahController.getAllMataKuliah());
        tblMataKuliah.setItems(matakuliahList);
    }
    
    private void clearForm() {
        txtKode.clear();
        txtNama.clear();
        spnSks.getValueFactory().setValue(2);
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
    }
}
