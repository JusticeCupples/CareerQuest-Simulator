import java.io.Serializable;

public class Challenge implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private String skill;
    private int experienceReward;
    private String associatedCareer;
    private String associatedSpecialization;
    private int difficultyLevel;

    public Challenge(String name, String description, String skill, int experienceReward, String associatedCareer, String associatedSpecialization, int difficultyLevel) {
        this.name = name;
        this.description = description;
        this.skill = skill;
        this.experienceReward = experienceReward;
        this.associatedCareer = associatedCareer;
        this.associatedSpecialization = associatedSpecialization;
        this.difficultyLevel = difficultyLevel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSkill() {
        return skill;
    }

    public int getExperienceReward() {
        return experienceReward;
    }

    // Add getter for associatedCareer
    public String getAssociatedCareer() {
        return associatedCareer;
    }

    // Add getter for associatedSpecialization
    public String getAssociatedSpecialization() {
        return associatedSpecialization;
    }

    // Add getter for difficultyLevel
    public int getDifficultyLevel() {
        return difficultyLevel;
    }
}
