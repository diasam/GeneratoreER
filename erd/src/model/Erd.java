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
    private final List<Table> entities;
    private final List<Relationship> relationships;
    private final List<Table> relationshipTables;
    public Erd(String name) {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), name);
    }
    public Erd() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "undefined");
    }

    public Erd(List<Table> entities, List<Relationship> relationships, List<Table> relationshipTables, String name) {
        this.entities = entities;
        this.relationships = relationships;
        this.relationshipTables = relationshipTables;
        this.name = name;
    }

    public List<Table> getEntities() {
        orderEntities();
        return entities;
    }

    private void orderEntities() {
        Table e;
        for(int i = 0; i < entities.size(); i++) {
            e = entities.get(i);
            for(int j = i + 1; j < entities.size(); j++) {
                if(e.getDependencies().contains(entities.get(j))) {
                    Collections.swap(entities, i, j);
                }
            }
        }

    }

    public List<Relationship> getRelationships() {
        relationships.forEach(relationship -> relationship.getLinks().forEach(cardinality -> System.out.println(cardinality.getEntity())));
        return relationships;
    }


    public List<Table> getRelationshipTables() {
        System.out.print("ABC\t\t");
        relationshipTables.stream().map(Table::getPrimaryKeys).forEach(System.out::println);
        //relationshipTables.stream().map(Table::getPrimaryKeys).collect(Collectors.toList()).forEach(System.out::println);
        return relationshipTables;
    }

    public void addEntity(Table entity) {
        entities.add(entity);
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
