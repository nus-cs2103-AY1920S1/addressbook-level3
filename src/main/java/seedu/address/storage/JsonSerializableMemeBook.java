package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.MemeBook;
import seedu.address.model.ReadOnlyMemeBook;
import seedu.address.model.meme.Meme;

/**
 * An Immutable MemeBook that is serializable to JSON format.
 */
@JsonRootName(value = "memebook")
class JsonSerializableMemeBook {

    public static final String MESSAGE_DUPLICATE_MEME = "Memes list contains duplicate meme(s).";

    private final List<JsonAdaptedMeme> memes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMemeBook} with the given memes.
     */
    @JsonCreator
    public JsonSerializableMemeBook(@JsonProperty("memes") List<JsonAdaptedMeme> memes) {
        this.memes.addAll(memes);
    }

    /**
     * Converts a given {@code ReadOnlyMemeBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMemeBook}.
     */
    public JsonSerializableMemeBook(ReadOnlyMemeBook source) {
        memes.addAll(source.getMemeList().stream().map(JsonAdaptedMeme::new).collect(Collectors.toList()));
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
        return memeBook;
    }

}
