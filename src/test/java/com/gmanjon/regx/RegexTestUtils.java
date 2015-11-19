package com.gmanjon.regx;

import org.junit.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gmanjon on 18/11/2015.
 */
public class RegexTestUtils {

    public static void assertMatches(String match, String regex) {
        Assert.assertTrue(Pattern.matches(regex, match));
    }

    public static void assertNoMatch(String noMatch, String regex) {
        Assert.assertFalse(noMatch.matches(regex));
    }

    public static void assertParameterMatches(String string, String regex, int groupIndex, String paramExpectedValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        matcher.find();
        Assert.assertEquals(paramExpectedValue, matcher.group(groupIndex));
    }

    public static void assertParameterMatches(String string, String regex, String groupName, String paramExpectedValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        matcher.find();
        Assert.assertEquals(paramExpectedValue, matcher.group(groupName));
    }

    public static void assertParameterNoMatch(String string, String regex, int paramIndex, String paramUnexpectedValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        matcher.find();
        Assert.assertNotEquals(paramUnexpectedValue, matcher.group(paramIndex));
    }

    public static void assertParameterNoMatch(String string, String regex, String groupName, String paramUnexpectedValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        matcher.find();
        Assert.assertNotEquals(paramUnexpectedValue, matcher.group(groupName));
    }
}
