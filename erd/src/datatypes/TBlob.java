package datatypes;

public class TBlob extends DataType {
    public TBlob() {
        super("Blob");
    }

    @Override
    public String toString() {
        return getName();
    }
}
