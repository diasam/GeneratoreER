package relationships;

import database.Database;

public class One extends Cardinality {
    public One() {
        super(0,1);
    }

    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}
