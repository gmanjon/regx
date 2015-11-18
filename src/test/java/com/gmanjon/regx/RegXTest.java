package com.gmanjon.regx;

import static com.gmanjon.regx.RegX.regx;
import static com.gmanjon.regx.RegexTestUtils.assertMatches;
import static com.gmanjon.regx.RegexTestUtils.assertNoMatch;
import static com.gmanjon.regx.RegexTestUtils.assertParameterMatches;
import org.junit.Test;

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


}
