package com.typee.storage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.commons.exceptions.DataConversionException;
import com.typee.commons.util.JsonUtil;
import com.typee.ui.Tab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Implemented Storage unit for Typee application.
 */
public class JsonTypeeStorage implements TypeeStorage {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Path filePath;

    public JsonTypeeStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public ObservableList<Tab> getTabList() throws DataConversionException {
        logger.info(filePath.toString());
        Optional<List<Tab>> optionalTabs = JsonUtil.readJsonFileIntoList(filePath, Tab.class);
        List<Tab> tabList = optionalTabs.orElse(new ArrayList<Tab>());
        return FXCollections.observableList(tabList);
    }
}
