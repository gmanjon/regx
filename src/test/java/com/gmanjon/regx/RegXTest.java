package com.gmanjon.regx;

import static com.gmanjon.regx.RegX.regx;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegXTest {

    @Test
    public void anyChar() {
        String regex = regx().anyChar().getRegex();

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
        String regex = regx().anyChar().anyTimes().getRegex();

        assertMatches("a", regex);
        assertMatches("bb", regex);
        assertMatches(".....", regex);
        assertMatches("", regex);
        assertMatches(")P(/WY893gv`\\9p`r78vb'", regex);
    }

    @Test
    public void alphanumericChar() {
        String regex = regx().alphanumericChar().getRegex();

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
        String regex = regx().anyWord().getRegex();

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
        String regex = regx().digit().getRegex();

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
    public void group() {
        String regex = regx().literal("aa").group().anyTimes().getRegex();

        assertMatches("aa", regex);
        assertMatches("aaaa", regex);
        assertMatches("aaaaaa", regex);

        assertNoMatch("a", regex);
        assertNoMatch("aaa", regex);
        assertNoMatch("aaaaa", regex);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void groupNoCapturing() {
        String regex = regx().literal("aa").group().anyTimes().getRegex();
        assertParameterMatches("aa", regex, 1, null);
    }

    @Test
    public void capturingGroup() {
        RegX regex2 = regx().regex("cd").capturingGroup();
        RegX regex3 = regx().regex("ef");
        RegX regex1 = regx().regex("ab").followedBy(regex2).followedBy(regex3);

        String regex = regex1.getRegex();

        assertMatches("abcdef", regex);
        assertParameterMatches("abcdef", regex, 1, "cd");
    }

    @Test
    public void namedCapturingGroup() {
        String groupName = "myGroup";
        RegX regex2 = regx().regex("cd").capturingGroup(groupName);
        RegX regex3 = regx().regex("ef");
        RegX regex1 = regx().regex("ab").followedBy(regex2).followedBy(regex3);

        String regex = regex1.getRegex();

        assertMatches("abcdef", regex);
        assertParameterMatches("abcdef", regex, groupName, "cd");
    }

    @Test
    public void oneOrMoreTimes() {
        String regex = regx().anyChar().oneOrMoreTimes().getRegex();

        assertMatches("a", regex);
        assertMatches("bb", regex);
        assertMatches(".....", regex);
        assertMatches(")P(/WY893gv`\\9p`r78vb'", regex);

        assertNoMatch("", regex);
    }

    @Test
    public void literalTestChar() {
        char c = '*';
        String regex = regx().literal(c).getRegex();
        assertMatches(String.valueOf(c), regex);
    }

    @Test
    public void literalTestString() {
        String literal = "*+*\\\\s";
        String regex = regx().literal(literal).getRegex();
        assertMatches(literal, regex);
    }

    @Test
    public void or() {
        RegX regex1 = regx().literal("achili");
        RegX regex2 = regx().literal("pu");
        RegX regex3 = regx().literal("apu");
        String regex = regex1.or(regex2).or(regex3).getRegex();

        assertMatches("achili", regex);
        assertMatches("pu", regex);
        assertMatches("apu", regex);

        assertNoMatch("achilipuapu", regex);
        assertNoMatch("achili|pu|apu", regex);
    }

    private void assertMatches(String match, String regex) {
        Assert.assertTrue(match.matches(regex));
    }

    private void assertNoMatch(String noMatch, String regex) {
        Assert.assertFalse(noMatch.matches(regex));
    }

    private void assertParameterMatches(String string, String regex, int groupIndex, String paramExpectedValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        matcher.matches();
        Assert.assertEquals(paramExpectedValue, matcher.group(groupIndex));
    }

    private void assertParameterMatches(String string, String regex, String groupName, String paramExpectedValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        matcher.matches();
        Assert.assertEquals(paramExpectedValue, matcher.group(groupName));
    }

    private void assertParameterNoMatch(String string, String regex, int paramIndex, String paramUnexpectedValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        matcher.matches();
        Assert.assertNotEquals(paramUnexpectedValue, matcher.group(paramIndex));
    }

}
