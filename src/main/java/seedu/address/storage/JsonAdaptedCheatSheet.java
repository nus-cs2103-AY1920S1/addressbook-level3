package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.cheatsheet.Title;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Todo.
 * Linking cheatsheet to JSON file.
 */

public class JsonAdaptedCheatSheet {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CheatSheet's %s field is missing!";

    private final String title;
    private final List<JsonAdaptedContent> contents = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCheatSheet} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedCheatSheet(@JsonProperty("title") String title, @JsonProperty("contents") List<JsonAdaptedContent> contents,
                                @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.title = title;
        if (contents != null) {
            this.contents.addAll(contents);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code CheatSheet} into this class for Jackson use.
     */
    public JsonAdaptedCheatSheet(CheatSheet source) {
        title = source.getTitle().value;
        contents.addAll(source.getContents().stream()
                .map(JsonAdaptedContent::new)
                .collect(Collectors.toList()));
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted cheatSheet object into the model's {@code CheatSheet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted cheatSheet.
     */
    public CheatSheet toModelType() throws IllegalValueException {
        final List<Tag> cheatSheetTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            cheatSheetTags.add(tag.toModelType());
        }

        final Set<Content> cheatSheetContents = new HashSet<>();
        for (JsonAdaptedContent content : contents) {
            cheatSheetContents.add(content.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        final Set<Content> modelContents = new HashSet<>(cheatSheetContents);
        final Set<Tag> modelTags = new HashSet<>(cheatSheetTags);
        return new CheatSheet(modelTitle, modelContents, modelTags);
    }

}
