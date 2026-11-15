/** A dietitian. Holds an accreditation label. */
public class Dietitian extends HealthProfessional {
    private final String accreditation;

    public Dietitian(String id, String name, String[] availableDays, String accreditation) {
        super(id, name, availableDays);
        if (accreditation == null || accreditation.trim().isEmpty()) {
            throw new IllegalArgumentException("Accreditation is required.");
        }
        this.accreditation = accreditation.trim();
    }

    public String getAccreditation() { return accreditation; }

    @Override
    public String getSpecialty() {
        return "Dietitian";
    }

    @Override
    public String toString() {
        return super.toString() + " | accreditation=" + accreditation;
    }
}
