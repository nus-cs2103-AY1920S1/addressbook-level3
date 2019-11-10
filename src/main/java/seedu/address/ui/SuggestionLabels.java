package seedu.address.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Represents all the enums that can be used as a building block to build suggestion templates.
 */
public enum SuggestionLabels {
    //Constants used as instructions

    //ADD Instruction
    ADD_PARTICIPANT_INSTRUCTION(new Text("(Adds a new Participant)")),
    ADD_MENTOR_INSTRUCTION(new Text("(Adds a new Mentor)")),
    ADD_TEAM_INSTRUCTION(new Text("(Adds a new Team)")),

    //EDIT Instruction
    EDIT_PARTICIPANT_INSTRUCTION(new Text(
            "(Edits existing Participant according to PARAMETERS)"
    )),
    EDIT_MENTOR_INSTRUCTION(new Text("(Edits existing Mentor according to PARAMETERS)")),
    EDIT_TEAM_INSTRUCTION(new Text("(Edits existing Team according to PARAMETERS)")),

    //FIND Instruction
    FIND_PARTICIPANT_INSTRUCTION(new Text("(Finds a new Participant)")),
    FIND_MENTOR_INSTRUCTION(new Text("(Finds a new Mentor)")),
    FIND_TEAM_INSTRUCTION(new Text("(Finds a new Team)")),

    //DELETE Instruction
    DELETE_PARTICIPANT_INSTRUCTION(new Text("(Deletes an existing Participant)")),
    DELETE_MENTOR_INSTRUCTION(new Text("(Deletes an existing Mentor)")),
    DELETE_TEAM_INSTRUCTION(new Text("(Deletes an existing Team)")),

    //LIST Instruction
    LIST_PARTICIPANT_INSTRUCTION(new Text("(Lists all Participants)")),
    LIST_MENTOR_INSTRUCTION(new Text("(Lists all Mentors)")),
    LIST_TEAM_INSTRUCTION(new Text("(Lists all Teams)")),

    //SCORE Instruction
    SCORE_ADD_INSTRUCTION(new Text("(Adds POINTS to Team with TEAM_ID)")),
    SCORE_SUB_INSTRUCTION(new Text("(Subtracts POINTS to Team with TEAM_ID)")),
    SCORE_SET_INSTRUCTION(new Text("(Set Team with TEAM_ID to obtain a SCORE)")),
    LEADERBOARD_INSTRUCTION(new Text("(Lists all Teams from highest to lowest Score)")),
    GET_TOP_INSTRUCTION(new Text("(Lists the top NUMBER of Teams according to Score)")),

    //IMPORT Instruction
    IMPORT_INSTRUCTION(new Text("(Adds multiple Participants with details in .csv file)")),
    EXPORT_INSTRUCTION(new Text(
            "(Export Entity and their details into .csv file specified by FILE_PATH)"
    )),

    //HISTORY Instruction
    HISTORY_INSTRUCTION(new Text("(Gets all past commands you have entered)")),
    UNDO_INSTRUCTION(new Text("(Undoes the most recent command)")),
    REDO_INSTRUCTION(new Text("(Redoes the most recent command that you have undone)")),

    //ASSIGN/REMOVE Instruction
    ASSIGN_PARTICIPANT_INSTRUCTION(new Text("(assign participant by ID to team with TEAM_ID)")),
    ASSIGN_MENTOR_INSTRUCTION(new Text("(assign mentor by ID to team with TEAM_ID)")),
    REMOVE_PARTICIPANT_INSTRUCTION(new Text("(removes participant by ID from team with TEAM_ID)")),
    REMOVE_MENTOR_INSTRUCTION(new Text("(removes participant by ID from team with TEAM_ID)")),

    //OTHER Instruction
    HELP_INSTRUCTION(new Text("(Opens a new Help Window)")),

    EXIT_INSTRUCTION(new Text("(Exits from application)")),

    //Constants that are used as template(this Text is also indicative of command type)

    //ADD Templates
    ADD_PARTICIPANT(new Text("add participant ")),
    ADD_MENTOR(new Text("add mentor ")),
    ADD_TEAM(new Text("add team ")),

    //LIST Templates
    LIST_PARTICIPANT(new Text("list participants ")),
    LIST_MENTOR(new Text("list mentors ")),
    LIST_TEAM(new Text("list teams ")),

    //EDIT Templates
    EDIT_PARTICIPANT(new Text("edit participant ")),
    EDIT_MENTOR(new Text("edit mentor ")),
    EDIT_TEAM(new Text("edit team ")),

    //FIND Templates
    FIND_PARTICIPANT(new Text("find participant ")),
    FIND_MENTOR(new Text("find mentor ")),
    FIND_TEAM(new Text("find team ")),

    //DELETE Templates
    DELETE_PARTICIPANT(new Text("delete participant ")),
    DELETE_MENTOR(new Text("delete mentor ")),
    DELETE_TEAM(new Text("delete team ")),

    //SCORE Templates
    SCORE_ADD(new Text("score add ")),
    SCORE_SUB(new Text("score sub ")),
    SCORE_SET(new Text("score set ")),
    LEADERBOARD(new Text("leaderboard ")),
    GET_TOP(new Text("getTop ")),

    //HISTORY Templates
    HISTORY(new Text("history ")),
    UNDO(new Text("undo ")),
    REDO(new Text("redo ")),

    //IMPORT/EXPORT Templates
    IMPORT(new Text("import ")),
    EXPORT(new Text("export ")),

    //ASSIGN Templates
    ASSIGN_PARTICIPANT(new Text("assign participant ")),
    ASSIGN_MENTOR(new Text("assign mentor ")),
    REMOVE_PARTICIPANT(new Text("remove participant ")),
    REMOVE_MENTOR(new Text("remove mentor ")),

    //OTHER Templates
    HELP(new Text("help ")),

    EXIT(new Text("exit "));

    private final Text text;

    SuggestionLabels(Text text) {
        if (text.getText().matches("\\[[A-Za-z{}_ ]+\\] |[A-Z_{} ]+")) {
            text.setFill(Color.GREY);
        } else if (text.getText().matches("\\([A-Za-z_. ]+\\)")) {
            text.setFill(Color.CORNFLOWERBLUE);
        }
        this.text = text;
    }

    public Text getText() {
        return this.text;
    }


}
