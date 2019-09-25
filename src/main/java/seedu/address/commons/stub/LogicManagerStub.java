package seedu.address.commons.stub;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import jdk.jfr.Event;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;
import seedu.address.logic.Logic;
import java.time.Instant;


public class LogicManagerStub {
    public CommandResult execute(String commandText) {
        return new CommandResult("yeet");
    }

    public ReadOnlyAddressBook getAddressBook() {
        return null;
    }

    public ObservableList<EventSourceStub> getFilteredEventSourceList() {
        // model.getFilteredPersonList();
        EventSourceStub a = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        System.out.println("EEEEEE");
        ObservableList<EventSourceStub> list =  FXCollections.observableArrayList(a);
        return list;
    }

    public Path getAddressBookFilePath() {
        // model.getAddressBookFilePath();
        return null;
    }

    public GuiSettings getGuiSettings() {
        // model.getGuiSettings();
        return null;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        // model.setGuiSettings(guiSettings);
    }
}
