package terrain;

import javafx.scene.image.Image;
import units.Unit;

// TODO: You may refer to the Unit classes (Archer, Cavalry, Infantry, Pikeman) for how to setup the Image for the other Terrain classes.
// NOTE: TerrainOutOfBounds is a special case and the code is fully given.
public abstract class Terrain
{
	protected static final int MOVEMENT_COST = 0;
	protected boolean impassable = false;
	protected boolean occupied = false;
	protected Unit occupyingUnit = null;
	
	public Terrain(int movementCost)
	{
		this.MOVEMENT_COST = movementCost;
	}
	
	public abstract Image getImage();
	
	public int getMovementCost() { return MOVEMENT_COST; }
	
	// called when a Unit moves onto this terrain
	public void occupy(Unit occupyingUnit)
	{
		occupied = true;
		this.occupyingUnit = occupyingUnit;
	}
	
	// called when a Unit moves away from this terrain
	public void unoccupy()
	{
		occupied = false;
		occupyingUnit = null;
	}
	
	// getter of "occupyingUnit"
	public Unit getOccupyingUnit() { return occupyingUnit; }
	
	// getter of "occupied"
	public boolean isOccupied() { return occupied; }
	
	// returns whether the terrain is blocked 
	// (blocked because it is being occupied by a Unit, or because the terrain is impassable)
	public boolean isBlocked() { return (impassable || occupied); }
}