package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_DUPLICATE;
import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.model.exceptions.DuplicateElementException;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class AddEventCommand extends Command {

    private final ModelManager model;
    private final EventSource event;

    private AddEventCommand(Builder builder) {
        String description = Objects.requireNonNull(builder.description);
        DateTime start = Objects.requireNonNull(builder.start);

        this.model = builder.model;
        this.event = EventSource.newBuilder(description, start)
            .setEnd(builder.end)
            .setRemind(builder.remind)
            .setTags(builder.tags)
            .build();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new Builder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        List<EventSource> events = new ArrayList<>(this.model.getEvents());
        events.add(this.event);

        // Replace model
        try {
            this.model.setModelData(new ModelData(events, this.model.getTasks()));
        } catch (DuplicateElementException e) {
            throw new CommandException(MESSAGE_ADD_EVENT_DUPLICATE);
        }

        return new UserOutput(String.format(MESSAGE_ADD_EVENT_SUCCESS, this.event.getDescription()));
    }

    /**
     * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
     */
    static class Builder extends CommandBuilder {

        public static final String OPTION_END_DATE_TIME = "--end";
        public static final String OPTION_REMIND_DATE_TIME = "--remind";
        public static final String OPTION_TAGS = "--tag";

        private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
        private static final String ARGUMENT_START_DATE_TIME = "START_DATE_TIME";
        private static final String ARGUMENT_END_DATE_TIME = "END_DATE_TIME";
        private static final String ARGUMENT_REMIND_DATE_TIME = "REMIND_DATE_TIME";
        private static final String ARGUMENT_TAGS = "TAGS";

        private final ModelManager model;

        private String description;
        private DateTime start;
        private DateTime end;
        private DateTime remind;
        private List<String> tags;

        private Builder(ModelManager model) {
            this.model = model;
        }

        @Override
        protected RequiredArgumentList defineCommandArguments() {
            return ArgumentList.required()
                .addArgument(StringArgument.newBuilder(ARGUMENT_DESCRIPTION, v -> this.description = v))
                .addArgument(DateTimeArgument.newBuilder(ARGUMENT_START_DATE_TIME, v -> this.start = v));
        }

        @Override
        protected Map<String, OptionalArgumentList> defineCommandOptions() {
            return Map.of(
                OPTION_END_DATE_TIME, ArgumentList.optional()
                    .addArgument(DateTimeArgument.newBuilder(ARGUMENT_END_DATE_TIME, v -> this.end = v)),
                OPTION_REMIND_DATE_TIME, ArgumentList.optional()
                    .addArgument(DateTimeArgument.newBuilder(ARGUMENT_REMIND_DATE_TIME, v -> this.remind = v)),
                OPTION_TAGS, ArgumentList.optional()
                    .setVariableArguments(StringVariableArguments.newBuilder(ARGUMENT_TAGS, v -> this.tags = v))
            );
        }

        @Override
        protected Command commandBuild() {
            return new AddEventCommand(this);
        }
    }
}
