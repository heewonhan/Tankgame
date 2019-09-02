package tankgame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class GameObject {
    protected int x;
    protected int y;
    protected short angle;
    protected Image img;
    
    public GameObject() {
    	x = y  = 0;
    	angle = 0;
	}
    
    
    public void paint(Graphics g, Point vPoint, boolean thumb) {
    	
    	if(thumb){ 
            
    		double width = vPoint.x;
    		double height = vPoint.y;
    		double rx = (width/World.virtualWidth)*x;
    		double ry = (height/World.virtualHeight)*y;
    		double imageWidth = img.getWidth(null);
    		double imageHeight = img.getHeight(null);
    		 		
    		int w = (int)((width/World.virtualWidth)*imageWidth);
    		int h = (int)((height/World.virtualHeight)*imageHeight);
    				
    		if(w == 0 || h== 0){
    			return;
    		}

    		
            Graphics2D graphic2D = (Graphics2D) g;
            graphic2D.drawImage(img, (int)rx, (int)ry, (int)rx+w,(int)ry+h,0,0,img.getWidth(null),img.getHeight(null),null);
    	}else {
    		
    		
        	int rx = vPoint.x + x;
        	int ry = vPoint.y + y;
        	
            AffineTransform rotation = AffineTransform.getTranslateInstance(rx, ry);
            rotation.rotate(Math.toRadians(angle), img.getWidth(null) / 2, img.getHeight(null) / 2);
            Graphics2D graphic2D = (Graphics2D) g;
            graphic2D.drawImage(img, rotation, null);
		}
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
		return x;
	}
    
    public int getY() {
		return y;
	}
    
    public void setImg(Image img) {
        this.img = img;
    }
    
 
    Rectangle getBounds(){
    	return new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
    }
}
