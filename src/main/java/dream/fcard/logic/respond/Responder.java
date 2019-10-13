package dream.fcard.logic.respond;

import dream.fcard.model.State;

/**
 * Interface to take in user input and execute program behaviour.
 */
public class Responder {
    public static void takeInput(String input, State state) {
        for (Responses r : Responses.values()) {
            if (r.call(input, state)) {
                break;
            }
        }
    }
}
