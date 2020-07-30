package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

   public class Data {
        private double K;
        private double Y;

        public Data(double k, double y) {
            this.K = k;
            this.Y = y;
        }

       public double getK() {
           return K;
       }

       public void setK(double k) {
           this.K = k;
       }

       public double getY() {
           return Y;
       }

       public void setY(double y) {
           this.Y = y;
       }
    }

    ObservableList<Data> data = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Data> tblData;

    @FXML
    private TableColumn<Data, Double> colK;

    @FXML
    private TableColumn<Data, Double> colY;

    @FXML
    private TextField editA;

    @FXML
    private TextField editB;

    @FXML
    private Button btnFillRandom;

    @FXML
    private Button btnRun;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnClear;

    @FXML
    void initialize() {
        initData();

        colK.setCellValueFactory(new PropertyValueFactory<Data, Double>("K"));
        colY.setCellValueFactory(new PropertyValueFactory<Data, Double>("Y"));

        btnExit.setOnAction(event -> {
            Platform.exit();
        });

        btnClear.setOnAction(event -> {
            editA.clear();
            editA.setPromptText("Введите число");
            editB.clear();
            editB.setPromptText("Введите число");
            btnRun.setDisable(true);
        });

        btnFillRandom.setOnAction(event -> {
            data.clear();
            initData();
            btnRun.setDisable(false);
        });

        btnRun.setOnAction(event -> {
            try {
                calc();
            }
            catch (Exception e) {
                btnClear.fire();
            }
        });

        editA.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!tryParseDouble(editA.getText())) {
                    editA.clear();
                    editA.setPromptText("Введите число");
                    btnRun.setDisable(true);
                }
                else {
                    btnRun.setDisable(false);
                }
            }
        });

        editB.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!tryParseDouble(editB.getText())) {
                    editB.clear();
                    editB.setPromptText("Введите число");
                    btnRun.setDisable(true);
                }
                else {
                    btnRun.setDisable(false);
                }
            }
        });

        tblData.setItems(data);
    }

    private void calc() {
        double a = Double.parseDouble(editA.getText());
        double b = Double.parseDouble(editB.getText());
        for(int i = 0; i<10; i++) {
            double k = data.get(i).getK();
            double y = Math.sqrt(Math.cos(k)* Math.cos(k)/(a*a + b*b - Math.sin(k)));
            double sumK = 0;
            for (int j = 0; j < i; j++) {
                sumK +=  data.get(i).getK();
            }
            data.set(i, new Data(k, y*sumK));
        }
    }
    private void initData() {
        editA.setText(String.format("%d", Math.round(randomBetween(-100, 100))));
        editB.setText(String.format("%d", Math.round(randomBetween(-100, 100))));
        for(int i = 0; i<10; i++) {
            data.add(new Data(randomBetween(-100, 100), Double.NaN));
        }
    }

    private double randomBetween(int min, int max) {
        return Math.random() * (max - min + 1) + min;
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
