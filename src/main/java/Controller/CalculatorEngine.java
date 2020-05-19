package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorEngine implements EventHandler<ActionEvent> {

    private final Controller controller;
    private char action; // Арифметический метод
    private char sex = 'W';
    private double result = 0; // Результат выражения или значения
    private double displayValue = 0; // Значение на экране
    private int mark = 0; // Метка
    private String value;
    private int SKF = 0;
    private int QT = 0;

    public CalculatorEngine(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent event) {

        Button clickedButton = (Button) event.getSource();
        if (controller.displayField.getText().equals("На ноль делить нельзя!")) {
            controller.displayField.setText("");
            controller.displayField.setFont(Font.font(" ", FontWeight.BOLD, 55));
        }
        String displayFieldText = controller.displayField.getText();
        if (!displayFieldText.equals("") && !displayFieldText.equals(".")) displayValue = Double.parseDouble(displayFieldText);
        Object screen = event.getSource();

        // Отображение вводимых чисел на экране
        String buttonLabel = clickedButton.getText();
        final boolean b = buttonLabel.equals("1") || buttonLabel.equals("2") ||
                buttonLabel.equals("3") || buttonLabel.equals("4") ||
                buttonLabel.equals("5") || buttonLabel.equals("6") ||
                buttonLabel.equals("7") || buttonLabel.equals("8") ||
                buttonLabel.equals("9") || buttonLabel.equals("0") ||
                buttonLabel.equals(".");

        if (mark == 0 && b) {
            controller.displayField.setText(displayFieldText + buttonLabel);
        }

        if (mark == 1 && b) {
            if (buttonLabel.equals(".")) return;
            controller.displayField.setText("" + buttonLabel);
            mark = 0;
        }

        StringBuilder builder = new StringBuilder(controller.displayField.getText());
        if (builder.length() > 0 && builder.charAt(0) == '-' && builder.charAt(1) == '0') {
            builder.deleteCharAt(1);
            controller.displayField.setText(String.valueOf(builder));
        }
        if (builder.length() > 0 && builder.charAt(0) == '.') {
            builder.deleteCharAt(0);
            builder.insert(0, ".");
            builder.insert(0, "0");
            controller.displayField.setText(String.valueOf(builder));
        }
        if (builder.length() > 1) {
            if (builder.charAt(0) == '0' && builder.charAt(1) != '.') {
                builder.deleteCharAt(0);
                controller.displayField.setText(String.valueOf(builder));
            }
        }

        // Выбор арифметического действия
        if (screen == controller.numButtons[4]) {
            QT = 0;
            SKF = 0;
            result = 0;
            displayValue = 0;
            controller.displayField.setText("0");
        } else if (screen == controller.numButtons[5]) {
            String str = controller.displayField.getText();
            if (str != null && str.length() > 0) {
                str = str.substring(0, str.length() - 1);
                controller.displayField.setText(str);
            }
        } else if (screen == controller.numButtons[19]) {
            action = '+';
            result = displayValue;
            mark = 1;
        } else if (screen == controller.numButtons[15]) {
            if (displayValue == 0 && controller.displayField.getText().equals(""))
                controller.displayField.setText(buttonLabel + "0");
            else {
                action = '-';
                result = displayValue;
                mark = 1;
            }
        } else if (screen == controller.numButtons[7]) {
            action = '/';
            result = displayValue;
            mark = 1;
        } else if (screen == controller.numButtons[11]) {
            action = '*';
            result = displayValue;
            mark = 1;
        } else if (screen == controller.numButtons[22]) {
            if (displayFieldText.indexOf(".") > 0) {
                controller.displayField.setText(displayFieldText + "");
            } else {
                controller.displayField.setText(displayFieldText + buttonLabel);
            }
        } else if (screen == controller.numButtons[6]) {
            action = '^';
            result = displayValue;
            mark = 1;
        } else if (screen == controller.numButtons[0]) {
            sex = 'W';
            if (clickedButton.getText().equals("Жен")) {
                clickedButton.setText("Муж");
                sex = 'M';
            } else {
                clickedButton.setText("Жен");
                sex = 'W';
            }
        } else if (screen == controller.numButtons[1]) {
            action = 'K';
            result = displayValue;
            mark = 1;
            SKF = 1;
        } else if (screen == controller.numButtons[2]) {
            action = 'Q';
            result = displayValue;
            mark = 1;
            QT = 1;
        } else if ((SKF == 1 || QT == 1) && screen == controller.numButtons[3] && displayValue != 0) {
            if (SKF == 1) controller.showMessage("СКФ (по формуле CKD-EPI): = " + resultSKF() + " мл/мин/1,73м2\n" +
                        "Градация " + value + "  (по классификации KDIGO)");
            if (QT == 1) controller.showMessage(resultQTc());
        } else if (screen == controller.numButtons[20]) {
            StringBuilder str = new StringBuilder(controller.displayField.getText());
            if (str.length() > 0 && str.charAt(0) != '-') {
                str.insert(0, "-");
                controller.displayField.setText(String.valueOf(str));
            } else if (str.length() > 0 && str.charAt(0) == '-') {
                str.deleteCharAt(0);
                controller.displayField.setText(String.valueOf(str));
            }
        } else if (screen == controller.numButtons[23]) {

            //  Арифметическое действие
            if (action == '+') {
                result = result + displayValue;
                controller.displayField.setText("" + withFiveDigits(stringWithoutZero(result)));
            } else if (action == '-') {
                result = result - displayValue;
                controller.displayField.setText("" + withFiveDigits(stringWithoutZero(result)));
            } else if (action == '/') {
                if (displayValue == 0) {
                    controller.displayField.setFont(Font.font(" ", FontWeight.BOLD, 22));
                    controller.displayField.setText("На ноль делить нельзя!");
                } else {
                    result = result / displayValue;
                    controller.displayField.setText("" + withFiveDigits(stringWithoutZero(result)));
                }
            } else if (action == '*') {
                result = result * displayValue;
                controller.displayField.setText("" + withFiveDigits(stringWithoutZero(result)));
            } else if (action == '^') {
                double oldResult = result;
                for (int i = 1; i < displayValue; i++) {
                    result = result * oldResult;
                }
                controller.displayField.setText("" + withFiveDigits(stringWithoutZero(result)));
            } else if (action == 'K' && displayValue != 0) {
                resultSKF();
            } else if (action == 'I') {
                result = result / ((displayValue /100) * (displayValue / 100));
                BigDecimal aroundIMT = new BigDecimal(result).setScale(1, RoundingMode.HALF_EVEN);
                controller.displayField.setText("" + aroundIMT);
            } else if (action == 'Q') {
                resultQTc();
            }
        }
    }

    private BigDecimal resultSKF() {
        double age = Math.pow (0.993, result);
        double mg = displayValue/88.4;
        double skf;
        double GFR = 1;
        if (sex == 'W' && mg <= 0.7) {
            skf = Math.pow ((mg/0.7), -0.329);
            GFR = 144 * skf * age;
        }
        if (sex == 'W' && mg > 0.7) {
            skf = Math.pow ((mg/0.7), -1.209);
            GFR = 144 * skf * age;
        }
        if (sex == 'M' && mg <= 0.9) {
            skf = Math.pow ((mg/0.9), -0.411);
            GFR = 141 * skf * age;
        }
        if (sex == 'M' && mg > 0.9) {
            skf = Math.pow ((mg/0.9), -1.209);
            GFR = 141 * skf * age;
        }
        BigDecimal aroundGFR = new BigDecimal(GFR).setScale(0, RoundingMode.HALF_EVEN);
        controller.displayField.setText("" + aroundGFR);

        if (GFR > 90)
            value = "1";
        if (GFR< 90 && GFR >= 60)
            value = "2";
        if (GFR < 60 && GFR >= 45)
            value = "3а";
        if (GFR < 45 && GFR >= 30)
            value = "3б";
        if (GFR < 30 && GFR >= 15)
            value = "4";
        if (GFR < 15 && GFR > 0)
            value = "5";

        SKF = 0;
        return aroundGFR;
    }

    private String resultQTc() {
        if (result >= 60 && result <= 100) {
            double RR = 60 / result;
            double QTc = displayValue / Math.sqrt(RR);
            BigDecimal aroundQTc = new BigDecimal(QTc).setScale(0, RoundingMode.HALF_EVEN);
            controller.displayField.setText("" + aroundQTc);
            QT = 0;
            return ("QTc (по формуле Базетта) = " + aroundQTc + " мсек\n");
        } else {
            double RR = 60 / result;
            double cons = 0.154;
            double QTc = displayValue + (cons * (1 - RR)) * 1000;
            BigDecimal aroundQTc = new BigDecimal(QTc).setScale(0, RoundingMode.HALF_EVEN);
            controller.displayField.setText("" + aroundQTc);
            QT = 0;
            return ("QTc (по формуле Framingham) = " + aroundQTc + " мсек\n");
        }
    }

    private String stringWithoutZero(double result) {
        String newValue = String.valueOf(result);
        if (newValue.endsWith(".0")) newValue = newValue.substring(0, newValue.length() - 2);
        return newValue;
    }

    private String withFiveDigits(String result) {
        BigDecimal aroundResult;
        if (result.split("\\.").length > 1) {
            aroundResult = new BigDecimal(result).setScale(4, RoundingMode.HALF_EVEN);
            String stringAroundResult = String.valueOf(aroundResult);
            if (stringAroundResult.endsWith(".0000"))
                stringAroundResult = stringAroundResult.substring(0, stringAroundResult.length() - 5);
            if (stringAroundResult.endsWith("000"))
                stringAroundResult = stringAroundResult.substring(0, stringAroundResult.length() - 3);
            if (stringAroundResult.endsWith("00"))
                stringAroundResult = stringAroundResult.substring(0, stringAroundResult.length() - 2);
            if (stringAroundResult.endsWith("0"))
                stringAroundResult = stringAroundResult.substring(0, stringAroundResult.length() - 1);
            return stringAroundResult;
        }
        return result;
    }

}
