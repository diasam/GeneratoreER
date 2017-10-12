package datatypes;

import database.Database;

public class TLongText extends DataType {
    public TLongText() {
        super("LongBlobText");
    }

    @Override
    public String toString() {
        return "TLongText{}";
    }
    @Override
    public void accept(Database database) {

    }
}
