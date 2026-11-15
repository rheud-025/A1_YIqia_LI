import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive console demonstration of the appointment system.
 */
public class AssignmentOne {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AppointmentManager manager = new AppointmentManager();

        // Seed a small roster of health professionals.
        List<HealthProfessional> roster = new ArrayList<>(Arrays.asList(
                (HealthProfessional) new GeneralPractitioner("1001", "Dr. Alice Wong",
                        new String[]{"Mon", "Wed", "Fri"}, true),
                new GeneralPractitioner("1002", "Dr. Bob Smith",
                        new String[]{"Tue", "Thu"}, false),
                new GeneralPractitioner("1003", "Dr. Carol Lee",
                        new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"}, true),
                new Specialist("2001", "Dr. David Park",
                        new String[]{"Wed"}, "Cardiology"),
                new Specialist("2002", "Dr. Eve Tanaka",
                        new String[]{"Tue", "Thu"}, "Paediatrics"),
                new Nurse("3001", "Nurse Fiona Brown",
                        new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"}, true),
                new Dietitian("4001", "Anna Nguyen",
                        new String[]{"Mon", "Thu"}, "APD")
        ));

        while (true) {
            System.out.println("\n===== CLINIC APPOINTMENT SYSTEM =====");
            System.out.println("1. List all health professionals");
            System.out.println("2. List all appointments (sorted by time)");
            System.out.println("3. Book a new appointment");
            System.out.println("4. Cancel an appointment");
            System.out.println("5. Filter appointments");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    printRoster(roster);
                    break;
                case "2":
                    printAppointments(manager.getAllSorted());
                    break;
                case "3":
                    bookAppointment(scanner, manager, roster);
                    break;
                case "4":
                    cancelAppointment(scanner, manager);
                    break;
                case "5":
                    filterAppointments(scanner, manager);
                    break;
                case "6":
                    System.out.println("Goodbye.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    private static void printRoster(List<HealthProfessional> roster) {
        System.out.println("\n--- Health Professionals ---");
        for (HealthProfessional hp : roster) {
            System.out.println(hp);
        }
    }

    private static void printAppointments(List<Appointment> list) {
        System.out.println("\n--- Appointments (" + list.size() + ") ---");
        if (list.isEmpty()) {
            System.out.println("(none)");
        }
        for (Appointment a : list) {
            System.out.println(a);
        }
    }

    private static HealthProfessional findById(List<HealthProfessional> roster, String id) {
        for (HealthProfessional hp : roster) {
            if (hp.getId().equals(id)) return hp;
        }
        return null;
    }

    private static void bookAppointment(Scanner scanner, AppointmentManager manager,
                                        List<HealthProfessional> roster) {
        printRoster(roster);
        System.out.print("Enter professional ID: ");
        String id = scanner.nextLine().trim();
        HealthProfessional hp = findById(roster, id);
        if (hp == null) {
            System.out.println("WARNING: professional ID not found.");
            return;
        }

        System.out.print("Enter patient name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter patient mobile (10 digits): ");
        String mobile = scanner.nextLine().trim();

        System.out.print("Enter time slot (e.g. 09:30, 08:00-15:30): ");
        String slotLabel = scanner.nextLine().trim();
        TimeSlot slot = TimeSlot.fromLabel(slotLabel);
        if (slot == null) {
            System.out.println("WARNING: invalid time slot.");
            return;
        }

        try {
            Patient patient = new Patient(name, mobile);
            String result = manager.addAppointment(patient, hp, slot);
            System.out.println(result);
        } catch (IllegalArgumentException ex) {
            System.out.println("WARNING: " + ex.getMessage());
        }
    }

    private static void cancelAppointment(Scanner scanner, AppointmentManager manager) {
        System.out.println("Cancel by:");
        System.out.println("1. Professional ID + time slot");
        System.out.println("2. Patient mobile number");
        System.out.print("Choose: ");
        String mode = scanner.nextLine().trim();

        if (mode.equals("1")) {
            System.out.print("Enter professional ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter time slot (e.g. 09:30): ");
            TimeSlot slot = TimeSlot.fromLabel(scanner.nextLine().trim());
            if (slot == null) {
                System.out.println("WARNING: invalid time slot.");
                return;
            }
            System.out.println(manager.cancelByProfessionalAndSlot(id, slot));
        } else if (mode.equals("2")) {
            System.out.print("Enter patient mobile: ");
            String mobile = scanner.nextLine().trim();
            System.out.println(manager.cancelByPhone(mobile));
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void filterAppointments(Scanner scanner, AppointmentManager manager) {
        System.out.println("Filter by:");
        System.out.println("1. Professional ID");
        System.out.println("2. Patient mobile");
        System.out.print("Choose: ");
        String mode = scanner.nextLine().trim();

        if (mode.equals("1")) {
            System.out.print("Enter professional ID: ");
            String id = scanner.nextLine().trim();
            printAppointments(manager.filterByProfessionalId(id));
        } else if (mode.equals("2")) {
            System.out.print("Enter patient mobile: ");
            String mobile = scanner.nextLine().trim();
            printAppointments(manager.filterByPhone(mobile));
        } else {
            System.out.println("Invalid option.");
        }
    }
}
