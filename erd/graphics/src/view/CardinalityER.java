package view;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.Erd;
import relationships.*;

public class CardinalityER extends DiagramElement {
    protected Cardinality cardinality;
    protected Line line;
    protected EntityER entity;
    protected RelationshipER relationship;
    protected Label c1;

    protected ContextMenu contextMenu;
    protected MenuItem many;
    protected MenuItem one;
    protected MenuItem oneOrMore;
    protected MenuItem onlyOne;

    protected static final Color LINE_COLOR = Color.DARKRED;
    protected static final String MANY_VALUE = "N";
    private static final double LINE_THICKNESS = 5.0f;
    public CardinalityER(Pane root, Group group, Cardinality cardinality) {
        super(root, group);
        this.cardinality = cardinality;
        initializeCardinality();
        initializeMenu();
    }

    public CardinalityER(Pane root, Erd erd) {
        this(root, new Group(), new OnlyOne());
    }

    public void setEntity(EntityER entity) {
        if(entity != null) {
            resetTextLabel();
            this.entity = entity;
            entity.children.add(this);
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
            resetTextLabel();
            this.relationship = relationship;
            cardinality.setRelationship(this.relationship.relationship);
            if(entity != null)
                drawPane();
            relationship.label.widthProperty().addListener((obs, oldVal, newVal) -> {
                line.setStartX(relationship.group.localToScene(relationship.group.getLayoutBounds()).getMinX()
                        - root.localToScene(root.getLayoutBounds()).getMinX()
                        + relationship.group.localToScene(relationship.group.getLayoutBounds()).getWidth() / 2);
            });

            relationship.label.heightProperty().addListener((obs, oldVal, newVal) -> {
                line.setStartY(relationship.group.localToScene(relationship.group.getLayoutBounds()).getMinY()
                        - root.localToScene(root.getLayoutBounds()).getMinY()
                        + relationship.group.localToScene(relationship.group.getLayoutBounds()).getHeight() / 2);
            });
            relationship.group.layoutXProperty().addListener((obs, oldVal, newVal) -> {
                line.setStartX(relationship.group.localToScene(relationship.group.getLayoutBounds()).getMinX()
                        - root.localToScene(root.getLayoutBounds()).getMinX()
                        + relationship.group.localToScene(relationship.group.getLayoutBounds()).getWidth() / 2);
            });
            relationship.group.layoutYProperty().addListener((obs, oldVal, newVal) -> {

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
            c1.setLayoutX(newVal.doubleValue() + line.getEndX() - (newVal.doubleValue() + line.getEndX())/2);
        });
        line.startYProperty().addListener((obs, oldVal, newVal) -> {
            c1.setLayoutY(newVal.doubleValue() + line.getEndY() - (newVal.doubleValue() + line.getEndY())/2);
        });
        line.endXProperty().addListener((obs, oldVal, newVal) -> {
            c1.setLayoutX(newVal.doubleValue() + line.getStartX() - (newVal.doubleValue() + line.getStartX())/2);
        });
        line.endYProperty().addListener((obs, oldVal, newVal) -> {
            c1.setLayoutY(newVal.doubleValue() + line.getStartY() - (newVal.doubleValue() + line.getStartY())/2);
        });
    }
    private void resetTextLabel() {
        int min = cardinality.getMin();
        int max = cardinality.getMax();

        c1.setText((min == Cardinality.MANY_CARD ? MANY_VALUE : String.valueOf(min))
                + "-"
                + (max == Cardinality.MANY_CARD ? MANY_VALUE : String.valueOf(max)));
    }
    private void replaceCardinality(Cardinality newCardinality) {
        //if(relationship.relationship.getLinks().size() > 2 && newCardinality.getClass().equals(relationship.relationship.getLinks().get(0))) {
            //System.out.println("Cardinality: \t\t\t\t\t"+cardinality.getEntity());
            cardinality.getRelationship().getLinks().remove(cardinality);
            cardinality = Cardinality.copyCardinality(cardinality, newCardinality);
            cardinality.getRelationship().getLinks().add(cardinality);
            entity.entity.changed();
            //System.out.println("Changed:\t\t\t"+(relationship.relationship.getLinks().stream().anyMatch(cardinality1 -> cardinality1.equals(cardinality))));
            //System.out.println("Cardinality: \t\t\t\t\t"+cardinality.getEntity());
            //erd.getRelationships().forEach((relationship) -> relationship.getLinks().forEach((cardinality) -> System.out.println(cardinality.getEntity())));

            //System.out.println(cardinality.getClass());
            //System.out.println("CIAO");
            //System.out.println(relationship.relationship.getLinks().contains(cardinality));
            //relationship.relationship.getLinks().forEach(x->System.out.println(x.getEntity().getName()));
            //System.out.println("CIAO");
            resetTextLabel();
        //}

    }
    private void initializeMenuEvents() {
        many.setOnAction(event -> {
            replaceCardinality(new Many());
        });
        one.setOnAction(event -> {
            replaceCardinality(new One());
        });
        oneOrMore.setOnAction(e -> {
            replaceCardinality(new OneOrMore());
        });
        onlyOne.setOnAction((ActionEvent event) -> {
            replaceCardinality(new OnlyOne());
        });
        line.setOnContextMenuRequested(event -> {
            if(relationship.relationship.getLinks().size() <= 2) {
                contextMenu.show(line, event.getScreenX(), event.getScreenY());
            }
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

    }
    private void initializeEvents() {

    }

    @Override
    public void drawPane() {
        line.setFill(LINE_COLOR);
        line.setStrokeWidth(LINE_THICKNESS);
        c1.setTextFill(Color.BLACK);
        //c1.setLabelFor(line);
        root.getChildren().addAll(line ,c1);
        line.toBack();
        line.toBack();
        c1.toFront();
        c1.toFront();
    }

    @Override
    protected void deleteChildren() {
        children.forEach(DiagramElement::deleteChildren);
        root.getChildren().removeAll(line, c1);
        relationship.relationship.getLinks().remove(cardinality);
    }
}
