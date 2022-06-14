import java.util.Random;
import java.util.Scanner;

public class RandomAgent {
    private static int TECHNICAL_DEBT_CARD = 9;
    private static int ZONES_COUNT = 8;
    private static int BONUS_CARD = 8;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            //read game phase
            String gamePhase = scanner.nextLine();
            System.err.println("Random agent");
            System.err.println(gamePhase);

            //read applications details
            int applicationsCount = Integer.parseInt(scanner.nextLine());
            System.err.println(applicationsCount);
            int[][] applications = new int[applicationsCount][ZONES_COUNT + 1];
            for (int i=0;i<applicationsCount;++i) {
                String applicationData = scanner.nextLine();
                System.err.println(applicationData); //application details
                String[] applicationDetails = applicationData.split(" ");
                for (int resourceId=1;resourceId<applicationDetails.length;++resourceId) {
                    applications[i][resourceId-1] = Integer.parseInt(applicationDetails[resourceId]);
                }
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
            System.err.println(cardLocationsCount);
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
            for(int i=0;i<movesCount;++i) {
                System.err.println(scanner.nextLine());
            }

            System.out.println("RANDOM random action");
        }
    }
}
