package datatypes;

import database.Database;

public class TTinyInt extends DataType{
    public TTinyInt() {
        super("TinyInt");
    }

    @Override
    public String toString() {
        return null;
    }
    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
