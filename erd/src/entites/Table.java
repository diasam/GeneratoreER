package entites;

import attributes.Attribute;
import database.Generable;

import java.util.List;

public interface Table extends Generable {
    String getName();
    void setName(String name);
    List<Attribute> getNormalAttributes();
    List<Attribute> getPrimaryKeys();
    List<Attribute> getForeignKeys();
    List<Table> getDependencies();
    void addNormalAttribute(Attribute normalAttribute);
    void addPrimaryKey(Attribute pk);
    void addDependency(Table table);
}
