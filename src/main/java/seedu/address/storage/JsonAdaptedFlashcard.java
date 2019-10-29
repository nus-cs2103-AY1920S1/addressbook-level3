package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.ScheduleIncrement;
import seedu.address.model.flashcard.Statistics;
import seedu.address.model.flashcard.Title;
import seedu.address.model.flashcard.exceptions.StringToScheduleIncrementConversionException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Flashcard}.
 */
class JsonAdaptedFlashcard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing!";
    public static final String MISSING_STATISTICS_FIELD_MESSAGE_FORMAT = "Statistic's %s field is missing!";

    private final String question;
    private final String answer;
    private final String title;
    private final String statisticsLastViewed;
    private final String statisticsToViewNext;
    private final String statisticsCurrentIncrement;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedFlashcard(@JsonProperty("question") String question, @JsonProperty("answer") String answer,
                                @JsonProperty("title") String title,
                                @JsonProperty("statisticsLastViewed") String statisticsLastViewed,
                                @JsonProperty("statisticsToViewNext") String statisticsToViewNext,
                                @JsonProperty("statisticsCurrentIncrement") String statisticsCurrentIncrement,
                                @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.question = question;
        this.answer = answer;
        this.title = title;
        this.statisticsLastViewed = statisticsLastViewed;
        this.statisticsToViewNext = statisticsToViewNext;
        this.statisticsCurrentIncrement = statisticsCurrentIncrement;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedFlashcard(Flashcard source) {
        question = source.getQuestion().fullQuestion;
        answer = source.getAnswer().fullAnswer;
        title = source.getTitle().fullTitle;
        statisticsLastViewed = source.getStatistics().getLastViewed().toString();
        statisticsToViewNext = source.getStatistics().getToViewNext().toString();
        statisticsCurrentIncrement = source.getStatistics().getCurrentIncrement().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     *
     * @return
     * @throws IllegalValueException
     */
    public Statistics toModelTypeHelper() throws IllegalValueException {
        try {
            if (statisticsLastViewed == null) {
                throw new IllegalValueException(String.format(MISSING_STATISTICS_FIELD_MESSAGE_FORMAT,
                        "lastViewed"));
            }
            if (statisticsToViewNext == null) {
                throw new IllegalValueException(String.format(MISSING_STATISTICS_FIELD_MESSAGE_FORMAT,
                        "toViewNext"));
            }
            if (statisticsCurrentIncrement == null) {
                throw new IllegalValueException((String.format(MISSING_STATISTICS_FIELD_MESSAGE_FORMAT,
                        "currentIncrement")));
            }
            return new Statistics(LocalDate.parse(statisticsLastViewed), LocalDate.parse(statisticsToViewNext),
                    ScheduleIncrement.getScheduleIncrementFromString(statisticsCurrentIncrement));
        } catch (StringToScheduleIncrementConversionException e) {
            throw new IllegalValueException(e.getMessage());
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Statistics.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    public Flashcard toModelType() throws IllegalValueException {
        final List<Tag> flashcardTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            flashcardTags.add(tag.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName()));
        }
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_CONSTRAINTS);
        }
        final Answer modelAnswer = new Answer(answer);

        if (title == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        final Statistics modelStatistics = toModelTypeHelper();

        final Set<Tag> modelTags = new HashSet<>(flashcardTags);
        return new Flashcard(modelQuestion, modelAnswer, modelTitle, modelStatistics, modelTags);
    }

}
