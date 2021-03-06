package relationships;

import database.Database;
import database.Generable;
import entites.Entity;

import java.util.Observable;
import java.util.Observer;

public abstract class Cardinality implements Observer, Generable {
    private Entity entity;
    private Relationship relationship;
    private int min;
    private int max;
    public final static int MANY_CARD = -1;
    public Cardinality(int min, int max) {
        this.max = max;
        this.min = min;
    }

    public Cardinality(Entity entity, Relationship relationship, int min, int max) {
        this.entity = entity;
        this.relationship = relationship;
        this.min = min;
        this.max = max;
        setObservers();
    }

    private void setObservers() {
        if(entity != null)
            entity.addObserver(this);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        entity.deleteObserver(this);
        this.entity = entity;
        setObservers();
        update(entity, entity);
    }

    public Relationship getRelationship() {
        return relationship;

    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
        update(entity, entity);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(relationship != null) {
            relationship.checkCardinalities();
        }
    }
    public static Cardinality copyCardinality(Cardinality from, Cardinality to) {
        to.setEntity(from.getEntity());
        to.setRelationship(from.getRelationship());

        return to;
    }
    public static Cardinality factoryByType(Cardinality c) {
        Cardinality temp;
        if(c instanceof Many) {
            temp = new Many();
        }
        else if(c instanceof OneOrMore ) {
            temp = new OneOrMore();
        }
        else if(c instanceof One) {
            temp = new One();
        }
        else {
            temp = new OnlyOne();
        }
        return temp;
    }
    @Override
    public abstract String accept(Database database);

}
