package dream.fcard.logic.respond;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

import dream.fcard.model.StateEnum;

/**
 * Dispatches global consumers for CLI.
 */
public class Dispatcher {
    private static HashMap<String, Consumer> consumers = new HashMap<>();

    public static void addConsumer(String identifier, Consumer c) {
        consumers.put(identifier, c);
    }

    public static void removeConsumer(String identifier) {
        consumers.remove(identifier);
    }

    @SuppressWarnings("unchecked")
    public static void doTask(String identifier, Object obj) {
        Consumer c = consumers.get(identifier);
        if (c != null) {
            c.accept(obj);
        }
    }

    private static Boolean validInput(String input) {
        return (input.contains("front/") && input.contains("back/"))
                | (input.contains("front/") && input.contains("choice/"))
                | (input.contains("front/") && input.contains("testCase/"));
    }

    private static String getDeckName(String input) {
        return input.split("deck/")[1].split(" ")[0].strip();
    }
}
