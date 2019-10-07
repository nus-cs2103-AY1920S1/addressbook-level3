package dream.fcard.logic;

import java.nio.file.Path;

import dream.fcard.commons.core.GuiSettings;
import dream.fcard.model.Model;
import dream.fcard.model.ReadOnlyAddressBook;
import dream.fcard.model.cards.Person;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    private final Model model;

    public LogicManager(Model model) {
        this.model = model;
    }


    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
