package seedu.flashcard.logic.commands;

import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.Statistics;
import seedu.flashcard.model.Tag;

/**
 * Command to view statistics.
 */
public class ProgressCommand extends Command {
    public static final String COMMAND_WORD = "progress";

    private final String tagName;

    public ProgressCommand (String tagName) {
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        String results;
        if (tagName.isBlank()) {
            results = "Here are the overall statistics"
                    + "\nTotal number of times answered correctly:" + Statistics.getTotalCorrect(flashcardList)
                    + "\nTotal number of times answered wrongly:" + Statistics.getTotalWrong(flashcardList)
                    + "\nTotal number of attempts:" + Statistics.getTotal(flashcardList);
            return new CommandResult(results, false, false);
        }

        if (flashcardList.hasTag(tagName)) {
            Tag targetTag = flashcardList.getTag(tagName);
            results = "Here are the statistics for " + tagName
                    + "\nTotal number of times answered correctly:" + Statistics.getTagTotalCorrect(targetTag)
                    + "\nTotal number of times answered wrongly:" + Statistics.getTagTotalWrong(targetTag)
                    + "\nTotal number of attempts:" + Statistics.getTagTotal(targetTag);
            return new CommandResult(results, false, false);
        }

        return new CommandResult("tag may not exist or wrong name given" , true, false);
    }

}
