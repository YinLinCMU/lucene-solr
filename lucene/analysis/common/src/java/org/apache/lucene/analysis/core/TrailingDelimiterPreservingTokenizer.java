package org.apache.lucene.analysis.core;

import org.apache.lucene.analysis.util.CharTokenizer;

import java.util.Set;

public class TrailingDelimiterPreservingTokenizer extends CharTokenizer {
    private final Set<Character> preservedDelimiters;
    private final Set<Character> notPreservedDelimiters;

    public TrailingDelimiterPreservingTokenizer(
            Set<Character> preservedDelimiters, Set<Character> notPreservedDelimiters) {
        this.preservedDelimiters = preservedDelimiters;
        this.notPreservedDelimiters = notPreservedDelimiters;
    }

    @Override
    protected boolean isTokenChar(int character) {
        return !isNonPreservedDelimiter(character) && !isPreservedDelimiter(character);
    }

    @Override
    protected boolean isNonPreservedDelimiter(int character) {
        // Supplementary code point are two chars in length. Simply casting can produce wrong result
        return !Character.isSupplementaryCodePoint(character) && notPreservedDelimiters.contains((char) character);
    }

    @Override
    protected boolean isPreservedDelimiter(int character) {
        // Supplementary code point are two chars in length. Simply casting can produce wrong result
        return !Character.isSupplementaryCodePoint(character) && preservedDelimiters.contains((char) character);
    }
}
