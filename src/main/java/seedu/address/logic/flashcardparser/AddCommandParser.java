package seedu.address.logic.flashcardparser;

import seedu.address.flashcard.Answer;
import seedu.address.flashcard.Flashcard;
import seedu.address.flashcard.ShortAnswerFlashcard;
import seedu.address.flashcard.ShortAnswerQuestion;
import seedu.address.logic.flashcardcommands.AddCommand;

/**
 * Parser of the add command.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the string of arguments to be added.
     * @param args string containing the parameters for the new flashcard
     * @return new add command
     */
    public AddCommand parse (String args) {

        Flashcard flashcard = new ShortAnswerFlashcard(new ShortAnswerQuestion(""), new Answer(""));
        return new AddCommand(flashcard);
    }
}
