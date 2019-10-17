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

public abstract class MultiLoanCommandParser implements CommandParser<MultiLoanCommand> {

    protected List<PersonLoanIndexPair> personLoanIndexPairs;
    protected List<Index> personIndices;

    public MultiLoanCommandParser() {
        personLoanIndexPairs = new ArrayList<PersonLoanIndexPair>();
        personIndices = new ArrayList<Index>();
    }

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

    protected void parsePersonLoanIndexPair(String arg) throws ParseException {
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
