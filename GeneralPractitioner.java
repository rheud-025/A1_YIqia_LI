/** A general practitioner (GP). Adds a bulk-billing flag. */
public class GeneralPractitioner extends HealthProfessional {
    private final boolean bulkBilling;

    public GeneralPractitioner(String id, String name, String[] availableDays, boolean bulkBilling) {
        super(id, name, availableDays);
        this.bulkBilling = bulkBilling;
    }

    public boolean isBulkBilling() { return bulkBilling; }

    @Override
    public String getSpecialty() {
        return "General Practitioner";
    }

    @Override
    public String toString() {
        return super.toString() + " | bulkBilling=" + bulkBilling;
    }
}
