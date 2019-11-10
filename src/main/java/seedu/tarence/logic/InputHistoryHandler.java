package seedu.tarence.logic;

import java.util.List;

import seedu.tarence.model.Model;

/**
 * Handles retrieving of past inputs to fill the command box with.
 */
public class InputHistoryHandler {

    private Model model;

    public InputHistoryHandler(Model model) {
        this.model = model;
    }

    public String getPastInput(String arrowDirection) {
        if (arrowDirection.equals("")) {
            model.resetInputHistoryIndex();
            return null;
        }

        List<String> inputHistory = model.getInputHistory();

        if (inputHistory.size() == 0) {
            return null; // no change to value of command box
        }

        if (model.hasInputChanged()) {
            model.resetInputHistoryIndex();
            model.setInputChangedToFalse();
            return inputHistory.get(0); // return last saved input
        }

        int inputHistoryIndex = model.getInputHistoryIndex();
        if (arrowDirection.equals("up")
            && inputHistoryIndex < inputHistory.size() - 1) {
            model.incrementInputHistoryIndex();
        } else if (arrowDirection.equals("down")
                && inputHistoryIndex > 0) {
            model.decrementInputHistoryIndex();
        }
        return inputHistory.get(model.getInputHistoryIndex());
    }
}
