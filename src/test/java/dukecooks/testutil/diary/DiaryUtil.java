package dukecooks.testutil.diary;

import dukecooks.logic.commands.diary.AddDiaryCommand;
import dukecooks.logic.commands.diary.EditDiaryCommand;
import dukecooks.logic.commands.diary.EditPageCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.diary.components.Diary;

/**
 * A utility class for Person.
 */
public class DiaryUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Diary diary) {
        return AddDiaryCommand.COMMAND_WORD + " " + AddDiaryCommand.VARIANT_WORD + " " + getDiaryDetails(diary);
    }

    /**
     * Returns the part of command string for the given {@code diary}'s details.
     */
    public static String getDiaryDetails(Diary diary) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_DIARY_NAME + diary.getDiaryName().fullName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDiaryDescriptor}'s details.
     */
    public static String getEditDiaryDescriptorDetails(EditDiaryCommand.EditDiaryDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDiaryName().ifPresent(name -> sb.append(CliSyntax.PREFIX_DIARY_NAME).append(name.fullName)
                .append(" "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPageDescriptor}'s details.
     */
    public static String getEditPageDescriptorDetails(EditPageCommand.EditPageDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(CliSyntax.PREFIX_PAGE_TITLE).append(title.fullTitle)
                .append(" "));

        descriptor.getPageType().ifPresent(type -> sb.append(CliSyntax.PREFIX_PAGE_TYPE).append(type.pageType)
                .append(" "));

        descriptor.getPageDescription().ifPresent(description -> sb.append(CliSyntax.PREFIX_PAGE_DESCRIPTION)
                .append(description.fullPageDescription).append(" "));

        descriptor.getImage().ifPresent(image -> sb.append(CliSyntax.PREFIX_IMAGE).append(image.getDataPath())
                .append(" "));

        return sb.toString();
    }
}
