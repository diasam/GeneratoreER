package relationships;

import database.Database;
import entites.Entity;

public class OnlyOne extends Cardinality {
    public OnlyOne() {
        super(1,1);
    }

    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
