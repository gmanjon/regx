package com.gmanjon.regx;

import static com.gmanjon.regx.RegX.regx;
import static com.gmanjon.regx.RegexTestUtils.assertMatches;
import static com.gmanjon.regx.RegexTestUtils.assertNoMatch;
import org.junit.Test;

/**
 * Created by gmanjon on 18/11/2015.
 */

public class ReadmeSamplesTest {

    @Test
    public void basicUsageTest() {
        String regx = regx().literal("a").anyTimes().followedBy(regx().literalGroup("bb").anyTimes()).toString();
        System.out.println("Regex: " + regx);

        assertMatches("", regx);
        assertMatches("a", regx);
        assertMatches("bb", regx);
        assertMatches("aaa", regx);
        assertMatches("bbbb", regx);
        assertMatches("aaabbbb", regx);

        assertNoMatch("b", regx);
        assertNoMatch("ab", regx);
        assertNoMatch("bbb", regx);
        assertNoMatch("abbb", regx);
    }

    @Test
    public void mailValidatorTest() {
        RegX startAccountName = regx().alphabeticChar();
        System.out.println("Regex: " + startAccountName);

        RegX continueAccountName = regx().literal('.').optional().anyWord().group().anyTimes();
        System.out.println("Regex: " + continueAccountName);

        RegX at = regx().literal('@');
        System.out.println("Regex: " + at);

        RegX domain = regx().anyWord().group().anyTimes().literal('.').alphabeticChar(2, 5);
        System.out.println("Regex: " + domain);

        String regx = startAccountName.followedBy(continueAccountName).followedBy(at).followedBy(domain).toString();
        System.out.println("Regex: " + regx);

        assertMatches("asdc@asdc.com", regx);

        assertNoMatch("asdc?asdc@asdc.com", regx);
    }
}
