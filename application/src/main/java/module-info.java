module ftprx.application {
    requires ftprx.server;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jsr305;
    opens com.ftprx.application to javafx.graphics;
    opens com.ftprx.application.controller to javafx.fxml;
}