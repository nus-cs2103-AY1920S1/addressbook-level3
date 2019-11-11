package unrealunity.visit.logic.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import unrealunity.visit.commons.core.LogsCenter;
import unrealunity.visit.commons.util.FileUtil;
import unrealunity.visit.commons.util.ProfileUtil;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.person.Person;

/**
 * Generates a .txt file detailing the ProfileWindow details.
 */
public class GenerateProfileCommand extends Command {
    public static final String MESSAGE_GENERATE_PROFILE_SUCCESS = "Profile successfully generated!";
    private static final Logger logger = LogsCenter.getLogger(GenerateProfileCommand.class);

    private final String name;
    private final String tags;
    private final String phone;
    private final String email;
    private final String address;
    private final String visits;
    private final String time;


    public GenerateProfileCommand(Person person) {
        // Stringify Person particulars
        name = ProfileUtil.stringifyName(person.getName());
        tags = ProfileUtil.stringifyTags(person.getTags());
        phone = ProfileUtil.stringifyPhone(person.getPhone());
        email = ProfileUtil.stringifyEmail(person.getEmail());
        address = ProfileUtil.stringifyAddress(person.getAddress());

        // Stringify Person Visits
        visits = ProfileUtil.stringifyVisit(person.getVisitList());

        // Stringify Current Time
        time = ProfileUtil.stringifyDate(LocalDateTime.now());
    }

    /**
     * Generates and writes a .txt file containing the Profile of the patient.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        // File name formation : NAME_PHONE_dd-MM-yyyy HH-mm-ss.txt
        // E.g. Alex Yeoh_87438807_31-10-2019 11-41-02.txt
        String filename = name + "_" + phone + "_" + time + ".txt";

        // Get the path for file to be created
        Path path = Paths.get("generated_profiles", filename);

        // Create parent directory and file using FileUtil
        try {
            FileUtil.createIfMissing(path);
        } catch (IOException e) {
            throw new CommandException("Error creating new folder - Check permissions to directory -" + e.getMessage());
        }

        // Form profile .txt content
        String fileContent = ProfileUtil.buildProfileReport(name, tags, phone, email,
                address, visits, time);

        // Write to file content to file in path
        try {
            FileUtil.writeToProtectedFile(path, fileContent);
            logger.info("User .txt profile successfully exported to " + path);
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
                && visits.equals(e.visits)
                && time.equals(e.time);

    }

}
