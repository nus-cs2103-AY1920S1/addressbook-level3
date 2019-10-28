package seedu.address.model;

import seedu.address.model.person.Email;

public class OwnerAccount {

    private final Email email;
    private final String password;

    public OwnerAccount(Email email, String password) {
        this.email = email;
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean equals(OwnerAccount ownerAccount) {
        return this.email.equals(ownerAccount.getEmail());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Email Address: ")
                .append(getEmail())
                .append(" Password: ")
                .append(getPassword());

        return builder.toString();
    }
}
