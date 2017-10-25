package attributes;

import database.Database;
import datatypes.DataType;

public class NormalAttribute extends Attribute {
    public NormalAttribute(String name, DataType dataType) {
        super(name, dataType);
    }
    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
