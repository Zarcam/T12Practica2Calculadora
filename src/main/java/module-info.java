module com.t12practica2calculadora {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.t12practica2calculadora to javafx.fxml;
    exports com.t12practica2calculadora;
}