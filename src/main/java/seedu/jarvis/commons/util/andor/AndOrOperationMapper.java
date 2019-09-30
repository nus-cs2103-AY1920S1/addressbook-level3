package seedu.jarvis.commons.util.andor;

import static seedu.jarvis.commons.util.andor.AndOrOperation.AND;
import static seedu.jarvis.commons.util.andor.AndOrOperation.LEAF;
import static seedu.jarvis.commons.util.andor.AndOrOperation.OR;

/**
 * A helper class that maps the {@code AndOrOperation enum}'s constants to their {@code String}
 * forms, and vice versa.
 *
 * @author ryanYtan
 */
public class AndOrOperationMapper {
    /**
     * Returns an {@code AndOrOperation enum} type given a {@code String} representing the type.
     *
     * @param type string to check
     * @return an {@code AndOrOperation enum} type
     */
    static AndOrOperation resolveType(String type) {
        switch (type) {
        case "and":
            return AND;
        case "or":
            return OR;
        default:
            return LEAF;
        }
    }
}
