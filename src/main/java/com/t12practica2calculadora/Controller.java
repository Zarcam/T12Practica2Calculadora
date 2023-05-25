package com.t12practica2calculadora;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField output;

    private Timeline animacionBarra = new Timeline();

    @FXML
    private void pulsadoBoton(ActionEvent event){
        Button boton = (Button) event.getSource();

        switch(boton.getText()){
            case "=":
                calcular();
                break;
            case "C":
                output.setText("");
                break;
            case "+", "X", "/":

                break;
            case "-":
                break;
            case ".":
                break;
            default:
                output.setText(output.getText().concat(boton.getText()));
                break;
        }
    }

    private void calcular(){
        animacionBarra.play();
    }

    private void prepararAgitar(){
        animacionBarra.setCycleCount(1);

        KeyValue kv = new KeyValue(output.rotateProperty(), 2, Interpolator.LINEAR);
        KeyValue kv2 = new KeyValue(output.rotateProperty(), 0, Interpolator.LINEAR);
        KeyValue kv3 = new KeyValue(output.rotateProperty(), -2, Interpolator.LINEAR);

        KeyFrame kf = new KeyFrame(Duration.millis(0), kv);
        KeyFrame kf2 = new KeyFrame(Duration.millis(100), kv2);
        KeyFrame kf3 = new KeyFrame(Duration.millis(200), kv3);
        KeyFrame kf4 = new KeyFrame(Duration.millis(300), kv2);

        animacionBarra.getKeyFrames().add(0, kf);
        animacionBarra.getKeyFrames().add(1, kf2);
        animacionBarra.getKeyFrames().add(2, kf3);
        animacionBarra.getKeyFrames().add(3, kf4);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepararAgitar();
    }
}