package datatypes;

import database.Database;

public class TLongBlob extends DataType {
    public TLongBlob() {
        super("LongBlob");
    }

    @Override
    public String toString() {
        return "TLongBlob{}";
    }
    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}
