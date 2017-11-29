package view;


import attributes.Attribute;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.TInteger;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Optional;


public abstract class AttributeER extends DiagramElement{
    protected Group entityGroup;
    protected Attribute attribute;
    protected Ellipse ellipse;
    protected Label label;
    protected Line line;

    private ContextMenu contextMenu;
    private MenuItem changeName;
    private TextInputDialog textInputDialog;
    public static final double PADDING = 20.0;
    public AttributeER(Pane root, Group entityGroup, Attribute attribute) {
        super(root);
        this.entityGroup = entityGroup;
        this.attribute = attribute;
        initializeAttribute();
        initializeMenu();
        drawPane();
        entityGroup.toFront();
    }
    private void initializeAttribute() {
        ellipse = new Ellipse();
        label = new Label(attribute.getName());
        label.setWrapText(true);
        line = new Line(group.getLayoutX() + group.localToScene(group.getLayoutBounds()).getWidth() / 2
                ,group.getLayoutY() + group.localToScene(group.getLayoutBounds()).getHeight() / 2
                ,entityGroup.getLayoutX() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getWidth() / 2
                ,entityGroup.getLayoutY() + entityGroup.localToScene(entityGroup.getLayoutBounds()).getHeight() / 2);

        group.getChildren().addAll(ellipse, label);
        root.getChildren().addAll(line,group);
        initializeAttributeEvents();
    }
    private void initializeAttributeEvents() {
        label.textProperty().addListener((obs, oldVal, newVal) -> {
            attribute.setName(newVal );
        });
        group.setOnMouseDragged((e) -> {
            group.relocate(
                    e.getSceneX() - root.localToScene(root.getLayoutBounds()).getMinX()
                            - group.localToScene(group.getLayoutBounds()).getWidth() / 2,
                    e.getSceneY()
                            - root.localToScene(root.getLayoutBounds()).getMinY()
                            - group.localToScene(group.getLayoutBounds()).getHeight() / 2);
        });

        group.layoutXProperty().addListener((obs, oldVal, newVal) -> {
            line.setStartX(newVal.doubleValue()
                    + label.localToScene(label.getLayoutBounds()).getWidth() / 2);

        });
        group.layoutYProperty().addListener((obs, oldVal, newVal) -> {
            line.setStartY(newVal.doubleValue()
                    + label.localToScene(label.getLayoutBounds()).getHeight() / 2);
        });
        label.widthProperty().addListener((obs, oldVal, newVal) -> {
            line.setStartX(label.localToScene(label.getLayoutBounds()).getMinX()
                    - root.localToScene(root.getLayoutBounds()).getMinX() + newVal.doubleValue() / 2);
            ellipse.setRadiusX((newVal.doubleValue()
                    + PADDING) / 2);
            ellipse.setCenterX(label.localToScene(label.getLayoutBounds()).getWidth() / 2);
        });
        label.heightProperty().addListener((obs, oldVal, newVal) -> {
            line.setStartY(label.localToScene(label.getLayoutBounds()).getMinY()
                    - root.localToScene(root.getLayoutBounds()).getMinY() + newVal.doubleValue() / 2);
            ellipse.setRadiusY((newVal.doubleValue() + PADDING) / 2);
            ellipse.setCenterY(label.localToScene(label.getLayoutBounds()).getHeight() / 2);
        });
        label.layoutXProperty().addListener((obs, oldVal, newVal) -> {
            ellipse.setCenterX(newVal.doubleValue() / 2);
        });
        label.layoutYProperty().addListener((obs, oldVal, newVal) -> {
            ellipse.setCenterY(label.localToScene(label.getLayoutBounds()).getHeight()/2);
        });
        entityGroup.layoutXProperty().addListener((e) -> {
            line.setEndX(entityGroup.getLayoutX()
                    + entityGroup.localToScene(entityGroup.getLayoutBounds()).getWidth() / 2);
        });
        entityGroup.layoutYProperty().addListener((e) -> {
            line.setEndY(entityGroup.getLayoutY()
                    + entityGroup.localToScene(entityGroup.getLayoutBounds()).getHeight() / 2);
        });
    }
    @Override
    public void drawPane() {
        group.toBack();
        entityGroup.toFront();
        group.relocate(Math.abs(entityGroup.getLayoutX() + 50), Math.abs(entityGroup.getLayoutY() - 50));
        ellipse.setFill(Color.WHITE);
        ellipse.setStroke(Color.BLACK);
        line.toBack();
    }
    private void initializeMenu() {
        contextMenu = new ContextMenu();
        changeName = new MenuItem("changeName");
        contextMenu.getItems().add(changeName);
        initializeMenuEvents();
    }
    private void initializeMenuEvents() {
        textInputDialog = new TextInputDialog(attribute.getName());
        textInputDialog.setTitle("Change name");
        textInputDialog.setContentText("Please, insert a new name");
        changeName.setOnAction(e -> {
            Optional<String> result = textInputDialog.showAndWait();
            result.ifPresent(name -> label.setText(name));
        });
        group.setOnContextMenuRequested(event -> contextMenu.show(group, event.getScreenX(), event.getScreenY()));
    }

    @Override
    protected void deleteChildrens() {
        children.forEach(x -> x.deleteChildrens());
        root.getChildren().removeAll(group, line);
    }

}
