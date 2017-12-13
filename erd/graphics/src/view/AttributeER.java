package view;


import attributes.Attribute;
import datatypes.*;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.Map;


public abstract class AttributeER extends DiagramElement{
    protected Group entityGroup;
    protected Attribute attribute;
    protected Ellipse ellipse;
    protected Label label;
    protected Line line;

    private ContextMenu contextMenu;
    private Menu changeType;

    private Map<String, MenuItem> attributeTypes = new HashMap<>();

    private MenuItem tBlob;
    private MenuItem tDate;
    private MenuItem tFloat;
    private MenuItem tInteger;
    private MenuItem tLongBlob;
    private MenuItem tLongText;
    private MenuItem tMediumBlob;
    private MenuItem tText;
    private MenuItem tTinyInt;
    private MenuItem tVarchar;

    private MenuItem changeSize;

    private MenuItem changeName;
    private MenuItem delete;
    private TextInputDialog textInputDialog;

    private TextInputDialog numberInputDialog;
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
        changeName = new MenuItem("Change name");
        changeType = new Menu("Change data type");
        numberInputDialog = new TextInputDialog();

        tInteger = new MenuItem("Integer");
        tFloat = new MenuItem("Float");
        tTinyInt = new MenuItem("Tiny int/Boolean");
        tVarchar = new MenuItem("Varchar");
        tText = new MenuItem("Text");
        tLongText = new MenuItem("Long text");
        tDate = new MenuItem("Date");
        tBlob = new MenuItem("Blob");
        tLongBlob = new MenuItem("Long blob");
        tMediumBlob = new MenuItem("Medium blob");

        changeSize = new MenuItem("Change attribute size");

        delete = new MenuItem("Delete attribute");

        changeType.getItems().addAll(tInteger, tFloat, tTinyInt, tVarchar, tText, tLongText, tDate, tBlob, tLongBlob, tMediumBlob);
        contextMenu.getItems().addAll(changeName, changeType, changeSize, delete);


        initializeMenuEvents();
    }
    private void initializeMenuEvents() {
        textInputDialog = new TextInputDialog(attribute.getName());
        textInputDialog.setTitle("Change name");
        textInputDialog.setContentText("Please, insert a new name");

        numberInputDialog.setTitle("Change size of the attribute");
        numberInputDialog.setContentText("Please, insert a new size for the attribute");

        changeName.setOnAction(e -> {
            textInputDialog.showAndWait()
                    .ifPresent(name -> label.setText(name));
        });
        changeSize.setOnAction(e -> numberInputDialog.showAndWait().ifPresent(number -> {
            int n = 32;
            try {
                n = Integer.parseInt(number);
            }
            catch (NumberFormatException exception) {}
            ((Sizeable) attribute.getDataType()).setSize(n);
        }));
        tInteger.setOnAction(e -> attribute.setDataType(new TInteger()));
        tFloat.setOnAction(e -> attribute.setDataType(new TFloat()));
        tTinyInt.setOnAction(e -> attribute.setDataType(new TTinyInt()));
        tVarchar.setOnAction(e -> attribute.setDataType(new TVarchar()));
        tText.setOnAction(e -> attribute.setDataType(new TText()));
        tLongText.setOnAction(e -> attribute.setDataType(new TLongText()));
        tDate.setOnAction(e -> attribute.setDataType(new TDate()));
        tBlob.setOnAction(e -> attribute.setDataType(new TBlob()));
        tLongBlob.setOnAction(e -> attribute.setDataType(new TLongBlob()));
        tMediumBlob.setOnAction(e -> attribute.setDataType(new TMediumBlob()));
        group.setOnContextMenuRequested(event -> {
            changeSize.setDisable(!(attribute.getDataType() instanceof Sizeable));
            contextMenu.show(group, event.getScreenX(), event.getScreenY());
        });
        delete.setOnAction(e -> deleteChildren());
    }

    @Override
    protected void deleteChildren() {
        attribute.remove();
        children.forEach(x -> x.deleteChildren());
        root.getChildren().removeAll(group, line);
    }

}
