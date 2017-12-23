package com.uddernetworks.thotdestruction.main;

import com.uddernetworks.thotdestruction.entities.EntityManager;
import com.uddernetworks.thotdestruction.entities.EntityTextureManager;
import com.uddernetworks.thotdestruction.entities.Player;
import com.uddernetworks.thotdestruction.entities.ThotRiflePickup;
import com.uddernetworks.thotdestruction.entities.thot.BasicThot;
import com.uddernetworks.thotdestruction.gfx.*;
import com.uddernetworks.thotdestruction.level.Level;
import com.uddernetworks.thotdestruction.level.tile.TileUtils;
import com.uddernetworks.thotdestruction.weapons.ThotRifle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    private static final int WIDTH = 300;
    private static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 3;
    private static final String NAME = "Thot Destruction";

    private boolean running = false;
    private int tickCount = 0;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
//    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private JFrame jFrame;
    private SpriteSheet spriteSheet;
    private Screen screen;
    private InputHandler inputHandler;
    private EntityManager entityManager;
    private FontManager fontManager;
    private ScopeRenderer scopeRenderer;
    private Player player;
    private Level level;
    private AnimationSet animationSet;
    private EntityTextureManager entityTextureManager;

    public Game() {
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        jFrame = new JFrame(NAME);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        jFrame.add(this, BorderLayout.CENTER);
        jFrame.pack();

        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    public synchronized void start() {
        running = true;
        init();
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    private int fps = -1;

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                ticks++;
                tick();
                delta--;
                shouldRender = true;
            }

//            try {
//                Thread.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                this.fps = frames;
                System.out.println("TPS: " + ticks + "  FPS:" + frames);
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void init() {
        System.out.println("Game.init");

        inputHandler = new InputHandler(this);
        spriteSheet = new SpriteSheet("/spritesheet.png");
        TileUtils.loadAllTiles(spriteSheet);

        animationSet = new AnimationSet(spriteSheet);

        animationSet.addSet("LEFT", PlayerAnimations.LEFT_STILL, PlayerAnimations.LEFT_WALK_1, PlayerAnimations.LEFT_STILL, PlayerAnimations.LEFT_WALK_2, PlayerAnimations.LEFT_STILL);
        animationSet.addSet("RIGHT", PlayerAnimations.RIGHT_STILL, PlayerAnimations.RIGHT_WALK_1, PlayerAnimations.RIGHT_STILL, PlayerAnimations.RIGHT_WALK_2, PlayerAnimations.RIGHT_STILL);
        animationSet.addSet("BACKWARD",PlayerAnimations.BACKWARD_STILL, PlayerAnimations.BACKWARD_WALK_1, PlayerAnimations.BACKWARD_STILL, PlayerAnimations.BACKWARD_WALK_2, PlayerAnimations.BACKWARD_STILL);
        animationSet.addSet("FORWARD", PlayerAnimations.FORWARD_STILL, PlayerAnimations.FORWARD_WALK_1, PlayerAnimations.FORWARD_STILL, PlayerAnimations.FORWARD_WALK_2, PlayerAnimations.FORWARD_STILL);


        animationSet.addSet("RIFLE_LEFT", PlayerAnimations.RIFLE_LEFT_STILL, PlayerAnimations.RIFLE_LEFT_WALK_1, PlayerAnimations.RIFLE_LEFT_STILL, PlayerAnimations.RIFLE_LEFT_WALK_2, PlayerAnimations.RIFLE_LEFT_STILL);
        animationSet.addSet("RIFLE_RIGHT", PlayerAnimations.RIFLE_RIGHT_STILL, PlayerAnimations.RIFLE_RIGHT_WALK_1, PlayerAnimations.RIFLE_RIGHT_STILL, PlayerAnimations.RIFLE_RIGHT_WALK_2, PlayerAnimations.RIFLE_RIGHT_STILL);
        animationSet.addSet("RIFLE_BACKWARD",PlayerAnimations.RIFLE_BACKWARD_STILL, PlayerAnimations.RIFLE_BACKWARD_WALK_1, PlayerAnimations.RIFLE_BACKWARD_STILL, PlayerAnimations.RIFLE_BACKWARD_WALK_2, PlayerAnimations.RIFLE_BACKWARD_STILL);
        animationSet.addSet("RIFLE_FORWARD", PlayerAnimations.RIFLE_FORWARD_STILL, PlayerAnimations.RIFLE_FORWARD_WALK_1, PlayerAnimations.RIFLE_FORWARD_STILL, PlayerAnimations.RIFLE_FORWARD_WALK_2, PlayerAnimations.RIFLE_FORWARD_STILL);

        scopeRenderer = new ScopeRenderer(spriteSheet, this);

        entityTextureManager = new EntityTextureManager(spriteSheet);

        entityTextureManager.addTexture(ThotRiflePickup.class, 0, 1, 1, 1);
        entityTextureManager.addTexture(BasicThot.class, 0, 14, 2, 2);

        entityManager = new EntityManager(this);

        fontManager = new FontManager();

        level = new Level(this, "/level.png", "/entitymap.png");

        level.readTiles();
        level.readEntities();

        screen = new Screen(spriteSheet, image, level, WIDTH, HEIGHT);

        player = new Player(this, WIDTH / 2, HEIGHT / 2);

        entityManager.add(player);
    }

    public void tick() {
        tickCount++;

        entityManager.tickAll();
        scopeRenderer.tick();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.render();

        entityManager.renderAll(screen);

        fontManager.drawString(screen, String.format("%1$3s", this.fps) + "FPS", Color.GREEN, 4, 4);

        scopeRenderer.render(screen);

        Graphics graphics = bs.getDrawGraphics();

        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        graphics.dispose();
        bs.show();
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public Screen getScreen() {
        return screen;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    public ScopeRenderer getScopeRenderer() {
        return scopeRenderer;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }

    public AnimationSet getAnimationSet() {
        return animationSet;
    }

    public static void main(String[] args) {
        new Game().start();
    }

    public EntityTextureManager getEntityTextureManager() {
        return entityTextureManager;
    }
}
