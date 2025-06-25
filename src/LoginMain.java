import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Memuat file FXML dari folder login di dalam bin
            Parent root = FXMLLoader.load(getClass().getResource("/login/login.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
