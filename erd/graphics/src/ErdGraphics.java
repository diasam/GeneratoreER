import entites.Entity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Erd;
import view.EntityER;
import view.MyPane;

import java.io.IOException;

public class ErdGraphics extends Application {
    //MyPane myPane = new MyPane(new Pane());
    //EntityER myPane = new EntityER(new Pane());
    private Erd erd = new Erd("Noname");
    private EntityER myPane;
    private Scene scene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/view/erd_graphics_root.fxml")));
        primaryStage.setTitle("yo");
        primaryStage.setScene(scene);
        primaryStage.show();
        myPane = new EntityER();
        myPane.setStage(primaryStage);
        Pane erPane = ((Pane)scene.lookup("#er-pane"));
        //myPane = new EntityER(erPane, new Entity(), erd);
        //.getChildren().add(myPane.getPane());
        //erPane.getChildren().add(myPane.getPane());
        ((Pane)scene.lookup("#er-pane")).requestLayout();
        erPane.setOnMouseClicked((e) -> {
            if(e.getButton().compareTo(MouseButton.MIDDLE) == 0 ) {
                EntityER entityEr = new EntityER(erPane, new Entity(), erd);
                entityEr.getGroup().relocate(e.getX(), e.getY());
            }
        });

    }

}
