package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.Erd;
import relationships.Cardinality;
import relationships.Many;

public class CardinalityER extends DiagramElement {
    protected Cardinality cardinality;
    protected Line line;
    protected EntityER entity;
    protected RelationshipER relationship;
    protected Label c1;
    protected Label c2;
    protected Rectangle clickableArea;

    public CardinalityER(Pane root, Group group, Cardinality cardinality) {
        super(root, group);
        this.cardinality = cardinality;
        initializeCardinality();
        //drawPane();
    }

    public CardinalityER(Pane root, Erd erd) {
        this(root, new Group(), new Many());
        /*new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1000);
                    if(entity != null) {
                        System.out.println(line.localToScene(line.getLayoutBounds()).getMinX() - root.localToScene(root.getLayoutBounds()).getMinX() + "asdf");

                    }
                } catch (InterruptedException e) {
                    System.out.println("yo");
                }
            }
        }).start();*/
    }

    public void setEntity(EntityER entity) {
        if(entity != null) {
            this.entity = entity;
            //cardinality.setEntity(this.entity.entity);
            if(relationship != null)
                drawPane();
            line.setEndX(entity.group.getLayoutX()
                    + entity.group.localToScene(entity.group.getLayoutBounds()).getWidth() / 2);
            line.setEndY(entity.group.getLayoutY()
                    + entity.group.localToScene(entity.group.getLayoutBounds()).getHeight() / 2);
            entity.group.layoutXProperty().addListener((obs, oldVal, newVal) -> {
                line.setEndX(newVal.doubleValue()
                        + entity.group.localToScene(entity.group.getLayoutBounds()).getWidth() / 2);
            });
            entity.group.layoutYProperty().addListener((obs, oldVal, newVal) -> {
                line.setEndY(newVal.doubleValue()
                        + entity.group.localToScene(entity.group.getLayoutBounds()).getHeight() / 2);
            });
        }
    }
    public void setRelationship(RelationshipER relationship) {
        if(relationship != null) {
            this.relationship = relationship;
            cardinality.setRelationship(this.relationship.relationship);
            if(entity != null)
                drawPane();
            relationship.group.layoutXProperty().addListener((obs, oldVal, newVal) -> {
                //line.setStartX(newVal.doubleValue() + relationship.localToScene(relationship.getLayoutBounds()).getWidth() / 2);
                line.setStartX(relationship.group.localToScene(relationship.group.getLayoutBounds()).getMinX()
                        - root.localToScene(root.getLayoutBounds()).getMinX()
                        + relationship.group.localToScene(relationship.group.getLayoutBounds()).getWidth() / 2);
            });
            relationship.group.layoutYProperty().addListener((obs, oldVal, newVal) -> {
                //line.setStartY(newVal.doubleValue() + relationship.localToScene(relationship.getLayoutBounds()).getHeight() / 2);
                line.setStartY(relationship.group.localToScene(relationship.group.getLayoutBounds()).getMinY()
                        - root.localToScene(root.getLayoutBounds()).getMinY()
                        + relationship.group.localToScene(relationship.group.getLayoutBounds()).getHeight() / 2);
            });
            line.setStartX(relationship.group.localToScene(relationship.group.getLayoutBounds()).getMinX()
                    - root.localToScene(root.getLayoutBounds()).getMinX()
                    + relationship.group.localToScene(relationship.group.getLayoutBounds()).getWidth() / 2);
            line.setStartY(relationship.group.localToScene(relationship.group.getLayoutBounds()).getMinY()
                    - root.localToScene(root.getLayoutBounds()).getMinY()
                    + relationship.group.localToScene(relationship.group.getLayoutBounds()).getHeight() / 2);
        }
    }


    private void initializeCardinality() {
        line = new Line();
        c1 = new Label();
        c2 = new Label();
        clickableArea = new Rectangle();
    }
    private void initializeEvents() {

    }

    @Override
    public void drawPane() {
        line.setFill(Color.BLACK);
        //group.getChildren().addAll(c1, c2);
        root.getChildren().addAll(line, group,c1,c2, clickableArea);
        line.toBack();
        line.toBack();
        c1.toBack();
        c1.toBack();
    }

    @Override
    protected void deleteChildrens() {

    }
}
