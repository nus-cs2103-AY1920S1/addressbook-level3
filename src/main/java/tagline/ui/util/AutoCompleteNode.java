package tagline.ui.util;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A node to match a query string.
 * Can have children which match further sections of the string.
 */
public class AutoCompleteNode {
    private String matcher;
    private List<AutoCompleteNode> children = new ArrayList<>();

    public AutoCompleteNode(String matcher) {
        this.matcher = matcher;
    }

    public static AutoCompleteNode getRootNode() {
        return new RootAutoCompleteNode();
    }

    /**
     * Adds a new child matcher node to the current node.
     */
    public void addChildren(AutoCompleteNode... other) {
        requireAllNonNull(Arrays.asList(other));
        this.children.addAll(Arrays.asList(other));
    }

    /**
     * Returns true if {@code queryString} is a strict prefix substring of {@code matcher}.
     */
    public boolean isMatch(String query) {
        requireNonNull(query);
        return matcher.startsWith(query)
            && !matcher.equals(query);
    }

    /**
     * Returns true if {@code queryString} begins with {@code matcher} followed by a whitespace.
     */
    public boolean isTrimmable(String query) {
        requireNonNull(query);
        return query.matches("^" + matcher + "\\s.*");
    }

    /**
     * Trims the matcher from a query string.
     */
    public String trimMatcher(String query) throws IllegalArgumentException {
        requireNonNull(query);
        if (!isTrimmable(query)) {
            throw new IllegalArgumentException("AutoCompleteNode Error: Invalid trim");
        }
        return query.replaceFirst("^" + matcher + "\\s+", "");
    }

    /**
     * Prepends the matcher to the result of a query to a child node.
     */
    public String prependMatcher(String result) {
        requireNonNull(result);
        return matcher + " " + result;
    }

    /**
     * Returns all matches for queryString among the current matcher node and its children.
     * Matchers for child nodes will be applied with a whitespace after the parent matcher.
     */
    public List<String> findMatches(String query) {
        requireNonNull(query);

        //if matchString begins with queryString
        if (isMatch(query)) {
            return Arrays.asList(matcher);
        }

        //if there is no match
        if (!isTrimmable(query)) {
            return new ArrayList<>();
        }

        List<String> rawResult = new ArrayList<>();
        String trimmedQuery = trimMatcher(query);
        for (AutoCompleteNode child : children) {
            rawResult.addAll(child.findMatches(trimmedQuery));
        }

        return rawResult.stream().map(this::prependMatcher).collect(Collectors.toList());
    }

    /**
     * A special root node that does not match any strings and only holds children.
     */
    private static class RootAutoCompleteNode extends AutoCompleteNode {
        RootAutoCompleteNode() {
            super("");
        }

        @Override
        public boolean isMatch(String query) {
            requireNonNull(query);
            return false; //never matches
        }

        @Override
        public boolean isTrimmable(String query) {
            requireNonNull(query);
            return true; //always trimmable
        }

        @Override
        public String trimMatcher(String query) {
            requireNonNull(query);
            return query;
        }

        @Override
        public String prependMatcher(String result) {
            requireNonNull(result);
            return result;
        }
    }
}
