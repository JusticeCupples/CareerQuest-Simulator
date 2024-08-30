import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CareerManager {
    private List<Career> careers;
    private List<Challenge> challenges;

    public CareerManager() {
        this.careers = new ArrayList<>();
        this.challenges = new ArrayList<>();
        initializeCareers();
        initializeChallenges();
    }

    private void initializeCareers() {
        Career softwareDeveloper = new Career("Software Developer", "Develop software applications");
        softwareDeveloper.addSpecialization("Frontend Developer", new String[]{"JavaScript", "HTML", "CSS"});
        softwareDeveloper.addSpecialization("Backend Developer", new String[]{"Java", "Python", "Databases"});
        softwareDeveloper.addSpecialization("Full Stack Developer", new String[]{"JavaScript", "Java", "Databases"});
        careers.add(softwareDeveloper);

        Career dataScientist = new Career("Data Scientist", "Analyze and interpret complex data");
        dataScientist.addSpecialization("Machine Learning Engineer", new String[]{"Python", "Machine Learning", "Mathematics"});
        dataScientist.addSpecialization("Data Analyst", new String[]{"SQL", "Statistics", "Data Visualization"});
        dataScientist.addSpecialization("Big Data Engineer", new String[]{"Hadoop", "Spark", "Distributed Systems"});
        careers.add(dataScientist);
    }

    private void initializeChallenges() {
        challenges.add(new Challenge("Debug a Program", "Find and fix bugs in a given program", "Problem Solving", 50, "Software Developer", "Backend Developer", 1));
        challenges.add(new Challenge("Implement a Data Structure", "Create an efficient implementation of a specific data structure", "Java", 60, "Software Developer", "Backend Developer", 2));
        challenges.add(new Challenge("Optimize Algorithm", "Improve the performance of a given algorithm", "Problem Solving", 70, "Software Developer", "Backend Developer", 3));
        challenges.add(new Challenge("Create Responsive UI", "Design and implement a responsive user interface", "CSS", 55, "Software Developer", "Frontend Developer", 1));
        challenges.add(new Challenge("Implement SPA", "Create a single-page application using a modern framework", "JavaScript", 65, "Software Developer", "Frontend Developer", 2));
        challenges.add(new Challenge("Optimize Web Performance", "Improve the loading speed and performance of a web application", "JavaScript", 75, "Software Developer", "Frontend Developer", 3));
        challenges.add(new Challenge("Analyze Dataset", "Perform exploratory data analysis on a given dataset", "Statistics", 60, "Data Scientist", "Data Analyst", 1));
        challenges.add(new Challenge("Build Machine Learning Model", "Develop a machine learning model for a specific problem", "Machine Learning", 80, "Data Scientist", "Machine Learning Engineer", 2));
        challenges.add(new Challenge("Data Visualization", "Create insightful visualizations from a complex dataset", "Python", 70, "Data Scientist", "Data Analyst", 3));
    }

    public List<Career> getCareers() {
        return careers;
    }

    public Challenge getRandomChallenge(String careerTitle, String specialization, int playerLevel) {
        List<Challenge> careerChallenges = challenges.stream()
            .filter(c -> c.getAssociatedCareer().equals(careerTitle) &&
                         c.getAssociatedSpecialization().equals(specialization) &&
                         c.getDifficultyLevel() <= playerLevel)
            .collect(Collectors.toList());
        
        if (careerChallenges.isEmpty()) {
            return null;
        }
        
        Random random = new Random();
        return careerChallenges.get(random.nextInt(careerChallenges.size()));
    }

    public List<Challenge> getAvailableChallenges(String careerTitle, String specialization, int playerLevel) {
        return challenges.stream()
            .filter(c -> c.getAssociatedCareer().equals(careerTitle) &&
                         c.getAssociatedSpecialization().equals(specialization) &&
                         c.getDifficultyLevel() <= playerLevel)
            .collect(Collectors.toList());
    }
}
