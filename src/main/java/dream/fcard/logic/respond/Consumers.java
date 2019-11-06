package dream.fcard.logic.respond;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Dispatches global consumers for CLI.
 */
public class Consumers {
    private static HashMap<String, Consumer> consumers = new HashMap<>();

    public static void addConsumer(String identifier, Consumer c) {
        consumers.put(identifier, c);
    }

    public static void removeConsumer(String identifier) {
        consumers.remove(identifier);
    }

    /**
     * Get a consumer and run it.
     * @param key The identifier for the consumer
     * @param x the object for the consumer
     * @return whether the consumer was successfully used.
     */
    @SuppressWarnings("unchecked")
    public static Boolean doTask(String key, Object x) {
        Consumer c = consumers.get(key);
        if (c == null) {
            return false;
        }
        c.accept(x);
        return true;
    }
}
