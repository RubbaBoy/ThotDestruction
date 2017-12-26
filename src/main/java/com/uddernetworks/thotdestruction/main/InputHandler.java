package com.uddernetworks.thotdestruction.main;

import com.uddernetworks.thotdestruction.entities.thot.TrackingThot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key ctrl = new Key();
    public Key shift = new Key();
    public Key q = new Key();

    public InputHandler(Game game) {
        game.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    private void toggleKey(int key, boolean pressed) {
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            up.toggleKey(pressed);
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            left.toggleKey(pressed);
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            down.toggleKey(pressed);
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            right.toggleKey(pressed);
        } else if (key == KeyEvent.VK_CONTROL) {
            ctrl.toggleKey(pressed);
        } else if (key == KeyEvent.VK_SHIFT) {
            shift.toggleKey(pressed);
        } else if (key == KeyEvent.VK_Q) {
            q.toggleKey(pressed);
        }
    }

    public class Key {
        private boolean isPressed = false;

        public void toggleKey(boolean isPressed) {
            this.isPressed = isPressed;
        }

        public boolean isPressed() {
            return this.isPressed;
        }
    }
}
