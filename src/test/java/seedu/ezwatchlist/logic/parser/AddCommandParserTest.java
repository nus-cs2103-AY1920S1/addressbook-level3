package seedu.ezwatchlist.logic.parser;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    /*
    @Test
    public void parse_allFieldsPresent_success() {
        Show expectedShow = new ShowBuilder(FIGHTCLUB).withActors(VALID_ACTOR_AMY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + DESCRIPTION_DESC_BOB + WATCHED_DESC_AMY
                + DATE_DESC_BOB + RUNNING_TIME_DESC_BOB + ACTOR_DESC_BOB, new AddCommand(expectedShow));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + DESCRIPTION_DESC_BOB + WATCHED_DESC_AMY
                + DATE_DESC_BOB + RUNNING_TIME_DESC_BOB + ACTOR_DESC_BOB, new AddCommand(expectedShow));

        // multiple date of release - last date accepted
        assertParseSuccess(parser, NAME_DESC_BOB + DESCRIPTION_DESC_BOB + WATCHED_DESC_BOB + DATE_DESC_AMY
                + DATE_DESC_BOB + RUNNING_TIME_DESC_BOB + ACTOR_DESC_BOB, new AddCommand(expectedShow));

        // multiple description - last description accepted
        assertParseSuccess(parser, NAME_DESC_BOB + DESCRIPTION_DESC_AMY + DESCRIPTION_DESC_BOB
                + WATCHED_DESC_BOB + WATCHED_DESC_BOB
                + DATE_DESC_BOB + RUNNING_TIME_DESC_BOB + ACTOR_DESC_BOB, new AddCommand(expectedShow));

        // multiple tags - all accepted
        Show expectedShowMultipleTags = new ShowBuilder(FIGHTCLUB).withActors(VALID_ACTOR_AMY, VALID_ACTOR_BOB)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + DESCRIPTION_DESC_BOB + WATCHED_DESC_BOB + DATE_DESC_BOB
                + RUNNING_TIME_DESC_BOB + ACTOR_DESC_BOB, new AddCommand(expectedShowMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero actor
        Show expectedShow = new ShowBuilder(AVENGERSENDGAME).withActors().build();
        assertParseSuccess(parser, AVENGERSENDGAME.getName()
                + "movie" + AVENGERSENDGAME.getDateOfRelease().value +AVENGERSENDGAME.isWatched()
                + AVENGERSENDGAME.getRunningTime().toString() + AVENGERSENDGAME.getDescription()
                + "", new AddCommand(expectedShow));
    }
*/

}
