package com.gmanjon.regx;

public class RegX {

    private StringBuilder regexBuilder = new StringBuilder("");
    
    public static RegX regx() {
        return new RegX();
    }

    public RegX anyChar() {
        regexBuilder.append(".");
        return this;
    }

    public RegX anyTimes(boolean lazy) {
        regexBuilder.append("*");
        if (lazy) {
            regexBuilder.append("?");
        }
        return this;
    }

    public RegX anyTimesGreedy() {
        return anyTimes(false);
    }

    public RegX anyTimesLazy() {
        return anyTimes(true);
    }

    public RegX anyTimes() {
        return anyTimes(false);
    }

    public RegX alphanumericChar() {
        regexBuilder.append("\\w");
        return this;
    }

    public RegX anyWord() {
        regexBuilder.append("\\w+");
        return this;
    }

    public RegX digit() {
        regexBuilder.append("\\d");
        return this;
    }

    public RegX group() {
        regexBuilder.insert(0, "(?:").append(')');
        return this;
    }

    public RegX capturingGroup(String name) {
        StringBuilder sb = new StringBuilder("(?<");
        sb.append(name).append(">");
        regexBuilder.insert(0, sb.toString()).append(')');
        return this;
    }

    public RegX capturingGroup() {
        regexBuilder.insert(0, "(").append(')');
        return this;
    }

    public RegX oneOrMoreTimes() {
        regexBuilder.append("+");
        return this;
    }

    public RegX literal(char c) {
        regexBuilder.append("\\").append(c);
        return this;
    }

    public RegX literal(String s) {
        regexBuilder.append("\\Q").append(s).append("\\E");
        return this;
    }

    public RegX regex(String regex) {
        regexBuilder.append(regex);
        return this;
    }

    public RegX followedBy(RegX regx) {
        regexBuilder.append(regx.regexBuilder);
        return this;
    }

    public RegX or(RegX regx) {
        regexBuilder.append("|").append(regx.regexBuilder);
        return this;
    }

    public String getRegex() {
        return regexBuilder.toString();
    }    
    
}
