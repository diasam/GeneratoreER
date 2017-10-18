package database;

import attributes.ForeignKey;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.*;
import entites.Entity;
import entites.Table;
import model.Erd;
import relationships.*;

import java.util.HashMap;

public class Sql extends Database {
    private boolean done = false;
    private final HashMap<Table, Boolean> tablesCreated;
    public Sql() {
        super();
        tablesCreated = new HashMap<>();
    }

    @Override
    public void generate(NormalAttribute normalAttribute) {
        append(normalAttribute.getName().concat(" "));
        normalAttribute.getDataType().accept(this);
    }

    @Override
    public void generate(PrimaryKey primaryKey) {
        append(primaryKey.getName().concat(" "));
        primaryKey.getDataType().accept(this);
        append(", ");
        newLine();
        append("PRIMARY KEY("
                .concat(primaryKey.getName()
                .concat(")")));
    }

    @Override
    public void generate(ForeignKey foreignKey) {
        append(foreignKey.getName().concat(" "));
        foreignKey.getDataType().accept(this);
        append(", ");
        newLine();
        append("FOREIGN KEY ("
                .concat(foreignKey.getName())
                .concat(") REFERENCES ")
                .concat(foreignKey.getReference().getName())
                .concat("(")
                .concat(foreignKey.getName()
                .concat(")")));
    }
    @Override
    public void generate(Erd erd) {
        append("create database if not exists ");
        append(erd.getName().concat(";"));
        newLine();
        append("use database ". concat(erd.getName()).concat(";"));
        newLine();
        erd.getRelationships().stream()
                .forEach((x) -> x.accept(this));
        erd.getRelationshipTables().stream()
                .forEach((x) -> x.accept(this));
        erd.getEntities().stream()
                .forEach((x) -> x.accept(this));


    }
    @Override
    public void generate(Entity entity) {
        if(!tablesCreated.containsKey(entity)) {
            tablesCreated.put(entity, true);
            entity.getDependencies().stream().forEach((x) -> x.accept(this));
            done = false;
            append("create table ".concat(entity.getName()).concat(" ("));
            addIndentation();
            indent();
            newLine();
            entity.getNormalAttributes().stream().forEach((x) -> {
                x.accept(this);
                append(",");
                done = true;

            });
            if (done) {
                newLine();
                done = false;
            }
            entity.getPrimaryKeys().stream().forEach((x) -> {
                x.accept(this);
                append(",");
                done = true;
            });
            if (done) {
                newLine();
                done = false;
            }
            entity.getForeignKeys().stream().forEach((x) -> {
                x.accept(this);
                append(",");
                done = true;
            });
            if (done) {
                subIndentation();
                newLine();
                done = false;
            }
            subIndentation();
        }

    }
    public static void main(String[] args) {
        Erd erd = new Erd("Db");
        Database d = new Sql();
        Entity e1 = new Entity();
        Entity e2 = new Entity();
        erd.addEntity(e1);
        erd.addEntity(e2);
        Relationship r = new Relationship(erd);
        erd.addRelationship(r);
        Cardinality c1 = new OnlyOne();
        Cardinality c2 = new Many();
        c1.setEntity(e1);
        c2.setEntity(e2);
        e2.setName("Prova_Entita2");
        e2.addPrimaryKey(new PrimaryKey("foreign_test", new TDate()));
        e1.setName("Prova_Entita1");
        e1.addNormalAttribute(new NormalAttribute("normalAttribute", new TInteger()));
        e1.addPrimaryKey(new PrimaryKey("PrimaryKey", new TInteger()));
        r.addCardinality(c1);
        r.addCardinality(c2);
        d.generate(erd);
        System.out.println(d.getScript());
        System.out.println(e1.getClass());
    }

    @Override
    public void generate(Many many) {
        many.getEntity().accept(this);
        endInstruction();
    }

    @Override
    public void generate(One one) {
        one.getEntity().accept(this);
        endInstruction();

    }

    protected void endInstruction() {
        removeLast();
        subIndentation();
        newLine();
        append(");");
        newLine();
    }

    @Override
    public void generate(OneOrMore oneOrMore) {
        oneOrMore.getEntity().accept(this);
        endInstruction();
    }

    @Override
    public void generate(OnlyOne onlyOne) {
        onlyOne.getEntity().accept(this);
        onlyOne.getEntity().getForeignKeys().stream()
                .forEach((x) -> {
                    addIndentation();
                    indent();
                    append("constraint check ");
                    append("(");
                    append(x.getName());
                    append(" is not null");
                    append("),");
                    newLine();
                });
        endInstruction();
    }

    @Override
    public void generate(Relationship relationship) {
        relationship.getLinks().stream()
                .filter((x) -> x instanceof Many || x instanceof OneOrMore)
                .forEach((x) -> x.accept(this));
        relationship.getLinks().stream()
                .filter((x) -> x instanceof One || x instanceof OnlyOne)
                .forEach((x) -> x.accept(this));
    }

    @Override
    public void generate(TFloat t) {
        append(t.getName());
        append("(");
        append(Integer.toString(t.getSize()));
        append(")");
    }

    @Override
    public void generate(TInteger t) {
        append(t.getName());
        append("(");
        append(Integer.toString(t.getSize()));
        append(")");
    }

    @Override
    public void generate(TDate t) {
        append(t.getName());
    }

    @Override
    public void generate(TBlob t) {
        append(t.getName());
    }

    @Override
    public void generate(TLongBlob t) {
        append(t.getName());
    }

    @Override
    public void generate(TLongText t) {
        append(t.getName());
    }

    @Override
    public void generate(TMediumBlob t) {
        append(t.getName());
    }

    @Override
    public void generate(TText t) {
        append(t.getName());
    }

    @Override
    public void generate(TTinyInt t) {
        append(t.getName());
    }

    @Override
    public void generate(TVarchar t) {
        append(t.getName());
        append("(");
        append(Integer.toString(t.getSize()));
        append(")");
    }
}
