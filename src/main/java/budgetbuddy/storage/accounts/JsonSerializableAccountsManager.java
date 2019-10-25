package budgetbuddy.storage.accounts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.account.Account;

/**
 * An immutable AccountsManager that is serializable to JSON format.
 */
@JsonRootName(value = "accountsmanager")
public class JsonSerializableAccountsManager {

    private final List<JsonAdaptedAccount> accounts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAccountsManager} with the given accounts.
     */
    @JsonCreator
    public JsonSerializableAccountsManager(@JsonProperty("accounts") List<JsonAdaptedAccount> accounts) {
        this.accounts.addAll(accounts);
    }

    /**
     * Converts a given {@code AccountsManager} into this class for Jackson use.
     * @param source Future changes to the source will not affect the created {@code JsonSerializableAccountsManager}.
     */
    public JsonSerializableAccountsManager(AccountsManager source) {
        accounts.addAll(source.getAccounts().stream().map(JsonAdaptedAccount::new).collect(Collectors.toList()));
    }

    /**
     * Converts this accounts manager into the model's {@code AccountsManager} object.
     * @throws IllegalValueException If any data constraints are violated.
     */
    public AccountsManager toModelType() throws IllegalValueException {
        List<Account> accountList = new ArrayList<Account>();
        for (JsonAdaptedAccount jsonAdaptedAccount : accounts) {
            accountList.add(jsonAdaptedAccount.toModelType());
        }
        return new AccountsManager(accountList);
    }
}
