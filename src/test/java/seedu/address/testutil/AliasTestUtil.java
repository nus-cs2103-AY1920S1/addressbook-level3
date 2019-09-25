package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_CHICKEN;

import seedu.address.commons.core.Alias;
import seedu.address.commons.core.AliasMappings;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;

/**
 * A utility class representing {@code AliasMappings} objects to be used in tests.
 */
public class AliasTestUtil {

    public static final Alias ALIAS_LIST_SHORTCUT = new Alias("ls", ListCommand.COMMAND_WORD);
    public static final Alias ALIAS_ADD_WITH_ARGUMENTS = new Alias(
            "addchicken",
            FindCommand.COMMAND_WORD + DESCRIPTION_DESC_CHICKEN + PRICE_DESC_CHICKEN);
    public static final Alias ALIAS_FIND_SHORTCUT_INCOMPLETE = new Alias ("f", FindCommand.COMMAND_WORD);

    // for recursive
    public static final Alias ALIAS_A_TO_B = new Alias("a", "b");
    public static final Alias ALIAS_B_TO_C = new Alias("b", "c");
    public static final Alias ALIAS_C_TO_A = new Alias("c", "a");
    public static final Alias ALIAS_TO_ALIAS = new Alias("d", AliasCommand.COMMAND_WORD);

    // for alias is reserved
    public static final Alias ALIAS_NAME_ADD = new Alias(AddCommand.COMMAND_WORD, "a");
    public static final Alias ALIAS_NAME_ALIAS = new Alias(AliasCommand.COMMAND_WORD, "a");
    public static final Alias ALIAS_NAME_CLEAR = new Alias(ClearCommand.COMMAND_WORD, "a");
    public static final Alias ALIAS_NAME_DELETE = new Alias(DeleteCommand.COMMAND_WORD, "a");
    public static final Alias ALIAS_NAME_EDIT = new Alias(EditCommand.COMMAND_WORD, "a");
    public static final Alias ALIAS_NAME_EXIT = new Alias(ExitCommand.COMMAND_WORD, "a");
    public static final Alias ALIAS_NAME_FIND = new Alias(FindCommand.COMMAND_WORD, "a");
    public static final Alias ALIAS_NAME_HELP = new Alias(HelpCommand.COMMAND_WORD, "a");
    public static final Alias ALIAS_NAME_LIST = new Alias(ListCommand.COMMAND_WORD, "a");


    public static AliasMappings VALID_ALIAS_MAPPINGS = new AliasMappings()
            .addAlias(ALIAS_LIST_SHORTCUT)
            .addAlias(ALIAS_ADD_WITH_ARGUMENTS)
            .addAlias(ALIAS_FIND_SHORTCUT_INCOMPLETE)
            .addAlias(ALIAS_A_TO_B)
            .addAlias(ALIAS_B_TO_C);
}
