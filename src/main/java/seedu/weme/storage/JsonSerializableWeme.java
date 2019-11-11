package seedu.weme.storage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.Weme;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.template.Template;

/**
 * An Immutable Weme that is serializable to JSON format.
 */
@JsonRootName(value = "weme")
class JsonSerializableWeme {

    public static final String MESSAGE_DUPLICATE_MEME = "Memes list contains duplicate meme(s).";
    public static final String MESSAGE_DUPLICATE_TEMPLATE = "Templates list contains duplicate meme(s).";

    private final List<JsonAdaptedMeme> memes = new ArrayList<>();
    private final List<JsonAdaptedTemplate> templates = new ArrayList<>();
    private final JsonSerializableStats stats;
    private final JsonSerializableRecords records;

    /**
     * Constructs a {@code JsonSerializableWeme} with the given memes and templates.
     */
    @JsonCreator
    public JsonSerializableWeme(@JsonProperty("memes") List<JsonAdaptedMeme> memes,
                                @JsonProperty("templates") List<JsonAdaptedTemplate> templates,
                                @JsonProperty("stats") JsonSerializableStats stats,
                                @JsonProperty("records") JsonSerializableRecords records) {
        this.memes.addAll(memes);
        this.templates.addAll(templates);
        this.stats = stats;
        this.records = records;
    }

    /**
     * Converts a given {@code ReadOnlyWeme} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWeme}.
     * @param folderPath the path to the data folder of Weme.
     */
    public JsonSerializableWeme(ReadOnlyWeme source, Path folderPath) {
        Path memeFolderPath = folderPath.resolve(UserPrefs.MEME_IMAGE_DIRECTORY_NAME);
        Path templateFolderPath = folderPath.resolve(UserPrefs.TEMPLATE_IMAGE_DIRECTORY_NAME);
        memes.addAll(source.getMemeList().stream().map(meme -> new JsonAdaptedMeme(meme, memeFolderPath))
                .collect(Collectors.toList()));
        templates.addAll(source.getTemplateList().stream()
                .map(template -> new JsonAdaptedTemplate(template, templateFolderPath))
                .collect(Collectors.toList()));
        stats = new JsonSerializableStats(source.getStats(), memeFolderPath);
        records = new JsonSerializableRecords(source.getRecords());
    }

    public JsonSerializableStats getStats() {
        return stats;
    }

    /**
     * Converts this Weme into the model's {@code Weme} object.
     *
     * @param folderPath the path to the data folder of Weme.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Weme toModelType(Path folderPath) throws IllegalValueException {
        Weme weme = new Weme();
        Path memeFolderPath = folderPath.resolve(UserPrefs.MEME_IMAGE_DIRECTORY_NAME);
        Path templateFolderPath = folderPath.resolve(UserPrefs.TEMPLATE_IMAGE_DIRECTORY_NAME);
        for (JsonAdaptedMeme jsonAdaptedMeme : memes) {
            Meme meme = jsonAdaptedMeme.toModelType(memeFolderPath);
            if (weme.hasMeme(meme)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEME);
            }
            weme.addMeme(meme);
        }
        for (JsonAdaptedTemplate jsonAdaptedTemplate : templates) {
            Template template = jsonAdaptedTemplate.toModelType(templateFolderPath);
            if (weme.hasTemplate(template)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TEMPLATE);
            }
            weme.addTemplate(template);
        }
        weme.setStats(stats.toModelType(memeFolderPath));
        weme.setRecords(records.toModelType());
        return weme;
    }

}
