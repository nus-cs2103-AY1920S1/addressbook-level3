package seedu.flashcard.flashcardlogic.parser;

import seedu.flashcard.flashcardmodel.flashcard.Answer;
import seedu.flashcard.flashcardmodel.flashcard.Flashcard;
import seedu.flashcard.flashcardmodel.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.flashcardmodel.flashcard.ShortAnswerQuestion;
import seedu.flashcard.flashcardlogic.commands.AddCommand;

/**
 * Parser of the add command.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the string of arguments to be added.
     * @param args string containing the parameters for the new flashcardmodel
     * @return new add command
     */
    public AddCommand parse (String args) {

        Flashcard flashcard = new ShortAnswerFlashcard(new ShortAnswerQuestion(""), new Answer(""));
        return new AddCommand(flashcard);
    }
}
