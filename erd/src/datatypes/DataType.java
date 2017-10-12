package datatypes;

import database.Database;
import database.Visitable;

public abstract class DataType implements Visitable{
    private final String name;
    public DataType(String name) {
        this.name = name;
    }

    @Override
    public abstract String toString();//TODO Finire di implementare nelle sottoclassi il metodo toString

    public String getName() {
        return name;
    }

    @Override
    public abstract void accept(Database database);
}
