package seedu.address.model.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ApiLinksTest extends ApiLinks {

    @Test
    public void get_allCommandApiLinks_success() {

        String base = "/javadocs/seedu/address/logic/commands/";

        ArrayList<String> expectedStringList = new ArrayList<>(
                Arrays.asList(
                        "HelpCommand.html",
                        "AddContactCommand.html",
                        "AddClaimCommand.html",
                        "AddIncomeCommand.html",
                        "DeleteContactCommand.html",
                        "DeleteIncomeCommand.html",
                        "EditContactCommand.html",
                        "EditIncomeCommand.html",
                        "ExitCommand.html",
                        "CheckCommand.html",
                        "GotoCommand.html",
                        "RejectClaimCommand.html",
                        "BudgetCommand.html",
                        "ClearCommand.html",
                        "ApproveClaimCommand.html",
                        "SortCommand.html",
                        "SortReverseCommand.html",
                        "DeleteShortcutCommand.html"
                ));

        ArrayList<String> commandList = SecondaryCommand.getCommandList();
        for (int i = 0; i < commandList.size(); i++) {
            assertEquals(base + expectedStringList.get(i), getLink(new SecondaryCommand(commandList.get(i))));
        }
    }
}
