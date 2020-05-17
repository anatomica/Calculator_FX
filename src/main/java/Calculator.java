import Controller.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Calculator extends javafx.application.Application {
    public static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Калькулятор Макса");
        stage.getIcons().add(new Image("/calculator.png"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scene.fxml"));
        Parent root = loader.load();

        scene = new Scene(root);

        Controller controller = loader.getController();
        // stage.setOnHidden(e -> controller.shutdown());
        stage.setOnHidden(event -> { });
        stage.setScene(scene);
        stage.setX(1200);
        stage.setY(330);
        stage.show();
    }
}