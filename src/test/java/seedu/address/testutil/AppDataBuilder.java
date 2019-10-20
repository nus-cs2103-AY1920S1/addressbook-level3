package seedu.address.testutil;

import seedu.address.model.AppData;
import seedu.address.model.note.Note;

/**
 * A utility class to help with building AppData objects.
 * Example usage: <br>
 *     {@code AppData ab = new AppDataBuilder().withNote("John", "Doe").build();}
 */
public class AppDataBuilder {

    private AppData appData;

    public AppDataBuilder() {
        appData = new AppData();
    }

    public AppDataBuilder(AppData appData) {
        this.appData = appData;
    }

    /**
     * Adds a new {@code Note} to the {@code AppData} that we are building.
     */
    public AppDataBuilder withNote(Note note) {
        appData.addNote(note);
        return this;
    }

    public AppData build() {
        return appData;
    }
}
