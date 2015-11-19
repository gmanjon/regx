package com.gmanjon.regx;

import java.util.regex.Pattern;

public class RegX {

    private CharacterClass lastCharacterClass;
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

    public RegX startOfLine() {
        currentGroup.append("^");
        return this;
    }

    public RegX endOfLine() {
        currentGroup.append("$");
        return this;
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

    private void times(int from, int to) {
        if (from >= 0 && to >= 0) {
            currentGroup.append('{').append(from).append(',').append(to).append('}');
        } else if (from >= 0) {
            currentGroup.append('{').append(from).append(',').append('}');
        } else {
            throw new IllegalArgumentException("Combination of {from, to} not allowed: {" + from + ", " + to + "}");
        }
    }

    private void times(int times) {
        if (times < 0) {
            throw new IllegalArgumentException("Number provided must be higher or equal to 0");
        }
        currentGroup.append('{').append(times).append('}');
    }

    public RegX upperChar() {
        currentGroup.append("[A-Z]");
        return this;
    }

    public RegX lowerChar() {
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

    public RegX nonDigit() {
        currentGroup.append("\\D");
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

    public RegX literal(int i) {
        return literal((char) i);
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

    // character classes

    public RegX oneOf(String string) {
        currentGroup.append('[').append(string).append(']');
        lastCharacterClass = CharacterClass.CLASS;
        return this;
    }

    public RegX noneOf(String string) {
        currentGroup.append("[^").append(string).append(']');
        lastCharacterClass = CharacterClass.NEGATIVE_CLASS;
        return this;
    }

    public RegX range(char... ranges) {
        validateRange(ranges);
        currentGroup.append('[').append(buildRanges(ranges)).append(']');
        lastCharacterClass = CharacterClass.RANGE;
        return this;
    }

    public RegX rangeExclude(char... ranges) {
        validateRange(ranges);
        currentGroup.append("[^").append(buildRanges(ranges)).append(']');
        lastCharacterClass = CharacterClass.NEGATIVE_RANGE;
        return this;
    }

    // intersections

    public RegX intersectOneOf(String string) {
        validateIntersect();
        removeLastBracket();
        if (lastCharacterClass == CharacterClass.CLASS) {
            currentGroup.append(string).append("]");
        } else {
            currentGroup.append("&&[").append(string).append("]]");
        }
        lastCharacterClass = CharacterClass.NONE;
        return this;
    }

    public RegX intersectNoneOf(String string) {
        validateIntersect();
        removeLastBracket();
        if (lastCharacterClass == CharacterClass.NEGATIVE_CLASS) {
            currentGroup.append(string).append(']');
        } else {
            currentGroup.append("&&[^").append(string).append("]]");
        }
        lastCharacterClass = CharacterClass.NONE;
        return this;
    }

    public RegX intersect(char... ranges) {
        validateRangeIntersection(ranges);
        removeLastBracket();
        if (lastCharacterClass == CharacterClass.RANGE) {
            currentGroup.append(buildRanges(ranges)).append(']');
        } else {
            currentGroup.append("&&[").append(buildRanges(ranges)).append("]]");
        }
        lastCharacterClass = CharacterClass.NONE;
        return this;
    }

    public RegX intersectExclude(char... ranges) {
        validateRangeIntersection(ranges);
        removeLastBracket();
        if (lastCharacterClass == CharacterClass.NEGATIVE_RANGE) {
            currentGroup.append(buildRanges(ranges)).append(']');
        } else {
            currentGroup.append("&&[^").append(buildRanges(ranges)).append("]]");
        }
        lastCharacterClass = CharacterClass.NONE;
        return this;
    }

    private void validateRangeIntersection(char... ranges) {
        validateRange(ranges);
        validateIntersect();
    }

    private void validateRange(char... ranges) {
        if (ranges.length%2 != 0) {
            throw new IllegalArgumentException("Range characters must be an even number");
        }
    }

    private void validateIntersect() {
        if (lastCharacterClass == CharacterClass.NONE) {
            throw new IllegalStateException("At the moment only one depth of intersections is supported");
        }
        if (currentGroup.charAt(currentGroup.length() - 1) != ']') {
            throw new IllegalStateException("You can only intersect within character classes");
        }
    }

    private void removeLastBracket() {
        currentGroup.deleteCharAt(currentGroup.length() - 1);
    }

    private StringBuilder buildRanges(char... ranges) {
        StringBuilder currentRange = new StringBuilder();
        for (int i = 0; i < ranges.length; i = i + 2) {
            char from = ranges[i];
            char to = ranges[i+1];
            currentRange.append(from).append('-').append(to);
        }
        return currentRange;
    }

    // end of character classes section

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
        System.out.println(regexBuilder.toString());
        return regexBuilder.toString();
    }
}
