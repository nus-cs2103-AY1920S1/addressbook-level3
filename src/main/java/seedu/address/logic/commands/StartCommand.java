package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.FlashCard;

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

    public static final String MESSAGE_NO_FLASHCARDS = "No flashcards to test!";

    private final AddressBookParser addressBookParser;

    private final String tagName;

    public StartCommand(AddressBookParser addressBookParser) {
        this.tagName = "";
        this.addressBookParser = addressBookParser;
    }

    public StartCommand(AddressBookParser addressBookParser, String tagName) {
        assert(!tagName.isEmpty());
        this.tagName = tagName;
        this.addressBookParser = addressBookParser;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);


        // start stub TODO: delete this
        /*
        ArrayList<FlashCard> testList = new ArrayList<>();
        testList.add(new FlashCard(new Question("1+1"), new Answer("2"), new Rating("good"),
                SampleDataUtil.getTagSet("test")));
        testList.add(new FlashCard(new Question("1+2"), new Answer("3"), new Rating("good"),
                SampleDataUtil.getTagSet("test")));
        testList.add(new FlashCard(new Question("1+3"), new Answer("4"), new Rating("good"),
                SampleDataUtil.getTagSet("test")));
         */
        // end stub

        ArrayList<FlashCard> testList = searchTag(model);
        model.initializeTestModel(testList);
        if (!model.hasTestFlashCard()) {
            return new CommandResult(MESSAGE_NO_FLASHCARDS);
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
                && tagName.equals(((StartCommand) other).tagName)); // state check
    }

    /** Searches the list of flashcard to fetch the relevant tags. */
    private ArrayList<FlashCard> searchTag(Model model) {
        if (tagName.isEmpty()) {
            return new ArrayList<>(model.getFlashCardList());
        }
        CategoryContainsAnyKeywordsPredicate getPredicate =
                new CategoryContainsAnyKeywordsPredicate(Arrays.asList(tagName));
        model.updateFilteredFlashCardList(getPredicate);
        return new ArrayList<>(model.getFilteredFlashCardList());
    }
}
