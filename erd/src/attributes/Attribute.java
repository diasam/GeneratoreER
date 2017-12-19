package attributes;
import database.Database;
import database.Generable;
import datatypes.DataType;
import entites.Table;

import java.util.Optional;

public abstract class Attribute implements Generable {
    private String name;
    private DataType dataType;
    private Table table;
    public Attribute(String name, DataType dataType, Table table) {
        this.name = name;
        this.dataType = dataType;
        this.table = table;
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

    public void remove() {
        if(table != null) {
            table.getNormalAttributes().remove(this);
            table.getPrimaryKeys().remove(this);
        }

    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
