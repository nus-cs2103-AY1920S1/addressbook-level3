package com.typee.logic.interactive.parser.state;

import com.typee.logic.interactive.parser.ArgumentMultimap;

public interface OptionalState {
    public boolean canBeSkipped(ArgumentMultimap newArgs);
}
