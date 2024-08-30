import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.io.File;

public class Game {
    private Player player;
    private CareerManager careerManager;
    private Scanner scanner;
    private int timeUnits = 10;

    public Game() {
        this.player = new Player();
        this.careerManager = new CareerManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Gamified Career Path Simulator!");
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        player.setName(playerName);

        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    exploreCareerPaths();
                    break;
                case 2:
                    takeChallenge();
                    break;
                case 3:
                    manageCareer();
                    break;
                case 4:
                    viewProfile();
                    break;
                case 5:
                    viewSkillTree();
                    break;
                case 6:
                    restoreTimeUnits();
                    break;
                case 7:
                    saveGame();
                    break;
                case 8:
                    loadGame();
                    break;
                case 9:
                    System.out.println("Thank you for playing. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("Time Units: " + timeUnits);
        System.out.println("1. Explore Career Paths");
        System.out.println("2. Take a Challenge");
        System.out.println("3. Manage Career");
        System.out.println("4. View Profile");
        System.out.println("5. View Skill Tree");
        System.out.println("6. Restore Time Units");
        System.out.println("7. Save Game");
        System.out.println("8. Load Game");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private void exploreCareerPaths() {
        System.out.println("\nExploring Career Paths...");
        List<Career> careers = careerManager.getCareers();
        
        for (int i = 0; i < careers.size(); i++) {
            System.out.println((i + 1) + ". " + careers.get(i).getTitle());
        }
        
        System.out.print("Enter the number of the career you want to explore (0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice > 0 && choice <= careers.size()) {
            Career selectedCareer = careers.get(choice - 1);
            System.out.println("\n" + selectedCareer);
        } else if (choice != 0) {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private void takeChallenge() {
        if (player.getCurrentCareer() == null || player.getSpecialization() == null) {
            System.out.println("You need to choose a career and specialization before taking on challenges.");
            return;
        }

        if (timeUnits < 1) {
            System.out.println("You don't have enough time units to take a challenge.");
            return;
        }

        displayAvailableChallenges();
    }

    private void displayAvailableChallenges() {
        System.out.println("\nAvailable Challenges:");
        List<Challenge> availableChallenges = careerManager.getAvailableChallenges(
            player.getCurrentCareer().getTitle(),
            player.getSpecialization(),
            player.getCareerLevel()
        );

        if (availableChallenges.isEmpty()) {
            System.out.println("No challenges available for your current career, specialization, and level.");
            return;
        }

        for (int i = 0; i < availableChallenges.size(); i++) {
            Challenge challenge = availableChallenges.get(i);
            System.out.println((i + 1) + ". " + challenge.getName() + " (Difficulty: " + challenge.getDifficultyLevel() + ")");
        }

        System.out.print("Enter the number of the challenge you want to attempt (0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= availableChallenges.size()) {
            Challenge selectedChallenge = availableChallenges.get(choice - 1);
            attemptChallenge(selectedChallenge);
        } else if (choice != 0) {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private void attemptChallenge(Challenge challenge) {
        System.out.println("\nChallenge: " + challenge.getName());
        System.out.println("Description: " + challenge.getDescription());
        System.out.println("Skill: " + challenge.getSkill());
        System.out.println("Difficulty: " + challenge.getDifficultyLevel());
        System.out.println("Experience Reward: " + challenge.getExperienceReward());

        System.out.print("Do you want to attempt this challenge? (y/n): ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("y")) {
            int successChance = 100 - (challenge.getDifficultyLevel() - player.getCareerLevel()) * 20;
            successChance = Math.max(10, Math.min(90, successChance)); // Ensure chance is between 10% and 90%

            if (Math.random() * 100 < successChance) {
                System.out.println("Congratulations! You completed the challenge!");
                player.addExperiencePoints(challenge.getExperienceReward());
                player.improveSkill(challenge.getSkill(), 1);
                System.out.println("You gained " + challenge.getExperienceReward() + " experience points.");
                System.out.println("Your " + challenge.getSkill() + " skill improved by 1.");
                player.levelUp();
            } else {
                System.out.println("Unfortunately, you failed to complete the challenge. Keep practicing and try again!");
            }

            timeUnits--;
        } else {
            System.out.println("Challenge skipped.");
        }
    }

    private void viewProfile() {
        System.out.println("\nPlayer Profile:");
        System.out.println(player);
    }

    private void manageCareer() {
        System.out.println("\nCareer Management");
        if (player.getCurrentCareer() == null) {
            System.out.println("You don't have a current career. Let's choose one!");
            chooseCareer();
        } else {
            System.out.println("Current Career: " + player.getCurrentCareer().getTitle());
            System.out.println("Career Level: " + player.getCareerLevel());
            System.out.println("\n1. Change Career");
            System.out.println("2. Try for Promotion");
            System.out.println("3. Go Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    chooseCareer();
                    break;
                case 2:
                    tryPromotion();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void chooseCareer() {
        List<Career> careers = careerManager.getCareers();
        System.out.println("\nAvailable Careers:");
        for (int i = 0; i < careers.size(); i++) {
            System.out.println((i + 1) + ". " + careers.get(i).getTitle());
        }
        System.out.print("Enter the number of the career you want to choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= careers.size()) {
            Career selectedCareer = careers.get(choice - 1);
            player.setCurrentCareer(selectedCareer);
            System.out.println("Congratulations! You are now a " + selectedCareer.getTitle() + ".");
            chooseSpecialization(selectedCareer);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private void chooseSpecialization(Career career) {
        System.out.println("\nAvailable Specializations:");
        Map<String, String[]> specializations = career.getSpecializations();
        String[] specializationNames = specializations.keySet().toArray(new String[0]);
        for (int i = 0; i < specializationNames.length; i++) {
            System.out.println((i + 1) + ". " + specializationNames[i]);
        }
        System.out.print("Enter the number of the specialization you want to choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= specializationNames.length) {
            String selectedSpecialization = specializationNames[choice - 1];
            player.setSpecialization(selectedSpecialization);
            System.out.println("You have chosen the " + selectedSpecialization + " specialization.");
            String[] requiredSkills = specializations.get(selectedSpecialization);
            System.out.println("Focus on improving these skills: " + String.join(", ", requiredSkills));
        } else {
            System.out.println("Invalid choice. No specialization selected.");
        }
    }

    private void tryPromotion() {
        if (player.getCurrentCareer() == null) {
            System.out.println("You need to have a career before trying for a promotion.");
            return;
        }

        int requiredExperience = player.getCareerLevel() * 100;
        if (player.hasRequiredSkills(player.getCurrentCareer()) && player.getExperiencePoints() >= requiredExperience) {
            player.promoteCareer();
            player.addExperiencePoints(-requiredExperience);
            System.out.println("Congratulations! You've been promoted to " + player.getCurrentCareer().getTitle() + " level " + player.getCareerLevel() + ".");
        } else {
            System.out.println("You don't meet the requirements for a promotion yet. Keep improving your skills and gaining experience!");
        }
    }

    private void saveGame() {
        System.out.print("Enter a name for your save file: ");
        String fileName = scanner.nextLine() + ".sav";
        GameState gameState = new GameState(player, timeUnits);
        GameState.saveGame(gameState, fileName);
    }

    private void loadGame() {
        System.out.print("Enter the name of your save file: ");
        String fileName = scanner.nextLine() + ".sav";
        File file = new File(fileName);
        if (file.exists()) {
            GameState gameState = GameState.loadGame(fileName);
            if (gameState != null) {
                this.player = gameState.getPlayer();
                this.timeUnits = gameState.getTimeUnits();
                this.careerManager = new CareerManager(); // Reinitialize CareerManager
                System.out.println("Game loaded successfully.");
            }
        } else {
            System.out.println("Save file not found.");
        }
    }

    private void viewSkillTree() {
        if (player.getCurrentCareer() == null || player.getSpecialization() == null) {
            System.out.println("You need to choose a career and specialization to view the skill tree.");
            return;
        }

        System.out.println("\nSkill Tree for " + player.getCurrentCareer().getTitle() + " - " + player.getSpecialization());
        Map<String, String[]> specializations = player.getCurrentCareer().getSpecializations();
        String[] skills = specializations.get(player.getSpecialization());

        for (String skill : skills) {
            int level = player.getSkillLevel(skill);
            System.out.print(skill + ": ");
            for (int i = 0; i < 10; i++) {
                if (i < level) {
                    System.out.print("█");
                } else {
                    System.out.print("░");
                }
            }
            System.out.println(" (" + level + "/10)");
        }
    }

    private void restoreTimeUnits() {
        System.out.println("Restoring time units...");
        timeUnits = 10;
        System.out.println("Time units restored to " + timeUnits + ".");
    }
}
