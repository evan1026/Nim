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

	public static float radius = 0.025f; 

	private static int rowVertices = 20;

	private static int colVertices = 20;

	private Color color;

	private static int drawStyle = GLU.GLU_LINE; 


	private double speedConstant = 2.0;

	private boolean stuck = false;


	public Projectile(){

		this(new Point3D(0,0,0), new Rotation(0, 0, 0), new Point3D(0,0,0), Color.WHITE);

	}

	public Projectile(Point3D position, Rotation rotationSpeed, Point3D velocities, Color color){
		this.position = new Point3D(position.x,position.y,position.z);
		this.color = color;
		this.rotationSpeed = new Rotation(rotationSpeed.pitch, rotationSpeed.yaw, rotationSpeed.roll);
		this.velocity = new Point3D(velocities.x, velocities.y, velocities.z);
	}

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

	
	public boolean isStuck(){
		return stuck;
	}
	
	public void setStuck(boolean stuck){
		this.stuck = stuck;
	}


	public Point3D getPosition(){
		return position;
	}
	public void setPosition(Point3D position){
		this.position = position;
	}


	public Point3D getVelocity(){
		return velocity;
	}

	public void setVelocity(Point3D velocities){
		this.velocity = velocities;
	}

	public Rotation getRotation(){
		return rotation;
	}

	public void setRotation(Rotation rotation){
		this.rotation = rotation;
	}

	public String toString(){
		return "Position: " + getPosition() + "\nRotation: " + getRotation() + "\nVelocity: " + getVelocity();
	}


}
