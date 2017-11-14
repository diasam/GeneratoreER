package view;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MyPane  {
    private Pane pane;
    // Clock pane's width and height
    private double w = 250, h = 250;
    private Circle circle;
    private Color color = Color.BLACK;
    public MyPane(Pane pane) {
        this.pane = pane;
        pane.setPrefSize(w,h);
        drawPane();
        setEvents();
    }

    public void setEvents() {
        pane.setOnMouseClicked(e -> {
            /*if (circle.contains(e.getX(), e.getY())) {
                circle.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                pane.requestLayout();
            }
            else if(pane.getChildren().get(1).contains(e.getX(), e.getY())) {
                Label l = ((Label)pane.getChildren().get(2));
                l.setText(l.getText() + "s");
                pane.requestLayout();
            }*/

        });
        pane.setOnMouseDragged(e -> {
            //pane.relocate(e.getX(), e.getY());
            //pane.requestLayout();

            if (circle.contains(e.getX(), e.getY())) {
                //circle.setTranslateX(e.getX() - circle.getRadius());
                //circle.setTranslateY(e.getY() - circle.getRadius());
                pane.relocate(e.getSceneX(), e.getSceneY());
                pane.requestLayout();
            }

        });
    }
    public void drawPane() {
        Rectangle background = new Rectangle(w,h);
        background.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        pane.getChildren().add(background);
        double clockRadius = Math.min(w, h) * 0.8 * 0.5;
        circle = new Circle(w/2, h/2, clockRadius);
        circle.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        Label l = new Label("Hello there");
        pane.getChildren().add(circle);
        l.setPrefSize(100,100);
        pane.getChildren().add(l);
    }
    public Pane getPane() {
        return pane;
    }
}
