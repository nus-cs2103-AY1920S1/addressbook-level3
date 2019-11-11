package seedu.address.logic.commands.suggestions;

import seedu.address.logic.parser.CliSyntax;

class LookAtGroupMemberCommandSuggesterTest extends SuggesterImplTester {

    protected LookAtGroupMemberCommandSuggesterTest() throws ReflectiveOperationException {
        super(LookAtGroupMemberCommandSuggester.class);
        disableAutoTestFor(CliSyntax.PREFIX_NAME);
    }
}
