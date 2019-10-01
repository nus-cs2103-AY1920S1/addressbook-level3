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
public class AndOrOperationMapperUtil {
    /**
     * Returns an {@code AndOrOperation enum} type given a {@code String} representing the type.
     *
     * @param type string to check
     * @return an {@code AndOrOperation enum} type
     */
    protected static AndOrOperation resolveType(String type) {
        if (type == null) {
            return LEAF;
        }

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
