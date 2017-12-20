package relationships;

import attributes.Attribute;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import database.Database;
import database.Generable;
import entites.Entity;
import entites.Table;
import model.Erd;

import java.util.*;
import java.util.stream.Collectors;

public class Relationship implements Generable {
    private final Erd erd;
    private final List<Cardinality> links;
    private final List<Attribute> attributes;
    private String name;
    private Table table;

    private boolean dependency = false;

    public Relationship(Erd erd) {
        this(new ArrayList<>(), new ArrayList<>(), "Relationship", erd);
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
        links.stream()
                .filter((cardinality) -> cardinality instanceof One || cardinality instanceof OnlyOne)
                .findFirst()
                .ifPresent((one) ->
                        links.stream()
                                .filter((cardinality) -> cardinality instanceof Many || cardinality instanceof OneOrMore)
                                .findFirst()
                                .ifPresent((many) -> Optional.ofNullable(many.getEntity())
                                    .ifPresent(entityM ->
                                        Optional.ofNullable(one.getEntity()).ifPresent(entityO ->
                                            entityO.addDependency(entityM))
                                    ))
                );
        dependency = true;
    }
    protected void createTable(Table t) {
        removeTable();
        erd.addRelationshipTables(t);
        table = t;
    }
    private void setCardinalityManyMany() {
        removeTable();
        List<Attribute> pks = new ArrayList<>(attributes.stream()
                .filter(attribute -> attribute instanceof PrimaryKey)
                .collect(Collectors.toList()));
        List<Table> dependencies = new ArrayList<>(links.size());
        links.stream()
                .filter(cardinality -> cardinality.getEntity() != null)
                .forEach((cardinality) -> {
                    pks.addAll(cardinality.getEntity().getPrimaryKeys() );
                    dependencies.add(cardinality.getEntity());
                });

        createTable( new Entity( getName()
                               , attributes.stream()
                                    .filter(attribute -> attribute instanceof NormalAttribute)
                                    .collect(Collectors.toList())
                               , pks
                               , dependencies));
        if(dependency) {
            for(int i = 0; i < links.size(); i++) {
                Cardinality tmp = links.get(i);
                for(int j = 0; j < links.size(); j++) {
                    tmp.getEntity().getDependencies().remove(links.get(j).getEntity());
                }

            }
        }
        dependency = false;
    }
    private void removeTable() {
        if(table != null) {
            erd.getRelationshipTables().remove(table);
            table = null;
        }
    }
    private void setCardinalityOneToOne() {
        removeTable();
        setOneToOneFk(links.get(0).getEntity());
    }
    public void setOneToOneFk(Table t){
        if(t != null) {
            links.stream()
                    .filter(x -> x.getEntity() != null)
                    .filter((x) -> !x.getEntity().equals(t))
                    .findFirst()
                    .ifPresent(cardinality -> t.addDependency(cardinality.getEntity()));
            dependency = true;
        }
    }
    public boolean addCardinality(Cardinality cardinality) {
        Class c = cardinality.getClass();
        boolean flag = false;
        // Si controlla se ci sono meno di due cardinalita
        // o se tutti i tipi di cardinalita sono identici
        if(links.size() < 2) {
            links.add(cardinality);
            flag = true;
        }

        else if(links.stream().allMatch(cardinalityConsumer -> cardinalityConsumer.getClass().equals(c))
                && links.stream().allMatch(cardinalityConsumer -> cardinalityConsumer instanceof Many
                                                                  || cardinalityConsumer instanceof OneOrMore)) {
            links.add(cardinality);
            flag = true;
        }
        if(flag)
            checkCardinalities();
        return flag;
    }
    public void removeCardinality(Cardinality cardinality) {
        // Problema quando si cercano di eliminare delle entità
        // che hanno più dipendenze
        links.remove(cardinality);
    }
    public int checkCardinalities() {
        int cnt = -1;
        if(links.size() > 1 && erd != null) {
            cnt = 0;
            for(Cardinality c : links) {
                if((c instanceof Many) || (c instanceof OneOrMore)) {
                    cnt++;
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
                // Non funziona quando si cambiano le cardinalità
                // Le dipendenze devono essere spostate nella cardinalità
                //if (attributes.isEmpty())
                //   setCardinalityOneMore();
                //else
                    setCardinalityManyMany();
            }
            /**
             * Relazione Uno-Uno
             */
            else if(cnt == 0){
                setCardinalityOneToOne();
            }
        }
        return cnt;
    }
    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
        checkCardinalities();
    }
    public void removeAttribute(Attribute attribute) {
        attributes.remove(attribute);
        checkCardinalities();
    }
    public List<Attribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
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
    public void delete() {

    }
}