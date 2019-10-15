package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_PARSE_ERROR;

import java.util.Arrays;
import java.util.List;

import seedu.flashcard.logic.commands.AddCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.McqQuestion;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.flashcard.ShortAnswerQuestion;


/**
 * Parses input arguments to generate {@Code AddCommand}
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the string of arguments to be added.
     * @param args string containing the parameters for the new flashcard
     * @return new add command
     */
    public AddCommand parse(String args) throws ParseException {
        try {
            int firstIndex = args.indexOf("/");
            int secondIndex = args.indexOf("/ans", firstIndex + 1);
            String question = args.substring(firstIndex + 3, secondIndex).trim();
            String answer = args.substring(secondIndex + 4).trim();
            Flashcard toAdd;
            // determine which type of question is it and add correspondingly
            if (args.substring(firstIndex + 1, firstIndex + 2).equals("m")) {
                String[] options = question.split("/");
                List<String> optionList = Arrays.asList(options);
                toAdd = new McqFlashcard(new McqQuestion(question, optionList), new Answer(answer));
            } else {
                toAdd = new ShortAnswerFlashcard(new ShortAnswerQuestion(question), new Answer(answer));
            }
            return new AddCommand(toAdd);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_PARSE_ERROR);
        }
    }
}
