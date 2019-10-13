package calofit.logic.parser;

import calofit.logic.commands.SetBudgetCommand;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.dish.Calorie;

/**
 * Parses input arguments and creates a new {@link SetBudgetCommand} object.
 */
public class SetBudgetCommandParser implements Parser<SetBudgetCommand> {

    public static final String MISSING_CALORIE_FIELD = "Missing calorie field!";

    @Override
    public SetBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MISSING_CALORIE_FIELD);
        }
        Calorie calories = ParserUtil.parseCalorie(argMultimap.getPreamble());
        return new SetBudgetCommand(calories);
    }
}
