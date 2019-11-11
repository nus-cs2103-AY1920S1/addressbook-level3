package seedu.address.model.password.analyser;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.result.Result;

/**
 * Represents {@code Analyser} object that analyses every {code Password} in password book to produce an {@code Result}.
 * Interface for different analyser classes to implement analyse method.
 */
public interface Analyser {

    /**
     * Reviews specific aspect of every password in the password book to produce a list of {@code Result} objects
     * containing information of the analysis.
     *
     * @param passwordList the list of the {@code Password} objects in the password book.
     */
    List<Result> analyse(List<Password> passwordList);

    /**
     * Returns the header message for each type of analyser.
     */
    String getHeader();
}
