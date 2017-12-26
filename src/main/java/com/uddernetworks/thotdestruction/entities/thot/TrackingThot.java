package com.uddernetworks.thotdestruction.entities.thot;

import com.uddernetworks.thotdestruction.main.Game;
import com.uddernetworks.thotdestruction.pathfinding.Node;
import com.uddernetworks.thotdestruction.pathfinding.PathMap;

import java.util.ArrayList;
import java.util.List;

public class TrackingThot extends BasicThot {

    private int playerX = 0;
    private int playerY = 0;
    private PathMap pathMap;
//    private List<Node> path;
//    private int pathIndex = 0;

    public TrackingThot(Game game, int x, int y) {
        super(game, false, x, y, false);

        pathMap = new PathMap(game.getLevel());

        pathMap.printMap();

        findPlayer();
    }

    public void findPlayer() {
//        System.out.println("Finding player!");
        if (game.getPlayer() == null) return;
//        System.out.println("good");

        int currPlayerX = game.getPlayer().xExact;
        int currPlayerY = game.getPlayer().yExact;

        if (currPlayerX != playerX || currPlayerY != playerY) {
//            System.out.println("GOODERRR");
            playerX = currPlayerX;
            playerY = currPlayerY;
            refreshPath();
        }
    }

    private void refreshPath() {
        System.out.println("From (" + (this.x / 16) + ", " + (this.y / 16) + ") to (" + (playerX / 16) + ", " + (playerY / 16) + ")");

//        List<Node> path = pathMap.findPath(this.xExact, this.yExact, playerX, playerY);
        List<Node> path = pathMap.findPath(game, this.x / 16, this.y / 16, playerX / 16, playerY / 16);

        List<Integer> pathx = new ArrayList<>();
        List<Integer> pathy = new ArrayList<>();

        for (Node node : path) {
            pathx.add(node.getX() * 16);
            pathy.add(node.getY() * 16);

//            System.out.println("(" + node.getX() + ", " + node.getY()  +")");
        }

        this.pathIndex = 0;
        this.incrementAmount = this.speed;
        this.setPath(pathx, pathy);

//        System.out.println("xPath = " + this.xPath);

    }

    private int tickCount = 0;

    @Override
    public void tick() {
        tickCount++;

        if (tickCount % 20 != 0) {

//            System.out.println("x = " + x + " xOffset = " + game.getScreen().getXOffset());

            super.tick();
//            System.out.println("At (" + (this.x) + ", " + (this.y) + ")");

            return;
        }

//        if (game.getPlayer() != null && (this.xPath.size() == 0)) {
        if (game.getPlayer() != null) {
            findPlayer();
        }

        super.tick();

//        if (foundPlayer) {

//            refreshPath();
//        }

//        if (path.size() >= pathIndex) return;
//
//        Node currentNode = path.get(pathIndex);
//        this.x = currentNode.getX();
//        this.y = currentNode.getY();
//


//        pathIndex++
    }


}
