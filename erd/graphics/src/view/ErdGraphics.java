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

public class ErdGraphics extends Application {
    public static void main(String[] args) {
        Map<String, String> m = new HashMap<>();
        m.put("prova", "prova2");
        m.put("prova", "prova3");
        //System.out.print(m.get("prova"));
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
