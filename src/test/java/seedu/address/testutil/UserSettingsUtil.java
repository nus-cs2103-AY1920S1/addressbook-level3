package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FINE_INCREMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOAN_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_RENEWS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEW_PERIOD;

import seedu.address.logic.commands.SetCommand;

/**
 * A utility class for UserSettings.
 */
public class UserSettingsUtil {
    /**
     * Returns the part of command string for the given {@code SetUserSettingsDescriptor}'s details.
     */
    public static String getSetUserSettingsDescriptorDetails(SetCommand.SetUserSettingsDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();

        descriptor.getLoanPeriod().ifPresent(loanPeriod -> sb.append(PREFIX_LOAN_PERIOD)
                .append(loanPeriod.getLoanPeriod()).append(" "));

        descriptor.getRenewPeriod().ifPresent(renewPeriod -> sb.append(PREFIX_RENEW_PERIOD)
                .append(renewPeriod.getRenewPeriod()).append(" "));

        descriptor.getFineIncrement().ifPresent(fineIncrement -> sb.append(PREFIX_FINE_INCREMENT)
                .append(fineIncrement.getFineIncrement()).append(" "));

        descriptor.getMaxRenews().ifPresent(maxRenews -> sb.append(PREFIX_MAX_RENEWS)
                .append(maxRenews.getMaxRenews()).append(" "));

        return sb.toString();
    }
}
