package cs.f10.t1.nursetraverse.testutil;

/**
 * Csv String versions of {@Link TypicalPatients}
 */
public class TypicalCsv {

    public static final String TYPICAL_CSV_STRING = "name,phone,email,address,tagged,visitTodos\n"
            + "\"Alice Pauline\",94351253,alice@example.com,\"123, Jurong West Ave 6, #08-111\",friends,\n"
            + "\"Benson Meier\",98765432,johnd@example.com,\"311, Clementi Ave 2, #02-25\",\"owesMoney\n"
            + "friends\",\n"
            + "\"Carl Kurz\",95352563,heinz@example.com,\"wall street\",,\n"
            + "\"Daniel Meier\",87652533,cornelia@example.com,\"10th street\",friends,\n"
            + "\"Elle Meyer\",9482224,werner@example.com,\"michegan ave\",,\n"
            + "\"Fiona Kunz\",9482427,lydia@example.com,\"little tokyo\",,\n"
            + "\"George Best\",9482442,anna@example.com,\"4th street\",,\"Check first aid kit\"\n";

    public static final String INVALID_CSV_STRING = "name,phone,email,address,tagged,visitTodos\n"
            + "\"Alice Pauline\",invalid phone,invalid email,\"123, Jurong West Ave 6, #08-111\",friends,\n"
            + "\"Benson Meier\",98765432,empty address ->,\"\",\"owesMoney\n"
            + "friends\",\n"
            + ",\"<- empty name\",heinz@example.com,\"wall street\",,\n";

    public static final String CSV_STRING_TO_MERGE = "name,phone,email,address,tagged,visitTodos\n"
            + "\"Hoon Meier\",8482424,stefan@example.com,\"little india\",,\n";
}
