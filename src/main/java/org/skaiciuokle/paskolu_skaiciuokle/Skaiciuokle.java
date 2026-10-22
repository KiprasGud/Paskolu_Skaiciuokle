package org.skaiciuokle.paskolu_skaiciuokle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Skaiciuokle extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Skaiciuokle.class.getResource("Ekranas.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Skaičiuoklė");
        stage.setScene(scene);
        stage.show();
    }
}
