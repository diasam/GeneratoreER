package attributes;
import database.Database;
import database.Visitable;
import datatypes.DataType;
import datatypes.TInteger;

import java.util.Observable;

public abstract class Attribute implements Visitable {
    private String name;
    private DataType dataType;

    public Attribute(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", dataType=" + dataType;
    }

    @Override
    public abstract String accept(Database database);
    public static void main(String[] args) {
        Attribute pk = new PrimaryKey("abc", new TInteger());
        System.out.println((ForeignKey) pk);
    }


}
