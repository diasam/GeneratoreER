package relationships;

import attributes.Attribute;
import attributes.PrimaryKey;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import datatypes.TInteger;
import entites.Entity;
import model.Erd;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipTest {
    @org.junit.jupiter.api.Test
    void checkCardinalities() {
        checkOneMany();
        checkOneOne();
        checkManyMany();
        checkLinks();
    }
    private void checkLinks() {
        Erd erd = new Erd();
        Relationship r = new Relationship(erd);
        assertTrue(r.getLinks().isEmpty());
        Cardinality c = new Many();
        r.addCardinality(new Many());
        assertFalse(r.getLinks().isEmpty());

    }

    private void checkManyMany() {
        Erd erd = new Erd();
        Relationship r = new Relationship(erd);
        Entity e1 = new Entity();
        e1.setName("Entita1");
        Entity e2 = new Entity();
        e2.setName("Entita2");

        e1.addPrimaryKey(new PrimaryKey("Id_Entita1", new TInteger()));

        Cardinality c1 = new Many();
        c1.setEntity(e1);
        r.addCardinality(c1);

        Cardinality c2 = new OneOrMore();
        c2.setEntity(e2);
        r.addCardinality(c2);

        assertTrue(erd.getRelationshipTables().size() == 0);
        c1.setRelationship(r);
        c2.setRelationship(r);
        assertTrue(r.getLinks().size() == 2);

        List<Attribute> pks = erd.getRelationshipTables().get(0).getPrimaryKeys();
        List<Attribute> fks = erd.getRelationshipTables().get(0).getForeignKeys();

        assertTrue(pks.containsAll(fks));
        assertTrue(erd.getRelationshipTables().size() == 1, "Relationships' tables should be of size 1");


        erd.getRelationshipTables().get(0).getPrimaryKeys().stream().forEach(System.out::println);
        e2.addPrimaryKey(new PrimaryKey("PK1", new TInteger()));
        for(int i = 0; i < 100; i++)
            e2.addPrimaryKey(new PrimaryKey("E2_PK".concat(Integer.toString(i)), new TInteger()));

        pks = erd.getRelationshipTables().get(0).getPrimaryKeys();
        fks = erd.getRelationshipTables().get(0).getForeignKeys();

        assertTrue(fks.containsAll(pks), "Foreign keys should point to the primary keys");
    }
    private void checkOneOne() {
        Erd erd = new Erd();

        Relationship r = new Relationship(erd);

        Entity e1 = new Entity();
        e1.setName("Entita1");
        Entity e2 = new Entity();
        e2.setName("Entita2");

        e1.addPrimaryKey(new PrimaryKey("Id_1", new TInteger()));

        for(int i = 0; i < 100; i++)
            e2.addPrimaryKey(new PrimaryKey("Id".concat(Integer.toString(i)), new TInteger()));

        Cardinality c1 = new One();
        c1.setEntity(e1);
        r.addCardinality(c1);
        Cardinality c2 = new OnlyOne();
        c2.setEntity(e2);
        r.addCardinality(c2);
        r.checkCardinalities();
        assertEquals(true, e2.getForeignKeys().equals(e1.getPrimaryKeys()));
        assertEquals(true, e1.getForeignKeys().equals(e2.getPrimaryKeys()));
    }
    private void checkOneMany() {
        Erd erd = new Erd();

        Relationship r = new Relationship(erd);

        Entity e1 = new Entity();
        e1.setName("Entita1");
        Entity e2 = new Entity();
        e2.setName("Entita2");

        e1.addPrimaryKey(new PrimaryKey("Id_1", new TInteger()));

        for(int i = 0; i < 100; i++)
            e2.addPrimaryKey(new PrimaryKey("Id".concat(Integer.toString(i)), new TInteger()));

        Cardinality c1 = new One();
        c1.setEntity(e1);
        r.addCardinality(c1);
        Cardinality c2 = new Many();
        c2.setEntity(e2);
        r.addCardinality(c2);
        e2.addPrimaryKey(new PrimaryKey("ID", new TInteger()));
        r.checkCardinalities();

        assertEquals(true, e1.getForeignKeys().equals(e2.getPrimaryKeys()));
        assertEquals(true, e2.getForeignKeys().size() == 0);
    }

}