package com.t12practica2calculadora;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
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
                if(output.getText().isEmpty() || !Character.isDigit(output.getText().charAt(output.getText().length()-1))){
                    animacionBarra.play();
                }else{
                    calcular();
                }
                break;
            case "C":
                output.setText("");
                break;
            case "+", "X", "/":
                if(output.getText().isEmpty() || !Character.isDigit(output.getText().charAt(output.getText().length()-1))){
                    animacionBarra.play();
                }else{
                    output.setText(output.getText().concat(boton.getText()));
                }
                break;
            case "-":
                if((output.getText().isEmpty() || output.getText().charAt(output.getText().length()-1) != '-') && !output.getText().equals("Infinity")){
                    output.setText(output.getText().concat(boton.getText()));
                }else{
                    animacionBarra.play();
                }
                break;
            case ".":
                if(!output.getText().isEmpty() && comprobarPunto()){
                    output.setText(output.getText().concat(boton.getText()));
                }else{
                    animacionBarra.play();
                }
                break;
            case "<--":
                if(!output.getText().isEmpty()) {
                    output.setText(output.getText().substring(0, output.getText().length() - 1));
                }else{
                    animacionBarra.play();
                }
                break;
            default:
                if(!output.getText().equals("Infinity")) {
                    output.setText(output.getText().concat(boton.getText()));
                }else{
                    animacionBarra.play();
                }
                break;
        }
    }

    private boolean comprobarPunto(){
        if(Character.isDigit(output.getText().charAt(output.getText().length()-1))){
           for(int i = output.getText().length()-2; i >= 0; i--){
               if(!Character.isDigit(output.getText().charAt(i))){
                   return output.getText().charAt(i) != '.';
               }
           }
           return true;
        }else{
            return false;
        }
    }

    private void calcular(){
        ArrayList<String> stringTrozeado = new ArrayList<>();

        for(String i : output.getText().split("((?=\\+)|(?<=\\+))|((?=-)|(?<=-))|((?=X)|(?<=X))|((?=/)|(?<=/))")){
            stringTrozeado.add(i);
        }

        //Generar numeros negativos antes del calculo
        for(int i = 0; i < stringTrozeado.size(); i++){
            if(stringTrozeado.get(i).equals("-")){
                if(i-1 < 0 || !Character.isDigit(stringTrozeado.get(i-1).charAt(stringTrozeado.get(i-1).length()-1))){
                    double aux = Double.parseDouble(stringTrozeado.get(i+1));
                    aux *= -1;
                    //Sustituyo el valor positivo por el negativo
                    stringTrozeado.remove(i+1);
                    stringTrozeado.add(i+1, Double.toString(aux));
                    //Elimino el signo - suelto
                    stringTrozeado.remove(i);
                }
            }
        }

        //Multiplicacion y division
        for(int i = 0; i < stringTrozeado.size(); i++){
            if(stringTrozeado.get(i).equals("X") || stringTrozeado.get(i).equals("/")){
                double termino1 = Double.parseDouble(stringTrozeado.get(i-1));
                double termino2 = Double.parseDouble(stringTrozeado.get(i+1));

                double resultadoOp;

                if(stringTrozeado.get(i).equals("X")) {
                    resultadoOp = termino1 * termino2;
                }else{
                    resultadoOp = termino1 / termino2;
                }

                stringTrozeado.remove(i+1);
                stringTrozeado.remove(i);
                stringTrozeado.remove(i-1);
                stringTrozeado.add(i-1, Double.toString(resultadoOp));

                i = 0;
            }
        }

        //Suma y resta
        for(int i = 0; i < stringTrozeado.size(); i++){
            if(stringTrozeado.get(i).equals("+") || stringTrozeado.get(i).equals("-")){
                double termino1 = Double.parseDouble(stringTrozeado.get(i-1));
                double termino2 = Double.parseDouble(stringTrozeado.get(i+1));

                double resultadoOp;

                if(stringTrozeado.get(i).equals("+")) {
                    resultadoOp = termino1 + termino2;
                }else{
                    resultadoOp = termino1 - termino2;
                }

                stringTrozeado.remove(i+1);
                stringTrozeado.remove(i);
                stringTrozeado.remove(i-1);
                stringTrozeado.add(i-1, Double.toString(resultadoOp));

                i = 0;
            }
        }

        for(String i : stringTrozeado){
            System.out.println(i);
        }

        if(stringTrozeado.size() == 1) {
            output.setText(stringTrozeado.get(0));
        }else{
            System.out.println("Algo raro ha pasao");
        }
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