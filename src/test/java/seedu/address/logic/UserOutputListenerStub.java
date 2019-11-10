package seedu.address.logic;

import seedu.address.ui.ColorTheme;
import seedu.address.ui.UserOutput;
import seedu.address.ui.listeners.UserOutputListener;

/**
 * A UserOutputListener that copies whatever it is notified.
 */
public class UserOutputListenerStub implements UserOutputListener {

    private UserOutput userOutput;

    public UserOutput getUserOutput() {
        return userOutput;
    }

    @Override
    public void onUserOutput(UserOutput output, ColorTheme results) {
        this.userOutput = output;
    }
}
