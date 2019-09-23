package seedu.address.storage.borrowerrecords;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.borrower.Borrower;

/**
 * Jackson-friendly version of {@link Borrower}.
 */
class JsonAdaptedBorrower {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Borrower's %s field is missing!";

    private final String userName;
//    private final String phone;
//    private final String email;
//    private final String address;
//    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBorrower} with the given book details.
     */
    @JsonCreator
    public JsonAdaptedBorrower(@JsonProperty("userName") String userName) {
        this.userName = userName;
//        this.phone = phone;
//        this.email = email;
//        this.address = address;
//        if (tagged != null) {
//            this.tagged.addAll(tagged);
//        }
    }

    /**
     * Converts a given {@code Borrower} into this class for Jackson use.
     */
    public JsonAdaptedBorrower(Borrower source) {

        // TODO
        userName = source.getUserName();
//        phone = source.getPhone().value;
//        email = source.getEmail().value;
//        address = source.getAddress().value;
//        tagged.addAll(source.getTags().stream()
//                .map(JsonAdaptedTag::new)
//                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted book object into the model's {@code Borrower} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Borrower toModelType() throws IllegalValueException {

        // TODO
        /*
        final List<Tag> bookTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            bookTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(bookTags);

         */
        return new Borrower();
    }

}
