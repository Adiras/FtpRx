module ftprx.application {
    requires ftprx.server;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens com.ftprx.application to javafx.graphics;
    opens com.ftprx.application.controler to javafx.fxml;
}