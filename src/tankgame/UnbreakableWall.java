package tankgame;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends GameObject{
	
    public UnbreakableWall(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        setImg(image);
    }
}
