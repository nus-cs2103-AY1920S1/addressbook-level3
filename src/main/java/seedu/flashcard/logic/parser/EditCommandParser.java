package seedu.flashcard.logic.parser;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_QUESTION;

import seedu.flashcard.logic.commands.EditCommand;

/**
 * Parse input argument to generate a {@Code EditCommand}
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * TODO: Implement the following parser of edit command.
     * TODO: Write corresponding tests of this parser.
     * Parse the given context in the edit command
     * @param userInput The input string from the user
     * @return new edit command
     */
    @Override
    public EditCommand parse(String userInput) {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_QUESTION, PREFIX_ANSWER);

        int idToEdit;
        idToEdit = parseInt(userInput.split(" ")[1]);

        EditCommand.EditFlashCardDescriptor editFlashCardDescriptor = new EditCommand.EditFlashCardDescriptor();

        if(argMultimap.getValue(PREFIX_QUESTION).isPresent()) {
            editFlashCardDescriptor.setQuestion(ParserUtil.parseShortAnswerQuestion(argMultimap.getValue(PREFIX_QUESTION).get()));
        }

        if(argMultimap.getValue(PREFIX_ANSWER).isPresent()) {
            editFlashCardDescriptor.setAnswer(ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get()));
        }

     return new EditCommand(idToEdit, editFlashCardDescriptor);
    }
}
