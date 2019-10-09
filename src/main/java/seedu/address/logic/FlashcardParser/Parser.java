package seedu.address.logic.FlashcardParser;

import seedu.address.logic.FlashcardCommands.Command;

public interface Parser<T extends Command> {

    T parse(String userInput);
}

