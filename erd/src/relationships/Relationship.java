package relationships;

import attributes.Attribute;
import database.Database;
import database.Visitable;
import entites.Entity;
import entites.Table;
import model.Erd;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Relationship implements Visitable {
    private final Erd erd;
    private final List<Cardinality> links;
    private final List<Attribute> attributes;
    private String name;
    private Table table;
    public Relationship(Erd erd) {
        this(new ArrayList<>(), new ArrayList<>(), "frt", erd);
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
        removeTable();
        /*Optional<Cardinality> one = links.stream()
                .filter((x) -> x instanceof One || x instanceof OnlyOne)
                .findFirst();
        Optional<Cardinality> many = links.stream()
                .filter((x) -> x instanceof Many || x instanceof OneOrMore)
                .findFirst();
        if(one.isPresent() && many.isPresent()) {
            one.get().getEntity().addDependency(many.get().getEntity());
            if(attributes.size() > 0) {
                createTable(new Entity(getName()
                        , attributes
                        , one.get().getEntity().getPrimaryKeys()
                        , links.stream()
                                    .map((x) -> x.getEntity())
                                    .collect(Collectors.toList())));
            }
        }*/

        System.out.println("Changed the thing");
        links.stream()
            .filter((x) -> x instanceof One || x instanceof OnlyOne)
            .findFirst()
            .ifPresent((one) -> {
                links.stream()
                        .filter((x) -> x instanceof Many || x instanceof OneOrMore)
                        .findFirst()
                        .ifPresent((many) -> {
                            if(attributes.size() > 0) {
                                createTable(  new Entity(getName()
                                            , attributes
                                            , one.getEntity().getPrimaryKeys()
                                            , links.stream()
                                                .map((x) -> x.getEntity())
                                                .collect(Collectors.toList())));
                            }
                            else {
                                one.getEntity().addDependency(many.getEntity());
                            }
                        });
            }
        );


    }
    protected void createTable(Table t) {
        removeTable();
        erd.addRelationshipTables(t);
        table = t;
    }
    private void setCardinalityManyMany() {
        final AtomicInteger i = new AtomicInteger(0);
        removeTable();
        List<Attribute> pks = new ArrayList<>();
        List<Table> dependencies = new ArrayList<>(links.size());
        links.stream().forEach((x) -> {
            if(x.getEntity() != null ) {
                pks.addAll(x.getEntity().getPrimaryKeys() );
                dependencies.add(
                        x.getEntity());
                System.out.println(i.getAndIncrement());
            }

        });
        createTable( new Entity(getName(), attributes, pks, dependencies));
    }
    private void removeTable() {
        if(table != null) {
            erd.getRelationshipTables().remove(table);
            table = null;
        }
    }
    private void setCardinalityOneToOne() {
        removeTable();
        //links.stream().forEach(x -> System.out.println(x.getEntity()));
        setOneToOneFk(links.get(0).getEntity());
    }
    public void setOneToOneFk(Table t){
        links.stream()
                //TODO Documentare
                .filter(x -> x.getEntity() != null)
                .filter((x) -> !x.getEntity().equals(t))
                .findFirst()
                .ifPresent(cardinality -> t.addDependency(cardinality.getEntity()));

    }
    public void addCardinality(Cardinality cardinality) {
        Class c = cardinality.getClass();
        // Si controlla se ci sono meno di due cardinalita
        // o se tutti i tipi di cardinalita sono identici
        if(links.size() < 2 || links.stream().allMatch((x) -> x.getClass().equals(c))) {
            links.add(cardinality);
            checkCardinalities();
        }
    }
    public void removeCardinality(Cardinality cardinality) {
        links.remove(cardinality);
    }
    public void checkCardinalities() {
        System.out.println("Size:\t" + (links.size()));
        if(links.size() > 1 && erd != null) {
            int cnt = 0;
            for(Cardinality c : links) {
                if((c instanceof Many) || (c instanceof OneOrMore)) {
                    cnt++;
                    System.out.println("Classe:\t\t\t\t\t\t"+c.getClass());
                }
            }
            /**
             * Relazione molti-molti
             */
            if(cnt == links.size()) {
                setCardinalityManyMany();
            }
            /**
             * Relazione uno-molti
             */
            else if(cnt > 0) {
                setCardinalityOneMore();
            }
            /**
             * Relazione Uno-Uno
             */
            else if(cnt == 0){
                setCardinalityOneToOne();
            }
            System.out.println("Cnt is: \t\t"+cnt);
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