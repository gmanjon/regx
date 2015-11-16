package com.readableregex;

import static com.readableregex.RR.rr;

public class Test {

    public static void main(String[] args) {
        String regex1 = rr().anyChar()
                        .anyTimes()
                        .group()
                        .literal("achilipu").getRegex();

        String regex2 = rr().anyChar()
                        .anyTimes()
                        .group()
                        .literal("achilipu").getRegex();
        System.out.println("Regex: " + regex2);
    }
}
