module com.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    exports com.example.lab6 to javafx.graphics;
    exports com.example.lab6.Controller to javafx.graphics, javafx.fxml;

    opens com.example.lab6.Controller to javafx.fxml;

}