package relationships;

import database.Database;

public class One extends Cardinality {
    public One() {
        super(0,1);
    }

    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
