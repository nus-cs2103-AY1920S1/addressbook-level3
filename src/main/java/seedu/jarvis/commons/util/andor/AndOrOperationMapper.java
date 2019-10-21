package seedu.jarvis.commons.util.andor;

import static seedu.jarvis.commons.util.andor.AndOrOperation.AND;
import static seedu.jarvis.commons.util.andor.AndOrOperation.DATA;
import static seedu.jarvis.commons.util.andor.AndOrOperation.OR;

/**
 * Mapper from different strings to its respective AndOrOperation.
 */
public class AndOrOperationMapper {
    public static final String KEYWORD_AND_NODE = "and";
    public static final String KEYWORD_OR_NODE = "or";

    /**
     * Returns an {@code AndOrOperation} value representing the type of node to create.
     *
     * @param type of node
     * @return an {@AndOrOperation} value
     */
    protected static AndOrOperation ofType(String type) {
        switch (type) {
        case KEYWORD_AND_NODE:
            return AND;
        case KEYWORD_OR_NODE:
            return OR;
        default:
            return DATA;
        }
    }
}
