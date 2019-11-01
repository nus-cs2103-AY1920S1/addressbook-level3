package dream.fcard.logic.respond;

import dream.fcard.model.State;
import dream.fcard.model.StateEnum;

/**
 * Enum of groups of Responses enum. Their function argument is a lambda
 * that takes in user input and state to determine if their response
 * belongs to the group.
 */
public enum ResponseGroup {
    CREATE((i,s) -> s.getCurrState() == StateEnum.CREATE),
    DEFAULT((i,s) -> s.getCurrState() == StateEnum.DEFAULT),
    MATCH_ALL((i,s) -> true);

    private ResponseFunc func;

    ResponseGroup(ResponseFunc f) {
        func = f;
    }

    public boolean isInGroup(String i, State s) {
        return func.funcCall(i,s);
    }
}
