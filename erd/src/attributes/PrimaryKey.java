package attributes;

import database.Database;
import datatypes.DataType;
import entites.Table;

public class PrimaryKey extends Attribute {
    public PrimaryKey(String name, DataType dataType, Table table) {
        super(name, dataType, table);
    }
    public PrimaryKey(DataType dataType, Table table) {
        super("undefined_primary_key", dataType, table);
    }
    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
