package seedu.address.calendar.logic;

import seedu.address.calendar.commands.AlternativeCommand;
import seedu.address.calendar.model.Calendar;
import seedu.address.logic.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class SuggestionManager {
    private List<AlternativeCommand> suggestedCommands = new ArrayList<>();

    void add(Command<Calendar> latestSuggestedCommand) {
        assert latestSuggestedCommand instanceof AlternativeCommand : "Only alternative commands can be added here";
        suggestedCommands.clear();
        suggestedCommands.add((AlternativeCommand) latestSuggestedCommand);
    }

    void add(Command<Calendar>... latestSuggestedCommands) {
        for (Command<Calendar> latestSuggestedCommand : latestSuggestedCommands) {
            assert latestSuggestedCommand instanceof AlternativeCommand : "Only alternative commands can be added here";
        }
        suggestedCommands.clear();

        for (Command<Calendar> latestSuggestCommand : latestSuggestedCommands) {
            suggestedCommands.add((AlternativeCommand) latestSuggestCommand);
        }
    }

    List<AlternativeCommand> getCommands() {
        assert !suggestedCommands.isEmpty() : "A suggested command must be added before retrieving it";
        List<AlternativeCommand> commands = List.copyOf(suggestedCommands);
        suggestedCommands.clear();
        return commands;
    }

    void forgetSuggestion() {
        suggestedCommands.clear();
    }

    boolean hasSuggestedCommand() {
        return !suggestedCommands.isEmpty();
    }
}
