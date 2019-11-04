package seedu.deliverymans.logic.parser.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.commands.universal.EditOrderCommand.MESSAGE_INVALID_FOOD_FORMAT;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_RESTAURANT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.universal.EditOrderCommand;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.Name;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_CUSTOMER,
                PREFIX_RESTAURANT, PREFIX_FOOD, PREFIX_QUANTITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE), pe);
        }

        EditOrderCommand.EditOrderDescriptor editOrderDescriptor = new EditOrderCommand.EditOrderDescriptor();
        if (argMultimap.getValue(PREFIX_CUSTOMER).isPresent()) {
            editOrderDescriptor.setCustomer(ParserUtil.parseName(argMultimap.getValue(PREFIX_CUSTOMER).get()));
        }
        if (argMultimap.getValue(PREFIX_RESTAURANT).isPresent()) {
            editOrderDescriptor.setRestaurant(ParserUtil.parseName(argMultimap.getValue(PREFIX_RESTAURANT).get()));
        }
        parseFoodForEdit(argMultimap.getAllValues(PREFIX_FOOD), argMultimap.getAllValues(PREFIX_QUANTITY))
                .ifPresent(editOrderDescriptor::setFoods);

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
    private Optional<Map<Name, Integer>> parseFoodForEdit(Collection<String> foods,
                                                          Collection<String> quantities) throws ParseException {
        assert foods != null;

        if (foods.isEmpty()) {
            return Optional.empty();
        }

        if (foods.size() != quantities.size()) {
            throw new ParseException(MESSAGE_INVALID_FOOD_FORMAT);
        }

        ArrayList<Name> food = ParserUtil.parseNames(foods);
        ArrayList<Integer> amount = ParserUtil.parseQuantity(quantities);
        HashMap<Name, Integer> lst = new HashMap<>();
        for (int i = 0; i < food.size(); ++i) {
            lst.put(food.get(i), amount.get(i));
        }

        return Optional.of(lst);
    }
}
