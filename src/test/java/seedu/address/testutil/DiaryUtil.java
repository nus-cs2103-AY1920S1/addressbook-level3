package seedu.address.testutil;

import seedu.address.logic.commands.AddDiaryCommand;
import seedu.address.logic.commands.EditDiaryCommand.EditDiaryDescriptor;
import seedu.address.model.diary.Diary;

/**
 * A utility class for Diary.
 */
public class DiaryUtil {

    /**
     * Returns an add command string for adding the {@code diary}.
     */
    public static String getAddCommand(Diary diary) {
        return AddDiaryCommand.COMMAND_WORD + " " + getDiaryDetails(diary);
    }

    /**
     * Returns the part of command string for the given {@code diary}'s details.
     */
    public static String getDiaryDetails(Diary diary) {
        StringBuilder sb = new StringBuilder();
        sb.append(diary.getName().fullName);
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDiaryDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditDiaryDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(name.fullName).append(" "));
        return sb.toString();
    }
}
