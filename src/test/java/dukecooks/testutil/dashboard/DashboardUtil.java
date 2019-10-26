package dukecooks.testutil.dashboard;

import dukecooks.logic.commands.dashboard.AddTaskCommand;
import dukecooks.logic.commands.dashboard.EditTaskCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.dashboard.components.Dashboard;

/**
 * A utility class for Dashboard.
 */
public class DashboardUtil {

    /**
     * Returns an add command string for adding the {@code dashboard}.
     */
    public static String getAddTaskCommand(Dashboard dashboard) {
        return AddTaskCommand.COMMAND_WORD + " " + AddTaskCommand.VARIANT_WORD + " " + getDashboardDetails(dashboard);
    }

    /**
     * Returns the part of command string for the given {@code dashboard}'s details.
     */
    public static String getDashboardDetails(Dashboard dashboard) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_TASKNAME + dashboard.getDashboardName().fullName + " ");
        sb.append(CliSyntax.PREFIX_TASKDATE + dashboard.getTaskDate().taskDate + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDashboardDescriptor}'s details.
     */
    public static String getEditDashboardDescriptorDetails(EditTaskCommand.EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDashboardName().ifPresent(name -> sb.append(CliSyntax.PREFIX_TASKNAME)
                .append(name.fullName).append(" "));

        descriptor.getTaskDate().ifPresent(date -> sb.append(CliSyntax.PREFIX_TASKDATE).append(date.taskDate));
        return sb.toString();
    }
}
