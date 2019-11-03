package seedu.address.diaryfeature.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.diaryfeature.model.Details;

/**
 * Jackson-friendly version of {@link seedu.address.diaryfeature.model.diaryEntry.DiaryEntry}.
 */

public class JsonAdaptedDetails {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Details are incomplete";

    private final String username;
    private final String password;


    /**
     * Constructs a {@code JsonAdaptedDiaryEntry} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedDetails(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;

    }

    public JsonAdaptedDetails(Optional<Details> source) {
        if (source.isEmpty()) {
            username = "null";
            password = "null";
        } else {
            username = source.get().getUserName();
            password = source.get().getPassword();
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the addressBookModel's {@code Person} object.
     *
     */
    public Optional<Details> toModelType() {
        if (username.equalsIgnoreCase("null") || password.equalsIgnoreCase("null")) {
            return Optional.empty();
        } else {
            Details curr = new Details(username, password);
            return Optional.of(curr);
        }
    }

}
