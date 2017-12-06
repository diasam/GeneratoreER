package view;

import attributes.Attribute;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class AttributeFactoryER {
    private final ArrayList<AttributeER> attributes = new ArrayList<>();
    public void create(Pane root, Group entityGroup, NormalAttribute attribute) {
        attributes.add(new NormalAttributeER(root, entityGroup, attribute));
    }
    public void create(Pane root, Group entityGroup, PrimaryKey attribute) {
        attributes.add(new PrimaryKeyER(root, entityGroup, attribute));
    }
    public ArrayList<AttributeER> getAttributes() {
        return attributes;
    }
    public AttributeER getLast() {
        return attributes.get(attributes.size()-1);
    }
}
