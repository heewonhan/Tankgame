package tankgame;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Bullet extends GameObject{
	private Tank player;
	private final int r = 4;
	
    public Bullet(int x, int y, short angle, Tank tank, Image image) {
    	this.angle = angle;
        this.x = x;
        this.y = y;
        player = tank;
        setImg(image);
    }
    
    public Tank getPlayer() {
		return player;
	}
    
   
    public void moveForwards() {
    	int oldx = x;
    	int oldy = y;
    	
    	int vx = (int) (Math.round(r * (Math.cos(Math.toRadians(angle)))));
    	int vy = (int) (Math.round(r * (Math.sin(Math.toRadians(angle)))));
        x += vx;
        y += vy;

        checkCollision(oldx,oldy);
    }
	
   
    private void checkCollision(int oldx, int oldy) {
    	List<GameObject> removed = new ArrayList<>();
    	
    	for(GameObject sprite : player.world.gameObjects){
    		if(sprite == player){
    			continue;
    			
    		}else if(sprite instanceof Tank){
    			if(getBounds().intersects(sprite.getBounds())){
    				
    				removed.add(this);
    				
    				
    				Tank tank = (Tank)sprite;
    				tank.minusEnergy(1);
    				
    				
    				tank.bulletSpeed = false;
    			}
    			
    		}else if(sprite instanceof UnbreakableWall){
    			if(getBounds().intersects(sprite.getBounds())){
    				removed.add(this);
    			}
    		}else if(sprite instanceof BreakableWall){
    			
    			if(getBounds().intersects(sprite.getBounds())){
	    			removed.add(this);
	    			removed.add(sprite);
    			}
    		}
    	}
    	
    	//funciton for remove 
    	for (GameObject gameObject : removed) {
			player.world.gameObjects.remove(gameObject);
			player.world.bullets.remove(gameObject);
		}
	}
}
