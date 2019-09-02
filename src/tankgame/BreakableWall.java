package tankgame;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject{
	
    public BreakableWall(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        setImg(image);
    }
}
