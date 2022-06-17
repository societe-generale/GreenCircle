use strict;
use warnings;
use 5.32.1;
$|=1;

# These constants represent the indices that are used in all the "resources" arrays.
use constant TRAINING => 0; # TRAINING node
use constant CODING => 1;   # CODING node
use constant DAILY => 2;    # DAILY_ROUTINE node
use constant PRIORITY => 3; # TASK_PRIORITIZATION node
use constant STUDY => 4;    # ARCHITECTURE_REVIEW node
use constant DELIVERY => 5; # CONTINUOUS_DELIVERY node
use constant REVIEW => 6;   # CODE_REVIEW node
use constant REFACTOR => 7; # REFACTORING node
use constant BONUS => 8;    # BONUS node
use constant DEBT => 9;     # TECHNICAL_DEBT node

# game loop
while (1) {
    my $tokens;
    
    chomp(my $game_phase = <>); # can be MOVE, GIVE_CARD, THROW_CARD, PLAY_CARD or RELEASE
    chomp(my $applications_count = <>);
    my @applications;
    for (0..$applications_count-1) {
        # node 0: The object type (typically APPLICATION)
        # node 1: object identifier
        # node 2: number of TRAINING skills needed to release this application
        # node 3: number of CODING skills needed to release this application
        # node 4: number of DAILY_ROUTINE skills needed to release this application
        # node 5: number of TASK_PRIORITIZATION skills needed to release this application
        # node 6: number of ARCHITECTURE_STUDY skills needed to release this application
        # node 7: number of CONTINUOUS_DELIVERY skills needed to release this application
        # node 8: number of CODE_REVIEW skills needed to release this application
        # node 9: number of REFACTORING skills needed to release this application
        
        chomp($tokens=<>);
        my @data = split / /,$tokens;
        my @resources;
        for my $i (2..9) {
            push @resources, $data[$i];
        }
        my %application = ( type => $data[0], id => $data[1], resources => \@resources );
        push @applications, \%application;
    }
    
    my @players;
    for my $i (0..1) {
        # node 0: id of the zone in which the player is located
        # node 1: current score of the player
        # node 2: number of DAILY_ROUTINE the player has played. It allows them to take cards from the adjacent zones 
        # node 3: number of ARCHITECTURE_STUDY the player has played. It allows them to draw more cards
        
        chomp($tokens=<>);
        my @data = split / /,$tokens;
        my %player = ( location => $data[0], score => $data[1], daily => $data[2], study => $data[3] );
        push @players, \%player;
    }
    
    my (@myHand, @myDraw, @myDiscard, @myAuto, @oppCards, @oppAuto);
    chomp(my $card_locations_count = <>);
    for my $i (0..$card_locations_count-1) {
        # node 0: the location of the card list. It can be HAND, DRAW, DISCARD or OPPONENT_CARDS (AUTOMATED and OPPONENT_AUTOMATED will appear in later leagues)
        # node 1: the number of TRAINING cards in this location
        # node 2: the number of CODING cards in this location
        # node 3: the number of DAILY_ROUTINE cards in this location
        # node 4: the number of TASK_PRIORITIZATION cards in this location
        # node 5: the number of ARCHITECURE_STUDY cards in this location
        # node 6: the number of CONTINUOUS_DELIVERY cards in this location
        # node 7: the number of CODE_REVIEW cards in this location
        # node 8: the number of REFACTORING cards in this location
        # node 9: the number of BONUS cards in this location
        # node 10: the number of TECHNICAL_DEBT cards in this location
        
        chomp($tokens=<>);
        my @data = split / /, $tokens;
        my @resources;
        for my $i (1..10) {
            push @resources, $data[$i];
        }
        if ($data[0] eq "HAND") {
            @myHand = @resources;
        } elsif ($data[0] eq "DRAW") {
            @myDraw = @resources;
        } elsif ($data[0] eq "DISCARD") {
            @myDiscard = @resources;
        } elsif ($data[0] eq "AUTOMATED") {
            @myAuto = @resources;
        } elsif ($data[0] eq "OPPONENT_CARDS") {
            @oppCards = @resources;
        } elsif ($data[0] eq "OPPONENT_AUTOMATED") {
            @oppAuto = @resources;
        }
    }
    
    chomp(my $possible_moves_count = <>);
    my @moves;
    for my $i (0..$possible_moves_count-1) {
        chomp(my $possible_move = <>);
        push @moves, $possible_move;
    }
    
    # In the first league: RANDOM | MOVE <zoneId> | RELEASE <applicationId> | WAIT; In later leagues: | GIVE <cardType> | THROW <cardType> | TRAINING | CODING | DAILY_ROUTINE | TASK_PRIORITIZATION <cardTypeToThrow> <cardTypeToTake> | ARCHITECTURE_STUDY | CONTINUOUS_DELIVERY <cardTypeToAutomate> | CODE_REVIEW | REFACTORING;

    if ($game_phase eq "MOVE") {
        say "RANDOM";
    } elsif ($game_phase eq "GIVE_CARD") {
        say "RANDOM";
    } elsif ($game_phase eq "THROW_CARD") {
        say "RANDOM";
    } elsif ($game_phase eq "PLAY_CARD") {
        say "RANDOM";
    } elsif ($game_phase eq "RELEASE") {
        say "RANDOM";
    }
}

