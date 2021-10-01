# Regex builder library

## Usage

Basic usage:

You can either create a new object to start to regex:
```java
RegX regx = new RegX();
regx.anyChar().anyTimes();
```

or you can just simply import the static method ```regx()``` and start to regex right away:
```java
static import com.gmanjon.regx.RegX.regx;
...
regx().anyChar().anyTimes();
```

* Create a regular expression meaning "any _a_ followed by an even number of _b's_"

Regular Expression: ```a*(bb)*```<br />
RegX Syntax:
```java
String regx = regx().literal("a").anyTimes().followedBy(regx().literalGroup("bb").anyTimes()).toString();
```

* Create a regular expression for validating emails (may be could be more accurate to mail specifications, but will do for example pourpuses)

Regular Expression: ```[a-zA-Z](\.?\w+)*@(\w+)*\.[a-zA-Z]{2,5}```<br />
RegX Syntax:
```java
RegX startAccountName = regx().alphabeticChar();
RegX continueAccountName = regx().literal('.').optional().anyWord().group().anyTimes();
RegX at = regx().literal('@');
RegX domain = regx().anyWord().group().anyTimes().literal('.').alphabeticChar(2, 5);
String regx = startAccountName.followedBy(continueAccountName).followedBy(at).followedBy(domain).toString();
```

## Java 7 Pattern Support

All examples assume the RegX object is already created in a variables called ```r```, either via static method or constructor.

:red_circle: Not yet supported. Be patient, it will be.

### Characters

<table>
    <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>x</td>
        <td>The character x</td>
        <td> `r.literal('x')` </td>
    </tr>
    <tr>
        <td>\\</td>
        <td>The backslash character</td>
        <td> ```r.literal('\\')``` </td>
    </tr>
    <tr>
        <td>\0n</td>
        <td>The character with octal value 0n (0 <= n <= 7)</td>
        <td>```r.literal(0n)```</td>
    </tr>
    <tr>
        <td>\0nn</td>
        <td>The character with octal value 0nn (0 <= n <= 7)</td>
        <td>```r.literal(0nn)```</td>
    </tr>
    <tr>
        <td>\0mnn</td>
        <td>The character with octal value 0mnn (0 <= m <= 3, 0 <= n <= 7)</td>
        <td>{{r.literal(0mnn)}}</td>
    </tr>
    <tr>
        <td>\xhh</td>
        <td>The character with hexadecimal value 0xhh</td>
        <td>```r.literal(0xhh)```</td>
    </tr>
    <tr>
        <td>\uhhhh</td>
        <td>The character with hexadecimal value 0xhhhh</td>
        <td>```r.literal(0xhhhh)```</td>
    </tr>
    <tr>
        <td>\x{h...h}</td>
        <td>The character with hexadecimal value 0xh...h (Character.MIN_CODE_POINT  <= 0xh...h <=  Character.MAX_CODE_POINT)</td>
        <td>```r.literal(0xh...h)```</td>
    </tr>
    <tr>
        <td>\t</td>
        <td>The tab character ('\u0009')</td>
        <td>```r.literal('\t')```</td>
    </tr>
    <tr>
        <td>\n</td>
        <td>The newline (line feed) character ('\u000A')</td>
        <td>```r.literal('\n')```</td>
    </tr>
    <tr>
        <td>\r</td>
        <td>The carriage-return character ('\u000D')</td>
        <td>```r.literal('\r')```</td>
    </tr>
    <tr>
        <td>\f</td>
        <td>The form-feed character ('\u000C')</td>
        <td>```r.literal('\f')```</td>
    </tr>
    <tr>
        <td>\a</td>
        <td>The alert (bell) character ('\u0007')</td>
        <td>```r.literal(0x0007)``` or ```r.regex("\a")```</td>
    </tr>
    <tr>
        <td>\e</td>
        <td>The escape character ('\u001B')</td>
        <td>```r.literal(0x001B)``` or ```r.regex("\e")```</td>
    </tr>
    <tr>
        <td>\cx</td>
        <td>The control character corresponding to x</td>
        <td>```r.literal('\x')```</td>
    </tr>
</table>

### Character classes
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>[abc]</td>
        <td>a, b, or c (simple class)</td>
        <td>```r.oneOf("abc")```</td>
    </tr>
    <tr>
        <td>[^abc]</td>
        <td>Any character except a, b, or c (negation)</td>
        <td>```r.noneOf("abc")```</td>
    </tr>
    <tr>
        <td>[a-zA-Z]</td>
        <td>a through z or A through Z, inclusive (range)</td>
        <td>```r.range('a','z','A,'Z')```</td>
    </tr>
    <tr>
        <td>[a-d[m-p]]</td>
        <td>a through d, or m through p: [a-dm-p] (union)</td>
        <td>```r.range('a','d','m','p')```</td>
    </tr>
    <tr>
        <td>[a-z&&[def]]</td>
        <td>d, e, or f (intersection)</td>
        <td>```r.range('a','z').intersectOneOf("def")```</td>
    </tr>
    <tr>
        <td>[a-z&&[^bc]]</td>
        <td>a through z, except for b and c: [ad-z] (subtraction)</td>
        <td>```r.range('a','z').intersectNoneOf("bc")```</td>
    </tr>
    <tr>
        <td>[a-z&&[^m-p]]</td>
        <td>a through z, and not m through p: [a-lq-z](subtraction)</td>
        <td>```r.range('a','z').intersectExclude('m','p')```</td>
    </tr>
</table>

### Predefined character classes
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>.</td>
        <td>Any character (may or may not match line terminators)</td>
        <td>```r.anyChar()```</td>
    </tr>
    <tr>
        <td>\d</td>
        <td>A digit: [0-9]</td>
        <td>```r.digit()```</td>
    </tr>
    <tr>
        <td>\D</td>
        <td>A non-digit: [^0-9]</td>
        <td>```r.nonDigit()```</td>
    </tr>
    <tr>
        <td>\s</td>
        <td>A whitespace character: [ \t\n\x0B\f\r]</td>
        <td></td>
    </tr>
    <tr>
        <td>\S</td>
        <td>A non-whitespace character: [^\s]</td>
        <td></td>
    </tr>
    <tr>
        <td>\w</td>
        <td>A word character: [a-zA-Z_0-9]</td>
        <td></td>
    </tr>
    <tr>
        <td>\W</td>
        <td>A non-word character: [^\w]</td>
        <td></td>
    </tr>
</table>

### POSIX character classes (US-ASCII only)
<table>
    <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>\p{Lower}</td>
        <td>A lower-case alphabetic character: [a-z]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Upper}</td>
        <td>An upper-case alphabetic character:[A-Z]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{ASCII}</td>
        <td>All ASCII:[\x00-\x7F]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Alpha}</td>
        <td>An alphabetic character:[\p{Lower}\p{Upper}]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Digit}</td>
        <td>A decimal digit: [0-9]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Alnum}</td>
        <td>An alphanumeric character:[\p{Alpha}\p{Digit}]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Punct}</td>
        <td>Punctuation: One of !"###$%&'()*+,-./:;<=>?@[\]^_`{|}~</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Graph}</td>
        <td>A visible character: [\p{Alnum}\p{Punct}]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Print}</td>
        <td>A printable character: [\p{Graph}\x20]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Blank}</td>
        <td>A space or a tab: [ \t]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Cntrl}</td>
        <td>A control character: [\x00-\x1F\x7F]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{XDigit}</td>
        <td>A hexadecimal digit: [0-9a-fA-F]</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Space}</td>
        <td>A whitespace character: [ \t\n\x0B\f\r]</td>
        <td>:red_circle:</td>
    </tr>
</table>

### java.lang.Character classes (simple java character type)
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>\p{javaLowerCase}</td>
        <td>Equivalent to java.lang.Character.isLowerCase()</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{javaUpperCase}</td>
        <td>Equivalent to java.lang.Character.isUpperCase()</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{javaWhitespace}</td>
        <td>Equivalent to java.lang.Character.isWhitespace()</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{javaMirrored}</td>
        <td>Equivalent to java.lang.Character.isMirrored()</td>
        <td>:red_circle:</td>
    </tr>
</table>

### Classes for Unicode scripts, blocks, categories and binary properties
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>\p{IsLatin}</td>
        <td>A Latin script character (script)</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{InGreek}</td>
        <td>A character in the Greek block (block)</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Lu}</td>
        <td>An uppercase letter (category)</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{IsAlphabetic}</td>
        <td>An alphabetic character (binary property)</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\p{Sc}</td>
        <td>A currency symbol</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>\P{InGreek}</td>
        <td>Any character except one in the Greek block (negation)</td>
        <td>:red_circle:</td>
    </tr>
    <tr>
        <td>[\p{L}&&[^\p{Lu}]] </td>
        <td>Any letter except an uppercase letter (subtraction)</td>
        <td>:red_circle:</td>
    </tr>
</table>

### Boundary matchers
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>^</td>
        <td>The beginning of a line</td>
        <td>```r.startOfLine()```</td>
    </tr>
    <tr>
        <td>$</td>
        <td>The end of a line</td>
        <td>```r.endOfLine()```</td>
    </tr>
    <tr>
        <td>\b</td>
        <td>A word boundary</td>
        <td></td>
    </tr>
    <tr>
        <td>\B</td>
        <td>A non-word boundary</td>
        <td></td>
    </tr>
    <tr>
        <td>\A</td>
        <td>The beginning of the input</td>
        <td></td>
    </tr>
    <tr>
        <td>\G</td>
        <td>The end of the previous match</td>
        <td></td>
    </tr>
    <tr>
        <td>\Z</td>
        <td>The end of the input but for the final terminator, if any</td>
        <td></td>
    </tr>
    <tr>
        <td>\z</td>
        <td>The end of the input</td>
        <td></td>
    </tr>
</table>

### Greedy quantifiers
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>X?</td>
        <td>X, once or not at all</td>
        <td>```r.literal('X').optional()```</td>
    </tr>
    <tr>
        <td>X*</td>
        <td>X, zero or more times</td>
        <td>```r.literal('X').anyTimes()```</td>
    </tr>
    <tr>
        <td>X+</td>
        <td>X, one or more times</td>
        <td>```r.literal('X').oneOrMoreTimes()```</td>
    </tr>
    <tr>
        <td>X{n}</td>
        <td>X, exactly n times</td>
        <td>```r.literal('X').times(n)```</td>
    </tr>
    <tr>
        <td>X{n,}</td>
        <td>X, at least n times</td>
        <td>```r.literal('X').times(n, -1)```</td>
    </tr>
    <tr>
        <td>X{n,m}</td>
        <td>X, at least n but not more than m times</td>
        <td>```r.literal('X').times(n, m)```</td>
    </tr>
</table>

### Reluctant quantifiers
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>X??</td>
        <td>X, once or not at all</td>
        <td>```r.literal('X').optional()```</td>
    </tr>
    <tr>
        <td>X*?</td>
        <td>X, zero or more times</td>
        <td>```r.literal('X').anyTimesLazy()```</td>
    </tr>
    <tr>
        <td>X+?</td>
        <td>X, one or more times</td>
        <td>```r.literal('X').oneOrMoreTimesLazy()```</td>
    </tr>
    <tr>
        <td>X{n}?</td>
        <td>X, exactly n times</td>
        <td>```r.literal('X').times(n)```</td>
    </tr>
    <tr>
        <td>X{n,}?</td>
        <td>X, at least n times</td>
        <td></td>
    </tr>
    <tr>
        <td>X{n,m}?</td>
        <td>X, at least n but not more than m times</td>
        <td></td>
    </tr>
</table>

### Possessive quantifiers
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>X?+</td>
        <td>X, once or not at all</td>
        <td></td>
    </tr>
    <tr>
        <td>X*+</td>
        <td>X, zero or more times</td>
        <td></td>
    </tr>
    <tr>
        <td>X++</td>
        <td>X, one or more times</td>
        <td></td>
    </tr>
    <tr>
        <td>X{n}+</td>
        <td>X, exactly n times</td>
        <td></td>
    </tr>
    <tr>
        <td>X{n,}+</td>
        <td>X, at least n times</td>
        <td></td>
    </tr>
    <tr>
        <td>X{n,m}+</td>
        <td>X, at least n but not more than m times</td>
        <td></td>
    </tr>
</table>

### Logical operators
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>XY</td>
        <td>X followed by Y</td>
        <td></td>
    </tr>
    <tr>
        <td>X|Y</td>
        <td>Either X or Y</td>
        <td></td>
    </tr>
    <tr>
        <td>(X)</td>
        <td>X, as a capturing group</td>
        <td></td>
    </tr>
</table>

### Back references
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>\n</td>
        <td>Whatever the nth capturing group matched</td>
        <td></td>
    </tr>
    <tr>
        <td>\k<name></td>
        <td>Whatever the named-capturing group "name" matched</td>
        <td></td>
    </tr>
</table>

### Quotation
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>\</td>
        <td>Nothing, but quotes the following character</td>
        <td></td>
    </tr>
    <tr>
        <td>\Q</td>
        <td>Nothing, but quotes all characters until \E</td>
        <td></td>
    </tr>
    <tr>
        <td>\E</td>
        <td>Nothing, but ends quoting started by \Q</td>
        <td></td>
    </tr>
</table>

### Special constructs (named-capturing and non-capturing)
<table>
      <tr>
        <th>Construct</th>
        <th>Matches</th>
        <th>Example</th>
    </tr>
    <tr>
        <td>(?<name>X)</td>
        <td>X, as a named-capturing group</td>
        <td></td>
    </tr>
    <tr>
        <td>(?:X)</td>
        <td>X, as a non-capturing group</td>
        <td></td>
    </tr>
    <tr>
        <td>(?idmsuxU-idmsuxU) </td>
        <td>Nothing, but turns match flags i d m s u x U on - off</td>
        <td></td>
    </tr>
    <tr>
        <td>(?idmsux-idmsux:X)  </td>
        <td>X, as a non-capturing group with the given flags i d m s u x on - off</td>
        <td></td>
    </tr>
    <tr>
        <td>(?=X)</td>
        <td>X, via zero-width positive lookahead</td>
        <td></td>
    </tr>
    <tr>
        <td>(?!X)</td>
        <td>X, via zero-width negative lookahead</td>
        <td></td>
    </tr>
    <tr>
        <td>(?<=X)</td>
        <td>X, via zero-width positive lookbehind</td>
        <td></td>
    </tr>
    <tr>
        <td>(?<!X)</td>
        <td>X, via zero-width negative lookbehind</td>
        <td></td>
    </tr>
    <tr>
        <td>(?>X)</td>
        <td>X, as an independent, non-capturing group</td>
        <td></td>
    </tr>
</table>
