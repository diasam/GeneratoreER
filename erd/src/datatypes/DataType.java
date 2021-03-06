package datatypes;

import database.Database;
import database.Generable;

public abstract class DataType implements Generable {
    /**
     * Nome del tipo di dato
     */
    private final String name;
    public DataType(String name) {
        this.name = name;
    }

    @Override
    public abstract String toString();

    public String getName() {
        return name;
    }

    @Override
    public abstract String accept(Database database);
}
