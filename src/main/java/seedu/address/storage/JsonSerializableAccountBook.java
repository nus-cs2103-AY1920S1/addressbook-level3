package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AccountBook;
import seedu.address.model.account.Account;

/**
 * An Immutable TutorAid that is serializable to JSON format.
 */
@JsonRootName(value = "accountbook")
public class JsonSerializableAccountBook {

    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Account list contains duplicate account(s).";

    private final List<JsonAdaptedAccount> accounts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTutorAid} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAccountBook(@JsonProperty("accounts") List<JsonAdaptedAccount> acct) {
        this.accounts.addAll(acct);
    }

    /**
     * Converts a given {@code ReadOnlyTutorAid} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTutorAid}.
     */
    public JsonSerializableAccountBook(AccountBook source) {
        accounts.addAll(source.getList().stream().map(JsonAdaptedAccount::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code TutorAid} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AccountBook toModelType() throws IllegalValueException {
        AccountBook accountBook = new AccountBook();
        for (JsonAdaptedAccount jsonAdaptedAccount : accounts) {
            Account account = jsonAdaptedAccount.toModelType();
            if (accountBook.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            accountBook.addAccount(account);
        }
        return accountBook;
    }
}
