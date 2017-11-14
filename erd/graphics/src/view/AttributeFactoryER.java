package view;

import attributes.NormalAttribute;
import attributes.PrimaryKey;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class AttributeFactoryER {
    public static AttributeER create(Pane entityPane, NormalAttribute attribute) {
        return new NormalAttributeER(new Pane(), entityPane, attribute);
    }
    public static  AttributeER create(Pane entityPane, PrimaryKey attribute) {
        return new PrimaryKeyER(new Pane(), entityPane, attribute);
    }
}
