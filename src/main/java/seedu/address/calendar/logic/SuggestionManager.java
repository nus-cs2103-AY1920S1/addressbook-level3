package seedu.address.calendar.logic;

import java.util.Optional;

import seedu.address.calendar.logic.commands.AlternativeCommand;
import seedu.address.calendar.model.Calendar;
import seedu.address.logic.commands.Command;

/**
 * Manages suggested commands to be executed.
 */
public class SuggestionManager {
    private Optional<AlternativeCommand> suggestedCommand = Optional.empty();

    void add(Command<Calendar> latestSuggestedCommand) {
        assert latestSuggestedCommand instanceof AlternativeCommand : "Only alternative commands can be added here";
        suggestedCommand = Optional.of((AlternativeCommand) latestSuggestedCommand);
    }

    AlternativeCommand getCommand() {
        return suggestedCommand.orElseThrow();
    }

    void forgetSuggestion() {
        suggestedCommand = Optional.empty();
    }

    boolean hasSuggestedCommand() {
        return !suggestedCommand.isEmpty();
    }
}
