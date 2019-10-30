package seedu.address.logic.quiz.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.quiz.commands.FindCommand;
import seedu.address.logic.quiz.parser.exceptions.ParseException;
import seedu.address.model.quiz.person.NameContainsKeywordsPredicate;



/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        boolean allowTypo = false;
        String trimmedArgs = args.trim();

        if (trimmedArgs.startsWith("-i ")) {
            allowTypo = true;
            trimmedArgs = trimmedArgs.substring(3);
        }

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.substring(0, 5).equals("<key>")) {
            trimmedArgs = trimmedArgs.substring(5).trim();
            String[] nameKeywords = trimmedArgs.split(",");

            if (trimmedArgs.equals("")) {
                throw new ParseException("Word parameter cannot be empty");
            }

            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords), allowTypo));
        } else {
            String[] nameKeywords = trimmedArgs.split(" <key>");

            if (nameKeywords.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            } else {
                String[] instructionKeyword = nameKeywords[0].split(",");
                String[] searchKeyword = nameKeywords[1].split(",");
                validateParsedFindKeyword(instructionKeyword, searchKeyword);

                return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(instructionKeyword),
                        Arrays.asList(searchKeyword), allowTypo));
            }
        }
    }

    /**
     * Validate the input that the user enter and throw an error if the command does not
     * follow the syntax provided.
     *
     * @param instructionKeyword the left part of your command before key instruction
     * @param searchKeyword command or keywords that follows after the key instruction
     * @throws ParseException
     */
    public void validateParsedFindKeyword (String[] instructionKeyword, String[] searchKeyword) throws ParseException {
        for (int i = 0; i < searchKeyword.length; i++) {
            String validWord = searchKeyword[i].replaceAll("[^A-Za-z0-9]", "");
            if (validWord.equals("")) {
                throw new ParseException("Some of the search keywords are empty");
            }
        }

        if (instructionKeyword.length > 5) {
            throw new ParseException("Category instruction overload. Maximum allowed 5 instructions");
        } else if (searchKeyword.length == 0) {
            throw new ParseException(String.format("Nothing to search here", FindCommand.MESSAGE_USAGE));
        }
    }
}
