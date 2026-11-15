import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class for any health professional.
 * Holds the common data (id, name, working days) and forces subclasses to
 * describe their own specialty. Cannot be instantiated directly.
 */
public abstract class HealthProfessional {
    private final String id;
    private final String name;
    private final Set<String> availableDays;

    public HealthProfessional(String id, String name, String[] availableDays) {
        if (id == null || !id.matches("\\d+")) {
            throw new IllegalArgumentException("Professional ID must contain digits only.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Professional name is required.");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.availableDays = new HashSet<>();
        if (availableDays != null) {
            this.availableDays.addAll(Arrays.asList(availableDays));
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }

    /** Defensive copy so external code cannot mutate the internal set. */
    public Set<String> getAvailableDays() {
        return new HashSet<>(availableDays);
    }

    /** Each subclass supplies its own specialty label. */
    public abstract String getSpecialty();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HealthProfessional)) return false;
        HealthProfessional other = (HealthProfessional) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getSpecialty() + " | ID=" + id + " | name=" + name
                + " | available=" + availableDays;
    }
}
