package attributes;

import database.Database;
import datatypes.DataType;

public class NormalAttribute extends Attribute {
    public NormalAttribute(String name, DataType dataType) {
        super(name, dataType);
    }
    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}
