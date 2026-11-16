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

        // Part 6: run the automatic scripted demonstration first (no input needed).
        runScriptedDemo(manager, roster);
        System.out.println("\nNow entering the interactive menu.");

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

    /**
     * Part 6 scripted demonstration. Runs automatically without user input and
     * exercises both normal cases and error cases end-to-end.
     */
    private static void runScriptedDemo(AppointmentManager manager,
                                        List<HealthProfessional> roster) {
        System.out.println("\n############################################################");
        System.out.println("# SCRIPTED DEMONSTRATION (Part 6) - runs automatically     #");
        System.out.println("############################################################");

        // 1. Show the full professional roster.
        printRoster(roster);

        // 2. Create several patients.
        Patient p1 = new Patient("John Doe", "0412345678");
        Patient p2 = new Patient("Mary Lee", "0423456789");
        Patient p3 = new Patient("Tom Chen", "0434567890");
        Patient p4 = new Patient("Anna Smith", "0445678901");

        // 3. Book appointments across different professional types.
        System.out.println("\n--- Booking appointments ---");
        HealthProfessional gp1001 = findById(roster, "1001");
        HealthProfessional spec2001 = findById(roster, "2001");
        HealthProfessional nurse3001 = findById(roster, "3001");
        HealthProfessional diet4001 = findById(roster, "4001");

        System.out.println(manager.addAppointment(p1, gp1001, TimeSlot.SLOT_0930));
        System.out.println(manager.addAppointment(p2, spec2001, TimeSlot.SLOT_1000));
        System.out.println(manager.addAppointment(p3, nurse3001, TimeSlot.SLOT_0830));
        System.out.println(manager.addAppointment(p1, gp1001, TimeSlot.SLOT_1100));
        System.out.println(manager.addAppointment(p4, diet4001, TimeSlot.SLOT_0900));

        // 4. Display all appointments, sorted by time earliest-first.
        printAppointments(manager.getAllSorted());

        // 5. Error case: attempt to double-book the same professional at the same slot.
        System.out.println("\n--- Error case: double booking ---");
        System.out.println(manager.addAppointment(p2, gp1001, TimeSlot.SLOT_0930));

        // 6. Filter by professional ID.
        System.out.println("\n--- Filter by professional ID 1001 ---");
        printAppointments(manager.filterByProfessionalId("1001"));

        // 7. Filter by patient mobile.
        System.out.println("\n--- Filter by patient mobile 0412345678 ---");
        printAppointments(manager.filterByPhone("0412345678"));

        // 8. Cancel one appointment, then display again to show the update.
        System.out.println("\n--- Cancel nurse appointment (ID 3001 at 08:30) ---");
        System.out.println(manager.cancelByProfessionalAndSlot("3001", TimeSlot.SLOT_0830));
        printAppointments(manager.getAllSorted());

        // 9. Error case: cancel an appointment that no longer exists.
        System.out.println("\n--- Error case: cancel nonexistent appointment ---");
        System.out.println(manager.cancelByProfessionalAndSlot("3001", TimeSlot.SLOT_0830));

        // 10. Error case: cancel by a phone number with no appointments.
        System.out.println("\n--- Error case: cancel by unknown phone ---");
        System.out.println(manager.cancelByPhone("0499999999"));

        System.out.println("\n############################################################");
        System.out.println("# END OF SCRIPTED DEMONSTRATION                            #");
        System.out.println("############################################################");
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
