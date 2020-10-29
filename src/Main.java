//package test;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
		
		File file = new File("../images/out.bmp");
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("../images/borboleta.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// you should stop here
		byte[][] green = new byte[30][40];
		for(int x=0; x<30; x++){
		  for(int y=0; y<40; y++){
		     int color = img.getRGB(x,y);
		     //alpha[x][y] = (byte)(color>>24);
		     //red[x][y] = (byte)(color>>16);
		     green[x][y] = (byte)(color>>8);
		     //blue[x][y] = (byte)(color);
		  }
		}
		
		try {
			ImageIO.write(img, "BMP", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}