import sys
import math
from collections import namedtuple
from enum import Enum


class CardType(Enum):
    TRAINING = 0,
    CODING = 1,
    DAILY_ROUTINE = 2,
    TASK_PRIORITIZATION = 3,
    ARCHITECTURE_STUDY = 4,
    CONTINUOUS_INTEGRATION = 5,
    CODE_REVIEW = 6,
    REFACTORING = 7,
    BONUS = 8,
    TECHNICAL_DEBT = 9

# Complete the hackathon before your opponent by following the principles of Green IT


Application = namedtuple('Application', ['id', 'card_needed'])

# game loop
while True:
    game_phase = input()  # can be MOVE, GIVE_CARD, THROW_CARD, PLAY_CARD or RELEASE
    applications_count = int(input())
    applications = []
    for i in range(applications_count):
        # training_needed: number of TRAINING skills needed to release this application
        # coding_needed: number of CODING skills needed to release this application
        # daily_routine_needed: number of DAILY_ROUTINE skills needed to release this application
        # task_prioritization_needed: number of TASK_PRIORITIZATION skills needed to release this application
        # architecture_study_needed: number of ARCHITECTURE_STUDY skills needed to release this application
        # continuous_delivery_needed: number of CONTINUOUS_DELIVERY skills needed to release this application
        # code_review_needed: number of CODE_REVIEW skills needed to release this application
        # refactoring_needed: number of REFACTORING skills needed to release this application
        object_type, _id, training_needed, coding_needed, daily_routine_needed, task_prioritization_needed, architecture_study_needed, continuous_delivery_needed, code_review_needed, refactoring_needed = input().split()
        _id = int(_id)
        training_needed = int(training_needed)
        coding_needed = int(coding_needed)
        daily_routine_needed = int(daily_routine_needed)
        task_prioritization_needed = int(task_prioritization_needed)
        architecture_study_needed = int(architecture_study_needed)
        continuous_delivery_needed = int(continuous_delivery_needed)
        code_review_needed = int(code_review_needed)
        refactoring_needed = int(refactoring_needed)
        application = Application(_id, [training_needed, coding_needed, daily_routine_needed, task_prioritization_needed, architecture_study_needed, continuous_delivery_needed, code_review_needed, refactoring_needed])
        applications.append(application)
    # for i in range(2):
        # player_location: id of the zone in which the player is located
        # player_permanent_daily_routine_cards: number of DAILY_ROUTINE the player has played. It allows them to take cards from the adjacent zones
        # player_permanent_architecture_study_cards: number of ARCHITECTURE_STUDY the player has played. It allows them to draw more cards
    player_location, player_score, player_permanent_daily_routine_cards, player_permanent_architecture_study_cards = [int(j) for j in input().split()]
    other_player_location, other_player_score, other_player_permanent_daily_routine_cards, other_player_permanent_architecture_study_cards = [int(j) for j in input().split()]
    card_locations_count = int(input())
    for i in range(card_locations_count):
        # cards_location: the location of the card list. It can be HAND, DRAW, DISCARD or OPPONENT_CARDS (AUTOMATED and OPPONENT_AUTOMATED will appear in later leagues)
        cards_location, training_cards_count, coding_cards_count, daily_routine_cards_count, task_prioritization_cards_count, architecture_study_cards_count, continuous_delivery_cards_count, code_review_cards_count, refactoring_cards_count, bonus_cards_count, technical_debt_cards_count = input().split()
        training_cards_count = int(training_cards_count)
        coding_cards_count = int(coding_cards_count)
        daily_routine_cards_count = int(daily_routine_cards_count)
        task_prioritization_cards_count = int(task_prioritization_cards_count)
        architecture_study_cards_count = int(architecture_study_cards_count)
        continuous_delivery_cards_count = int(continuous_delivery_cards_count)
        code_review_cards_count = int(code_review_cards_count)
        refactoring_cards_count = int(refactoring_cards_count)
        bonus_cards_count = int(bonus_cards_count)
        technical_debt_cards_count = int(technical_debt_cards_count)
    possible_moves_count = int(input())
    possible_moves = []
    for i in range(possible_moves_count):
        possible_move = input()
        possible_moves.append(possible_move)
    print(", ".join(possible_moves), file=sys.stderr)

    # Write an action using print
    # To debug: print("Debug messages...", file=sys.stderr)

    # In the first league: RANDOM | MOVE <zoneId> | RELEASE <applicationId> | WAIT; In later leagues: | GIVE <cardType> | THROW <cardType> | TRAINING | CODING | DAILY_ROUTINE | TASK_PRIORITIZATION <cardTypeToThrow> <cardTypeToTake> | ARCHITECTURE_STUDY | CONTINUOUS_DELIVERY <cardTypeToAutomate> | CODE_REVIEW | REFACTORING;
    if game_phase == "MOVE":
        # Write your code here to move your player
        # You must move from your desk
        print(f'MOVE {(player_location + 1) % 8}')
    elif game_phase == "GIVE_CARD":
        # Starting from league 2, you must give a card to the opponent if you move close to them.
        # Write your code here to give a card
        # RANDOM | GIVE cardTypeId
        print("RANDOM")
    elif game_phase == "THROW_CARD":
        # Starting from league 3, you must throw 2 cards away every time you go through the administrative task desk.
        # Write your code here to throw a card
        # RANDOM | THROW cardTypeId
        print("RANDOM")
    elif game_phase == "PLAY_CARD":
        # Starting from league 2, you can play some cards from your hand.
        # Write your code here to play a card
        # WAIT | RANDOM | TRAINING | CODING | DAILY_ROUTINE | TASK_PRIORITIZATION <cardTypeIdToThrow> <cardTypeIdToTake> | ARCHITECTURE_STUDY | CONTINUOUS_INTEGRATION <cardTypeIdToAutomate> | CODE_REVIEW | REFACTORING
        print("RANDOM")
    elif game_phase == "RELEASE":
        # Write your code here to release an application
        # RANDOM | WAIT | RELEASE applicationId
        print("RANDOM")
    else:
        print("RANDOM")
