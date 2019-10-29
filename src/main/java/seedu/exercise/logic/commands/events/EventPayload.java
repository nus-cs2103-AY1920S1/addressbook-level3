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

    /**
     * Stores the given key data pair into the event payload.
     *
     * @param key the key with which the specified data is to be associated
     * @param data the data to be stored in the payload under the given key
     * @return the current instance of the EventPayload with the key-data
     * mapping added
     */
    public EventPayload<T> put(String key, T data) {
        payload.put(key, data);
        return this;
    }

    /**
     * Returns the data to which the specified key is mapped,
     * or null if this payload contains no mapping for the key.
     *
     * @param key the key whose associated data is to be returned
     * @return the data to which the specified key is mapped,
     * or null if this payload contains no mapping for the key
     */
    public Object get(String key) {
        return payload.get(key);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventPayload // instanceof handles nulls
                && payload.equals(((EventPayload) other).payload));
    }
}
