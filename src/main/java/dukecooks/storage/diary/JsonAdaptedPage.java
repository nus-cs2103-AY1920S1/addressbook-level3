package dukecooks.storage.diary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.Image;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.Title;

/**
 * Jackson-friendly version of {@link Page}.
 */
class JsonAdaptedPage {

    private final String pageTitle;
    private final String pageDescription;
    private final String imageFilePath;

    /**
     * Constructs a {@code JsonAdaptedPage} with the given {@code pageTitle} and {@code pageDescription}.
     */
    @JsonCreator
    public JsonAdaptedPage(@JsonProperty("pageTitle") String pageTitle,
                           @JsonProperty("pageDescription") String pageDescription,
                           @JsonProperty("imageFilePath") String imageFilePath) {
        this.pageTitle = pageTitle;
        this.pageDescription = pageDescription;
        this.imageFilePath = imageFilePath;
    }

    /**
     * Converts a given {@code Page} into this class for Jackson use.
     */
    public JsonAdaptedPage(Page source) {
        pageTitle = source.getTitle().toString();
        pageDescription = source.getDescription().toString();
        imageFilePath = source.getImage().getFilePath();
    }

    /**
     * Converts this Jackson-friendly adapted Page object into the model's {@code Page} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Page.
     */
    public Page toModelType() throws IllegalValueException {
        if (!Title.isValidTitle(pageTitle)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }

        if (!PageDescription.isValidPageDescription(pageDescription)) {
            throw new IllegalValueException(PageDescription.MESSAGE_CONSTRAINTS);
        }

        if (!Image.isValidImage(imageFilePath)) {
            throw new IllegalValueException(Image.MESSAGE_CONSTRAINTS);
        }

        return new Page(new Title(pageTitle), new PageDescription(pageDescription), new Image(imageFilePath));
    }

}
