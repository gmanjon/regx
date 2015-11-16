package com.gmanjon.regx;

import static com.gmanjon.regx.RegX.regx;
import org.junit.Assert;
import org.junit.Test;

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
//
//    @Test
//    public void wordChar() {
//        String regex = regx().wordChar().getRegex();
//        String expectedRegex = "\\w";
//        Assert.assertEquals(expectedRegex, regex);
//    }
//
//    @Test
//    public void anyWord() {
//        String regex = regx().anyWord().getRegex();
//        String expectedRegex = "\\w+";
//        Assert.assertEquals(expectedRegex, regex);
//    }
//
//    @Test
//    public void digit() {
//        String regex = regx().digit().getRegex();
//        String expectedRegex = "\\d";
//        Assert.assertEquals(expectedRegex, regex);
//    }
//
//    @Test
//    public void group() {
//        String regex = regx().group().getRegex();
//        Assert.assertTrue(regex.startsWith("(?:"));
//        Assert.assertTrue(regex.endsWith(")"));
//    }
//
//    @Test
//    public void capturingGroup() {
//        String regex = regx().capturingGroup(null).getRegex();
//        Assert.assertTrue(regex.startsWith("("));
//        Assert.assertFalse(regex.startsWith("(?:"));
//        Assert.assertTrue(regex.endsWith(")"));
//    }
//
//    @Test
//    public void oneOrMoreTimes() {
//        String regex = regx().oneOrMoreTimes().getRegex();
//        String expectedRegex = "+";
//        Assert.assertEquals(expectedRegex, regex);
//    }
//
//    @Test
//    public void literalTestChar() {
//        char c = 'a';
//        String regex = regx().literal(c).getRegex();
//        Assert.assertEquals(String.valueOf(c), regex);
//    }
//
//    @Test
//    public void literalTestString() {
//        String literal = "asdc";
//        String regex = regx().literal(literal).getRegex();
//        Assert.assertEquals(literal, regex);
//    }
//
//    @Test
//    public void insert() {
//        RegX regexSecondary = regx().literal("regexSecondary_");
//        String regexMain = regx().literal("regexMain").insert(regexSecondary).getRegex();
//        Assert.assertEquals("regexMain_regexSecondary", regexMain);
//    }

    private void assertMatches(String match, String regex) {
        Assert.assertTrue(match.matches(regex));
    }

    private void assertNoMatch(String noMatch, String regex) {
        Assert.assertFalse(noMatch.matches(regex));
    }

}
