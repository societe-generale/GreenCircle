package com.codingame.view;

import com.codingame.game.Config;
import com.codingame.game.Game;
import com.codingame.game.Player;
import com.codingame.game.Zone;
import com.codingame.game.card.Card;
import com.codingame.game.card.CardType;
import com.codingame.game.card.TechnicalDebtCard;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.*;
import com.codingame.view.tooltip.TooltipGlobalData;
import com.codingame.view.tooltip.TooltipModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.awt.geom.Point2D;
import java.util.*;

import static java.lang.Math.min;

@Singleton
public class View {
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule gem;
    @Inject private TooltipModule tooltipModule;

    // Game zone group
    private static final int Z_LAYER_DEV = 10;
    private static final int Z_LAYER_CARD = 5;
    private static final int Z_LAYER_SCORE = 15;
    private static final int Z_LAYER_HUD = 30;

    // Dev group
    private static final int Z_LAYER_DEV_SPRITE = 0;
    private static final int Z_LAYER_DEV_ACTION = 1;
    private static final int Z_LAYER_DEV_MESSAGE = 4;
    private static final double Y_OFFSET_DEV_MESSAGE = -80;

    // Card group
    private static final int Z_LAYER_CARD_SPRITE = 1;
    private static final int Z_LAYER_CARD_PILE_SPRITE = 0;
    private static final int Z_LAYER_CARD_MESSAGE = 4;

    public static final int HUD_HEIGHT = 150;

    int cellSize = 1;
    private int screenWidth;
    private int screenHeight;
    List<BitmapText> technicalDebtLabels;
    List<PlayerMove> playerMoves;
    List<PlayerView> players;
    List<CardView> cards = new ArrayList<>();
    SpriteAnimation scores[][];
    Sprite technicalDebtBars[];
    CardView[] playerDrawPiles;
    CardView[] playerDiscardPiles;
    Sprite applicationsTooltipBox;
    Sprite[] drawPileTooltipBoxes;

    Group gameZone;
    Sprite background;
    Rectangle playerMask;

    //hardcoded locations : YOLO
    static Map<Integer, Point2D.Double> zoneCells = new HashMap<>();
    static {
        //player 0 (blue)
        zoneCells.put(-1, new Point2D.Double(1035,212)); //start zone
        zoneCells.put(0, new Point2D.Double(1537,361)); //zone 0
        zoneCells.put(1, new Point2D.Double(1598,567)); //zone 1
        zoneCells.put(2, new Point2D.Double(1485,730)); //zone 2
        zoneCells.put(3, new Point2D.Double(1280,820)); //zone 3
        zoneCells.put(4, new Point2D.Double(1043,835)); //zone 4
        zoneCells.put(5, new Point2D.Double(815,778)); //zone 5
        zoneCells.put(6, new Point2D.Double(655,648)); //zone 6
        zoneCells.put(7, new Point2D.Double(619,462)); //zone 7
        //player 1 (orange)
        zoneCells.put(8, new Point2D.Double(1176,212)); //start zone
        zoneCells.put(9, new Point2D.Double(1593,464)); //zone 0
        zoneCells.put(10, new Point2D.Double(1556,648)); //zone 1
        zoneCells.put(11, new Point2D.Double(1395,778)); //zone 2
        zoneCells.put(12, new Point2D.Double(1165,835)); //zone 3
        zoneCells.put(13, new Point2D.Double(930,820)); //zone 4
        zoneCells.put(14, new Point2D.Double(727,730)); //zone 5
        zoneCells.put(15, new Point2D.Double(612,567)); //zone 6
        zoneCells.put(16, new Point2D.Double(675,361)); //zone 7
    }

    static Point2D.Double[] drawPilesCells = new Point2D.Double[Config.ZONES_COUNT+2];
    static {
        drawPilesCells[0]= new Point2D.Double(1462,505); //zone 0
        drawPilesCells[1]= new Point2D.Double(1449,657); //zone 1
        drawPilesCells[2]= new Point2D.Double(1346,755); //zone 2
        drawPilesCells[3]= new Point2D.Double(1184,807); //zone 3
        drawPilesCells[4]= new Point2D.Double(1025,807); //zone 4
        drawPilesCells[5]= new Point2D.Double(873,755); //zone 5
        drawPilesCells[6]= new Point2D.Double(765,657); //zone 6
        drawPilesCells[7]= new Point2D.Double(757,505); //zone 7
        drawPilesCells[8]= new Point2D.Double(986,579); //bonus
        drawPilesCells[9]= new Point2D.Double(1236,598); //technical debt
    }

    static double[] drawPilesOrientations = new double[Config.ZONES_COUNT+2]; //in radians
    static {
        drawPilesOrientations[0] = 5.9;
        drawPilesOrientations[1] = 0.5;
        drawPilesOrientations[2] = 1.2;
        drawPilesOrientations[3] = 1.5;
        drawPilesOrientations[4] = 4.9;
        drawPilesOrientations[5] = 5.3;
        drawPilesOrientations[6] = 5.9;
        drawPilesOrientations[7] = 0.5;
        drawPilesOrientations[8] = -0.2;
        drawPilesOrientations[9] = 0.3;
    }

    static Point2D.Double[] playerDrawPilesCells = new Point2D.Double[2]; //hardcoded number of players!
    static {
        playerDrawPilesCells[0] = new Point2D.Double(50,150);
        playerDrawPilesCells[1] = new Point2D.Double(2170,150);
    }

    static Point2D.Double[] playerDiscardPilesCells = new Point2D.Double[2]; //hardcoded number of players!
    static {
        playerDiscardPilesCells[0] = new Point2D.Double(50,270);
        playerDiscardPilesCells[1] = new Point2D.Double(2170,270);
    }

    private Point2D.Double getPlayerHandCardLocation(Player player, int locationId) {
        int playerId = player.getIndex();
        int coefMirror = playerId == 0 ? -1 : 1;
        int cardWidth = 85;
        int playerHandCardOffset = 950;
        int playerHudZoneWidth = 2217 / (gameManager.getPlayerCount());//screen=2217
        double x = playerHudZoneWidth + coefMirror * (playerHandCardOffset - locationId * cardWidth);
        return new Point2D.Double(x, 150);
    }

    static Point2D.Double[] scoreLocations = new Point2D.Double[2];
    static {
        //player 0
        scoreLocations[0] = new Point2D.Double(566,57);
        //player 1
        scoreLocations[1] = new Point2D.Double(1361,57);
    }

    private Point2D.Double getCoordinates(int playerIndex, int zoneId)
    {
        return zoneCells.get((Config.ZONES_COUNT+1) * playerIndex + zoneId);
    }

    private Point2D.Double getCoordinates(Player player)
    {
        return zoneCells.get((Config.ZONES_COUNT+1) * player.getIndex() + player.getZoneId());
    }

    private static double fitAspectRatioContains(int srcWidth, int srcHeight, int maxWidth, int maxHeight) {
        return min((double) maxWidth / srcWidth, (double) maxHeight / srcHeight);
    }

    public void init() {
        background = gem.createSprite().setImage("Background_V2.jpg");
        background.setScale(0.5);
        gameZone = gem.createGroup();
        playerMoves = new ArrayList<>();
        screenWidth = gem.getWorld().getWidth();
        screenHeight = gem.getWorld().getHeight();
        int gameZoneWidth = 2220;
        int gameZoneHeight = 1080;

        double coefficient = fitAspectRatioContains(gameZoneWidth, gameZoneHeight, screenWidth, screenHeight);
        gameZone.setScale(coefficient);

        center(gameZone, gameZoneWidth * coefficient, gameZoneHeight * coefficient, screenWidth, screenHeight);
        //gameZone.setY(gameZone.getY() + HUD_HEIGHT / 2);

        TooltipGlobalData tooltipData = new TooltipGlobalData(
                gameZoneWidth, gameZoneHeight, gameZone.getX(), gameZone.getY(), cellSize, coefficient
        );
        tooltipModule.init(tooltipData);

        playerMask = gem.createRectangle()
                .setWidth(gameZoneWidth)
                .setHeight(gameZoneHeight)
                .setZIndex(20);
        gameZone.add(playerMask);

        initTooltips();
        initPlayers();
        initCards();
        initHud();
        endOfTurn();
    }

    private void initTooltips() {
        applicationsTooltipBox = gem.createSprite()
                .setImage("invisible.png")
                .setAnchor(0.5)
                .setVisible(true)
                .setX(1118)
                .setY(110)
                .setScaleX(0.5)
                .setScaleY(0.5)
                .setZIndex(-1);
        gameZone.add(applicationsTooltipBox);
        drawPileTooltipBoxes = new Sprite[Config.ZONES_COUNT+2];
        for (int i=0;i<Config.ZONES_COUNT+2;i++) {
            Sprite drawPileTooltip = gem.createSprite()
                    .setImage("invisible.png")
                    .setAnchor(0.5)
                    .setVisible(true)
                    .setScaleX(0.25)
                    .setScaleY(0.25)
                    .setZIndex(-1);
            gameZone.add(drawPileTooltip);
            setToGridCenterCoordinates(drawPileTooltip, drawPilesCells[i]);
            drawPileTooltipBoxes[i] = drawPileTooltip;
        }
    }

    private void initPlayers() {
        players = new ArrayList<>(gameManager.getPlayerCount());

        for (Player playerModel : gameManager.getActivePlayers()) {

            PlayerView player = createPlayerGroup(playerModel);

            setToGridCenterCoordinates(player.group, getCoordinates(playerModel));
            gameZone.add(player.group);
            players.add(player);

            //TODO: toggleModule.displayOnToggleState(boat.message, "messageToggle", true);

            //updateTooltipText(player);
        }
    }

    private void updateTooltipText(CardView card) {
        tooltipModule.setTooltipText(
                card.sprite,
                card.getCardModel().getTooltipText()
        );
    }

    private void updateTooltipText(PlayerView player) {
        tooltipModule.setTooltipText(
                player.sprite,
                player.getPlayerModel().getTooltipText()
        );
    }

    private void initCards() {
        for (CardView card : cards) {
            if (card.getCardModel().isActionCard()) {
                int zoneId = card.getCardModel().getCardType().ordinal();
                card.setViewState(CardType.values()[zoneId], true);
                card.sprite.setRotation(drawPilesOrientations[zoneId]);
                setToGridCenterCoordinates(card.group, drawPilesCells[zoneId]);
            }
            else if (card.getCardModel().isGoodActionBonus()) {
                card.setViewState(CardType.BONUS, true);
                card.sprite.setRotation(drawPilesOrientations[card.getCardModel().getCardType().ordinal()]);
                setToGridCenterCoordinates(card.group, drawPilesCells[card.getCardModel().getCardType().ordinal()]);
            }
            else {
                card.setViewState(CardType.TECHNICAL_DEBT, true);
                card.sprite.setRotation(drawPilesOrientations[card.getCardModel().getCardType().ordinal()]);
                setToGridCenterCoordinates(card.group, drawPilesCells[card.getCardModel().getCardType().ordinal()]);
            }

            gameZone.add(card.group);
        }

        //players
        playerDrawPiles = new CardView[2];
        playerDiscardPiles = new CardView[2];
        for (int playerId=0;playerId<2;++playerId) {
            Player player = gameManager.getPlayer(playerId);
            for (Card cardModel : player.getDrawPile()) {
                CardView card = cards.get(cardModel.getId());
                card.setViewState(cardModel.getCardType(), false);
                card.sprite.setRotation(0);
                setToGridCenterCoordinates(card.group, playerDrawPilesCells[playerId]);
            }
            int locationId=0;
            for (Card cardModel : player.getCardsInHand()) {
                CardView card = cards.get(cardModel.getId());
                setToGridCenterCoordinates(card.group, getPlayerHandCardLocation(player, locationId));
                card.setViewState(cardModel.getCardType(), true, true);
                card.sprite.setZIndex(Z_LAYER_CARD_SPRITE);
                card.sprite.setRotation(0);
                locationId++;
            }

            CardView drawPile = createCardGroup(new TechnicalDebtCard(-1));
            gameZone.add(drawPile.group);
            drawPile.setViewState(CardType.TECHNICAL_DEBT, false);
            setToGridCenterCoordinates(drawPile.group, playerDrawPilesCells[playerId]);
            playerDrawPiles[playerId] = drawPile;

            CardView discardPile = createCardGroup(new TechnicalDebtCard(-2));
            gameZone.add(discardPile.group);
            discardPile.setViewState(CardType.TECHNICAL_DEBT, false);
            setToGridCenterCoordinates(discardPile.group, playerDiscardPilesCells[playerId]);
            playerDiscardPiles[playerId] = discardPile;
        }
    }

    public void refreshPlayersTooltips(Game game) {
        for (PlayerView player : players) {
            updateTooltipText(player);
        }
    }

    public void refreshPlayerHandCards(Player player) {
        int locationId=0;
        for (Card cardModel : player.getCardsInHand()) {
            CardView card = cards.get(cardModel.getId());
            setToGridCenterCoordinates(card.group, getPlayerHandCardLocation(player,locationId));
            card.setViewState(cardModel.getCardType(), true, true);
            card.sprite.setRotation(0);
            card.sprite.setZIndex(Z_LAYER_CARD_SPRITE);
            card.sprite.setVisible(true);
            locationId++;
        }
    }

    public void refreshApplications(Game game) {
        tooltipModule.setTooltipText(applicationsTooltipBox, game.getApplicationsTooltip());
    }

    public void refreshCards(Game game) {
        //players
        for (int playerId=0;playerId<2;++playerId) {
            Player player = gameManager.getPlayer(playerId);
            for (Card cardModel : player.getDrawPile()) {
                CardView card = cards.get(cardModel.getId());
                card.setViewState(cardModel.getCardType(), false);
                card.sprite.setVisible(false);
                card.sprite.setRotation(0);
                setToGridCenterCoordinates(card.group, playerDrawPilesCells[playerId]);
            }
            tooltipModule.setTooltipText(playerDrawPiles[playerId].sprite, player.getDrawPileTooltipText());

            refreshPlayerHandCards(player);
            for (Card cardModel : player.getDiscardPile()) {
                CardView card = cards.get(cardModel.getId());
                card.sprite.setVisible(false);
                card.setViewState(cardModel.getCardType(), true);
                card.sprite.setRotation(0);
                setToGridCenterCoordinates(card.group, playerDiscardPilesCells[playerId]);
            }
            for (Card cardModel : player.getPlayedCards()) {
                CardView card = cards.get(cardModel.getId());
                card.sprite.setVisible(false);
                card.setViewState(cardModel.getCardType(), true);
                card.sprite.setRotation(0);
                setToGridCenterCoordinates(card.group, playerDiscardPilesCells[playerId]);
            }

            tooltipModule.setTooltipText(playerDiscardPiles[playerId].sprite, player.getDiscardPileTooltipText());

            for (Card cardModel : player.getAutomatedCards()) {
                CardView card = cards.get(cardModel.getId());
                card.sprite.setVisible(false);
            }
            for (Card cardModel : player.getPermanentSkillCards()) {
                CardView card = cards.get(cardModel.getId());
                card.sprite.setVisible(false);
            }
        }

        //zones draw piles
        for(Zone zone : game.getZones()) {
            for (Card cardModel : zone.getCards()) {
                CardView card = cards.get(cardModel.getId());
                card.setViewState(CardType.values()[zone.getId()], true);
                card.sprite.setRotation(drawPilesOrientations[zone.getId()]);
                setToGridCenterCoordinates(card.group, drawPilesCells[zone.getId()]);
                card.sprite.setVisible(false);
            }
            tooltipModule.setTooltipText(drawPileTooltipBoxes[zone.getId()], String.format("%d cards left", zone.getCardsCount()));
        }

        //technical debt cards pool
        for (Card cardModel : game.getTechnicalDebtCardsPool()) {
            CardView card = cards.get(cardModel.getId());
            card.setViewState(cardModel.getCardType(), true);
            card.sprite.setRotation(drawPilesOrientations[cardModel.getCardType().ordinal()]);
            setToGridCenterCoordinates(card.group, drawPilesCells[cardModel.getCardType().ordinal()]);
            card.sprite.setVisible(false);
        }
        tooltipModule.setTooltipText(drawPileTooltipBoxes[CardType.TECHNICAL_DEBT.ordinal()], String.format("%d cards left", game.getTechnicalDebtCardsPool().size()));

        //bonus cards pool
        for (Card cardModel : game.getBonusCardsPool()) {
            CardView card = cards.get(cardModel.getId());
            card.setViewState(cardModel.getCardType(), true);
            card.sprite.setRotation(drawPilesOrientations[cardModel.getCardType().ordinal()]);
            setToGridCenterCoordinates(card.group, drawPilesCells[cardModel.getCardType().ordinal()]);
            card.sprite.setVisible(false);
        }
        tooltipModule.setTooltipText(drawPileTooltipBoxes[CardType.BONUS.ordinal()], String.format("%d cards left", game.getBonusCardsPool().size()));

        for (CardView card : cards) {
            updateTooltipText(card);
        }
    }

    private CardView createCardGroup(Card cardModel) {
        CardView card = new CardView(cardModel);

        card.sprite = gem.createSpriteAnimation()
                .setDuration(gameManager.getFrameDuration())
                .setLoop(true)
                .setVisible(true)
                .setPlaying(false)
                .setAnchor(0.5)
                .setMask(playerMask)
                .setZIndex(Z_LAYER_CARD_SPRITE);

        card.message = gem.createText()
                .setFontSize(30)
                .setFontFamily("Arial")
                .setAnchor(0.5)
                .setFillColor(2)
                .setMaxWidth(136)
                .setStrokeThickness(4)
                .setZIndex(Z_LAYER_CARD_MESSAGE)
                .setVisible(true);

        card.group = gem.createGroup(card.sprite)
                .setZIndex(Z_LAYER_CARD);

        Group baseScaleWrapper = wrapGroupAround(card.sprite).setScale(0.25);
        card.rotationWrapper = wrapGroupAround(card.sprite);
        card.setViewState(CardType.TRAINING,false); //init with a picture
        gem.commitEntityState(0, baseScaleWrapper);

        return card;
    }

    private PlayerView createPlayerGroup(Player playerModel) {
        PlayerView player = new PlayerView(playerModel);
        player.sprite = gem.createSpriteAnimation()
                .setDuration(gameManager.getFrameDuration())
                .setVisible(true)
                .setPlaying(false)
                .setAnchor(0.5)
                .setMask(playerMask)
                .setZIndex(Z_LAYER_DEV_SPRITE);

        player.badAction = gem.createSpriteAnimation()
                .setImages(getActionImages(player.getColor(), "Bad", 27))
                .setLoop(false)
                .setVisible(false)
                .setPlaying(false)
                .setZIndex(Z_LAYER_DEV_ACTION)
                .setAnchor(0.5);

        player.goodAction = gem.createSpriteAnimation()
                .setImages(getActionImages(player.getColor(), "Good", 31))
                .setLoop(false)
                .setVisible(false)
                .setPlaying(false)
                .setZIndex(Z_LAYER_DEV_ACTION)
                .setAnchor(0.5);

        player.message = gem.createText()
                .setFontSize(30)
                .setFontFamily("Arial")
                .setAnchor(0.5)
                .setFillColor(playerModel.getColorToken())
                .setMaxWidth(136)
                .setStrokeThickness(4)
                .setZIndex(Z_LAYER_DEV_MESSAGE)
                .setVisible(true);

        setToAbsoluteCenterWithOffset(player.message, getCoordinates(playerModel), 0, Y_OFFSET_DEV_MESSAGE);

        player.group = gem.createGroup(player.sprite, player.badAction, player.goodAction)
                .setZIndex(Z_LAYER_DEV);

        Group baseScaleWrapper = wrapGroupAround(player.sprite).setScale(1);

        player.setViewState(playerModel.getIndex());

        gem.commitEntityState(0, baseScaleWrapper);

        return player;
    }

    private Group wrapGroupAround(Entity<?> entity) {
        Group wrapper = gem.createGroup();
        Optional<ContainerBasedEntity<?>> parent = entity.getParent();
        parent.ifPresent(p -> {
            p.remove(entity);
        });
        wrapper.add(entity);
        parent.ifPresent(p -> {
            p.add(wrapper);
        });
        return wrapper;
    }

    private void setToAbsoluteCenterWithOffset(Entity<?> entity, Point2D.Double position, double offsetX, double offsetY) {
        entity.setX((int) ((position.x + offsetX + 0.5) * (gameZone.getScaleX() * cellSize) + gameZone.getX()));
        entity.setY((int) ((position.y + offsetY + 0.5) * (gameZone.getScaleY() * cellSize) + gameZone.getY()));
    }

    private void setToGridCenterCoordinates(Entity<?> entity, Point2D.Double position) {
        entity
                .setX((int) (position.getX() * cellSize + cellSize / 2))
                .setY((int) (position.getY() * cellSize + cellSize / 2));
    }

    //header: player (name + avatar) & score
    private void initHud() {
        int hudWidth = 616;//1233/2

        Group hudGroup = gem.createGroup()
                .setZIndex(Z_LAYER_HUD);

        Sprite hudLeft = gem.createSprite()
                .setImage("HUD_Masque_BLUE")
                .setZIndex(1);

        Sprite hudRight = gem.createSprite()
                .setImage("HUD_Masque_ORANGE")
                .setX(screenWidth - hudWidth)
                .setZIndex(1);

        hudGroup.add(hudLeft, hudRight);

        technicalDebtLabels = new ArrayList<>(gameManager.getPlayerCount());

        int playerHudZoneWidth = screenWidth / (gameManager.getPlayerCount());
        int avatarSize = 110;//205/2, 103 a bit too low
        int playerHudNameOffset = 650;
        int playerHudScoreOffset = 704;
        int playerHudAvatarOffset = 897;
        int playerHudTechnicalDebtBarOffset = 704+120;//704 pour tout remplir. 904 pas trop visible

        Sprite logo = gem.createSprite()
                .setImage("logo_jeu.png")
                .setAnchor(0.5)
                .setX(playerHudZoneWidth)
                .setY(50)
                .setScale(0.15)
                .setZIndex(0);
        hudGroup.add(logo);

        scores = new SpriteAnimation[2][5];
        technicalDebtBars = new Sprite[2];

        for (Player p : gameManager.getPlayers()) {

            int coefMirror = p.getIndex() == 0 ? -1 : 1;

            BitmapText nameLabel = gem.createBitmapText()
                    .setFont("BRLNS_66")
                    .setFontSize(36)
                    .setText(p.getNicknameToken())
                    .setMaxWidth(300)
                    .setAnchorX(0.5)
                    .setX(playerHudZoneWidth + coefMirror * playerHudNameOffset)
                    .setY(7)
                    .setZIndex(2);

            BitmapText technicalDebtLabel = gem.createBitmapText()
                    .setFont("BRLNS_66")
                    .setFontSize(20)
                    .setText("Technical Debt: 4")
                    .setAnchorX(0.5)
                    .setX(playerHudZoneWidth + coefMirror * playerHudScoreOffset)
                    .setY(60)//70 too low
                    .setZIndex(2);

            Sprite avatar = gem.createSprite()
                    .setImage(p.getAvatarToken())
                    .setAnchor(0.5)
                    .setX(playerHudZoneWidth + coefMirror * playerHudAvatarOffset)
                    .setY(60)//70: picture is too low. 50 seems too high
                    .setBaseHeight(avatarSize)
                    .setBaseWidth(avatarSize)
                    .setZIndex(0);

            hudGroup.add(nameLabel, technicalDebtLabel, avatar);
            technicalDebtLabels.add(technicalDebtLabel);

            Sprite technicalDebt = gem.createSprite()
                    .setImage("Jauge_Bad.png")
                    .setAnchor(0.5)
                    .setVisible(true)
                    .setX(playerHudZoneWidth + coefMirror * playerHudTechnicalDebtBarOffset)
                    .setY(93)//92 ou 93 pas mal
                    .setScaleX(0.2)
                    .setScaleY(0.5)
                    .setZIndex(-1);
            technicalDebtBars[p.getIndex()] = technicalDebt;
            adaptTechnicalDebtBar(p, 0);

            hudGroup.add(technicalDebt);

            for (int score=0; score<5;++score) {
                SpriteAnimation scoreView = gem.createSpriteAnimation()
                        .setDuration(gameManager.getFrameDuration())
                        .setImages(getScoreImages(score))
                        .setAnchor(0.5)
                        .setLoop(false)
                        .setPlaying(false)
                        .setVisible(false)
                        .setZIndex(3);

                setToGridCenterCoordinates(scoreView, scoreLocations[p.getIndex()]);

                gem.commitEntityState(0, scoreView);

                hudGroup.add(scoreView);
                scores[p.getIndex()][score] = scoreView;
            }
        }
    }

    public void adaptTechnicalDebtBar(Player player, int time) {
        BitmapText label = technicalDebtLabels.get(player.getIndex());
        label.setText(String.format("Technical Debt: %d", player.getTechnicalDebtCardsCount()));
        gem.commitEntityState(time, label);

        int technicalDebtValue = min(player.getTechnicalDebtCardsCount(), 20);
        int playerHudZoneWidth = screenWidth / (gameManager.getPlayerCount());
        int coefMirror = player.getIndex() == 0 ? -1 : 1;
        Sprite bar = technicalDebtBars[player.getIndex()];
        double width=493;
        int leftPartX = 827;
        int basicOffset = 704; //704=in the middle. So the left part is at 704 + 493/2(coz it's the middle)/2(coz it's full at scale 0.5 so 827
        double scale = technicalDebtValue * 0.5 / 20; //[0.0 = empty= 0 debt, 0.5=full=20 debt]
        int offset = (int)(leftPartX - scale*width/2);
        bar.setScaleX(scale)
                .setX(playerHudZoneWidth + coefMirror * offset);

        gem.commitEntityState(time, bar);
    }

    private String[] getScoreImages(int score) {
        List<String> scoreImages = new ArrayList<>();
        for (int i=1;i<=31;++i){
            scoreImages.add(String.format("Objectif_%d%04d.png", score + 1, i));
        }
        return scoreImages.toArray(new String[0]);
    }

    private String[] getActionImages(String playerColor, String actionType, int maxImages) {
        List<String> scoreImages = new ArrayList<>();
        for (int i=1;i<=maxImages;++i){
            scoreImages.add(String.format("%s_%s%04d.png", playerColor, actionType, i));
        }
        return scoreImages.toArray(new String[0]);
    }

    public void animateScore(Player player, int score) {
        double switchFXStart = 2 / 35d;
        double switchFXDuration = 31 / 35d;
        SpriteAnimation scoreToDisplay = scores[player.getIndex()][score-1];
        gem.commitEntityState(0, scoreToDisplay);
        scoreToDisplay.reset()
                .setDuration((int) (gameManager.getFrameDuration() * switchFXDuration))
                .setVisible(true)
                .play();
        gem.commitEntityState(switchFXStart, scoreToDisplay);
    }

    public void animateGoodRelease(Player playerModel) {
        double switchFXStart = 2 / 35d;
        double switchFXDuration = 31 / 35d;
        SpriteAnimation release = players.get(playerModel.getIndex()).goodAction;
        SpriteAnimation meeple = players.get(playerModel.getIndex()).sprite;
        animateRelease(switchFXStart, switchFXDuration, release, meeple);
    }

    public void animateBadRelease(Player playerModel) {
        double switchFXStart = 2 / 35d;
        double switchFXDuration = 27 / 35d;
        SpriteAnimation release = players.get(playerModel.getIndex()).badAction;
        SpriteAnimation meeple = players.get(playerModel.getIndex()).sprite;
        animateRelease(switchFXStart, switchFXDuration, release, meeple);
    }

    private void animateRelease(double switchFXStart, double switchFXDuration, SpriteAnimation release, SpriteAnimation meeple) {
        gem.commitEntityState(0, release);
        gem.commitEntityState(0, meeple);
        meeple.setVisible(false);
        release.reset()
                .setDuration((int) (gameManager.getFrameDuration() * switchFXDuration))
                .setVisible(true)
                .play();
        gem.commitEntityState(switchFXStart, release);
        gem.commitEntityState(switchFXStart, meeple);
        release.setVisible(false);
        meeple.setVisible(true);
        gem.commitEntityState(switchFXStart+switchFXDuration, release);
        gem.commitEntityState(switchFXStart+switchFXDuration, meeple);
    }

    private void center(Entity<?> entity, double entityWidth, double entityHeight, int containerWidth, int containerHeight) {
        int x = (int) (containerWidth / 2 - (double) entityWidth / 2);
        int y = (int) (containerHeight / 2 - (double) entityHeight / 2);
        entity
                .setX(x)
                .setY(y);
    }

    public void startOfTurn() {
        playerMoves.clear();
    }

    public void endOfTurn() {
        performMoves();
    }

    public void movePlayer(Player playerModel, int zoneFrom, int zoneTo) {
        playerMoves.add(new PlayerMove(playerModel, zoneFrom, zoneTo));
    }

    private void performMoves() {
        playerMoves.stream().forEach((move) -> animateMove(move.playerModel, move.zoneFrom, move.zoneTo));
    }

    private void animateMove(Player playerModel, int zoneFrom, int zoneTo) {
        List<Integer> zonesList = new ArrayList<>();
        zonesList.add(zoneFrom);
        while  (zoneFrom != zoneTo) {
            int nextZone = zoneFrom+1;
            if (nextZone>=Config.ZONES_COUNT) {
                nextZone = -1; //administrative tasks
            }
            zonesList.add(nextZone);
            zoneFrom = nextZone;
        }

        PlayerView player = players.get(playerModel.getIndex());
        PlayerView opponent = players.get(1-playerModel.getIndex());
        double time = 0;
        double step = 1d / (double)(zonesList.size()+2);
        gem.commitEntityState(time, player.group);
        gem.commitEntityState(time, player.message);
        gem.commitEntityState(time, opponent.group);
        for (int i=1;i< zonesList.size();++i) {
            time += step;
            animateMoveOneZone(player, opponent, zonesList.get(i-1), zonesList.get(i));
            gem.commitEntityState(time, player.group);
            gem.commitEntityState(time, player.message);
            gem.commitEntityState(time, opponent.group);
        }
    }

    private void animateMoveOneZone(PlayerView player, PlayerView opponent, int zoneFrom, int zoneTo) {
        //set correct Z to have the correct meeple in front of the other depending of the viewer depth perception
        int playerIndex = player.model.getIndex();
        int opponentZone = opponent.getPlayerModel().getZoneId();
        if ((playerIndex==0 && zoneTo==1 && opponentZone==0)
            || (playerIndex==0 && zoneTo==2 && opponentZone==1)
            || (playerIndex==0 && zoneTo==6 && opponentZone==6)
            || (playerIndex==0 && zoneTo==7 && opponentZone==7)
            || (playerIndex==1 && zoneTo==0 && opponentZone==0)
            || (playerIndex==1 && zoneTo==1 && opponentZone==1)
            || (playerIndex==1 && zoneTo==5 && opponentZone==6)
            || (playerIndex==1 && zoneTo==6 && opponentZone==7)) {
            //put player in front
            player.group.setZIndex(Z_LAYER_DEV+1);
            opponent.group.setZIndex(Z_LAYER_DEV);
        }
        else if ((playerIndex==0 && zoneTo==0 && opponentZone==0)
            || (playerIndex==0 && zoneTo==1 && opponentZone==1)
            || (playerIndex==0 && zoneTo==6 && opponentZone==5)
            || (playerIndex==0 && zoneTo==7 && opponentZone==6)
            || (playerIndex==1 && zoneTo==0 && opponentZone==1)
            || (playerIndex==1 && zoneTo==1 && opponentZone==2)
            || (playerIndex==1 && zoneTo==6 && opponentZone==6)
            || (playerIndex==1 && zoneTo==7 && opponentZone==7)) {
            //put opponent in front
            player.group.setZIndex(Z_LAYER_DEV);
            opponent.group.setZIndex(Z_LAYER_DEV + 1);
        }


        Point2D.Double from = getCoordinates(playerIndex,zoneFrom);
        Point2D.Double to = getCoordinates(playerIndex,zoneTo);
        translatePlayer(player, from, to);
    }

    private void translatePlayer(
            PlayerView player, Point2D.Double from, Point2D.Double to) {
        setToAbsoluteCenterWithOffset(player.message, from, 0, Y_OFFSET_DEV_MESSAGE);
        setToGridCenterCoordinates(player.group, from);

        setToAbsoluteCenterWithOffset(player.message, to, 0, Y_OFFSET_DEV_MESSAGE);
        setToGridCenterCoordinates(player.group, to);
    }

    public void addCard(Card cardModel) {
        CardView card = createCardGroup(cardModel);
        cards.add(card);
    }

    public void setPlayerMessage(Player playerModel) {
        PlayerView player = players.get(playerModel.getIndex());
        if (playerModel.getMessage() != null) {
            player.message.setText(playerModel.getMessage());
            player.message.setVisible(true);
        } else {
            player.message.setVisible(false);
        }
        gem.commitEntityState(0, player.message);
    }

    public void playerTakesCardInHand(Card cardTaken) {
        CardView card = cards.get(cardTaken.getId());
        int zoneId = cardTaken.getCardType().ordinal();
        setToGridCenterCoordinates(card.group, drawPilesCells[zoneId]);
        card.sprite.setRotation(drawPilesOrientations[zoneId]);
        card.sprite.setVisible(true);
        gem.commitEntityState(0.1, card.sprite);
    }

    public void playerThrowsCard(double startTime, Card cardThrown) {
        CardView card = cards.get(cardThrown.getId());
        gem.commitEntityState(startTime, card.group);
        int zoneId = cardThrown.getCardType().ordinal();
        setToGridCenterCoordinates(card.group, drawPilesCells[zoneId]);
        card.sprite.setRotation(drawPilesOrientations[zoneId]);
        gem.commitEntityState(0.95, card.group);
        card.sprite.setVisible(false);
    }

    public void playerPlaysCard(Card playedCard) {
        CardView card = cards.get(playedCard.getId());
        gem.commitEntityState(0.1, card.group);
        card.group.setY(card.group.getY() - 50);
        gem.commitEntityState(0.3, card.group);
    }

    public void playerDiscardsCardAtEndOfTurn(Card playedCard, Player player) {
        CardView card = cards.get(playedCard.getId());
        setToGridCenterCoordinates(card.group, playerDiscardPilesCells[player.getIndex()]);
        card.sprite.setRotation(0);
        gem.commitEntityState(0.95, card.group);
        card.sprite.setVisible(false);
    }

    //used for CONTINUOUS_INTEGRATION or for permanent skills
    public void moveCardToPlayer(Card playedCard, Player player) {
        CardView card = cards.get(playedCard.getId());
        setToGridCenterCoordinates(card.group, getCoordinates(player.getIndex(),player.getZoneId()));
        gem.commitEntityState(0.95, card.group);
        card.sprite.setVisible(false);
    }

    public void commitAll(double time) {
        gem.commitWorldState(time);
    }

    public void addCardInDiscardPile(Card cardModel, Player player) {
        CardView card = cards.get(cardModel.getId());
        int zoneId = cardModel.getCardType().ordinal();
        setToGridCenterCoordinates(card.group, drawPilesCells[zoneId]);
        card.sprite.setRotation(drawPilesOrientations[zoneId]);
        card.sprite.setVisible(true);
        gem.commitEntityState(0.1, card.sprite);
    }
}
