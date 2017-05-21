package com.puebla.TiledGame.gameStates;

import com.puebla.TiledGame.main.Game;
import com.puebla.TiledGame.manager.Collisions;
import com.puebla.TiledGame.manager.KeyController;
import com.puebla.TiledGame.model.Camara;
import com.puebla.TiledGame.model.OvalActor;
import com.puebla.TiledGame.model.OvalEnemy;
import com.puebla.TiledGame.model.RectActor;
import com.puebla.TiledGame.model.RectEnemy;
import com.puebla.TiledGame.tileMap.TileMap;

import java.awt.Graphics;

/**
 * @author federico on 12/04/17.
 * @email fede.larregle@gmail.com
 */
public class PlayState implements GameState{

    private final TileMap tileMap;
    private final Game game;
    private final RectActor player;
    private final RectEnemy enemy;
    private final Camara camara;
    private static final Collisions collisions;

    static {
        collisions = Collisions.getInstance();
    }

    public PlayState(Game game) {
        this.game = game;
        this.tileMap = game.getTileMap();
        this.player = new RectActor(game, tileMap, 24, 24);
        this.camara = game.getCamara();
        this.enemy = new RectEnemy(game, tileMap, player, 72, 24);
    }

    @Override
    public void update() {
        handleInput();
        player.update();
        camara.move(player);
        //enemy.update();
        if ( collisions.checkRectangleCollision(player, enemy) ) {
            player.gotHit();
            if ( player.isDead() ) {
                game.setGameState(new GameOverState(game));
            }
        }
        /* TODO check collision for circled player and enemy
        if ( collisions.checkCircleCollision(player, enemy) ) {
            player.gotHit();
            if ( player.isDead() ) {
                game.setGameState(new GameOverState(game));
            }
        }*/

    }

    @Override
    public void draw(Graphics graphics) {
        tileMap.draw(graphics);
        player.draw(graphics);
        enemy.draw(graphics);
    }

    @Override
    public void handleInput() {

        if (KeyController.isPressed(KeyController.P)) {
            game.setGameState(new PauseState(game, this));
        }
        if (KeyController.isPressed(KeyController.LEFT)) {
            player.setLeft(true);
        }
        if (KeyController.isPressed(KeyController.RIGHT)) {
            player.setRight(true);
        }
        if (KeyController.isPressed(KeyController.UP)) {
            player.setUp(true);
        }
        if (KeyController.isPressed(KeyController.DOWN)) {
            player.setDown(true);
        }
        if (KeyController.isPressed(KeyController.W)) {
            camara.setMovingUp(true);
        }
        if (KeyController.isPressed(KeyController.A)) {
            camara.setMovingLeft(true);
        }
        if (KeyController.isPressed(KeyController.S)) {
            camara.setMovingDown(true);
        }
        if (KeyController.isPressed(KeyController.D)) {
            camara.setMovingRight(true);
        }


        if (!(KeyController.isPressed(KeyController.LEFT))) {
            player.setLeft(false);
        }
        if (!(KeyController.isPressed(KeyController.RIGHT))) {
            player.setRight(false);
        }
        if (!(KeyController.isPressed(KeyController.UP))) {
            player.setUp(false);
        }
        if (!(KeyController.isPressed(KeyController.DOWN))) {
            player.setDown(false);
        }
        if (!(KeyController.isPressed(KeyController.W))) {
            camara.setMovingUp(false);
        }
        if (!(KeyController.isPressed(KeyController.A))) {
            camara.setMovingLeft(false);
        }
        if (!(KeyController.isPressed(KeyController.S))) {
            camara.setMovingDown(false);
        }
        if (!(KeyController.isPressed(KeyController.D))) {
            camara.setMovingRight(false);
        }

    }
}
