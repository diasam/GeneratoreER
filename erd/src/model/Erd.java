package model;

import entites.Entity;
import entites.Table;
import relationships.Relationship;

import java.util.ArrayList;
import java.util.List;

public class Erd {
    private final List<Table> entities;
    private final List<Relationship> relationships;
    private final List<Table> relationshipTables;
    public Erd() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Erd(List<Table> entities, List<Relationship> relationships, List<Table> relationshipTables) {
        this.entities = entities;
        this.relationships = relationships;
        this.relationshipTables = relationshipTables;
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
    }
    public void addRelationship(Relationship relationship) {
        relationships.add(relationship);
    }
    public void addRelationshipTables(Table table) {
        relationshipTables.add(table);
    }
}
