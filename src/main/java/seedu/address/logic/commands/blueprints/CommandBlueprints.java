package seedu.address.logic.commands.blueprints;

import java.util.HashMap;

public class CommandBlueprints {

    private static final String COMMAND_ADD_EVENT = "add_event";

    private static final HashMap<String, CommandBuilder> BLUEPRINTS;

    static {
        BLUEPRINTS = new HashMap<>();
        BLUEPRINTS.put(COMMAND_ADD_EVENT, new AddEventCommandBlueprint());
    }

    public static CommandBuilder get(String command) {
        return BLUEPRINTS.get(command);
    }
}
