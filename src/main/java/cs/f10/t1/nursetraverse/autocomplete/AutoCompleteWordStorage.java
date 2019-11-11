package cs.f10.t1.nursetraverse.autocomplete;

import static cs.f10.t1.nursetraverse.autocomplete.UserinputParserUtil.parseFirstSegment;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.ADD_APPT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.ADD_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.APPT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.APP_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.BEGIN_VISIT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.CANCEL_VISIT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.CLEAR_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.DELETE_APPT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.DELETE_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.EDIT_APPT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.EDIT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.EXIT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.EXPORT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.FIND_APPT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.FIND_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.FINISH_VISIT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.HELP_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.IMPORT_MERGE_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.IMPORT_REPLACE_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.INDEX_A_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.INDEX_P_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.LIST_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PAT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_ADDRESS_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_APPT_DESC_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_APPT_END_DATE_AND_TIME_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_APPT_START_DATE_AND_TIME_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_EMAIL_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_FILENAME_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_INDEXES_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_NAME_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_PATIENT_INDEX_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_PATIENT_VISIT_TODO_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_PHONE_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_RECUR_DAYS_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_RECUR_HOURS_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_RECUR_MINUTES_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_RECUR_MONTHS_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_RECUR_WEEKS_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_RECUR_YEARS_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_TAG_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_VISIT_REMARKS_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_VISIT_TASK_FINISH_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_VISIT_TASK_INDEX_AND_DETAIL_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_VISIT_TASK_UNFINISH_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.REDO_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.UNDO_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.UPDATE_VISIT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.VISIT_DESCRIPTION;

import cs.f10.t1.nursetraverse.logic.commands.AddCommand;
import cs.f10.t1.nursetraverse.logic.commands.ClearCommand;
import cs.f10.t1.nursetraverse.logic.commands.DeleteCommand;
import cs.f10.t1.nursetraverse.logic.commands.EditCommand;
import cs.f10.t1.nursetraverse.logic.commands.ExitCommand;
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
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.appointment.AutoCompleteWord;
import cs.f10.t1.nursetraverse.model.autocomplete.CommandWord;
import cs.f10.t1.nursetraverse.model.autocomplete.IndexWord;
import cs.f10.t1.nursetraverse.model.autocomplete.ObjectWord;
import cs.f10.t1.nursetraverse.model.autocomplete.PrefixWord;
import cs.f10.t1.nursetraverse.model.patient.Patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Class that initialise and stores all list
 */
public class AutoCompleteWordStorage {

    public static final String VISIT_OBJECT_WORD = parseFirstSegment(BeginVisitCommand.COMMAND_WORD)
            .get(0);
    public static final String PAT_OBJECT_WORD = parseFirstSegment(AddCommand.COMMAND_WORD)
            .get(0);
    public static final String APP_OBJECT_WORD = parseFirstSegment(ExportCommand.COMMAND_WORD)
            .get(0);
    public static final String APPT_OBJECT_WORD = parseFirstSegment(AddAppointmentCommand.COMMAND_WORD)
            .get(0);

    private ObservableList<AutoCompleteWord> oListAllObjectWord;
    private ObservableList<AutoCompleteWord> oListAllCommandWord;
    private ObservableList<AutoCompleteWord> oListAllPrefixWord;
    private FilteredList<Patient> patList;
    private FilteredList<Appointment> apptList;
    private ObservableList<HistoryRecord> historyList;

    public AutoCompleteWordStorage(FilteredList<Patient> patList,
                                   FilteredList<Appointment> apptList,
                                   ObservableList<HistoryRecord> historyList) {
        this.oListAllCommandWord = initAllCommandWordList();
        this.oListAllPrefixWord = initAllPrefixWordList();
        this.oListAllObjectWord = initAllObjectWordList();
        this.patList = patList;
        this.apptList = apptList;
        this.historyList = historyList;
    }

    /**
     * Initialise command list
     */
    private ObservableList<AutoCompleteWord> initAllCommandWordList() {
        ObservableList<AutoCompleteWord> oListAllCommandWord = FXCollections.observableArrayList();

        // Visit commands
        oListAllCommandWord.add(new CommandWord(VISIT_OBJECT_WORD,
                parseFirstSegment(BeginVisitCommand.COMMAND_WORD).get(1), BEGIN_VISIT_DESCRIPTION,
                true, false));
        oListAllCommandWord.add(new CommandWord(VISIT_OBJECT_WORD,
                parseFirstSegment(CancelOngoingVisitCommand.COMMAND_WORD).get(1), CANCEL_VISIT_DESCRIPTION,
                false, false));
        oListAllCommandWord.add(new CommandWord(VISIT_OBJECT_WORD,
                parseFirstSegment(FinishOngoingVisitCommand.COMMAND_WORD).get(1), FINISH_VISIT_DESCRIPTION,
                false, false));
        oListAllCommandWord.add(new CommandWord(VISIT_OBJECT_WORD,
                parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD).get(1), UPDATE_VISIT_DESCRIPTION,
                false, true));

        // App commands
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD,
                parseFirstSegment(UndoCommand.COMMAND_WORD).get(1), UNDO_DESCRIPTION,
                true, false));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD,
                parseFirstSegment(RedoCommand.COMMAND_WORD).get(1), REDO_DESCRIPTION,
                false, false));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD,
                parseFirstSegment(ImportReplaceCommand.COMMAND_WORD).get(1), IMPORT_REPLACE_DESCRIPTION,
                false, true));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD,
                parseFirstSegment(ImportMergeCommand.COMMAND_WORD).get(1), IMPORT_MERGE_DESCRIPTION,
                false, true));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD,
                parseFirstSegment(ExportCommand.COMMAND_WORD).get(1), EXPORT_DESCRIPTION,
                false, true));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD,
                parseFirstSegment(HelpCommand.COMMAND_WORD).get(1), HELP_DESCRIPTION,
                false, false));
        oListAllCommandWord.add(new CommandWord(APP_OBJECT_WORD,
                parseFirstSegment(ExitCommand.COMMAND_WORD).get(1), EXIT_DESCRIPTION,
                false, false));

        // Patient commands
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(ListCommand.COMMAND_WORD).get(1), LIST_DESCRIPTION,
                false, false));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(FindCommand.COMMAND_WORD).get(1), FIND_DESCRIPTION,
                false, false));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1), ADD_DESCRIPTION,
                false, true));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1), EDIT_DESCRIPTION,
                true, true));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(DeleteCommand.COMMAND_WORD).get(1), DELETE_DESCRIPTION,
                true, false));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(ClearCommand.COMMAND_WORD).get(1), CLEAR_DESCRIPTION,
                false, false));

        // Appointment commands
        oListAllCommandWord.add(new CommandWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1), ADD_APPT_DESCRIPTION,
                false, true));
        oListAllCommandWord.add(new CommandWord(APPT_OBJECT_WORD,
                parseFirstSegment(DeleteAppointmentCommand.COMMAND_WORD).get(1), DELETE_APPT_DESCRIPTION,
                true, false));
        oListAllCommandWord.add(new CommandWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1), EDIT_APPT_DESCRIPTION,
                false, true));
        oListAllCommandWord.add(new CommandWord(APPT_OBJECT_WORD,
                parseFirstSegment(FindAppointmentCommand.COMMAND_WORD).get(1), FIND_APPT_DESCRIPTION,
                false, false));

        return oListAllCommandWord;
    }

    /**
     * Initialise prefix list
     */
    private ObservableList<AutoCompleteWord> initAllPrefixWordList() {
        ObservableList<AutoCompleteWord> oListAllPrefixWord = FXCollections.observableArrayList();

        // Visit prefixes
        oListAllPrefixWord.add(new PrefixWord(VISIT_OBJECT_WORD,
                parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_VISIT_TASK_INDEX_AND_DETAIL.getPrefix(),
                PREFIX_VISIT_TASK_INDEX_AND_DETAIL_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(VISIT_OBJECT_WORD,
                parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_VISIT_TASK_UNFINISH.getPrefix(), PREFIX_VISIT_TASK_UNFINISH_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(VISIT_OBJECT_WORD,
                parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_VISIT_REMARKS.getPrefix(), PREFIX_VISIT_REMARKS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(VISIT_OBJECT_WORD,
                parseFirstSegment(UpdateOngoingVisitCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_VISIT_TASK_FINISH.getPrefix(), PREFIX_VISIT_TASK_FINISH_DESCRIPTION));
        // App prefixes
        oListAllPrefixWord.add(new PrefixWord(APP_OBJECT_WORD,
                parseFirstSegment(ExportCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_INDEXES.getPrefix(), PREFIX_INDEXES_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APP_OBJECT_WORD,
                parseFirstSegment(ExportCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_FILENAME.getPrefix(), PREFIX_FILENAME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APP_OBJECT_WORD,
                parseFirstSegment(ImportReplaceCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_FILENAME.getPrefix(), PREFIX_FILENAME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APP_OBJECT_WORD,
                parseFirstSegment(ImportMergeCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_FILENAME.getPrefix(), PREFIX_FILENAME_DESCRIPTION));
        // Pat prefixes
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_NAME.getPrefix(), PREFIX_NAME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PHONE.getPrefix(), PREFIX_PHONE_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_EMAIL.getPrefix(), PREFIX_EMAIL_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_ADDRESS.getPrefix(), PREFIX_ADDRESS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_TAG.getPrefix(), PREFIX_TAG_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PATIENT_VISIT_TODO.getPrefix(), PREFIX_PATIENT_VISIT_TODO_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_NAME.getPrefix(), PREFIX_NAME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PHONE.getPrefix(), PREFIX_PHONE_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_EMAIL.getPrefix(), PREFIX_EMAIL_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_ADDRESS.getPrefix(), PREFIX_ADDRESS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_TAG.getPrefix(), PREFIX_TAG_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PATIENT_VISIT_TODO.getPrefix(), PREFIX_PATIENT_VISIT_TODO_DESCRIPTION));
        // Appt prefixes
        // Add
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PATIENT_INDEX.getPrefix(), PREFIX_PATIENT_INDEX_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME.getPrefix(),
                PREFIX_APPT_START_DATE_AND_TIME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME.getPrefix(), PREFIX_APPT_END_DATE_AND_TIME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_YEARS.getPrefix(), PREFIX_RECUR_YEARS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_MONTHS.getPrefix(), PREFIX_RECUR_MONTHS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_WEEKS.getPrefix(), PREFIX_RECUR_WEEKS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_DAYS.getPrefix(), PREFIX_RECUR_DAYS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_HOURS.getPrefix(), PREFIX_RECUR_HOURS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_MINUTES.getPrefix(), PREFIX_RECUR_MINUTES_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(AddAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION.getPrefix(), PREFIX_APPT_DESC_DESCRIPTION));
        // Edit
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PATIENT_INDEX.getPrefix(), PREFIX_PATIENT_INDEX_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME.getPrefix(),
                PREFIX_APPT_START_DATE_AND_TIME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME.getPrefix(), PREFIX_APPT_END_DATE_AND_TIME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_YEARS.getPrefix(), PREFIX_RECUR_YEARS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_MONTHS.getPrefix(), PREFIX_RECUR_MONTHS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_WEEKS.getPrefix(), PREFIX_RECUR_WEEKS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_DAYS.getPrefix(), PREFIX_RECUR_DAYS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_HOURS.getPrefix(), PREFIX_RECUR_HOURS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_RECUR_MINUTES.getPrefix(), PREFIX_RECUR_MINUTES_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(APPT_OBJECT_WORD,
                parseFirstSegment(EditAppointmentCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION.getPrefix(), PREFIX_APPT_DESC_DESCRIPTION));

        return oListAllPrefixWord;
    }

    /**
     * Initialise object list
     */
    private ObservableList<AutoCompleteWord> initAllObjectWordList() {
        ObservableList<AutoCompleteWord> oListAllObjectWord = FXCollections.observableArrayList();

        oListAllObjectWord.add(new ObjectWord(PAT_OBJECT_WORD, PAT_DESCRIPTION));
        oListAllObjectWord.add(new ObjectWord(VISIT_OBJECT_WORD, VISIT_DESCRIPTION));
        oListAllObjectWord.add(new ObjectWord(APPT_OBJECT_WORD, APPT_DESCRIPTION));
        oListAllObjectWord.add(new ObjectWord(APP_OBJECT_WORD, APP_DESCRIPTION));

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
        ObservableList<AutoCompleteWord> oListAllIndexWord = FXCollections.observableArrayList();
        if (objectWord.getSuggestedWord().equals(APPT_OBJECT_WORD)) {
            for (int i = 0; i < apptList.size(); i++) {
                oListAllIndexWord.add(new IndexWord(Integer.toString(i + 1), INDEX_A_DESCRIPTION));
            }
        } else if (objectWord.getSuggestedWord().equals(PAT_OBJECT_WORD)) {
            for (int i = 0; i < patList.size(); i++) {
                oListAllIndexWord.add(new IndexWord(Integer.toString(i + 1), INDEX_P_DESCRIPTION));
            }
        } else {
            for (int i = 0; i < historyList.size(); i++) {
                oListAllIndexWord.add(new IndexWord(Integer.toString(i + 1), INDEX_P_DESCRIPTION));
            }
        }
        return oListAllIndexWord;
    }
}
