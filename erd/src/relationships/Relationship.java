package relationships;

import attributes.Attribute;
import attributes.PrimaryKey;
import datatypes.TInteger;
import entites.Entity;
import entites.Table;
import model.Erd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Relationship {
    private final Erd erd;
    private final List<Cardinality> links;
    private String name;
    private Table table;
    public Relationship(Erd erd) {
        this(new ArrayList<>(), "Undefined", erd);
    }
    public Relationship(List<Cardinality> links, String name, Erd erd) {
        this.links = links;
        this.name = name;
        this.erd = erd;
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
        if(one.isPresent() && many.isPresent()){
            many.get().getEntity().getPrimaryKeys().stream()
                    .forEach(one.get().getEntity()::addForeignKey);
        }
    }
    private void setCardinalityManyMany() {
        List<Attribute> pks = new ArrayList<>();
        links.stream().forEach((x) -> pks.addAll(x.getEntity().getPrimaryKeys()));

        if(table != null) {
            erd.getRelationshipTables().remove(table);
        }
        table = new Entity(getName(), null,pks, pks);
        erd.addRelationshipTables(table);
    }
    private void setCardinalityOneToOne() {
        links.get(0).getEntity().getPrimaryKeys().stream()
                .forEach(links.get(1).getEntity()::addForeignKey);
        links.get(1).getEntity().getPrimaryKeys().stream()
                .forEach(links.get(0).getEntity()::addForeignKey);

    }
    public void addCardinality(Cardinality cardinality) {
        links.add(cardinality);
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
}