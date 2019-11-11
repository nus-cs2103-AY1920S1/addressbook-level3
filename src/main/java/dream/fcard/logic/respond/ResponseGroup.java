package dream.fcard.logic.respond;

import dream.fcard.model.StateEnum;
import dream.fcard.model.StateHolder;

/**
 * Enum of groups of Responses enum. Their function argument is a lambda
 * that takes in user input and state to determine if their response
 * belongs to the group.
 */
public enum ResponseGroup {
    TEST(i -> StateHolder.getState().getCurrState() == StateEnum.TEST),
    TEST_FBCARD(i -> StateHolder.getState().getCurrState() == StateEnum.TEST_FBCARD),
    TEST_FBCARD_BACK(i -> StateHolder.getState().getCurrState() == StateEnum.TEST_FBCARD_BACK),
    TEST_MCQ(i -> StateHolder.getState().getCurrState() == StateEnum.TEST_MCQ),
    TEST_MCQ_BACK(i-> StateHolder.getState().getCurrState() == StateEnum.TEST_MCQ_BACK),
    TEST_JSJAVA(i -> StateHolder.getState().getCurrState() == StateEnum.TEST_JSJAVA),
    DEFAULT(i -> StateHolder.getState().getCurrState() == StateEnum.DEFAULT),
    MAKE_JS(i -> StateHolder.getState().getCurrState() == StateEnum.MAKE_JS),
    MAKE_JAVA(i -> StateHolder.getState().getCurrState() == StateEnum.MAKE_JAVA),
    MATCH_ALL(i -> true);

    private ResponseFunc func;

    ResponseGroup(ResponseFunc f) {
        func = f;
    }

    public boolean isInGroup(String i) {
        return func.funcCall(i);
    }
}
