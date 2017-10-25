package relationships;

import attributes.Attribute;
import database.Database;
import database.Visitable;
import entites.Entity;
import entites.Table;
import model.Erd;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Relationship implements Visitable {
    private final Erd erd;
    private final List<Cardinality> links;
    private final List<Attribute> attributes;
    private String name;
    private Table table;
    public Relationship(Erd erd) {
        this(new ArrayList<>(), new ArrayList<>(), "Undefined", erd);
    }
    public Relationship(List<Cardinality> links, List<Attribute> attributes, String name, Erd erd) {
        this.links = links;
        this.name = name;
        this.erd = erd;
        this.attributes = attributes;
    }
    public List<Cardinality> getLinks() {
        return links;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Erd getErd() {
        return erd;
    }

    private void setCardinalityOneMore() {
        Optional<Cardinality> one = links.stream()
                .filter((x) -> x instanceof One || x instanceof OnlyOne)
                .findFirst();
        Optional<Cardinality> many = links.stream()
                .filter((x) -> x instanceof Many || x instanceof OneOrMore)
                .findFirst();
        if(one.isPresent() && many.isPresent()) {
            one.get().getEntity().addDependency(many.get().getEntity());
            if(attributes.size() > 0) {
                createTable(new Entity(getName(),
                        attributes,
                        one.get().getEntity().getPrimaryKeys(),
                        links.stream()
                                .map((x) -> x.getEntity())
                                .collect(Collectors.toList())));
            }
        }


    }
    protected void createTable(Table t) {
        if(table != null) {
            erd.getRelationshipTables().remove(table);
        }
        erd.addRelationshipTables(t);
        table = t;
    }
    private void setCardinalityManyMany() {
        List<Attribute> pks = new ArrayList<>();
        List<Table> dependencies = new ArrayList<>(links.size());
        links.stream().forEach((x) -> {
            pks.addAll(x.getEntity().getPrimaryKeys());
            dependencies.add(x.getEntity());
        } );
        createTable( new Entity(getName(), attributes, pks, dependencies));

    }
    private void setCardinalityOneToOne() {
        setOneToOneFk(links.get(0).getEntity());
    }
    public void setOneToOneFk(Table t){
        Optional<Cardinality> e = links.stream()
                .filter((x) -> !x.getEntity().equals(t))
                .findFirst();
        if(e.isPresent()) {
            t.addDependency(e.get().getEntity());

        }

    }
    public void addCardinality(Cardinality cardinality) {
        Class c = cardinality.getClass();
        if(links.size() < 2 || links.stream().allMatch((x) -> x.getClass().equals(c))) {
            links.add(cardinality);
            checkCardinalities();
        }
    }
    public void removeCardinality(Cardinality cardinality) {
        links.remove(cardinality);
    }
    public void checkCardinalities() {
        if(links.size() > 1 && erd != null) {
            int cnt = 0;
            for(Cardinality c : links) {
                if(c instanceof Many || c instanceof OneOrMore) {
                    cnt++;
                }
            }
            if(cnt == links.size()) {
                setCardinalityManyMany();
            }
            else if(cnt > 0) {
                setCardinalityOneMore();
            }
            else if(cnt == 0){
                setCardinalityOneToOne();
            }
        }
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public String accept(Database database) {
        return database.generate(this);
    }
}