package relationships;

import database.Database;

public class Many extends Cardinality {
    public Many() {
        super(0,MANY_CARD);
    }

    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
