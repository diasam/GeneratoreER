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
import java.util.stream.Stream;

public class Sql extends Database {
    private boolean done = false;
    private final HashMap<Table, Boolean> tablesCreated;
    public Sql() {
        super();
        tablesCreated = new HashMap<>();
    }
    public static String concat(String a, String b) {
        return a.concat("\n").concat(b);
    }
    @Override
    public String getScript(Erd erd) {
        Stream.concat(erd.getTables().stream(), erd.getRelationshipTables().stream()).forEach((x) -> {
                removeLast(x);
                newLine(x);
                append(x, ");");

        });
        return script.get(erd)
                .concat(erd.getTables().stream()
                        .map((x) -> script.get(x))
                        .reduce("", Sql::concat))
                .concat(erd.getRelationshipTables().stream()
                        .map((x) -> script.get(x))
                        .reduce("", Sql::concat));
    }
    @Override
    public String generate(NormalAttribute normalAttribute) {
        return normalAttribute.getName().concat(" ")
                .concat(normalAttribute.getDataType().accept(this));

    }

    @Override
    public String generate(PrimaryKey primaryKey) {
        return primaryKey.getName();
    }

    @Override
    public String generate(ForeignKey foreignKey) {

        return "FOREIGN KEY ("
                    .concat(foreignKey.getName())
                    .concat(") REFERENCES ")
                    .concat(foreignKey.getReference().getName())
                    .concat("(")
                    .concat(foreignKey.getName()
                    .concat(")"));
    }
    @Override
    public String generate(Erd erd) {
        append(erd,"create database if not exists ");
        append(erd, erd.getName().concat(";"));
        newLine(erd);
        append(erd, "use ". concat(erd.getName()).concat(";"));
        newLine(erd);
        erd.getTables().stream()
                .filter((x) -> x.getDependencies().size() == 0)
                .forEach((x) -> x.accept(this));
        erd.getRelationships().stream()
                .forEach((x) -> x.accept(this));
        erd.getRelationshipTables().stream()
                .forEach((x) -> x.accept(this));
        return "";
    }
    @Override
    public String generate(Entity entity) {
        if(!tablesCreated.containsKey(entity)) {
            tablesCreated.put(entity, true);
            done = false;
            append(entity, "create table ".concat(entity.getName()).concat(" ("));
            addIndentation(entity);
            indent(entity);
            newLine(entity);
            entity.getNormalAttributes().stream().forEach((normalAttribute) -> {
                append(entity, normalAttribute.accept(this).concat(","));
                done = true;
            });
            // Aggiunge una nuova linea
            if (done) {
                newLine(entity);
                done = false;
            }

            entity.getPrimaryKeys().stream().forEach(primaryKey -> append(entity, primaryKey.getName().concat(" ")
                    .concat(primaryKey.getDataType().accept(this))
                    .concat(", \n\t")));
            removeLast(entity);

            if(entity.getPrimaryKeys().size() > 0) {
                append(entity, "PRIMARY KEY (");
            }
            entity.getPrimaryKeys().stream().forEach((primaryKey) -> {
                append(entity, primaryKey.accept(this).concat(","));
                done = true;
            });
            if(entity.getPrimaryKeys().size() > 0) {
                removeLast(entity);
                append(entity, "),");
            }
            if (done) {
                newLine(entity);
                done = false;
            }

            entity.getForeignKeys().stream().forEach((foreignKey) -> {
                if (!script.get(entity).contains(foreignKey.getName() + " " + foreignKey.getDataType()))
                    append(entity, foreignKey.getName().concat(" ")
                                    .concat(foreignKey.getDataType().accept(this))
                                    .concat(", \n\t"));
                append(entity, foreignKey.accept(this).concat(","));
                done = true;
            });
            if (done) {
                subIndentation(entity);
                newLine(entity);
                done = false;
            }
            subIndentation(entity);
        }
        return script.get(entity);
    }
    public static void main(String[] args) {
        // ER Diagram
        Erd erd = new Erd("Db");
        Database d = new Sql();
        // Due entita
        Entity e1 = new Entity();
        Entity e2 = new Entity();

        erd.addTable(e1);
        erd.addTable(e2);

        Relationship r = new Relationship(erd);
        erd.addRelationship(r);

        Cardinality c1 = new Many();
        Cardinality c2 = new Many();
        c1.setEntity(e1);
        c2.setEntity(e2);
        e2.setName("Prova_Entita2");
        e2.addPrimaryKey(new PrimaryKey("foreign_test", new TDate(),e2));
        e1.setName("Prova_Entita1");
        e1.addNormalAttribute(new NormalAttribute("normalAttribute", new TInteger(),e1));
        e1.addPrimaryKey(new PrimaryKey("PrimaryKey", new TInteger(),e1));
        r.addCardinality(c1);
        r.addCardinality(c2);

        Entity e3 = new Entity();
        e3.setName("Entity3");

        e3.addPrimaryKey(new PrimaryKey("PrimaryKeyProva", new TInteger(),e3));
        Entity e4 = new Entity();
        e4.setName("Entity4");
        e4.addPrimaryKey(new PrimaryKey("PrimaryKeyProva", new TInteger(),e4));
        Relationship r2 = new Relationship(erd);
        erd.addTable(e3);
        erd.addTable(e4);
        erd.addRelationship(r2);
        Cardinality c3 = new OnlyOne();
        Cardinality c4 = new Many();

        c3.setEntity(e2);
        c4.setEntity(e3);
        r2.addCardinality(c3);
        r2.addCardinality(c4);
        Entity e5 = new Entity();
        e5.setName("no_dep");
        erd.addTable(e5);
        e5.addNormalAttribute(new NormalAttribute("Prova", new TInteger(),e5));
        d.generate(erd);
        System.out.println(d.getScript(erd));
    }

    @Override
    public String generate(Many many) {
        many.getEntity().accept(this);
        return "";
        //return many.getEntity().accept(this);

    }

    @Override
    public String generate(One one) {
        one.getEntity().accept(this);
        return "";
        //return one.getEntity().accept(this);
    }

    protected void endInstruction(Generable generable) {
        removeLast(generable);
        subIndentation(generable);
        newLine(generable);
        append(generable,");");
        newLine(generable);
    }

    @Override
    public String generate(OneOrMore oneOrMore) {
        oneOrMore.getEntity().accept(this);
        return "";
        //return oneOrMore.getEntity().accept(this);
    }

    @Override
    public String generate(OnlyOne onlyOne) {
        if(onlyOne != null && onlyOne.getEntity() != null) {
            onlyOne.getEntity().accept(this);
            Entity e = onlyOne.getEntity();
            addIndentation(e);
            indent(e);
            onlyOne.getEntity().getForeignKeys().stream()
                    .forEach((x) -> {
                        addIndentation(e);
                        indent(e);
                        append(e, "constraint check ");
                        append(e, "(");
                        append(e, x.getName());
                        append(e, " is not null");
                        append(e, "),");
                        newLine(e);
                    });
            subIndentation(e);
        }
        return "";
    }

    @Override
    public String generate(Relationship relationship) {
        relationship.checkCardinalities();

        // Prima si generano le entità legate ad una cardinalità di tipo molti
        relationship.getLinks().stream()
                .filter((x) -> x instanceof Many || x instanceof OneOrMore)
                .forEach((x) -> x.accept(this));
        // In seguito si generano le entità legate ad una cardinalità di tipo Uno
        relationship.getLinks().stream()
                .filter((x) -> x instanceof One || x instanceof OnlyOne)
                .forEach((x) -> x.accept(this));
        return "";
    }

    @Override
    public String generate(TFloat t) {
        return t.getName()
                .concat("(")
                .concat(Integer.toString(t.getSize()))
                .concat(")");
    }

    @Override
    public String generate(TInteger t) {
        return t.getName()
                .concat("(")
                .concat(Integer.toString(t.getSize()))
                .concat(")");
    }

    @Override
    public String generate(TDate t) {
        return t.getName();
    }

    @Override
    public String generate(TBlob t) {
        return t.getName();
    }

    @Override
    public String generate(TLongBlob t) {
        return t.getName();
    }

    @Override
    public String generate( TLongText t) {
        return t.getName();
    }

    @Override
    public String generate(TMediumBlob t) {
        return t.getName();
    }

    @Override
    public String generate(TText t) {
        return t.getName();
    }

    @Override
    public String generate(TTinyInt t) {
        return t.getName();
    }

    @Override
    public String generate(TVarchar t) {
        return t.getName()
                .concat("(")
                .concat(Integer.toString(t.getSize()))
                .concat(")");
    }

}
