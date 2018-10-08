package org.academiadecodigo.codecadets;

import org.academiadecodigo.codecadets.enums.GameStates;
import org.academiadecodigo.codecadets.gameobjects.Target;
import org.academiadecodigo.codecadets.gameobjects.weapons.Weapon;
import org.academiadecodigo.codecadets.handlers.DuckMouseHandler;
import org.academiadecodigo.codecadets.renderer.Renderer;

import java.util.LinkedList;

public class Game {

    private Renderer renderer;
    private Player player;
    private DuckMouseHandler mouseHandler;

    // Game Properties
    private boolean gameEnded;
    private GameStates gameState;
    private LinkedList<Target> enemyList;
    private final int TARGETS_NUMBER = 20;


    public Game() {
        gameEnded = false;
    }

    public void init(String player) {
        this.player = new Player(player);
        this.renderer = new Renderer();
        this.renderer.initRender();
        this.mouseHandler = new DuckMouseHandler(this, this.renderer);
        this.enemyList = new LinkedList<>();
    }

    public void gameStart(){

        for(int i = 0; i < TARGETS_NUMBER; i++) {
            enemyList.add(FactoryTargets.createEnemy());
        }

        player.changeWeapon(FactoryWeapons.createWeapon());
        player.getScore().resetScore();
        renderer.drawClips(player.getWeapon().getType().getClips());
        renderer.reloadAmmo(player.getWeapon().getType().getClipBullets());
        renderer.drawWeapon(player.getWeapon());
        renderer.drawScore(player.getScore().getScore());
        gameState = GameStates.GAMEPLAYING;
        gameEnded = false;

        while(!gameEnded){
           tick();
        }

        while (gameEnded){

            switch (gameState){
                case GAMEEXIT:
                    System.exit(0);
                    break;
                case GAMERESTART:
                    gameStart();
                    return;
            }
        }
    }

    private void tick() {
        if(player.getWeapon().getAmmo() == 0 &&
                player.getWeapon().getClips() == 0){
            gameEnded = true;
        }
    }

    public void eventShoot(){
        Weapon weapon = player.getWeapon();

        for (Target target : enemyList) {
            if (target == null) {
                continue;
            }

            if (weapon.getPosition().getX() < target.getPosition().getX() - weapon.getType().getSpread()) {
                continue;
            }

            if (weapon.getPosition().getX() > target.getPosition().getX() + target.getPicture().getWidth() + weapon.getType().getSpread()) {
                continue;
            }

            if (weapon.getPosition().getY() < target.getPosition().getY() - weapon.getType().getSpread()) {
                continue;
            }

            if (weapon.getPosition().getY() > target.getPosition().getY() + target.getPicture().getHeight() + weapon.getType().getSpread()) {
                continue;
            }
            weapon.shoot(target);
            renderer.drawAmmo(player.getWeapon().getAmmo(), player.getWeapon().getType().getClipBullets());
            renderer.drawClips(player.getWeapon().getType().getClips());
            return;
        }

        weapon.shoot(null);
        renderer.drawAmmo(player.getWeapon().getAmmo(), player.getWeapon().getType().getClipBullets());
        renderer.drawClips(player.getWeapon().getType().getClips());
    }

    public Player getPlayer() {
        return player;
    }

    public GameStates getGameState() {
        return gameState;
    }
}
