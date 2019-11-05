package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_IMPORT_ICS_SUCCESS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import seedu.address.ics.IcsException;
import seedu.address.ics.IcsParser;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which imports Events stored in an Ics file into Horo.
 */
public class ImportIcsCommand extends Command {
    private final ModelManager model;
    private final String filepath;

    public ImportIcsCommand(ImportIcsCommandBuilder builder) {
        this.model = builder.getModel();
        this.filepath = builder.getFilepath();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new ImportIcsCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() {
        try {
            IcsParser icsParser = IcsParser.getParser(filepath);
            EventSource[] newEvents = icsParser.parseEvents();
            TaskSource[] newTasks = icsParser.parseTasks();

            // Get model events and tasks and append new items.
            List<EventSource> events = new ArrayList<>(model.getEvents());
            List<TaskSource> tasks = new ArrayList<>(model.getTasks());
            events.addAll(Arrays.asList(newEvents));
            tasks.addAll(Arrays.asList(newTasks));

            // Replace model
            model.setModelData(new ModelData(events, tasks));

            return new UserOutput(String.format(MESSAGE_IMPORT_ICS_SUCCESS, filepath));
        } catch (IcsException e) {
            return new UserOutput(e.getMessage());
        }
    }


}
