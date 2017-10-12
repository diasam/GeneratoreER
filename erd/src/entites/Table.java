package entites;

import attributes.Attribute;
import attributes.ForeignKey;
import attributes.PrimaryKey;
import database.Visitable;

import java.util.List;

public interface Table extends Comparable<Table>, Visitable {
    public String getName();
    public List<Attribute> getNormalAttributes();
    public List<Attribute> getPrimaryKeys();
    public List<Attribute> getForeignKeys();
    public List<Table> getDependencies();
    public void addNormalAttribute(Attribute normalAttribute);
    public void addPrimaryKey(Attribute pk);
    public void addDependency(Table table);
}
