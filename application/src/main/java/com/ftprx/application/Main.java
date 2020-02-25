package com.ftprx.application;

import com.ftprx.server.ServerModule;
import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.Inject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {
    private static final String TITLE      = "FtpRx";
    private static final boolean RESIZABLE = true;
    private static final int MIN_WIDTH     = 800;
    private static final int MIN_HEIGHT    = 450;

    @Inject
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        GuiceContext context = new GuiceContext(this,
                () -> Arrays.asList(new ServerModule()));
        context.init();

        fxmlLoader.setLocation(getClass().getClassLoader().getResource("root.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setTitle(TITLE);
        primaryStage.setResizable(RESIZABLE);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
