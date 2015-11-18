package com.gmanjon.regx;

import java.util.regex.Pattern;

public class RegX {

    private boolean captureAllGroups = true;
    private StringBuilder regexBuilder = new StringBuilder("");
    private StringBuilder currentGroup = new StringBuilder("");

    public static RegX regx() {
        return new RegX();
    }

    public static RegX regx(boolean captureAllGroups) {
        RegX instance = new RegX();
        instance.captureAllGroups = captureAllGroups;
        return instance;
    }

    public RegX anyChar() {
        currentGroup.append(".");
        return this;
    }

    public RegX anyTimes(boolean lazy) {
        currentGroup.append("*");
        if (lazy) {
            currentGroup.append("?");
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

    public RegX oneOrMoreTimes(boolean lazy) {
        currentGroup.append("+");
        if (lazy) {
            currentGroup.append("?");
        }
        return this;
    }

    public RegX oneOrMoreTimesGreedy() {
        return oneOrMoreTimes(false);
    }

    public RegX oneOrMoreTimesLazy() {
        return oneOrMoreTimes(true);
    }

    public RegX oneOrMoreTimes() {
        return oneOrMoreTimes(false);
    }

    public RegX optional() {
        currentGroup.append("?");
        return this;
    }

    public RegX alphanumericChar() {
        currentGroup.append("\\w");
        return this;
    }

    public RegX alphabeticChar() {
        currentGroup.append("[a-zA-Z]");
        return this;
    }

    public RegX alphabeticChar(int from, int to) {
        currentGroup.append("[a-zA-Z]").append('{').append(from).append(',').append(to).append('}');
        return this;
    }

    private void appendFromTo(int from, int to) {
        if (from >= 0 && to >= 0) {
            currentGroup.append('{').append(from).append(',').append(to).append('}');
        } else if (from >= 0) {
            currentGroup.append('{').append(from).append(',').append('}');
        } else {
            throw new IllegalArgumentException("Combination of {from, to} not allowed: {" + from + ", " + to + "}");
        }
    }

    private void appendExactly(int times) {
        if (times >= 0) {
            currentGroup.append('{').append(times).append('}');
        } else {
            throw new IllegalArgumentException("Number provided must be higher than 0");
        }
    }

    public RegX uppercaseChar() {
        currentGroup.append("[A-Z]");
        return this;
    }

    public RegX lowercaseChar() {
        currentGroup.append("[a-z]");
        return this;
    }

    public RegX anyWord() {
        currentGroup.append("\\w+");
        return this;
    }

    public RegX digit() {
        currentGroup.append("\\d");
        return this;
    }

    public RegX group() {
        if (captureAllGroups) {
            return capturingGroup();
        }
        currentGroup.insert(0, "(?:").append(')');
        endGroup();
        return this;
    }

    public RegX literalGroup(String literal) {
        literal(literal);
        group();
        return this;
    }

    public RegX capturingGroup(String name) {
        StringBuilder sb = new StringBuilder("(?<");
        sb.append(name).append(">");
        currentGroup.insert(0, sb.toString()).append(')');
        endGroup();
        return this;
    }

    public RegX capturingGroup() {
        currentGroup.insert(0, "(").append(')');
        endGroup();
        return this;
    }

    private void endGroup() {
        regexBuilder.append(currentGroup.toString());
        currentGroup = new StringBuilder();
    }

    public RegX literal(char c) {
        if (c == '.') {
            currentGroup.append("\\.");
        } else if (needsEscaping(String.valueOf(c))) {
            currentGroup.append("\\").append(c);
        } else {
            currentGroup.append(c);
        }
        return this;
    }

    public RegX literal(String s) {
        if (needsEscaping(s)) {
            currentGroup.append("\\Q").append(s).append("\\E");
        } else {
            currentGroup.append(s.replaceAll("\\.", "\\."));
        }
        return this;
    }

    public RegX regex(String regex) {
        currentGroup.append(regex);
        return this;
    }

    public RegX followedBy(RegX regx) {
        currentGroup.append(regx.toString());
        return this;
    }

    public RegX followedBy() {
        return this;
    }

    public RegX or(RegX regx) {
        currentGroup.append("|").append(regx.currentGroup);
        return this;
    }

    public Pattern toPattern() {
        return Pattern.compile(toString());
    }
//
//    private boolean isAlreadyGrouped() {
//        int parenthesisIndex = 0;
//        int lastClosedParenthesisIndex = 0;
//        char previousChar = 'a';
//        String regex = currentGroup.toString();
//        for (int i = 0; i < regex.length(); i++) {
//            char currentChar = regex.charAt(i);
//            if (currentChar == '(' && previousChar != '\\') {
//                parenthesisIndex++;
//            } else if (currentChar == ')' && previousChar != '\\') {
//                lastClosedParenthesisIndex = parenthesisIndex;
//                parenthesisIndex--;
//            }
//            previousChar = currentChar;
//        }
//        return regex.startsWith("(") &&
//                regex.endsWith(")") &&
//                lastClosedParenthesisIndex == 1;
//    }

    private boolean needsEscaping(String sequence) {
        try {
            return !sequence.matches(sequence);
        } catch(Exception e) {
            return true;
        }
    }

    @Override
    public String toString() {
        endGroup();
        return regexBuilder.toString();
    }
}
