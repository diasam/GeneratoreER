import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ErDiagram extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();
    }
    private void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillOval(50, 100, 200, 200);
        gc.setFill(Color.RED);
        gc.fillRect(300, 100, 200, 200);
    }

}
