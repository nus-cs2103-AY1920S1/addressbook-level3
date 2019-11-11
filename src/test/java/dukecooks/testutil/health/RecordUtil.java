package dukecooks.testutil.health;

import java.util.Set;

import dukecooks.logic.commands.health.AddRecordCommand;
import dukecooks.logic.commands.health.EditRecordCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Remark;

/**
 * A utility class for Record.
 */
public class RecordUtil {

    /**
     * Returns an add command string for adding the {@code record}.
     */
    public static String getAddRecordCommand(Record record) {
        return AddRecordCommand.COMMAND_WORD + " " + AddRecordCommand.VARIANT_WORD + " " + getRecordDetails(record);
    }

    /**
     * Returns the part of command string for the given {@code record}'s details.
     */
    public static String getRecordDetails(Record record) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_TYPE + record.getType().toString() + " ");
        record.getRemarks().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_REMARK + s.remarkName + " ")
        );
        sb.append(CliSyntax.PREFIX_VALUE + record.getValue().toString() + " ");
        sb.append(CliSyntax.PREFIX_DATETIME + record.getTimestamp().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditRecordDescriptor}'s details.
     */
    public static String getEditRecordDescriptorDetails(EditRecordCommand.EditRecordDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getType().ifPresent(type -> sb.append(CliSyntax.PREFIX_TYPE).append(type.toString()).append(" "));
        if (descriptor.getRemarksToAdd().isPresent()) {
            Set<Remark> remarks = descriptor.getRemarksToAdd().get();
            if (remarks.isEmpty()) {
                sb.append(CliSyntax.PREFIX_REMARK);
            } else {
                remarks.forEach(s -> sb.append(CliSyntax.PREFIX_REMARK).append(s.remarkName).append(" "));
            }
        }
        descriptor.getValue().ifPresent(value -> sb.append(CliSyntax.PREFIX_VALUE).append(value.getValue())
                .append(" "));
        descriptor.getTimestamp().ifPresent(timestamp -> sb.append(CliSyntax.PREFIX_DATETIME)
                .append(timestamp.toString()).append(" "));
        return sb.toString();
    }
}
