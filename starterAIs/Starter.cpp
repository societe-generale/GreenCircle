#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

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
};

string CartTypesLabels[10] = {
    "TRAINING",
    "CODING",
    "DAILY_ROUTINE",
    "TASK_PRIORITIZATION",
    "ARCHITECTURE_STUDY",
    "CONTINUOUS_INTEGRATION",
    "CODE_REVIEW",
    "REFACTORING",
    "BONUS",
    "TECHNICAL_DEBT"};

class Opponent
{
public:
    int Location;
    int Score;
    int Cards[10];
    int AutomatedCards[10];

    Opponent(int location, int score)
    {
        Location = location;
        Score = score;
    }

    void SetCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                            int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        Cards[0] = trainingCardsCount;
        Cards[1] = codingCardsCount;
        Cards[2] = dailyRoutineCardsCount;
        Cards[3] = taskPrioritizationCardsCount;
        Cards[4] = architectureStudyCardsCount;
        Cards[5] = continuousDeliveryCardsCount;
        Cards[6] = codeReviewCardsCount;
        Cards[7] = refactoringCardsCount;
        Cards[8] = bonusCardsCount;
        Cards[9] = technicalDebtCardsCount;
    }

    void SetAutomatedCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        AutomatedCards[0] = trainingCardsCount;
        AutomatedCards[1] = codingCardsCount;
        AutomatedCards[2] = dailyRoutineCardsCount;
        AutomatedCards[3] = taskPrioritizationCardsCount;
        AutomatedCards[4] = architectureStudyCardsCount;
        AutomatedCards[5] = continuousDeliveryCardsCount;
        AutomatedCards[6] = codeReviewCardsCount;
        AutomatedCards[7] = refactoringCardsCount;
        AutomatedCards[8] = bonusCardsCount;
        AutomatedCards[9] = technicalDebtCardsCount;
    }
};

class Application
{
public:
    int Id;
    int NeededSkills[8];

    Application() {}

    Application(int id, int trainingNeeded, int codingNeeded, int dailyRoutineNeeded, int taskPrioritizationNeeded, int architectureStudyNeeded, int continuousDeliveryNeeded, int codeReviewNeeded, int refactoringNeeded)
    {
        Id = id;
        NeededSkills[0] = trainingNeeded;
        NeededSkills[1] = codingNeeded;
        NeededSkills[2] = dailyRoutineNeeded;
        NeededSkills[3] = taskPrioritizationNeeded;
        NeededSkills[4] = architectureStudyNeeded;
        NeededSkills[5] = continuousDeliveryNeeded;
        NeededSkills[6] = codeReviewNeeded;
        NeededSkills[7] = refactoringNeeded;
        cerr << *this << endl;
    }
    
    friend ostream& operator<<(ostream& os, const Application& app);
};

ostream& operator<<(ostream& os, const Application& app)
{
    os << "APPLICATION #" << app.Id << ":";
    for(int cardType=0; cardType<8;cardType++)
    {
        if (app.NeededSkills[cardType] > 0)
            os << " " << app.NeededSkills[cardType] << " " << CartTypesLabels[cardType];
    }

    return os;
};

class Player
{
public:
    int Location;
    int Score;
    int AvailableCards[10];
    int AutomatedCards[10];
    int CardsInDrawPile[10];
    int CardsInDiscardPile[10];
    int UsefulCardsNotInHand;
    int UsefulCardsInHand;

    Player() {}

    Player(int location, int score)
    {
        Location = location;
        Score = score;
        UsefulCardsNotInHand = 0;
    }

    void SetCardsInHand(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        AvailableCards[0] = trainingCardsCount;
        AvailableCards[1] = codingCardsCount;
        AvailableCards[2] = dailyRoutineCardsCount;
        AvailableCards[3] = taskPrioritizationCardsCount;
        AvailableCards[4] = architectureStudyCardsCount;
        AvailableCards[5] = continuousDeliveryCardsCount;
        AvailableCards[6] = codeReviewCardsCount;
        AvailableCards[7] = refactoringCardsCount;
        AvailableCards[8] = bonusCardsCount;
        AvailableCards[9] = technicalDebtCardsCount;

        UsefulCardsInHand = trainingCardsCount + codingCardsCount + dailyRoutineCardsCount + taskPrioritizationCardsCount + architectureStudyCardsCount
                        + continuousDeliveryCardsCount + codeReviewCardsCount + refactoringCardsCount + bonusCardsCount;
    }

    void SetAutomatedCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        AutomatedCards[0] = trainingCardsCount;
        AutomatedCards[1] = codingCardsCount;
        AutomatedCards[2] = dailyRoutineCardsCount;
        AutomatedCards[3] = taskPrioritizationCardsCount;
        AutomatedCards[4] = architectureStudyCardsCount;
        AutomatedCards[5] = continuousDeliveryCardsCount;
        AutomatedCards[6] = codeReviewCardsCount;
        AutomatedCards[7] = refactoringCardsCount;
        AutomatedCards[8] = bonusCardsCount;
        AutomatedCards[9] = technicalDebtCardsCount;
    }

    void SetDrawPileCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        CardsInDrawPile[0] = trainingCardsCount;
        CardsInDrawPile[1] = codingCardsCount;
        CardsInDrawPile[2] = dailyRoutineCardsCount;
        CardsInDrawPile[3] = taskPrioritizationCardsCount;
        CardsInDrawPile[4] = architectureStudyCardsCount;
        CardsInDrawPile[5] = continuousDeliveryCardsCount;
        CardsInDrawPile[6] = codeReviewCardsCount;
        CardsInDrawPile[7] = refactoringCardsCount;
        CardsInDrawPile[8] = bonusCardsCount;
        CardsInDrawPile[9] = technicalDebtCardsCount;
    }

    void SetDiscardPileCards(int trainingCardsCount, int codingCardsCount, int dailyRoutineCardsCount, int taskPrioritizationCardsCount, int architectureStudyCardsCount,
                        int continuousDeliveryCardsCount, int codeReviewCardsCount, int refactoringCardsCount, int bonusCardsCount, int technicalDebtCardsCount)
    {
        CardsInDiscardPile[0] = trainingCardsCount;
        CardsInDiscardPile[1] = codingCardsCount;
        CardsInDiscardPile[2] = dailyRoutineCardsCount;
        CardsInDiscardPile[3] = taskPrioritizationCardsCount;
        CardsInDiscardPile[4] = architectureStudyCardsCount;
        CardsInDiscardPile[5] = continuousDeliveryCardsCount;
        CardsInDiscardPile[6] = codeReviewCardsCount;
        CardsInDiscardPile[7] = refactoringCardsCount;
        CardsInDiscardPile[8] = bonusCardsCount;
        CardsInDiscardPile[9] = technicalDebtCardsCount;
    }

    void MissingSkillsToRelease(const Application& app, int neededSkills[])
    {
        for (int skillId=0;skillId<8;skillId++)
        {
            neededSkills[skillId] = app.NeededSkills[skillId];
            neededSkills[skillId] -= 2 * AvailableCards[skillId]; //2 skills per card
            neededSkills[skillId] -= 2 * AutomatedCards[skillId]; //2 skills per card
        }
    }

    int MissingSkillsCountToRelease(Application& app)
    {
        int missingSkills[8];
        MissingSkillsToRelease(app, missingSkills);
        int availableBonusSkills = AvailableCards[CardType::BONUS] + AutomatedCards[CardType::BONUS] ;
        int missingSkillsCount = -availableBonusSkills;
        for (int i=0; i<8;++i)
        {
            if (missingSkills[i] > 0)
                missingSkillsCount += missingSkills[i];
        }
        return missingSkillsCount;
    }

    bool CanRelease(Application& app)
    {
        int missingSkillsCount = MissingSkillsCountToRelease(app);
        int availableWrongSkills=0;
        for(int skillId=0;skillId<8;skillId++)
        {
            availableWrongSkills += 2 * AvailableCards[skillId]; //2 skills per card
            availableWrongSkills += 2 * AutomatedCards[skillId]; //2 skills per card
        }
        availableWrongSkills += AvailableCards[CardType::BONUS];
        availableWrongSkills += AutomatedCards[CardType::BONUS];
        return missingSkillsCount <= availableWrongSkills;
    }
};

/**
 * Complete the hackathon before your opponent by following the principles of Green IT
 **/

int main()
{

    // game loop
    while (1) {
        string game_phase; // can be MOVE, GIVE_CARD, THROW_CARD, PLAY_CARD or RELEASE
        cin >> game_phase; cin.ignore();
        int applications_count;
        cin >> applications_count; cin.ignore();

        Application applications[applications_count];

        for (int i = 0; i < applications_count; i++) {
            string object_type;
            int id;
            int training_needed; // number of TRAINING skills needed to release this application
            int coding_needed; // number of CODING skills needed to release this application
            int daily_routine_needed; // number of DAILY_ROUTINE skills needed to release this application
            int task_prioritization_needed; // number of TASK_PRIORITIZATION skills needed to release this application
            int architecture_study_needed; // number of ARCHITECTURE_STUDY skills needed to release this application
            int continuous_delivery_needed; // number of CONTINUOUS_DELIVERY skills needed to release this application
            int code_review_needed; // number of CODE_REVIEW skills needed to release this application
            int refactoring_needed; // number of REFACTORING skills needed to release this application
            cin >> object_type >> id >> training_needed >> coding_needed >> daily_routine_needed >> task_prioritization_needed >> architecture_study_needed >> continuous_delivery_needed >> code_review_needed >> refactoring_needed; cin.ignore();

            applications[i] = Application(id, training_needed, coding_needed, daily_routine_needed, task_prioritization_needed,
                architecture_study_needed, continuous_delivery_needed, code_review_needed, refactoring_needed);
        }

        Player me;
        for (int i = 0; i < 2; i++) {
            int player_location; // id of the zone in which the player is located
            int player_score;
            int player_permanent_daily_routine_cards; // number of DAILY_ROUTINE the player has played. It allows them to take cards from the adjacent zones
            int player_permanent_architecture_study_cards; // number of ARCHITECTURE_STUDY the player has played. It allows them to draw more cards
            cin >> player_location >> player_score >> player_permanent_daily_routine_cards >> player_permanent_architecture_study_cards; cin.ignore();
        
            if (i==0)
                me = Player(player_location, player_score);
        }

        int card_locations_count;
        cin >> card_locations_count; cin.ignore();
        for (int i = 0; i < card_locations_count; i++) {
            string cards_location; // the location of the card list. It can be HAND, DRAW, DISCARD or OPPONENT_CARDS (AUTOMATED and OPPONENT_AUTOMATED will appear in later leagues)
            int training_cards_count;
            int coding_cards_count;
            int daily_routine_cards_count;
            int task_prioritization_cards_count;
            int architecture_study_cards_count;
            int continuous_delivery_cards_count;
            int code_review_cards_count;
            int refactoring_cards_count;
            int bonus_cards_count;
            int technical_debt_cards_count;
            cin >> cards_location >> training_cards_count >> coding_cards_count >> daily_routine_cards_count >> task_prioritization_cards_count >> architecture_study_cards_count >> continuous_delivery_cards_count >> code_review_cards_count >> refactoring_cards_count >> bonus_cards_count >> technical_debt_cards_count; cin.ignore();

            if (cards_location=="HAND")
                me.SetCardsInHand(training_cards_count, coding_cards_count, daily_routine_cards_count, task_prioritization_cards_count, architecture_study_cards_count,
                    continuous_delivery_cards_count, code_review_cards_count, refactoring_cards_count, bonus_cards_count, technical_debt_cards_count);
            
            else if (cards_location=="AUTOMATED")
                me.SetAutomatedCards(training_cards_count, coding_cards_count, daily_routine_cards_count, task_prioritization_cards_count, architecture_study_cards_count,
                    continuous_delivery_cards_count, code_review_cards_count, refactoring_cards_count, bonus_cards_count, technical_debt_cards_count);
            
            else if (cards_location=="DRAW" || cards_location=="DISCARD")
                me.UsefulCardsNotInHand += training_cards_count + coding_cards_count + daily_routine_cards_count + task_prioritization_cards_count + architecture_study_cards_count
                    + continuous_delivery_cards_count + code_review_cards_count + refactoring_cards_count + bonus_cards_count;
        }

        int possible_moves_count;
        cin >> possible_moves_count; cin.ignore();
        for (int i = 0; i < possible_moves_count; i++) {
            string possible_move;
            getline(cin, possible_move);
            cerr << possible_move << endl;
        }

        // Write an action using cout. DON'T FORGET THE "<< endl"
        // To debug: cerr << "Debug messages..." << endl;


        // In the first league: RANDOM | MOVE <zoneId> | RELEASE <applicationId> | WAIT; In later leagues: | GIVE <cardType> | THROW <cardType> | TRAINING | CODING | DAILY_ROUTINE | TASK_PRIORITIZATION <cardTypeToThrow> <cardTypeToTake> | ARCHITECTURE_STUDY | CONTINUOUS_DELIVERY <cardTypeToAutomate> | CODE_REVIEW | REFACTORING;
        if (game_phase=="MOVE")
        {
            //Write your code here to move your player
            //You must move from your desk
            cout << "MOVE " << (me.Location + 1) % 8 << endl;//MOVE deskId
        }
        else if (game_phase=="GIVE_CARD")
        {
            //Starting from league 2, you must give a card to the opponent if you move close to them.
            //Write your code here to give a card
            cout << "RANDOM"<< endl;//RANDOM | GIVE cardTypeId
        }
        else if (game_phase=="THROW_CARD")
        {
            //Starting from league 3, you must throw 2 cards away every time you go through the administrative task desk.
            //Write your code here to throw a card
            cout << "RANDOM"<< endl;//RANDOM | THROW cardTypeId
        }
        else if (game_phase=="PLAY_CARD")
        {
            //Starting from league 2, you can play some cards from your hand.
            //Write your code here to play a card
            cout << "RANDOM"<< endl;//WAIT | RANDOM | TRAINING | CODING | DAILY_ROUTINE | TASK_PRIORITIZATION <cardTypeIdToThrow> <cardTypeIdToTake> | ARCHITECTURE_STUDY | CONTINUOUS_INTEGRATION <cardTypeIdToAutomate> | CODE_REVIEW | REFACTORING
        }
        else if (game_phase=="RELEASE")
        {
            //Write your code here to release an application
            cout << "RANDOM"<< endl;//RANDOM | WAIT | RELEASE applicationId
        }
        else
            cout << "RANDOM"<< endl;
    }
}
