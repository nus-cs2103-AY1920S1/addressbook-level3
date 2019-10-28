package dream.fcard.logic.respond.commands;

import dream.fcard.logic.respond.ResponseFunc;
import dream.fcard.logic.respond.exception.DuplicateFoundException;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.State;


public class EditCommand implements ResponseFunc {
    @Override
    public boolean funcCall(String i, State s) throws DeckNotFoundException, DuplicateFoundException {
        return false;
    }
}
