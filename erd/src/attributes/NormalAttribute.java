package attributes;

import database.Database;
import datatypes.DataType;
import entites.Table;

public class NormalAttribute extends Attribute {
    public NormalAttribute(String name, DataType dataType, Table table) {
        super(name, dataType, table);
    }
    public NormalAttribute(DataType dataType, Table table) {
        super("undefined_attribute", dataType, table);
    }
    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
