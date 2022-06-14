import java.util.Arrays;
import java.util.Scanner;

class Player {
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
            System.err.println("Virtuous agent");
            System.err.println(gamePhase);

            //read applications details
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

            switch (gamePhase) {
                case "MOVE":
                    int bestLocation = (myLocation + 1) % ZONES_COUNT;
                    int availableBonus = myDrawPile[BONUS_CARD] + myCardsInHand[BONUS_CARD] + myDiscardPile[BONUS_CARD];
                    for (int step=1;step<ZONES_COUNT;++step) {
                        int target = (myLocation+step) % ZONES_COUNT;
                        int available = 2*(myDrawPile[target] + myCardsInHand[target] + myDiscardPile[target]) + availableBonus;
                        if (Arrays.stream(applications).anyMatch(app -> app.resources[target]>available)) {
                            bestLocation = target;
                            break;
                        }
                    }
                    System.out.println(String.format("MOVE %d", bestLocation));
                    break;
                case "GIVE_CARD":
                    if (myCardsInHand[BONUS_CARD]>0) {
                        System.out.println(String.format("GIVE %d", BONUS_CARD));
                    }
                    else {
                        System.out.println("RANDOM");
                    }
                    break;
                case "THROW_CARD":
                    if (myCardsInHand[BONUS_CARD]>0) {
                        System.out.println(String.format("THROW %d", BONUS_CARD));
                    }
                    else {
                        System.out.println("RANDOM");
                    }
                    break;
                case "PLAY_CARD":
                    //if can release an application, do nothing (to keep the cards)
                    if (myCardsInHand[REFACTORING_CARD]>0 && myCardsInHand[TECHNICAL_DEBT_CARD]>0) {
                        //refactoring to remove a technical debt
                        System.out.println("REFACTORING");
                    }
                    else if (myCardsInHand[DAILY_ROUTINE_CARD]>0) {
                        //Daily routine to take cards form far away desks
                        System.out.println("DAILY_ROUTINE");
                    }
                    else if (myCardsInHand[ARCHITECTURE_STUDY_CARD]>0) {
                        //Architecture Study to draw more cards
                        System.out.println("ARCHITECTURE_STUDY");
                    }
                    else if (myCardsInHand[CODE_REVIEW_CARD]>0) {
                        //Code Review to get 2 bonus cards in discard pile
                        System.out.println("CODE_REVIEW");
                    }
                    else if (myCardsInHand[CONTINUOUS_INTEGRATION_CARD]>0 && isThereAnotherCardToAutomate(myCardsInHand)) {
                        //Continuous Integration to automate one card
                        int cardType=0;
                        for (;cardType<9;cardType++) {
                            if ((cardType==CONTINUOUS_INTEGRATION_CARD && myCardsInHand[cardType]>1)
                                    || (cardType!=CONTINUOUS_INTEGRATION_CARD && myCardsInHand[cardType]>0)) {
                                break;
                            }
                        }
                        System.out.println(String.format("CONTINUOUS_INTEGRATION %d",cardType));
                    }
                    else if (myCardsInHand[TRAINING_CARD]>0) {
                        //Training to draw more cards (only if good cards to draw > technical debt cards to draw
                        System.out.println("TRAINING");
                    }
                    else {
                        //do nothing
                        System.out.println("WAIT");
                    }
                    break;
                case "RELEASE":
                    int bestApplication = -1;
                    int myAvailableBonus = myCardsInHand[BONUS_CARD] + myAutomatedCards[BONUS_CARD];
                    int possibleTechnicalDebts = myAvailableBonus;
                    if (myScore==4) {
                        possibleTechnicalDebts = 0;
                    }
                    for (Application application : applications) {
                        int missing = 0;
                        for (int i=0;i<ZONES_COUNT;++i) {
                            missing += Math.max(0, application.resources[i] - 2*(myCardsInHand[i] + myAutomatedCards[i]));
                            possibleTechnicalDebts += 2*(myCardsInHand[i] + myAutomatedCards[i]);
                        }
                        System.err.println(String.format("App %d needs %d resources. I have %d bonus", application.id, missing, myAvailableBonus));
                        if (missing <= myAvailableBonus) {
                            bestApplication = application.id;
                            break;
                        } else if (myScore<4 && missing<= possibleTechnicalDebts && missing<3) {
                            bestApplication = application.id;
                            break;
                        }
                    }
                    if (bestApplication>=0) {
                        System.out.println(String.format("RELEASE %d", bestApplication));
                    } else {
                        System.out.println("WAIT");
                    }
                    break;
                default:
                    System.out.println("RANDOM");
                    break;
            }
        }
    }

    private static boolean isThereAnotherCardToAutomate(int myCardsInHand[]) {
        for (int cardType=0;cardType<9;cardType++) {
            if ((cardType==CONTINUOUS_INTEGRATION_CARD && myCardsInHand[cardType]>1)
                    || (cardType!=CONTINUOUS_INTEGRATION_CARD && myCardsInHand[cardType]>0)) {
                return true;
            }
        }
        return false;
    }
}
