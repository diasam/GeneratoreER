package view;


import attributes.NormalAttribute;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class NormalAttributeER extends AttributeER {
    public NormalAttributeER(Pane root, Group entityGroup, NormalAttribute attribute) {
        super(root, entityGroup, attribute);
    }

    @Override
    public void accept(AttributeFactoryER attributeFactoryER) {

    }
}
