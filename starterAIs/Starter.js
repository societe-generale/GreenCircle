class Player {
    constructor(inputs) {
        this.playerLocation = parseInt(inputs[0]); // id of the zone in which the player is located
        this.playerScore = parseInt(inputs[1]);
        this.playerPermanentDailyRoutineCards = parseInt(inputs[2]); // number of DAILY_ROUTINE the player has played. It allows them to take cards from the adjacent zones
        this.playerPermanentArchitectureStudyCards = parseInt(inputs[3]);
    }
}

class Application{
    constructor(inputs) {
        this.objectType = inputs[0];
        this.id = parseInt(inputs[1]);
        this.trainingNeeded = parseInt(inputs[2]); // number of TRAINING skills needed to release this application
        this.codingNeeded = parseInt(inputs[3]); // number of CODING skills needed to release this application
        this.dailyRoutineNeeded = parseInt(inputs[4]); // number of DAILY_ROUTINE skills needed to release this application
        this.taskPrioritizationNeeded = parseInt(inputs[5]); // number of TASK_PRIORITIZATION skills needed to release this application
        this.architectureStudyNeeded = parseInt(inputs[6]); // number of ARCHITECTURE_STUDY skills needed to release this application
        this.continuousDeliveryNeeded = parseInt(inputs[7]); // number of CONTINUOUS_DELIVERY skills needed to release this application
        this.codeReviewNeeded = parseInt(inputs[8]); // number of CODE_REVIEW skills needed to release this application
        this.refactoringNeeded = parseInt(inputs[9]);
    }
}

class Card{
    constructor(inputs) {
        this.cardsLocation = inputs[0]; // the location of the card list. It can be HAND, DRAW, DISCARD or OPPONENT_CARDS (AUTOMATED and OPPONENT_AUTOMATED will appear in later leagues)
        this.trainingCardsCount = parseInt(inputs[1]);
        this.codingCardsCount = parseInt(inputs[2]);
        this.dailyRoutineCardsCount = parseInt(inputs[3]);
        this.taskPrioritizationCardsCount = parseInt(inputs[4]);
        this.architectureStudyCardsCount = parseInt(inputs[5]);
        this.continuousDeliveryCardsCount = parseInt(inputs[6]);
        this.codeReviewCardsCount = parseInt(inputs[7]);
        this.refactoringCardsCount = parseInt(inputs[8]);
        this.bonusCardsCount = parseInt(inputs[9]);
        this.technicalDebtCardsCount = parseInt(inputs[10]);
    }
}


class Move{
    constructor(input) {
        this.name = input
    }

}

let debug = true,
    _readline = () => {
        let entry = readline();
        if(debug) console.error(entry);
        return entry;
    },
    applications = [];

while (true) {
    let gamePhase = _readline(),
        nApplications = parseInt(_readline()),
        applications = [...Array(nApplications)].map(_ => new Application(_readline().split(' '))),
        players = [...Array(2)].map(_ => new Player(_readline().split(' '))),
        nCardLocations = parseInt(_readline()),
        cardsLocations = [...Array(nCardLocations)].map(_ => new Card(_readline().split(' '))),
        nPossibleMoves = parseInt(_readline()),
        possibleMoves  = [...Array(nPossibleMoves)].map(_ => new Move(_readline()));

    console.log('RANDOM');
}
