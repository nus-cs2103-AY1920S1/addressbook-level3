package seedu.address.testutil.diary;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DIARY_NAME;

import seedu.address.logic.commands.diary.AddDiaryCommand;
import seedu.address.logic.commands.diary.EditDiaryCommand.EditDiaryDescriptor;
import seedu.address.model.diary.components.Diary;

/**
 * A utility class for Person.
 */
public class DiaryUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Diary diary) {
        return AddDiaryCommand.COMMAND_WORD + " " + getDiaryDetails(diary);
    }

    /**
     * Returns the part of command string for the given {@code diary}'s details.
     */
    public static String getDiaryDetails(Diary diary) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DIARY_NAME + diary.getDiaryName().fullName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDiaryDescriptor}'s details.
     */
    public static String getEditDiaryDescriptorDetails(EditDiaryDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDiaryName().ifPresent(name -> sb.append(PREFIX_DIARY_NAME).append(name.fullName).append(" "));
        return sb.toString();
    }
}
