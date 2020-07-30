package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleDoubleProperty;

public class Controller {

    private double[][] matrix;
    final private int matrixRows = 5;
    final private int matrixCols = 5;
    final private int maxRndVal = 10;
    final private int minRndVal = -10;
    private double minValue = Double.NaN;
    private double maxValue = Double.NaN;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRun;

    @FXML
    private Button btnFillRandom;

    @FXML
    private TableView<double[]> tblData;

    @FXML
    private Label lblMinVal;

    @FXML
    private Label lblMaxVal;

    @FXML
    void initialize() {

        btnFillRandom.setOnAction(event -> {
            randomMatrix();
            fillTableData();
            lblMaxVal.setText(Double.toString(maxValue));
            lblMinVal.setText(Double.toString(minValue));
            btnRun.setDisable(false);
        });

        btnRun.setOnAction(event -> {
            calc();
            fillTableData();
        });

        btnRun.setDisable(true);
    }

    void calc() {
        if (Double.compare(maxValue, minValue*10) >= 0) {
            for(int i = 0 ; i < matrixRows; i++) {
                for(int j = 0 ; j < matrixCols; j++) {
                    if (matrix[i][j] == 0)  matrix[i][j] = 1;
                    if (matrix[i][j] < 0)  matrix[i][j] = Math.abs(matrix[i][j]);
                }
            }
        }
    }

    void fillTableData() {

        tblData.getColumns().clear();
        tblData.getItems().clear();

        if (matrixRows == 0) return;

        for(int i = 0 ; i < matrixCols; i++) {
            TableColumn<double[], Number> column = new TableColumn<>("C"+(i+1));
            column.setPrefWidth(tblData.getWidth()/matrixCols - 1);
            final int columnIndex = i;
            column.setCellValueFactory(cellData -> {
                double[] row = cellData.getValue();
                return new SimpleDoubleProperty(row[columnIndex]);
            });
            tblData.getColumns().add(column);
        }

        for(int i = 0; i < matrixRows; i++) {
            tblData.getItems().add(matrix[i]);
        }
    }

    private double randomBetween(int min, int max) {
        return Math.random() * (max - min + 1) + min;
    }

    private void randomMatrix() {
        // fil random values
        matrix = new double[matrixRows][matrixCols];
        for(int i = 0; i < matrixRows; i++) {
            for(int j = 0; j < matrixCols; j++) {
                matrix[i][j] = Math.round(randomBetween(minRndVal, maxRndVal));
            }
        }

        //find min/max
        minValue = maxValue = matrix[0][0];
        for(int i = 0 ; i < matrixRows; i++) {
            for(int j = 0 ; j < matrixCols; j++) {
                if (matrix[i][j] < minValue)  minValue = matrix[i][j];
                if (matrix[i][j] > maxValue)  maxValue = matrix[i][j];
            }
        }

    }


}
