import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class Career implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String description;
    private Map<String, String[]> specializations;

    public Career(String title, String description) {
        this.title = title;
        this.description = description;
        this.specializations = new HashMap<>();
    }

    public void addSpecialization(String name, String[] skills) {
        specializations.put(name, skills);
    }

    public Map<String, String[]> getSpecializations() {
        return specializations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Career: ").append(title).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Specializations:\n");
        for (Map.Entry<String, String[]> entry : specializations.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(": ");
            for (String skill : entry.getValue()) {
                sb.append(skill).append(", ");
            }
            sb.setLength(sb.length() - 2); // Remove last comma and space
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public String[] getRequiredSkills() {
        return specializations.get(getSpecialization());
    }

    public String getSpecialization() {
        return specializations.keySet().iterator().next(); // Returns the first specialization
    }
}
