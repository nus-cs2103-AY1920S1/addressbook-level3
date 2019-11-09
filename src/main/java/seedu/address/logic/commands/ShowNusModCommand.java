package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Gets details about a module from NusMods
 */
public class ShowNusModCommand extends Command {
    public static final String COMMAND_WORD = "showmod";
    public static final String NUSMODS_URL = "https://nusmods.com/modules/%s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_MODULE_CODE + "MODULE_CODE\n";

    public static final String MESSAGE_SUCCESS = "Showing module %s on browser!";
    public static final String MESSAGE_MODULE_NOT_FOUND = "Invalid module %s.";
    public static final String MESSAGE_INTERNAL_ERROR = "Internal error.";
    public static final String MESSAGE_BROWSER_FAILED = "Default browser not found or failed to launch!";

    private final ModuleCode moduleCode;

    public ShowNusModCommand(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AcadYear acadYear = model.getAcadYear();
        ModuleId moduleId = new ModuleId(acadYear, moduleCode);
        Module module;

        try {
            module = model.findModule(moduleId);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URL(String.format(NUSMODS_URL,
                        module.getModuleCode())).toURI());
            }
            return new CommandResultBuilder(
                    String.format(MESSAGE_SUCCESS, module.getModuleCode())).build();

        } catch (ModuleNotFoundException e) {
            return new CommandResultBuilder(
                    String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode)).build();

        } catch (MalformedURLException | URISyntaxException e) {
            assert false; // should not happen
            return new CommandResultBuilder(MESSAGE_INTERNAL_ERROR).build();
        } catch (IOException e) {
            // if the user default browser is not found, or it fails to be launched,
            // or the default handler application failed to be launched.
            return new CommandResultBuilder(MESSAGE_BROWSER_FAILED).build();
        }
    }

    @Override
    public boolean equals(Command command) {
        return command == this // short circuit if same object
                || (command instanceof ShowNusModCommand // instanceof handles nulls
                && moduleCode.equals(((ShowNusModCommand) command).moduleCode));
    }
}
