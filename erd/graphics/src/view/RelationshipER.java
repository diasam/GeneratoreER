package view;

import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.TInteger;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Erd;
import relationships.Cardinality;
import relationships.Relationship;

import java.util.Optional;

public class RelationshipER extends DiagramElement {


    protected final AttributeFactoryER attributesFactory = new AttributeFactoryER();

    protected Relationship relationship;
    protected Rectangle relationshipRectangle;
    protected Label label;

    protected ContextMenu contextMenu;
    protected Menu attributes;
    protected MenuItem pk;
    protected MenuItem att;
    protected MenuItem changeName;

    protected MenuItem delete;
    private TextInputDialog textInputDialog;

    private ListChangeListener listener;

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
        //TODO Documentare
        erd.addRelationship(relationship);
        initializeRelationship();
        initializeMenu();
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
        // Centramento label
        label.textProperty().addListener((obs, oldVal, newVal) -> {
            relationship.setName(newVal);
        });
        label.widthProperty().addListener((obs, oldValue, newValue) -> {
            relationshipRectangle.setWidth(newValue.doubleValue());
            relationshipRectangle.setHeight(newValue.doubleValue());
            double h = relationshipRectangle.localToScene(relationshipRectangle.getLayoutBounds()).getHeight();
            double w = relationshipRectangle.localToScene(relationshipRectangle.getLayoutBounds()).getWidth();
            double diagonal = Math.sqrt(Math.pow(h, 2) + Math.pow(w, 2));
            label.setLayoutY(diagonal / 4 - label.getHeight() / 2);
        });
        label.heightProperty().addListener((obs, oldValue, newValue) -> {
            double h = relationshipRectangle.localToScene(relationshipRectangle.getLayoutBounds()).getHeight();
            double w = relationshipRectangle.localToScene(relationshipRectangle.getLayoutBounds()).getWidth();
            double diagonal = Math.sqrt(Math.pow(h, 2) + Math.pow(w, 2));
            label.setLayoutY(diagonal / 4 - label.getHeight() / 2);
        });
        // Movimento della relazione
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
        // Collegamento relazione-entità
        group.setOnMouseClicked(e -> {
            if(e.getButton().compareTo(MouseButton.PRIMARY) == 0 && e.isShiftDown()) {
                CardinalityER c = new CardinalityER(root, erd);
                children.add(c);
                //if(listener != null) {
                //    ChronoEvents.getInstance().getEvents().removeListener(listener);
                //    listener = null;
                //}
                if(relationship.addCardinality(c.cardinality)) {
                    c.setRelationship(this);
                    ChronoEvents.getInstance().getEvents().add(this);
                    listener = (event) -> {
                        event.next();
                        if(event.wasAdded()) {
                            ChronoEvents.getInstance().getLast().ifPresent(x -> {
                                if (x instanceof EntityER) {
                                    c.setEntity((EntityER) x);
                                }
                            });
                            ChronoEvents.getInstance().getEvents().removeListener(listener);
                            listener = null;
                        }


                    };
                    ChronoEvents.getInstance().getEvents().addListener(listener);
                    /*ChronoEvents.getInstance().getEvents().addListener((ListChangeListener) event -> {
                        if(listener != null) {
                            ChronoEvents.getInstance().getEvents().removeListener(listener);
                            listener = null;
                        }
                    });*/
                }
                else {
                    c.cardinality = Cardinality.factoryByType(relationship.getLinks().get(0));

                    if(relationship.addCardinality(c.cardinality)) {
                        c.setRelationship(this);
                        ChronoEvents.getInstance().getEvents().add(this);
                        listener = (event) -> {
                            event.next();
                            if(event.wasAdded()) {
                                ChronoEvents.getInstance().getLast().ifPresent(x -> {
                                    if (x instanceof EntityER) {
                                        c.setEntity((EntityER) x);
                                    }
                                });
                                ChronoEvents.getInstance().getEvents().removeListener(listener);
                            }
                        };
                        ChronoEvents.getInstance().getEvents().addListener(listener);
                        /*ChronoEvents.getInstance().getEvents().addListener((ListChangeListener) event -> {
                            if(listener != null) {
                                ChronoEvents.getInstance().getEvents().removeListener(listener);
                                listener = null;
                            }
                        });*/
                    }
                }

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
    private void initializeMenu() {
        contextMenu = new ContextMenu();
        changeName = new MenuItem("changeName");
        attributes = new Menu("Add Attribute");
        pk = new MenuItem("Primary key");
        att = new MenuItem("Attribute");
        delete = new MenuItem("Delete");
        contextMenu.getItems().addAll(attributes, changeName, delete);
        attributes.getItems().addAll(att, pk);
        initializeMenuEvents();
    }

    private void initializeMenuEvents() {
        textInputDialog = new TextInputDialog(relationship.getName());
        textInputDialog.setTitle("Change name");
        textInputDialog.setContentText("Please, insert a new name");
        changeName.setOnAction(e -> {
            textInputDialog.showAndWait()
                .ifPresent(name -> label.setText(name));
        });
        group.setOnContextMenuRequested(event -> contextMenu.show(group, event.getScreenX(), event.getScreenY()));

        pk.setOnAction(event -> {
            PrimaryKey primaryKey = new PrimaryKey(new TInteger(), relationship.getTable());
            relationship.addAttribute(primaryKey);
            attributesFactory.create(root
                    , group
                    , primaryKey);
            addLastFromFactory();
            attributesFactory.getLast().relationshipER = Optional.ofNullable(this);
        });
        att.setOnAction(event -> {
            NormalAttribute na = new NormalAttribute(new TInteger(), relationship.getTable());
            relationship.addAttribute(na);
            attributesFactory.create(root
                    , group
                    , na);
            addLastFromFactory();
            attributesFactory.getLast().relationshipER = Optional.ofNullable(this);
        });
        delete.setOnAction((ActionEvent event) -> {
            deleteChildren();
        });
        group.setOnContextMenuRequested(event -> {
            contextMenu.show(label, event.getScreenX(), event.getScreenY());
        });

    }

    @Override
    public void drawPane() {
        relationshipRectangle.setFill(BACKGROUND);
        relationshipRectangle.setRotate(45);
        relationshipRectangle.setStroke(STROKE);
    }

    @Override
    protected void deleteChildren() {
        children.forEach(x -> x.deleteChildren());
        root.getChildren().remove(group);
        erd.getRelationships().remove(relationship);

    }
    private void addLastFromFactory() {
        children.add(attributesFactory.getLast());
    }
}
