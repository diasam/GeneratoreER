package entites;

import attributes.Attribute;
import attributes.ForeignKey;
import attributes.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Entity extends Observable implements Table{
    private String name;
    private final List<Attribute> normalAttributes;
    private final List<Attribute> primaryKeys; //TODO aggiornare uml
    private final List<Attribute> foreignKeys; //TODO aggiornare uml

    public Entity() {
        this("Undefined", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Entity(String name, List<Attribute> normalAttributes, List<Attribute> primaryKeys, List<Attribute> foreignKeys) {
        this.name = name;
        this.normalAttributes = normalAttributes;
        this.primaryKeys = primaryKeys;
        this.foreignKeys = foreignKeys;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addNormalAttribute(Attribute attribute) {
        normalAttributes.add(attribute);
        changed();
    }
    @Override
    public void addPrimaryKey(Attribute pk) {
        primaryKeys.add(pk);
        changed();
    }
    @Override
    public void addForeignKey(Attribute fk) {
        foreignKeys.add(fk);
        changed();
    }
    @Override
    public List<Attribute> getNormalAttributes() {
        return normalAttributes;
    }
    @Override
    public List<Attribute> getPrimaryKeys() {
        return primaryKeys;
    }
    @Override
    public List<Attribute> getForeignKeys() {
        return foreignKeys;
    }

    private void changed() {
        setChanged();
        notifyObservers();
    }




}