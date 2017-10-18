package datatypes;

import database.Database;

public class TInteger extends DataType implements Sizeable{
    private int size = 32;
    public TInteger() {
        super("Integer");
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Integer("+size+")";
    }
    @Override
    public void accept(Database database) {
        database.generate(this);
    }
}

