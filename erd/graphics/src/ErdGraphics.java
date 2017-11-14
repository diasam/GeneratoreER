import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.EntityER;
import view.MyPane;

import java.io.IOException;

public class ErdGraphics extends Application {
    //MyPane myPane = new MyPane(new Pane());
    //EntityER myPane = new EntityER(new Pane());
    EntityER myPane;
    Scene scene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/view/erd_graphics_root.fxml")));
        primaryStage.setTitle("yo");
        primaryStage.setScene(scene);
        primaryStage.show();

        Pane erPane = ((Pane)scene.lookup("#er-pane"));
        myPane = new EntityER(new StackPane(), erPane);
        //.getChildren().add(myPane.getPane());
        erPane.getChildren().add(myPane.getPane());
        ((Pane)scene.lookup("#er-pane")).requestLayout();

    }

}
