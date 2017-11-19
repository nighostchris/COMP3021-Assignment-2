package terrain;

import javafx.scene.image.Image;
import pa1.GameApplication;

public class Hills extends Terrain
{
	private static final Image IMAGE_HILLS = new Image("terrain_images/hills.png");
	
	public Hills()
	{
		super(3);
	}
	
	public Image getImage() { return IMAGE_HILLS; }
}