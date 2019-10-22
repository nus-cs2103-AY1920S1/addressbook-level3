package seedu.weme.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.template.Template;

/**
 * An Immutable MemeBook that is serializable to JSON format.
 */
@JsonRootName(value = "memebook")
class JsonSerializableMemeBook {

    public static final String MESSAGE_DUPLICATE_MEME = "Memes list contains duplicate meme(s).";
    public static final String MESSAGE_DUPLICATE_TEMPLATE = "Templates list contains duplicate meme(s).";

    private final List<JsonAdaptedMeme> memes = new ArrayList<>();
    private final List<JsonAdaptedTemplate> templates = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMemeBook} with the given memes and templates.
     */
    @JsonCreator
    public JsonSerializableMemeBook(@JsonProperty("memes") List<JsonAdaptedMeme> memes,
                                    @JsonProperty("templates") List<JsonAdaptedTemplate> templates) {
        this.memes.addAll(memes);
        this.templates.addAll(templates);
    }

    /**
     * Converts a given {@code ReadOnlyMemeBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMemeBook}.
     */
    public JsonSerializableMemeBook(ReadOnlyMemeBook source) {
        memes.addAll(source.getMemeList().stream().map(JsonAdaptedMeme::new).collect(Collectors.toList()));
        templates.addAll(source.getTemplateList().stream().map(JsonAdaptedTemplate::new).collect(Collectors.toList()));
    }

    /**
     * Converts this meme book into the model's {@code MemeBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MemeBook toModelType() throws IllegalValueException {
        MemeBook memeBook = new MemeBook();
        for (JsonAdaptedMeme jsonAdaptedMeme : memes) {
            Meme meme = jsonAdaptedMeme.toModelType();
            if (memeBook.hasMeme(meme)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEME);
            }
            memeBook.addMeme(meme);
        }
        for (JsonAdaptedTemplate jsonAdaptedTemplate : templates) {
            Template template = jsonAdaptedTemplate.toModelType();
            if (memeBook.hasTemplate(template)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TEMPLATE);
            }
            memeBook.addTemplate(template);
        }
        return memeBook;
    }

}
