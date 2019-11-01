package seedu.scheduler.model;

import java.nio.file.Path;
import java.util.List;

import seedu.scheduler.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getIntervieweeListFilePath();

    Path getInterviewerListFilePath();

    String getStartTime();

    String getEndTime();

    int getDurationPerSlot();

    String getOrganisation();

    String getInterviewLocation();

    List<String> getCcEmails();

    String getEmailSubject();

    String getEmailAdditionalInformation();

}
