package seedu.flashcard.logic.parser;

import seedu.flashcard.logic.commands.TagCommand;

/**
 * Parses input arguments to generate {@Code TagCommand}
 */
public class TagCommandParser implements Parser {

    /**
     * TODO: Implement the following tag command parser.
     * TODO: Write corresponding test cases for this parser.
     * Parses the string of arguments.
     * @param args string containing the parameters for the new model
     * @return new tag command
     */
    @Override
    public TagCommand parse(String args) {
        String[] parameters = args.split(" ", 2);
        return new TagCommand(Integer.parseInt(parameters[0]), parameters[1]);
    }
}
