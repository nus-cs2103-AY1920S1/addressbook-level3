package seedu.mark.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Bookmark}.
 */
class JsonAdaptedBookmark {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Bookmark's %s field is missing!";

    private final String name;
    private final String url;
    private final String remark;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String folder;

    /**
     * Constructs a {@code JsonAdaptedBookmark} with the given bookmark details.
     */
    @JsonCreator
    public JsonAdaptedBookmark(@JsonProperty("name") String name,
        @JsonProperty("url") String url, @JsonProperty("remark") String remark,
        @JsonProperty("folder") String folder, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.url = url;
        this.remark = remark;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.folder = folder;
    }

    /**
     * Converts a given {@code Bookmark} into this class for Jackson use.
     */
    public JsonAdaptedBookmark(Bookmark source) {
        name = source.getName().fullName;
        url = source.getUrl().value;
        remark = source.getRemark().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        folder = source.getFolder().folderName;
    }

    /**
     * Converts this Jackson-friendly adapted bookmark object into the model's {@code Bookmark} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted bookmark.
     */
    public Bookmark toModelType() throws IllegalValueException {
        final List<Tag> bookmarkTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            bookmarkTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (url == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Url.class.getSimpleName()));
        }
        if (!Url.isValidUrl(url)) {
            throw new IllegalValueException(Url.MESSAGE_CONSTRAINTS);
        }
        final Url modelUrl = new Url(url);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(bookmarkTags);

        if (folder == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Folder.class.getSimpleName()));
        }
        if (!Folder.isValidFolder(folder)) {
            throw new IllegalValueException(Folder.MESSAGE_CONSTRAINTS);
        }
        final Folder modelFolder = new Folder(folder);
        return new Bookmark(modelName, modelUrl, modelRemark, modelFolder, modelTags);
    }

}
