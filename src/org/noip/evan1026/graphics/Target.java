package org.noip.evan1026.graphics;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Color;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

public class Target {


	private Point3D position;
	private Rotation rotation;

	private int row;
	private int colomn;
	
	public static float radius = 0.5f;

	private static int SphereRowVertices = 20;

	private static int SphereColVertices = 20;


	private static int drawStyle = GLU.GLU_LINE; 

	//initial is 0
	private Color color = start.teamColor[0];

	public Target(Point3D position, Rotation rotation, int row, int colomn){

		this.position = new Point3D(position.x, position.y, position.z);
		this.rotation = new Rotation(rotation.pitch, rotation.yaw, rotation.roll);

		this.row = row;
		this.colomn = colomn;

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
		s.draw(radius, SphereRowVertices, SphereColVertices);

		glPopMatrix();

	}

	public boolean isColliding(Projectile pro){

		float dX = Math.abs(this.position.x - pro.getPosition().x);
		float dY = Math.abs(this.position.y - pro.getPosition().y);
		float dZ = Math.abs(this.position.z - pro.getPosition().z);


		//The Pythagorean theorem works in three dimensions.. neat!
		float distance = (float) Math.sqrt(
				Math.pow(dX, 2) +
				Math.pow(dY, 2) + 
				Math.pow(dZ, 2));


		return distance < Projectile.radius + Target.radius;

	}



	public Point3D getPosition(){
		return position;
	}
	public void setPosition(Point3D position){
		this.position = position;
	}

	public Rotation getRotation(){
		return rotation;
	}

	public void setRotation(Rotation rotation){
		this.rotation = rotation;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}

	public String toString(){
		return "Position: " + getPosition() + "\nRotation: " + getRotation();
	}



}
