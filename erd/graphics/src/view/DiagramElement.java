package view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import model.Erd;

import java.util.ArrayList;

public abstract class DiagramElement {
    protected static Erd erd;
    protected Group group;
    protected static final double W = 250, H = 250;
    protected Pane root;
    protected static Stage stage;
    protected final ArrayList<DiagramElement> children = new ArrayList<>();
    public DiagramElement() {
    }

    public DiagramElement(Pane root, Group group, Erd erd) {
        this.group = group;
        this.root = root;
        this.erd = erd;
    }
    public DiagramElement(Pane root, Group group) {
        this.group = group;
        this.root = root;
    }
    public DiagramElement(Pane root, Erd erd) {
        this(root
                , new Group()
                , erd);
    }
    public DiagramElement(Pane root) {
        this(root
                , new Group()); }

    public static Erd getErd() {
        return erd;
    }

    public Group getGroup() {
        return group;
    }

    public static double getW() {
        return W;
    }

    public static double getH() {
        return H;
    }

    public Pane getRoot() {
        return root;
    }

    public static void setErd(Erd erd) {
        DiagramElement.erd = erd;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        DiagramElement.stage = stage;
    }

    public abstract void drawPane();
    protected abstract void deleteChildren();
}
