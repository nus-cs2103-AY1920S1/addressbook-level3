package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;
import seedu.address.model.util.SampleDataUtil;

//@@author keiteo
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

    private final AddressBookParser addressBookParser;

    private final String deckName; // TODO: will integrate after deck class is completed

    public StartCommand(AddressBookParser addressBookParser) {
        this.deckName = "Test"; // TODO: will get a random deck name from list of decks
        this.addressBookParser = addressBookParser;
    }

    public StartCommand(AddressBookParser addressBookParser, String deckName) {
        this.deckName = deckName;
        this.addressBookParser = addressBookParser;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // start stub TODO: to replace with actual deck classes
        ArrayList<FlashCard> testList = new ArrayList<>();
        testList.add(new FlashCard(new Question("1+1"), new Answer("2"), new Rating("good"),
                SampleDataUtil.getTagSet("test")));
        testList.add(new FlashCard(new Question("1+2"), new Answer("3"), new Rating("good"),
                SampleDataUtil.getTagSet("test")));
        testList.add(new FlashCard(new Question("1+3"), new Answer("4"), new Rating("good"),
                SampleDataUtil.getTagSet("test")));
        // end stub

        model.initializeTestModel(testList);
        if (!model.hasTestFlashCard()) {
            return new CommandResult("Empty deck!");
        }

        addressBookParser.startTest();
        String question = model.getTestQuestion();
        addressBookParser.setAwaitingAnswer(true);
        return new CommandResult(MESSAGE_START_TEST_SUCCESS + "\n" + question);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartCommand // instanceof handles nulls
                && deckName.equals(((StartCommand) other).deckName)); // state check
    }

}
