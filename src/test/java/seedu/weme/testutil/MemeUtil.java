package seedu.weme.testutil;

import static seedu.weme.commons.util.FileUtil.MESSAGE_READ_FILE_FAILURE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import seedu.weme.commons.util.StringUtil;
import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand.EditMemeDescriptor;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * A utility class for Meme.
 */
public class MemeUtil {

    /**
     * Returns an add command string for adding the {@code meme}.
     */
    public static String getAddCommand(Meme meme) {
        return MemeAddCommand.COMMAND_WORD + " " + getMemeDetails(meme);
    }

    /**
     * Returns the part of command string for the given {@code meme}'s details.
     */
    public static String getMemeDetails(Meme meme) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_FILEPATH + meme.getImagePath().toString() + " ");
        sb.append(PREFIX_DESCRIPTION + meme.getDescription().value + " ");
        meme.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMemeDescriptor}'s details.
     */
    public static String getEditMemeDescriptorDetails(EditMemeDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        //descriptor.getFilePath().ifPresent(path -> sb.append(PREFIX_FILEPATH).append(path.toString()).append(" "));
        descriptor.getDescription().ifPresent(description ->
                sb.append(PREFIX_DESCRIPTION).append(description.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the SHA-1 hash of a given file as a String.
     */
    public static String generateSha1Hash(Path file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] content = Files.readAllBytes(file);
            return StringUtil.byteArrayToHex(digest.digest(content));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format(MESSAGE_READ_FILE_FAILURE, file.toString()));
        }
    }

    /**
     * Returns true if {@code firstMeme} and {@code secondMeme} supplied have the same SHA-1 hash,
     * and therefore the same contents.
     */
    public static boolean isSameMemeImage(Meme firstMeme, Meme secondMeme) {
        return generateSha1Hash(firstMeme.getImagePath().getFilePath())
                .equals(generateSha1Hash(secondMeme.getImagePath().getFilePath()));
    }

}
