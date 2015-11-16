package com.gmanjon.regx;

public class RegX {

    private StringBuilder regex = new StringBuilder("");
    
    public static RegX regx() {
        return new RegX();
    }

    public RegX anyChar() {
        regex.append(".");
        return this;
    }

    public RegX anyTimes() {
        regex.append("*");
        return this;
    }

    public RegX wordChar() {
        regex.append("\\w");
        return this;
    }

    public RegX anyWord() {
        regex.append("\\w+");
        return this;
    }

    public RegX digit() {
        regex.append("\\d");
        return this;
    }

    public RegX group() {
        regex.insert(0, "(?:").append(')');
        return this;
    }

    public RegX capturingGroup(String name) {
        regex.insert(0, "(?P").append(')');
        return this;
    }

    public RegX oneOrMoreTimes() {
        regex.append("+");
        return this;
    }

    public RegX literal(char c) {
        regex.append(c);
        return this;
    }

    public RegX literal(String s) {
        regex.append("\\Q").append(s).append("\\E");
        return this;
    }

    public RegX insert(RegX regx) {
        regex.append(regx.regex);
        return this;
    }

    public String getRegex() {
        return regex.toString();
    }    
    
}
