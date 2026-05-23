module ejercicio.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires javafx.base;
    requires java.desktop;

    opens ejercicio.login.igu to javafx.fxml;
    opens ejercicio.login.logica;
    opens ejercicio.login.persistencia;

    exports ejercicio.login.igu;
    exports ejercicio.login.logica;
    exports ejercicio.login.persistencia;
}