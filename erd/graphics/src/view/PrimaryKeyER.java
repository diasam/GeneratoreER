package view;

import attributes.PrimaryKey;
import javafx.scene.layout.Pane;

public class PrimaryKeyER extends AttributeER {
    public PrimaryKeyER(Pane pane, Pane entityPane, PrimaryKey attribute) {
        super(pane, entityPane, attribute);
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