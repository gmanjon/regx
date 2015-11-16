package com.readableregex;

public class RR {

    private StringBuilder regex = new StringBuilder("");
    
    public static RR rr() {
        return new RR();
    }

    public RR anyChar() {
        regex.append(".");
        return this;
    }

    public RR anyTimes() {
        regex.append("*");
        return this;
    }

    public RR wordChar() {
        regex.append("\\w");
        return this;
    }

    public RR anyWord() {
        regex.append("\\w+");
        return this;
    }

    public RR digit() {
        regex.append("\\d");
        return this;
    }

    public RR group() {
        regex.insert(0, "(?").append(')');
        return this;
    }

    public RR capturingGroup(String name) {
        regex.insert(0, "(?P").append(')');
        return this;
    }

    public RR oneOrMoreTimes() {
        regex.append("+");
        return this;
    }

    public RR literal(char c) {
        regex.append(c);
        return this;
    }

    public RR literal(String s) {
        regex.append("\\Q").append(s).append("\\E");
        return this;
    }

    public RR insert(RR rr) {
        regex.append(rr.regex);
        return this;
    }

    public String getRegex() {
        return regex.toString();
    }    
    
}
