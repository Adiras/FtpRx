module ftprx.application {
    requires ftprx.server;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.jetbrains.annotations;
    opens com.ftprx.application to javafx.graphics;
    opens com.ftprx.application.controller to javafx.fxml;
}