package dream.fcard.logic.respond;

import dream.fcard.model.State;

/**
 * Interface for lambdas or commands classes for Responses enum.
 */
public interface ResponseFunc {

    /**
     * Lambda's signature interface.
     * @param i input string
     * @param s state object
     */
    boolean funcCall(String i, State s);
}
