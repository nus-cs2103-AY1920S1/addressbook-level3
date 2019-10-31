package tagline.testutil.note;

import static tagline.logic.commands.NoteCommandTestUtil.VALID_CONTENT_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_CONTENT_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_NOTEID_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_NOTEID_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMECREATED_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMECREATED_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMELASTUPDATED_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMELASTUPDATED_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TITLE_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TITLE_PROTECTOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tagline.model.note.Note;
import tagline.model.note.NoteBook;

/**
 * A utility class containing a list of {@code Note} objects to be used in tests.
 */
public class TypicalNotes {

    public static final long NOTEID_TIMEHEIST = 31;
    public static final String TITLE_TIMEHEIST = "Time Heist";
    public static final String CONTENT_TIMEHEIST = "\"Six stones, three teams, one shot. "
        + "Five years ago, we lost. All of us. We lost friends. We lost family. We lost "
        + "a part of ourselves. Today, we have a chance to take it all back. You know "
        + "your teams. You know your missions. Get the stones. Get them back.\""
        + "-- Steve Rogers";
    public static final String TIMECREATED_TIMEHEIST = "16-Oct-2023 08:38:09";
    public static final String TIMELASTUPDATED_TIMEHEIST = "17-Oct-2023 17:19:18";

    public static final long NOTEID_ULTRON = 21;
    public static final String TITLE_ULTRON = "Ultron Offensive";
    public static final String CONTENT_ULTRON = "The Ultron Offensive[1] was a series "
        + "of attacks executed by the rogue artificial intelligence, Ultron, in a quest "
        + "to bring about the extinction of humanity. His greatest opposition was by the "
        + "Avengers, and the repetitive confrontations between the two led to various "
        + "negative repercussions against the team. The conflict culminated in a wide-"
        + "scale destructive battle in the Eastern European country of Sokovia, where "
        + "the Avengers, aided by new members and allied forces from the former "
        + "S.H.I.E.L.D. agency, defeated Ultron. ";
    public static final String TIMECREATED_ULTRON = "29-Apr-2015 07:19:05";
    public static final String TIMELASTUPDATED_ULTRON = "06-May-2015 22:18:11";

    // Manually added
    public static final Note TIMEHEIST = new NoteBuilder().withNoteId(NOTEID_TIMEHEIST)
        .withTitle(TITLE_TIMEHEIST)
        .withContent(CONTENT_TIMEHEIST).withTimeCreated(TIMECREATED_TIMEHEIST)
        .withTimeLastEdited(TIMELASTUPDATED_TIMEHEIST).build();

    public static final Note ULTRON = new NoteBuilder().withNoteId(NOTEID_ULTRON)
        .withTitle(TITLE_ULTRON)
        .withContent(CONTENT_ULTRON).withTimeCreated(TIMECREATED_ULTRON)
        .withTimeLastEdited(TIMELASTUPDATED_ULTRON).build();


    // Manually added
    public static final long NOTEID_TOKYO = 45;
    public static final String TITLE_TOKYO = "Massacre in Tokyo";
    public static final String CONTENT_TOKYO = "\"Killing all these people isn't going to "
        + "bring your family back. We found something. A chance. Maybe...\"\n"
        + "    \"Don't.\"\n"
        + "    \"Don't what?\"\n"
        + "    \"Don't give me hope.\"\n"
        + "    \"I'm sorry I couldn't give it to you sooner.\" \n"
        + "    ―Natasha Romanoff and Clint Barton";
    public static final String TIMECREATED_TOKYO = "01-Oct-2023 01:09:01";
    public static final String TIMELASTUPDATED_TOKYO = "31-Oct-2023 23:59:58";

    public static final Note TOKYO = new NoteBuilder().withNoteId(NOTEID_TOKYO)
        .withTitle(TITLE_TOKYO)
        .withContent(CONTENT_TOKYO).withTimeCreated(TIMECREATED_TOKYO)
        .withTimeLastEdited(TIMELASTUPDATED_TOKYO).build();

    public static final long NOTEID_EARTH = 49;
    public static final String TITLE_EARTH = "Battle Of Earth";
    public static final String CONTENT_EARTH = "The Battle of Earth was the culminating "
        + "battle for the fate of the planet and the entire universe, fought among "
        + "the Avengers and their allies united against the alternate timeline "
        + "versions of Thanos, the Black Order, and their full compliments of "
        + "Outriders, Chitauri, and Sakaaran armies. The monumental scale of the "
        + "battle makes it one of the largest extraterrestrial conflicts in Earth's "
        + "history.";
    public static final String TIMECREATED_EARTH = "17-Oct-2023 04:13:21";
    public static final String TIMELASTUPDATED_EARTH = "17-Oct-2023 17:29:14";

    public static final Note EARTH = new NoteBuilder().withNoteId(NOTEID_EARTH)
        .withTitle(TITLE_EARTH)
        .withContent(CONTENT_EARTH).withTimeCreated(TIMECREATED_EARTH)
        .withTimeLastEdited(TIMELASTUPDATED_EARTH).build();
    public static final Note EARTH_NO_TITLE = new NoteBuilder().withNoteId(NOTEID_EARTH)
        .withTitle("")
        .withContent(CONTENT_EARTH).withTimeCreated(TIMECREATED_EARTH)
        .withTimeLastEdited(TIMELASTUPDATED_EARTH).build();

    // Manually added - Note's details found in {@code NoteCommandTestUtil}
    public static final Note PROTECTOR = new NoteBuilder().withNoteId(VALID_NOTEID_PROTECTOR)
        .withTitle(VALID_TITLE_PROTECTOR)
        .withContent(VALID_CONTENT_PROTECTOR).withTimeCreated(VALID_TIMECREATED_PROTECTOR)
        .withTimeLastEdited(VALID_TIMELASTUPDATED_PROTECTOR).build();

    public static final Note INCIDENT = new NoteBuilder().withNoteId(VALID_NOTEID_INCIDENT)
        .withTitle(VALID_TITLE_INCIDENT)
        .withContent(VALID_CONTENT_INCIDENT).withTimeCreated(VALID_TIMECREATED_INCIDENT)
        .withTimeLastEdited(VALID_TIMELASTUPDATED_INCIDENT).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalNotes() {
    } // prevents instantiation

    /**
     * Returns an {@code NoteBook} with all the typical notes.
     */
    public static NoteBook getTypicalNoteBook() {
        NoteBook nb = new NoteBook();
        for (Note note : getTypicalNotes()) {
            nb.addNote(note);
        }
        return nb;
    }

    public static List<Note> getTypicalNotes() {
        return new ArrayList<>(Arrays.asList(PROTECTOR, INCIDENT, TIMEHEIST, ULTRON));
    }
}
