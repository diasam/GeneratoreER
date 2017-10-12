package relationships;

import database.Database;

public class Many extends Cardinality {
    public Many() {
        super(0,MANY_CARD);
    }

    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}
