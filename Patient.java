/** A patient: name and mobile contact number. */
public class Patient {
    private final String name;
    private final String mobile;

    public Patient(String name, String mobile) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required.");
        }
        if (mobile == null || !mobile.matches("\\d{10}")) {
            throw new IllegalArgumentException("Mobile must be 10 digits.");
        }
        this.name = name.trim();
        this.mobile = mobile.trim();
    }

    public String getName() { return name; }
    public String getMobile() { return mobile; }

    @Override
    public String toString() {
        return name + " (" + mobile + ")";
    }
}
