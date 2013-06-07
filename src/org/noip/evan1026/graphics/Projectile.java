package org.noip.evan1026.graphics;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Color;


import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;


public class Projectile {

	private Point3D position;

	private Point3D velocity;

	private Rotation rotation = new Rotation(); //just start it upright

	private Rotation rotationSpeed;

	private Color color;

	private static int drawStyle = GLU.GLU_LINE; 

	private double speedConstant = 1.0;

	private boolean stuck = false;
	
	private Target targetStuckTo = null;

	private boolean toBeDestroyed = false;

    private static final int rowVertices = 20;

    private static final int colVertices = 20;
	
	public static final float radius = 0.025f; 

    public static final float projectileMaxDisplacement = 50;
	
    /**
     * Calls the other constructor with a {@link Point3D}(0,0,0), a {@link Rotation}(0,0,0), a {@link Point3D}(0,0,0),
     * and {@link Color}.WHITE
     */
	public Projectile(){
		this(new Point3D(0,0,0), new Rotation(0, 0, 0), new Point3D(0,0,0), Color.WHITE);
	}

	/**
	 * Main constructor. The parameter names speak for themselves.
	 * @param position 
	 * @param rotationSpeed
	 * @param velocity
	 * @param color
	 */
	public Projectile(Point3D position, Rotation rotationSpeed, Point3D velocity, Color color){
		this.position = new Point3D(position.x,position.y,position.z);
		this.color = color;
		this.rotationSpeed = new Rotation(rotationSpeed.pitch, rotationSpeed.yaw, rotationSpeed.roll);
		this.velocity = new Point3D(velocity.x, velocity.y, velocity.z);
	}

	/**
	 * Does some magic OpenGL stuff to draw the projectile to the screen.
	 */
	public void draw(){


		glPushMatrix();

		glTranslatef(-1 * position.x, -1 * position.y, -1 * position.z);

		glRotatef(rotation.pitch, 1, 0, 0);
		glRotatef(rotation.yaw, 0, 1, 0);
		glRotatef(rotation.roll, 0, 0, 1);

		glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
		Sphere s = new Sphere();

		s.setDrawStyle(drawStyle);
		s.draw(radius, rowVertices, colVertices);

		glPopMatrix();

	}

	/**
	 * Updates the projectile's position based on it's velocity and whether or not it's stuck in place.
	 */
	public void update(){
		if (!stuck){
			position.x += velocity.x * speedConstant;

			position.y += velocity.y * speedConstant;

			position.z += velocity.z * speedConstant;

			rotation.pitch = (rotation.pitch + rotationSpeed.pitch) % 360;
			rotation.yaw = (rotation.yaw + rotationSpeed.yaw) % 360;
			rotation.roll = (rotation.roll + rotationSpeed.roll) % 360;
		}
	}

	/**
	 * Creates a projectile that appears to shoot out of the camera by using the camera's pitch and yaw to determine
	 * the velocity of the projectile.
	 * @param camera The camera it's shooting out of.
	 * @return The generated projectile.
	 */
	public static Projectile createProjectileFromCamera(Camera camera){
		
		
		//generate the velocity vector of the projectile
		//according to the angle of the camera
		//took a few minutes to think this through
		float xVel, yVel, zVel;
		xVel = (float) ( -1 *  start.degSin(camera.getRotation().yaw) * start.degSin(camera.getRotation().pitch + 90) );
		yVel = (float) ( -1 *  start.degCos(camera.getRotation().pitch + 90) );
		zVel = (float) ( start.degCos(camera.getRotation().yaw) * start.degSin(camera.getRotation().pitch + 90) );
		float velocityMult = 0.05f;
		
		Projectile tempProjectile = new Projectile(
				new Point3D(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z),
				new Rotation(2,2,2),
				new Point3D(xVel * velocityMult, yVel * velocityMult, zVel * velocityMult),
				(start.game.isPlayer1Turn()) ? start.teamColor[1] : start.teamColor[2]
				);
		
		
		return tempProjectile;
		
	}
	
	/**
	 * 
	 * @return True if the ball is out of a predefined sphere around the game. False otherwise.
	 */
	public boolean isOutOfZone(){
		float projDist = (float) Math.sqrt(Math.pow(this.getPosition().x, 2) + Math.pow(this.getPosition().y, 2) + Math.pow(this.getPosition().z, 2));
		
		return projDist > projectileMaxDisplacement;
	}
	
	/**
	 * 
	 * @param destroy True if the projectile should be marked to be destroyed. False otherwise.
	 */
	public void setToBeDestroyed(boolean destroy){
		toBeDestroyed = destroy;
	}
	
	/**
	 * 
	 * @return True if the projectile is marked to be destroyed. False otherwise.
	 */
	public boolean isToBeDestroyed(){
		return toBeDestroyed;
	}
	
	/**
	 * 
	 * @return The {@link Target} that the projectile has latched onto like a parasite.
	 */
	public Target getTargetStuckTo(){
		return targetStuckTo;
	}
	
	/**
	 * 
	 * @return True if the projectile is attached to a {@link Target}.
	 */
	public boolean isStuck(){
		return stuck;
	}
	
	/**
	 * 
	 * @param stuck If the projectile is stuck to a {@link Target}.
	 * @param target The {@link Target} it's stuck to.
	 */
	public void setStuck(boolean stuck, Target target){
		this.stuck = stuck;
		targetStuckTo = target;
	}
	
	/**
	 * 
	 * @return The {@link Color} of the projectile.
	 */
	public Color getColor(){
		return color;
	}
	
	/**
	 * 
	 * @param color The new {@link Color}.
	 */
	public void setColor(Color color){
		this.color = color;
	}

	/**
	 * 
	 * @return The position, as a {@link Point3D}.
	 */
	public Point3D getPosition(){
		return position;
	}
	
	/**
	 * 
	 * @param position The new position, as a {@link Point3D}.
	 */
	public void setPosition(Point3D position){
		this.position = position;
	}

	/**
	 * 
	 * @return The velocity, represented as a {@link Point3D} (treated as a vector, rather than a point).
	 */
	public Point3D getVelocity(){
		return velocity;
	}

	/**
	 * 
	 * @param velocities The new velocity, represented as a {@link Point3D} (treated as a vector, rather than a point).
	 */
	public void setVelocity(Point3D velocities){
		this.velocity = velocities;
	}

	/**
	 * 
	 * @return The {@link Rotation} of the projectile.
	 */
	public Rotation getRotation(){
		return rotation;
	}

	/**
	 * 
	 * @param rotation The new {@link Rotation} of the projectile.
	 */
	public void setRotation(Rotation rotation){
		this.rotation = rotation;
	}

	public String toString(){
		return "Position: " + getPosition() + "\nRotation: " + getRotation() + "\nVelocity: " + getVelocity();
	}


}
