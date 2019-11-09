package seedu.address.ui;

import static seedu.address.ui.SuggestionLabels.ADD_MENTOR;
import static seedu.address.ui.SuggestionLabels.ADD_MENTOR_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.ADD_PARTICIPANT;
import static seedu.address.ui.SuggestionLabels.ADD_PARTICIPANT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.ADD_TEAM;
import static seedu.address.ui.SuggestionLabels.ADD_TEAM_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.ASSIGN_MENTOR;
import static seedu.address.ui.SuggestionLabels.ASSIGN_MENTOR_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.ASSIGN_PARTICIPANT;
import static seedu.address.ui.SuggestionLabels.ASSIGN_PARTICIPANT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.DELETE_MENTOR;
import static seedu.address.ui.SuggestionLabels.DELETE_MENTOR_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.DELETE_PARTICIPANT;
import static seedu.address.ui.SuggestionLabels.DELETE_PARTICIPANT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.DELETE_TEAM;
import static seedu.address.ui.SuggestionLabels.DELETE_TEAM_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.EDIT_MENTOR;
import static seedu.address.ui.SuggestionLabels.EDIT_MENTOR_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.EDIT_PARTICIPANT;
import static seedu.address.ui.SuggestionLabels.EDIT_PARTICIPANT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.EDIT_TEAM;
import static seedu.address.ui.SuggestionLabels.EDIT_TEAM_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.EXIT;
import static seedu.address.ui.SuggestionLabels.EXIT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.EXPORT;
import static seedu.address.ui.SuggestionLabels.EXPORT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.FIND_MENTOR;
import static seedu.address.ui.SuggestionLabels.FIND_MENTOR_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.FIND_PARTICIPANT;
import static seedu.address.ui.SuggestionLabels.FIND_PARTICIPANT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.FIND_TEAM;
import static seedu.address.ui.SuggestionLabels.FIND_TEAM_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.GET_TOP;
import static seedu.address.ui.SuggestionLabels.GET_TOP_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.HELP;
import static seedu.address.ui.SuggestionLabels.HELP_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.HISTORY;
import static seedu.address.ui.SuggestionLabels.HISTORY_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.IMPORT;
import static seedu.address.ui.SuggestionLabels.IMPORT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.LEADERBOARD;
import static seedu.address.ui.SuggestionLabels.LEADERBOARD_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.LIST_MENTOR;
import static seedu.address.ui.SuggestionLabels.LIST_MENTOR_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.LIST_PARTICIPANT;
import static seedu.address.ui.SuggestionLabels.LIST_PARTICIPANT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.LIST_TEAM;
import static seedu.address.ui.SuggestionLabels.LIST_TEAM_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.REDO;
import static seedu.address.ui.SuggestionLabels.REDO_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.REMOVE_MENTOR;
import static seedu.address.ui.SuggestionLabels.REMOVE_MENTOR_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.REMOVE_PARTICIPANT;
import static seedu.address.ui.SuggestionLabels.REMOVE_PARTICIPANT_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.SCORE_ADD;
import static seedu.address.ui.SuggestionLabels.SCORE_ADD_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.SCORE_SET;
import static seedu.address.ui.SuggestionLabels.SCORE_SET_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.SCORE_SUB;
import static seedu.address.ui.SuggestionLabels.SCORE_SUB_INSTRUCTION;
import static seedu.address.ui.SuggestionLabels.UNDO;
import static seedu.address.ui.SuggestionLabels.UNDO_INSTRUCTION;

import java.util.stream.Collectors;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


/**
 * represents the possible suggestions that could be displated by autocompletetextfield
 */
public class SuggestionTemplates {
    //IMPORT/EXPORT Commands
    public static final TextFlow IMPORT_TEMPLATE;
    public static final TextFlow EXPORT_TEMPLATE;

    //HISTORY Commands
    public static final TextFlow HISTORY_TEMPLATE;
    public static final TextFlow UNDO_TEMPLATE;
    public static final TextFlow REDO_TEMPLATE;
    public static final TextFlow HELP_TEMPLATE;

    //ADD Commands
    public static final TextFlow ADD_MENTOR_TEMPLATE;
    public static final TextFlow ADD_PARTICIPANT_TEMPLATE;
    public static final TextFlow ADD_TEAM_TEMPLATE;

    //EDIT Commands
    public static final TextFlow EDIT_MENTOR_TEMPLATE;
    public static final TextFlow EDIT_PARTICIPANT_TEMPLATE;
    public static final TextFlow EDIT_TEAM_TEMPLATE;

    //DELETE Command
    public static final TextFlow DELETE_MENTOR_TEMPLATE;
    public static final TextFlow DELETE_PARTICIPANT_TEMPLATE;
    public static final TextFlow DELETE_TEAM_TEMPLATE;

    //FIND Command
    public static final TextFlow FIND_MENTOR_TEMPLATE;
    public static final TextFlow FIND_PARTICIPANT_TEMPLATE;
    public static final TextFlow FIND_TEAM_TEMPLATE;

    //LIST Command
    public static final TextFlow LIST_MENTOR_TEMPLATE;
    public static final TextFlow LIST_PARTICIPANT_TEMPLATE;
    public static final TextFlow LIST_TEAM_TEMPLATE;

    //ASSIGN/REMOVE Command
    public static final TextFlow ASSIGN_PARTICIPANT_TEMPLATE;
    public static final TextFlow ASSIGN_MENTOR_TEMPLATE;
    public static final TextFlow REMOVE_PARTICIPANT_TEMPLATE;
    public static final TextFlow REMOVE_MENTOR_TEMPLATE;

    //SCORE Commands
    public static final TextFlow SCORE_ADD_TEMPLATE;
    public static final TextFlow SCORE_SUB_TEMPLATE;
    public static final TextFlow SCORE_SET_TEMPLATE;
    public static final TextFlow LEADERBOARD_TEMPLATE;
    public static final TextFlow GET_TOP_TEMPLATE;
    public static final TextFlow EXIT_TEMPLATE;


    static {

        Text filepathPrefix0 = new Text("fp/");
        Text filepath0 = new Text("PATH_TO_CSV_FILE ");
        filepath0.setFill(Color.GREY);
        IMPORT_TEMPLATE = new TextFlow(
                IMPORT.getText(), filepathPrefix0,
                filepath0, IMPORT_INSTRUCTION.getText()
        );

        //HISTORY Commands
        HISTORY_TEMPLATE = new TextFlow(
                HISTORY.getText(), HISTORY_INSTRUCTION.getText()
        );

        //HELP Command

        HELP_TEMPLATE = new TextFlow(
                HELP.getText(), HELP_INSTRUCTION.getText()
        );

        //ADD Commands
        Text namePrefix1 = new Text("n/");
        Text name1 = new Text("NAME ");
        Text phonePrefix1 = new Text("p/");
        Text phone1 = new Text("PHONE ");
        Text emailPrefix1 = new Text("e/");
        Text email1 = new Text("EMAIL ");
        Text organizationPrefix1 = new Text("o/");
        Text organization1 = new Text("ORGANIZATION ");
        Text specialisationPrefix1 = new Text("s/");
        Text specialisation1 = new Text("SPECIALISATION");

        name1.setFill(Color.GREY);
        phone1.setFill(Color.GREY);
        email1.setFill(Color.GREY);
        organization1.setFill(Color.GREY);
        specialisation1.setFill(Color.GREY);
        ADD_MENTOR_TEMPLATE = new TextFlow(
                ADD_MENTOR.getText(),
                namePrefix1, name1,
                phonePrefix1, phone1,
                emailPrefix1, email1,
                organizationPrefix1, organization1,
                specialisationPrefix1, specialisation1,
                ADD_MENTOR_INSTRUCTION.getText()
        );

        Text namePrefix2 = new Text("n/");
        Text name2 = new Text("NAME ");
        Text projectNamePrefix2 = new Text("pn/");
        Text projectName2 = new Text("PROJECT_NAME ");
        Text subjectPrefix2 = new Text("s/");
        Text subject2 = new Text("SUBJECT ");
        Text locationPrefix2 = new Text("l/");
        Text table2 = new Text("TABLE_NUMBER ");


        name2.setFill(Color.GREY);
        projectName2.setFill(Color.GREY);
        subject2.setFill(Color.GREY);
        table2.setFill(Color.GREY);

        ADD_TEAM_TEMPLATE = new TextFlow(
                ADD_TEAM.getText(),
                namePrefix2, name2,
                projectNamePrefix2, projectName2,
                subjectPrefix2, subject2,
                locationPrefix2, table2,
                ADD_TEAM_INSTRUCTION.getText()
        );

        Text namePrefix3 = new Text("n/");
        Text name3 = new Text("NAME ");
        Text phonePrefix3 = new Text("p/");
        Text phone3 = new Text("PHONE ");
        Text emailPrefix3 = new Text("e/");
        Text email3 = new Text("EMAIL ");

        name3.setFill(Color.GREY);
        phone3.setFill(Color.GREY);
        email3.setFill(Color.GREY);

        ADD_PARTICIPANT_TEMPLATE = new TextFlow(
                ADD_PARTICIPANT.getText(),
                namePrefix3, name3,
                phonePrefix3, phone3,
                emailPrefix3, email3,
                ADD_PARTICIPANT_INSTRUCTION.getText()

        );


        //EDIT Commands
        Text participantId1 = new Text("PARTICIPANT_ID ");
        Text parameters1 = new Text("[PARAMETERS] ");

        participantId1.setFill(Color.GREY);
        parameters1.setFill(Color.GREY);
        EDIT_PARTICIPANT_TEMPLATE = new TextFlow(EDIT_PARTICIPANT.getText(), participantId1,
                parameters1, EDIT_PARTICIPANT_INSTRUCTION.getText()
        );

        Text mentorId2 = new Text("MENTOR_ID ");
        Text parameters2 = new Text("[PARAMETERS] ");

        mentorId2.setFill(Color.GREY);
        parameters2.setFill(Color.GREY);
        EDIT_MENTOR_TEMPLATE = new TextFlow(EDIT_MENTOR.getText(), mentorId2,
                parameters2, EDIT_MENTOR_INSTRUCTION.getText()
        );

        Text teamId3 = new Text("TEAM_ID ");
        Text parameters3 = new Text("[PARAMETERS] ");

        teamId3.setFill(Color.GREY);
        parameters3.setFill(Color.GREY);
        EDIT_TEAM_TEMPLATE = new TextFlow(EDIT_TEAM.getText(), teamId3,
                parameters3, EDIT_TEAM_INSTRUCTION.getText()
        );

        //DELETE Commands
        Text participantId4 = new Text("PARTICIPANT_ID ");

        participantId4.setFill(Color.GREY);

        DELETE_PARTICIPANT_TEMPLATE = new TextFlow(
                DELETE_PARTICIPANT.getText(), participantId4,
                DELETE_PARTICIPANT_INSTRUCTION.getText()
        );

        Text mentorId5 = new Text("MENTOR_ID ");

        mentorId5.setFill(Color.GREY);

        DELETE_MENTOR_TEMPLATE = new TextFlow(
                DELETE_MENTOR.getText(), mentorId5,
                DELETE_MENTOR_INSTRUCTION.getText()
        );

        Text teamId6 = new Text("TEAM_ID");

        teamId6.setFill(Color.GREY);

        DELETE_TEAM_TEMPLATE = new TextFlow(
                DELETE_TEAM.getText(), teamId6,
                DELETE_TEAM_INSTRUCTION.getText()
        );

        Text participantId7 = new Text("PARTICIPANT_ID ");

        participantId7.setFill(Color.GREY);


        FIND_PARTICIPANT_TEMPLATE = new TextFlow(
                FIND_PARTICIPANT.getText(), participantId7,
                FIND_PARTICIPANT_INSTRUCTION.getText()
        );

        Text mentorId8 = new Text("MENTOR_ID ");

        mentorId8.setFill(Color.GREY);
        FIND_MENTOR_TEMPLATE = new TextFlow(
                FIND_MENTOR.getText(), mentorId8, FIND_MENTOR_INSTRUCTION.getText()
        );

        Text teamId8 = new Text("TEAM_ID ");

        teamId8.setFill(Color.GREY);

        FIND_TEAM_TEMPLATE = new TextFlow(
                FIND_TEAM.getText(), teamId8, FIND_TEAM_INSTRUCTION.getText()
        );


        LIST_PARTICIPANT_TEMPLATE = new TextFlow(
                LIST_PARTICIPANT.getText(), LIST_PARTICIPANT_INSTRUCTION.getText()
        );

        LIST_MENTOR_TEMPLATE = new TextFlow(
                LIST_MENTOR.getText(), LIST_MENTOR_INSTRUCTION.getText()
        );

        LIST_TEAM_TEMPLATE = new TextFlow(
                LIST_TEAM.getText(), LIST_TEAM_INSTRUCTION.getText()
        );

        Text teamId9 = new Text("TEAM_ID ");
        Text points9 = new Text("NEW_POINTS ");

        teamId9.setFill(Color.GREY);
        points9.setFill(Color.GREY);

        SCORE_ADD_TEMPLATE = new TextFlow(
                SCORE_ADD.getText(), teamId9,
                points9, SCORE_ADD_INSTRUCTION.getText()
        );

        Text teamId10 = new Text("TEAM_ID ");
        Text points10 = new Text("NEW_POINTS ");

        teamId10.setFill(Color.GREY);
        points10.setFill(Color.GREY);

        SCORE_SUB_TEMPLATE = new TextFlow(
                SCORE_SUB.getText(), teamId10,
                points10, SCORE_SUB_INSTRUCTION.getText()
        );

        Text teamId11 = new Text("TEAM_ID ");
        Text newPoints11 = new Text("NEW_POINTS ");

        teamId11.setFill(Color.GREY);
        newPoints11.setFill(Color.GREY);

        SCORE_SET_TEMPLATE = new TextFlow(
                SCORE_SET.getText(), teamId11,
                newPoints11, SCORE_SET_INSTRUCTION.getText()
        );

        LEADERBOARD_TEMPLATE = new TextFlow(LEADERBOARD.getText(), LEADERBOARD_INSTRUCTION.getText());

        Text number12 = new Text("NUMBER ");

        number12.setFill(Color.GREY);

        GET_TOP_TEMPLATE = new TextFlow(GET_TOP.getText(), number12, GET_TOP_INSTRUCTION.getText());

        EXIT_TEMPLATE = new TextFlow(EXIT.getText(), EXIT_INSTRUCTION.getText());

        Text participantId13 = new Text("PARTICIPANT_ID");
        Text teamId13 = new Text("TEAM_ID ");

        participantId13.setFill(Color.GREY);
        teamId13.setFill(Color.GREY);

        ASSIGN_PARTICIPANT_TEMPLATE = new TextFlow(
                ASSIGN_PARTICIPANT.getText(), participantId13,
                teamId13, ASSIGN_PARTICIPANT_INSTRUCTION.getText()
        );

        Text mentorId14 = new Text("MENTOR_ID ");
        Text teamId14 = new Text("TEAM_ID ");

        mentorId14.setFill(Color.GREY);
        teamId14.setFill(Color.GREY);

        ASSIGN_MENTOR_TEMPLATE = new TextFlow(
                ASSIGN_MENTOR.getText(), mentorId14,
                teamId14, ASSIGN_MENTOR_INSTRUCTION.getText()
        );

        Text participantId15 = new Text("PARTICIPANT_ID ");
        Text teamId15 = new Text("TEAM_ID ");

        participantId15.setFill(Color.GREY);
        teamId15.setFill(Color.GREY);

        REMOVE_PARTICIPANT_TEMPLATE = new TextFlow(
                REMOVE_PARTICIPANT.getText(), participantId15,
                teamId15, REMOVE_PARTICIPANT_INSTRUCTION.getText()
        );

        Text mentorId16 = new Text("MENTOR_ID ");
        Text teamId16 = new Text("TEAM_ID ");

        mentorId16.setFill(Color.GREY);
        teamId16.setFill(Color.GREY);

        REMOVE_MENTOR_TEMPLATE = new TextFlow(
                REMOVE_MENTOR.getText(), mentorId16,
                teamId16, REMOVE_MENTOR_INSTRUCTION.getText()
        );

        Text entity17 = new Text("[team/mentor/participant] ");
        Text filepathPrefix17 = new Text("fp/");
        Text filepath17 = new Text("PATH_TO_CSV_FILE ");
        filepath17.setFill(Color.GREY);
        entity17.setFill(Color.GREY);
        EXPORT_TEMPLATE = new TextFlow(
                EXPORT.getText(), entity17,
                filepathPrefix17, filepath0,
                EXPORT_INSTRUCTION.getText()
        );

        //UNDO/REDO Method
        Text numbers17 = new Text("[NUMBER] ");
        UNDO_TEMPLATE = new TextFlow(
                UNDO.getText(), numbers17, UNDO_INSTRUCTION.getText()
        );

        Text numbers18 = new Text("[NUMBER] ");
        REDO_TEMPLATE = new TextFlow(
                REDO.getText(), numbers18, REDO_INSTRUCTION.getText()
        );

    }


    /**
     * Returns a String that represents the template form of commands.
     * Only prefix and commands are returned.
     * Constants that are used to guide the user(e.g NAME, PHONE_NUMBER) are not returned).
     */
    public static final String getString(TextFlow template) {
        String result = template.getChildren()
                .stream()
                .map(textElement -> (Text) textElement)
                .map(t -> t.getText())
                .filter(s -> !s.matches("\\[[A-Za-z{}_ ]+\\] |[A-Z_{} ]+|\\([A-Za-z_ ]+\\)"))
                .collect(Collectors.joining(" "));
        return result;

    }


}
