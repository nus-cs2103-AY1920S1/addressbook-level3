package dream.fcard.logic.respond;

import dream.fcard.model.State;

/**
 * Interface to take in user input and execute program behaviour.
 */
public class Responder {
    /**
     * Takes in user input and the current state of the program. Uses the user input to decide which is the appropriate
     * responseFunc to call.
     *
     * @param input A String representing the user input.
     * @param state The current State of the program.
     */
    public static void takeInput(String input, State state) {
        for (Responses r : Responses.values()) {
            if (r.call(input, state)) {
                break;
            }
        }
    }
}
