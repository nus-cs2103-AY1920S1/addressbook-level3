// @@author shiweing
package tagline.logic.commands.note;

import java.util.Collections;
import java.util.List;

import tagline.model.tag.Tag;

/**
 * Generates a {@code TagFilter} containing the list of tags to find and filter notes against.
 */
public class TagFilter implements NoteFilter<Tag> {

    private final String filterString;
    private final List<Tag> filterValues;

    public TagFilter(String tagString, List<Tag> tags) {
        filterString = tagString;
        filterValues = tags;
    }

    public List<Tag> getFilterValues() {
        return Collections.unmodifiableList(filterValues);
    }

    public FilterType getFilterType() {
        return FilterType.TAG;
    }

    @Override
    public String toString() {
        return filterString;
    }
}
