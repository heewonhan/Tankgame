package tankgame;

import java.awt.Image;
import java.awt.Toolkit;

public class GameResource {
	public static String folder = "/resources/";
	
	public static Image tank1,tank2;
	public static Image background;
	
	public static Image unbreakableWall;
	public static Image breakableWall;
	
	public static Image bullet;
	public static Image PowerUp;
	public static String musicFile;
	public static String musicBulletFile;
    static Image powerUp;
	
	
	
	public static void load(){
        Toolkit tk = Toolkit.getDefaultToolkit();
		
        musicFile = "/resources/Music.mp3";
        musicBulletFile = "/resources/Explosion_small.wav";
		
		tank1 = tk.getImage(GameResource.class.getResource("/resources/Tank1.gif"));
		tank2 = tk.getImage(GameResource.class.getResource("/resources/Tank2.gif"));
		
		background = tk.getImage(GameResource.class.getResource("/resources/Background.png"));
		
		unbreakableWall = tk.getImage(GameResource.class.getResource("/resources/Wall1.gif"));
		breakableWall = tk.getImage(GameResource.class.getResource("/resources/Wall2.gif"));
		
		bullet = tk.getImage(GameResource.class.getResource("/resources/Shell.gif"));
		
		PowerUp = tk.getImage(GameResource.class.getResource("/resources/Pickup.gif"));
	}



}
