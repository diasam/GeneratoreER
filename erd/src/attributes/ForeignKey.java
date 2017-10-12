package attributes;

import database.Database;
import datatypes.DataType;
import entites.Table;

public class ForeignKey extends Attribute {
    private Table reference;
    public ForeignKey(String name, DataType dataType) {
        super(name, dataType);
    }
    public ForeignKey(Attribute pk, Table reference) {
        this(pk.getName(), pk.getDataType());
        this.reference = reference;
    }

    public Table getReference() {
        return reference;
    }

    public void setReference(Table reference) {
        this.reference = reference;
    }

    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}
