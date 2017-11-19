package terrain;

import javafx.scene.image.Image;
import pa1.GameApplication;

// TODO: Also setup the Images for Water Animation.
// Use "static {}" to initialize static fields.
// You may change ANIM_TIME_PER_FRAME to your own preference.
// Refer to Lab 12.
public class Water extends Terrain
{
	public static final int NUM_ANIM_FRAMES = 4;
	public static final int ANIM_TIME_PER_FRAME = 1000;
	
	private static final Image IMAGE_WATER = new Image("terrain_images/water1.png");
	private static final Image[] ANIM_FRAMES = new Image[NUM_ANIM_FRAMES];
	
	static
	{
		ANIM_FRAMES[0] = new Image("terrain_images/water1.png");
		ANIM_FRAMES[1] = new Image("terrain_images/water2.png");
		ANIM_FRAMES[2] = new Image("terrain_images/water3.png");
		ANIM_FRAMES[3] = new Image("terrain_images/water4.png");
	}

	public Water()
	{
		super(-1);
	}
	
	public Image getImage() { return IMAGE_WATER; }
	
	public Image getAnimFrame(int index) { return ANIM_FRAMES[index]; }
}