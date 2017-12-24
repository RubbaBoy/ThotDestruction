package com.uddernetworks.thotdestruction.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    private Game game;

    public MouseHandler(Game game) {
        this.game = game;

        game.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        int x = event.getX() / 3;
        int y = event.getY() / 3;

        System.out.println("Clicked at (" + (x - game.getScreen().getXOffset()) + ", " + (y - game.getScreen().getXOffset()) + ")");

        game.getPlayer().click(x, y);
    }

    @Override
    public void mouseReleased(MouseEvent event) {

    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }
}
