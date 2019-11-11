package seedu.billboard.model.statistics.formats;

import java.util.List;
import java.util.Map;

/**
 * Function that groups a list of type T into a map of strings to lists of type T.
 */
@FunctionalInterface
public interface GroupingFunction<T> {
    Map<String, ? extends List<? extends T>> group(List<? extends T> items);
}
