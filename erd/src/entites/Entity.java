package entites;

import attributes.Attribute;
import attributes.ForeignKey;
import database.Database;
import database.Generable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Entity extends Observable implements Table, Generable {
    private String name;
    private final List<Attribute> normalAttributes;
    private final List<Attribute> primaryKeys;
    private final List<Table> dependencies;
    public Entity() {
        this("Undefined", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Entity(String name, List<Attribute> normalAttributes, List<Attribute> primaryKeys, List<Table> dependencies) {
        this.name = name;
        this.normalAttributes = normalAttributes;
        this.primaryKeys = primaryKeys;
        this.dependencies = dependencies;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
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
    public void addDependency(Table table) {
        if(!dependencies.contains(table)) {
            dependencies.add(table);
            //dependencies.forEach(normalAttributes::add);
            changed();
        }
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
        List<Attribute> fks = new ArrayList<>();
        dependencies.forEach((entity) -> entity.getPrimaryKeys()
                        .forEach(primaryKey -> fks.add(new ForeignKey(primaryKey, entity, this))));
        return fks;
    }

    public List<Table> getDependencies() {
        return dependencies;
    }
    @Override
    public String accept(Database database) {
        return database.generate(this);
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }
    public int compareTo(Table t) {
        if(t.getDependencies().size() == 0)
            return -1;
        else if(dependencies.size() == 0)
            return 1;
        else
            return 0;
    }

}