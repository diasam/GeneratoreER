package relationships;

import database.Database;
import entites.Entity;

public class OnlyOne extends Cardinality {
    public OnlyOne() {
        super(1,1);
    }

    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}
