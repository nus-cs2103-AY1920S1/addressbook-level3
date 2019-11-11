// @@author shiweing
package tagline.logic.commands.note;

import java.util.List;

/**
 * Stores filter for note listing.
 */
public interface NoteFilter<T> {
    /**
     * Represents the type of filter to list notes by.
     */
    enum FilterType {
        KEYWORD, TAG
    }

    /**
     * Returns a list of filters.
     */
    List<T> getFilterValues();

    /**
     * Returns the {@code FilterType}.
     */
    FilterType getFilterType();
}
