package terrain;

import javafx.scene.image.Image;
import pa1.GameApplication;

public class Plains extends Terrain
{
	private static final Image IMAGE_PLAINS = new Image("terrain_images/plains.png");
	
	public Plains()
	{
		super(1);
	}
	
	public Image getImage() { return IMAGE_PLAINS; }
}