package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MATNO;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NUSID;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.Optional;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.AddStudentCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;

/**
 * Parses input arguments and creates a new AddStudentCommand object
 */
public class AddStudentCommandParser extends CommandParser<AddStudentCommand> {
    public static final ModCode TEMP_MOD_CODE = new ModCode("AB1231");
    public static final TutName TEMP_TUT_NAME = new TutName("temp tutorial");

    private static final boolean IS_FULL_FORMAT = true;

    private static final OptionalArgument[] optionalArgs = {
        OptionalArgument.OPTIONAL_MATNO,
        OptionalArgument.OPTIONAL_NUSID
    };
    /**
     * Parses the given {@code String} of arguments in the context of the AddStudentCommand
     * and returns an AddStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddStudentCommand parse(String args) throws ParseException {
        Optional<ArgumentMultimap> argumentMultimap;

        // Check for invalid parsing: combination of first & second format
        if (formatIsUnclear(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
        }

        // Parse first format
        argumentMultimap = validateIndexFormat(args);
        if (argumentMultimap.isPresent()) {
            return parseCommand(argumentMultimap.get(), !IS_FULL_FORMAT);
        }

        // Parse second format
        argumentMultimap = validateFullFormat(args);
        if (argumentMultimap.isPresent()) {
            return parseCommand(argumentMultimap.get(), IS_FULL_FORMAT);
        }

        // If both fail, format is invalid
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
    }

    /**
     * Initial check if argument contains a combination of both first and second format
     * @param args - user input
     */
    private boolean formatIsUnclear(String args) {
        ArgumentMultimap argMultimap =
                OptionalArgumentTokenizer.tokenize(args, optionalArgs,
                        PREFIX_INDEX, PREFIX_NAME, PREFIX_EMAIL, PREFIX_MODULE, PREFIX_TUTORIAL_NAME,
                        PREFIX_MATNO, PREFIX_NUSID);

        // Tutorial name, module name and index are all present
        if (arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_NAME, PREFIX_EMAIL,
                PREFIX_MODULE, PREFIX_TUTORIAL_NAME)) {
            return true;
        }

        // Tutorial name and index are present
        if (arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_NAME, PREFIX_EMAIL, PREFIX_TUTORIAL_NAME)) {
            return true;
        }

        // Module name and index are all present
        if (arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_NAME, PREFIX_EMAIL, PREFIX_MODULE)) {
            return true;
        }
        return false;
    }

    /**
     * Validates the given argument format based on the shortcut index method
     * @return Optional.empty() if the format is invalid. Else returns Optional of multimap containing
     * mapped prefixes
     */
    private Optional<ArgumentMultimap> validateIndexFormat(String args) {
        ArgumentMultimap argMultimap =
                OptionalArgumentTokenizer.tokenize(args, optionalArgs,
                        PREFIX_INDEX, PREFIX_NAME, PREFIX_EMAIL, PREFIX_MATNO, PREFIX_NUSID);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_NAME, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(argMultimap);
    }

    /**
     * Validates the given arguments format based on the full format
     * @return Optional.empty() if the format is invalid. Else returns Optional of multimap containing
     * mapped prefixes
     */
    private Optional<ArgumentMultimap> validateFullFormat(String args) {
        ArgumentMultimap argMultimap =
                OptionalArgumentTokenizer.tokenize(args, optionalArgs,
                        PREFIX_NAME, PREFIX_EMAIL, PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_MATNO, PREFIX_NUSID);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL, PREFIX_MODULE, PREFIX_TUTORIAL_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(argMultimap);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private AddStudentCommand parseCommand (ArgumentMultimap argMultimap, boolean isFullFormat)
            throws ParseException {

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        ModCode modCode;
        TutName tutName;
        Index tutIdx;

        // Retrieve optional arguments
        Optional<MatricNum> matricNum;
        if (argMultimap.getValue(PREFIX_MATNO).isPresent()) {
            matricNum = Optional.of(ParserUtil.parseMatricNum(argMultimap.getValue(PREFIX_MATNO).get()));
        } else {
            matricNum = Optional.empty();
        }
        Optional<NusnetId> nusnetId;
        if (argMultimap.getValue(PREFIX_NUSID).isPresent()) {
            nusnetId = Optional.of(ParserUtil.parseNusnetId(argMultimap.getValue(PREFIX_NUSID).get()));
        } else {
            nusnetId = Optional.empty();
        }

        Student student;
        // Retrieve tutorial and module code based on index
        if (isFullFormat) {
            modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());
            tutName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIAL_NAME).get());
            student = new Student(name, email, matricNum, nusnetId, modCode, tutName);
            return new AddStudentCommand(student);
        } else {
            argMultimap.getValue(PREFIX_INDEX);
            tutIdx = retrieveIndex(argMultimap);
            student = new Student(name, email, matricNum, nusnetId, TEMP_MOD_CODE, TEMP_TUT_NAME);
            return new AddStudentCommand(student, tutIdx);
        }
    }

    /**
     * Retrieves index entered by user and parses through the Indexing format
     */
    private Index retrieveIndex(ArgumentMultimap argumentMultimap) throws ParseException {
        try {
            return Index.fromOneBased(Integer.valueOf(argumentMultimap.getValue(PREFIX_INDEX).get()));
        } catch (RuntimeException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT,
                    AddStudentCommand.MESSAGE_USAGE));
        }
    }
}
