package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_IMPORT_ICS_DUPLICATE;
import static seedu.address.commons.core.Messages.MESSAGE_IMPORT_ICS_SUCCESS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.ics.IcsException;
import seedu.address.ics.IcsParser;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.model.exceptions.DuplicateElementException;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UserOutput;

//@@author marcusteh1238
/**
 * Represents a Command which imports Events stored in an Ics file into Horo.
 */
public class ImportIcsCommand extends Command {
    private final ModelManager model;
    private final String filepath;

    //@@author marcusteh1238
    public ImportIcsCommand(ImportIcsCommandBuilder builder) {
        this.model = builder.getModel();
        this.filepath = builder.getFilepath();
    }

    //@@author marcusteh1238
    public static CommandBuilder newBuilder(ModelManager model) {
        return new ImportIcsCommandBuilder(model).init();
    }

    //@@author marcusteh1238
    @Override
    public UserOutput execute() throws CommandException {
        EventSource[] newEvents;
        TaskSource[] newTasks;

        try {
            IcsParser icsParser = IcsParser.getParser(filepath);
            newEvents = icsParser.parseEvents();
            newTasks = icsParser.parseTasks();
        } catch (IcsException e) {
            throw new CommandException(e.getMessage());
        }

        // Get model events and tasks and append new items.
        List<EventSource> events = new ArrayList<>(model.getEvents());
        List<TaskSource> tasks = new ArrayList<>(model.getTasks());
        events.addAll(Arrays.asList(newEvents));
        tasks.addAll(Arrays.asList(newTasks));

        // Replace model
        try {
            model.setModelData(new ModelData(events, tasks));
        } catch (DuplicateElementException e) {
            throw new CommandException(MESSAGE_IMPORT_ICS_DUPLICATE);
        }

        return new UserOutput(String.format(MESSAGE_IMPORT_ICS_SUCCESS, filepath));
    }


}
