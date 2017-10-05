package attributes;
import datatypes.DataType;

import java.util.Observable;

public abstract class Attribute {
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
}
