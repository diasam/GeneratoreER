package view;

import attributes.Attribute;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.TInteger;
import entites.Entity;
import entites.Table;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityER extends DiagramElement {

    private Table entity;
    //private Rectangle background;
    private Rectangle entityRect;
    private ContextMenu contextMenu;
    private List<AttributeER> attributes;
    public EntityER(Pane pane, Pane root, Table entity) {
        super(pane, root);
        this.entity = entity;
        //entity.getNormalAttributes().stream().reduce((x) -> new ArrayList<>(), List::); //.reduce(new ArrayList<DiagramElement>(), Collections.)
        //attributes = Stream.concat(entity.getNormalAttributes().stream(), entity.getPrimaryKeys().stream()).map().collect(Collectors.toList());
        //attributes = Stream.concat(entity.getNormalAttributes().stream(), entity.getPrimaryKeys().stream()).collect(Collectors.toList());
        //attributes = Stream.concat(entity.getNormalAttributes().stream(), entity.getPrimaryKeys().stream()).map((x) -> AttributeFactoryER.create(pane, root, x));
        //Attribute a = new PrimaryKey(new TInteger());

        drawPane();
        pane.setOnMouseDragged(e -> {
            pane.relocate(
                    e.getSceneX() - root.localToScene(root.getLayoutBounds()).getMinX() - pane.getWidth() / 2,
                    e.getSceneY() - root.localToScene(root.getLayoutBounds()).getMinY() - pane.getHeight() / 2);

            //System.out.println("Node " + "\n\tx:\t " + e.getX() + "\n\ty:\t" + e.getY());
            //System.out.println("Screen " + "\n\tx:\t " + e.getScreenX() + "\n\ty:\t" + e.getScreenY());
            //System.out.println("Scene " + "\n\tx:\t " + e.getSceneY() + "\n\ty:\t" + e.getSceneY());
            //System.out.println(root.localToScene(root.getLayoutBounds()).getMinX());
            //System.out.println(root.localToScene(root.getLayoutBounds()).getMinY());
            //System.out.println(pane.localToScene(pane.getLayoutBounds()).getMinX());
        });
        pane.setTranslateZ(0);

    }

    public void drawPane() {
        Group g = new Group();
        contextMenu = new ContextMenu();
        Menu attributes = new Menu("Add Attribute");
        MenuItem pk = new MenuItem("Primary key");
        MenuItem att = new MenuItem("Attribute");
        attributes.getItems().addAll(att, pk);
        contextMenu.getItems().addAll(attributes);
        pk.setOnAction(event -> {
            PrimaryKey primaryKey = new PrimaryKey(new TInteger());
            entity.addPrimaryKey(primaryKey);
            AttributeFactoryER.create(pane,primaryKey);
            entity.getPrimaryKeys().stream().map(x -> x.getName()).forEach(System.out::println);
            //System.out.println(pane.getLayoutX());
        });
        att.setOnAction(event -> {
            entity.addNormalAttribute(new NormalAttribute(new TInteger()));
            //root.getChildren().add(new NormalAttributeER(pane, root, new Pane(), entity.getNormalAttributes().stream().reduce((first, second) -> second).orElse(new NormalAttribute(new TInteger()))));
            //entity.getNormalAttributes().stream().map(x -> x.getName()).forEach(System.out::println);
        });



        background = new Rectangle(W,H);
        background.setFill(Color.WHITE);
        pane.getChildren().add(background);

        entityRect = new Rectangle(pane.getWidth(), pane.getHeight()/2);
        entityRect.setFill(Color.WHITE);
        entityRect.setStroke(Color.BLACK);
        pane.getChildren().add(entityRect);

        entityRect.setOnContextMenuRequested(event -> contextMenu.show(entityRect, event.getScreenX(), event.getScreenY()));

        TextField l = new TextField(entity.getName());
        //l.setBackground(Color.TRANSPARENT);

        //l.setBackground(Color.TRANSPARENT);
        g.getChildren().add(l);
        pane.getChildren().add(g);
    }

    public EntityER(Pane pane, Pane root) {
        this(pane, root, new Entity());
    }

    public Table getEntity() {
        return entity;
    }

    public void setEntity(Table entity) {
        this.entity = entity;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
}
