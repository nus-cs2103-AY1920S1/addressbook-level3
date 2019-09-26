package seedu.jarvis.commons.util.andor;

import static seedu.jarvis.commons.util.andor.AndOrOperation.AND;
import static seedu.jarvis.commons.util.andor.AndOrOperation.OR;
import static seedu.jarvis.commons.util.andor.AndOrOperation.LEAF;

import java.util.EnumMap;

/**
 * A helper class that maps the {@code AndOrOperation enum}'s constants to their {@code String}
 * forms, and vice versa.
 */
public class AndOrOperationMapper {

    /**
     * Returns an {@code AndOrOperation enum} type to a String, returns null if the type is a
     * leaf.
     *
     * @param type {@code AndOrOperation enum} type
     * @return a String representing the given type
     */
    static String asString(AndOrOperation type) {
        EnumMap<AndOrOperation, String> operationMap = new EnumMap<>(AndOrOperation.class);
        operationMap.put(AND, "all of");
        operationMap.put(OR, "one of");
        operationMap.put(LEAF, null);
        return operationMap.get(type);
    }

    /**
     * Returns an {@code AndOrOperation enum} type given the String.
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
