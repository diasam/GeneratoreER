package model;

import entites.Entity;
import entites.Table;
import relationships.Relationship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Erd {
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
        return entities;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }


    public List<Table> getRelationshipTables() {
        return relationshipTables;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        /*Collections.sort(entities);
        List<Table> dep = new ArrayList<>();
        Collections.copy(dep, entities);*/

    }
    public void addRelationship(Relationship relationship) {
        relationships.add(relationship);
    }
    public void addRelationshipTables(Table table) {
        relationshipTables.add(table);
    }
}
