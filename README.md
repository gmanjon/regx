<html>
	<head>
		<style>
			span {
				display: inline-block;
				width: 30px;
			}
			span.red {
				color: #FF0000;
			}
			span.green {
				color: #00FF00;
			}
		</style>
	</head>
	<body>
			
		<h1>Regex builder library</h1>

		<h4>Supported constructions:</h4>
		<h2>Characters</h2>
		<table>
			<tr>
				<td><span class="green">&#x2714;</span></td>
				<td>x</td>
				<td>The character x</td></tr>
			<tr>
				<td><span class="green">&#x2714;</span></td>
				<td>\\</td>
				<td>The backslash character</td></tr>
			<tr>
				<td><span class="green">&#x2714;</span></td>
				<td>\0n</td>
				<td>The character with octal value 0n (0 <= n <= 7)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\0nn</td>
				<td>The character with octal value 0nn (0 <= n <= 7)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\0mnn</td>
				<td>The character with octal value 0mnn (0 <= m <= 3, 0 <= n <= 7)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\xhh</td>
				<td>The character with hexadecimal value 0xhh</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\uhhhh</td>
				<td>The character with hexadecimal value 0xhhhh</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\x{h...h}</td>
				<td>The character with hexadecimal value 0xh...h (Character.MIN_CODE_POINT  <= 0xh...h <=  Character.MAX_CODE_POINT)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\t</td>
				<td>The tab character ('\u0009')</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\n</td>
				<td>The newline (line feed) character ('\u000A')</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\r</td>
				<td>The carriage-return character ('\u000D')</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\f</td>
				<td>The form-feed character ('\u000C')</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\a</td>
				<td>The alert (bell) character ('\u0007')</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\e</td>
				<td>The escape character ('\u001B')</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\cx</td>
				<td>The control character corresponding to x</td></tr>
		</table>

		<h2>Character classes</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>[abc]</td>
				<td>a, b, or c (simple class)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>[^abc]</td>
				<td>Any character except a, b, or c (negation)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>[a-zA-Z]</td>
				<td>a through z or A through Z, inclusive (range)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>[a-d[m-p]]</td>
				<td>a through d, or m through p: [a-dm-p] (union)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>[a-z&&[def]]</td>
				<td>d, e, or f (intersection)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>[a-z&&[^bc]]</td>
				<td>a through z, except for b and c: [ad-z] (subtraction)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>[a-z&&[^m-p]]</td>
				<td>a through z, and not m through p: [a-lq-z](subtraction)</td></tr>
		</table>

		<h2>Predefined character classes</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>.</td>
				<td>Any character (may or may not match line terminators)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\d</td>
				<td>A digit: [0-9]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\D</td>
				<td>A non-digit: [^0-9]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\s</td>
				<td>A whitespace character: [ \t\n\x0B\f\r]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\S</td>
				<td>A non-whitespace character: [^\s]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\w</td>
				<td>A word character: [a-zA-Z_0-9]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\W</td>
				<td>A non-word character: [^\w]</td></tr>
		</table>

		<h2>POSIX character classes (US-ASCII only)</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Lower}</td>
				<td>A lower-case alphabetic character: [a-z]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Upper}</td>
				<td>An upper-case alphabetic character:[A-Z]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{ASCII}</td>
				<td>All ASCII:[\x00-\x7F]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Alpha}</td>
				<td>An alphabetic character:[\p{Lower}\p{Upper}]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Digit}</td>
				<td>A decimal digit: [0-9]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Alnum}</td>
				<td>An alphanumeric character:[\p{Alpha}\p{Digit}]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Punct}</td>
				<td>Punctuation: One of !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Graph}</td>
				<td>A visible character: [\p{Alnum}\p{Punct}]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Print}</td>
				<td>A printable character: [\p{Graph}\x20]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Blank}</td>
				<td>A space or a tab: [ \t]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Cntrl}</td>
				<td>A control character: [\x00-\x1F\x7F]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{XDigit}</td>
				<td>A hexadecimal digit: [0-9a-fA-F]</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Space}</td>
				<td>A whitespace character: [ \t\n\x0B\f\r]</td></tr>
		</table>

		<h2>java.lang.Character classes (simple java character type)</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{javaLowerCase}</td>
				<td>Equivalent to java.lang.Character.isLowerCase()</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{javaUpperCase}</td>
				<td>Equivalent to java.lang.Character.isUpperCase()</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{javaWhitespace}</td>
				<td>Equivalent to java.lang.Character.isWhitespace()</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{javaMirrored}</td>
				<td>Equivalent to java.lang.Character.isMirrored()</td></tr>
		</table>

		<h2>Classes for Unicode scripts, blocks, categories and binary properties</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{IsLatin}</td>
				<td>A Latin script character (script)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{InGreek}</td>
				<td>A character in the Greek block (block)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Lu}</td>
				<td>An uppercase letter (category)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{IsAlphabetic}</td>
				<td>An alphabetic character (binary property)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\p{Sc}</td>
				<td>A currency symbol</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\P{InGreek}</td>
				<td>Any character except one in the Greek block (negation)</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>[\p{L}&&[^\p{Lu}]] </td>
				<td>Any letter except an uppercase letter (subtraction)</td></tr>
		</table>

		<h2>Boundary matchers</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>^</td>
				<td>The beginning of a line</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>$</td>
				<td>The end of a line</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\b</td>
				<td>A word boundary</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\B</td>
				<td>A non-word boundary</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\A</td>
				<td>The beginning of the input</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\G</td>
				<td>The end of the previous match</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\Z</td>
				<td>The end of the input but for the final terminator, if any</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\z</td>
				<td>The end of the input</td></tr>
		</table>

		<h2>Greedy quantifiers</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X?</td>
				<td>X, once or not at all</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X*</td>
				<td>X, zero or more times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X+</td>
				<td>X, one or more times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X{n}</td>
				<td>X, exactly n times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X{n,}</td>
				<td>X, at least n times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X{n,m}</td>
				<td>X, at least n but not more than m times</td></tr>
		</table>

		<h2>Reluctant quantifiers</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X??</td>
				<td>X, once or not at all</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X*?</td>
				<td>X, zero or more times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X+?</td>
				<td>X, one or more times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X{n}?</td>
				<td>X, exactly n times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X{n,}?</td>
				<td>X, at least n times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X{n,m}?</td>
				<td>X, at least n but not more than m times</td></tr>
		</table>

		<h2>Possessive quantifiers</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X?+</td>
				<td>X, once or not at all</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X*+</td>
				<td>X, zero or more times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X++</td>
				<td>X, one or more times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X{n}+</td>
				<td>X, exactly n times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X{n,}+</td>
				<td>X, at least n times</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X{n,m}+</td>
				<td>X, at least n but not more than m times</td></tr>
		</table>

		<h2>Logical operators</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>XY</td>
				<td>X followed by Y</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>X|Y</td>
				<td>Either X or Y</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(X)</td>
				<td>X, as a capturing group</td></tr>
		</table>

		<h2>Back references</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\n</td>
				<td>Whatever the nth capturing group matched</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\k<name></td>
				<td>Whatever the named-capturing group "name" matched</td></tr>
		</table>

		<h2>Quotation</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\</td>
				<td>Nothing, but quotes the following character</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\Q</td>
				<td>Nothing, but quotes all characters until \E</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>\E</td>
				<td>Nothing, but ends quoting started by \Q</td></tr>
		</table>

		<h2>Special constructs (named-capturing and non-capturing)</h2>
		<table>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(?<name>X)</td>
				<td>X, as a named-capturing group</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(?:X)</td>
				<td>X, as a non-capturing group</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(?idmsuxU-idmsuxU) </td>
				<td>Nothing, but turns match flags i d m s u x U on - off</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(?idmsux-idmsux:X)  </td>
				<td>X, as a non-capturing group with the given flags i d m s u x on - off</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(?=X)</td>
				<td>X, via zero-width positive lookahead</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(?!X)</td>
				<td>X, via zero-width negative lookahead</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(?<=X)</td>
				<td>X, via zero-width positive lookbehind</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(?<!X)</td>
				<td>X, via zero-width negative lookbehind</td></tr>
			<tr>
				<td><span class="red">&#x2718;</span></td>
				<td>(?>X)</td>
				<td>X, as an independent, non-capturing group</td></tr>
		</table>
	</body>
</html>