package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_CHICKEN;

import seedu.address.commons.core.Alias;
import seedu.address.commons.core.AliasMappings;
import seedu.address.commons.exceptions.RecursiveAliasException;
import seedu.address.logic.commands.alias.AddAliasCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.expense.ClearCommand;
import seedu.address.logic.commands.expense.DeleteExpenseCommand;
import seedu.address.logic.commands.expense.EditExpenseCommand;
import seedu.address.logic.commands.expense.FindExpenseCommand;
import seedu.address.logic.commands.expense.ListExpenseCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;

/**
 * A utility class representing {@code AliasMappings} objects to be used in tests.
 */
public class AliasTestUtil {

    public static final Alias ALIAS_LIST_SHORTCUT = new Alias("ls", ListExpenseCommand.COMMAND_WORD);
    public static final Alias ALIAS_ADD_WITH_ARGUMENTS = new Alias(
            "addchicken",
            FindExpenseCommand.COMMAND_WORD + DESCRIPTION_DESC_CHICKEN + PRICE_DESC_CHICKEN);
    public static final Alias ALIAS_FIND_SHORTCUT_INCOMPLETE = new Alias ("f", FindExpenseCommand.COMMAND_WORD);
    // for recursive
    public static final Alias ALIAS_A_TO_B = new Alias("a", "b");
    public static final Alias ALIAS_B_TO_C = new Alias("b", "c");
    public static final Alias ALIAS_C_TO_A = new Alias("c", "a");
    //An alias mappings object with valid aliases stored.
    public static final AliasMappings VALID_ALIAS_MAPPINGS;

    private static final String IGNORED_VALUE = "ignored value";
    // for alias is reserved
    public static final Alias ALIAS_NAME_ADD = new Alias(AddEventCommand.COMMAND_WORD, IGNORED_VALUE);
    public static final Alias ALIAS_NAME_ALIAS = new Alias(AddAliasCommand.COMMAND_WORD, IGNORED_VALUE);
    public static final Alias ALIAS_NAME_CLEAR = new Alias(ClearCommand.COMMAND_WORD, IGNORED_VALUE);
    public static final Alias ALIAS_NAME_DELETE = new Alias(DeleteExpenseCommand.COMMAND_WORD, IGNORED_VALUE);
    public static final Alias ALIAS_NAME_EDIT = new Alias(EditExpenseCommand.COMMAND_WORD, IGNORED_VALUE);
    public static final Alias ALIAS_NAME_EXIT = new Alias(ExitCommand.COMMAND_WORD, IGNORED_VALUE);
    public static final Alias ALIAS_NAME_FIND = new Alias(FindExpenseCommand.COMMAND_WORD, IGNORED_VALUE);
    public static final Alias ALIAS_NAME_HELP = new Alias(HelpCommand.COMMAND_WORD, IGNORED_VALUE);
    public static final Alias ALIAS_NAME_LIST = new Alias(ListExpenseCommand.COMMAND_WORD, IGNORED_VALUE);

    static {
        AliasMappings tempValidAliasMappings;
        try {
            tempValidAliasMappings = new AliasMappings()
                        .addAlias(ALIAS_LIST_SHORTCUT)
                        .addAlias(ALIAS_ADD_WITH_ARGUMENTS)
                        .addAlias(ALIAS_FIND_SHORTCUT_INCOMPLETE)
                        .addAlias(ALIAS_A_TO_B)
                        .addAlias(ALIAS_B_TO_C);
        } catch (RecursiveAliasException e) {
            tempValidAliasMappings = null;
            e.printStackTrace();
        }
        VALID_ALIAS_MAPPINGS = tempValidAliasMappings;
    }
}
