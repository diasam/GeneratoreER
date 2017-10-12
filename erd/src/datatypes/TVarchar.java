package datatypes;

import database.Database;

public class TVarchar extends DataType implements Sizeable {
    private int size = 0;
    public TVarchar(){
        super("Varchar");
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getSize() {
        return this.size;
    }
    @Override
    public void accept(Database database) {

    }
}

