package entites;

import attributes.Attribute;
import attributes.ForeignKey;
import attributes.PrimaryKey;

import java.util.List;

public interface Table {
    public List<Attribute> getNormalAttributes();
    public List<Attribute> getPrimaryKeys();
    public List<Attribute> getForeignKeys();
    public void addNormalAttribute(Attribute normalAttribute);
    public void addPrimaryKey(Attribute pk);
    public void addForeignKey(Attribute fk);


}
