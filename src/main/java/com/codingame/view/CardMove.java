package com.codingame.view;

import com.codingame.game.card.Card;

import java.awt.geom.Point2D;

public class CardMove {
    Point2D.Double positionFrom, positionTo;
    double orientationFrom, orientationTo;
    Card cardModel;

    public CardMove(Card cardModel, Point2D.Double positionFrom, Point2D.Double positionTo, double orientationFrom, double orientationTo) {
        super();
        this.positionFrom = positionFrom;
        this.positionTo = positionTo;
        this.orientationFrom = orientationFrom;
        this.orientationTo = orientationTo;
        this.cardModel = cardModel;
    }
}
