package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import tagline.logic.commands.note.TagNoteCommand;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.tag.TagParserUtil;
import tagline.model.note.NoteId;
import tagline.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagNoteCommand Object
 */
public class TagNoteParser implements Parser<TagNoteCommand> {
    public static final String MESSAGE_NO_TAG = "At least one tag must be provided.";

    /**
     * Parses the given {@code String} of arguments in the context of the TagNoteCommand
     * and returns a TagNoteCommand object for execution.
     *
     * @throws tagline.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public TagNoteCommand parse(String args) throws ParseException {
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

        return new TagNoteCommand(noteId, tagList);
    }
}
