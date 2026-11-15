/**
 * A single appointment: a patient, a chosen health professional, and a time slot.
 * Implements Comparable so a collection of appointments can be sorted by time.
 */
public class Appointment implements Comparable<Appointment> {
    private final Patient patient;
    private final HealthProfessional professional;
    private final TimeSlot timeSlot;

    public Appointment(Patient patient, HealthProfessional professional, TimeSlot timeSlot) {
        if (patient == null || professional == null || timeSlot == null) {
            throw new IllegalArgumentException("Patient, professional and time slot are required.");
        }
        this.patient = patient;
        this.professional = professional;
        this.timeSlot = timeSlot;
    }

    public Patient getPatient() { return patient; }
    public HealthProfessional getProfessional() { return professional; }
    public TimeSlot getTimeSlot() { return timeSlot; }

    /** Order by the declared order of the TimeSlot enum (earliest first). */
    @Override
    public int compareTo(Appointment other) {
        return this.timeSlot.ordinal() - other.timeSlot.ordinal();
    }

    @Override
    public String toString() {
        return timeSlot.getLabel() + " | " + professional.getName()
                + " (" + professional.getSpecialty() + ", ID=" + professional.getId() + ")"
                + " | patient: " + patient;
    }
}
