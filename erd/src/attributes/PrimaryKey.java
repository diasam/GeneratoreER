package attributes;

import database.Database;
import datatypes.DataType;

public class PrimaryKey extends Attribute {
    public PrimaryKey(String name, DataType dataType) {
        super(name, dataType);
    }
    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}
