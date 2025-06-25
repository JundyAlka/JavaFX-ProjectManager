package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Simple validation
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username dan password tidak boleh kosong!");
            return;
        }

        // Here you would typically validate credentials against a database
        // For now, we'll just accept any non-empty username/password
        try {
            // Close the login window
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();

            // Load the dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
            Parent root = loader.load();
            
            // Set the username in the dashboard
            DashboardController dashboardController = loader.getController();
            dashboardController.setUsername(username);
            
            Stage dashboardStage = new Stage();
            dashboardStage.setScene(new Scene(root, 1000, 600));
            dashboardStage.setTitle("Anomali Sistem - Dashboard");
            dashboardStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Gagal memuat dashboard: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegister() {
        try {
            // Close the login window
            Stage stage = (Stage) usernameField.getScene().getWindow();
            
            // Load the register view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterView.fxml"));
            Parent root = loader.load();
            
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(root, 800, 500));
            registerStage.setTitle("Sistem Akademik Terpadu - Pendaftaran");
            registerStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Gagal membuka halaman pendaftaran: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleBackToLogin() {
        try {
            // Close the current window (register window)
            Stage stage = (Stage) (usernameField != null ? usernameField.getScene().getWindow() : null);
            if (stage != null) {
                stage.close();
            }
            
            // Show the login window again
            Stage loginStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
            loginStage.setScene(new Scene(root, 800, 600));
            loginStage.setTitle("Sistem Akademik Terpadu - Login");
            loginStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Gagal kembali ke halaman login: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleRegisterSubmit() {
        try {
            // In a real application, you would validate and save the registration data here
            showAlert("Pendaftaran Berhasil", "Akun Anda telah berhasil dibuat. Silakan login dengan akun Anda.");
            
            // After successful registration, go back to login
            handleBackToLogin();
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Gagal melakukan pendaftaran: " + e.getMessage());
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
