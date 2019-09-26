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
        EventSourceStub b = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub c = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub d = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub e = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub f = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub g = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub h = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub i = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub j = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));

        EventSourceStub k = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub l = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub m = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub n = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub o = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub p = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub q = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub r = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub s = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));
        EventSourceStub t = new EventSourceStub(new NameStub("This is an event 1"), new TimeStub(Instant.now()));

        ObservableList<EventSourceStub> list =  FXCollections.observableArrayList(a, b, c, d, e, f, g, h, i, j,
                k,l,m,n,o,p,q,r,s,t);
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
