package dream.fcard.logic.exam;

import dream.fcard.model.exceptions.IndexNotFoundException;

/**
 * Interface to define behaviour of exams.
 * Look to eventually expand to timed and untimed tests.
 */
public interface Exam {

    public void initExam() throws IndexNotFoundException;

}
