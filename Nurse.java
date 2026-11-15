/** A nurse. Can take blood samples when qualified. */
public class Nurse extends HealthProfessional {
    private final boolean canTakeBlood;

    public Nurse(String id, String name, String[] availableDays, boolean canTakeBlood) {
        super(id, name, availableDays);
        this.canTakeBlood = canTakeBlood;
    }

    public boolean isCanTakeBlood() { return canTakeBlood; }

    @Override
    public String getSpecialty() {
        return "Nurse";
    }

    @Override
    public String toString() {
        return super.toString() + " | canTakeBlood=" + canTakeBlood;
    }
}
