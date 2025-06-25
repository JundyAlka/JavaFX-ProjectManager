package controller;

import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    @FXML private StackPane contentArea;
    @FXML private Label lblUsername;
    
    private VBox createDashboardCard(String title, String value, String color, String iconText) {
        // UMPWR color scheme
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-padding: 15 10; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 3, 0, 0, 1);"
        );
        
        // Icon with color
        Label iconLabel = new Label(iconText);
        iconLabel.setStyle(
            "-fx-font-size: 24px; " +
            "-fx-text-fill: " + color + "; " +
            "-fx-padding: 0 0 5 0;"
        );
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle(
            "-fx-text-fill: #666666; " +
            "-fx-font-size: 11px; " +
            "-fx-font-weight: 600;"
        );
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        
        Label valueLabel = new Label(value);
        valueLabel.setStyle(
            "-fx-text-fill: " + color + "; " +
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-alignment: center;"
        );
        valueLabel.setMaxWidth(Double.MAX_VALUE);
        valueLabel.setAlignment(Pos.CENTER);
        
        // Add hover effect
        card.setOnMouseEntered(e -> card.setStyle(
            "-fx-background-color: #f8fafc; " +
            "-fx-padding: 18 10; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(26,95,122,0.15), 6, 0, 0, 3);"
        ));
        
        card.setOnMouseExited(e -> card.setStyle(
            "-fx-background-color: white; " +
            "-fx-padding: 15 10; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 3, 0, 0, 1);"
        ));
        
        VBox.setMargin(iconLabel, new javafx.geometry.Insets(0, 0, 3, 0));
        VBox.setMargin(titleLabel, new javafx.geometry.Insets(0, 5, 3, 5));
        VBox.setMargin(valueLabel, new javafx.geometry.Insets(0, 0, 0, 0));
        
        card.getChildren().addAll(iconLabel, titleLabel, valueLabel);
        return card;
    }
    
    public void setUsername(String username) {
        if (lblUsername != null) {
            lblUsername.setText(username);
            // Update the dashboard content when username is set
            loadBeranda();
        }
    }
    
    @FXML
    public void initialize() {
        // Set initial content to Beranda
        loadBeranda();
    }
    
    @FXML
    private void handleMenuBeranda() {
        loadBeranda();
    }
    
    private HBox createQuickLinkButton(String text, String color) {
        HBox button = new HBox();
        button.setAlignment(Pos.CENTER);
        button.setStyle(
            "-fx-background-color: " + color + "; " +
            "-fx-background-radius: 5; " +
            "-fx-padding: 8 15; " +
            "-fx-cursor: hand;"
        );
        
        Label icon = new Label("•");
        icon.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 0 5 0 0;");
        
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        button.getChildren().addAll(icon, label);
        
        // Hover effect
        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-background-color: " + color + "d9; " +
            "-fx-background-radius: 5; " +
            "-fx-padding: 8 15; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        ));
        
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-background-color: " + color + "; " +
            "-fx-background-radius: 5; " +
            "-fx-padding: 8 15; " +
            "-fx-cursor: hand;"
        ));
        
        return button;
    }
    
    private HBox createNewsItem(String title, String date, String excerpt) {
        HBox newsItem = new HBox(10);
        newsItem.setAlignment(Pos.CENTER_LEFT);
        newsItem.setStyle("-fx-padding: 15 0; -fx-border-color: #eee; -fx-border-width: 0 0 1 0;");
        
        // Calendar icon
        VBox dateBox = new VBox();
        dateBox.setStyle(
            "-fx-background-color: #f8f9fa; " +
            "-fx-padding: 8 10; " +
            "-fx-background-radius: 5; " +
            "-fx-alignment: center;"
        );
        
        Label dayLabel = new Label(date.split(" ")[0]);
        dayLabel.setStyle("-fx-text-fill: #1a5f7a; -fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label monthLabel = new Label(date.split(" ")[1]);
        monthLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");
        
        dateBox.getChildren().addAll(dayLabel, monthLabel);
        
        // News content
        VBox contentBox = new VBox(5);
        contentBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(contentBox, Priority.ALWAYS);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold; -fx-wrap-text: true;");
        
        Label excerptLabel = new Label(excerpt);
        excerptLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px; -fx-wrap-text: true;");
        
        contentBox.getChildren().addAll(titleLabel, excerptLabel);
        
        // Read more button
        Hyperlink readMore = new Hyperlink("Baca Selengkapnya");
        readMore.setStyle(
            "-fx-text-fill: #1a5f7a; " +
            "-fx-font-size: 12px; " +
            "-fx-padding: 0; " +
            "-fx-border-width: 0;"
        );
        
        // Add arrow icon
        Label arrow = new Label("→");
        arrow.setStyle("-fx-text-fill: #1a5f7a; -fx-font-size: 12px; -fx-padding: 0 0 0 5;");
        
        HBox readMoreBox = new HBox(readMore, arrow);
        readMoreBox.setAlignment(Pos.CENTER_LEFT);
        
        contentBox.getChildren().add(readMoreBox);
        
        newsItem.getChildren().addAll(dateBox, contentBox);
        return newsItem;
    }
    
    private void loadBeranda() {
        try {
            // Main container with shadow and rounded corners
            VBox mainContainer = new VBox(0);
            mainContainer.setStyle(
                "-fx-background-color: white; " +
                "-fx-background-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2); " +
                "-fx-max-width: 900;"
            );
            mainContainer.setMaxWidth(Region.USE_PREF_SIZE);
            mainContainer.setFillWidth(true);
            
            // University header with gradient background
            VBox headerBox = new VBox(8);
            headerBox.setAlignment(Pos.CENTER);
            headerBox.setStyle(
                "-fx-background-color: linear-gradient(to right, #1a5f7a, #2c8aad); " +
                "-fx-padding: 15 10; " +
                "-fx-background-radius: 8 8 0 0;"
            );
            headerBox.setMaxWidth(Double.MAX_VALUE);
            
            // Set max width/height to prevent horizontal scrolling
            mainContainer.setMaxWidth(Region.USE_PREF_SIZE);
            mainContainer.setMaxHeight(Region.USE_PREF_SIZE);
            mainContainer.setSpacing(0);
            
            // University logo and name
            HBox universityHeader = new HBox(20);
            universityHeader.setAlignment(Pos.CENTER);
            
            // University logo and name with better styling
            VBox logoBox = new VBox();
            logoBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.15); " +
                "-fx-min-width: 70; " +
                "-fx-min-height: 70; " +
                "-fx-background-radius: 10; " +
                "-fx-alignment: center;"
            );
            
            Label logoLabel = new Label("UMP");
            logoLabel.setStyle(
                "-fx-text-fill: white; " +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold;"
            );
            logoBox.getChildren().add(logoLabel);
            
            // University name and address with better typography
            VBox univInfo = new VBox(5);
            Label univName = new Label("UNIVERSITAS MUHAMMADIYAH PURWOREJO");
            univName.setStyle(
                "-fx-font-size: 20px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-text-alignment: center;"
            );
            
            Label univAddress = new Label("Jl. KH. A. Dahlan 3 & 6 Purworejo, Jawa Tengah 54111");
            univAddress.setStyle(
                "-fx-font-size: 13px; " +
                "-fx-text-fill: rgba(255,255,255,0.95); " +
                "-fx-font-weight: 500;"
            );
            
            univInfo.getChildren().addAll(univName, univAddress);
            universityHeader.getChildren().addAll(logoBox, univInfo);
            
            // Greeting section with user info
            VBox greetingBox = new VBox(6);
            greetingBox.setAlignment(Pos.CENTER);
            greetingBox.setStyle("-fx-padding: 15 0 20 0;");
            
            // Get current hour for time-based greeting
            int hour = java.time.LocalTime.now().getHour();
            String greeting = "Selamat ";
            greeting += hour < 12 ? "Pagi" : hour < 15 ? "Siang" : hour < 19 ? "Sore" : "Malam";
            
            Label greetingLabel = new Label(greeting);
            greetingLabel.setStyle(
                "-fx-font-size: 24px; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: 600;"
            );
            
            String displayUsername = lblUsername.getText();
            Label usernameLabel = new Label(displayUsername);
            usernameLabel.setStyle(
                "-fx-font-size: 18px; " +
                "-fx-text-fill: #e0f7ff; " +
                "-fx-font-weight: 600;"
            );
            
            Label welcomeLabel = new Label("SISTEM INFORMASI AKADEMIK");
            welcomeLabel.setStyle(
                "-fx-font-size: 13px; " +
                "-fx-text-fill: rgba(255,255,255,0.9); " +
                "-fx-font-weight: 500;"
            );
            
            greetingBox.getChildren().addAll(greetingLabel, usernameLabel, welcomeLabel);
            
            // Stats cards in a responsive row
            HBox cardsContainer = new HBox(12);
            cardsContainer.setAlignment(Pos.CENTER);
            cardsContainer.setStyle(
                "-fx-padding: 15 10 20 10; " +
                "-fx-alignment: center; " +
                "-fx-wrap-text: true;"
            );
            HBox.setHgrow(cardsContainer, Priority.ALWAYS);
            cardsContainer.setFillHeight(true);
            cardsContainer.setMaxWidth(Double.MAX_VALUE);
            cardsContainer.setAlignment(Pos.CENTER);
            
            // Create cards with improved styling
            VBox card1 = createDashboardCard("TOTAL MAHASISWA", "3,842", "#1a5f7a", "👥");
            VBox card2 = createDashboardCard("DOSEN & STAFF", "327", "#2c8aad", "👨‍🏫");
            VBox card3 = createDashboardCard("PROGRAM STUDI", "25", "#3ab4cc", "📚");
            VBox card4 = createDashboardCard("FAKULTAS", "7", "#4adde8", "🏛️");
            
            // Set consistent card size and style with better spacing
            for (VBox card : new VBox[]{card1, card2, card3, card4}) {
                card.setMinSize(150, 100);
                card.setMaxSize(150, 100);
                VBox.setVgrow(card, Priority.ALWAYS);
                card.setAlignment(Pos.CENTER);
            }
            
            cardsContainer.getChildren().addAll(card1, card2, card3, card4);
            
            // Footer with contact info
            HBox footer = new HBox();
            footer.setAlignment(Pos.CENTER);
            footer.setStyle(
                "-fx-padding: 15; " +
                "-fx-background-color: rgba(255,255,255,0.1); " +
                "-fx-background-radius: 0 0 10 10;"
            );
            
            Label footerLabel = new Label(
                "© " + LocalDate.now().getYear() + " Universitas Muhammadiyah Purworejo | " +
                "Telp: (0275) 321494 | Email: info@umpwr.ac.id"
            );
            footerLabel.setStyle(
                "-fx-text-fill: rgba(255,255,255,0.9); " +
                "-fx-font-size: 11px;"
            );
            footerLabel.setAlignment(Pos.CENTER);
            
            footer.getChildren().add(footerLabel);
            
            // Add all components to main container
            headerBox.getChildren().addAll(universityHeader, greetingBox);
            mainContainer.getChildren().addAll(headerBox, cardsContainer, footer);
            
            // Create a container with padding
            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);
            container.setStyle(
                "-fx-padding: 10; " +
                "-fx-background-color: #f5f7fa;"
            );
            container.getChildren().add(mainContainer);
            container.setFillWidth(true);
            container.setMaxWidth(Double.MAX_VALUE);
            
            // Set up scroll pane with proper styling
            ScrollPane scrollPane = new ScrollPane(container);
            scrollPane.setFitToWidth(true);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setStyle(
                "-fx-background: white; " +
                "-fx-background-color: transparent; " +
                "-fx-padding: 0; " +
                "-fx-hbar-policy: never; " +
                "-fx-vbar-policy: as-needed;"
            );
            
            // Clear and set the content
            contentArea.getChildren().clear();
            contentArea.getChildren().add(scrollPane);
            
            // Request layout
            Platform.runLater(() -> {
                scrollPane.requestLayout();
                container.requestLayout();
            });
        } catch (Exception e) {
            showAlert("Error", "Gagal memuat halaman beranda: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleMenuPengguna() {
        try {
            System.out.println("Mencoba memuat PenggunaView.fxml...");
            
            // Cek apakah file FXML ada
            URL url = getClass().getClassLoader().getResource("view/PenggunaView.fxml");
            if (url == null) {
                String errorMsg = "File PenggunaView.fxml tidak ditemukan.\n" +
                                "Pastikan file berada di: src/view/PenggunaView.fxml";
                System.err.println(errorMsg);
                showAlert("File Tidak Ditemukan", errorMsg);
                return;
            }
            
            System.out.println("URL FXML: " + url);
            
            // Coba memuat FXML
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            
            // Jika berhasil dimuat, tampilkan di content area
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
            System.out.println("PenggunaView.fxml berhasil dimuat");
            
        } catch (Exception e) {
            String errorMsg = "Terjadi kesalahan saat memuat halaman Data Pengguna.\n\n" +
                            "Detail Error: " + e.getMessage() + "\n\n" +
                            "Penyebab umum:\n" +
                            "1. File FXML tidak ditemukan atau rusak\n" +
                            "2. Controller class tidak ditemukan\n" +
                            "3. Error inisialisasi controller\n" +
                            "4. Koneksi database bermasalah";
            
            System.err.println("Gagal memuat PenggunaView.fxml:");
            e.printStackTrace();
            
            showAlert("Error Memuat Halaman", errorMsg);
        }
    }
    
    @FXML
    private void handleMenuMataKuliah() {
        try {
            System.out.println("Mencoba memuat MataKuliahView.fxml...");
            
            // Cek apakah file FXML ada
            URL url = getClass().getClassLoader().getResource("view/MataKuliahView.fxml");
            if (url == null) {
                String errorMsg = "File MataKuliahView.fxml tidak ditemukan.\n" +
                                "Pastikan file berada di: src/view/MataKuliahView.fxml";
                System.err.println(errorMsg);
                showAlert("File Tidak Ditemukan", errorMsg);
                return;
            }
            
            System.out.println("URL FXML: " + url);
            
            // Coba memuat FXML
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            
            // Jika berhasil dimuat, tampilkan di content area
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
            System.out.println("MataKuliahView.fxml berhasil dimuat");
            
        } catch (Exception e) {
            String errorMsg = "Terjadi kesalahan saat memuat halaman Data Mata Kuliah.\n\n" +
                            "Detail Error: " + e.getMessage() + "\n\n" +
                            "Penyebab umum:\n" +
                            "1. File FXML tidak ditemukan atau rusak\n" +
                            "2. Controller class tidak ditemukan\n" +
                            "3. Error inisialisasi controller\n" +
                            "4. Koneksi database bermasalah";
            
            System.err.println("Gagal memuat MataKuliahView.fxml:");
            e.printStackTrace();
            
            showAlert("Error Memuat Halaman", errorMsg);
        }
    }
    
    @FXML
    private void handleMenuPengaturan() {
        showAlert("Info", "Fitur pengaturan akan segera hadir.");
    }
    
    @FXML
    private void handleLogout() {
        try {
            // Load login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent root = loader.load();
            
            // Get current stage
            Stage stage = (Stage) contentArea.getScene().getWindow();
            
            // Set new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login - Anomali Sistem");
            stage.centerOnScreen();
            
        } catch (Exception e) {
            showAlert("Error", "Gagal melakukan logout: " + e.getMessage());
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        
        // Create text area for long messages
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        
        // Create scroll pane for the text area
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        alert.getDialogPane().setContent(scrollPane);
        alert.getDialogPane().setPrefSize(600, 300);
        alert.showAndWait();
    }
}