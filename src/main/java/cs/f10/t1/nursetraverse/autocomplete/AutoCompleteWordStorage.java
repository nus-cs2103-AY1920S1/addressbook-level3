package cs.f10.t1.nursetraverse.autocomplete;

import cs.f10.t1.nursetraverse.logic.commands.AddCommand;
import cs.f10.t1.nursetraverse.logic.commands.ClearCommand;
import cs.f10.t1.nursetraverse.logic.commands.DeleteCommand;
import cs.f10.t1.nursetraverse.logic.commands.EditCommand;
import cs.f10.t1.nursetraverse.logic.commands.ExportCommand;
import cs.f10.t1.nursetraverse.logic.commands.FindCommand;
import cs.f10.t1.nursetraverse.logic.commands.HelpCommand;
import cs.f10.t1.nursetraverse.logic.commands.ImportMergeCommand;
import cs.f10.t1.nursetraverse.logic.commands.ImportReplaceCommand;
import cs.f10.t1.nursetraverse.logic.commands.ListCommand;
import cs.f10.t1.nursetraverse.logic.commands.RedoCommand;
import cs.f10.t1.nursetraverse.logic.commands.UndoCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.AddAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.DeleteAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.EditAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.FindAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.BeginVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.CancelOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.FinishOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.UpdateOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.parser.CliSyntax;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Class that initialise and stores all list
 */
public class AutoCompleteWordStorage {

    public static final String VISIT_OBJECT_WORD = UserinputParserUtil
            .parseFirstSegment(BeginVisitCommand.COMMAND_WORD)
            .get(0);
    public static final String PATIENT_OBJECT_WORD = UserinputParserUtil
            .parseFirstSegment(AddCommand.COMMAND_WORD)
            .get(0);
    public static final String APP_OBJECT_WORD = UserinputParserUtil
            .parseFirstSegment(ExportCommand.COMMAND_WORD)
            .get(0);
    public static final String APPT_OBJECT_WORD = UserinputParserUtil
            .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
            .get(0);

    private ObservableList<AutoCompleteWord> oListAllObjectWord;
    private ObservableList<AutoCompleteWord> oListAllCommandWord;
    private ObservableList<AutoCompleteWord> oListAllPrefixWord;
    private FilteredList<Patient> patList;
    private FilteredList<Appointment> apptList;

    public AutoCompleteWordStorage(FilteredList<Patient> patList, FilteredList<Appointment> apptList) {
        this.oListAllCommandWord = initAllCommandWordList();
        this.oListAllPrefixWord = initAllPrefixWordList();
        this.oListAllObjectWord = initAllObjectWordList();
        this.patList = patList;
        this.apptList = apptList;
    }

    /**
     * Initialise command list
     */
    private ObservableList<AutoCompleteWord> initAllCommandWordList() {
        ObservableList<AutoCompleteWord> oListAllCommandWord = FXCollections.observableArrayList();

        // Visit commands
        oListAllCommandWord.add(new CommandWord(VISIT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(BeginVisitCommand.COMMAND_WORD)
                .get(1), true, false));
        oListAllCommandWord.add(new CommandWord(VISIT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(CancelOngoingVisitCommand.COMMAND_WORD)
                .get(1), false, false));
        oListAllCommandWord.add(new CommandWord(VISIT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(FinishOngoingVisitCommand.COMMAND_WORD)
                .get(1), false, false));
        oListAllCommandWord.add(new CommandWord(VISIT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD)
                .get(1), false, true));

        // App commands
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(UndoCommand.COMMAND_WORD)
                .get(1), false, false));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(RedoCommand.COMMAND_WORD)
                .get(1), false, false));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(ImportReplaceCommand.COMMAND_WORD)
                .get(1), false, true));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(ImportMergeCommand.COMMAND_WORD)
                .get(1), false, true));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(ExportCommand.COMMAND_WORD)
                .get(1), false, true));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(HelpCommand.COMMAND_WORD)
                .get(1), false, false));

        // Patient commands
        oListAllCommandWord.add(new CommandWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(ListCommand.COMMAND_WORD)
                .get(1), false, false));
        oListAllCommandWord.add(new CommandWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(FindCommand.COMMAND_WORD)
                .get(1), false, false));
        oListAllCommandWord.add(new CommandWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddCommand.COMMAND_WORD)
                .get(1), false, true));
        oListAllCommandWord.add(new CommandWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditCommand.COMMAND_WORD)
                .get(1), true, true));
        oListAllCommandWord.add(new CommandWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(DeleteCommand.COMMAND_WORD)
                .get(1), true, false));
        oListAllCommandWord.add(new CommandWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(ClearCommand.COMMAND_WORD)
                .get(1), false, false));

        // Appointment commands
        oListAllCommandWord.add(new CommandWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), false, true));
        oListAllCommandWord.add(new CommandWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(DeleteAppointmentCommand.COMMAND_WORD)
                .get(1), true, false));
        oListAllCommandWord.add(new CommandWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), false, true));
        oListAllCommandWord.add(new CommandWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(FindAppointmentCommand.COMMAND_WORD)
                .get(1), false, false));

        return oListAllCommandWord;
    }

    /**
     * Initialise prefix list
     */
    private ObservableList<AutoCompleteWord> initAllPrefixWordList() {
        ObservableList<AutoCompleteWord> oListAllPrefixWord = FXCollections.observableArrayList();

        // Visit prefixes
        oListAllPrefixWord.add(new PrefixWord(VISIT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_VISIT_TASK_INDEX_AND_DETAIL.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(VISIT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_TAG.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(VISIT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_VISIT_TASK_UNFINISH.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(VISIT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_VISIT_REMARKS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(VISIT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_VISIT_TASK_FINISH.getPrefix()));
        // App prefixes
        oListAllPrefixWord.add(new PrefixWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(ExportCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_INDEXES.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(ExportCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_NAME.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(ImportReplaceCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_NAME.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APP_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(ImportMergeCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_NAME.getPrefix()));
        // Pat prefixes
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_NAME.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_PHONE.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_EMAIL.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_ADDRESS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_TAG.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_PATIENT_VISIT_TODO.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_NAME.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_PHONE.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_EMAIL.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_ADDRESS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_TAG.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(PATIENT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_PATIENT_VISIT_TODO.getPrefix()));
        // Appt prefixes
        // Add
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_PATIENT_INDEX.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_YEARS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_MONTHS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_WEEKS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_DAYS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_HOURS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_MINUTES.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION.getPrefix()));
        // Edit
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_PATIENT_INDEX.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_YEARS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_MONTHS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_WEEKS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_DAYS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_HOURS.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_RECUR_MINUTES.getPrefix()));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD, UserinputParserUtil
                .parseFirstSegment(EditAppointmentCommand.COMMAND_WORD)
                .get(1), CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION.getPrefix()));

        return oListAllPrefixWord;
    }

    /**
     * Initialise object list
     */
    private ObservableList<AutoCompleteWord> initAllObjectWordList() {
        ObservableList<AutoCompleteWord> oListAllObjectWord = FXCollections.observableArrayList();

        oListAllObjectWord.add(new ObjectWord(PATIENT_OBJECT_WORD));
        oListAllObjectWord.add(new ObjectWord(APP_OBJECT_WORD));
        oListAllObjectWord.add(new ObjectWord(VISIT_OBJECT_WORD));
        oListAllObjectWord.add(new ObjectWord(APPT_OBJECT_WORD));

        return oListAllObjectWord;
    }

    public ObservableList<AutoCompleteWord> getOListAllCommandWord() {
        return FXCollections.observableArrayList(oListAllCommandWord);
    }

    public ObservableList<AutoCompleteWord> getOListAllPrefixWord() {
        return FXCollections.observableArrayList(oListAllPrefixWord);
    }

    public ObservableList<AutoCompleteWord> getOListAllObjectWord() {
        return FXCollections.observableArrayList(oListAllObjectWord);
    }

    /**
     * Generate index word list according to the number of specified object present
     *
     * @param objectWord object that determines the number of index that will be generated
     * @return list of index word
     */
    public ObservableList<AutoCompleteWord> generateOListAllIndexWord(ObjectWord objectWord) {
        FilteredList listToGenerateFrom;
        if (objectWord.getSuggestedWord().equals(APPT_OBJECT_WORD)) {
            listToGenerateFrom = apptList;
        } else {
            listToGenerateFrom = patList;
        }

        ObservableList<AutoCompleteWord> oListAllIndexWord = FXCollections.observableArrayList();
        for (int i = 0; i < listToGenerateFrom.size(); i++) {
            oListAllIndexWord.add(new IndexWord(Integer.toString(i + 1)));
        }
        return oListAllIndexWord;
    }
}
