package relationships;

import database.Database;

public class OneOrMore extends Cardinality {
    public OneOrMore() {
        super(1,MANY_CARD);
    }

    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}
