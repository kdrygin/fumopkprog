package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSolve;

    @FXML
    private TextField editB;

    @FXML
    private TextField editA;

    @FXML
    private TextField editX;

    @FXML
    private Button btnClear;

    @FXML
    private TextField editAnswer;

    @FXML
    private Button btnExit;

    @FXML
    void initialize() {
        btnExit.setOnAction(event -> {
            Platform.exit();
        });

        btnClear.setOnAction(event -> {
            editA.clear();
            editB.clear();
            editX.clear();
            editAnswer.clear();
            btnSolve.setDisable(true);
        });

        btnSolve.setOnAction(event -> {
            double x = Double.parseDouble(editX.getText());
            double a = Double.parseDouble(editA.getText());
            double b = Double.parseDouble(editB.getText());
            double y = x > 7 ? x * Math.pow(a + b, 2) : (x + 4)/(a*a + b*b);
            editAnswer.setText(String.format("%f", y));
        });

        editA.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!tryParseDouble(editA.getText())) {
                    editA.setText("");
                    editA.setPromptText("Введите числовое значение");
                    btnSolve.setDisable(true);
                } else if ( checkABX() )  {
                    editA.setText("");
                    editA.setPromptText("Введите не нулевое значение");
                    btnSolve.setDisable(true);
                }
                else {
                    btnSolve.setDisable(false);
                }
            }
        });

        editB.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!tryParseDouble(editB.getText())) {
                    editB.setText("");
                    editB.setPromptText("Введите числовое значение");
                    btnSolve.setDisable(true);
                } else if ( checkABX() )  {
                    editB.setText("");
                    editB.setPromptText("Введите не нулевое значение");
                    btnSolve.setDisable(true);
                }
                else {
                    btnSolve.setDisable(false);
                }
            }
        });

        editX.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!tryParseDouble(editX.getText())) {
                    editX.setText("");
                    editX.setPromptText("Введите числовое значение");
                    btnSolve.setDisable(true);
                }  else if ( checkABX() )  {
                    editX.setText("");
                    editX.setPromptText("Введите значение больше 7");
                    btnSolve.setDisable(true);
                }
                else {
                    btnSolve.setDisable(false);
                }
            }
        });
    }

    boolean checkABX() {
        return  Double.parseDouble(editX.getText()) <= 7 &&
                Double.parseDouble(editB.getText()) == 0 &&
                Double.parseDouble(editA.getText()) == 0;
    }
    boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
        
    }

}
