/* Name: Group 5
 Member names & IU code:
	Mai Nguyên Hoàng – ITITIU21208
	Nguyễn Minh Duy – ITITIU21186
 Purpose: Chrome Dino game for OOP Lab Project
*/
package Handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {

	public static BufferedImage getResourceImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

}