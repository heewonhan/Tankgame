package tankgame;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Tank extends GameObject{

    private final int speed = 4;
    private final int angleSpeed = 2;
    
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean bulletPressed;
    private int energy = Settings.totalEnergy;
    
    public Tank(int x, int y, short angle, Image image) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        setImg(image);
    }

    public int getEnergy() {
		return energy;
	}
    public void minusEnergy(int num){
    	energy -= num;
    }
    public boolean dead(){
    	return energy <= 0;
    }
    
    public void setAngle(short angle) {
        this.angle = angle;
    }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    
    private long lastBulletTime = 0;
    public void toggleBulletPressed(){
    	this.bulletPressed = true;
    }
    public void unToggleBulletPressed(){
    	this.bulletPressed = false;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        
        if(this.bulletPressed){
        	this.emitBullet();
        }
    }

    public World world;
    public Point vPoint;
	public boolean bulletSpeed = false;
	
	//shoot bullet 
    private void emitBullet() {
    	long interval = Settings.bulletInterval;
    	
    	if(bulletSpeed){
    		interval /= 2;
    	}
    	
    	if(System.currentTimeMillis() - lastBulletTime > interval){
    		
    		world.bullets.add(new Bullet(x, y, angle, this, GameResource.bullet));
    		lastBulletTime = System.currentTimeMillis();
    		world.tankGame.playBullet();
    	}
	}

	private void rotateLeft() {
        this.angle -= angleSpeed;
    }

    private void rotateRight() {
        this.angle += angleSpeed;
    }
    
    
   	private void moveForwards() {
       	int oldx = x;
       	int oldy = y;
       	
       	int vx = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
       	int vy = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));
           x += vx;
           y += vy;
           
           
           int dx = world.width/2 - x;
           int dy = world.height/2 - y;
           vPoint.x = dx;
           vPoint.y = dy;
           checkBorder();
           
           checkCollision(oldx,oldy);
           
         
       }

    
    private void moveBackwards() {
    	int oldx = x;
    	int oldy = y;
    	
        int vx = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
        int vy = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;

        int dx = world.width/2 - x;
        int dy = world.height/2 - y;
        vPoint.x = dx;
        vPoint.y = dy;
        checkBorder();
        
        checkCollision(oldx,oldy);
        
        //world.tankMoved();
    }
 

    //check collision 
    private void checkCollision(int oldx, int oldy) {
    	List<GameObject> removed = new ArrayList<>();
    	
    	for(GameObject sprite : world.gameObjects){
    		// collide itself 
    		if(sprite == this){
    			continue;
    			// collide with another tank
    		}else if(sprite instanceof Tank){
    			double tx = sprite.getX();
    			double ty = sprite.getY();
    			double diag = Math.sqrt(Settings.tankWidth*Settings.tankWidth + Settings.tankHeight*Settings.tankHeight);
    			double distance = Math.sqrt((x-tx)*(x-tx) + (y-ty)*(y-ty));
    			if(distance < diag){
    				x = oldx;
    				y = oldy;
    			}
    			// collide with unbreakable wall 
    		}else if(sprite instanceof UnbreakableWall){
    			Rectangle bound = sprite.getBounds();
    			if(bound.intersects(getBounds())){
    				x = oldx;
    				y = oldy;
    			}
    			// collide with breakablewall 
    		}else if(sprite instanceof BreakableWall){
    			Rectangle bound = sprite.getBounds();
    			if(bound.intersects(getBounds())){
    				x = oldx;
    				y = oldy;
    			}
    			
    		}else if(sprite instanceof PowerUp){
    			Rectangle bound = sprite.getBounds();
    			if(bound.intersects(getBounds())){
    				removed.add(sprite);
    				this.bulletSpeed = true;
    				energy = Settings.totalEnergy;
    			}
    		}
    	}
    	
    	//remove oject from the screen 
    	for (GameObject gameObject : removed) {
			world.gameObjects.remove(gameObject);
			world.bullets.remove(gameObject);
		}
	}

    // check border 
    private void checkBorder() {
        if (x < 0) {
            x = 0;
        }
        if (x >= World.virtualWidth - Settings.tankWidth) {
            x = World.virtualWidth - Settings.tankWidth;
        }
        if (y < 0) {
            y = 0;
        }
        if (y >= World.virtualHeight - Settings.tankHeight) {
            y = World.virtualHeight - Settings.tankHeight;
        }
        
        if(vPoint.x < world.width - World.virtualWidth){
        	vPoint.x = world.width - World.virtualWidth;
        }
        if(vPoint.x > 0){
        	vPoint.x = 0;
        }
        if(vPoint.y < world.height - World.virtualHeight){
        	vPoint.y = world.height - World.virtualHeight;
        }
        if(vPoint.y > 0){
        	vPoint.y = 0;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }
    
}
