package datatypes;

import database.Database;

public class TMediumBlob extends DataType {
    public TMediumBlob() {
        super("MediumBlob");
    }

    @Override
    public String toString() {
        return "TMediumBlob{}";
    }
    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
