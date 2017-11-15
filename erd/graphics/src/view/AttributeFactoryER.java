package view;

import attributes.NormalAttribute;
import attributes.PrimaryKey;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import javafx.scene.shape.Rectangle;

public class AttributeFactoryER {
    public static AttributeER create(Pane root, Group entityGroup, NormalAttribute attribute) {
        return new NormalAttributeER(root, entityGroup, attribute);
    }
    public static  AttributeER create(Pane root, Group entityGroup, PrimaryKey attribute) {
        return new PrimaryKeyER(root, entityGroup, attribute);
    }
}
