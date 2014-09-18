package net.wytrem.voxelengine;


import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


public class VoxelEngine
{
	public static final int displayWidth = 800, displayHeight = 600;

	public World world;
	public WorldRenderer renderWorld;

	private Camera camera;

	float mouseSensitivity = 0.05f;
	float movementSpeed = 10.0f;
	float mouseDx;
	float mouseDy;

	float dt = 0;
	long lastTime = 0;
	long time = 0;

	private boolean debug = false;

	public VoxelEngine()
	{
	}

	private void init()
	{
		this.initGL3D();
		this.world = new World();
		this.renderWorld = new WorldRenderer(this.world);
		this.camera = new Camera(0, 0, 0, this.world);
	}

	private void initGL3D()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1.0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective(45.0f, (float) displayWidth / (float) displayHeight, 0.1f, 100.0f);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}

	public void start()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
			Display.setFullscreen(false);
			Display.setTitle("VoxelEngine - Wytrem");
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		this.init();
		this.run();
	}

	private void run()
	{
		Mouse.setGrabbed(true);
		
		while (!(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)))
		{
			this.update();
			this.intput();
			this.render();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
		System.out.println("Stopping!");
		System.exit(0);
	}

	private void update()
	{
		this.time = Sys.getTime();
		this.dt = (this.time - this.lastTime) / 1000.0f;
		this.lastTime = this.time;

		this.mouseDx = Mouse.getDX();
		this.mouseDy = Mouse.getDY();
		
		if (this.debug )
		{
			System.out.println("camera.pos: " + this.camera.getPosition());
		}
	}

	private void intput()
	{
		this.camera.yaw(this.mouseDx * this.mouseSensitivity);
		this.camera.pitch(-this.mouseDy * this.mouseSensitivity);

		if (Keyboard.isKeyDown(Keyboard.KEY_Z))
		{
			this.camera.walkForward(this.movementSpeed * this.dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.camera.walkBackwards(this.movementSpeed * this.dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q))
		{
			this.camera.strafeLeft(this.movementSpeed * this.dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.camera.strafeRight(this.movementSpeed * this.dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			this.camera.jump(-(this.movementSpeed * this.dt));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			this.camera.jump((this.movementSpeed * this.dt));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F1))
		{
			this.debug = !this.debug;
		}
	}

	private void render()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		this.camera.lookThrough();
		this.renderWorld.renderWorld(this.world);
	}
}
