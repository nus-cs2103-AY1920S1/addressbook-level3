package dream.fcard.logic.respond;

import dream.fcard.model.State;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.IndexNotFoundException;

/**
 * Interface for lambdas or commands classes for Responses enum.
 */
public interface ResponseFunc {

    /**
     * Lambda's signature interface.
     *
     * @param commandInput string
     * @param programState state object
     */
    boolean funcCall(String commandInput, State programState) throws DeckNotFoundException, IndexNotFoundException;
}
