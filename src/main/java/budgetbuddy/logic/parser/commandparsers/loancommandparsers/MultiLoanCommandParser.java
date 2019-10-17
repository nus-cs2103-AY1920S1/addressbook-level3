package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.loancommands.MultiLoanCommand;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.loan.util.PersonLoanIndexPair;

/**
 * Represents a command parser that can handle multiple loan targets.
 */
public abstract class MultiLoanCommandParser implements CommandParser<MultiLoanCommand> {

    protected List<PersonLoanIndexPair> personLoanIndexPairs;
    protected List<Index> personIndices;

    public MultiLoanCommandParser() {
        personLoanIndexPairs = new ArrayList<PersonLoanIndexPair>();
        personIndices = new ArrayList<Index>();
    }

    /**
     * Parses multiple loan targets into two lists to be passed on to Command classes.
     * {@code personLoanIndexPairs} is used for loan targets that a subset of a person's loans.
     * {@code personIndices} is used for persons whose entire loan list is targeted.
     * @param args The loan targets (as a String) to be parsed (e.g. 1 2.3 4.2-3-5).
     * @throws ParseException If parsing fails at any point.
     */
    protected void parseMultiLoanArgs(String args) throws ParseException {
        String[] argsArray = args.split("\\s+");
        for (String arg : argsArray) {
            if (arg.length() == 1) {
                personIndices.add(CommandParserUtil.parseIndex(arg));
            } else if (arg.length() > 1) {
                parsePersonLoanIndexPair(arg);
            }
        }
    }

    /**
     * Parses a subset of a specific person's loans.
     * The person's index and the targeted loans' indices are stored in {@code personLoanIndexPairs}.
     * @param arg The person's loans as a String (e.g. 1.3-4-6).
     * @throws ParseException If parsing fails at any point.
     */
    private void parsePersonLoanIndexPair(String arg) throws ParseException {
        List<String> indicesString = new ArrayList<String>();
        try {
            String[] personLoansArray = arg.split("\\.");
            indicesString.add(personLoansArray[0]);
            indicesString.addAll(Arrays.asList(personLoansArray));
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            throw new ParseException(String.format("Parsing of %s failed.", arg));
        }

        List<Index> indices = new ArrayList<Index>();
        for (String index : indicesString) {
            indices.add(CommandParserUtil.parseIndex(index));
        }

        for (int i = 1; i < indices.size(); i++) {
            personLoanIndexPairs.add(new PersonLoanIndexPair(indices.get(0), indices.get(i)));
        }
    }
}
