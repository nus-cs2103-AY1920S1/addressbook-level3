package seedu.eatme.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.eatme.commons.exceptions.IllegalValueException;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Review;
import seedu.eatme.model.eatery.Tag;

/**
 * Jackson-friendly version of {@link Eatery}.
 */
class JsonAdaptedEatery {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Eatery's %s field is missing!";

    private final String name;
    private final String isOpen;
    private final String address;
    private final String category;
    private final List<JsonAdaptedReview> reviews = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEatery} with the given eatery details.
     */
    @JsonCreator
    public JsonAdaptedEatery(@JsonProperty("name") String name,
                             @JsonProperty("isOpen") String isOpen,
                             @JsonProperty("address") String address,
                             @JsonProperty("category") String category,
                             @JsonProperty("reviews") List<JsonAdaptedReview> reviews,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.isOpen = isOpen;
        this.address = address;
        this.category = category;
        if (reviews != null) {
            this.reviews.addAll(reviews);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Eatery} into this class for Jackson use.
     */
    public JsonAdaptedEatery(Eatery source) {
        name = source.getName().fullName;
        isOpen = String.valueOf(source.getIsOpen());
        address = source.getAddress().value;
        category = source.getCategory().getName();
        reviews.addAll(source.getReviews().stream()
                .map(JsonAdaptedReview::new)
                .collect(Collectors.toList()));
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted eatery object into the model's {@code Eatery} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted eatery.
     */
    public Eatery toModelType() throws IllegalValueException {
        final List<Review> eateryReviews = new ArrayList<>();
        for (JsonAdaptedReview review : reviews) {
            eateryReviews.add(review.toModelType());
        }

        final List<Tag> eateryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            eateryTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (isOpen == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isOpen"));
        }

        if (!isOpen.equals("true") && !isOpen.equals("false")) {
            throw new IllegalValueException("isOpen has to be either true or false, not blank or anything else.");
        }
        final boolean modelIsOpen = Boolean.parseBoolean(isOpen.toLowerCase());

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }
        if (!Category.isValidCategory(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        final Category modelCategory = new Category(category);

        final List<Review> modelReviews = new ArrayList<>(eateryReviews);
        final Set<Tag> modelTags = new HashSet<>(eateryTags);
        Eatery eatery = new Eatery(modelName, modelIsOpen, modelAddress, modelCategory, modelTags);
        eatery.setReviews(modelReviews);
        return eatery;
    }
}
