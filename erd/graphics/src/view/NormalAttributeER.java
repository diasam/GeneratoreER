package view;


import attributes.NormalAttribute;
import javafx.scene.layout.Pane;

public class NormalAttributeER extends AttributeER {
    public NormalAttributeER(Pane pane, Pane entityPane, NormalAttribute attribute) {
        super(pane, entityPane, attribute);
    }

    @Override
    public void accept(AttributeFactoryER attributeFactoryER) {

    }
}
