package seedu.address.diaryfeature.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.diaryfeature.model.DiaryBook;

public class JsonDiaryBookStorage implements DiaryBookStorage {
    private static final Logger logger = LogsCenter.getLogger(seedu.address.diaryfeature.storage.JsonDiaryBookStorage.class);
        private Path filePath;

        public JsonDiaryBookStorage(Path filePath) {
            this.filePath = filePath;
        }

        public Path getDiaryBookFilePath() {
            return filePath;
        }

        @Override
        public void saveDiaryBook(DiaryBook diaryBook) throws IOException {
            saveDiaryBook(diaryBook, filePath);
        }

        /**
         *
         * @param filePath location of the data. Cannot be null.
         */
        public void saveDiaryBook(DiaryBook diaryBook, Path filePath) throws IOException {
            requireNonNull(diaryBook);
            requireNonNull(filePath);

            FileUtil.createIfMissing(filePath);
            JsonUtil.saveJsonFile(new JsonSerializableDiaryBook(diaryBook), filePath);
        }

    }


