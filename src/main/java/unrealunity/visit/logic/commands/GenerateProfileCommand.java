package unrealunity.visit.logic.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import unrealunity.visit.commons.core.LogsCenter;
import unrealunity.visit.commons.util.FileUtil;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.Model;
import unrealunity.visit.ui.HelpWindow;

/**
 * Saves new record to Visit List.
 */
public class GenerateProfileCommand extends Command {
    public static final String MESSAGE_GENERATE_PROFILE_SUCCESS = "Profile successfully generated!";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String line = "=======================================================================\n";
    private static final String header = line + "=========================== Patient Profile =============="
            + "=============\n" + line + "\n";

    private String name;
    private String tags;
    private String phone;
    private String email;
    private String address;
    private String visits;

    public GenerateProfileCommand(String name, String tags, String phone, String email,
                                  String address, String visits) {
        this.name = name;
        this.tags = tags;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.visits = visits;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Get date for profile generation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss"); // Windows Unix naming safe
        String now = LocalDateTime.now().format(formatter);

        // File name formation : NAME_PHONE_dd-MM-yyyy HH-mm-ss.txt
        // E.g. Alex Yeoh_87438807_31-10-2019 11-41-02.txt
        String filename = this.name + "_" + this.phone + "_" + now + ".txt";


        // Get the path for file to be created
        Path path = Paths.get("generated_profiles", filename);

        // Create parent directory and file using FileUtil
        try {
            FileUtil.createIfMissing(path);
        } catch (IOException e) {
            throw new CommandException("Error creating new file - Check permissions to folder: " + e.getMessage());
        }

        // Form profile .txt content
        StringBuilder output = new StringBuilder();

        output.append(header);

        output.append("Name:\n");
        output.append(this.name);
        output.append("\n\n");

        output.append("Tags:\n");
        output.append(this.tags);
        output.append("\n\n");

        output.append("Phone:\n");
        output.append(this.phone);
        output.append("\n\n");

        output.append("Email:\n");
        output.append(this.email);
        output.append("\n\n");

        output.append("Address:\n");
        output.append(this.address);
        output.append("\n\n");

        output.append("Visits:\n\n");
        output.append("==================================================================\n");
        output.append(this.visits);
        output.append("\n\n");

        output.append("[Profile generated at " + now + ".]");

        try {
            FileUtil.writeToFile(path, output.toString());
            logger.info("User .pdf profile successfully exported to " + path);
        } catch (IOException e) {
            throw new CommandException("Error writing to filepath: " + e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_GENERATE_PROFILE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GenerateProfileCommand)) {
            return false;
        }

        // state check
        GenerateProfileCommand e = (GenerateProfileCommand) other;
        return name.equals(e.name)
                && tags.equals(e.tags)
                && phone.equals(e.phone)
                && email.equals(e.email)
                && address.equals(e.address)
                && visits.equals(e.visits);

    }

}
