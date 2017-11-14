package view;


import attributes.Attribute;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public abstract class AttributeER extends DiagramElement implements Generable{
    protected Pane entityPane;
    protected Attribute attribute;
    protected Ellipse ellipse;
    protected Label label;
    protected Line line;
    public AttributeER(Pane pane, Pane entityPane, Attribute attribute) {
        super(pane);
        this.entityPane = entityPane;
        this.attribute = attribute;
        drawPane();
        //entityPane.setOnMouseDragReleased();
        entityPane.setOnContextMenuRequested((e) -> {
            entityPane.setLayoutX(entityPane.getLayoutX() + 100);
            entityPane.setLayoutY(entityPane.getLayoutY() + 100);
            line.setStartX(pane.getLayoutX() + pane.getWidth() / 2);
            line.setStartY(pane.getLayoutY() + pane.getHeight() / 2);
            line.setEndX(entityPane.getLayoutX() + entityPane.getWidth() / 2);
            line.setEndY(entityPane.getLayoutY() + entityPane.getHeight() / 2);
            System.out.println(entityPane.getLayoutX());
            System.out.println("yo");
        });
        entityPane.setOnMouseDragged(e -> {
            line.setStartX(pane.getLayoutX() + pane.getWidth() / 2);
            line.setStartY(pane.getLayoutY() + pane.getHeight() / 2);
            line.setEndX(entityPane.getLayoutX() + entityPane.getWidth() / 2);
            line.setEndY(entityPane.getLayoutY() + entityPane.getHeight() / 2);

        } );

    }

    public void drawPane() {
        label = new Label(attribute.getName());
        line = new Line(
                pane.getLayoutX() + pane.getWidth() / 2,
                pane.getLayoutY() + pane.getHeight() / 2,
                entityPane.getLayoutX() + entityPane.getWidth() / 2,
                entityPane.getLayoutY() + entityPane.getHeight() / 2);
        Group g = new Group();

        g.getChildren().add(label);
                //entityPane.localToScene(entityPane.getLayoutBounds()).getMinX(),
               //entityPane.localToScene(entityPane.getLayoutBounds()).getMinY(),
                //pane.localToScene(pane.getLayoutBounds()).getMinX(),
                //pane.localToScene(pane.getLayoutBounds()).getMinY());

        ellipse = new Ellipse(label.getLayoutX() + 100,label.getLayoutY() + 100, label.getWidth(), label.getHeight());
        ellipse.setFill(Color.BLACK);
        ellipse.setStroke(Color.BLACK);
        g.getChildren().add(ellipse);
        root.getChildren().addAll(g,line);
        pane.setTranslateZ(-1);
        System.out.println(label.getLayoutX() );
        //root.getChildren().add(pane);
        System.out.println(entityPane.getLayoutX());
    }

}
