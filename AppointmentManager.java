import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores and manages all of today's appointments.
 * Supports adding (with double-booking protection), filtering, sorting, and cancelling.
 */
public class AppointmentManager {
    private final List<Appointment> appointments = new ArrayList<>();

    /** Add an appointment. Returns a status message for the caller. */
    public String addAppointment(Patient patient, HealthProfessional professional, TimeSlot slot) {
        for (Appointment existing : appointments) {
            if (existing.getProfessional().equals(professional)
                    && existing.getTimeSlot() == slot) {
                return "WARNING: " + professional.getName()
                        + " is already booked at " + slot.getLabel() + ".";
            }
        }
        appointments.add(new Appointment(patient, professional, slot));
        return "OK: appointment booked for " + patient.getName()
                + " with " + professional.getName() + " at " + slot.getLabel() + ".";
    }

    /** All appointments, sorted by time earliest-first. */
    public List<Appointment> getAllSorted() {
        List<Appointment> copy = new ArrayList<>(appointments);
        Collections.sort(copy);
        return copy;
    }

    public List<Appointment> filterByProfessionalId(String professionalId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getProfessional().getId().equals(professionalId)) {
                result.add(a);
            }
        }
        Collections.sort(result);
        return result;
    }

    public List<Appointment> filterByPhone(String mobile) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getPatient().getMobile().equals(mobile)) {
                result.add(a);
            }
        }
        Collections.sort(result);
        return result;
    }

    /** Cancel the appointment for a given professional at a given slot. */
    public String cancelByProfessionalAndSlot(String professionalId, TimeSlot slot) {
        Appointment found = null;
        for (Appointment a : appointments) {
            if (a.getProfessional().getId().equals(professionalId)
                    && a.getTimeSlot() == slot) {
                found = a;
                break;
            }
        }
        if (found == null) {
            return "WARNING: no appointment found for professional ID "
                    + professionalId + " at " + slot.getLabel() + ".";
        }
        appointments.remove(found);
        return "OK: cancelled " + found;
    }

    /** Cancel all appointments belonging to a given patient phone number. */
    public String cancelByPhone(String mobile) {
        List<Appointment> toRemove = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getPatient().getMobile().equals(mobile)) {
                toRemove.add(a);
            }
        }
        if (toRemove.isEmpty()) {
            return "WARNING: no appointment found for mobile " + mobile + ".";
        }
        appointments.removeAll(toRemove);
        return "OK: cancelled " + toRemove.size() + " appointment(s) for " + mobile + ".";
    }

    public int count() {
        return appointments.size();
    }
}
