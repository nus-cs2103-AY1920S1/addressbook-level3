/*
Step 14.

It has to override execute() from command interface.

Interacts with Game interface.
Extends to Step 15 in Game.java
 */
package seedu.address.logic.commands.switches;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.util.ModeEnum;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Starts the game.
 */
public class StartCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts the word bank identified by the index number used in the displayed card list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    private static final String MESSAGE_GAME_IN_PROGRESS = "A game session is still in progress!"
            + " (Use 'stop' to terminate) Guess the word:";

    public StartCommand() {

    }
    public ModeEnum getNewMode(ModeEnum old) throws ModeSwitchException {
        return ModeEnum.GAME;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (model.getGame() != null && !model.getGame().isOver()) {
            throw new CommandException(MESSAGE_GAME_IN_PROGRESS
                    + "\n" + model.getGame().getCurrQuestion());
        }

        String wordBankName = model.getWordBank().getName();
        String pathString = "data/" + wordBankName + ".json";
        Path filePath = Paths.get(pathString);
        WordBank wordBank = SampleDataUtil.getSampleWordBank();
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(filePath);
        addressBookStorage.getWordBankList();
        String usedWordBankTitle = "Pokémon sample"; // todo change later
        try {
            Optional<ReadOnlyWordBank> thisBank = addressBookStorage.readAddressBook();
            if (thisBank.isPresent()) {
                wordBank = (WordBank) thisBank.get();
                usedWordBankTitle = wordBankName;
            }
        } catch (DataConversionException e) {
            e.printStackTrace();
        }
        Game newGame = new Game(wordBank);
        model.setGame(newGame);
        String currQuestion = model.getGame().getCurrQuestion();
        return new StartCommandResult(usedWordBankTitle, currQuestion);
    }
}
