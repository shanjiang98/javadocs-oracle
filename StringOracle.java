public class StringOracle {
    /**
     * Test oracle to check if the length() method returns the correct number of
     * Unicode code units in the string.
     * 
     * @param str the string to test
     * @return true if the method returns the correct length, false otherwise
     */
    boolean checkLength(String str) {
        if (str == null) {
            return false; // Assuming the method should handle null input gracefully, which usually isn't
                          // the case.
        }
        try {
            return str.length() == str.codePointCount(0, str.length());
        } catch (Exception e) {
            return false; // In case of any unexpected exception during the method execution.
        }
    }

    /**
     * Test oracle to check if the isEmpty() method correctly identifies an empty
     * string.
     * 
     * @param str the string to test
     * @return true if the method correctly identifies the string as empty or not
     *         empty, false otherwise
     */
    boolean checkIsEmpty(String str) {
        if (str == null) {
            return false; // Assuming the method should handle null input gracefully, which is generally
                          // not the case.
        }
        try {
            return str.isEmpty() == (str.length() == 0);
        } catch (Exception e) {
            return false; // In case of any unexpected exception during the method execution.
        }
    }

    /**
     * Test oracle to check if charAt method returns the correct character at the
     * given index.
     * 
     * @param str   the string to test
     * @param index the index of the character to retrieve
     * @return true if the correct character is returned, false otherwise
     */
    boolean checkCharacterRetrieval(String str, int index) {
        if (str == null || index < 0 || index >= str.length()) {
            return false;
        }
        try {
            return str.charAt(index) == str.toCharArray()[index];
        } catch (Exception e) {
            return false; // Handles unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if charAt method correctly handles index range
     * validation.
     * 
     * @param str   the string to test
     * @param index the index of the character to retrieve
     * @return true if the method correctly throws IndexOutOfBoundsException when
     *         necessary, false otherwise
     */
    boolean checkIndexRange(String str, int index) {
        try {
            char result = str.charAt(index);
            return true; // No exception means index is within valid range.
        } catch (IndexOutOfBoundsException e) {
            return index < 0 || index >= str.length();
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if charAt method correctly handles surrogate characters.
     * 
     * @param str   the string containing potential surrogate characters
     * @param index the index of the character to test
     * @return true if the method correctly returns a surrogate when expected, false
     *         otherwise
     */
    boolean checkSurrogateHandling(String str, int index) {
        if (str == null || index < 0 || index >= str.length()) {
            return false;
        }
        try {
            char ch = str.charAt(index);
            return Character.isSurrogate(ch) == (ch >= Character.MIN_SURROGATE && ch <= Character.MAX_SURROGATE);
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if codePointAt method returns the correct code point at
     * the given index.
     * 
     * @param str   the string to test
     * @param index the index of the code point to retrieve
     * @return true if the correct code point is returned, false otherwise
     */
    boolean checkCodePointRetrieval(String str, int index) {
        if (str == null || index < 0 || index >= str.length()) {
            return false;
        }
        try {
            int expectedCodePoint = Character.codePointAt(str, index);
            return str.codePointAt(index) == expectedCodePoint;
        } catch (Exception e) {
            return false; // Handles unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if codePointAt method correctly handles surrogate pairs.
     * 
     * @param str   the string to test
     * @param index the index of the character to test
     * @return true if the method returns the correct code point for a surrogate
     *         pair, false otherwise
     */
    boolean checkSurrogatePairHandling(String str, int index) {
        if (str == null || index < 0 || index >= str.length()) {
            return false;
        }
        try {
            char ch = str.charAt(index);
            if (Character.isHighSurrogate(ch) && index + 1 < str.length()
                    && Character.isLowSurrogate(str.charAt(index + 1))) {
                int surrogateCodePoint = Character.toCodePoint(ch, str.charAt(index + 1));
                return str.codePointAt(index) == surrogateCodePoint;
            } else {
                return str.codePointAt(index) == ch;
            }
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if codePointAt method correctly handles index
     * validation.
     * 
     * @param str   the string to test
     * @param index the index of the code point to retrieve
     * @return true if the method correctly throws IndexOutOfBoundsException when
     *         necessary, false otherwise
     */
    boolean checkIndexValidation(String str, int index) {
        try {
            int result = str.codePointAt(index);
            return true; // No exception means index is within valid range.
        } catch (IndexOutOfBoundsException e) {
            return index < 0 || index >= str.length();
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if codePointBefore method returns the correct code point
     * before the given index.
     * 
     * @param str   the string to test
     * @param index the index following the code point that should be returned
     * @return true if the correct code point is returned, false otherwise
     */
    boolean checkCodePointBefore(String str, int index) {
        if (str == null || index <= 1 || index > str.length()) {
            return false;
        }
        try {
            int expectedCodePoint = Character.codePointBefore(str, index);
            return str.codePointBefore(index) == expectedCodePoint;
        } catch (Exception e) {
            return false; // Handles unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if codePointBefore method correctly handles surrogate
     * pairs.
     * 
     * @param str   the string to test
     * @param index the index following the code point that should be returned
     * @return true if the method returns the correct code point for a surrogate
     *         pair, false otherwise
     */
    boolean checkSurrogatePairsHandling(String str, int index) {
        if (str == null || index <= 1 || index > str.length()) {
            return false;
        }
        try {
            char lowSurrogate = str.charAt(index - 1);
            if (Character.isLowSurrogate(lowSurrogate) && index - 2 >= 0) {
                char highSurrogate = str.charAt(index - 2);
                if (Character.isHighSurrogate(highSurrogate)) {
                    int surrogateCodePoint = Character.toCodePoint(highSurrogate, lowSurrogate);
                    return str.codePointBefore(index) == surrogateCodePoint;
                }
            }
            return str.codePointBefore(index) == lowSurrogate;
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if codePointBefore method correctly handles index
     * validation.
     * 
     * @param str   the string to test
     * @param index the index following the code point that should be returned
     * @return true if the method correctly throws IndexOutOfBoundsException when
     *         necessary, false otherwise
     */
    boolean checkIndexValidation1(String str, int index) {
        try {
            int result = str.codePointBefore(index);
            return true; // No exception means index is within valid range.
        } catch (IndexOutOfBoundsException e) {
            return index < 1 || index > str.length();
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if codePointCount method returns the correct number of
     * code points in the specified text range.
     * 
     * @param str        the string to test
     * @param beginIndex the starting index of the text range
     * @param endIndex   the ending index of the text range
     * @return true if the correct number of code points is returned, false
     *         otherwise
     */
    boolean checkCodePointCount(String str, int beginIndex, int endIndex) {
        if (str == null || beginIndex < 0 || endIndex > str.length() || beginIndex > endIndex) {
            return false;
        }
        try {
            int expectedCount = str.codePointCount(beginIndex, endIndex);
            return str.codePointCount(beginIndex, endIndex) == expectedCount;
        } catch (Exception e) {
            return false; // Handles unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if codePointCount method correctly counts unpaired
     * surrogates.
     * 
     * @param str        the string containing potential surrogates
     * @param beginIndex the starting index of the text range
     * @param endIndex   the ending index of the text range
     * @return true if unpaired surrogates are counted correctly, false otherwise
     */
    boolean checkSurrogateHandling(String str, int beginIndex, int endIndex) {
        if (str == null || beginIndex < 0 || endIndex > str.length() || beginIndex > endIndex) {
            return false;
        }
        try {
            // Directly counting code points in the substring and comparing with expected
            // value
            String subStr = str.substring(beginIndex, endIndex);
            int countByMethod = str.codePointCount(beginIndex, endIndex);
            int actualCount = 0;
            for (int i = 0; i < subStr.length(); i++) {
                if (!Character.isSurrogate(subStr.charAt(i)) || (Character.isHighSurrogate(subStr.charAt(i))
                        && (i + 1 < subStr.length()) && Character.isLowSurrogate(subStr.charAt(i + 1)))) {
                    actualCount++;
                }
                if (Character.isLowSurrogate(subStr.charAt(i))
                        && (i == 0 || !Character.isHighSurrogate(subStr.charAt(i - 1)))) {
                    actualCount++;
                }
            }
            return countByMethod == actualCount;
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if codePointCount method correctly handles index
     * validation.
     * 
     * @param str        the string to test
     * @param beginIndex the starting index of the text range
     * @param endIndex   the ending index of the text range
     * @return true if the method correctly throws IndexOutOfBoundsException when
     *         necessary, false otherwise
     */
    boolean checkIndexValidation(String str, int beginIndex, int endIndex) {
        try {
            int result = str.codePointCount(beginIndex, endIndex);
            return true; // No exception means indices are within valid range.
        } catch (IndexOutOfBoundsException e) {
            return beginIndex < 0 || endIndex > str.length() || beginIndex > endIndex;
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if offsetByCodePoints method returns the correct index
     * based on the code point offset.
     * 
     * @param str             the string to test
     * @param index           the starting index
     * @param codePointOffset the offset in code points
     * @return true if the returned index is correct, false otherwise
     */
    boolean checkOffsetByCodePoints(String str, int index, int codePointOffset) {
        if (str == null || index < 0 || index > str.length()) {
            return false;
        }
        try {
            int calculatedIndex = str.offsetByCodePoints(index, codePointOffset);
            int actualIndex = index;
            int offsetCount = 0;
            if (codePointOffset > 0) {
                while (offsetCount < codePointOffset && actualIndex < str.length()) {
                    if (Character.isHighSurrogate(str.charAt(actualIndex)) && actualIndex + 1 < str.length()
                            && Character.isLowSurrogate(str.charAt(actualIndex + 1))) {
                        actualIndex++;
                    }
                    actualIndex++;
                    offsetCount++;
                }
            } else {
                while (offsetCount < Math.abs(codePointOffset) && actualIndex > 0) {
                    actualIndex--;
                    if (Character.isLowSurrogate(str.charAt(actualIndex)) && actualIndex > 0
                            && Character.isHighSurrogate(str.charAt(actualIndex - 1))) {
                        actualIndex--;
                    }
                    offsetCount++;
                }
            }
            return calculatedIndex == actualIndex;
        } catch (Exception e) {
            return false; // Handles unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if offsetByCodePoints method correctly validates index
     * and offset.
     * 
     * @param str             the string to test
     * @param index           the starting index
     * @param codePointOffset the offset in code points
     * @return true if the method correctly throws IndexOutOfBoundsException when
     *         necessary, false otherwise
     */
    boolean checkIndexAndOffsetValidation(String str, int index, int codePointOffset) {
        try {
            str.offsetByCodePoints(index, codePointOffset);
            return true; // No exception means all validations passed.
        } catch (IndexOutOfBoundsException e) {
            return (index < 0 || index > str.length() ||
                    (codePointOffset > 0 && str.length() - index < codePointOffset) ||
                    (codePointOffset < 0 && index < Math.abs(codePointOffset)));
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if getChars method correctly copies characters into the
     * destination array.
     * 
     * @param str      the string from which characters are copied
     * @param srcBegin the starting index in the string
     * @param srcEnd   the ending index in the string
     * @param dst      the destination character array
     * @param dstBegin the starting index in the destination array
     * @return true if characters are correctly copied, false otherwise
     */
    boolean checkCharacterCopying(String str, int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        if (str == null || dst == null || srcBegin < 0 || srcEnd > str.length() || srcBegin > srcEnd || dstBegin < 0
                || dstBegin + (srcEnd - srcBegin) > dst.length) {
            return false;
        }
        try {
            char[] expected = new char[srcEnd - srcBegin];
            System.arraycopy(str.toCharArray(), srcBegin, expected, 0, srcEnd - srcBegin);
            str.getChars(srcBegin, srcEnd, dst, dstBegin);
            for (int i = 0; i < expected.length; i++) {
                if (dst[dstBegin + i] != expected[i]) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false; // Handle unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if getChars method correctly handles range validation
     * and throws exceptions.
     * 
     * @param str      the string from which characters are copied
     * @param srcBegin the starting index in the string
     * @param srcEnd   the ending index in the string
     * @param dst      the destination character array
     * @param dstBegin the starting index in the destination array
     * @return true if method throws IndexOutOfBoundsException under invalid
     *         conditions, false otherwise
     */
    boolean checkRangeValidation(String str, int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        try {
            str.getChars(srcBegin, srcEnd, dst, dstBegin);
            return srcBegin >= 0 && srcEnd <= str.length() && srcBegin <= srcEnd && dstBegin >= 0
                    && dstBegin + (srcEnd - srcBegin) <= dst.length;
        } catch (IndexOutOfBoundsException e) {
            return srcBegin < 0 || srcEnd > str.length() || srcBegin > srcEnd || dstBegin < 0
                    || dstBegin + (srcEnd - srcBegin) > dst.length;
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if getBytes method correctly converts characters to
     * bytes and copies them into the destination array.
     * 
     * @param str      the string from which characters are copied
     * @param srcBegin the starting index in the string
     * @param srcEnd   the ending index in the string
     * @param dst      the destination byte array
     * @param dstBegin the starting index in the destination array
     * @return true if characters are correctly converted to bytes and copied, false
     *         otherwise
     */
    boolean checkCharacterToByteConversion(String str, int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
        if (str == null || dst == null || srcBegin < 0 || srcEnd > str.length() || srcBegin > srcEnd || dstBegin < 0
                || dstBegin + (srcEnd - srcBegin) > dst.length) {
            return false;
        }
        try {
            byte[] expected = new byte[srcEnd - srcBegin];
            for (int i = 0; i < expected.length; i++) {
                expected[i] = (byte) (str.charAt(srcBegin + i) & 0xFF);
            }
            str.getBytes(srcBegin, srcEnd, dst, dstBegin);
            for (int i = 0; i < expected.length; i++) {
                if (dst[dstBegin + i] != expected[i]) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false; // Handle unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if getBytes method correctly handles range validation
     * and throws exceptions.
     * 
     * @param str      the string from which characters are copied
     * @param srcBegin the starting index in the string
     * @param srcEnd   the ending index in the string
     * @param dst      the destination byte array
     * @param dstBegin the starting index in the destination array
     * @return true if method throws IndexOutOfBoundsException under invalid
     *         conditions, false otherwise
     */
    boolean checkRangeValidation(String str, int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
        try {
            str.getBytes(srcBegin, srcEnd, dst, dstBegin);
            return srcBegin >= 0 && srcEnd <= str.length() && srcBegin <= srcEnd && dstBegin >= 0
                    && dstBegin + (srcEnd - srcBegin) <= dst.length;
        } catch (IndexOutOfBoundsException e) {
            return srcBegin < 0 || srcEnd > str.length() || srcBegin > srcEnd || dstBegin < 0
                    || dstBegin + (srcEnd - srcBegin) > dst.length;
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if getBytes method correctly encodes a string into bytes
     * using the specified charset.
     * 
     * @param str         the string to encode
     * @param charsetName the name of the charset to use for encoding
     * @return true if the string is correctly encoded into bytes, false otherwise
     */
    boolean checkStringEncoding(String str, String charsetName) {
        if (str == null || charsetName == null) {
            return false;
        }
        try {
            byte[] encodedBytes = str.getBytes(charsetName);
            String decodedString = new String(encodedBytes, charsetName);
            return str.equals(decodedString);
        } catch (UnsupportedEncodingException e) {
            return false; // Charset not supported
        } catch (Exception e) {
            return false; // Handle unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if getBytes method correctly handles the specified
     * charset and throws UnsupportedEncodingException when necessary.
     * 
     * @param str         the string to encode
     * @param charsetName the name of the charset to use for encoding
     * @return true if method throws UnsupportedEncodingException under invalid
     *         conditions, false otherwise
     */
    boolean checkCharsetHandling(String str, String charsetName) {
        try {
            byte[] bytes = str.getBytes(charsetName);
            return Charset.isSupported(charsetName); // Check if charset is supported and encoding proceeds without
                                                     // exception
        } catch (UnsupportedEncodingException e) {
            return !Charset.isSupported(charsetName); // Expect UnsupportedEncodingException for unsupported charsets
        } catch (Exception e) {
            return false; // Handle unexpected exceptions.
        }
    }

    /**
     * Test oracle to check if getBytes method correctly encodes a string into bytes
     * using the provided charset.
     * 
     * @param str     the string to encode
     * @param charset the charset to use for encoding
     * @return true if the string is correctly encoded into bytes, false otherwise
     */
    boolean checkStringEncoding(String str, Charset charset) {
        if (str == null || charset == null) {
            return false;
        }
        try {
            byte[] encodedBytes = str.getBytes(charset);
            String decodedString = new String(encodedBytes, charset);
            return str.equals(decodedString);
        } catch (Exception e) {
            return false; // Handle unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if getBytes method properly handles malformed inputs and
     * unmappable characters using the provided charset.
     * 
     * @param str     the string to encode, possibly containing characters not
     *                supported by the charset
     * @param charset the charset to use for encoding
     * @return true if all malformed inputs and unmappable characters are replaced
     *         with the charset's default replacement bytes, false otherwise
     */
    boolean checkMalformedAndUnmappableHandling(String str, Charset charset) {
        if (str == null || charset == null) {
            return false;
        }
        try {
            byte[] encodedBytes = str.getBytes(charset);
            for (char ch : str.toCharArray()) {
                if (!charset.newEncoder().canEncode(ch)) {
                    // Verify that the byte array does not contain the unmapped character.
                    String reconstructedString = new String(encodedBytes, charset);
                    if (reconstructedString.indexOf(ch) != -1) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false; // Handle unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to check if getBytes method correctly encodes a string into bytes
     * using the platform's default charset.
     * 
     * @param str the string to encode
     * @return true if the string is correctly encoded into bytes, false otherwise
     */
    boolean checkStringEncoding(String str) {
        if (str == null) {
            return false;
        }
        try {
            byte[] encodedBytes = str.getBytes();
            String decodedString = new String(encodedBytes);
            return str.equals(decodedString);
        } catch (Exception e) {
            return false; // Handle unexpected exceptions gracefully.
        }
    }

    /**
     * Test oracle to evaluate the behavior of the getBytes method when the string
     * contains characters not encodable in the platform's default charset.
     * 
     * @param str the string to encode, possibly containing characters not supported
     *            by the default charset
     * @return true if the method does not throw an unexpected exception, false
     *         otherwise
     */
    boolean checkUnspecifiedBehavior(String str) {
        try {
            byte[] bytes = str.getBytes();
            // Attempting to decode the bytes back to a string to see if the process
            // introduces any anomalies.
            String decodedString = new String(bytes);
            // The test passes if no exception is thrown and the resultant string is
            // reasonable,
            // though we cannot check equality due to the unspecified outcome.
            return decodedString != null;
        } catch (Exception e) {
            return false; // The method should not throw exceptions not handled by the specification.
        }
    }

    /**
     * Test oracle to check if the equals method correctly determines equality.
     * 
     * @param str the String to test
     * @param obj the object to compare against (typically another String)
     * @return true if the results are consistent with equals specifications, false
     *         otherwise
     */
    boolean checkEquals(String str, Object obj) {
        if (str == null)
            return obj == null;
        if (!(obj instanceof String))
            return false;
        return str.equals(obj) == obj.equals(str); // Should be symmetric and true if obj is a String that matches str.
    }

    /**
     * Test oracle to check if equalsIgnoreCase method correctly determines equality
     * ignoring case.
     * 
     * @param str        the String to test
     * @param anotherStr the String to compare against
     * @return true if the strings are equal ignoring case, false otherwise
     */
    boolean checkEqualsIgnoreCase(String str, String anotherStr) {
        if (str == null || anotherStr == null)
            return str == anotherStr;
        if (str.length() != anotherStr.length())
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.toLowerCase(str.charAt(i)) != Character.toLowerCase(anotherStr.charAt(i)) &&
                    Character.toUpperCase(str.charAt(i)) != Character.toUpperCase(anotherStr.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test oracle to check if the compareTo method correctly determines the
     * lexicographical order.
     * 
     * @param str        the String to test
     * @param anotherStr the String to compare against
     * @return true if the comparison is consistent with lexicographical rules,
     *         false otherwise
     */
    boolean checkCompareTo(String str, String anotherStr) {
        int result = str.compareTo(anotherStr);
        int reverseResult = anotherStr.compareTo(str);
        if (result == 0)
            return str.equals(anotherStr);
        if (result < 0)
            return str.length() < anotherStr.length() || str.compareTo(anotherStr) < 0;
        if (result > 0)
            return str.length() > anotherStr.length() || str.compareTo(anotherStr) > 0;
        return true; // Handling symmetric result consistency
    }

    /**
     * Test oracle to check if contentEquals method correctly compares a String to a
     * StringBuffer.
     * 
     * @param str the String to test
     * @param sb  the StringBuffer to compare against
     * @return true if the String represents the same sequence of characters as the
     *         StringBuffer, false otherwise
     */
    boolean checkContentEquals(String str, StringBuffer sb) {
        if (str == null || sb == null) {
            return false; // Return false if either parameter is null as per typical Java conventions
        }
        String stringBufferContent = sb.toString();
        return str.equals(stringBufferContent);
    }

    /**
     * Test oracle to assess if the method synchronizes on the StringBuffer
     * properly.
     * This is conceptual as synchronization effects are not directly observable
     * through return values.
     * We can verify if synchronization is attempted by observing the locking
     * behavior in a multi-threaded environment.
     * 
     * @param str the String to test
     * @param sb  the StringBuffer to compare against
     * @return true if no exceptions or deadlocks occur, suggesting synchronization
     *         is properly handled, false otherwise
     */
    boolean checkSynchronization(String str, StringBuffer sb) {
        try {
            final boolean[] result = { false };
            Thread thread = new Thread(() -> {
                synchronized (sb) {
                    try {
                        // Simulate work inside the synchronized block
                        Thread.sleep(100);
                        result[0] = str.contentEquals(sb);
                    } catch (InterruptedException e) {
                        result[0] = false;
                    }
                }
            });
            thread.start();
            // Attempt to call contentEquals while sb is locked by another thread
            boolean methodResult = str.contentEquals(sb);
            thread.join(); // Ensure the thread completes
            return result[0] && methodResult; // Both operations should complete successfully if synchronization is
                                              // handled correctly
        } catch (Exception e) {
            return false; // If an exception or deadlock occurs, synchronization may be mishandled
        }
    }

    /**
     * Test oracle to check if contentEquals method correctly compares a String to a
     * CharSequence.
     * 
     * @param str the String to test
     * @param cs  the CharSequence to compare against
     * @return true if the String represents the same sequence of characters as the
     *         CharSequence, false otherwise
     */
    boolean checkContentEquals(CharSequence str, CharSequence cs) {
        if (str == null || cs == null) {
            return false; // Return false if either parameter is null as per typical Java conventions
        }
        return str.toString().equals(cs.toString());
    }

    /**
     * Test oracle to assess if the method synchronizes on the StringBuffer properly
     * when cs is a StringBuffer.
     * This is conceptual as synchronization effects are not directly observable
     * through return values.
     * We can verify if synchronization is attempted by observing the locking
     * behavior in a multi-threaded environment.
     * 
     * @param str the String to test
     * @param cs  the CharSequence to compare against (potentially a StringBuffer)
     * @return true if no exceptions or deadlocks occur, suggesting synchronization
     *         is properly handled, false otherwise
     */
    boolean checkSynchronization(String str, CharSequence cs) {
        if (!(cs instanceof StringBuffer)) {
            return true; // Only applicable if cs is a StringBuffer
        }
        StringBuffer sb = (StringBuffer) cs;
        try {
            final boolean[] result = { false };
            Thread thread = new Thread(() -> {
                synchronized (sb) {
                    try {
                        // Simulate work inside the synchronized block
                        Thread.sleep(100);
                        result[0] = str.contentEquals(sb);
                    } catch (InterruptedException e) {
                        result[0] = false;
                    }
                }
            });
            thread.start();
            // Attempt to call contentEquals while sb is locked by another thread
            boolean methodResult = str.contentEquals(sb);
            thread.join(); // Ensure the thread completes
            return result[0] && methodResult; // Both operations should complete successfully if synchronization is
                                              // handled correctly
        } catch (Exception e) {
            return false; // If an exception or deadlock occurs, synchronization may be mishandled
        }
    }

    /**
     * Test oracle to check if equalsIgnoreCase method correctly compares two
     * strings ignoring case.
     * 
     * @param str1 the first String to test
     * @param str2 the second String to compare against
     * @return true if the strings are considered equal ignoring case, false
     *         otherwise
     */
    boolean checkEqualsIgnoreCase1(String str1, String str2) {
        if (str1 == null || str2 == null)
            return str1 == str2; // Handling null values consistently with Java
        if (str1.length() != str2.length())
            return false; // Early return if lengths differ

        for (int i = 0; i < str1.length(); i++) {
            char ch1 = str1.charAt(i);
            char ch2 = str2.charAt(i);
            if (ch1 == ch2)
                continue; // Characters are exactly the same
            if (Character.toUpperCase(ch1) != Character.toUpperCase(ch2)
                    && Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
                return false; // Characters differ when case is ignored
            }
        }
        return true; // All characters matched ignoring case
    }

    /**
     * Test oracle to check if compareToIgnoreCase method correctly compares two
     * strings ignoring case.
     * 
     * @param str1 the String to test
     * @param str2 the String to compare against
     * @return true if the method correctly determines the lexicographical order
     *         ignoring case, false otherwise
     */
    boolean checkCompareToIgnoreCase(String str1, String str2) {
        if (str1 == null || str2 == null)
            return str1 == null && str2 == null;
        // Normalizing both strings by converting all characters to upper case then to
        // lower case
        String normalizedStr1 = str1.toUpperCase().toLowerCase();
        String normalizedStr2 = str2.toUpperCase().toLowerCase();
        // Comparing lexicographically
        int expectedResult = normalizedStr1.compareTo(normalizedStr2);
        int actualResult = str1.compareToIgnoreCase(str2);
        // Check if the results are consistent with the expectations
        return Integer.signum(expectedResult) == Integer.signum(actualResult);
    }

    /**
     * Test oracle to check if regionMatches method correctly compares specified
     * regions of two strings.
     * 
     * @param s1      the main String to test
     * @param toffset the starting offset in s1
     * @param s2      the other String to compare against
     * @param ooffset the starting offset in s2
     * @param len     the number of characters to compare
     * @return true if the specified regions match exactly, false otherwise
     */
    boolean checkRegionMatches(String s1, int toffset, String s2, int ooffset, int len) {
        if (s1 == null || s2 == null)
            return false; // Handling null values
        if (toffset < 0 || ooffset < 0 || len < 0)
            return false; // Negative values should return false
        if (toffset + len > s1.length() || ooffset + len > s2.length())
            return false; // Check bounds

        for (int i = 0; i < len; i++) {
            if (s1.charAt(toffset + i) != s2.charAt(ooffset + i)) {
                return false; // Characters must match exactly
            }
        }
        return true; // Regions match exactly
    }

    /**
     * Test oracle to verify that regionMatches method handles boundary and input
     * validation correctly.
     * 
     * @param s1      the main String to test
     * @param toffset the starting offset in s1
     * @param s2      the other String to compare against
     * @param ooffset the starting offset in s2
     * @param len     the number of characters to compare
     * @return true if boundary conditions and inputs are correctly validated, false
     *         otherwise
     */
    boolean checkBoundaryAndInputValidation(String s1, int toffset, String s2, int ooffset, int len) {
        try {
            // Intentionally violating boundary conditions to test validation
            if (toffset < 0 || ooffset < 0 || len < 0) {
                return !s1.regionMatches(toffset, s2, ooffset, len);
            }
            if (toffset + len > s1.length() || ooffset + len > s2.length()) {
                return !s1.regionMatches(toffset, s2, ooffset, len);
            }
            return true; // Validation passed correctly
        } catch (Exception e) {
            return false; // Should not throw any exceptions
        }
    }

    /**
     * Test oracle to check if regionMatches method correctly compares specified
     * regions of two strings, considering case sensitivity based on the ignoreCase
     * parameter.
     * 
     * @param s1         the main String to test
     * @param toffset    the starting offset in s1
     * @param s2         the other String to compare against
     * @param ooffset    the starting offset in s2
     * @param len        the number of characters to compare
     * @param ignoreCase whether to ignore case in comparison
     * @return true if the specified regions match as expected (considering case
     *         sensitivity), false otherwise
     */
    boolean checkRegionMatches(String s1, boolean ignoreCase, int toffset, String s2, int ooffset, int len) {
        if (s1 == null || s2 == null)
            return false; // Handling null values
        if (toffset < 0 || ooffset < 0 || len < 0)
            return false; // Negative values should return false
        if (toffset + len > s1.length() || ooffset + len > s2.length())
            return false; // Check bounds

        for (int i = 0; i < len; i++) {
            char c1 = s1.charAt(toffset + i);
            char c2 = s2.charAt(ooffset + i);
            if (ignoreCase) {
                if (Character.toLowerCase(c1) != Character.toLowerCase(c2)
                        && Character.toUpperCase(c1) != Character.toUpperCase(c2)) {
                    return false; // Characters do not match, considering case insensitivity
                }
            } else {
                if (c1 != c2) {
                    return false; // Characters do not match, considering case sensitivity
                }
            }
        }
        return true; // Regions match as expected
    }

    /**
     * Test oracle to verify that regionMatches method handles boundary and input
     * validation correctly.
     * 
     * @param s1         the main String to test
     * @param toffset    the starting offset in s1
     * @param s2         the other String to compare against
     * @param ooffset    the starting offset in s2
     * @param len        the number of characters to compare
     * @param ignoreCase whether to ignore case in comparison
     * @return true if boundary conditions and inputs are correctly validated, false
     *         otherwise
     */
    boolean checkBoundaryAndInputValidation(String s1, boolean ignoreCase, int toffset, String s2, int ooffset,
            int len) {
        try {
            // Intentionally violating boundary conditions to test validation
            if (toffset < 0 || ooffset < 0 || len < 0) {
                return !s1.regionMatches(ignoreCase, toffset, s2, ooffset, len);
            }
            if (toffset + len > s1.length() || ooffset + len > s2.length()) {
                return !s1.regionMatches(ignoreCase, toffset, s2, ooffset, len);
            }
            return true; // Validation passed correctly
        } catch (Exception e) {
            return false; // Should not throw any exceptions
        }
    }

    /**
     * Test oracle to check if startsWith method correctly identifies if the
     * substring starting at a given index matches the specified prefix.
     * 
     * @param str     the main String to test
     * @param prefix  the prefix to look for
     * @param toffset the starting offset in the main string
     * @return true if the substring starting at toffset starts with the prefix,
     *         false otherwise
     */
    boolean checkStartsWith(String str, String prefix, int toffset) {
        if (str == null || prefix == null)
            return false; // Handle null values which are typically not expected
        if (toffset < 0 || toffset > str.length())
            return false; // Boundary conditions: false if toffset is out of bounds

        // Extract substring from toffset and check if it starts with the prefix
        String substr = str.substring(toffset);
        return substr.startsWith(prefix);
    }

    /**
     * Test oracle to verify that startsWith method handles boundary conditions
     * correctly.
     * 
     * @param str     the main String to test
     * @param prefix  the prefix to look for
     * @param toffset the starting offset in the main string
     * @return true if boundary conditions are correctly enforced, false otherwise
     */
    boolean checkBoundaryConditions(String str, String prefix, int toffset) {
        if (str == null || prefix == null)
            return false; // Handle null values appropriately
        if (toffset < 0 || toffset > str.length()) {
            // Expecting method to return false without throwing an error if toffset is out
            // of bounds
            try {
                return !str.startsWith(prefix, toffset);
            } catch (Exception e) {
                return false; // Method should not throw an exception
            }
        }
        return true; // If within bounds, boundary check passed
    }

    /**
     * Test oracle to check if startsWith method correctly identifies if this string
     * starts with the specified prefix.
     * 
     * @param str    the main String to test
     * @param prefix the prefix to look for
     * @return true if the string starts with the prefix, false otherwise
     */
    boolean checkStartsWith(String str, String prefix) {
        if (str == null || prefix == null)
            return false; // Handle null values which are typically not expected
        return str.startsWith(prefix);
    }

    /**
     * Test oracle to verify that startsWith correctly returns true when the prefix
     * is empty.
     * 
     * @param str the main String to test
     * @return true if startsWith returns true for an empty prefix, false otherwise
     */
    boolean checkEmptyPrefix(String str) {
        if (str == null)
            return false; // If str is null, method should handle gracefully but typically it's not
                          // expected to be called with null
        return str.startsWith("");
    }

    /**
     * Test oracle to ensure that startsWith returns true when the prefix is exactly
     * the same as the string.
     * 
     * @param str the main String to test
     * @return true if startsWith returns true when the prefix is the string itself,
     *         false otherwise
     */
    boolean checkPrefixIsStringItself(String str) {
        if (str == null)
            return false; // If str is null, method should handle gracefully but typically it's not
                          // expected to be called with null
        return str.startsWith(str);
    }

    /**
     * Test oracle to check if endsWith method correctly identifies if this string
     * ends with the specified suffix.
     * 
     * @param str    the main String to test
     * @param suffix the suffix to look for
     * @return true if the string ends with the suffix, false otherwise
     */
    boolean checkEndsWith(String str, String suffix) {
        if (str == null || suffix == null)
            return false; // Handling null values which are typically not expected
        return str.endsWith(suffix);
    }

    /**
     * Test oracle to verify that endsWith correctly returns true when the suffix is
     * empty.
     * 
     * @param str the main String to test
     * @return true if endsWith returns true for an empty suffix, false otherwise
     */
    boolean checkEmptySuffix(String str) {
        if (str == null)
            return false; // If str is null, method should handle gracefully but typically it's not
                          // expected to be called with null
        return str.endsWith("");
    }

    /**
     * Test oracle to ensure that endsWith returns true when the suffix is exactly
     * the same as the string.
     * 
     * @param str the main String to test
     * @return true if endsWith returns true when the suffix is the string itself,
     *         false otherwise
     */
    boolean checkSuffixIsStringItself(String str) {
        if (str == null)
            return false; // If str is null, method should handle gracefully but typically it's not
                          // expected to be called with null
        return str.endsWith(str);
    }

    /**
     * Test oracle to check if hashCode method correctly computes the hash code of a
     * string.
     * 
     * @param str the String to compute the hash code
     * @return true if the hash code matches the expected calculation, false
     *         otherwise
     */
    boolean checkHashCode(String str) {
        int expectedHashCode = 0;
        int n = str.length();
        for (int i = 0; i < n; i++) {
            expectedHashCode += str.charAt(i) * Math.pow(31, (n - i - 1));
        }
        int actualHashCode = str.hashCode();
        return actualHashCode == expectedHashCode;
    }

    /**
     * Test oracle to verify that hashCode returns a consistent value for unchanged
     * strings.
     * 
     * @param str the String to test
     * @return true if repeated calls to hashCode return the same value, false
     *         otherwise
     */
    boolean checkHashCodeConsistency(String str) {
        int firstHashCode = str.hashCode();
        int secondHashCode = str.hashCode();
        return firstHashCode == secondHashCode;
    }

    /**
     * Test oracle to ensure that the hash code for an empty string is zero.
     * 
     * @return true if the hash code of an empty string is zero, false otherwise
     */
    boolean checkEmptyStringHashCode() {
        String empty = "";
        return empty.hashCode() == 0;
    }

    /**
     * Test oracle to check if indexOf method correctly identifies the index of the
     * first occurrence of a specified character.
     * 
     * @param str the String to test
     * @param ch  the character (Unicode code point) to search for
     * @return true if the returned index matches the expected index, false
     *         otherwise
     */
    boolean checkIndexOf(String str, int ch) {
        int expectedIndex = -1;
        // Adjusting the loop limit for code points greater than 0xFFFF
        int end = str.codePointCount(0, str.length());
        for (int i = 0, cpIndex = 0; i < str.length(); i += Character.charCount(str.codePointAt(i)), cpIndex++) {
            int currentCP = str.codePointAt(i);
            if (currentCP == ch) {
                expectedIndex = cpIndex;
                break;
            }
        }

        int actualIndex = str.indexOf(ch);
        // Adjust index for code points that use two code units
        if (actualIndex != -1 && ch > 0xFFFF) {
            actualIndex = str.substring(0, actualIndex).codePointCount(0, actualIndex);
        }

        return actualIndex == expectedIndex;
    }

    /**
     * Test oracle to verify that indexOf method correctly returns -1 when the
     * character does not exist in the string.
     * 
     * @param str the String to test
     * @param ch  the character (Unicode code point) that should not exist in the
     *            string
     * @return true if the method correctly returns -1 for a non-existent character,
     *         false otherwise
     */
    boolean checkCharacterNotFound(String str, int ch) {
        if (!str.contains(String.valueOf(Character.toChars(ch)))) {
            return str.indexOf(ch) == -1;
        }
        return true; // If character exists, this test is not applicable
    }

    /**
     * Test oracle to check if indexOf method correctly identifies the index of the
     * first occurrence of a specified character starting from a given index.
     * 
     * @param str       the String to test
     * @param ch        the character (Unicode code point) to search for
     * @param fromIndex the index to start the search from
     * @return true if the returned index matches the expected index, false
     *         otherwise
     */
    boolean checkIndexOfFromIndex(String str, int ch, int fromIndex) {
        int expectedIndex = -1;
        int start = Math.max(fromIndex, 0); // Adjust start index according to the spec
        for (int i = start; i < str.length(); i++) {
            if (str.codePointAt(i) == ch && i >= fromIndex) {
                expectedIndex = i;
                break;
            }
        }

        int actualIndex = str.indexOf(ch, fromIndex);
        return actualIndex == expectedIndex;
    }

    /**
     * Test oracle to verify that indexOf handles boundary conditions for fromIndex
     * correctly.
     * 
     * @param str       the String to test
     * @param ch        the character to search for
     * @param fromIndex the starting index for the search
     * @return true if boundary conditions are handled correctly, false otherwise
     */
    boolean checkBoundaryConditions(String str, int ch, int fromIndex) {
        if (fromIndex < 0) {
            // Expect the same result as starting from 0
            return str.indexOf(ch, 0) == str.indexOf(ch, fromIndex);
        } else if (fromIndex > str.length()) {
            // Expect -1 since the search starts beyond the string length
            return str.indexOf(ch, fromIndex) == -1;
        }
        return true;
    }

    /**
     * Test oracle to check if lastIndexOf method correctly identifies the index of
     * the last occurrence of a specified character.
     * 
     * @param str the String to test
     * @param ch  the character (Unicode code point) to search for
     * @return true if the returned index matches the expected index, false
     *         otherwise
     */
    boolean checkLastIndexOf(String str, int ch) {
        int expectedIndex = -1;
        for (int i = str.length() - 1; i >= 0; i--) {
            int currentCP = str.codePointAt(i);
            if (currentCP == ch) {
                expectedIndex = i;
                if (Character.isSupplementaryCodePoint(currentCP)) {
                    expectedIndex = i; // Adjust for surrogate pairs if necessary
                }
                break;
            }
            // Handle surrogate pairs
            if (Character.isSurrogate(str.charAt(i)) && i > 0
                    && Character.isSurrogatePair(str.charAt(i - 1), str.charAt(i))) {
                i--;
            }
        }

        int actualIndex = str.lastIndexOf(ch);
        return actualIndex == expectedIndex;
    }

    /**
     * Test oracle to verify that lastIndexOf handles both BMP and supplementary
     * characters correctly.
     * 
     * @param str the String to test
     * @param ch  the character (Unicode code point) to search for
     * @return true if the search correctly identifies the position of BMP and
     *         supplementary characters, false otherwise
     */
    boolean checkUnicodeHandling(String str, int ch) {
        if (Character.isSupplementaryCodePoint(ch)) {
            int index = str.lastIndexOf(ch);
            return index != -1 && str.codePointAt(index) == ch;
        } else {
            int index = str.lastIndexOf(ch);
            return index != -1 && str.charAt(index) == ch;
        }
    }

    /**
     * Test oracle to ensure that lastIndexOf returns -1 when the character does not
     * occur in the string.
     * 
     * @param str the String to test
     * @param ch  the character (Unicode code point) that should not exist in the
     *            string
     * @return true if lastIndexOf correctly returns -1 for a non-existent
     *         character, false otherwise
     */
    boolean checkCharacterNotFound1(String str, int ch) {
        return str.lastIndexOf(ch) == -1;
    }

    /**
     * Test oracle to check if lastIndexOf correctly identifies the index of the
     * last occurrence of a specified character starting from a given index.
     * 
     * @param str       the String to test
     * @param ch        the character (Unicode code point) to search for
     * @param fromIndex the index to start the search from, moving backward
     * @return true if the returned index matches the expected index, false
     *         otherwise
     */
    boolean checkLastIndexOfFromIndex(String str, int ch, int fromIndex) {
        int expectedIndex = -1;
        // Adjust start index based on the character length and fromIndex
        int start = Math.min(fromIndex, str.length() - 1);
        for (int i = start; i >= 0; i--) {
            int currentCP = str.codePointAt(i);
            if (currentCP == ch && i <= fromIndex) {
                expectedIndex = i;
                if (Character.isSupplementaryCodePoint(currentCP)) {
                    expectedIndex = i; // Adjust for surrogate pairs if necessary
                    break;
                }
                break;
            }
            // Handle surrogate pairs
            if (Character.isSurrogate(str.charAt(i)) && i > 0
                    && Character.isSurrogatePair(str.charAt(i - 1), str.charAt(i))) {
                i--;
            }
        }

        int actualIndex = str.lastIndexOf(ch, fromIndex);
        return actualIndex == expectedIndex;
    }

    /**
     * Test oracle to verify that lastIndexOf handles both BMP and supplementary
     * characters correctly when searching backwards from a specific index.
     * 
     * @param str       the String to test
     * @param ch        the character (Unicode code point) to search for
     * @param fromIndex the starting index for the backward search
     * @return true if the search correctly identifies the position of BMP and
     *         supplementary characters, false otherwise
     */
    boolean checkUnicodeHandling(String str, int ch, int fromIndex) {
        if (Character.isSupplementaryCodePoint(ch)) {
            int index = str.lastIndexOf(ch, fromIndex);
            return index != -1 && str.codePointAt(index) == ch;
        } else {
            int index = str.lastIndexOf(ch, fromIndex);
            return index != -1 && str.charAt(index) == ch;
        }
    }

    /**
     * Test oracle to ensure that lastIndexOf correctly handles boundary conditions
     * for fromIndex.
     * 
     * @param str       the String to test
     * @param ch        the character to search for
     * @param fromIndex the starting index for the search
     * @return true if boundary conditions are handled correctly, false otherwise
     */
    boolean checkBoundaryConditions1(String str, int ch, int fromIndex) {
        if (fromIndex >= str.length()) {
            // Should behave as if searching from the last character
            return str.lastIndexOf(ch, str.length() - 1) == str.lastIndexOf(ch, fromIndex);
        } else if (fromIndex < 0) {
            // Should return -1 immediately
            return str.lastIndexOf(ch, fromIndex) == -1;
        }
        return true;
    }

    /**
     * Test oracle to check if indexOf method correctly identifies the index of the
     * first occurrence of a specified substring.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring to search for
     * @return true if the returned index matches the expected index, false
     *         otherwise
     */
    boolean checkIndexOfSubstring(String mainStr, String substring) {
        int expectedIndex = -1;
        for (int i = 0; i <= mainStr.length() - substring.length(); i++) {
            if (mainStr.startsWith(substring, i)) {
                expectedIndex = i;
                break;
            }
        }
        int actualIndex = mainStr.indexOf(substring);
        return actualIndex == expectedIndex;
    }

    /**
     * Test oracle to ensure that indexOf returns 0 when the substring is empty.
     * 
     * @param mainStr the main String to test
     * @return true if indexOf returns 0 for an empty substring, false otherwise
     */
    boolean checkEmptySubstring(String mainStr) {
        return mainStr.indexOf("") == 0;
    }

    /**
     * Test oracle to ensure that indexOf returns -1 when the substring does not
     * occur in the string.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring that should not exist in the main string
     * @return true if indexOf correctly returns -1 for a non-existent substring,
     *         false otherwise
     */
    boolean checkSubstringNotFound(String mainStr, String substring) {
        if (!mainStr.contains(substring)) {
            return mainStr.indexOf(substring) == -1;
        }
        return true; // If substring exists, this test is not applicable
    }

    /**
     * Test oracle to check if indexOf method correctly identifies the index of the
     * first occurrence of a specified substring starting from a given index.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring to search for
     * @param fromIndex the index from which to start the search
     * @return true if the returned index matches the expected index, false
     *         otherwise
     */
    boolean checkIndexOfFromIndex(String mainStr, String substring, int fromIndex) {
        int expectedIndex = -1;
        int start = Math.max(fromIndex, 0); // Adjust start index to account for negative values
        for (int i = start; i <= mainStr.length() - substring.length(); i++) {
            if (mainStr.startsWith(substring, i)) {
                expectedIndex = i;
                break;
            }
        }

        int actualIndex = mainStr.indexOf(substring, fromIndex);
        return actualIndex == expectedIndex;
    }

    /**
     * Test oracle to ensure that indexOf returns -1 when the substring does not
     * occur starting from the specified index.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring that should not exist starting from fromIndex
     * @param fromIndex the starting index for the search
     * @return true if indexOf correctly returns -1 for a non-existent substring
     *         starting at fromIndex, false otherwise
     */
    boolean checkSubstringNotFound(String mainStr, String substring, int fromIndex) {
        int result = mainStr.indexOf(substring, fromIndex);
        return result == -1;
    }

    /**
     * Test oracle to verify that indexOf handles edge cases for fromIndex
     * correctly.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring to search for
     * @param fromIndex the index from which to start the search
     * @return true if boundary conditions are correctly enforced, false otherwise
     */
    boolean checkFromIndexHandling(String mainStr, String substring, int fromIndex) {
        if (fromIndex < 0) {
            // Expect the same result as starting from 0
            return mainStr.indexOf(substring, 0) == mainStr.indexOf(substring, fromIndex);
        } else if (fromIndex > mainStr.length()) {
            // Expect -1 since the search starts beyond the string length
            return mainStr.indexOf(substring, fromIndex) == -1;
        }
        return true;
    }

    /**
     * Test oracle to check if lastIndexOf method correctly identifies the index of
     * the last occurrence of a specified substring.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring to search for
     * @return true if the returned index matches the expected index, false
     *         otherwise
     */
    boolean checkLastIndexOfSubstring(String mainStr, String substring) {
        int expectedIndex = -1;
        for (int i = mainStr.length() - substring.length(); i >= 0; i--) {
            if (mainStr.startsWith(substring, i)) {
                expectedIndex = i;
                break;
            }
        }

        int actualIndex = mainStr.lastIndexOf(substring);
        return actualIndex == expectedIndex;
    }

    /**
     * Test oracle to ensure that lastIndexOf returns the length of the string when
     * the substring is empty.
     * 
     * @param mainStr the main String to test
     * @return true if lastIndexOf returns the string length for an empty substring,
     *         false otherwise
     */
    boolean checkEmptySubstringHandling(String mainStr) {
        return mainStr.lastIndexOf("") == mainStr.length();
    }

    /**
     * Test oracle to ensure that lastIndexOf returns -1 when the substring does not
     * occur in the string.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring that should not exist in the main string
     * @return true if lastIndexOf correctly returns -1 for a non-existent
     *         substring, false otherwise
     */
    boolean checkSubstringNotFound1(String mainStr, String substring) {
        if (!mainStr.contains(substring)) {
            return mainStr.lastIndexOf(substring) == -1;
        }
        return true; // If substring exists, this test is not applicable
    }

    /**
     * Test oracle to check if lastIndexOf method correctly identifies the last
     * occurrence of a specified substring starting from a given index and searching
     * backward.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring to search for
     * @param fromIndex the index from which to start the search backward
     * @return true if the returned index matches the expected index, false
     *         otherwise
     */
    boolean checkLastIndexOfFromIndex(String mainStr, String substring, int fromIndex) {
        int expectedIndex = -1;
        for (int i = Math.min(fromIndex, mainStr.length() - substring.length()); i >= 0; i--) {
            if (mainStr.startsWith(substring, i)) {
                expectedIndex = i;
                break;
            }
        }

        int actualIndex = mainStr.lastIndexOf(substring, fromIndex);
        return actualIndex == expectedIndex;
    }

    /**
     * Test oracle to verify that lastIndexOf handles edge cases for fromIndex
     * correctly.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring to search for
     * @param fromIndex the index from which to start the search backward
     * @return true if boundary conditions are correctly enforced, false otherwise
     */
    boolean checkFromIndexHandling1(String mainStr, String substring, int fromIndex) {
        if (fromIndex >= mainStr.length()) {
            // Should behave as if searching from the last character
            return mainStr.lastIndexOf(substring, mainStr.length() - 1) == mainStr.lastIndexOf(substring, fromIndex);
        } else if (fromIndex < 0) {
            // Should return -1 immediately
            return mainStr.lastIndexOf(substring, fromIndex) == -1;
        }
        return true;
    }

    /**
     * Test oracle to ensure that lastIndexOf returns -1 when the substring does not
     * occur starting from the specified index and searching backward.
     * 
     * @param mainStr   the main String to test
     * @param substring the substring that should not exist starting from fromIndex
     * @param fromIndex the starting index for the search
     * @return true if lastIndexOf correctly returns -1 for a non-existent
     *         substring, false otherwise
     */
    boolean checkSubstringNotFound1(String mainStr, String substring, int fromIndex) {
        int result = mainStr.lastIndexOf(substring, fromIndex);
        return result == -1;
    }

    /**
     * Test oracle to check if substring method correctly extracts the substring
     * starting from a given index.
     * 
     * @param mainStr    the main String to test
     * @param beginIndex the beginning index from which to extract the substring
     * @return true if the returned substring matches the expected substring, false
     *         otherwise
     */
    boolean checkSubstringExtraction(String mainStr, int beginIndex) {
        try {
            String expectedSubstring = mainStr.substring(beginIndex);
            String actualSubstring = mainStr.substring(beginIndex);
            return expectedSubstring.equals(actualSubstring);
        } catch (IndexOutOfBoundsException e) {
            return false; // Expected behavior if exception thrown correctly
        }
    }

    /**
     * Test oracle to ensure that substring throws IndexOutOfBoundsException when
     * beginIndex is out of range.
     * 
     * @param mainStr    the main String to test
     * @param beginIndex the beginning index from which to extract the substring
     * @return true if the exception is thrown for invalid beginIndex, false
     *         otherwise
     */
    boolean checkIndexOutOfBoundsException(String mainStr, int beginIndex) {
        try {
            mainStr.substring(beginIndex);
            return beginIndex >= 0 && beginIndex <= mainStr.length(); // Should not throw if within range
        } catch (IndexOutOfBoundsException e) {
            return beginIndex < 0 || beginIndex > mainStr.length(); // Should throw if out of range
        }
    }

    /**
     * Test oracle to verify that substring returns an empty string when beginIndex
     * equals the length of the string.
     * 
     * @param mainStr the main String to test
     * @return true if an empty string is returned when beginIndex is the length of
     *         the string, false otherwise
     */
    boolean checkReturnEmptyString(String mainStr) {
        int beginIndex = mainStr.length();
        String result = mainStr.substring(beginIndex);
        return result.isEmpty(); // Expect an empty string
    }

    /**
     * Test oracle to check if substring(int beginIndex, int endIndex) correctly
     * extracts the specified substring.
     * 
     * @param mainStr    the main String to test
     * @param beginIndex the beginning index from which to start the substring
     * @param endIndex   the ending index which is the upper limit of the substring
     *                   (exclusive)
     * @return true if the returned substring matches the expected substring, false
     *         otherwise
     */
    boolean checkSubstringExtraction(String mainStr, int beginIndex, int endIndex) {
        try {
            String expectedSubstring = mainStr.substring(beginIndex, endIndex);
            String actualSubstring = mainStr.substring(beginIndex, endIndex);
            return expectedSubstring.equals(actualSubstring);
        } catch (IndexOutOfBoundsException e) {
            return false; // Expected behavior if exception is thrown correctly
        }
    }

    /**
     * Test oracle to ensure that substring throws IndexOutOfBoundsException when
     * indices are out of range or invalid.
     * 
     * @param mainStr    the main String to test
     * @param beginIndex the beginning index for the substring
     * @param endIndex   the ending index for the substring
     * @return true if the exception is thrown for invalid indices, false otherwise
     */
    boolean checkIndexOutOfBoundsException(String mainStr, int beginIndex, int endIndex) {
        try {
            mainStr.substring(beginIndex, endIndex);
            return beginIndex >= 0 && endIndex <= mainStr.length() && beginIndex <= endIndex; // Should not throw if
                                                                                              // within valid range
        } catch (IndexOutOfBoundsException e) {
            return beginIndex < 0 || endIndex > mainStr.length() || beginIndex > endIndex; // Should throw if out of
                                                                                           // range
        }
    }

    /**
     * Test oracle to verify that substring returns an empty string when beginIndex
     * equals endIndex.
     * 
     * @param mainStr the main String to test
     * @param index   where beginIndex equals endIndex
     * @return true if an empty string is returned when beginIndex equals endIndex,
     *         false otherwise
     */
    boolean checkReturnEmptyString(String mainStr, int index) {
        String result = mainStr.substring(index, index);
        return result.isEmpty(); // Expect an empty string
    }

    /**
     * Test oracle to check if subSequence method correctly extracts the specified
     * subsequence.
     * 
     * @param mainStr    the main CharSequence to test
     * @param beginIndex the beginning index, inclusive
     * @param endIndex   the ending index, exclusive
     * @return true if the returned subsequence matches the expected subsequence,
     *         false otherwise
     */
    boolean checkSubSequenceExtraction(CharSequence mainStr, int beginIndex, int endIndex) {
        CharSequence expectedSubsequence = mainStr.subSequence(beginIndex, endIndex);
        CharSequence actualSubsequence = mainStr.subSequence(beginIndex, endIndex);
        return expectedSubsequence.toString().equals(actualSubsequence.toString());
    }

    /**
     * Test oracle to ensure that subSequence throws IndexOutOfBoundsException when
     * indices are out of range or invalid.
     * 
     * @param mainStr    the main CharSequence to test
     * @param beginIndex the beginning index for the subsequence
     * @param endIndex   the ending index for the subsequence
     * @return true if the exception is thrown for invalid indices, false otherwise
     */
    boolean checkIndexOutOfBoundsException(CharSequence mainStr, int beginIndex, int endIndex) {
        try {
            mainStr.subSequence(beginIndex, endIndex);
            return beginIndex >= 0 && endIndex <= mainStr.length() && beginIndex <= endIndex; // Should not throw if
                                                                                              // within valid range
        } catch (IndexOutOfBoundsException e) {
            return beginIndex < 0 || endIndex > mainStr.length() || beginIndex > endIndex; // Should throw if out of
                                                                                           // range
        }
    }

    /**
     * Test oracle to verify that subSequence behaves identically to substring for
     * valid indices.
     * 
     * @param mainStr    the main String to test, cast to CharSequence
     * @param beginIndex the beginning index, inclusive
     * @param endIndex   the ending index, exclusive
     * @return true if subSequence and substring produce identical results, false
     *         otherwise
     */
    boolean checkSubSequenceConsistency(String mainStr, int beginIndex, int endIndex) {
        String subStrResult = mainStr.substring(beginIndex, endIndex);
        CharSequence subSeqResult = mainStr.subSequence(beginIndex, endIndex);
        return subStrResult.equals(subSeqResult.toString());
    }

    /**
     * Test oracle to check if concat method correctly concatenates the specified
     * string to the end of the current string.
     * 
     * @param original the original String to test
     * @param toConcat the String to concatenate
     * @return true if the returned string matches the expected concatenated result,
     *         false otherwise
     */
    boolean checkConcatenation(String original, String toConcat) {
        String expectedResult = original + toConcat;
        String actualResult = original.concat(toConcat);
        return expectedResult.equals(actualResult);
    }

    /**
     * Test oracle to ensure that concat returns the original string unchanged when
     * the argument string is empty.
     * 
     * @param original the original String to test
     * @return true if concat with an empty string returns the original string,
     *         false otherwise
     */
    boolean checkConcatWithEmptyString(String original) {
        String result = original.concat("");
        return original.equals(result);
    }

    /**
     * Test oracle to verify that concat correctly forms a new string under normal
     * conditions and retains the original string if the concat string is empty.
     * 
     * @param original the original String to test
     * @param toConcat the String to concatenate
     * @return true if the method behaves correctly for both non-empty and empty
     *         concat strings, false otherwise
     */
    boolean checkConcatResultingString(String original, String toConcat) {
        String concatResult = original.concat(toConcat);
        if (toConcat.isEmpty()) {
            return original.equals(concatResult); // Should return original if toConcat is empty
        } else {
            return (original + toConcat).equals(concatResult); // Should return new concatenated string
        }
    }

    /**
     * Test oracle to check if replace method correctly replaces all occurrences of
     * oldChar with newChar.
     * 
     * @param original the original String to test
     * @param oldChar  the character to be replaced
     * @param newChar  the character to replace with
     * @return true if the returned string matches the expected result, false
     *         otherwise
     */
    boolean checkReplacement(String original, char oldChar, char newChar) {
        String expectedResult = original.replace(oldChar, newChar);
        String actualResult = original.replace(oldChar, newChar);
        return expectedResult.equals(actualResult);
    }

    /**
     * Test oracle to ensure that replace returns the original string unchanged when
     * oldChar does not occur in the string.
     * 
     * @param original the original String to test
     * @param oldChar  the character that does not occur in the string
     * @return true if the original string is returned unchanged, false otherwise
     */
    boolean checkUnchangedString(String original, char oldChar) {
        if (original.indexOf(oldChar) == -1) {
            String result = original.replace(oldChar, '*'); // Replace with a character unlikely to be in the original
            return original.equals(result);
        }
        return true; // If oldChar exists, this test is not applicable
    }

    /**
     * Test oracle to verify that characters other than oldChar remain unchanged in
     * the string after replacement.
     * 
     * @param original the original String to test
     * @param oldChar  the character to be replaced
     * @param newChar  the character to replace with
     * @return true if characters other than oldChar remain unchanged, false
     *         otherwise
     */
    boolean checkOtherCharactersUnchanged(String original, char oldChar, char newChar) {
        String modified = original.replace(oldChar, newChar);
        for (int i = 0; i < original.length(); i++) {
            if (original.charAt(i) != oldChar && original.charAt(i) != modified.charAt(i)) {
                return false; // Other characters should not change
            }
        }
        return true;
    }

    /**
     * Test oracle to check if the matches method correctly identifies whether the
     * string matches the given regex.
     * 
     * @param str   the string to test
     * @param regex the regular expression to match against
     * @return true if the result of str.matches(regex) is consistent with
     *         Pattern.matches(regex, str), false otherwise
     */
    boolean checkRegexMatch(String str, String regex) {
        boolean expected = Pattern.matches(regex, str);
        boolean actual = str.matches(regex);
        return expected == actual;
    }

    /**
     * Test oracle to ensure that matches throws PatternSyntaxException when regex
     * syntax is invalid.
     * 
     * @param str   the string to test
     * @param regex the invalid regular expression
     * @return true if PatternSyntaxException is thrown, false otherwise
     */
    boolean checkInvalidRegexSyntax(String str, String regex) {
        try {
            str.matches(regex);
            return false; // Should throw exception, returning false if it doesn't
        } catch (PatternSyntaxException e) {
            return true; // Correct behavior if exception is thrown
        }
    }

    /**
     * Test oracle to verify that the matches method returns true only if the string
     * exactly matches the regex.
     * 
     * @param str   the string to test
     * @param regex the regular expression that should exactly match the string
     * @return true if the entire string matches the regex exactly, false otherwise
     */
    boolean checkMatchConsistency(String str, String regex) {
        return str.matches(regex) == Pattern.matches("^" + regex + "$", str);
    }

    /**
     * Test oracle to check if the contains method correctly identifies the presence
     * of a specified CharSequence.
     * 
     * @param mainStr the main String to test
     * @param subSeq  the CharSequence to search for in the main string
     * @return true if the main string contains the subSeq as expected, false
     *         otherwise
     */
    boolean checkContains_wrong(String mainStr, CharSequence subSeq) {
        boolean actualResult = mainStr.contains(subSeq);
        return actualResult == mainStr.indexOf(subSeq.toString()) != -1;
    }

    /**
     * Test oracle to verify that the contains method correctly handles different
     * types of CharSequence inputs.
     * 
     * @param mainStr the main String to test
     * @param subSeq  the CharSequence (could be a String, StringBuffer, or
     *                StringBuilder) to search for
     * @return true if the method correctly identifies the presence or absence of
     *         subSeq, false if it behaves inconsistently
     */
    boolean checkParameterHandling(String mainStr, CharSequence subSeq) {
        // Convert different CharSequence types to String and check
        String subSeqString = subSeq.toString();
        boolean expectedResult = mainStr.contains(subSeqString);
        boolean actualResult = mainStr.contains(subSeq);
        return expectedResult == actualResult;
    }

    /**
     * Test oracle to ensure that the contains method returns true only if the
     * CharSequence is actually present in the string.
     * 
     * @param mainStr the main String to test
     * @param subSeq  the CharSequence that should be present or absent
     * @return true if the return value matches the actual presence of the subSeq,
     *         false otherwise
     */
    boolean checkReturnValueConsistency(String mainStr, CharSequence subSeq) {
        boolean containsResult = mainStr.contains(subSeq);
        boolean manualCheck = mainStr.indexOf(subSeq.toString()) != -1;
        return containsResult == manualCheck;
    }

    /**
     * Test oracle to check if replaceAll correctly replaces substrings matching
     * regex with the replacement string.
     * 
     * @param original    the original String to test
     * @param regex       the regular expression defining the pattern to replace
     * @param replacement the replacement string
     * @return true if the result matches the expected outcome from Pattern
     *         operations, false otherwise
     */
    boolean checkRegexReplacement(String original, String regex, String replacement) {
        String expected = Pattern.compile(regex).matcher(original).replaceAll(replacement);
        String actual = original.replaceAll(regex, replacement);
        return expected.equals(actual);
    }

    /**
     * Test oracle to ensure that replaceAll handles special characters in the
     * replacement string correctly.
     * 
     * @param original    the original String to test
     * @param regex       the regular expression to match
     * @param replacement the replacement string containing special characters like
     *                    backslashes or dollar signs
     * @return true if the result correctly interprets special characters in the
     *         context of regex replacement, false otherwise
     */
    boolean checkSpecialCharactersHandling(String original, String regex, String replacement) {
        String quotedReplacement = Matcher.quoteReplacement(replacement);
        String expectedResult = Pattern.compile(regex).matcher(original).replaceAll(quotedReplacement);
        String actualResult = original.replaceAll(regex, replacement);
        return expectedResult.equals(actualResult);
    }

    /**
     * Test oracle to verify that replaceAll throws PatternSyntaxException when
     * given an invalid regex.
     * 
     * @param original    the string to test
     * @param regex       the invalid regular expression
     * @param replacement the replacement string
     * @return true if PatternSyntaxException is thrown, false otherwise
     */
    boolean checkInvalidRegexSyntax(String original, String regex, String replacement) {
        try {
            original.replaceAll(regex, replacement);
            return false; // Should throw an exception
        } catch (PatternSyntaxException e) {
            return true; // Correct behavior if exception is thrown
        }
    }

    /**
     * Test oracle to confirm that the output of replaceAll is consistent with
     * direct Pattern compilation and matching.
     * 
     * @param original    the original String to test
     * @param regex       the regular expression to match
     * @param replacement the replacement string
     * @return true if replaceAll yields the same result as a manual compile and
     *         replace, false otherwise
     */
    boolean checkResultConsistency(String original, String regex, String replacement) {
        String manualResult = Pattern.compile(regex).matcher(original).replaceAll(replacement);
        String replaceAllResult = original.replaceAll(regex, replacement);
        return manualResult.equals(replaceAllResult);
    }

    /**
     * Test oracle to check if replace correctly replaces substrings matching the
     * literal target sequence.
     * 
     * @param original    the original String to test
     * @param target      the CharSequence target to be replaced
     * @param replacement the CharSequence replacement
     * @return true if the returned string matches the expected result after
     *         replacement, false otherwise
     */
    boolean checkLiteralReplacement(String original, CharSequence target, CharSequence replacement) {
        String expected = original.replace(target.toString(), replacement.toString());
        String actual = original.replace(target, replacement);
        return expected.equals(actual);
    }

    /**
     * Test oracle to verify the order of replacements is maintained from the start
     * to the end of the string.
     * 
     * @param original    the original String to test
     * @param target      the target sequence for replacement
     * @param replacement the replacement sequence
     * @return true if the first occurrence is replaced before subsequent ones,
     *         reflecting direct sequence replacement, false otherwise
     */
    boolean checkReplacementOrder(String original, CharSequence target, CharSequence replacement) {
        int firstOccurrence = original.indexOf(target.toString());
        String afterReplacement = original.replace(target, replacement);
        int newFirstOccurrence = afterReplacement.indexOf(replacement.toString());
        return newFirstOccurrence == firstOccurrence;
    }

    /**
     * Test oracle to ensure consecutive substrings are replaced correctly, taking
     * into account the effect of previous replacements.
     * 
     * @param original    the original String to test, e.g., "aaabaaab"
     * @param target      the target sequence to replace, e.g., "aaa"
     * @param replacement the replacement sequence, e.g., "b"
     * @return true if consecutive substrings are replaced according to the
     *         described method, false otherwise
     */
    boolean checkConsecutiveReplacements(String original, CharSequence target, CharSequence replacement) {
        String manualReplacement = original;
        while (manualReplacement.contains(target)) {
            manualReplacement = manualReplacement.replaceFirst(target.toString(), replacement.toString());
        }
        String methodReplacement = original.replace(target, replacement);
        return manualReplacement.equals(methodReplacement);
    }

    /**
     * Test oracle to confirm that the method returns a new string and does not
     * alter the original string.
     * 
     * @param original    the original String to test
     * @param target      the target sequence to replace
     * @param replacement the replacement sequence
     * @return true if the original string remains unchanged and the returned string
     *         is correctly altered, false otherwise
     */
    boolean checkReturnValueConsistency(String original, CharSequence target, CharSequence replacement) {
        String originalCopy = new String(original);
        String result = original.replace(target, replacement);
        return !original.equals(result) && originalCopy.equals(original);
    }

    /**
     * Test oracle to check if the split method correctly splits the string based on
     * the regex.
     * 
     * @param str   the string to test
     * @param regex the regular expression to split the string
     * @param limit the limit for splitting
     * @return true if the resulting array matches the expected result from
     *         Pattern.split, false otherwise
     */
    boolean checkRegexSplitting(String str, String regex, int limit) {
        String[] expected = Pattern.compile(regex).split(str, limit);
        String[] actual = str.split(regex, limit);
        return Arrays.equals(expected, actual);
    }

    /**
     * Test oracle to ensure that the order of substrings in the split array matches
     * the order in the original string.
     * 
     * @param str   the string to test
     * @param regex the regular expression to split the string
     * @param limit the limit for splitting
     * @return true if the substrings appear in the same order as they appear in the
     *         string, false otherwise
     */
    boolean checkOrderOfSubstrings(String str, String regex, int limit) {
        String[] splitResult = str.split(regex, limit);
        String reconstructed = String.join(regex, splitResult);
        return str.startsWith(reconstructed);
    }

    /**
     * Test oracle to verify that the split method correctly handles positive-width
     * and zero-width matches.
     * 
     * @param str   the string to test
     * @param regex the regular expression to split the string
     * @param limit the limit for splitting
     * @return true if leading and trailing behaviors are correct (empty substrings
     *         and discarding), false otherwise
     */
    boolean checkMatchHandling(String str, String regex, int limit) {
        String[] results = str.split(regex, limit);
        boolean leadCorrect = !results[0].isEmpty() || str.startsWith(regex);
        boolean trailCorrect = limit != 0 || !results[results.length - 1].isEmpty();
        return leadCorrect && trailCorrect;
    }

    /**
     * Test oracle to ensure that split throws PatternSyntaxException when given an
     * invalid regex.
     * 
     * @param str   the string to test
     * @param regex the invalid regular expression
     * @param limit the limit for splitting
     * @return true if PatternSyntaxException is thrown, false otherwise
     */
    boolean checkInvalidRegexSyntax(String str, String regex, int limit) {
        try {
            str.split(regex, limit);
            return false; // Should throw an exception
        } catch (PatternSyntaxException e) {
            return true; // Correct behavior if exception is thrown
        }
    }

    /**
     * Test oracle to check if the split method correctly splits the string based on
     * the regex, excluding trailing empty strings.
     * 
     * @param str   the string to test
     * @param regex the regular expression to split the string
     * @return true if the resulting array matches the expected result from
     *         Pattern.split with no trailing empty strings, false otherwise
     */
    boolean checkRegexSplitting(String str, String regex) {
        String[] expected = Pattern.compile(regex).split(str, 0); // Explicitly using zero to match method's behavior
        String[] actual = str.split(regex);
        return Arrays.equals(expected, actual);
    }

    /**
     * Test oracle to verify that trailing empty strings resulting from regex
     * matches at the end of the string are excluded.
     * 
     * @param str   the string to test
     * @param regex the regular expression to split the string
     * @return true if no trailing empty strings are in the resulting array unless
     *         explicitly included by the regex, false otherwise
     */
    boolean checkExclusionOfTrailingEmpties(String str, String regex) {
        String[] result = str.split(regex);
        return result.length == 0 || !result[result.length - 1].isEmpty();
    }

    /**
     * Test oracle to ensure that split throws PatternSyntaxException when given an
     * invalid regex.
     * 
     * @param str   the string to test
     * @param regex the invalid regular expression
     * @return true if PatternSyntaxException is thrown, false otherwise
     */
    boolean checkInvalidRegexSyntax1(String str, String regex) {
        try {
            str.split(regex);
            return false; // No exception means failure
        } catch (PatternSyntaxException e) {
            return true; // Correct behavior if exception is thrown
        }
    }

    /**
     * Test oracle to check if the join method correctly concatenates elements with
     * the specified delimiter.
     * 
     * @param delimiter the delimiter to use
     * @param elements  the elements to be joined
     * @return true if the resulting string is correctly concatenated with
     *         delimiters, false otherwise
     */
    boolean checkJoinDelimiterAndConcatenation(CharSequence delimiter, CharSequence... elements) {
        String expected = Arrays.stream(elements)
                .map(e -> e == null ? "null" : e.toString())
                .collect(Collectors.joining(delimiter.toString()));
        String actual = String.join(delimiter, elements);
        return expected.equals(actual);
    }

    /**
     * Test oracle to check if the join method handles null elements and null
     * delimiters properly.
     * 
     * @param delimiter the delimiter to use, which could be null
     * @param elements  the elements to be joined, which could contain null values
     * @return true if the method behaves correctly with null inputs, false
     *         otherwise
     */
    boolean checkNullHandlingInJoin(CharSequence delimiter, CharSequence... elements) {
        try {
            String result = String.join(delimiter, elements);
            if (delimiter == null)
                return false; // Should have thrown NullPointerException
            // Check for null elements handling by converting nulls to "null" strings
            // manually
            return result.equals(Arrays.stream(elements)
                    .map(e -> e == null ? "null" : e.toString())
                    .collect(Collectors.joining(delimiter.toString())));
        } catch (NullPointerException e) {
            return delimiter == null || elements == null; // Correct behavior if NPE is thrown for null delimiter or
                                                          // elements
        }
    }

    /**
     * Test oracle to check if the join method correctly concatenates elements of an
     * Iterable with the specified delimiter.
     * 
     * @param delimiter the delimiter to use
     * @param elements  the Iterable of CharSequence elements to be joined
     * @return true if the resulting string is correctly concatenated with
     *         delimiters, false otherwise
     */
    boolean checkJoinDelimiterAndConcatenation(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
        StringBuilder expected = new StringBuilder();
        Iterator<? extends CharSequence> it = elements.iterator();
        if (it.hasNext()) {
            CharSequence first = it.next();
            expected.append(first == null ? "null" : first);
        }
        while (it.hasNext()) {
            CharSequence element = it.next();
            expected.append(delimiter).append(element == null ? "null" : element);
        }
        String actual = String.join(delimiter, elements);
        return expected.toString().equals(actual);
    }

    /**
     * Test oracle to check if the join method handles null delimiter and null
     * elements properly.
     * 
     * @param delimiter the delimiter to use, which could be null
     * @param elements  the Iterable of CharSequence elements to be joined, which
     *                  could be null
     * @return true if the method behaves correctly with null inputs, false
     *         otherwise
     */
    boolean checkNullHandlingInJoin(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
        try {
            String result = String.join(delimiter, elements);
            if (delimiter == null || elements == null)
                return false; // Should have thrown NullPointerException
            // Manually build the expected result considering "null" strings for null
            // elements
            return true; // If reached here without an exception, the inputs were not null
        } catch (NullPointerException e) {
            return delimiter == null || elements == null; // Correct behavior if NPE is thrown for null inputs
        }
    }

    /**
     * Test oracle to verify if the toLowerCase method correctly handles
     * locale-specific case transformations.
     * 
     * @param input    the original string to be transformed
     * @param locale   the Locale used for lowercasing
     * @param expected the expected result of the transformation
     * @return true if the transformation is correct, false otherwise
     */
    boolean checkLocaleSpecificLowerCasing(String input, Locale locale, String expected) {
        String result = input.toLowerCase(locale);
        return expected.equals(result);
    }

    /**
     * Test oracle to check if toLowerCase changes the string length when necessary
     * according to Unicode standard.
     * 
     * @param input  the original string
     * @param locale the Locale that might influence the length change
     * @return true if the length of the output matches the expected outcome based
     *         on Unicode standard, false otherwise
     */
    boolean checkLengthChangeUnicodeStandard(String input, Locale locale) {
        String lowerCased = input.toLowerCase(locale);
        // Example condition; actual implementation should refer to specific Unicode
        // rules that may cause length changes
        return input.length() == lowerCased.length() || input.length() != lowerCased.length();
    }

    /**
     * Test oracle to verify if toLowerCase method correctly handles default locale
     * transformations.
     * 
     * @param input the original string to be transformed
     * @return true if the transformation is correct under the default locale, false
     *         otherwise
     */
    boolean checkLocaleSensitivity(String input) {
        String expected = input.toLowerCase(Locale.getDefault());
        String result = input.toLowerCase();
        return expected.equals(result);
    }

    /**
     * Test oracle to check for unexpected results due to locale sensitivity.
     * 
     * @param input  the original string
     * @param locale the specific locale to test against, e.g., Locale for Turkish
     * @return true if the output matches expected locale-specific outcomes, false
     *         if it leads to locale-sensitive discrepancies
     */
    boolean checkUnexpectedLocaleResults(String input, Locale locale) {
        String expected = input.toLowerCase(locale);
        String result = input.toLowerCase(); // Default locale might not be the one specified
        return expected.equals(result);
    }

    /**
     * Test oracle to ensure toLowerCase handles locale-insensitive strings
     * correctly using Locale.ROOT.
     * 
     * @param input the original string
     * @return true if using Locale.ROOT gives the expected locale-insensitive
     *         result, false otherwise
     */
    boolean checkLocaleInsensitiveHandling(String input) {
        String expected = input.toLowerCase(Locale.ROOT);
        String result = input.toLowerCase(); // Default method without specifying Locale.ROOT
        return expected.equals(result);
    }

    /**
     * Test oracle to verify if toUpperCase method correctly handles locale-specific
     * transformations.
     * 
     * @param input  the original string to be transformed
     * @param locale the Locale used for transformation
     * @return true if the transformation is correct under the specified locale,
     *         false otherwise
     */
    boolean checkLocaleSensitivity(String input, Locale locale) {
        String expected = input.toUpperCase(locale);
        String result = input.toUpperCase(locale); // Assuming a method overload exists for direct comparison
        return expected.equals(result);
    }

    /**
     * Test oracle to check for compliance with Unicode standard in upper case
     * conversions.
     * 
     * @param input  the original string
     * @param locale the Locale used for transformation
     * @return true if the output is compliant with Unicode standards for the given
     *         locale, false otherwise
     */
    boolean checkUnicodeCompliance_incorrect(String input, Locale locale) {
        String transformed = input.toUpperCase(locale);
        // This check needs real implementation details about Unicode compliance
        // verification.
        return verifyUnicodeStandard(transformed); // Placeholder for actual verification logic
    }

    /**
     * Test oracle to ensure the resulting string length is as expected when case
     * mappings are not 1:1.
     * 
     * @param input  the original string
     * @param locale the Locale used for transformation
     * @return true if the resulting string length matches the expected length
     *         post-transformation, false otherwise
     */
    boolean checkResultingStringLength(String input, Locale locale) {
        String transformed = input.toUpperCase(locale);
        // Specific logic to verify the length based on known mappings; using
        // pseudo-code here
        return transformed.length() >= input.length();
    }

    /**
     * Test oracle for verifying specific locale-sensitive transformations.
     * 
     * @param input    the original string
     * @param locale   the Locale, e.g., new Locale("tr", "TR") for Turkish
     * @param expected the expected result after transformation
     * @return true if the transformation matches the expected result, false
     *         otherwise
     */
    boolean checkSpecificLocaleMappings(String input, Locale locale, String expected) {
        String result = input.toUpperCase(locale);
        return result.equals(expected);
    }

    /**
     * Test oracle to check if trim() correctly removes leading and trailing
     * whitespace.
     * 
     * @param input the original string
     * @return true if all leading and trailing whitespace is removed, false
     *         otherwise
     */
    boolean checkTrimWhitespace_Incorrect(String input) {
        String trimmed = input.trim();
        return trimmed.equals(input.stripLeading().stripTrailing()); // Using stripLeading() and stripTrailing() for
                                                                     // comparison
    }

    /**
     * Test oracle to verify correct return values based on the presence of
     * whitespace.
     * 
     * @param input the original string
     * @return true if the returned string matches the expected trimmed result,
     *         false otherwise
     */
    boolean checkTrimReturnConditions_Incorrect(String input) {
        String trimmed = input.trim();
        if (input.isEmpty()) {
            return trimmed.equals(input); // Check for empty input
        } else if (input.trim().isEmpty()) {
            return trimmed.isEmpty(); // Check for all whitespace input
        } else {
            // Check correct trimming
            int firstIdx = input.indexOf(input.stripLeading().substring(0, 1));
            int lastIdx = input.lastIndexOf(input.stripTrailing().substring(input.stripTrailing().length() - 1));
            String expected = input.substring(firstIdx, lastIdx + 1);
            return trimmed.equals(expected);
        }
    }

    /**
     * Test oracle to verify that only characters greater than space are retained.
     * 
     * @param input the original string
     * @return true if no characters less than or equal to space are at the ends,
     *         false otherwise
     */
    boolean checkWhitespaceDefinition(String input) {
        String trimmed = input.trim();
        return (trimmed.isEmpty() || trimmed.charAt(0) > ' ' && trimmed.charAt(trimmed.length() - 1) > ' ');
    }

    /**
     * Test oracle to ensure the trim method handles empty strings correctly.
     * 
     * @param input the original string
     * @return true if the input is empty and the same string is returned, false
     *         otherwise
     */
    boolean checkEmptyStringHandling(String input) {
        if (input.isEmpty()) {
            String trimmed = input.trim();
            return trimmed.equals(input);
        }
        return true; // Only applies when input is empty
    }

    /**
     * Test oracle to verify that the toString() method on a string returns the
     * string itself.
     * 
     * @param input the original string
     * @return true if the method returns the same object reference, false otherwise
     */
    boolean checkIdentityReturn(String input) {
        return input.toString() == input;
    }

    /**
     * Test oracle to verify that the toCharArray() method returns a new array.
     * 
     * @param input the original string
     * @return true if the method returns a new array, false otherwise
     */
    boolean checkNewArrayCreation(String input) {
        char[] array = input.toCharArray();
        return array != null && array.getClass().isArray();
    }

    /**
     * Test oracle to verify that the array contents match the string.
     * 
     * @param input the original string
     * @return true if the array contents match the string, false otherwise
     */
    boolean checkArrayContent(String input) {
        char[] array = input.toCharArray();
        if (array.length != input.length())
            return false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != input.charAt(i))
                return false;
        }
        return true;
    }

    /**
     * Test oracle to verify that the array length matches the string length.
     * 
     * @param input the original string
     * @return true if the array length is equal to the string length, false
     *         otherwise
     */
    boolean checkArrayLength(String input) {
        char[] array = input.toCharArray();
        return array.length == input.length();
    }

    /**
     * Test oracle to verify that formatting uses the default locale.
     * 
     * @param input a sample format string that is locale-sensitive
     * @return true if the format string behaves as expected under the default
     *         locale, false otherwise
     */
    boolean checkLocaleSensitivity1(String input) {
        String formatted = String.format(input, 1234567.89); // Example for a number format
        String expected = String.format(Locale.getDefault(), input, 1234567.89);
        return formatted.equals(expected);
    }

    /**
     * Test oracle to verify that the format string processes arguments correctly.
     * 
     * @param format   the format string
     * @param args     the arguments to format
     * @param expected the expected result
     * @return true if the formatted string matches the expected result, false
     *         otherwise
     */
    boolean checkFormatStringProcessing(String format, Object[] args, String expected) {
        String result = String.format(format, args);
        return result.equals(expected);
    }

    /**
     * Test oracle to verify that the method handles arguments correctly.
     * 
     * @param format        the format string
     * @param args          the arguments (with potential extras)
     * @param expectedCount the expected number of arguments to be used
     * @return true if the method handles the correct number of arguments, false if
     *         it throws an exception or misuses them
     */
    boolean checkArgumentHandling(String format, Object[] args, int expectedCount) {
        try {
            String formatted = String.format(format, args);
            return formatted != null; // Simplistic check; needs more robust implementation based on specifics
        } catch (IllegalFormatException e) {
            return false; // Incorrect handling or format specification
        }
    }

    /**
     * Test oracle to verify that IllegalFormatException is thrown for invalid
     * formats.
     * 
     * @param format the invalid format string
     * @param args   the arguments that do not match the format
     * @return true if IllegalFormatException is thrown, false otherwise
     */
    boolean checkErrorHandling(String format, Object[] args) {
        try {
            String.format(format, args);
            return false; // No exception thrown, test fails
        } catch (IllegalFormatException e) {
            return true; // Correct behavior
        }
    }

    /**
     * Test oracle to verify that formatting adheres to locale-specific conventions.
     * 
     * @param locale the locale to use for formatting
     * @param format a format string sensitive to locale settings (e.g., number
     *               formats)
     * @param args   arguments to format
     * @return true if the formatted output adheres to locale conventions, false
     *         otherwise
     */
    boolean checkLocaleSpecificFormatting_incorrect(Locale locale, String format, Object[] args) {
        String result = String.format(locale, format, args);
        // Hypothetical method to get expected result based on locale
        String expected = getExpectedLocaleSpecificOutput(locale, format, args);
        return result.equals(expected);
    }

    /**
     * Test oracle to verify correct interpretation and application of the format
     * string.
     * 
     * @param locale   the locale to use for formatting
     * @param format   the format string
     * @param args     the arguments referenced by the format string
     * @param expected the expected formatted output
     * @return true if the output matches the expected result, false otherwise
     */
    boolean checkFormatStringInterpretation(Locale locale, String format, Object[] args, String expected) {
        String formatted = String.format(locale, format, args);
        return formatted.equals(expected);
    }

    /**
     * Test oracle to verify correct handling of variable arguments.
     * 
     * @param locale the locale for formatting
     * @param format the format string with placeholders for arguments
     * @param args   the arguments to be formatted, potentially mismatched in number
     * @return true if handled correctly, false if an exception is thrown
     *         unexpectedly
     */
    boolean checkVariableArgumentHandling(Locale locale, String format, Object[] args) {
        try {
            String.format(locale, format, args);
            return true; // Assumes the correct result implies correct handling
        } catch (IllegalFormatException e) {
            return false; // Incorrect handling
        }
    }

    /**
     * Test oracle to verify that IllegalFormatException is thrown under specified
     * conditions.
     * 
     * @param locale the locale for formatting
     * @param format an intentionally malformed format string
     * @param args   the arguments that do not match the required format
     * @return true if IllegalFormatException is thrown, false otherwise
     */
    boolean checkErrorHandling(Locale locale, String format, Object[] args) {
        try {
            String.format(locale, format, args);
            return false; // Failure: no exception thrown when expected
        } catch (IllegalFormatException e) {
            return true; // Correct behavior
        }
    }

    /**
     * Test oracle to verify that the method returns "null" when the input is null.
     * 
     * @param obj the object to be passed to valueOf method, should be null
     * @return true if the output is "null", false otherwise
     */
    boolean checkNullHandling(Object obj) {
        String result = String.valueOf(obj);
        return "null".equals(result);
    }

    /**
     * Test oracle to verify that the method returns the correct string
     * representation of a non-null object.
     * 
     * @param obj the non-null object to test
     * @return true if the output matches obj.toString(), false otherwise
     */
    boolean checkObjectStringRepresentation(Object obj) {
        String expected = obj.toString();
        String result = String.valueOf(obj);
        return expected.equals(result);
    }

    /**
     * Test oracle to verify that the method returns the correct string
     * representation of the character array.
     * 
     * @param data the character array to be converted to a string
     * @return true if the string matches the content of the character array, false
     *         otherwise
     */
    boolean checkCharArrayToString(char[] data) {
        String expected = new String(data); // Using String constructor for expected behavior
        String result = String.valueOf(data);
        return expected.equals(result);
    }

    /**
     * Test oracle to verify that modifications to the character array after
     * converting to a string do not affect the string.
     * 
     * @param data the character array to test
     * @return true if the String is unaffected by changes to the array, false
     *         otherwise
     */
    boolean checkArrayCopyingBehavior(char[] data) {
        String beforeModification = String.valueOf(data);
        data[0] = '#'; // Modify the array after creating the string
        String afterModification = String.valueOf(data);
        return beforeModification.equals(new String(data, 1, data.length - 1))
                && !beforeModification.equals(afterModification);
    }

    /**
     * Test oracle to verify the correct string representation of the specified
     * subarray of the character array.
     * 
     * @param data   the character array
     * @param offset the starting index of the subarray
     * @param count  the length of the subarray
     * @return true if the resulting string matches the content of the subarray,
     *         false otherwise
     */
    boolean checkSubarrayToString(char[] data, int offset, int count) {
        String expected = new String(data, offset, count); // Using String constructor for expected behavior
        String result = String.valueOf(data, offset, count);
        return expected.equals(result);
    }

    /**
     * Test oracle to verify that modifications to the character array after
     * converting to a string
     * do not affect the resulting string.
     * 
     * @param data   the character array
     * @param offset the starting index of the subarray
     * @param count  the length of the subarray
     * @return true if the string is unaffected by changes to the array, false
     *         otherwise
     */
    boolean checkArrayCopyingBehavior(char[] data, int offset, int count) {
        String beforeModification = String.valueOf(data, offset, count);
        data[offset] = '#'; // Modify the array after creating the string
        String afterModification = String.valueOf(data, offset, count);
        return beforeModification.equals(new String(data, offset, count))
                && !beforeModification.equals(afterModification);
    }

    /**
     * Test oracle to check if IndexOutOfBoundsException is thrown for invalid
     * offset or count values.
     * 
     * @param data   the character array
     * @param offset the starting index of the subarray
     * @param count  the length of the subarray
     * @return true if IndexOutOfBoundsException is thrown for invalid inputs, false
     *         otherwise
     */
    boolean checkBoundsChecking(char[] data, int offset, int count) {
        try {
            String.valueOf(data, offset, count);
            return false; // If no exception is thrown, test fails
        } catch (IndexOutOfBoundsException e) {
            return true; // Correct behavior: exception thrown
        }
    }

    /**
     * Test oracle for subarray conversion to string.
     * 
     * @param data   the character array
     * @param offset the starting index of the subarray
     * @param count  the length of the subarray
     * @return true if the resulting string correctly represents the subarray, false
     *         otherwise
     */
    boolean checkSubarrayConversion(char[] data, int offset, int count) {
        String expected = new String(data, offset, count);
        String result = String.copyValueOf(data, offset, count);
        return expected.equals(result);
    }

    /**
     * Test oracle to ensure that the array is copied and changes do not affect the
     * result.
     * 
     * @param data   the character array
     * @param offset the starting index
     * @param count  the number of characters
     * @return true if changes to the array after conversion do not affect the
     *         string, false otherwise
     */
    boolean checkArrayCopyingBehavior1(char[] data, int offset, int count) {
        String original = String.copyValueOf(data, offset, count);
        data[offset] = '#'; // Modify the array
        String modified = String.copyValueOf(data, offset, count);
        return original.equals(modified) && data[offset] == '#';
    }

    /**
     * Test oracle for bounds checking of the input parameters.
     * 
     * @param data   the character array
     * @param offset the starting index of the subarray
     * @param count  the length of the subarray
     * @return true if an IndexOutOfBoundsException is thrown for invalid
     *         parameters, false otherwise
     */
    boolean checkBounds(char[] data, int offset, int count) {
        try {
            String.copyValueOf(data, offset, count);
            return false; // Should not reach here without an exception if parameters are invalid
        } catch (IndexOutOfBoundsException e) {
            return true; // Correct behavior, exception is expected
        }
    }

    /**
     * Test oracle for array conversion to string.
     * 
     * @param data the character array
     * @return true if the resulting string correctly represents the entire array,
     *         false otherwise
     */
    boolean checkArrayConversion(char[] data) {
        String expected = new String(data); // Expected way to create a string from a char array
        String result = String.copyValueOf(data);
        return expected.equals(result);
    }

    /**
     * Test oracle to ensure consistency with valueOf method.
     * 
     * @param data the character array
     * @return true if copyValueOf and valueOf return the same string for the same
     *         input array, false otherwise
     */
    boolean checkConsistencyWithValueOf(char[] data) {
        String resultFromCopyValueOf = String.copyValueOf(data);
        String resultFromValueOf = String.valueOf(data);
        return resultFromCopyValueOf.equals(resultFromValueOf);
    }

    /**
     * Test oracle for converting boolean to string.
     * 
     * @param b the boolean value
     * @return true if the resulting string correctly represents the boolean value,
     *         false otherwise
     */
    boolean checkBooleanToString(boolean b) {
        String expected = b ? "true" : "false"; // Expected result based on boolean input
        String result = String.valueOf(b);
        return expected.equals(result);
    }

    /**
     * Test oracle for converting character to string.
     * 
     * @param c the character to convert
     * @return true if the resulting string correctly represents the character as a
     *         string of length 1, false otherwise
     */
    boolean checkCharToString(char c) {
        String result = String.valueOf(c);
        return result.length() == 1 && result.charAt(0) == c;
    }

    /**
     * Test oracle for converting an integer to string.
     * 
     * @param i the integer to convert
     * @return true if the resulting string correctly represents the integer as
     *         expected by Integer.toString, false otherwise
     */
    boolean checkIntToString(int i) {
        String result = String.valueOf(i);
        String expected = Integer.toString(i);
        return result.equals(expected);
    }

    /**
     * Test oracle for converting a long integer to string.
     * 
     * @param l the long integer to convert
     * @return true if the resulting string correctly represents the long integer as
     *         expected by Long.toString, false otherwise
     */
    boolean checkLongToString(long l) {
        String result = String.valueOf(l);
        String expected = Long.toString(l);
        return result.equals(expected);
    }

    /**
     * Test oracle for converting a float to string.
     * 
     * @param f the float to convert
     * @return true if the resulting string correctly represents the float as
     *         expected by Float.toString, false otherwise
     */
    boolean checkFloatToString(float f) {
        String result = String.valueOf(f);
        String expected = Float.toString(f);
        return result.equals(expected);
    }

    /**
     * Test oracle for converting a double to string.
     * 
     * @param d the double to convert
     * @return true if the resulting string correctly represents the double as
     *         expected by Double.toString, false otherwise
     */
    boolean checkDoubleToString(double d) {
        String result = String.valueOf(d);
        String expected = Double.toString(d);
        return result.equals(expected);
    }

    /**
     * Test oracle for String.intern().
     * 
     * @param s the string to intern
     * @return true if intern() correctly interns strings according to their
     *         canonical representation, false otherwise
     */
    boolean checkInternCanonical(String s) {
        String interned = s.intern();
        String anotherInterned = new String(s).intern(); // Create a new String object to avoid reference equality
        return interned == anotherInterned && interned.equals(s);
    }

    /**
     * Test oracle to verify that equal strings are interned to the same object.
     * 
     * @param s1 first string to intern
     * @param s2 second string to intern
     * @return true if both strings intern to the same reference when they are
     *         equal, false otherwise
     */
    boolean checkInternEquality(String s1, String s2) {
        return s1.equals(s2) ? s1.intern() == s2.intern() : true;
    }

}
