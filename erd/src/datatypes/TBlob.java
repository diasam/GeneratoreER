package datatypes;

import database.Database;

public class TBlob extends DataType {
    public TBlob() {
        super("Blob");
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void accept(Database database) {

    }
}
