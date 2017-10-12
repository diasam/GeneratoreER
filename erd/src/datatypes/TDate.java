package datatypes;

import database.Database;

public class TDate extends DataType{
    @Override
    public String toString() {
        return getName();
    }

    public TDate() {
        super("Date");
    }
    @Override
    public void accept(Database database) {

    }
}
