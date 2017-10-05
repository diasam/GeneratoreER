package datatypes;

public class TDate extends DataType{
    @Override
    public String toString() {
        return getName();
    }

    public TDate() {
        super("Date");
    }

}
