package seedu.address.model.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class WebLinksTest extends WebLinks {

    @Test
    public void get_allCommandWebLinks_success() {

        String base = "https://ay1920s1-cs2103t-w12-1.github.io/main/UserGuide.html";

        ArrayList<String> expectedStringList = new ArrayList<>(
                Arrays.asList(
                        "#viewing-help-code-help-code",
                        "#adding-a-person-code-add_contact-code",
                        "#adding-a-claim-code-add_claim-code",
                        "#adding-an-income-code-add_income-code",
                        "#deleting-a-contact-code-delete_contact-code",
                        "#deleting-an-income-code-delete_contact-code",
                        "#editing-a-contact-code-edit_contact-code",
                        "#editing-an-income-code-edit_income-code",
                        "#exiting-the-program-code-exit-code",
                        "#checking-a-person-or-claim-code-check-code",
                        "#changing-views-code-goto-code",
                        "#rejecting-a-claim-code-reject-code",
                        "#viewing-budget-code-budget-code",
                        "#clearing-all-data-code-clear-code",
                        "#approving-a-claim-code-approve-code",
                        "#sorting-the-contacts-claims-incomes-list-by-contact-s-name",
                        "#sorting-the-contacts-claims-incomes-list-in-reverse-order",
                        "#deleting-a-shortcut-code-delete_shortcut-code"
                ));

        ArrayList<String> commandList = SecondaryCommand.getCommandList();
        for (int i = 0; i < commandList.size(); i++) {
            assertEquals(base + expectedStringList.get(i), getLink(new SecondaryCommand(commandList.get(i))));
        }
    }


}
