package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;
import seedu.address.model.util.SampleDataUtil;

/**
 * Starts the flashcard test by going through the cards in the specified deck.
 * If no deck name is supplied, a random deck will be chosen.
 */
public class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts the flashcard test by going through the cards in the specified deck.\n"
            + "Parameters: DECK NAME. If no argument is supplied, a random deck will be chosen.\n"
            + "Example: " + COMMAND_WORD + " physics";

    public static final String MESSAGE_START_TEST_SUCCESS = "Starting test...";

    private final String deckName; // TODO: will integrate after deck class is completed

    public StartCommand() {
        this.deckName = "Test"; // TODO: will get a random deck name from list of decks
    }

    public StartCommand(String deckName) {
        this.deckName = deckName;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // TODO: get the system to enter start mode, disable other commands except good/bad/end

        // start stub
        List<FlashCard> testList = new ArrayList();
        testList.add(new FlashCard(new Question("1+1"), new Answer("2"), new Rating("good"),
                SampleDataUtil.getTagSet("test")));
        testList.add(new FlashCard(new Question("1+2"), new Answer("3"), new Rating("good"),
                SampleDataUtil.getTagSet("test")));
        testList.add(new FlashCard(new Question("1+3"), new Answer("4"), new Rating("good"),
                SampleDataUtil.getTagSet("test")));
        // end stub

        // TODO: integrate into GUI
        for (FlashCard fc : testList) {
            fc.getQuestion();
            // TODO: await command response
            fc.getAnswer();
        }

        return new CommandResult(MESSAGE_START_TEST_SUCCESS);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartCommand // instanceof handles nulls
                && deckName.equals(((StartCommand) other).deckName)); // state check
    }

}
