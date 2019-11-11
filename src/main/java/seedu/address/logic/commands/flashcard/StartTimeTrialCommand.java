package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.TIMETRIAL;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Loads the next flashcard into the window.
 */
public class StartTimeTrialCommand extends Command {

    public static final String COMMAND_WORD = TIMETRIAL;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts a time trial of all all the flashcards"
            + " with all the specified tag.\n"
            + "Parameters: [TAG]\n"
            + "Example: " + COMMAND_WORD + " CS2100";

    public static final String MESSAGE_SUCCESS = "Time trial started";

    private static final Logger logger = LogsCenter.getLogger(StartTimeTrialCommand.class);

    private String[] tagKeywords;
    private final FlashcardContainsTagPredicate tagPredicate;


    /**
     * Creates an AddFlashcardCommand to add the specified {@code Flashcard}
     */
    public StartTimeTrialCommand(FlashcardContainsTagPredicate tagPredicate, String[] tagKeywords) {
        requireAllNonNull(tagPredicate, tagKeywords);

        this.tagKeywords = tagKeywords;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder sb = new StringBuilder();
        for (Tag tag: tagPredicate.getTags()) {
            sb.append(tag + " ");
        }

        logger.info("Starting time trial of flashcards with the tags: "
            + sb.toString().trim());

        requireNonNull(model);

        ArrayList<Flashcard> deck = model.getTaggedFlashcards(tagPredicate);
        if (deck.size() == 0) {
            throw new TagNotFoundException("There are no flashcards with the tags specified!");
        }

        Optional<ArrayList<Flashcard>> optionalDeck = Optional.of(deck);

        assert (optionalDeck.get().size() > 0);

        return new FlashcardCommandResult(String.format(MESSAGE_SUCCESS), true, optionalDeck);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTimeTrialCommand // instanceof handles nulls
                && compareTagKeywords(((StartTimeTrialCommand) other).tagKeywords)
                && this.tagPredicate.equals(((StartTimeTrialCommand) other).tagPredicate));
    }

    /**
     * Used for testing if equals to other StartTimeTrial comamands, compares all tags.
     * @param tags other array of tags to compare to
     * @return boolean if both arrays of tags are the same
     */
    public boolean compareTagKeywords(String[] tags) {
        return new HashSet<>(Arrays.asList(tags))
            .equals(new HashSet<>(Arrays.asList(this.tagKeywords)));
    }
}
