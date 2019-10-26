package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CRITERIA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.model.policy.Policy;

/**
 * A utility class for Person.
 */
public class PolicyUtil {

    /**
     * Returns an add policy command string for adding the {@code policy}.
     */
    public static String getAddPolicyCommand(Policy policy) {
        return AddPolicyCommand.COMMAND_WORD + " " + getPolicyDetails(policy);
    }

    /**
     * Returns the part of command string for the given {@code policy}'s details.
     */
    public static String getPolicyDetails(Policy policy) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + policy.getName().policyName + " ");
        sb.append(PREFIX_DESCRIPTION + policy.getDescription().description + " ");
        sb.append(PREFIX_COVERAGE + policy.getCoverage().coverage + " ");
        sb.append(PREFIX_PRICE + policy.getPrice().price + " ");
        sb.append(PREFIX_START_AGE + policy.getStartAge().age + " ");
        sb.append(PREFIX_END_AGE + policy.getEndAge().age + " ");
        policy.getCriteria().stream().forEach(
            s -> sb.append(PREFIX_CRITERIA + s.tagName + " ")
        );
        policy.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPolicyDescriptor}'s details.
     */
    public static String getEditPolicyDescriptorDetails(EditPolicyCommand.EditPolicyDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.policyName).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION)
                .append(description.description).append(" "));
        descriptor.getCoverage().ifPresent(coverage -> sb.append(PREFIX_COVERAGE)
                .append(coverage.coverage).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_PRICE).append(price.price).append(" "));
        descriptor.getStartAge().ifPresent(startAge -> sb.append(PREFIX_START_AGE).append(startAge.age).append(" "));
        descriptor.getEndAge().ifPresent(endAge -> sb.append(PREFIX_END_AGE)
            .append(endAge.age).append(" "));
        return sb.toString();
    }
}
