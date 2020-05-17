package Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public TextField displayField;
    @FXML
    public GridPane gridPane;

    public Button[] numButtons = new Button[24];
    public CalculatorEngine calculatorEngine = new CalculatorEngine(this);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayField.setAlignment(Pos.CENTER_RIGHT);
        displayField.setPrefSize(350, 140);
        displayField.setFont(Font.font(" ", FontWeight.BOLD, 50));
        displayField.setStyle("-fx-background-color: lightgray;");
        displayField.setEditable(false);
        displayField.setFocusTraversable(false);
        displayField.setText("0");

        String[] buttonName = {"Жен", "Креат", "QT", "СКФ /\nQTc", "C", "<=", "(^)", "/",
                "1", "2", "3", "*", "4", "5", "6", "-", "7", "8", "9", "+", "+/-", "0", ".", "="};

        for (int i = 0; i < numButtons.length; i++) {
            for (int j = 0; j < numButtons.length; j++) {
                if (i == j) {
                    numButtons[j] = new Button(buttonName[i]);
                    numButtons[j].setOnAction(calculatorEngine);
                    if (i > 6) numButtons[j].setFont(Font.font(" ", 22));
                }
            }
        }

        int count = 0;
        for (int i = 0; i < gridPane.impl_getRowCount(); i++) {
            for (int j = 0; j < gridPane.impl_getColumnCount(); j++) {
                gridPane.add(numButtons[count], j, i);
                numButtons[count].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                if (count >= 8 && count <= 10 || count >= 12 && count <= 14 ||
                        count >= 16 && count <= 18 || count == 21) {
                    numButtons[count].setStyle("-fx-background-color: snow;");
                    numButtons[count].setFont(Font.font(" ", FontWeight.BOLD, 22));
                }
                // else numButtons[count].setStyle("-fx-background-color: aliceblue;");
                gridPane.setStyle("-fx-background-color: lightgray;");
                gridPane.setHgap(5);
                gridPane.setVgap(5);
                count++;
            }
        }
    }

    public void showMessage (String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация!");
            alert.setHeaderText("Результат расчета:");
            VBox dialogPaneContent = new VBox();
            TextArea textArea = new TextArea();
            textArea.setText(message);
            dialogPaneContent.getChildren().addAll(textArea);
            alert.getDialogPane().setContent(dialogPaneContent);
            alert.setResizable(true);
            alert.showAndWait();
        });
    }
}


