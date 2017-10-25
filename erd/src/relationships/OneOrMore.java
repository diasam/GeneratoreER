package relationships;

import database.Database;

public class OneOrMore extends Cardinality {
    public OneOrMore() {
        super(1,MANY_CARD);
    }

    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
