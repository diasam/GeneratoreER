package attributes;

import database.Database;
import datatypes.DataType;

public class PrimaryKey extends Attribute {
    public PrimaryKey(String name, DataType dataType) {
        super(name, dataType);
    }
    public PrimaryKey(DataType dataType) {
        super("undefined_primary_key", dataType);
    }
    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
