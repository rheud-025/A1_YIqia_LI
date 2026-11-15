/** A specialist (e.g. cardiologist, paediatrician, radiologist). */
public class Specialist extends HealthProfessional {
    private final String field;

    public Specialist(String id, String name, String[] availableDays, String field) {
        super(id, name, availableDays);
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialist field is required.");
        }
        this.field = field.trim();
    }

    public String getField() { return field; }

    @Override
    public String getSpecialty() {
        return "Specialist (" + field + ")";
    }

    @Override
    public String toString() {
        return super.toString() + " | field=" + field;
    }
}
