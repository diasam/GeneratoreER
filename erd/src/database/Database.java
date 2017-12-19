package database;

import attributes.ForeignKey;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.*;
import entites.Entity;
import model.Erd;
import relationships.*;

import java.util.HashMap;

public abstract class Database {
    protected final HashMap<Generable, String> script;
    protected final HashMap<Generable, Integer> indentation;
    protected int indentationString;

    public Database() {
        this(0, new HashMap<>(), new HashMap<>());
    }

    public Database(int indentationString, HashMap<Generable, String> script, HashMap<Generable, Integer> indentation) {
        this.indentationString = indentationString;
        this.script = script;
        this.indentation = indentation;
    }
    protected void append(Generable generable, String s) {

        script.put(generable, script.getOrDefault(generable, "").concat(s));
    }
    protected void addIndentation(Generable generable) {
        indentation(generable, 1);
    }
    protected void subIndentation(Generable generable) {
        indentation(generable, -1);
    }
    protected void indentation(Generable generable, int i) {
        indentation.put(generable, (indentation.getOrDefault(generable, 0) + i));
    }
    protected void indent(Generable generable) {
        if(indentation.containsKey(generable)) {
            for (int i = 0; i < indentation.get(generable); i++)
                append(generable, "\t");
        }
    }
    protected void resetIndentation(Generable generable) {
        indentation.put(generable, 0);
    }
    protected void newLine(Generable generable) {
        append(generable, "\n");
        indent(generable);

    }
    protected void removeLast(Generable generable) {
        String s = "";
        if(script.containsKey(generable)) {
            s = script.get(generable);
        }
        boolean flag = true;
        if(s.length() > 1) {
            while (flag && s.length() > 1 && (s.charAt(s.length() - 1) == '\n' || s.charAt(s.length() - 1) == '\t')) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.charAt(s.length() - 1) != '(')
                s = s.substring(0, s.length() - 1);
        }
        script.put(generable, s);

    }
    public abstract String getScript(Erd erd);
    public abstract String generate(Erd erd);
    public abstract String generate(Entity entity);
    public abstract String generate(NormalAttribute normalAttribute);
    public abstract String generate(PrimaryKey primaryKey);
    public abstract String generate(ForeignKey foreignKey);
    public abstract String generate(Many many);
    public abstract String generate(One one);
    public abstract String generate(OneOrMore oneOrMore);
    public abstract String generate(OnlyOne onlyOne);
    public abstract String generate(Relationship relationship);
    public abstract String generate(TFloat t);
    public abstract String generate(TInteger t);
    public abstract String generate(TDate t);
    public abstract String generate(TBlob t);
    public abstract String generate(TLongBlob t);
    public abstract String generate(TLongText t);
    public abstract String generate(TMediumBlob t);
    public abstract String generate(TText t);
    public abstract String generate(TTinyInt t);
    public abstract String generate(TVarchar t);
}
