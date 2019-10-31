package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import tagline.logic.commands.note.TagNoteCommand;
import tagline.logic.commands.note.UntagNoteCommand;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.tag.TagParserUtil;
import tagline.model.note.NoteId;
import tagline.model.tag.Tag;

/**
 * Parses input arguments and creates a new UntagNoteCommand Object
 */
public class UntagNoteParser implements Parser<UntagNoteCommand> {
    public static final String MESSAGE_NO_TAG = "At least one tag must be provided.";

    /**
     * Parses the given {@code String} of arguments in the context of the UntagNoteCommand
     * and returns an UntagNoteCommand object for execution.
     *
     * @throws tagline.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public UntagNoteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        NoteId noteId;

        try {
            noteId = NoteParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagNoteCommand.MESSAGE_USAGE), pe);
        }

        List<String> tags = argMultimap.getAllValues(PREFIX_TAG);
        if (tags.isEmpty()) {
            throw new ParseException(MESSAGE_NO_TAG);
        }

        List<Tag> tagList = new ArrayList<>();
        for (String tagString : tags) {
            Tag tag = TagParserUtil.parseTag(tagString);
            tagList.add(tag);
        }

        return new UntagNoteCommand(noteId, tagList);
    }
}
