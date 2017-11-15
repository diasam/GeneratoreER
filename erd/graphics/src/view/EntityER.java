package view;

import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.TInteger;
import entites.Table;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Erd;

import java.util.List;

public class EntityER extends DiagramElement {

    private Table entity;
    private Rectangle entityRect;
    private ContextMenu contextMenu;
    private Label entityName;
    private Menu attributes;
    private MenuItem pk;
    private MenuItem att;

    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    public EntityER() {
        super();
    }
    public EntityER(Pane root, Table entity, Erd erd) {
        //super(pane, root);
        super(root, erd);
        this.entity = entity;

        //entity.getNormalAttributes().stream().reduce((x) -> new ArrayList<>(), List::); //.reduce(new ArrayList<DiagramElement>(), Collections.)
        //attributes = Stream.concat(entity.getNormalAttributes().stream(), entity.getPrimaryKeys().stream()).map().collect(Collectors.toList());
        //attributes = Stream.concat(entity.getNormalAttributes().stream(), entity.getPrimaryKeys().stream()).collect(Collectors.toList());
        //attributes = Stream.concat(entity.getNormalAttributes().stream(), entity.getPrimaryKeys().stream()).map((x) -> AttributeFactoryER.create(pane, root, x));

        //Attribute a = new PrimaryKey(new TInteger());


        entityRect = new Rectangle(WIDTH, HEIGHT);
        entityName = new Label(entity.getName());
        contextMenu = new ContextMenu();
        attributes = new Menu("Add Attribute");
        pk = new MenuItem("Primary key");
        att = new MenuItem("Attribute");
        attributes.getItems().addAll(att, pk);
        contextMenu.getItems().addAll(attributes);
        pk.setOnAction(event -> {
            PrimaryKey primaryKey = new PrimaryKey(new TInteger());
            entity.addPrimaryKey(primaryKey);
            //AttributeFactoryER.create(root, background, primaryKey);
            AttributeFactoryER.create(root, group, primaryKey);
            entity.getPrimaryKeys().stream().map(x -> x.getName()).forEach(System.out::println);
            //System.out.println(pane.getLayoutX());
        });
        att.setOnAction(event -> {
            NormalAttribute na = new NormalAttribute(new TInteger());
            entity.addNormalAttribute(na);
            AttributeFactoryER.create(root, group, na);
            //root.getChildren().add(new NormalAttributeER(pane, root, new Pane(), entity.getNormalAttributes().stream().reduce((first, second) -> second).orElse(new NormalAttribute(new TInteger()))));
            //entity.getNormalAttributes().stream().map(x -> x.getName()).forEach(System.out::println);
        });
        group.setOnContextMenuRequested(event -> contextMenu.show(entityRect, event.getScreenX(), event.getScreenY()));

        drawPane();
        group.setOnMouseDragged(e -> {
            group.relocate(
                    e.getSceneX() - root.localToScene(root.getLayoutBounds()).getMinX() - entityRect.getWidth() / 2,
                    e.getSceneY() - root.localToScene(root.getLayoutBounds()).getMinY() - entityRect.getHeight() / 2);

            /*entityName.relocate(e.getSceneX() - root.localToScene(root.getLayoutBounds()).getMinX() - background.getWidth() / 2,
                    e.getSceneY() - root.localToScene(root.getLayoutBounds()).getMinY() - background.getHeight() / 2);*/
            //System.out.println("Node " + "\n\tx:\t " + e.getX() + "\n\ty:\t" + e.getY());
            //System.out.println("Screen " + "\n\tx:\t " + e.getScreenX() + "\n\ty:\t" + e.getScreenY());
            //System.out.println("Scene " + "\n\tx:\t " + e.getSceneY() + "\n\ty:\t" + e.getSceneY());
            //System.out.println(root.localToScene(root.getLayoutBounds()).getMinX());
            //System.out.println(root.localToScene(root.getLayoutBounds()).getMinY());
            //System.out.println(pane.localToScene(pane.getLayoutBounds()).getMinX());
        });
        stage.setOnShown((e) -> {
            entityName.relocate(entityName.getWidth() / 3, group.localToScene(group.getLayoutBounds()).getHeight() / 2 - entityName.getHeight() / 2);
        });
        new Thread(() -> {
            try {
                Thread.sleep(25);
                entityName.relocate(entityName.getWidth() / 3, group.localToScene(group.getLayoutBounds()).getHeight() / 2 - entityName.getHeight() / 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    public void drawPane() {
        /*
        contextMenu = new ContextMenu();
        Menu attributes = new Menu("Add Attribute");
        MenuItem pk = new MenuItem("Primary key");
        MenuItem att = new MenuItem("Attribute");
        attributes.getItems().addAll(att, pk);
        contextMenu.getItems().addAll(attributes);
        pk.setOnAction(event -> {
            PrimaryKey primaryKey = new PrimaryKey(new TInteger());
            entity.addPrimaryKey(primaryKey);
            //AttributeFactoryER.create(root, background, primaryKey);
            AttributeFactoryER.create(root, group, primaryKey);
            entity.getPrimaryKeys().stream().map(x -> x.getName()).forEach(System.out::println);
            //System.out.println(pane.getLayoutX());
        });
        att.setOnAction(event -> {
            NormalAttribute na = new NormalAttribute(new TInteger());
            entity.addNormalAttribute(na);
            AttributeFactoryER.create(root, group, na);
            //root.getChildren().add(new NormalAttributeER(pane, root, new Pane(), entity.getNormalAttributes().stream().reduce((first, second) -> second).orElse(new NormalAttribute(new TInteger()))));
            //entity.getNormalAttributes().stream().map(x -> x.getName()).forEach(System.out::println);
        });
        entityRect.setOnContextMenuRequested(event -> contextMenu.show(entityRect, event.getScreenX(), event.getScreenY()));
*/
        //background = new Rectangle(W,H);
        //background.setFill(Color.TRANSPARENT);
        //pane.getChildren().add(background);
        //root.getChildren().add(background);

        //entityRect = new Rectangle(background.getWidth(), background.getHeight()/2);
        entityRect.setFill(Color.TRANSPARENT);
        entityRect.setStroke(Color.BLACK);
        //entityRect.setY(group.getLayoutX() + group.localToScene(group.getLayoutBounds()).getHeight()/4 );


        //l.setBackground(Color.TRANSPARENT);
        entityName.relocate(entityRect.localToScene(entityRect.getLayoutBounds()).getMinX() + entityRect.getWidth() / 2,
                entityRect.localToScene(entityRect.getLayoutBounds()).getMinY() + entityRect.getHeight() / 2);
        //group.setAutoSizeChildren(true);
        //l.setBackground(Color.TRANSPARENT);
        //g.getChildren().add(entityName);
        group.getChildren().addAll(entityRect, entityName);
        root.getChildren().add(group);
    }


    public Table getEntity() {
        return entity;
    }

    public void setEntity(Table entity) {
        this.entity = entity;
    }

}
