package org.noip.evan1026.graphics;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Color;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.noip.evan1026.Board;

public class Target {


	private Point3D position;
	private Rotation rotation;

	private int row;
	private int colomn;
	
	public static final float radius = 0.5f;

	private static final int SphereRowVertices = 20;

	private static final int SphereColVertices = 20;

	private static int drawStyle = GLU.GLU_LINE; 
	
	private int capturedSide = -1; //-1 = no one

	/**
	 * 
	 * @param position The {@link Point3D} position in the world.
	 * @param rotation The {@link Rotation}.
	 * @param row The row of the {@link Board} of this piece.
	 * @param colomn The column (index) of the {@link Board} within the row.
	 */
	public Target(Point3D position, Rotation rotation, int row, int colomn){

		this.position = new Point3D(position.x, position.y, position.z);
		this.rotation = new Rotation(rotation.pitch, rotation.yaw, rotation.roll);

		this.row = row;
		this.colomn = colomn;

	}

	/**
	 * Does some magic OpenGL stuff to draw the target to the screen.
	 */
	public void draw(){

		glPushMatrix();

		glTranslatef(-1 * position.x, -1 * position.y, -1 * position.z);

		glRotatef(rotation.pitch, 1, 0, 0);
		glRotatef(rotation.yaw, 0, 1, 0);
		glRotatef(rotation.roll, 0, 0, 1);
		
		Color capColor = start.teamColor[ start.game.getPiece(row, colomn) ];
		
		glColor3f(capColor.getRed()/255.0f, capColor.getGreen()/255.0f, capColor.getBlue()/255.0f);
		Sphere s = new Sphere();

		s.setDrawStyle(drawStyle);
		s.draw(radius, SphereRowVertices, SphereColVertices);

		glPopMatrix();

	}

	/**
	 * 
	 * @param pro The {@link Projectile} with which to check for collision.
	 * @return True if the target is colliding with the {@link Projectile}. False otherwise.
	 */
	public boolean isColliding(Projectile pro){

		float dX = Math.abs(this.position.x - pro.getPosition().x);
		float dY = Math.abs(this.position.y - pro.getPosition().y);
		float dZ = Math.abs(this.position.z - pro.getPosition().z);

		//The Pythagorean theorem works in three dimensions.. neat!
		//Silly Brian. It works in any number of dimensions. 
		float distance = (float) Math.sqrt(
				Math.pow(dX, 2) +
				Math.pow(dY, 2) + 
				Math.pow(dZ, 2));

		return distance < Projectile.radius + Target.radius;

	}

	/**
	 * 
	 * @return Which player has captured this piece.
	 */
	public int getCaptured(){
		return capturedSide;
	}

	/**
	 * 
	 * @return The target's position within the world.
	 */
	public Point3D getPosition(){
		return position;
	}
	
	/**
	 * 
	 * @param position The new world position for the target.
	 */
	public void setPosition(Point3D position){
		this.position = position;
	}

	/**
	 * 
	 * @return How wonderfully spinny the target is.
	 */
	public Rotation getRotation(){
		return rotation;
	}

	/**
	 * 
	 * @param rotation How spinny you want the target to be.
	 */
	public void setRotation(Rotation rotation){
		this.rotation = rotation;
	}
	
	/**
	 * 
	 * @return The row within the {@link Board}.
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * 
	 * @return The column within the {@link Board}.
	 */
	public int getColomn(){
		return colomn;
	}
	

	public String toString(){
		return "Position: " + getPosition() + "\nRotation: " + getRotation();
	}
}
