// Hapus atau perbaiki baris package ini
// package your.package.name;  // Hapus atau ganti dengan package yang sesuai

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load login screen first
            URL fxmlUrl = getClass().getClassLoader().getResource("view/LoginView.fxml");
            if (fxmlUrl == null) {
                throw new RuntimeException("Tidak dapat menemukan file LoginView.fxml");
            }
            
            System.out.println("Loading FXML from: " + fxmlUrl);
            
            Parent root = FXMLLoader.load(fxmlUrl);
            
            // Set application title
            primaryStage.setTitle("Sistem Akademik Terpadu - Login");
            
            // Set scene with default size
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("Error saat memuat FXML:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        // Memastikan driver MySQL terdaftar
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver berhasil dimuat");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver tidak ditemukan!");
            e.printStackTrace();
            return;
        }
        
        // Menjalankan aplikasi JavaFX
        launch(args);
    }
}