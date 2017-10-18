package datatypes;

import database.Database;

public class TText extends DataType{
    public TText(){
        super("Text");
    }

    @Override
    public String toString() {
        return null;
    }
    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}
