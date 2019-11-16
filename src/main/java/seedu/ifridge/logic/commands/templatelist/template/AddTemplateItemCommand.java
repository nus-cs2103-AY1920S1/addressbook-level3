package seedu.ifridge.logic.commands.templatelist.template;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;

import java.util.List;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.food.exceptions.InvalidUnitException;

/**
 * Adds a template item to the specified template.
 */
public class AddTemplateItemCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = "tlist template " + COMMAND_WORD
            + ": Adds a food item to the specified template.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: tlist template " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Apples "
            + PREFIX_AMOUNT + "300g";

    public static final String MESSAGE_SUCCESS = "New food item added into template: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food item already exists in the template";
    public static final String MESSAGE_INCORRECT_UNIT = "This food item's unit conflicts with another food entry "
            + "with the same name";

    private final TemplateItem toAdd;
    private final Index index;

    /**
     * Creates an AddTemplateItemCommand to add the specified {@code templateItem}
     */
    public AddTemplateItemCommand(Index index, TemplateItem templateItem) {
        requireNonNull(index);
        requireNonNull(templateItem);
        this.toAdd = templateItem;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();
        // Check that the template index is valid
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }
        if (Amount.isEmptyAmount(toAdd.getAmount())) {
            throw new CommandException(Amount.MESSAGE_ZERO_AMOUNT);
        }
        UniqueTemplateItems templateToEdit = lastShownList.get(index.getZeroBased());
        UniqueTemplateItems editedTemplate = new UniqueTemplateItems(templateToEdit.getName());
        editedTemplate.setTemplateItems(templateToEdit);

        if (templateToEdit.contains(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        UnitDictionary unitDictionary = model.getUnitDictionary();
        try {
            unitDictionary.checkUnitDictionary(toAdd, model);
        } catch (InvalidUnitException e) {
            throw new CommandException(MESSAGE_INCORRECT_UNIT);
        }

        editedTemplate.add(toAdd);

        model.setTemplate(templateToEdit, editedTemplate);
        model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        model.setShownTemplate(editedTemplate);
        model.updateFilteredTemplateToBeShown();
        model.commitTemplateList(templateToEdit, editedTemplate, index.getZeroBased());

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        commandResult.setTemplateListItemCommand();

        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTemplateItemCommand // instanceof handles nulls
                && index.equals(((AddTemplateItemCommand) other).index)
                && toAdd.equals(((AddTemplateItemCommand) other).toAdd));
    }
}
