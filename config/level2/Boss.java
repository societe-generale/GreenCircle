import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Complete the hackathon before your opponent by following the principles of Green IT
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            String gamePhase = in.next(); // can be MOVE, GIVE_CARD, THROW_CARD, PLAY_CARD or COMPLETE_OBJECTIVE
            int objectivesCount = in.nextInt();
            for (int i = 0; i < objectivesCount; i++) {
                String objectType = in.next();
                int id = in.nextInt();
                int trainingNeeded = in.nextInt(); // number of TRAINING skills needed to complete this objective
                int codingNeeded = in.nextInt(); // number of CODING skills needed to complete this objective
                int dailyRoutineNeeded = in.nextInt(); // number of DAILY_ROUTINE skills needed to complete this objective
                int taskPrioritizationNeeded = in.nextInt(); // number of TASK_PRIORITIZATION skills needed to complete this objective
                int architectureStudyNeeded = in.nextInt(); // number of ARCHITECTURE_STUDY skills needed to complete this objective
                int continuousDeliveryNeeded = in.nextInt(); // number of CONTINUOUS_DELIVERY skills needed to complete this objective
                int codeReviewNeeded = in.nextInt(); // number of CODE_REVIEW skills needed to complete this objective
                int refactoringNeeded = in.nextInt(); // number of REFACTORING skills needed to complete this objective
            }
            for (int i = 0; i < 2; i++) {
                int playerLocation = in.nextInt(); // id of the zone in which the player is located
                int playerScore = in.nextInt();
                int playerPermanentDailyRoutineCards = in.nextInt(); // number of DAILY_ROUTINE the player has played. It allows them to take cards from the adjacent zones
                int playerPermanentArchitectureStudyCards = in.nextInt(); // number of ARCHITECTURE_STUDY the player has played. It allows them to draw more cards
            }
            int cardLocationsCount = in.nextInt();
            for (int i = 0; i < cardLocationsCount; i++) {
                String cardsLocation = in.next(); // the location of the card list. It can be HAND, DRAW, DISCARD or OPPONENT_CARDS (AUTOMATED and OPPONENT_AUTOMATED will appear in later leagues)
                int trainingCardsCount = in.nextInt();
                int codingCardsCount = in.nextInt();
                int dailyRoutineCardsCount = in.nextInt();
                int taskPrioritizationCardsCount = in.nextInt();
                int architectureStudyCardsCount = in.nextInt();
                int continuousDeliveryCardsCount = in.nextInt();
                int codeReviewCardsCount = in.nextInt();
                int refactoringCardsCount = in.nextInt();
                int bonusCardsCount = in.nextInt();
                int technicalDebtCardsCount = in.nextInt();
            }
            int possibleMovesCount = in.nextInt();
            if (in.hasNextLine()) {
                in.nextLine();
            }

            for (int i = 0; i < possibleMovesCount; i++) {
                String possibleMove = in.nextLine();
                if (i==0) {
                    System.out.println(possibleMove);
                }
            }
        }
    }
}