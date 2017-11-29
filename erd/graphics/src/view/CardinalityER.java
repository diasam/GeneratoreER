package view;

import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.TInteger;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.Erd;
import relationships.*;

import java.util.Optional;

public class CardinalityER extends DiagramElement {
    protected Cardinality cardinality;
    protected Line line;
    protected EntityER entity;
    protected RelationshipER relationship;
    protected Label c1;
    protected Label c2;
    protected Rectangle clickableArea;

    protected ContextMenu contextMenu;
    protected MenuItem many;
    protected MenuItem one;
    protected MenuItem oneOrMore;
    protected MenuItem onlyOne;

    private static final double LINE_THICKNESS = 5.0f;
    public CardinalityER(Pane root, Group group, Cardinality cardinality) {
        super(root, group);
        this.cardinality = cardinality;
        initializeCardinality();
        initializeMenu();
        //drawPane();
    }

    public CardinalityER(Pane root, Erd erd) {
        this(root, new Group(), new Many());
    }

    public void setEntity(EntityER entity) {
        if(entity != null) {
            this.entity = entity;
            cardinality.setEntity(this.entity.entity);
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
        line.startXProperty().addListener((obs, oldVal, newVal) -> {
            clickableArea.setX(newVal.doubleValue());
        });
        line.startYProperty().addListener((obs, oldVal, newVal) -> {
            clickableArea.setY(newVal.doubleValue());
        });
        line.endXProperty().addListener((obs, oldVal, newVal) -> {
            clickableArea.setWidth(newVal.doubleValue());
        });
        line.endYProperty().addListener((obs, oldVal, newVal) -> {
            clickableArea.setHeight(newVal.doubleValue());
        });
    }
    private void replaceCardinality(Cardinality newCardinality) {
        //System.out.println("Then\t"+cardinality.getClass().toString());
        cardinality.getRelationship().getLinks().remove(cardinality);
        cardinality = Cardinality.copyCardinality(cardinality, newCardinality);
        cardinality.getRelationship().getLinks().add(cardinality);
        //System.out.println("After\t"+cardinality.getClass().toString());
    }
    private void initializeMenuEvents() {
        many.setOnAction(event -> {
            replaceCardinality(new Many());
            /*
            PrimaryKey primaryKey = new PrimaryKey(new TInteger());
            entity.addPrimaryKey(primaryKey);
            attributesFactory.create(root
                    , group
                    , primaryKey);
            addLastFromFactory();
            */

        });
        one.setOnAction(event -> {
            replaceCardinality(new One());
            /*
            NormalAttribute na = new NormalAttribute(new TInteger());
            entity.addNormalAttribute(na);
            attributesFactory.create(root
                    , group
                    , na);
            addLastFromFactory();
            */
        });
        oneOrMore.setOnAction(e -> {
            replaceCardinality(new OneOrMore());
            /*
            Optional<String> result = textInputDialog.showAndWait();
            result.ifPresent(name -> entityName.setText(name));
            */
        });
        onlyOne.setOnAction((ActionEvent event) -> {
            replaceCardinality(new OnlyOne());
        });
        line.setOnContextMenuRequested(event -> {
            contextMenu.show(line, event.getScreenX(), event.getScreenY());
        });
    }
    private void initializeMenu() {
        contextMenu = new ContextMenu();
        one = new MenuItem("One");
        oneOrMore = new MenuItem("One or more");
        many = new MenuItem("Many");
        onlyOne = new MenuItem("Only one");

        contextMenu.getItems().addAll(one, oneOrMore, many, onlyOne);
        initializeMenuEvents();
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
        line.setStrokeWidth(LINE_THICKNESS);
        //group.getChildren().addAll(c1, c2);
        root.getChildren().addAll(line, group,c1,c2);
        line.toBack();
        line.toBack();
        c1.toBack();
        c1.toBack();
    }

    @Override
    protected void deleteChildrens() {

    }
}
