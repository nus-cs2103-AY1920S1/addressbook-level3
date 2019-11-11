package seedu.address.testutil;

import seedu.address.model.password.Password;

/**
 * A utility class containing a list of {@code Password} objects to be used in tests.
 */
public class TypicalPasswords {
    public static final Password PASSWORD1 = new PasswordBuilder().withUsername("RandomGuy1")
            .withPasswordValue("CFTGVYBH^&*(").withPasswordDescription("Dota fight club")
            .withWebsite("NIL")
            .withTags("game").build();

    public static final Password PASSWORD2 = new PasswordBuilder().withUsername("RandomGuy2")
            .withPasswordValue("CFTGVYBH^&*(").withPasswordDescription("LOL")
            .withWebsite("NIL")
            .withTags("game").build();
}
