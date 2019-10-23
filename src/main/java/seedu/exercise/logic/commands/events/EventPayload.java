package seedu.exercise.logic.commands.events;

import java.util.HashMap;

/**
 * A wrapper class to share essential data between Command and the corresponding Event
 * for undo and redo commands. Data is accessed using keys specified by the Command
 * classes.
 *
 * @param <T> The data stored can be of multiple types
 */
public class EventPayload<T> {

    private HashMap<String, ? super T> payload = new HashMap<>();

    public void put(String key, T data) {
        payload.put(key, data);
    }

    public Object get(String key) {
        return payload.get(key);
    }
}
