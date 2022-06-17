import java.util.Scanner;

public class BuggedAgent {
    private static int ZONES_COUNT = 8;
    private static int TRAINING_CARD = 0;
    private static int CODING_CARD = 1;
    private static int DAILY_ROUTINE_CARD = 2;
    private static int TASK_PRIORITIZATION_CARD = 3;
    private static int ARCHITECTURE_STUDY_CARD = 4;
    private static int CONTINUOUS_INTEGRATION_CARD = 5;
    private static int CODE_REVIEW_CARD = 6;
    private static int REFACTORING_CARD = 7;
    private static int BONUS_CARD = 8;
    private static int TECHNICAL_DEBT_CARD = 9;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        class Application {
            public int id;
            public int[] resources = new int[8];

            public Application(String data) {
                String[] applicationDetails = data.split(" ");
                id = Integer.parseInt(applicationDetails[1]);
                for (int resourceId=2;resourceId<applicationDetails.length;++resourceId) {
                    resources[resourceId-2] = Integer.parseInt(applicationDetails[resourceId]);
                }
            }
        }

        while (true) {
            //read game phase
            String gamePhase = scanner.nextLine();
            System.err.println("1st move agent");
            System.err.println(gamePhase);

            //read objectives details
            int applicationsCount = Integer.parseInt(scanner.nextLine());
            System.err.println(applicationsCount);
            Application[] applications = new Application[applicationsCount];
            for (int i=0;i<applicationsCount;++i) {
                String applicationData = scanner.nextLine();
                System.err.println(applicationData); //application details
                applications[i] = new Application(applicationData);
            }

            //read players details
            String playerDetails = scanner.nextLine();
            System.err.println(playerDetails); //zones cards details
            String[] playerLocationsDetails = playerDetails.split(" ");
            int myLocation = Integer.parseInt(playerLocationsDetails[0]);
            int myScore = Integer.parseInt(playerLocationsDetails[1]);
            int myPermanentDailyRoutineCards = Integer.parseInt(playerLocationsDetails[2]);
            int myPermanentArchitectureStudyCards = Integer.parseInt(playerLocationsDetails[3]);

            playerDetails = scanner.nextLine();
            System.err.println(playerDetails); //zones cards details
            playerLocationsDetails = playerDetails.split(" ");
            int opponentLocation = Integer.parseInt(playerLocationsDetails[0]);
            int opponentScore = Integer.parseInt(playerLocationsDetails[1]);
            int opponentPermanentDailyRoutineCards = Integer.parseInt(playerLocationsDetails[2]);
            int opponentPermanentArchitectureStudyCards = Integer.parseInt(playerLocationsDetails[3]);

            //read player cards
            int[] myCardsInHand = new int[10];
            int[] myDrawPile = new int[10];
            int[] myDiscardPile = new int[10];
            int[] myAutomatedCards = new int[10];

            int cardLocationsCount = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < cardLocationsCount; i++) {
                String cardsData = scanner.nextLine();
                System.err.println(cardsData); //cards details
                String[] cardsDetails = cardsData.split(" ");
                int[] cards = new int[10];
                String cardsLocation = cardsDetails[0]; // the location of the card list. It can be HAND, DRAW, DISCARD or OPPONENT_CARDS (AUTOMATED and OPPONENT_AUTOMATED will appear in later leagues)
                if (cardsLocation.equals("HAND")) {
                    cards = myCardsInHand;
                } else if (cardsLocation.equals("DRAW")) {
                    cards = myDrawPile;
                } else if (cardsLocation.equals("DISCARD")) {
                    cards = myDiscardPile;
                } else if (cardsLocation.equals("AUTOMATED")) {
                    cards = myAutomatedCards;
                }
                for (int j=1;j<cardsDetails.length;++j) {
                    cards[j-1] = Integer.parseInt(cardsDetails[j]);
                }
            }

            //read possible moves
            int movesCount = Integer.parseInt(scanner.nextLine());
            System.err.println(movesCount);
            String[] moves = new String[movesCount];
            for(int i=0;i<movesCount;++i) {
                moves[i] = scanner.nextLine();
                System.err.println(moves[i]);
            }

            if (gamePhase.equals("MOVE")) {
                if (myLocation==0) {
                    System.out.println(String.format("MOVE 1"));
                }
                else {
                    System.out.println(String.format("MOVE 0"));
                }
            }
            else if (gamePhase.equals("GIVE_CARD")){
                System.out.println("RANDOM");
            }
            else if (gamePhase.equals("THROW_CARD")){
                System.out.println("RANDOM");
            }
            else if (gamePhase.equals("RELEASE")){
                System.out.println("WAIT");
            }
            else if (gamePhase.equals("PLAY_CARD")){
                if (myCardsInHand[0]>0) {
                    System.out.println("TRAINING");
                }
                else if (myCardsInHand[1]>0) {
                    System.out.println("CODING");
                }
                else {
                    System.out.println("WAIT");
                }
            }
            else {
                System.out.println(moves[0]);
            }
        }
    }
}