package seedu.address.model.password.analyser.result;

import static java.util.Objects.requireNonNull;

import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;

/**
 * Represents a result produced by an {@code Analyser} for a particular password.
 * Contains information about the description of the result, as well as the specific password.
 * Additionally it should be able to return greater details about the analysis performed by the {@code Analyser}.
 */
public abstract class Result {
    protected Password password;
    protected ResultOutcome description;
    protected PasswordDescription passwordDesc;
    protected Username passwordUser;
    protected PasswordValue passwordValue;

    /**
     * Constructs a basic {@code Result}
     *
     * @param password the specific password to which the result holds information about.
     * @param description the evaluation description of the {@code Analyser}.
     */
    public Result(Password password, ResultOutcome description) {
        requireNonNull(password);
        requireNonNull(description);
        this.password = password;
        this.description = description;
        this.passwordDesc = password.getPasswordDescription();
        this.passwordUser = password.getUsername();
        this.passwordValue = password.getPasswordValue();
    }

    public String getPasswordDesc() {
        return passwordDesc.value;
    }

    public String getPasswordUser() {
        return passwordUser.value;
    }

    public String getPasswordValue() {
        return passwordValue.getEncryptedPasswordValue();
    }

    public void setDescription(ResultOutcome description) {
        this.description = description;
    }

    public ResultOutcome getDescription() {
        return description;
    }

    /**
     * Provides further in-depth information about the result produced by the {@code Analyser}.
     * @return the specific details of the result of analysis in string format.
     */
    public abstract String getGreaterDetail();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Result that = (Result) o;
        return password.equals(that.password)
                && description.equals(that.description);
    }

    /**
     * Provides summary information about the results produced by the {@code Analyser}.
     * @return brief detail of the result of analysis in string format.
     */
    @Override
    public String toString() {
        return String.format("%-20s %-5s %-20s %-5s %-20s %-5s %-20s", this.password.getPasswordDescription(),
                ":", this.password.getUsername(), ":",
                this.password.getPasswordValue() , ":" , getDescription()) + "\n";
    }

}
