package tankgame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class World {
	public final Random random = new Random(1);
	
	public TankGame tankGame;
	
	
	public final static int virtualWidth = 1536;
	public final static int virtualHeight = 1536;
	
	
	public final int width = 500;
	public final int height = 600;
	
	
	public Point vPoint1;
	
	
	public Point vPoint2;
	
	
	public int player1Live = 3,player2Live = 3;
	public Tank player1, player2;
	
	
	public List<GameObject> gameObjects = new ArrayList<>();
	
	
	public List<Bullet> bullets = new ArrayList<>();
	
	
	public boolean gameover = false;
	
	public World() {
		vPoint1 = new Point(0, 0);
        player1 = new Tank(100, 100, (short)0, GameResource.tank1);
        player1.world = this;
        player1.vPoint = vPoint1;
        
        vPoint2 = new Point(width-virtualWidth, height-virtualHeight);
        player2 = new Tank(virtualWidth-100, virtualHeight-100, (short)180, GameResource.tank2);
        player2.world = this;
        player2.vPoint = vPoint2;
        
        addWalls();
        gameObjects.add(player1);
        gameObjects.add(player2);
	}
	
	
	public void addWalls(){
		int gridRows = virtualHeight / Settings.wallHeight;
		int gridCols = virtualWidth / Settings.wallWidth;
		for(int j=0;j<gridCols+1;j++){
			gameObjects.add(new UnbreakableWall(j*Settings.wallWidth, 0, GameResource.unbreakableWall));
			gameObjects.add(new UnbreakableWall(j*Settings.wallWidth, (gridRows-1)*Settings.wallHeight, GameResource.unbreakableWall));
		}
		
		for(int i=0;i<gridRows+1;i++){
			gameObjects.add(new UnbreakableWall(0, i*Settings.wallHeight, GameResource.unbreakableWall));
			gameObjects.add(new UnbreakableWall((gridCols-1)*Settings.wallWidth, i*Settings.wallHeight, GameResource.unbreakableWall));
		}
		
		
		for(int j=gridCols/3;j<gridCols+1-gridCols/3;j++){
			gameObjects.add(new UnbreakableWall(j*Settings.wallWidth, (gridRows/2-1)*Settings.wallHeight, GameResource.unbreakableWall));
		}
		
		for(int i=0;i<gridRows+1;i++){
			if((i%10 >= 5 && i%10 <= 9)){
				gameObjects.add(new BreakableWall((gridCols/2)*Settings.wallWidth, i*Settings.wallHeight, GameResource.breakableWall));
				continue;
			}
			gameObjects.add(new UnbreakableWall((gridCols/2)*Settings.wallWidth, i*Settings.wallHeight, GameResource.unbreakableWall));
		}
		
		int[][] walls = new int[][] {
				
				{5,5,10},
				{6,5,10},
				{7,5,10},
				{20,10,30},
				{30,20,30},
				{31,30,40},
				{32,35,40},
		};
		
		for (int[] is : walls) {
			int row = is[0];
			int start = is[1];
			int end = is[2];
			for(int j=start;j<end;j++){
				gameObjects.add(new BreakableWall(j*Settings.wallWidth, row*Settings.wallHeight, GameResource.breakableWall));
			}
		}
	}

	public Tank getPlayer1() {
		return player1;
	}
	
	public Tank getPlayer2() {
		return player2;
	}

	//draw mini map
	public void paint(Graphics graphics, int width, int height) {
		List<GameObject> copy = new ArrayList<>();
		copy.addAll(gameObjects);
		
		for (GameObject gameObject : copy) {
			gameObject.paint(graphics,new Point(width, height),true);
		}
		
		List<Bullet> copy2 = new ArrayList<>();
		copy2.addAll(bullets);
		
		for (Bullet bullet : copy2) {
			bullet.paint(graphics, new Point(width, height), true);
		}
	}
	
	
	public void paint(Graphics graphics, Point vPoint) {
		List<GameObject> copy = new ArrayList<>();
		copy.addAll(gameObjects);
		
		for (GameObject gameObject : copy) {
			gameObject.paint(graphics,vPoint,false);
		}
		
		List<Bullet> copy2 = new ArrayList<>();
		copy2.addAll(bullets);
		for (Bullet bullet : copy2) {
			bullet.paint(graphics, vPoint, false);
		}
	}

	
	public void update() {
		
		
		player1.update();
		player2.update();
		
		
		List<Bullet> copy = new ArrayList<>();
		for (Bullet bullet : bullets) {
			copy.add(bullet);
		}
		for (Bullet bullet : copy) {
			bullet.moveForwards();
		}
		
                
		if(player1.dead()){
			gameObjects.remove(player1);
			if(player1Live > 0){
				vPoint1 = new Point(0, 0);
		        player1 = new Tank(100, 100, (short)0, GameResource.tank1);
		        
		        player1.world = this;
		        player1.vPoint = vPoint1;
		        tankGame.control1.setTank(player1);
		        
		        gameObjects.add(player1);
		        player1Live--;
			}else {
				player1 = null;
				gameover = true;
				return;
			}
		}
		
		if(player2.dead()){
			gameObjects.remove(player2);
			if(player2Live > 0){
		        vPoint2 = new Point(width-virtualWidth, height-virtualHeight);
		        player2 = new Tank(virtualWidth-100, virtualHeight-100, (short)180, GameResource.tank2);
		        player2.world = this;
		        player2.vPoint = vPoint2;
		        tankGame.control2.setTank(player2);
		        
		        gameObjects.add(player2);
		        player2Live--;
			}else {
				player2 = null;
				gameover = true;
			}
		}
	}
        
}

