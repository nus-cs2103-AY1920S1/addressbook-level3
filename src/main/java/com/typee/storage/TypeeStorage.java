package com.typee.storage;

import com.typee.commons.exceptions.DataConversionException;
import com.typee.model.Tab;

import javafx.collections.ObservableList;

/**
 * Represents Storage for Typee Application
 */
public interface TypeeStorage {
    ObservableList<Tab> getTabList() throws DataConversionException;
}
