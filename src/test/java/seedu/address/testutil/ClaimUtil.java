package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddClaimCommand;
import seedu.address.model.claim.Claim;

/**
 * A utility class for Claim.
 */
public class ClaimUtil {

    /**
     * Returns an add command string for adding the {@code contact}.
     */
    public static String getAddCommand(Claim claim) {
        return AddClaimCommand.COMMAND_WORD + " " + getPersonDetails(claim);
    }

    /**
     * Returns the part of command string for the given {@code contact}'s details.
     */
    public static String getPersonDetails(Claim claim) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION + claim.getDescription().text + " ");
        sb.append(PREFIX_CASH + claim.getAmount().value + " ");
        sb.append(PREFIX_DATE + claim.getDate().text + " ");
        sb.append(PREFIX_NAME + claim.getName().fullName + " ");
        sb.append(PREFIX_DESCRIPTION + claim.getDescription().text + " ");

        claim.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
