package tankgame;

import java.awt.Image;

public class PowerUp extends GameObject{
	
    public PowerUp(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        setImg(image);
    }

    
}
