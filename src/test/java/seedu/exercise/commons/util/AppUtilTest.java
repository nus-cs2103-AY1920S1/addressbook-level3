package seedu.exercise.commons.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.exercise.MainApp;
import seedu.exercise.commons.core.State;

public class AppUtilTest {

    @Test
    public void getImage_exitingImage() {
        assertNotNull(AppUtil.getImage("/images/ExerHealth.png"));
    }

    @Test
    public void getImage_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.getImage(null));
    }

    @Test
    public void checkArgument_true_nothingHappens() {
        AppUtil.checkArgument(true);
        AppUtil.checkArgument(true, "");
    }

    @Test
    public void checkArgument_falseWithoutErrorMessage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AppUtil.checkArgument(false));
    }

    @Test
    public void checkArgument_falseWithErrorMessage_throwsIllegalArgumentException() {
        String errorMessage = "error message";
        assertThrows(IllegalArgumentException.class, errorMessage, () -> AppUtil.checkArgument(false, errorMessage));
    }

    @Test
    public void requireMainAppState_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.requireMainAppState(null));
    }

    @Test
    public void requireMainAppState_sameState_nothingHappens() {
        //Static methods cannot be overridden and the setState method is simple enough to use it here
        MainApp.setState(State.NORMAL);
        AppUtil.requireMainAppState(State.NORMAL);
    }

    @Test
    public void requireMainAppState_differentState_throwsIllegalStateException() {
        MainApp.setState(State.NORMAL);
        assertThrows(IllegalStateException.class,
                String.format(AppUtil.UNEXPECTED_STATE,
                        State.IN_CONFLICT.toString()), () -> AppUtil.requireMainAppState(State.IN_CONFLICT));
    }
}
