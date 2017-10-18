package database;

import attributes.ForeignKey;
import attributes.NormalAttribute;
import attributes.PrimaryKey;
import datatypes.*;
import entites.Entity;
import entites.Table;
import model.Erd;
import relationships.*;

public abstract class Database {
    private String script;
    private int indentation;
    public Database() {
        this("", 0);
    }

    public Database(String script, int indentation) {
        this.script = script;
        this.indentation = indentation;
    }
    protected void addIndentation() {
        indentation++;
    }
    protected void subIndentation() {
        if(indentation > 0)
        indentation--;
    }
    protected void indent() {
        for(int i = 0; i < indentation; i++)
            append("\t");
    }
    protected void resetIndentation() {
        indentation = 0;
    }
    protected void newLine() {
        append("\n");
        indent();

    }
    protected void removeLast() {
        if(script.length() > 1) {
            while (script.charAt(script.length()-1) == '\n' || script.charAt(script.length()-1) == '\t' || script.length() <= 1) {
                script = script.substring(0, script.length()-1);
            }
            script = script.substring(0, script.length()-1);

        }

    }
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
    protected void append(String s) {
        //script = script.concat(s.concat(";\n"));
        script = script.concat(s);
    }
    public abstract void generate(Erd erd);
    public abstract void generate(Entity entity);
    public abstract void generate(NormalAttribute normalAttribute);
    public abstract void generate(PrimaryKey primaryKey);
    public abstract void generate(ForeignKey foreignKey);
    public abstract void generate(Many many);
    public abstract void generate(One one);
    public abstract void generate(OneOrMore oneOrMore);
    public abstract void generate(OnlyOne onlyOne);
    public abstract void generate(Relationship relationship);
    public abstract void generate(TFloat t);
    public abstract void generate(TInteger t);
    public abstract void generate(TDate t);
    public abstract void generate(TBlob t);
    public abstract void generate(TLongBlob t);
    public abstract void generate(TLongText t);
    public abstract void generate(TMediumBlob t);
    public abstract void generate(TText t);
    public abstract void generate(TTinyInt t);
    public abstract void generate(TVarchar t);
}
