package database;

import attributes.ForeignKey;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import com.sun.tools.javac.code.Attribute;
import datatypes.*;
import entites.Entity;
import entites.Table;
import model.Erd;
import relationships.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Database {
    protected String scriptString;
    protected final HashMap<Visitable, String> script;
    protected final HashMap<Visitable, Integer> indentation;
    protected int indentationString;

    public Database() {
        this("", 0, new HashMap<>(), new HashMap<>());
    }

    public Database(String scriptString, int indentationString, HashMap<Visitable, String> script, HashMap<Visitable, Integer> indentation) {
        this.scriptString = scriptString;
        this.indentationString = indentationString;
        this.script = script;
        this.indentation = indentation;
    }
    protected void append(Visitable visitable, String s) {
        /*if(!script.containsKey(visitable)) {
            script.put(visitable, "");
        }*/
        script.put(visitable, script.getOrDefault(visitable, "").concat(s));
    }
    protected void addIndentation(Visitable visitable) {
        indentation(visitable, 1);
    }
    protected void subIndentation(Visitable visitable) {
        indentation(visitable, -1);
    }
    protected void indentation(Visitable visitable, int i) {
        indentation.put(visitable, (indentation.getOrDefault(visitable, 0) + i));
    }
    protected void indent(Visitable visitable) {
        if(indentation.containsKey(visitable)) {
            for (int i = 0; i < indentation.get(visitable); i++)
                append(visitable, "\t");
        }
    }
    protected void resetIndentation(Visitable visitable) {
        indentation.put(visitable, 0);
    }
    protected void newLine(Visitable visitable) {
        append(visitable, "\n");
        indent(visitable);

    }
    protected void removeLast(Visitable visitable) {
        String s = "";
        if(script.containsKey(visitable)) {
            s = script.get(visitable);
        }
        boolean flag = true;
        if(s.length() > 1) {
            while (flag && s.length() > 1 && (s.charAt(s.length() - 1) == '\n' || s.charAt(s.length() - 1) == '\t')) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.charAt(s.length() - 1) != '(')
                s = s.substring(0, s.length() - 1);
        }
        script.put(visitable, s);

    }
    public abstract String getScript(Erd erd);


    public void setScript(String script) {
        this.scriptString = script;
    }

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
