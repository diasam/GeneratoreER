package view;

import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Erd;
import relationships.Relationship;

public class RelationshipER extends DiagramElement {
    protected Relationship relationship;
    protected Rectangle relationshipRectangle;
    protected Label label;
    protected static final Color BACKGROUND = Color.WHITE;
    protected static final Color STROKE = Color.BLACK;
    public RelationshipER(Pane root, Erd erd) {
        this(root
                , new Group()
                , erd
                , new Relationship(erd)
                );
    }
    public RelationshipER(Pane root, Group group, Erd erd, Relationship relationship) {
        super(root, group, erd);
        this.relationship = relationship;
        initializeRelationship();
        drawPane();
    }
    private void initializeRelationship() {
        relationshipRectangle = new Rectangle();
        label = new Label(relationship.getName());
        label.setWrapText(true);
        group.getChildren().addAll(relationshipRectangle, label);
        root.getChildren().add(group);
        initializeRelationshipEvents();
    }
    private void initializeRelationshipEvents() {
        label.widthProperty().addListener((obs, oldValue, newValue) -> {
            relationshipRectangle.setWidth(newValue.doubleValue());
            relationshipRectangle.setHeight(newValue.doubleValue());
        });
        label.heightProperty().addListener((obs, oldValue, newValue) -> {
            double h = relationshipRectangle.localToScene(relationshipRectangle.getLayoutBounds()).getHeight();
            double w = relationshipRectangle.localToScene(relationshipRectangle.getLayoutBounds()).getWidth();
            double diagonal = Math.sqrt(Math.pow(h, 2) + Math.pow(w, 2));
            label.setLayoutY(diagonal / 4 - label.getHeight() / 2);
        });
        group.setOnMouseDragged(e -> {
            if(e.getButton().compareTo(MouseButton.PRIMARY) == 0)
                group.relocate(
                        e.getSceneX()
                                - root.localToScene(root.getLayoutBounds()).getMinX()
                                - group.localToScene(group.getLayoutBounds()).getWidth() / 2
                        , e.getSceneY()
                                - root.localToScene(root.getLayoutBounds()).getMinY()
                                - group.localToScene(group.getLayoutBounds()).getHeight() / 2);
        });
        group.setOnMouseClicked(e -> {
            if(e.getButton().compareTo(MouseButton.PRIMARY) == 0 && e.isMetaDown()) {
                System.out.println(e);
                CardinalityER c = new CardinalityER(root, erd);
                relationship.addCardinality(c.cardinality);
                c.setRelationship(this);
                ChronoEvents.getInstance().getEvents().add(this);
                ListChangeListener listener = (event) -> {
                    ChronoEvents.getInstance().getLast().ifPresent(x -> {
                        if(x instanceof EntityER) {
                            c.setEntity((EntityER) x);
                        }
                        ChronoEvents.getInstance().getEvents().stream().forEach(System.out::println);
                    });
                };
                ChronoEvents.getInstance().getEvents().addListener(listener);
                ChronoEvents.getInstance().getEvents().addListener((ListChangeListener) event -> {
                    ChronoEvents.getInstance().getEvents().removeListener(listener);
                });
            }
        });
        /*
        (ListChangeListener)(event) -> {
            ChronoEvents.getInstance().getLast().ifPresent(x -> {
                if(x instanceof EntityER) {
                    ((CardinalityER) x).relationship = relationshipRectangle;
                }
            });

        }
        */
    }

    @Override
    public void drawPane() {
        relationshipRectangle.setFill(BACKGROUND);
        relationshipRectangle.setRotate(45);
        relationshipRectangle.setStroke(STROKE);
    }

    @Override
    protected void deleteChildrens() {

    }
}
