package seedu.deliverymans.logic.parser.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_RESTAURANT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.universal.EditOrderCommand;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.food.Food;

/**
 * Parses input arguments and creates a new EditOrderCommand object
 */
public class EditOrderCommandParser implements Parser<EditOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ORDER, PREFIX_CUSTOMER, PREFIX_RESTAURANT, PREFIX_FOOD);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE), pe);
        }

        EditOrderCommand.EditOrderDescriptor editOrderDescriptor = new EditOrderCommand.EditOrderDescriptor();
        
        parseFoodForEdit(argMultimap.getAllValues(PREFIX_FOOD)).ifPresent(editOrderDescriptor::setFoods);

        if (!editOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditOrderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditOrderCommand(index, editOrderDescriptor);
    }

    /**
     * Parses {@code Collection<String> foods} into a {@code Set<Tag>} if {@code foods} is non-empty.
     * If {@code foods} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero foods.
     */
    private Optional<Set<Food>> parseFoodForEdit(Collection<String> foods) throws ParseException {
        assert foods != null;

        if (foods.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> foodSet = foods.size() == 1 && foods.contains("") ? Collections.emptySet() : foods;
//        return Optional.of(ParserUtil.parseFoods(foodSet));
        return null;
    }

}
