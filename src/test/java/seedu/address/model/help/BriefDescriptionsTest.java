package seedu.address.model.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddClaimCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddIncomeCommand;
import seedu.address.logic.commands.ApproveClaimCommand;
import seedu.address.logic.commands.BudgetCommand;
import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteIncomeCommand;
import seedu.address.logic.commands.DeleteShortcutCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditIncomeCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GotoCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RejectClaimCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortReverseCommand;

public class BriefDescriptionsTest extends BriefDescriptions {

    @Test
    public void get_allBriefDescriptions_success() {

        ArrayList<String> expectedStringList = new ArrayList<>(
                Arrays.asList(
                        HelpCommand.MESSAGE_USAGE,
                        AddContactCommand.MESSAGE_USAGE,
                        AddClaimCommand.MESSAGE_USAGE,
                        AddIncomeCommand.MESSAGE_USAGE,
                        DeleteContactCommand.MESSAGE_USAGE,
                        DeleteIncomeCommand.MESSAGE_USAGE,
                        EditContactCommand.MESSAGE_USAGE,
                        EditIncomeCommand.MESSAGE_USAGE,
                        ExitCommand.MESSAGE_USAGE,
                        CheckCommand.MESSAGE_USAGE,
                        GotoCommand.MESSAGE_USAGE,
                        RejectClaimCommand.MESSAGE_USAGE,
                        BudgetCommand.MESSAGE_USAGE,
                        ClearCommand.MESSAGE_USAGE,
                        ApproveClaimCommand.MESSAGE_USAGE,
                        SortCommand.MESSAGE_USAGE,
                        SortReverseCommand.MESSAGE_USAGE,
                        DeleteShortcutCommand.MESSAGE_USAGE
                        ));

        ArrayList<String> commandList =  SecondaryCommand.getCommandList();
        for (int i = 0; i < commandList.size(); i++) {
            assertEquals(expectedStringList.get(i), getDescription(new SecondaryCommand(commandList.get(i))));
        }
    }
}
