package com.gmanjon.regx;

import static com.gmanjon.regx.RegX.regx;
import static com.gmanjon.regx.RegexTestUtils.assertMatches;
import static com.gmanjon.regx.RegexTestUtils.assertNoMatch;
import static com.gmanjon.regx.RegexTestUtils.assertParameterMatches;
import static com.gmanjon.regx.RegexTestUtils.assertParameterNoMatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegXTest {

    @Test
    public void anyChar() {
        String regex = regx().anyChar().toString();

        assertMatches("a", regex);
        assertMatches("b", regex);
        assertMatches(".", regex);
        assertMatches("\\", regex);

        assertNoMatch("", regex);
        assertNoMatch("aa", regex);
        assertNoMatch("..", regex);
    }

    @Test
    public void anyTimes() {
        String regex = regx().anyChar().anyTimes().toString();

        assertMatches("a", regex);
        assertMatches("bb", regex);
        assertMatches(".....", regex);
        assertMatches("", regex);
        assertMatches(")P(/WY893gv`\\9p`r78vb'", regex);
    }

    @Test
    public void anyTimesGreedy() {
        String regex = regx().literal('<').anyChar().anyTimesGreedy().literal('>').group().toString();

        assertParameterMatches("<a>b</a>", regex, 1, "<a>b</a>");
        assertParameterNoMatch("<a>b</a>", regex, 1, "<a>");
    }

    @Test
    public void anyTimesLazy() {
        String regex = regx().literal('<').anyChar().anyTimesLazy().literal('>').group().toString();

        assertParameterMatches("<a>b</a>", regex, 1, "<a>");
        assertParameterNoMatch("<a>b</a>", regex, 1, "<a>b</a>");
    }

    @Test
    public void oneOrMoreTimesGreedy() {
        String regex = regx().literal('<').anyChar().oneOrMoreTimesGreedy().literal('>').group().toString();

        assertParameterMatches("<a>b</a>", regex, 1, "<a>b</a>");
        assertParameterNoMatch("<a>b</a>", regex, 1, "<a>");
    }

    @Test
    public void oneOrMoreTimesLazy() {
        String regex = regx().literal('<').anyChar().oneOrMoreTimesLazy().literal('>').group().toString();

        assertParameterMatches("<a>b</a>", regex, 1, "<a>");
        assertParameterNoMatch("<a>b</a>", regex, 1, "<a>b</a>");
    }

    @Test
    public void alphanumericChar() {
        String regex = regx().alphanumericChar().toString();

        assertMatches("a", regex);
        assertMatches("z", regex);
        assertMatches("A", regex);
        assertMatches("Z", regex);
        assertMatches("_", regex);
        assertMatches("0", regex);
        assertMatches("9", regex);

        assertNoMatch(".", regex);
        assertNoMatch("aa", regex);
        assertNoMatch("", regex);
        assertNoMatch("-", regex);
    }

    @Test
    public void anyWord() {
        String regex = regx().anyWord().toString();

        assertMatches("o", regex);
        assertMatches("ole", regex);
        assertMatches("14", regex);
        assertMatches("_", regex);
        assertMatches("ole14", regex);
        assertMatches("ole_14", regex);
        assertMatches("14_ole", regex);
        assertMatches("_ole_14", regex);

        assertNoMatch(".", regex);
        assertNoMatch("ole-14", regex);
        assertNoMatch("ole?", regex);
        assertNoMatch(".ole", regex);
    }

    @Test
    public void digit() {
        String regex = regx().digit().toString();

        assertMatches("0", regex);
        assertMatches("9", regex);

        assertNoMatch("", regex);
        assertNoMatch(".", regex);
        assertNoMatch("a", regex);
        assertNoMatch("_", regex);
        assertNoMatch("11", regex);
        assertNoMatch("-", regex);
    }

    @Test
    public void nonDigit() {
        String regex = regx().nonDigit().toString();

        assertMatches(".", regex);
        assertMatches("a", regex);
        assertMatches("_", regex);
        assertMatches("-", regex);

        assertNoMatch("0", regex);
        assertNoMatch("9", regex);
    }

    @Test
    public void group() {
        String regex = regx().literal("aa").group().anyTimes().toString();

        assertMatches("aa", regex);
        assertMatches("aaaa", regex);
        assertMatches("aaaaaa", regex);

        assertNoMatch("a", regex);
        assertNoMatch("aaa", regex);
        assertNoMatch("aaaaa", regex);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void groupNoCapturing() {
        String regex = regx(false).literal("aa").group().anyTimes().toString();
        assertParameterMatches("aa", regex, 1, null);
    }

    @Test
    public void capturingGroup() {
        RegX regex2 = regx().regex("cd").capturingGroup();
        RegX regex3 = regx().regex("ef");
        RegX regex1 = regx().regex("ab").followedBy(regex2).followedBy(regex3);

        String regex = regex1.toString();

        assertMatches("abcdef", regex);
        assertParameterMatches("abcdef", regex, 1, "cd");
    }

    @Test
    public void namedCapturingGroup() {
        String groupName = "myGroup";
        RegX regex2 = regx().regex("cd").capturingGroup(groupName);
        RegX regex3 = regx().regex("ef");
        RegX regex1 = regx().regex("ab").followedBy(regex2).followedBy(regex3);

        String regex = regex1.toString();

        assertMatches("abcdef", regex);
        assertParameterMatches("abcdef", regex, groupName, "cd");
    }

    @Test
    public void oneOrMoreTimes() {
        String regex = regx().anyChar().oneOrMoreTimes().toString();

        assertMatches("a", regex);
        assertMatches("bb", regex);
        assertMatches(".....", regex);
        assertMatches(")P(/WY893gv`\\9p`r78vb'", regex);

        assertNoMatch("", regex);
    }

    @Test
    public void literalTestChar() {
        char c = '*';
        String regex = regx().literal(c).toString();
        assertMatches(String.valueOf(c), regex);
    }

    @Test
    public void literalTestString() {
        String literal = "*+*\\\\s";
        String regex = regx().literal(literal).toString();
        assertMatches(literal, regex);
    }

    @Test
    public void or() {
        RegX regex1 = regx().literal("achili");
        RegX regex2 = regx().literal("pu");
        RegX regex3 = regx().literal("apu");
        String regex = regex1.or(regex2).or(regex3).toString();

        assertMatches("achili", regex);
        assertMatches("pu", regex);
        assertMatches("apu", regex);

        assertNoMatch("achilipuapu", regex);
        assertNoMatch("achili|pu|apu", regex);
    }

    @Test
    public void oneOf() {
        assertMatches("aaafaaa", regx().literal("aaa").oneOf("fge").literal("aaa").toString());
        assertNoMatch("aaabaaa", regx().literal("aaa").oneOf("fge").literal("aaa").toString());
    }

    @Test
    public void noneOf() {
        assertNoMatch("aaafaaa", regx().literal("aaa").noneOf("fge").literal("aaa").toString());
        assertMatches("aaabaaa", regx().literal("aaa").noneOf("fge").literal("aaa").toString());
    }

    @Test
    public void range() {
        assertMatches("aaasaaa", regx().literal("aaa").range('b', 'y').literal("aaa").toString());
        assertNoMatch("aaazaaa", regx().literal("aaa").range('b', 'y').literal("aaa").toString());
    }

    @Test
    public void rangeExclude() {
        assertNoMatch("aaasaaa", regx().literal("aaa").rangeExclude('b', 'y').literal("aaa").toString());
        assertMatches("aaazaaa", regx().literal("aaa").rangeExclude('b', 'y').literal("aaa").toString());
    }

    @Test
    public void intersectExclude() {
        assertMatches("a", regx().range('a', 'c').intersectExclude('b', 'd').toString());
        assertNoMatch("b", regx().range('a','c').intersectExclude('b','d').toString());
        assertNoMatch("c", regx().range('a', 'c').intersectExclude('b', 'd').toString());
        assertNoMatch("d", regx().range('a', 'c').intersectExclude('b', 'd').toString());

        assertNoMatch("b", regx().rangeExclude('a', 'c').intersectExclude('d', 'y').toString());
        assertNoMatch("e", regx().rangeExclude('a', 'c').intersectExclude('d', 'y').toString());
        assertMatches("z", regx().rangeExclude('a', 'c').intersectExclude('d', 'y').toString());
    }

    @Test
    public void intersect() {
        assertNoMatch("a", regx().oneOf("abcd").intersect('c', 'z').toString());
        assertNoMatch("b", regx().oneOf("abcd").intersect('c', 'z').toString());
        assertMatches("c", regx().oneOf("abcd").intersect('c', 'z').toString());
        assertMatches("d", regx().oneOf("abcd").intersect('c', 'z').toString());
        assertNoMatch("e", regx().oneOf("abcd").intersect('c', 'z').toString());

        assertMatches("b", regx().range('a', 'c').intersect('d', 'y').toString());
        assertMatches("e", regx().range('a', 'c').intersect('d', 'y').toString());
        assertNoMatch("z", regx().range('a', 'c').intersect('d', 'y').toString());
    }

    @Test
    public void intersectNoneOf() {
        assertMatches("a", regx().range('a', 'b').intersectNoneOf("bc").toString());
        assertNoMatch("b", regx().range('a', 'b').intersectNoneOf("bc").toString());
        assertNoMatch("c", regx().range('a', 'b').intersectNoneOf("bc").toString());

        assertNoMatch("a", regx().noneOf("ab").intersectNoneOf("bc").toString());
        assertNoMatch("b", regx().noneOf("ab").intersectNoneOf("bc").toString());
        assertNoMatch("c", regx().noneOf("ab").intersectNoneOf("bc").toString());
        assertMatches("d", regx().noneOf("ab").intersectNoneOf("bc").toString());
    }

    @Test
    public void intersectOneOf() {
        assertNoMatch("a", regx().range('a', 'c').intersectOneOf("bc").toString());
        assertMatches("b", regx().range('a', 'c').intersectOneOf("bc").toString());
        assertMatches("c", regx().range('a', 'c').intersectOneOf("bc").toString());

        assertMatches("a", regx().oneOf("ab").intersectOneOf("cd").toString());
        assertMatches("b", regx().oneOf("ab").intersectOneOf("cd").toString());
        assertMatches("c", regx().oneOf("ab").intersectOneOf("cd").toString());
        assertMatches("d", regx().oneOf("ab").intersectOneOf("cd").toString());
        assertNoMatch("e", regx().oneOf("ab").intersectOneOf("cd").toString());
    }
}
