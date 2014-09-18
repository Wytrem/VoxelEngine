package net.wytrem.voxelengine;


import org.lwjgl.opengl.GL11;


public class WorldRenderer
{
	World world;
	
	public WorldRenderer(World world)
	{
		this.world = world;
	}

	public void renderWorld(World world)
	{
		Chunk chunk;

		for (int i = 0; i < world.chunkList.size(); i++)
		{
			chunk = world.chunkList.get(i);

			if (chunk != null)
			{
				this.renderChunk(chunk);
			}
		}
	}

	private void renderChunk(Chunk chunk)
	{
		for (int i = 0; i < 16; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				for (int k = 0; k < 16; k++)
				{
					int tileId = chunk.tiles[i][j][k];

					if (tileId < 0)
					{
						tileId = 0;
					}

					if (tileId >= Tile.tiles.length)
					{
						tileId = Tile.tiles.length - 1;
					}

					Tile tile = Tile.tiles[tileId];
					int posX = (chunk.x << 4) + i;
					int posY = j;
					int posZ = (chunk.z << 4) + k;
					
					
					if (tile != null)
					{
						this.renderTileAt(tile, posX, posY, posZ);
					}
				}
			}
		}
	}

	private boolean shouldRenderTileAt(int x, int y, int z)
	{
		Tile tile;
		
		
		tile = this.world.getTileAt(x - 1, y, z);
		if (tile == null || !tile.isOpaque())
		{
			return true;
		}
		
		tile = this.world.getTileAt(x + 1, y, z);

		if (tile == null || !tile.isOpaque())
		{
			return true;
		}
		
		if (y != 0)
		{
			tile = this.world.getTileAt(x, y - 1, z);

			if (tile == null || !tile.isOpaque())
			{
				return true;
			}
		}
		
		tile = this.world.getTileAt(x, y + 1, z);

		if (tile == null || !tile.isOpaque())
		{
			return true;
		}
		
		tile = this.world.getTileAt(x, y, z - 1);

		if (tile == null || !tile.isOpaque())
		{
			return true;
		}
		
		tile = this.world.getTileAt(x, y, z + 1);

		if (tile == null || !tile.isOpaque())
		{
			return true;
		}
		
		return false;
	}

	Texture texture;

	private void renderTileAt(Tile tile, int x, int y, int z)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		// bot
		texture = tile.getTexture(Tile.bot);
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3i(x, y, z);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3i(x + 1, y, z);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3i(x + 1, y, z + 1);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3i(x, y, z + 1);
		GL11.glEnd();
		
		// top
		texture = tile.getTexture(Tile.top);
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3i(x, y + 1, z);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3i(x + 1, y + 1, z);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3i(x + 1, y + 1, z + 1);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3i(x, y + 1, z + 1);
		GL11.glEnd();
		
		// left
		texture = tile.getTexture(Tile.left);
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3i(x, y, z);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3i(x, y + 1, z);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3i(x + 1, y + 1, z);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3i(x + 1, y, z);
		GL11.glEnd();
		
		// right
		texture = tile.getTexture(Tile.right);
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3i(x, y, z + 1);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3i(x, y + 1, z + 1);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3i(x + 1, y + 1, z + 1);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3i(x + 1, y, z + 1);
		GL11.glEnd();

		// front
		texture = tile.getTexture(Tile.front);
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3i(x, y, z);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3i(x, y + 1, z);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3i(x, y + 1, z + 1);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3i(x, y, z + 1);
		GL11.glEnd();

		// back
		texture = tile.getTexture(Tile.back);
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3i(x + 1, y, z);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3i(x + 1, y + 1, z);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3i(x + 1, y + 1, z + 1);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3i(x + 1, y, z + 1);
		GL11.glEnd();
	}
	
	
}
