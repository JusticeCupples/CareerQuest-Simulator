import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int experiencePoints;
    private Map<String, Integer> skills;
    private Career currentCareer;
    private int careerLevel;
    private String specialization;

    public Player() {
        this.experiencePoints = 0;
        this.skills = new HashMap<>();
        this.careerLevel = 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addExperiencePoints(int points) {
        this.experiencePoints += points;
    }

    public void addSkill(String skill, int level) {
        skills.put(skill, level);
    }

    public void improveSkill(String skill, int improvement) {
        skills.put(skill, skills.getOrDefault(skill, 0) + improvement);
    }

    public void setCurrentCareer(Career career) {
        this.currentCareer = career;
    }

    public Career getCurrentCareer() {
        return currentCareer;
    }

    public int getCareerLevel() {
        return careerLevel;
    }

    public void promoteCareer() {
        careerLevel++;
    }

    public boolean hasRequiredSkills(Career career) {
        for (String skill : career.getRequiredSkills()) {
            if (!skills.containsKey(skill) || skills.get(skill) < careerLevel) {
                return false;
            }
        }
        return true;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getSkillLevel(String skill) {
        return skills.getOrDefault(skill, 0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Experience Points: ").append(experiencePoints).append("\n");
        sb.append("Current Career: ").append(currentCareer != null ? currentCareer.getTitle() : "None").append("\n");
        sb.append("Career Level: ").append(careerLevel).append("\n");
        sb.append("Skills:\n");
        for (Map.Entry<String, Integer> entry : skills.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("Specialization: ").append(specialization != null ? specialization : "None").append("\n");
        return sb.toString();
    }

    public void levelUp() {
        int experienceNeeded = 100 * (careerLevel + 1);
        if (experiencePoints >= experienceNeeded) {
            careerLevel++;
            experiencePoints -= experienceNeeded;
            System.out.println("Congratulations! You've leveled up to career level " + careerLevel + "!");
        } else {
            System.out.println("Not enough experience points to level up. You need " + (experienceNeeded - experiencePoints) + " more points.");
        }
    }
}
