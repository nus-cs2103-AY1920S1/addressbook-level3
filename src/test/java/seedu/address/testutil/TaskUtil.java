//package seedu.address.testutil;
//
//import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDAY;
//import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDESCRIPTION;
//import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTIME;
//import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTITLE;
//import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TAG;
//
//import java.util.Set;
//
//import seedu.address.logic.calendar.commands.AddCommand;
//import seedu.address.logic.calendar.commands.EditCommand;
//import seedu.address.model.calendar.tag.TaskTag;
//import seedu.address.model.calendar.task.Task;
//
///**
// * A utility class for task.
// */
//public class TaskUtil {
//
//    /**
//     * Returns an add command string for adding the {@code Task}.
//     */
//    public static String getAddCommand(Task task) {
//        return AddCommand.COMMAND_WORD + " " + getTaskDetails(task);
//    }
//
//    /**
//     * Returns the part of command string for the given {@code Task}'s details.
//     */
//    public static String getTaskDetails(Task task) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(PREFIX_TASKTITLE + task.getTaskTitle().toString());
//        sb.append(PREFIX_TASKDAY + task.getTaskDay().value + " ");
//        sb.append(PREFIX_TASKDESCRIPTION + task.getTaskDescription().value + " ");
//        sb.append(PREFIX_TASKTIME + task.getTaskTime().value + " ");
//        task.getTaskTags().stream().forEach(
//            s -> sb.append(PREFIX_TAG + s.tagName + " ")
//        );
//        return sb.toString();
//    }
//
//    /**
//     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
//     */
//    public static String getEditTaskDescriptorDetails(EditCommand.EditTaskDescriptor descriptor) {
//        StringBuilder sb = new StringBuilder();
//        descriptor.getTaskTitle().ifPresent(title -> sb.append(PREFIX_TASKTITLE).append(title.fullName).append(" "));
//        descriptor.getTaskDay().ifPresent(phone -> sb.append(PREFIX_TASKDAY).append(phone.value).append(" "));
//        descriptor.getTaskDescription().ifPresent(email ->
//            sb.append(PREFIX_TASKDESCRIPTION).append(email.value).append(" "));
//        descriptor.getTaskTime().ifPresent(address -> sb.append(PREFIX_TASKTIME).append(address.value).append(" "));
//        if (descriptor.getTaskTags().isPresent()) {
//            Set<TaskTag> tags = descriptor.get().get();
//            if (tags.isEmpty()) {
//                sb.append(PREFIX_TAG);
//            } else {
//                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
//            }
//        }
//        return sb.toString();
//    }
//}
