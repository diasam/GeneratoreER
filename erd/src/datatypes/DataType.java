package datatypes;

public abstract class DataType {
    private final String name;
    public DataType(String name) {
        this.name = name;
    }

    @Override
    public abstract String toString();//TODO Finire di implementare nelle sottoclassi il metodo toString

    public String getName() {
        return name;
    }

}
