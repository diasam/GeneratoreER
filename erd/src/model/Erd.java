package model;

import database.Database;
import database.Generable;
import entites.Table;
import relationships.Relationship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Erd implements Generable {
    public String name;
    private final List<Table> tables;
    private final List<Relationship> relationships;
    private final List<Table> relationshipTables;
    public Erd(String name) {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), name);
    }
    public Erd() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "undefined");
    }

    public Erd(List<Table> tables, List<Relationship> relationships, List<Table> relationshipTables, String name) {
        this.tables = tables;
        this.relationships = relationships;
        this.relationshipTables = relationshipTables;
        this.name = name;
    }

    public List<Table> getTables() {
        orderEntities();
        return tables;
    }

    private void orderEntities() {
        Table e;
        for(int i = 0; i < tables.size(); i++) {
            e = tables.get(i);
            for(int j = i + 1; j < tables.size(); j++) {
                if(e.getDependencies().contains(tables.get(j))) {
                    Collections.swap(tables, i, j);
                }
            }
        }

    }

    public List<Relationship> getRelationships() {
        relationships.forEach(relationship -> relationship.getLinks().forEach(cardinality -> System.out.println(cardinality.getEntity())));
        return relationships;
    }


    public List<Table> getRelationshipTables() {
        return relationshipTables;
    }

    public void addTable(Table table) {
        tables.add(table);
    }
    public void addRelationship(Relationship relationship) {
        relationships.add(relationship);
    }
    public void addRelationshipTables(Table table) {
        relationshipTables.add(table);
    }

    @Override
    public String accept(Database database) {
        return database.generate(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
