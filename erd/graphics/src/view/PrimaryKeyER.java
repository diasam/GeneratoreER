package view;

import attributes.PrimaryKey;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class PrimaryKeyER extends AttributeER {
    public PrimaryKeyER(Pane root, Group entityGroup, PrimaryKey attribute) {
        super(root, entityGroup, attribute);
    }

    @Override
    public void drawPane() {
        super.drawPane();
        label.setUnderline(true);
    }

    @Override
    public void accept(AttributeFactoryER attributeFactoryER) {

    }
}