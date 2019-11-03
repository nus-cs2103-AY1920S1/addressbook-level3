package dream.fcard.logic.respond;

import dream.fcard.model.StateEnum;
import dream.fcard.model.StateHolder;

/**
 * Enum of groups of Responses enum. Their function argument is a lambda
 * that takes in user input and state to determine if their response
 * belongs to the group.
 */
public enum ResponseGroup {
    DEFAULT(i -> StateHolder.getState().getCurrState() == StateEnum.DEFAULT),
    MATCH_ALL(i -> true);

    private ResponseFunc func;

    ResponseGroup(ResponseFunc f) {
        func = f;
    }

    public boolean isInGroup(String i) {
        return func.funcCall(i);
    }
}
