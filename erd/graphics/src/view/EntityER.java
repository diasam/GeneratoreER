package view;

import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.TInteger;
import entites.Entity;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Erd;

import java.util.ArrayList;
import java.util.Optional;

public class EntityER extends DiagramElement {
    protected final AttributeFactoryER attributesFactory = new AttributeFactoryER();
    protected final ArrayList<AttributeER> attributesEr = attributesFactory.getAttributes();
    protected Entity entity;
    protected Rectangle entityRect;
    protected Label entityName;
    protected ContextMenu contextMenu;
    protected Menu attributes;
    protected MenuItem pk;
    protected MenuItem att;
    protected MenuItem changeName;
    protected MenuItem delete;
    protected TextInputDialog textInputDialog;
    protected static final int WIDTH = 100;
    protected static final int HEIGHT = 50;
    protected static final Color BACKGROUND = Color.WHITE;
    public EntityER() {
        super();
    }

    public EntityER(Pane root, Erd erd) {
        this(root
                , new Entity()
                , erd);
    }
    public EntityER(Pane root
            , Entity entity
            , Erd erd) {
        super(root, erd);
        this.entity = entity;
        erd.addTable(entity);
        initializeEntity();
        initializeMenu();
        drawPane();
    }
    private void initializeEntity() {
        entityRect = new Rectangle(WIDTH, HEIGHT);
        entityName = new Label(entity.getName());
        entityName.setWrapText(true);
        group.getChildren().addAll(entityRect, entityName);
        root.getChildren().add(group);
        initializeEntityEvents();
    }
    private void initializeEntityEvents() {
        entityName.textProperty().addListener((obs, oldVal, newVal) -> {
            entity.setName(newVal);
            //System.out.println(entity.getName());
        });
        entityName.widthProperty().addListener((obs, oldVal, newVal) -> {
            if(entityRect.getWidth() < newVal.doubleValue())
                entityRect.setWidth(newVal.doubleValue());
            entityName.setLayoutX(entityRect.getWidth() / 2 - newVal.doubleValue() / 2 );

        });
        entityName.heightProperty().addListener((obs, oldVal, newVal) -> {
            if(entityRect.getHeight() < newVal.doubleValue())
                entityRect.setHeight(newVal.doubleValue());
            entityName.setLayoutY(entityRect.getHeight() / 2 - newVal.doubleValue() / 2 );
        });
        group.setOnMouseDragged(e -> {
            if(e.getButton().compareTo(MouseButton.PRIMARY) == 0)
                group.relocate(
                        e.getSceneX() - root.localToScene(root.getLayoutBounds()).getMinX()
                                - entityRect.getWidth() / 2,
                        e.getSceneY() - root.localToScene(root.getLayoutBounds()).getMinY()
                                - entityRect.getHeight() / 2);
        });
        group.setOnMouseClicked(e -> {
            if(e.getButton().compareTo(MouseButton.PRIMARY) == 0 && e.isShiftDown()) {
                ChronoEvents.getInstance().getEvents().add(this);
                ChronoEvents.getInstance().getEvents().remove(this);
            }
        });
    }
    private void initializeMenu() {
        contextMenu = new ContextMenu();
        attributes = new Menu("Add Attribute");
        pk = new MenuItem("Primary key");
        att = new MenuItem("Attribute");
        changeName = new MenuItem("Change entity name");
        delete = new MenuItem("Delete");
        textInputDialog = new TextInputDialog(entity.getName());
        textInputDialog.setTitle("Change name");
        textInputDialog.setContentText("Please, insert a new name");
        attributes.getItems().addAll(att, pk);
        contextMenu.getItems().addAll(attributes, changeName, delete);
        initializeMenuEvents();
    }

    private void initializeMenuEvents() {
        pk.setOnAction(event -> {
            PrimaryKey primaryKey = new PrimaryKey(new TInteger(), entity);
            entity.addPrimaryKey(primaryKey);
            attributesFactory.create(root
                    , group
                    , primaryKey);
            addLastFromFactory();
        });
        att.setOnAction(event -> {
            NormalAttribute na = new NormalAttribute(new TInteger(), entity);
            entity.addNormalAttribute(na);
            attributesFactory.create(root
                    , group
                    , na);
            addLastFromFactory();
        });
        changeName.setOnAction(e -> {
            Optional<String> result = textInputDialog.showAndWait();
            result.ifPresent(name -> entityName.setText(name));
        });
        delete.setOnAction((ActionEvent event) -> {
            deleteChildren();
        });
        group.setOnContextMenuRequested(event -> {
            contextMenu.show(entityRect, event.getScreenX(), event.getScreenY());
        });
    }
    private void addLastFromFactory() {
        //children.add(attributesFactory.getAttributes().get(attributesFactory.getAttributes().size()-1));
        children.add(attributesFactory.getLast());
    }
    public void drawPane() {
        entityRect.setFill(BACKGROUND);
        entityRect.setStroke(Color.BLACK);
    }


    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    protected void deleteChildren() {
        children.forEach(x -> x.deleteChildren());
        root.getChildren().remove(group);
        erd.getTables().remove(entity);
    }
}
