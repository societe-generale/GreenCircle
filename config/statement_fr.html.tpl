<!-- LEAGUES level1 level2 level3 level4 -->
<div id="statement_back" class="statement_back" style="display: none"></div>
<div class="statement-body">

  <!-- LEAGUE ALERT -->
  <div style="color: #7cc576;
background-color: rgba(124, 197, 118,.1);
padding: 20px;
margin-right: 15px;
margin-left: 15px;
margin-bottom: 10px;
text-align: left;">
    <div style="text-align: center; margin-bottom: 6px">
      <img src="//cdn.codingame.com/smash-the-code/statement/league_wood_04.png" />
    </div>

    <!-- BEGIN level1 -->
    <p style="text-align: center; font-weight: 700; margin-bottom: 6px;">
      Ce challenge se déroule en <strong>ligues</strong>.
    </p>
    <!-- END -->
    <!-- BEGIN level2 level3 level4 -->
    <p style="text-align: center; font-weight: 700; margin-bottom: 6px;">
      <strong>Kit de Démarrage</strong>
    </p>
    <!-- END -->

    <div class="statement-league-alert-content">
      <!-- BEGIN level1 -->
      Pour ce challenge, plusieurs ligues pour le même jeu seront disponibles. Quand vous aurez prouvé votre valeur
      contre le premier Boss, vous accéderez à la ligue supérieure et débloquerez de nouveaux adversaires.<br> <br>
      <!-- END -->
      Des IAs de base sont disponibles dans le <a target="_blank" rel="nofollow noopener noreferrer"
        href="https://github.com/societe-generale/GreenCircle/tree/main/starterAIs">kit de démarrage</a>. Elles peuvent
      vous aider à appréhender votre propre IA.
    </div>
  </div>

  <div style="padding: 20px;
  margin-right: 15px;
  margin-bottom: 10px;
  text-align: left;">
    <p><em>Vidéo d'introduction par <strong>Sebastien et Loïc</strong>: <a href="https://youtu.be/********" rel="nofollow noopener noreferrer"
      target="_blank">https://youtu.be/*********</a></em></p>
  </div>


  <!-- GOAL -->
  <div class="statement-section statement-goal">
    <h2>
      <span class="icon icon-goal">&nbsp;</span>
      <span>Objectif</span>
    </h2>
    <div class="statement-goal-content">
      <span>
      Le jeu se déroule dans le service informatique d’une entreprise. La direction vient de lancer un hackathon sur le thème du Green IT.
      Deux équipes de développement sont en compétition pour la place de l'équipe la plus efficace.
      Développez et livrez vos applications avant l'équipe adverse pour maximiser vos points, mais faites attention à la dette technique et à l’aspect Green IT.
      </span>
    </div>
  </div>
  <!-- RULES -->
  <div class="statement-section statement-rules">
    <h2>
      <span class="icon icon-rules">&nbsp;</span>
      <span>Règles</span>
    </h2>

    <div class="statement-rules-content">
      <p>Chaque joueur incarne une équipe de développement informatique. Le jeu se déroule dans un open space contenant 8 postes de travail.
      Le hackathon se déroule sur plusieurs tours. À chaque tour, les deux joueurs jouent l’un après l’autre.
      </p>
      <p>Ce jeu utilise le principe du <strong>Deck Building</strong>. Chaque équipe aura un lot de cartes <strong>compétences</strong> personnelles qui s'enrichira au cours de la partie.
      <p>Chaque équipe commence la partie avec 4 cartes compétence <strong>généraliste</strong> et 4 cartes <strong>Dette Technique</strong> Ces cartes seront sa pioche personnelle et sa défausse sera recyclée pour refaire une pioche quand cette dernière sera épuisée.</p>
      <img src="https://github.fr.world.socgen/societe-generale/GreenCircle/tree/main/src/main/resources/view/assets/Tuto_DeckBuilding.png" alt="Deckbuilding: la défausse du joueur est mélangée pour créer sa pioche" />
      <br>L'équipe pourra récupérer d’autres cartes et en perdre certaines au cours de la partie.
      <h3 style="font-size: 24px; margin-top: 20px; margin-bottom: 10px; font-weight: 500; line-height: 1.1;">La zone de jeu (Open Space)
      </h3>
      <p>
        L’open space est composé de 8 postes de travail, chacun dédié à une tâche particulière
       <ul style="padding-left: 20px;padding-bottom: 0">
       <li><p>
       Formation <strong>TRAINING</strong> (0)
        </p></li>
        <li><p>
        Développement <strong>CODING</strong> (1)
        </p></li>
        <li><p>
        Point d'équipe quotidien <strong>DAILY_ROUTINE</strong> (2)
         </p></li>
        <li><p>
        Priorisation des tâches <strong>TASK_PRIORITIZATION</strong> (3)
         </p></li>
          <li><p>
         Etude d'architecture <strong>ARCHITECTURE_STUDY</strong> (4)
          </p></li>
          <li><p>
          Intégration Continue <strong>CONTINUOUS_INTEGRATION</strong> (5)
           </p></li>
          <li><p>
          Revue de code <strong>CODE_REVIEW</strong> (6)
           </p></li>
           <li><p>
           <strong>REFACTORING</strong> (7)
           </p></li></ul>
        <p>Ces postes sont numérotés de 0 à 7. Chacun de ces postes contient 5 cartes de compétence au début de la partie.
        </p><p>A cause de la covid-19, la direction a imposé un sens de déplacement dans les couloirs afin de ne pas se croiser. On doit donc toujours se déplacer dans le même sens.</p>
      <!-- BEGIN level3 -->
      <div class="statement-new-league-rule">
        <!-- END -->
        <!-- BEGIN level3 level4 -->
          Ce qui oblige à passer par le bureau des tâches administratives chaque fois qu’on passe du poste 7 au poste 0.
        <!-- END -->
        <!-- BEGIN level3 -->
      </div>
      <!-- END -->


      <h3 style="font-size: 24px; margin-top: 20px; margin-bottom: 10px; font-weight: 500; line-height: 1.1;">Les Applications
      </h3>
      <p>
        Chaque application nécessite certaines tâches pour être livrée. Les applications sont communes aux deux équipes. Une fois l'application livrée, elle n'est plus livrable par l'autre équipe. </p>
        <p>Les applications seront de plus en plus grosses au cours des ligues.</p>
        <p>Une petite application aura besoin de 3 lots de 2 tâches (par ex, 2 <strong>REFACTORING</strong>, 2 <strong>TRAINING</strong> et 2 <strong>CODING</strong>)</p>
        <p>Une grosse application aura besoin de 2 lots de 4 tâches (par ex, 4 <strong>DAILY_ROUTINE</strong> et 4 <strong>CODE_REVIEW</strong>)</p>
        <p>Chaque <strong>compétence</strong> généraliste (les cartes <strong>BONUS</strong>) permet de remplir une tâche (n’importe laquelle) de façon correcte et une tâche de façon baclée.</p>
        <p>Chaque <strong>compétence</strong> spécifique permet de remplir 2 tâches (liées à cette compétence) de façon correcte et 2 tâches (n’importe lesquelles) de façon baclée.</p>
        <p>Par ex, la compétence <strong>CODING</strong> permet de remplir 2 tâches <strong>CODING</strong>. Et à côté, elle peut aussi remplir 2 tâches <strong>TRAINING</strong> de façon baclée.</p>
        Chaque tâche baclée lors de la livraison d’une application rapporte une carte <strong>Dette Technique</strong>. Ces cartes ne servent à rien, à part à ralentir l’équipe de développement quand elles s’accumulent en encombrant la main tirée au début de chaque tour.
        <img src="https://github.fr.world.socgen/societe-generale/GreenCircle/tree/main/src/main/resources/view/assets/Tuto_Application.png" alt="Exemples de livraison d'une application avec différentes compétences" />

      <h3 style="font-size: 24px; margin-top: 20px; margin-bottom: 10px; font-weight: 500; line-height: 1.1;">Description d'un tour
      </h3>
      <p>
        A chaque tour, votre équipe récupère au hasard 4 cartes <strong>compétences</strong> parmi toutes celles qu’elle possède dans sa pioche.
      </p>
      <p>
        1.	Déplacement
        </p>
        <p>
        L’équipe commence par changer de poste de travail (afin de laisser la place à d’autres équipes).<br>
         Elle récupère alors une carte <strong>compétence</strong> correspondant au poste de travail où elle va (formation, revue de code, refactoring, développement...).<br>
          S’il n’y en a plus de disponible, elle récupère une carte compétence généraliste.
      </p>

      <!-- BEGIN level2 -->
      <div class="statement-new-league-rule">
        <!-- END -->
        <!-- BEGIN level2 level3 level4 -->
        <p>
          Si l’équipe arrive sur le poste de travail où se trouve déjà une autre équipe, ou sur un poste de travail adjacent à l’autre équipe, elle va la déranger (elle fait du bruit en travaillant).
          <br>Elle doit donc se faire accepter en cédant une <strong>compétence</strong> de son choix à l’équipe dérangée.
          <br>Si elle n’en a pas, elle récupère 2 <strong>Dettes Techniques</strong>.
        </p>
        <!-- END -->
        <!-- BEGIN level2 -->
      </div>
      <!-- END -->

      <!-- BEGIN level3 -->
      <div class="statement-new-league-rule">
        <!-- END -->
        <!-- BEGIN level3 level4 -->
        <p>
          Si l’équipe passe par le poste des <strong>tâches administratives</strong> (entre le poste 7 et le poste 0), elle perd 2 <strong>compétences</strong> de son choix.
          <br>Si elle n’en a pas assez, elle récupère une carte <strong>Dette Technique</strong> par <strong>compétence</strong> manquante.
        </p>
        <!-- END -->
        <!-- BEGIN level3 -->
      </div>
      <!-- END -->
        <p>
          2.	Faire une action
        </p>
        <!-- BEGIN level1 -->
        <p>Non applicable dans cette ligue.</p>
        <!-- END -->

      <!-- BEGIN level2 -->
      <div class="statement-new-league-rule">
        <!-- END -->
        <!-- BEGIN level2 level3 level4 -->
          Après son déplacement, l’équipe peut (si elle le désire), utiliser une de ses <strong>compétences</strong> disponibles (en main). Le jeu ne vous proposera cette phase que si elle est possible.
          Liste des compétences et leurs effets:
          <ul>
          <li>Formation <strong>TRAINING</strong> (0). L’équipe récupère 2 cartes <strong>compétences</strong> de sa pioche et peut jouer une carte de plus.</li>
          </ul>
        <!-- END -->
      <!-- BEGIN level2 -->
        </div>
      <!-- END -->
      <!-- BEGIN level3 -->
        <div class="statement-new-league-rule">
      <!-- END -->
      <!-- BEGIN level3 level4 -->
      <ul>
          <li>Développement <strong>CODING</strong> (1). L’équipe récupère 1 carte <strong>compétence</strong> de sa pioche et peut jouer deux cartes de plus.</li>
      </ul><ul>
         <li>Point d'équipe quotidien <strong>DAILY_ROUTINE</strong> (2). Cette <strong>compétence</strong> est permanente&nbsp;: une fois jouée, elle reste active jusqu’à ce que l’équipe ait livré une application.
         <br>Après son déplacement, l’équipe pourra récupérer une carte <strong>compétence</strong> d’un poste de travail éloigné de 1. L’effet peut être cumulatif.</li>
      </ul><ul>
         <li>Priorisation des tâches <strong>TASK_PRIORITIZATION</strong> (3). L’équipe se débarrasse d’une carte <strong>compétence</strong> de sa main et récupère une carte <strong>compétence</strong> disponible sur le plateau de jeu.</li>
          </ul>
      <!-- END -->
      <!-- BEGIN level3 -->
        </div>
      <!-- END -->
      <!-- BEGIN level2 -->
      <div class="statement-new-league-rule">
        <!-- END -->
        <!-- BEGIN level2 level3 level4 -->
      <ul>
          <li>Etude d'architecture <strong>ARCHITECTURE_STUDY</strong> (4). Cette <strong>compétence</strong> est permanente&nbsp;: une fois jouée, elle reste active jusqu’à ce que l’équipe ait livré une application.
          <br>L’équipe piochera une carte <strong>compétence</strong> de plus au début de son tour. L’effet peut être cumulatif.</li>
      </ul>
        <!-- END -->
        <!-- BEGIN level2 -->
          </div>
        <!-- END -->
        <!-- BEGIN level3 -->
          <div class="statement-new-league-rule">
        <!-- END -->
        <!-- BEGIN level3 level4 -->
              <ul>
  <li>Intégration Continue <strong>CONTINUOUS_INTEGRATION</strong> (5). L’équipe automatise une de ses <strong>compétences</strong> disponibles dans sa main. <br>Cette carte ne sera pas défaussée à la fin du tour (elle sera toujours disponible) et ne pourra servir que pour livrer une application.</li>
      </ul>
      <!-- END -->
      <!-- BEGIN level3 -->
        </div>
      <!-- END -->
      <!-- BEGIN level2 -->
      <div class="statement-new-league-rule">
        <!-- END -->
        <!-- BEGIN level2 level3 level4 -->
        <ul>
          <li>Revue de code <strong>CODE_REVIEW</strong> (6). L’équipe récupère 2 nouvelles <strong>compétences</strong> généralistes et les met dans sa défausse.</li>
        </ul>
        <ul>
          <li><strong>REFACTORING</strong>(7). L’équipe se débarrasse définitivement d’une carte <strong>Dette Technique</strong> de sa main.</li>
        </ul>
        <!-- END -->
        <!-- BEGIN level2 -->
      </div>
      <!-- END -->

      <p>
        3.	Livrer une application
      </p>
      <p>
        L’équipe peut (si elle le désire) utiliser ses <strong>compétences</strong> disponibles en main pour livrer une application.
         <br>Le jeu ne vous proposera cette phase que si elle est possible.
        <br>N’oubliez pas, si vous baclez des tâches, vous recevrez des cartes <strong>Dette Technique</strong> !

          <!-- BEGIN level2 -->
          <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level2 level3 level4 -->
              Après avoir livré une application, les <strong>compétences</strong> permanentes du joueur sont alors défaussées.
            <!-- END -->
            <!-- BEGIN level2 -->
          </div>
          <!-- END -->
      </p>
      <p>
        4.	Fin du tour
      </p>
      <p>
        Les <strong>compétences</strong> restant en main et celles jouées sont défaussées.
      </p>
    </div>

      <p>
        Fin du jeu
      </p>
      <p>
        Le hackathon s’arrête dès qu’une équipe a réussi à livrer 5 applications.
        </p><p>Attention, comme le hackathon est sous le thème du Green IT, les organisateurs sont très vigilants sur la qualité de la dernière application livrée.
         <br>La 5ème application d’une équipe ne pourra donc pas être livrée avec des tâches baclées !</p>
        <p>A partir du moment où une équipe a livré 5 applications, la partie s’arrête une fois que les deux équipes ont joué autant de tours.</p>
        <p>Le joueur gagnant est celui qui a livré le plus d’applications.<br>
        Si les joueurs ont livré le même nombre d’applications, le gagnant est le joueur qui possède le moins de cartes <strong>Dette Technique</strong>.
      </p>

  </div>




  <div class="statement-section statement-rules">
    <!-- Victory conditions -->
    <div class="statement-victory-conditions">
      <div class="icon victory"></div>
      <div class="blk">
        <div class="title">Conditions de Victoire</div>
        <div class="text">
          <ul style="padding-top:0; padding-bottom: 0;">
            <li>Vous avez livré 5 applications avant l'adversaire.</li>
            <li>Vous avez livré plus d'applications que votre adversaire après <strong>
                <const>200</const> phases de jeu.
              </strong>
            </li>
            <li>En cas d'égalité, vous avez moins de cartes <strong>Dette Technique</strong> que votre adversaire.
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- Lose conditions -->
    <div class="statement-lose-conditions">
      <div class="icon lose"></div>
      <div class="blk">
        <div class="title">Conditions de Défaite</div>
        <div class="text">
          <ul style="padding-top:0; padding-bottom: 0;">
            <li>Votre programme ne fournit pas d'instruction valide dans le temps imparti.</li>
          </ul>
        </div>
      </div>
    </div>
    <br>
    <h3 style="font-size: 14px;
                      font-weight: 700;
                      padding-top: 15px;
    color: #838891;
                      padding-bottom: 15px;">
      🐞 Conseils de débogage</h3>
    <ul>
      <li>Survolez un pion ou une carte pour voir davantage d'informations</li>
      <li>Ajoutez du texte à la fin d'une instruction pour afficher ce texte au-dessus de votre pion</li>
      <li>Cliquez sur la roue dentée pour afficher les options supplémentaires</li>
      <li>Utilisez le clavier pour contrôler l'action : espace pour play / pause, les flèches pour avancer pas à pas
      </li>
    </ul>
  </div>

  <!-- BEGIN level3 level4 -->
  <!-- EXPERT RULES -->
  <div class="statement-section statement-expertrules">
     <div class="statement-new-league-rule">
      <h2>
        <span class="icon icon-expertrules">&nbsp;</span>
        <span>Détails Techniques</span>
      </h2>
      <div class="statement-expert-rules-content">
        <ul style="padding-left: 20px;padding-bottom: 0">
          <li>
            <p>
              Les joueurs commencent la partie avec 4 <strong>Compétences Généralistes</strong> et 4 <strong>Dettes Techniques</strong> dans leur pioche.
            </p>
          </li>
          <li>
            <p>
              Le jeu commence avec 5 cartes <strong>compétence</strong> sur chaque poste de travail, 36 cartes de <strong>Compétences Généralistes</strong> et 100 cartes de <strong>Dette Technique</strong>.
            </p>
          </li>
          <li>
            <p>
              Chaque phase n’est proposée aux joueurs que s’ils peuvent y faire une action.
              S'ils n'ont pas le choix et que l'action est obligatoire, elle sera jouée automatiquement.
            </p>
          </li>
          <li>
            <p>
              Si vous devez piocher une carte de zone et qu'il n'y en a plus, vous prendrez une carte de <strong>compétence</strong> généraliste (BONUS) à la place.
            </p>
          </li>
          <li>
            <p>
              En cas d'égalité, le joueur qui a le moins de cartes <strong>Dette Technique</strong> gagnera.
            </p>
          </li>
          <li>
            <p>
              La 5me application ne peut pas être livrée en baclant des tâches. Il vous faudra donc le paiement exact.
            </p>
          </li>
          <li>
            <p>
              Vous pouvez voir le code source du jeu sur
              <a rel="nofollow noopener noreferrer" target="_blank"  href="https://github.com/societe-generale/GreenCircle">ce repo
                GitHub</a>.
            </p>
          </li>
        </ul>
      </div>
    </div>
  </div>
  <!-- END -->

  <!-- PROTOCOL -->
  <div class="statement-section statement-protocol">
    <h2>
      <span class="icon icon-protocol">&nbsp;</span>
      <span>Entrées / Sorties du Jeu</span>
    </h2>
    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Entrées pour un Tour de Jeu</div>
      <div class="text">
        <span class="statement-lineno">Ligne 1&nbsp;:</span> le nom <var>gamePhase</var> de la phase actuelle avec l’action attendue. Peut être MOVE,
        <!-- BEGIN level2 level3 -->
        GIVE_CARD,
        <!-- END -->
        <!-- BEGIN level3 -->
        THROW_CARD,
        <!-- END -->
        <!-- BEGIN level2 level3 -->
        PLAY_CARD,
        <!-- END -->
        RELEASE
        <br>
        <span class="statement-lineno">Ligne 2&nbsp;:</span>
        <var>applicationCount</var> pour le nombre d'applications restant à livrer.
        <br>
        <span class="statement-lineno">Les <var>applicationCount</var> lignes suivantes&nbsp;:</span>
        le mot APPLICATION suivi de 9 entiers, la description des applications à livrer et les compétences nécessaires pour les livrer (APPLICATION <var>applicationId</var> <var>trainingNeeded</var> <var>codingNeeded</var> <var>dailyRoutineNeeded</var> <var>taskPrioritizationNeeded</var> <var>architectureStudyNeeded</var> <var>continuousDeliveryNeeded</var> <var>codeReviewNeeded</var> <var>refactoringNeeded</var>).
        <br>
        <span class="statement-lineno">1 ligne par joueur&nbsp;:</span>
        4 entiers (vous êtes toujours le premier joueur):
        <ul><li><var>location</var> : le poste de travail occupé.</li></ul>
        <ul><li><var>score</var> : le nombre d’applications livrées.</li></ul>
        <ul><li><var>permanentDailyRoutineCards</var> :
        <!-- BEGIN level1 level2 -->
        non utilisé dans cette ligue.
        <!-- END -->
        <!-- BEGIN level3 level4 -->
        le nombre de cartes point d'équipe quotidien <strong>DAILY_ROUTINE</strong> (2) jouées.
        <!-- END -->
        </li></ul>
        <ul><li><var>permanentArchitectureStudyCards</var> :
        <!-- BEGIN level1 -->
        non utilisé dans cette ligue.
        <!-- END -->
        <!-- BEGIN level2 level3 level4 -->
        le nombre de cartes étude d'architecture <strong>ARCHITECTURE_STUDY</strong> (2) jouées.
        <!-- END -->
        </li></ul>
        <br>
        <span class="statement-lineno">Ligne suivante&nbsp;:</span>
        <var>cardLocationsCount</var> pour le nombre d'emplacements où on a des cartes.
        <br>
        <span class="statement-lineno">Les <var>cardLocationsCount</var> lignes suivantes&nbsp;:</span>
        le nom de l'emplacement suivi de 10 entiers, le nombre de cartes de chaque type dans cet emplacement (<var>cardLocation</var> <var>trainingCardsCount</var> <var>codingCardsCount</var> <var>dailyRoutineCardsCount</var> <var>taskPrioritizationCardsCount</var> <var>architectureStudyCardsCount</var> <var>continuousDeliveryCardsCount</var> <var>codeReviewCardsCount</var> <var>refactoringCardsCount</var> <var>bonusCardsCount</var> <var>technicalDebtCardsCount</var>).
        L'emplacement peut être HAND, DRAW, DISCARD
        <!-- BEGIN level1 level2 -->
         or OPPONENT_CARDS
         <!-- END -->
         <!-- BEGIN level3 level4 -->
         , AUTOMATED, OPPONENT_CARDS or OPPONENT_AUTOMATED
         <!-- END -->
        <br>
        <span class="statement-lineno">Ligne suivante&nbsp;:</span>
        <var>possibleMovesCount</var> pour le nombre de coups possibles à jouer.
        <br>
        <span class="statement-lineno">Les <var>possibleMovesCount</var> lignes suivantes&nbsp;:</span>
        une chaine de caractères, un coup possible.
        <br>
      </div>
    </div>
    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Sorties pour un Tour de Jeu</div>
      <div class="text">
          <span class="statement-lineno">1 ligne</span> contenant l'une des actions suivantes selon les phases de jeu&nbsp;:
          <ul><li>
              <action>RANDOM</action>&nbsp;: le joueur choisit de faire une des actions possibles au hasard.
            </li></ul>
            <ul><li>
              <action>WAIT</action>&nbsp;: le joueur choisit de ne pas faire l'action optionnelle.
            </li></ul>
            <ul><li>
              <action>MOVE</action> <var>zoneId</var>&nbsp;: le joueur se déplace sur le poste de travail donné.
              <br>Cette action est obligatoire et n'est disponible que dans la phase MOVE.
            </li></ul>
            <!-- BEGIN level3 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level3 level4 -->
            <ul><li>
              <action>MOVE</action> <var>zoneId</var> <var>cardTypeToTake</var>&nbsp;: le joueur se déplace sur le poste de travail donné et récupère la carte du type précisé.
              <br>Cette action est obligatoire et n'est disponible que dans la phase MOVE si le joueur a déjà joué des cartes DAILY_ROUTINE.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level3 -->
            </div>
            <!-- END -->
            <!-- BEGIN level2 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level2 level3 level4 -->
            <ul><li>
              <action>GIVE</action> <var>cardTypeId</var>&nbsp;: le joueur donne une carte du type donné à son adversaire.
              <br>Cette action est obligatoire et n'est disponible que dans la phase GIVE_CARD.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level2 -->
            </div>
            <!-- END -->
            <!-- BEGIN level3 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level3 level4 -->
            <ul><li>
              <action>THROW</action> <var>cardTypeId</var>&nbsp;: le joueur remet sur le plateau de jeu une carte du type donné.
              <br>Cette action est obligatoire et n'est disponible que dans la phase THROW_CARD.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level3 -->
            </div>
            <!-- END -->

            <!-- BEGIN level2 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level2 level3 level4 -->
            <ul><li>
              <action>TRAINING</action>&nbsp;: le joueur joue une carte TRAINING de sa main.
              <br>Cette action est facultative et n'est disponible que dans la phase PLAY_CARD.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level2 -->
            </div>
            <!-- END -->

            <!-- BEGIN level3 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level3 level4 -->
            <ul><li>
              <action>CODING</action>&nbsp;: le joueur joue une carte CODING de sa main.
              <br>Cette action est facultative et n'est disponible que dans la phase PLAY_CARD.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level3 -->
            </div>
            <!-- END -->

            <!-- BEGIN level3 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level3 level4 -->
            <ul><li>
              <action>DAILY_ROUTINE</action>&nbsp;: le joueur joue une carte DAILY_ROUTINE de sa main.
              <br>Cette action est facultative et n'est disponible que dans la phase PLAY_CARD.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level3 -->
            </div>
            <!-- END -->

            <!-- BEGIN level3 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level3 level4 -->
            <ul><li>
              <action>TASK_PRIORITIZATION</action> <var>cardTypeToThrow</var> <var>cardTypeToTake</var>&nbsp;: le joueur joue une carte TASK_PRIORITIZATION de sa main.
              <br>Cette action est facultative et n'est disponible que dans la phase PLAY_CARD.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level3 -->
            </div>
            <!-- END -->

            <!-- BEGIN level2 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level2 level3 level4 -->
            <ul><li>
              <action>ARCHITECTURE_STUDY</action>&nbsp;: le joueur joue une carte ARCHITECTURE_STUDY de sa main.
              <br>Cette action est facultative et n'est disponible que dans la phase PLAY_CARD.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level2 -->
            </div>
            <!-- END -->

            <!-- BEGIN level3 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level3 level4 -->
            <ul><li>
              <action>CONTINUOUS_INTEGRATION</action> <var>cardTypeToAutomate</var>&nbsp;: le joueur joue une carte CONTINUOUS_INTEGRATION de sa main.
              <br>Cette action est facultative et n'est disponible que dans la phase PLAY_CARD.
            </li></ul>
            <!-- END -->
          <!-- BEGIN level3 -->
          </div>
          <!-- END -->

            <!-- BEGIN level2 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level2 level3 level4 -->
            <ul><li>
              <action>CODE_REVIEW</action>&nbsp;: le joueur joue une carte CODE_REVIEW de sa main.
              <br>Cette action est facultative et n'est disponible que dans la phase PLAY_CARD.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level2 -->
            </div>
            <!-- END -->

            <!-- BEGIN level2 -->
            <div class="statement-new-league-rule">
            <!-- END -->
            <!-- BEGIN level2 level3 level4 -->
            <ul><li>
              <action>REFACTORING</action>&nbsp;: le joueur joue une carte REFACTORING de sa main.
              <br>Cette action est facultative et n'est disponible que dans la phase PLAY_CARD.
            </li></ul>
            <!-- END -->
            <!-- BEGIN level2 -->
            </div>
            <!-- END -->

            <ul><li>
              <action>RELEASE</action> <var>applicationId</var>&nbsp;: le joueur livre l'application donnée.
              <br>Cette action est facultative et n'est disponible que dans la phase RELEASE.
            </li>
          </ul>
        Ajoutez du texte à la fin d'une instruction pour afficher ce texte au-dessus de votre pion.
        <br><br> Exemples: <ul style="padding-top:0; padding-bottom: 0;">
          <li>
            <action>MOVE 3</action>
          </li>
          <li>
            <action>RELEASE 16</action>
          </li>
          <!-- BEGIN level2 level3 level4 -->
          <li>
            <action>GIVE 8 je fais un cadeau !</action>
          </li>
          <li>
            <action>REFACTORING adieu la dette technique !</action>
          </li>
          <!-- END -->
          <!-- BEGIN level3 level4 -->
          <li>
            <action>CONTINUOUS_INTEGRATION 1</action>
          </li>
          <li>
            <action>TASK_PRIORITIZATION 8 2</action>
          </li>
          <!-- END -->
          <li>
            <action>WAIT rien à faire...</action>
          </li>
          <li>
            <action>RANDOM je n'ai pas d'idée...</action>
          </li>
        </ul>
      </div>
    </div>
    <div class="blk">
      <div class="title">Contraintes</div>
      <div class="text">
        Temps de réponse par tour ≤ <const>30</const>ms (on économise les ressources quand on fait du Green IT)<br>
        Temps de réponse au premier tour ≤ <const>1000</const>ms
      </div>
    </div>
  </div>

  <!-- BEGIN level1 level2 -->
  <!-- LEAGUE ALERT -->
  <div style="color: #7cc576;
                      background-color: rgba(124, 197, 118,.1);
                      padding: 20px;
                      margin-top: 10px;
                      text-align: left;">
    <div style="text-align: center; margin-bottom: 6px"><img
        src="//cdn.codingame.com/smash-the-code/statement/league_wood_04.png" /></div>

    <div style="text-align: center; font-weight: 700; margin-bottom: 6px;">
      Qu'est-ce qui m'attend dans les prochaines ligues&nbsp;?
    </div>
    Les nouvelles règles débloquées dans les prochaines ligues sont&nbsp;:
    <ul>
      <!-- BEGIN level1 -->
      <li>Les joueurs pourront jouer certaines cartes</li>
      <li>Un joueur devra donner une carte à son adversaire s'il se place trop près de lui (pas des dettes techniques)</li>
      <li>La moitié des applications nécessiteront plus de ressources</li>
      <!-- END -->
      <!-- BEGIN level2 -->
      <li>Toutes les cartes seront jouables</li>
      <li>Quand il passera devant le bureau des tâches administratives, le joueur devra jeter 2 cartes (pas des dettes techniques)</li>
      <li>Toutes les applications nécessiteront plus de ressources</li>
      <!-- END -->
    </ul>
  </div>
  <!-- END -->

  <div>
  <div style="text-align: center; font-weight: 700; margin-bottom: 6px;">
        Pour en savoir plus sur le Green IT
      </div>
      <p><em>Les ambitions du Groupe Societe Generale sur le Green IT, qui devient un enjeu d’ampleur dans le contexte actuel&nbsp;:
      <a href="https://youtu.be/ZstnO7j1y4c" rel="nofollow noopener noreferrer" target="_blank">https://youtu.be/ZstnO7j1y4c</a></em></p>

      <p>Notre partenariat avec l’INR et notre signature à la Charte du Numérique responsable&nbsp;:
      <a href="https://careers.societegenerale.com/green-it-program " rel="nofollow noopener noreferrer" target="_blank">https://careers.societegenerale.com/green-it-program </a></p>

      <p>
      Les Masterclasses de nos experts en vidéos – tout savoir sur le Green IT</p>
          <p><em>Masterclass #1 Pourquoi le numérique responsable?&nbsp;: <a href="https://youtu.be/eLffG8Z0iXU" rel="nofollow noopener noreferrer"
            target="_blank">https://youtu.be/eLffG8Z0iXU</a></em></p>
          <p><em>Masterclass #2 Les enjeux de l'E-accessibilité pour l'IT&nbsp;: <a href="https://youtu.be/oRA_CrGxGgw" rel="nofollow noopener noreferrer"
            target="_blank">https://youtu.be/oRA_CrGxGgw</a></em></p>
          <p><em>Masterclass #3 Architecture sous le prisme du Green IT&nbsp;: <a href="https://youtu.be/x2fMjGqinLA" rel="nofollow noopener noreferrer"
            target="_blank">https://youtu.be/x2fMjGqinLA</a></em></p>
          <p><em>Masterclass #4 Vers une conception numérique responsable&nbsp;: <a href="https://youtu.be/kb2PM7OniRk" rel="nofollow noopener noreferrer"
            target="_blank">https://youtu.be/kb2PM7OniRk</a></em></p>

  </div>
</div>
<!-- SHOW_SAVE_PDF_BUTTON -->
