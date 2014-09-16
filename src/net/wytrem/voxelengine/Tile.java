package net.wytrem.voxelengine;

public class Tile
{
	
	public static final Tile[] tiles = new Tile[4096];
	
	public static final Tile dirt = new Tile(1).setColor(0xff0000);
	
	
	public final int id;
	public int color;
	
	public Tile(int id)
	{
		if (tiles[id] != null)
		{
			throw new IllegalArgumentException("Tile conflict @ " + id);
		}
		else
		{
			this.id = id;
		}
	}
	
	public Tile setColor(int color)
	{
		this.color = color;
		return this;
	}
}
