package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Erd;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ErdGraphics extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(ErdGraphics.class.getResource("erd_graphics_root.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        primaryStage.setTitle("ERD");
        primaryStage.setScene(new Scene(page));
        primaryStage.show();
    }


}
