package net.wytrem.voxelengine;


public class Tile
{
	public static final int bot = 0;
	public static final int top = 1;
	public static final int right = 2;
	public static final int left = 3;
	public static final int front = 4;
	public static final int back = 5;
	
	public static final Tile[] tiles = new Tile[4096];

	public static final Tile dirt = new Tile(1).setTextureName("dirt");
	public static final Tile grass = new Tile(2).setTextureName("dirt", bot).setTextureName("grass_top", top).setTextureName("grass_side", right, left, front, back);
	public static final Tile stone = new Tile(3).setTextureName("stone");

	public final int id;
	public String[] textureNames = new String[6];
	private final Texture[] textureObjects = new Texture[6];

	public Tile(int id)
	{
		if (tiles[id] != null)
		{
			throw new IllegalArgumentException("Tile conflict @ " + id);
		}
		else
		{
			this.id = id;
			tiles[id] = this;
		}
	}

	/**
	 * Index
	 * 0: bottom, 1: top, 2: left, 3: right, 4: front, 5:back
	 */
	public Tile setTextureName(String name, int... index)
	{
		for (int i : index)
		{
			this.textureNames[i] = name;
		}
		return this;
	}

	public Tile setTextureName(String name)
	{
		for (int i = 0; i < textureNames.length; i++)
		{
			this.setTextureName(name, i);
		}

		return this;
	}

	public Texture getTexture(int index)
	{
		return this.textureObjects[index];
	}

	public boolean isOpaque()
	{
		return true;
	}
	
	public boolean isSolid()
	{
		return true;
	}

	static
	{
		for (int i = 0; i < tiles.length; i++)
		{
			Tile tile = tiles[i];
			
			if (tile != null)
			{
				for (int j = 0; j < tile.textureObjects.length; j++)
				{
					tile.textureObjects[j] = Texture.loadTexture("textures/blocks/" + tile.textureNames[j] + ".png");
				}
			}
		}
	}
}
