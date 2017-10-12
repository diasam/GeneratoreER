package database;

import attributes.Attribute;
import attributes.ForeignKey;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.TDate;
import datatypes.TFloat;
import datatypes.TInteger;
import entites.Entity;
import entites.Table;
import model.Erd;
import relationships.*;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Sql extends Database {
    private boolean done = false;
    private final HashMap<Entity, Boolean> tablesCreated;
    public Sql() {
        super();
        tablesCreated = new HashMap<>();
    }

    @Override
    public void generate(NormalAttribute normalAttribute) {
        append(normalAttribute.getName()
                .concat(" ")
                .concat(normalAttribute.getDataType().toString()
                ));
    }

    @Override
    public void generate(PrimaryKey primaryKey) {
        append(primaryKey.getName().concat(" ")
                .concat(primaryKey.getDataType().toString()));
        append(", ");
        newLine();
        append("PRIMARY KEY("
                .concat(primaryKey.getName()
                .concat(")")));
    }

    @Override
    public void generate(ForeignKey foreignKey) {
        append(foreignKey.getName().concat(" ")
                .concat(foreignKey.getDataType().toString()));
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

    }
    @Override
    public void generate(Entity entity) {
        done = false;
        append("create table ".concat(entity.getName()).concat(" ("));
        addIndentation();
        indent();
        newLine();
        entity.getNormalAttributes().stream().forEach( (x) -> {
            x.accept(this);
            append(",");
            done = true;

        });
        if(done) {
            newLine();
            done = false;
        }
        entity.getPrimaryKeys().stream().forEach( (x) -> {
            x.accept(this);
            append(",");
            done = true;
        });
        if(done) {
            newLine();
            done = false;
        }
        entity.getForeignKeys().stream().forEach( (x) -> {
            x.accept(this);
            append(",");
            done = true;
        });
        if(done) {
            subIndentation();
            newLine();
            done = false;
        }
        subIndentation();
    }
    public static void main(String[] args) {
        Erd erd = new Erd("Db");
        Database d = new Sql();
        Entity e1 = new Entity();
        Entity e2 = new Entity();
        Relationship r = new Relationship(erd);
        Cardinality c1 = new OnlyOne();
        Cardinality c2 = new Many();
        c1.setEntity(e1);
        c2.setEntity(e2);
        e2.setName("Prova_Entita2");
        e2.addPrimaryKey(new PrimaryKey("foreign_test", new TInteger()));
        e1.setName("Prova_Entita1");
        e1.addNormalAttribute(new NormalAttribute("normalAttribute", new TInteger()));
        e1.addPrimaryKey(new PrimaryKey("PrimaryKey", new TInteger()));
        r.addCardinality(c1);
        r.addCardinality(c2);
        d.generate(r);
        System.out.println(d.getScript());


    }
    @Override
    public void generate(Table table) {

    }

    @Override
    public void generate(Many many) {
        many.getEntity().accept(this);
        removeLast();
        subIndentation();
        newLine();
        append(");");
        newLine();
    }

    @Override
    public void generate(One one) {
        one.getEntity().accept(this);
        removeLast();
        subIndentation();
        newLine();
        append(");");
        newLine();
    }


    @Override
    public void generate(OneOrMore oneOrMore) {
        oneOrMore.getEntity().accept(this);
        removeLast();
        subIndentation();
        newLine();
        append(");");
        newLine();
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
        removeLast();
        subIndentation();
        newLine();
        append(");");
        newLine();
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
    public void generate(TFloat tFloat) {

    }

    @Override
    public void generate(TInteger tInteger) {

    }

    @Override
    public void generate(TDate tDate) {

    }
}
