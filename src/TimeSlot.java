/**
 * Fixed clinic time slots: 08:00-16:00 in 30-minute intervals.
 * Using an enum makes the fixed set easy to read, type-safe and easy to extend.
 */
public enum TimeSlot {
    SLOT_0800("08:00"),
    SLOT_0830("08:30"),
    SLOT_0900("09:00"),
    SLOT_0930("09:30"),
    SLOT_1000("10:00"),
    SLOT_1030("10:30"),
    SLOT_1100("11:00"),
    SLOT_1130("11:30"),
    SLOT_1200("12:00"),
    SLOT_1230("12:30"),
    SLOT_1300("13:00"),
    SLOT_1330("13:30"),
    SLOT_1400("14:00"),
    SLOT_1430("14:30"),
    SLOT_1500("15:00"),
    SLOT_1530("15:30");

    private final String label;

    TimeSlot(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /** Parse a user-entered label like "09:30" into a TimeSlot, or null if no match. */
    public static TimeSlot fromLabel(String label) {
        if (label == null) return null;
        String trimmed = label.trim();
        for (TimeSlot s : values()) {
            if (s.label.equals(trimmed)) return s;
        }
        return null;
    }
}
