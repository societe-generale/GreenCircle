using System;
using System.Linq;
using System.IO;
using System.Text;
using System.Collections;
using System.Collections.Generic;

/**
 * Complete the hackathon before your opponent by following the principles of Green IT
 **/
class EntryPoint
{
    static void Main(string[] args)
    {
        string[] inputs;

        // game loop
        while (true)
        {
            string gamePhase = Console.ReadLine(); // can be MOVE, GIVE_CARD, THROW_CARD, PLAY_CARD or RELEASE
            var applications = new List<Application>();
            int applicationsCount = int.Parse(Console.ReadLine());
            string input;
            for (int i = 0; i < applicationsCount; i++)
            {
                input = Console.ReadLine();
                Console.Error.WriteLine(input);
                inputs = input.Split(' ');
                string objectType = inputs[0];
                int id = int.Parse(inputs[1]);
                int trainingNeeded = int.Parse(inputs[2]); // number of TRAINING skills needed to release this application
                int codingNeeded = int.Parse(inputs[3]); // number of CODING skills needed to release this application
                int dailyRoutineNeeded = int.Parse(inputs[4]); // number of DAILY_ROUTINE skills needed to release this application
                int taskPrioritizationNeeded = int.Parse(inputs[5]); // number of TASK_PRIORITIZATION skills needed to release this application
                int architectureStudyNeeded = int.Parse(inputs[6]); // number of ARCHITECTURE_STUDY skills needed to release this application
                int continuousDeliveryNeeded = int.Parse(inputs[7]); // number of CONTINUOUS_DELIVERY skills needed to release this application
                int codeReviewNeeded = int.Parse(inputs[8]); // number of CODE_REVIEW skills needed to release this application
                int refactoringNeeded = int.Parse(inputs[9]); // number of REFACTORING skills needed to release this application
                applications.Add(new Application(id, trainingNeeded, codingNeeded, dailyRoutineNeeded, taskPrioritizationNeeded, architectureStudyNeeded,
                    continuousDeliveryNeeded, codeReviewNeeded, refactoringNeeded));
            }
            Player me = null;
            for (int i = 0; i < 2; i++)
            {
                inputs = Console.ReadLine().Split(' ');
                int playerLocation = int.Parse(inputs[0]); // id of the zone in which the player is located
                int playerScore = int.Parse(inputs[1]);
                int playerPermanentDailyRoutineCards = int.Parse(inputs[2]); // number of DAILY_ROUTINE the player has played. It allows them to take cards from the adjacent zones
                int playerPermanentArchitectureStudyCards = int.Parse(inputs[3]); // number of ARCHITECTURE_STUDY the player has played. It allows them to draw more cards
                if (i==0) me = new Player(playerLocation, playerScore);
            }
            int cardLocationsCount = int.Parse(Console.ReadLine());
            for (int i = 0; i < cardLocationsCount; i++)
            {
                input = Console.ReadLine();
                Console.Error.WriteLine(input);
                inputs = input.Split(' ');
                string cardsLocation = inputs[0]; // the location of the card list. It can be HAND, DRAW, DISCARD or OPPONENT_CARDS (AUTOMATED and OPPONENT_AUTOMATED will appear in later leagues)
                int trainingCardsCount = int.Parse(inputs[1]);
                int codingCardsCount = int.Parse(inputs[2]);
                int dailyRoutineCardsCount = int.Parse(inputs[3]);
                int taskPrioritizationCardsCount = int.Parse(inputs[4]);
                int architectureStudyCardsCount = int.Parse(inputs[5]);
                int continuousDeliveryCardsCount = int.Parse(inputs[6]);
                int codeReviewCardsCount = int.Parse(inputs[7]);
                int refactoringCardsCount = int.Parse(inputs[8]);
                int bonusCardsCount = int.Parse(inputs[9]);
                int technicalDebtCardsCount = int.Parse(inputs[10]);
                if (cardsLocation=="HAND")
                    me.SetCardsInHand(trainingCardsCount, codingCardsCount, dailyRoutineCardsCount, taskPrioritizationCardsCount, architectureStudyCardsCount,
                        continuousDeliveryCardsCount, codeReviewCardsCount, refactoringCardsCount, bonusCardsCount, technicalDebtCardsCount);
                if (cardsLocation=="AUTOMATED")
                    me.SetAutomatedCards(trainingCardsCount, codingCardsCount, dailyRoutineCardsCount, taskPrioritizationCardsCount, architectureStudyCardsCount,
                        continuousDeliveryCardsCount, codeReviewCardsCount, refactoringCardsCount, bonusCardsCount, technicalDebtCardsCount);
                if (cardsLocation=="DRAW" || cardsLocation=="DISCARD")
                    me.UsefulCardsNotInHand += trainingCardsCount + codingCardsCount + dailyRoutineCardsCount + taskPrioritizationCardsCount + architectureStudyCardsCount
                        + continuousDeliveryCardsCount + codeReviewCardsCount + refactoringCardsCount + bonusCardsCount;

            }
            int possibleMovesCount = int.Parse(Console.ReadLine());
            for (int i = 0; i < possibleMovesCount; i++)
            {
                string possibleMove = Console.ReadLine();
                Console.Error.WriteLine(possibleMove);
            }

            // Write an action using Console.WriteLine()
            // To debug: Console.Error.WriteLine("Debug messages...");


            // In the first league: RANDOM | MOVE <zoneId> | RELEASE <applicationId> | WAIT; In later leagues: | GIVE <cardType> | THROW <cardType> | TRAINING | CODING | DAILY_ROUTINE | TASK_PRIORITIZATION <cardTypeToThrow> <cardTypeToTake> | ARCHITECTURE_STUDY | CONTINUOUS_DELIVERY <cardTypeToAutomate> | CODE_REVIEW | REFACTORING;
            if (gamePhase=="MOVE")
            {
                //Write your code here to move your player
                //You must move from your desk
                Console.WriteLine($"MOVE {(me.Location + 1) % 8}");//MOVE deskId
            }
            else if (gamePhase=="GIVE_CARD")
            {
                //Starting from league 2, you must give a card to the opponent if you move close to them.
                //Write your code here to give a card
                Console.WriteLine("RANDOM");//RANDOM | GIVE cardTypeId
            }
            else if (gamePhase=="THROW_CARD")
            {
                //Starting from league 3, you must throw 2 cards away every time you go through the administrative task desk.
                //Write your code here to throw a card
                Console.WriteLine("RANDOM");//RANDOM | THROW cardTypeId
            }
            else if (gamePhase=="PLAY_CARD")
            {
                //Starting from league 2, you can play some cards from your hand.
                //Write your code here to play a card
                Console.WriteLine("RANDOM");//WAIT | RANDOM | TRAINING | CODING | DAILY_ROUTINE | TASK_PRIORITIZATION <cardTypeIdToThrow> <cardTypeIdToTake> | ARCHITECTURE_STUDY | CONTINUOUS_INTEGRATION <cardTypeIdToAutomate> | CODE_REVIEW | REFACTORING
            }
            else if (gamePhase=="RELEASE")
            {
                //Write your code here to release an application
                Console.WriteLine("RANDOM");//RANDOM | WAIT | RELEASE applicationId
            }
            else
                Console.WriteLine("RANDOM");
        }
    }
}

enum CardType
{
    TRAINING=0,
    CODING,
    DAILY_ROUTINE,
    TASK_PRIORITIZATION,
    ARCHITECTURE_STUDY,
    CONTINUOUS_INTEGRATION ,
    CODE_REVIEW,
    REFACTORING,
    BONUS,
    TECHNICAL_DEBT
}

class Opponent
{
    public int Location;
    public int Score;
    public int[] Cards;
    public int[] AutomatedCards = new int[9];

    public Opponent(int location, int score)
    {
        Location = location;
        Score = score;
    }

    public void SetCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                            int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        Cards = new [] {trainingCardsCount, codingCardsCount, dailyRoutineCardsCount, taskPrioritizationCardsCount, architectureStudyCardsCount,
                        continuousDeliveryCardsCount, codeReviewCardsCount, refactoringCardsCount, bonusCardsCount, technicalDebtCardsCount};
    }

    public void SetAutomatedCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        AutomatedCards = new [] {trainingCardsCount, codingCardsCount, dailyRoutineCardsCount, taskPrioritizationCardsCount, architectureStudyCardsCount,
                        continuousDeliveryCardsCount, codeReviewCardsCount, refactoringCardsCount, bonusCardsCount, technicalDebtCardsCount};
    }
}

class Player
{
    public int Location;
    public int Score;
    public int[] AvailableCards;
    public int[] AutomatedCards = new int[9];
    public int[] CardsInDrawPile = new int[10];
    public int[] CardsInDiscardPile = new int[10];
    public int UsefulCardsNotInHand;
    public int UsefulCardsInHand;

    public Player(int location, int score)
    {
        Location = location;
        Score = score;
        UsefulCardsNotInHand = 0;
    }

    public void SetCardsInHand(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        AvailableCards = new [] {trainingCardsCount, codingCardsCount, dailyRoutineCardsCount, taskPrioritizationCardsCount, architectureStudyCardsCount,
                        continuousDeliveryCardsCount, codeReviewCardsCount, refactoringCardsCount, bonusCardsCount, technicalDebtCardsCount};
        UsefulCardsInHand = trainingCardsCount + codingCardsCount + dailyRoutineCardsCount + taskPrioritizationCardsCount + architectureStudyCardsCount
                        + continuousDeliveryCardsCount + codeReviewCardsCount + refactoringCardsCount + bonusCardsCount;
    }

    public void SetAutomatedCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        AutomatedCards = new [] {trainingCardsCount, codingCardsCount, dailyRoutineCardsCount, taskPrioritizationCardsCount, architectureStudyCardsCount,
                        continuousDeliveryCardsCount, codeReviewCardsCount, refactoringCardsCount, bonusCardsCount, technicalDebtCardsCount};
    }

    public void SetDrawPileCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        CardsInDrawPile = new [] {trainingCardsCount, codingCardsCount, dailyRoutineCardsCount, taskPrioritizationCardsCount, architectureStudyCardsCount,
                        continuousDeliveryCardsCount, codeReviewCardsCount, refactoringCardsCount, bonusCardsCount, technicalDebtCardsCount};
    }

    public void SetDiscardPileCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        CardsInDiscardPile = new [] {trainingCardsCount, codingCardsCount, dailyRoutineCardsCount, taskPrioritizationCardsCount, architectureStudyCardsCount,
                        continuousDeliveryCardsCount, codeReviewCardsCount, refactoringCardsCount, bonusCardsCount, technicalDebtCardsCount};
    }

    public int[] MissingSkillsToRelease(Application app)
    {
        var neededSkills = app.NeededSkills.ToArray();
        for(var skillId=0;skillId<8;skillId++)
        {
            neededSkills[skillId] -= 2 * AvailableCards[skillId]; //2 skills per card
            neededSkills[skillId] -= 2 * AutomatedCards[skillId]; //2 skills per card
        }
        return neededSkills;
    }

    public int MissingSkillsCountToRelease(Application app)
    {
        var missingSkills = MissingSkillsToRelease(app);
        var availableBonusSkills = AvailableCards[(int)CardType.BONUS] + AutomatedCards[(int)CardType.BONUS] ;
        var missingSkillsCount = missingSkills.Where(x => x>0).Sum(x => x) - availableBonusSkills;
        return missingSkillsCount;
    }

    public bool CanRelease(Application app)
    {
        var missingSkillsCount = MissingSkillsCountToRelease(app);
        var availableWrongSkills=0;
        for(var skillId=0;skillId<8;skillId++)
        {
            availableWrongSkills += 2 * AvailableCards[skillId]; //2 skills per card
            availableWrongSkills += 2 * AutomatedCards[skillId]; //2 skills per card
        }
        availableWrongSkills += AvailableCards[(int)CardType.BONUS];
        availableWrongSkills += AutomatedCards[(int)CardType.BONUS];
        return missingSkillsCount <= availableWrongSkills;
    }
}

class Application
{
    public int Id;
    public int[] NeededSkills;

    public Application(int id, int trainingNeeded, int codingNeeded, int dailyRoutineNeeded, int taskPrioritizationNeeded, int architectureStudyNeeded, int continuousDeliveryNeeded, int codeReviewNeeded, int refactoringNeeded)
    {
        Id = id;
        NeededSkills = new [] {trainingNeeded, codingNeeded, dailyRoutineNeeded, taskPrioritizationNeeded, architectureStudyNeeded, continuousDeliveryNeeded, codeReviewNeeded, refactoringNeeded};
        Console.Error.WriteLine(this);
    }

    public override string ToString()
    {
        var sb = new StringBuilder($"APPLICATION #{Id}:");
        for(int cardType=0; cardType<8;cardType++)
        {
            if (NeededSkills[cardType]>0)
                sb.Append($" {NeededSkills[cardType]} {Enum.GetName(typeof(CardType), cardType)}");
        }
        return sb.ToString();
    }
}