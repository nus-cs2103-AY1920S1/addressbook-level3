package dream.fcard.logic.respond.commands;

import dream.fcard.logic.respond.ResponseFunc;
import dream.fcard.logic.respond.exception.DuplicateFoundException;
import dream.fcard.model.State;
import dream.fcard.model.exceptions.DeckNotFoundException;

/**
 * Represents a command that creates a new deck or card (?).
 */
public class CreateCommand implements ResponseFunc {

    @Override
    public boolean funcCall(String i, State s) throws DeckNotFoundException, DuplicateFoundException {
        return false;
    }
}

