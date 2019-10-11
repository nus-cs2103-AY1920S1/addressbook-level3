/*
Step 14.

It has to override execute() from command interface.

Interacts with Game interface.
Extends to Step 15 in Game.java
 */
package seedu.address.logic.commands.switchmode;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Start the game.
 */
public class StartCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_GAME_START_SUCCESS = "Sample game session in progress, ";
    public static final String FIRST_QUESTION_MESSAGE = "guess the keyword! ";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts the word bank identified by the index number used in the displayed card list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    private Index targetIndex;
    private String wordBankName;

    public StartCommand(Index index) {
        this.targetIndex = index;
    }

    public StartCommand(String wordBankName) {
        this.wordBankName = wordBankName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String pathString = "data/" + wordBankName + ".json";
        Path filePath = Paths.get(pathString);
        WordBank wordBank = SampleDataUtil.getSampleWordBank();
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(filePath);
        try {
            Optional<ReadOnlyWordBank> thisBank = addressBookStorage.readAddressBook();
            wordBank = (WordBank) thisBank.get();
        } catch (DataConversionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game newGame = new Game(wordBank);
        model.setGame(newGame);
        String currQuestion = model.getGame().showCurrQuestion();
        return new CommandResult(
                MESSAGE_GAME_START_SUCCESS + FIRST_QUESTION_MESSAGE
                        + "\n"
                        + currQuestion,
                true);
    }
}
