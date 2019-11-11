// @@author shiweing
package tagline.logic.commands.note;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Generates a {@code KeywordFilter} containing the keywords to filter note content against.
 */
public class KeywordFilter implements NoteFilter<String> {

    private final String filterString;
    private final List<String> filterValues;

    public KeywordFilter(String keyword) {
        filterString = keyword;
        filterValues = Arrays.asList(keyword.split(" "));
    }

    public List<String> getFilterValues() {
        return Collections.unmodifiableList(filterValues);
    }

    public FilterType getFilterType() {
        return FilterType.KEYWORD;
    }

    @Override
    public String toString() {
        return filterString;
    }
}
