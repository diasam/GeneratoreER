package view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class DiagramElement {
    protected Pane pane;
    protected Shape background;
    protected static final double W = 250, H = 250;
    protected static Pane root;
    public DiagramElement(Pane pane, Pane root) {
        this.pane = pane;
        pane.setPrefSize(W,H);
        pane.resize(W,H);
        this.root = root;
    }

    public DiagramElement(Pane pane) {
        this.pane = pane;
    }

    public abstract void drawPane();
}
