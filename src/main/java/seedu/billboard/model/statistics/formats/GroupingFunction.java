package seedu.billboard.model.statistics.formats;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface GroupingFunction<T> {
    Map<String, ? extends List<? extends T>> group(List<? extends T> items);
}
