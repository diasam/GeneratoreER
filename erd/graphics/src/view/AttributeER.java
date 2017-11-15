package view;


import attributes.Attribute;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public abstract class AttributeER extends DiagramElement implements Generable{
    protected Group entityGroup;
    protected Attribute attribute;
    protected Ellipse ellipse;
    protected Label label;
    protected Line line;
    public static final double PADDING = 20.0;
    public AttributeER(Pane root, Group entityGroup, Attribute attribute) {
        super(root);
        this.entityGroup = entityGroup;
        this.attribute = attribute;
        ellipse = new Ellipse();
        label = new Label(attribute.getName());
        line = new Line(group.getLayoutX() + group.localToScene(group.getLayoutBounds()).getWidth() / 2,
                group.getLayoutY() + group.localToScene(group.getLayoutBounds()).getHeight() / 2,
                entityGroup.getLayoutX() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getWidth() / 2,
                entityGroup.getLayoutY() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getHeight() / 2);
        label.widthProperty().addListener((obs, oldVal, newVal) -> {
            //label.setLayoutX(label.getLayoutX() + label.localToScene(label.getLayoutBounds()).getWidth() / 4);
            ellipse.setRadiusX((newVal.doubleValue() + PADDING) / 2);
            ellipse.setCenterX(label.localToScene(label.getLayoutBounds()).getWidth() / 2);
            System.out.println("b" + newVal);
        });
        label.heightProperty().addListener((obs, oldVal, newVal) -> {
            //label.setLayoutX(group.getLayoutY() + group.localToScene(group.getLayoutBounds()).getHeight());
            ellipse.setRadiusY((newVal.doubleValue() + PADDING) / 2);
            ellipse.setCenterY(label.localToScene(label.getLayoutBounds()).getHeight() / 2);
            System.out.println("d" + newVal);
        });
        label.layoutXProperty().addListener((obs, oldVal, newVal) -> {
            ellipse.setCenterX(label.localToScene(label.getLayoutBounds()).getWidth() / 2);

            System.out.println("c" + newVal);
        });
        label.layoutYProperty().addListener((obs, oldVal, newVal) -> {
            ellipse.setCenterY(label.localToScene(label.getLayoutBounds()).getHeight()/2);
        });
        entityGroup.layoutXProperty().addListener((e) -> {
            line.setEndX(entityGroup.getLayoutX() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getWidth() / 2);
        });
        entityGroup.layoutYProperty().addListener((e) -> {
            line.setEndY(entityGroup.getLayoutY() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getHeight() / 2);
        });
        group.layoutXProperty().addListener((obs, oldVal, newVal) -> {
                //line.setStartX(group.getLayoutX() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getWidth() / 2);
                line.setStartX(group.getLayoutX() + group.localToScene(group.getLayoutBounds()).getWidth() / 2);
            //line.setStartX(group.getLayoutX());

        });
        group.layoutYProperty().addListener((obs, oldVal, newVal) -> {
            //line.setStartY(group.getLayoutY() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getHeight() / 2);
            System.out.println("\t\t" + group.getLayoutY());
            line.setStartY(newVal.doubleValue() + group.localToScene(group.getLayoutBounds()).getHeight() / 2);
            //line.setStartY(group.localToScene(group.getLayoutBounds()).getMinY() + group.localToScene(group.getLayoutBounds()).getHeight() / 2);
            //label.setLayoutX(label.localToScene(label.getLayoutBounds()).getMinY());
        });

        group.setOnMouseDragged((e) -> {
            group.relocate(
                    e.getSceneX() - root.localToScene(root.getLayoutBounds()).getMinX() - group.localToScene(group.getLayoutBounds()).getWidth() / 2,
                    e.getSceneY() - root.localToScene(root.getLayoutBounds()).getMinY() - group.localToScene(group.getLayoutBounds()).getHeight() / 2);
        });
        drawPane();
        //group.fireEvent(new);
/*
        group.getLayoutX() + group.localToScene(group.getLayoutBounds()).getWidth() / 2,
                group.getLayoutY() + group.localToScene(group.getLayoutBounds()).getHeight() / 2,
                entityGroup.getLayoutX() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getWidth() / 2,
                entityGroup.getLayoutY() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getHeight() / 2*/

        entityGroup.toFront();
        group.layoutXProperty().setValue(group.layoutXProperty().get());
        group.layoutYProperty().setValue(group.layoutYProperty().get());
    }

    public void drawPane() {
        //Group g = new Group();

        group.toBack();

        entityGroup.toFront();
        //group.minHeight(50);
        //group.minWidth(50);
       // group.resize(50,50);
        //label = new Label(attribute.getName());
        //label.setCenterShape(true);
        //label.relocate(entityGroup.getLayoutX() - 50, entityGroup.getLayoutY() - 50);
        group.relocate(Math.abs(entityGroup.getLayoutX() + 50), Math.abs(entityGroup.getLayoutY() - 50));

        //label.resize(15,15);

        //g.getChildren().add(label);

        /*line = new Line(
                group.getLayoutX() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getWidth() / 2,
                group.getLayoutY() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getHeight() / 2,
                entityGroup.getLayoutX() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getWidth() / 2,
                entityGroup.getLayoutY() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getHeight() / 2);*/

        //group.getChildren().add(label);
                //entityRect.localToScene(entityRect.getLayoutBounds()).getMinX(),
               //entityRect.localToScene(entityRect.getLayoutBounds()).getMinY(),
                //pane.localToScene(pane.getLayoutBounds()).getMinX(),
                //pane.localToScene(pane.getLayoutBounds()).getMinY());

        //ellipse = new Ellipse(100,100);
        //ellipse.relocate(group.getLayoutX(), group.getLayoutY());
        System.out.println("\t"+label.localToScene(label.getLayoutBounds()).getMinX());

        ellipse.setFill(Color.TRANSPARENT);
        //label.toFront();
        ellipse.setStroke(Color.BLACK);
        line.toBack();
        group.getChildren().addAll(ellipse, label);
        root.getChildren().addAll(line,group);
        System.out.println(label.getLayoutX() );
        //root.getChildren().add(pane);
        System.out.println(entityGroup.getLayoutX());

        //Rectangle r = new Rectangle(group.localToScene(group.getLayoutBounds()).getMinX(), group.localToScene(group.getLayoutBounds()).getMinY(), group.localToScene(group.getLayoutBounds()).getWidth(), group.localToScene(group.getLayoutBounds()).getHeight());



    }

}
