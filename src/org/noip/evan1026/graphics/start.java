package org.noip.evan1026.graphics;


import org.noip.evan1026.*;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import static org.lwjgl.opengl.GL11.*;

public class start {

	public static Game game;
	
	
	private static Camera camera;
	private static boolean mouseControl = true;
	private static boolean fullScreen = false;


	private static ArrayList<Projectile> projectiles;

	private static Projectile firstProjectile;
	private static Projectile secondProjectile;


	private static ArrayList<Target> targets;
	private static Point3D targetsPosition = new Point3D(-4, -3, 5);


	private static ArrayList<Rope> ropes;


	//										Neutral		Player 1	Player 2
	public final static Color[] teamColor = {Color.GREEN, Color.RED, Color.BLUE};

	private static int boardSize = 5;


	private static float roomSpin = 0;


	public static void begin(String[] args){

		initDisplay();
		initScene();
		initGame();
		initCamera();

		while (!Display.isCloseRequested()){

			physicsCalculations();
			render();
			checkInput();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();

		System.exit(0);

	}

	private static void initDisplay(){
		try {
			Display.setFullscreen(fullScreen);
			Toolkit tk = Toolkit.getDefaultToolkit();

			int height = (int) (tk.getScreenSize().height);
			int width = (int) (tk.getScreenSize().width);

			if (fullScreen){
				Display.setDisplayMode(new DisplayMode(width, height));
			}else{
				Display.setDisplayMode(new DisplayMode((int) (width * 0.75), (int) (height * 0.75)));
//				Display.setDisplayMode(new DisplayMode(1700, 800));
			}

			Display.setVSyncEnabled(true);

			Display.sync(60);
			Display.setTitle("Nim");

//			Display.setLocation(0, 0);
//			Display.setLocation(1400, 100);

			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Error with initializing LWJGL display");
			e.printStackTrace();
		}
	}

	private static void initGame(){

		game = new Game(boardSize);
		
		projectiles = new ArrayList<Projectile>();

		targets = new ArrayList<Target>();

		ropes = new ArrayList<Rope>();

		float spherePadding = 0.25f;

		for (int triRow = 0; triRow < boardSize; triRow++){

			float xPadding =  (float) ((boardSize - (triRow + 1)) / 2.0 * (Target.radius*4));

			for (int triCol = 0; triCol < triRow + 1; triCol++){

				targets.add(new Target(
						new Point3D(targetsPosition.x + xPadding + (4*Target.radius * triCol),  targetsPosition.y + triRow * (spherePadding + Target.radius*2), targetsPosition.z),
						new Rotation(0,0,0),
						triRow,
						triCol
						));
			}



		}


	}

	private static void initScene(){

		glClearColor(1.0f, 1.0f, 1.0f, 1);

		//Don't put stuff that's behind a thing in front of it...
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_DOUBLEBUFFER);


		//Don't render the back of polygons
		//REMEMBER TO INITIALIZE VERTICES IN
		//COUNTER-CLOCKWISE ORDER!!!!!!!
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);


		glPushAttrib(GL_TRANSFORM_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(90f, Display.getWidth()/Display.getHeight(), 0.001f, 100f);
		glPopAttrib();

		glMatrixMode(GL_MODELVIEW);

		Mouse.setGrabbed(mouseControl);

	}

	private static void initCamera(){

		camera = new Camera(new Point3D(0,0,0), new Rotation(0, 0, 0), 90, Display.getWidth()/Display.getHeight(), 0.001f, 100);
		camera.initPerspective();
	}

	/**
	 * Not really much physics in here, despite the name. Anyway it has 3 main functions: <br />
	 * &nbsp;1) Kill {@link Projectile}s that go out of bounds, as well as the ones they were paired with, and the {@link Rope} they were connected with. <br />
	 * &nbsp;2) Stick {@link Projectile}s to {@link Target}s when they collide; <br />
	 * &nbsp;3) Do {@link Game} logic to deal with capturing pieces and checking for a win.
	 */
	public static void physicsCalculations(){

		for (int i = 0; i < projectiles.size(); i++){

			//remove the far gone projectiles or ones with a dead partner... *sniffle*
			if (projectiles.get(i).isOutOfZone() || projectiles.get(i).isToBeDestroyed()){
				projectiles.remove(i);
				i--;
				continue;
			}
			projectiles.get(i).update();
		}



		for (int p = 0; p < projectiles.size(); p++){
			if (projectiles.get(p).isStuck()) continue; //skip if stuck already
			//this optimizes and makes it set the color only once it hits, not each loop through	
			for (int t = 0; t < targets.size(); t++){
				if (targets.get(t).isColliding(projectiles.get(p))){

					projectiles.get(p).setStuck(true, targets.get(t)); //stick projectiles to target

				}
			}
		}
		

		for (int i = 0; i < ropes.size(); i++){
			Projectile[] attachedProjs = ropes.get(i).getAttachedProjectiles();
			
			//remove ropes that have lost a partner :(
			if (ropes.get(i).lostProjectile()){
				for (int p = 0; p < attachedProjs.length; p++){
					if (attachedProjs[p] != null){
						attachedProjs[p].setToBeDestroyed(true);
					}
				}
				ropes.remove(i);
				i--;
				continue;
			}
			
			if (attachedProjs[0].getTargetStuckTo() == null || attachedProjs[1].getTargetStuckTo() == null) continue;

			
			boolean tempProjZeroIsLaterColomn = (attachedProjs[0].getTargetStuckTo().getColomn() > attachedProjs[1].getTargetStuckTo().getColomn());
			
			Target target1 = (tempProjZeroIsLaterColomn) ? attachedProjs[1].getTargetStuckTo() :  attachedProjs[0].getTargetStuckTo();
			Target target2 = (tempProjZeroIsLaterColomn) ? attachedProjs[0].getTargetStuckTo() :  attachedProjs[1].getTargetStuckTo();
			
			//TODO check if targets are already occupied!!!!
			
			if (ropes.get(i).isSolidified() || tryDoGameMove(target1, target2)){
				
				ropes.get(i).setSolidified(true);
				
				
				//target1.setCaptured( (playerTurn) ? 1 : 2 );
				//TODO color the targets
				
			}else{
				//remove the balls
				attachedProjs[0].setToBeDestroyed(true);
				attachedProjs[1].setToBeDestroyed(true);
			}
			
			
//			targets.get(t).setColor( (playerTurn) ? teamColor[1] : teamColor[2] );
			
				
		}
		
		
		//end of rope loop
		
		
		
		
	}

	private static void render(){

		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		glLoadIdentity();

		camera.doTranslations();


		//render sphere room
		renderSphere(new Point3D(0, 0, 0), Color.BLACK, GLU.GLU_LINE, new Rotation(90, 0, roomSpin), Projectile.projectileMaxDisplacement, 10, 10);
		//round and round we go!
		roomSpin +=0.25;
		roomSpin %= 360;

		//renderSphereTriangle(-1.0f, 0.0f, -5.0f, Color.YELLOW, 0.25f, 5, 0.25f);

		//draw platform cylinder
		glColor3f(0.5f, 0.2f, 0.8f);
		glPushMatrix();
		glTranslatef(0, -1f, 0);
		glRotated(90, 1, 0, 0);
		Cylinder patCyl = new Cylinder();
		patCyl.setDrawStyle(GLU.GLU_LINE);
		patCyl.draw(1.5f, 1.5f, 50.0f, 8, 1); //basically sets the radius, and number of rows/columns of
		//vertices that make up the circle
		glPopMatrix(); //remove any translations made


		//draw platform cylinder
		glColor3f(0.5f, 0.2f, 0.8f);
		glPushMatrix();
		glTranslatef(0, -1f, 0);
		glRotated(90, 1, 0, 0);
		Disk platDisk = new Disk();
		platDisk.setDrawStyle(GLU.GLU_LINE);
		platDisk.draw(1.5f, 0.0f, 8, 10);
		glPopMatrix(); 


		for (int i = 0; i < targets.size(); i++){
			targets.get(i).draw();
		}

		for (int i = 0; i < projectiles.size(); i++){
			projectiles.get(i).draw();
		}

		for (int i = 0; i < ropes.size(); i++){
			ropes.get(i).draw();
		}

		//draw Crosshair
		glLoadIdentity();
		renderSphere(new Point3D(0, 0, -0.1f), Color.BLACK, GLU.GLU_FILL, new Rotation(), 0.00025f, 20, 20);


	}

	

	private static void renderSphere(Point3D pos, Color color, int drawStyle, Rotation rotation,  float radius, int row, int col) {
		glPushMatrix();

		glTranslatef(pos.x, pos.y, pos.z);
		glRotatef(rotation.pitch, 1, 0, 0);
		glRotatef(rotation.yaw, 0, 1, 0);
		glRotatef(rotation.roll, 0, 0, 1);

		glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
		Sphere s = new Sphere();

		s.setDrawStyle(drawStyle);
		s.draw(radius, row, col); //basically sets the radius, and number of rows/columns of
		//vertices that make up the circle
		glPopMatrix(); //remove any translations made
	}

	@SuppressWarnings("unused")
	private static void renderSphere(Point3D pos, Color color, int drawStyle, Rotation rotation,  float radius) {
		renderSphere(pos, color, drawStyle, rotation, radius, (int) (radius * 75), (int) (radius * 75));
	}

	private static void checkInput(){


		//keyboard control does not use the event buffer
		//just real time state checking
		camera.doKeyboardControl();

		camera.doMouseControl();



		//run through keyboard events
		while (Keyboard.next()){ //while there are keyboard events in the event buffer
			if (Keyboard.getEventKeyState()){ //true if press event


			}else{ //if release event
				if (Keyboard.getEventKey() == Keyboard.KEY_Q) System.exit(0);
			}


		}
		while (Mouse.next()){ //event is whenever the mouse moves, or is clicked
			if (Mouse.getEventButton() == 2 && Mouse.getEventButtonState() && mouseControl){
				Mouse.setGrabbed(!Mouse.isGrabbed());
			}
			
			/*
			 * Event Buttons:
			 *-1 : none
			 * 0 : left button
			 * 1 : right button
			 * 2 : middle button
			 * 3 : Back (ignore, few people have 3 or 4)
			 * 4 : Forward  
			 */
			if (Mouse.getEventButton() == 0 ){ //only check mouse clicks

				
				//Generate a projectile on click and release
				
				
				if (game.isWon()){
					
					resetGame();
					continue; 
				}
				
				if (Mouse.getEventButtonState()){
					firstProjectile = Projectile.createProjectileFromCamera(camera);
					projectiles.add(firstProjectile);
				}else{
					if (firstProjectile == null) continue;
					secondProjectile = Projectile.createProjectileFromCamera(camera);
					projectiles.add(secondProjectile);
					
					ropes.add(new Rope(firstProjectile, secondProjectile));
				}
				
				destroyOpposition();
				

			}
			
			

		}

	}
	
	/**
	 * Marks problematic {@link Projectile}s for destruction.
	 */
	public static void destroyOpposition(){
		
		for (int i = 0; i < projectiles.size(); i++){
			if ( ( !projectiles.get(i).isStuck() )  && !( firstProjectile == projectiles.get(i) || secondProjectile == projectiles.get(i) ) ){
				projectiles.get(i).setToBeDestroyed(true);
			}
		}
		
	}
	
	/**
	 * Attempts to claim pieces, and returns based on the success.
	 * @param target1 The {@link Target} claimed by the first {@link Projectile}.
	 * @param target2 The {@link Target} claimed by the second {@link Projectile}.
	 * @return True if it was successful. False otherwise (if pieces are not in same row/already claimed/etc.).
	 */
	public static boolean tryDoGameMove(Target target1, Target target2){
		
		if (target1.getRow() != target2.getRow()) return false;
		
		int tempRow = target1.getRow();
	
		int tempDiffCol = Math.abs(target2.getColomn() - target1.getColomn());
		
		//ADD ONE TO THE DIFFERNCE BECAUSE ITS LENGTH FEPFWOFMWIOFCNMQOIF
		
		return game.tryRemoveSelection(tempRow, target1.getColomn(), tempDiffCol + 1);
		
	}
	
	public static void doOtherWin(){
		
		boolean winPlayer = game.getWinningPlayer();
		Color winColor = (winPlayer) ? teamColor[1] : teamColor[2];
		
		for (int i = 0; i < projectiles.size(); i++){
			projectiles.get(i).setColor(winColor);
		}
		
		//change all pices to the winning teams color
		for (int i = 0; i < game.getBoard().size(); i++){
			for (int j = 0; j < game.getBoard().get(i).length; j++){
				game.getBoard().get(i)[j] = (winPlayer) ? 1 : 2;
			}
		}
		for (int i = 0; i < ropes.size(); i++){
			ropes.get(i).setColor(winColor);
		}
		
	}
	
	
	public static void resetGame(){
		//it's a whole new game!
		boolean lastWinPlayer = game.getWinningPlayer();
		
		initGame(); 
		firstProjectile = null; 
		secondProjectile = null;
		game.setPlayerTurn(!lastWinPlayer);
		
	}

	
	public static void setMouseControl(boolean mControl){
		mouseControl = mControl;
		Mouse.setGrabbed(mControl);
	}
	
	/**
	 * 
	 * @param degrees The angle to calculate, in degrees.
	 * @return The sine of that angle.
	 */
	public static double degSin(double degrees){
		return Math.sin(Math.toRadians(degrees));
	}

	/**
     * 
     * @param degrees The angle to calculate, in degrees.
     * @return The cosine of that angle.
     */
	public static double degCos(double degrees){
		return Math.cos(Math.toRadians(degrees));
	}
	
	
	
}
