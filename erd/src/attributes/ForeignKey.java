package attributes;

import database.Database;
import datatypes.DataType;
import entites.Table;

public class ForeignKey extends Attribute {
    private Table reference;
    public ForeignKey(String name, DataType dataType, Table table) {
        super(name, dataType, table);
    }
    public ForeignKey(Attribute pk, Table reference, Table table) {
        this(pk.getName(), pk.getDataType(), table);
        this.reference = reference;
    }

    public Table getReference() {
        return reference;
    }

    public void setReference(Table reference) {
        this.reference = reference;
    }

    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
