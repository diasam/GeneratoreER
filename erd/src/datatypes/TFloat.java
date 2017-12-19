package datatypes;

import database.Database;

public class TFloat extends DataType implements Sizeable{
    private int size = 32;
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    public TFloat() {
        super("Float");
    }

    @Override
    public String toString() {
        return "TFloat{" +
                "size=" + size +
                '}';
    }
    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}
