package com.typee.model.util;

import java.util.Comparator;

import com.typee.model.engagement.Engagement;

/**
 * Specifies and implements comparators for each comparable property of {@code Engagement}
 */
public enum EngagementComparator implements Comparator<Engagement> {
    START_TIME {
        @Override
        public final int compare(final Engagement o1, final Engagement o2) {
            return o1.getTimeSlot().getStartTime().compareTo(o2.getTimeSlot().getStartTime());
        }
    },
    START_TIME_REVERSE {
        @Override
        public final int compare(final Engagement o1, final Engagement o2) {
            return START_TIME.compare(o2, o1);
        }
    },
    END_TIME {
        @Override
        public final int compare(final Engagement o1, final Engagement o2) {
            return o1.getTimeSlot().getEndTime().compareTo(o2.getTimeSlot().getEndTime());
        }
    },
    END_TIME_REVERSE {
        @Override
        public final int compare(final Engagement o1, final Engagement o2) {
            return END_TIME.compare(o2, o1);
        }
    },
    ALPHABETICAL {
        @Override
        public final int compare(final Engagement o1, final Engagement o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    },
    ALPHABETICAL_REVERSE {
        @Override
        public final int compare(final Engagement o1, final Engagement o2) {
            return ALPHABETICAL.compare(o2, o1);
        }
    },
    PRIORITY {
        @Override
        public final int compare(final Engagement o1, final Engagement o2) {
            return o1.getPriority().compareTo(o2.getPriority());
        }
    },
    PRIORITY_REVERSE {
        @Override
        public final int compare(final Engagement o1, final Engagement o2) {
            return PRIORITY.compare(o2, o1);
        }
    };

    public static final String MESSAGE_PROPERTY_CONSTRAINTS =
            "Please enter a proper ordering method. Please try: e.g. sort start ascending";

    public static EngagementComparator getComparator(String order)
            throws IllegalArgumentException {
        return EngagementComparator.valueOf(order.toUpperCase());
    }

}
