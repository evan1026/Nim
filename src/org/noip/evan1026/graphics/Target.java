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
		
		
		private static float radius = 0.025f;
		
		private static int rowVertices = 20;
		
		private static int colVertices = 20;
		
		private Color color;
		
		private static int drawStyle = GLU.GLU_LINE; 
		
		private boolean spinning = true;
		
		
		private double speedConstant = 2.0;
		
	
		
		public void draw(){
			
			
			glPushMatrix();
			
			glTranslatef(-1 * position.x, -1 * position.y, -1 * position.z);
			
			if (rotation.pitch != -1) glRotatef(rotation.pitch, 1, 0, 0);
			if (rotation.yaw != -1) glRotatef(rotation.yaw, 0, 1, 0);
			if (rotation.roll != -1) glRotatef(rotation.roll, 0, 0, 1);

			glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
			Sphere s = new Sphere();
			
			s.setDrawStyle(drawStyle);
			s.draw(radius, rowVertices, colVertices);
			
			glPopMatrix();
			
		}
		
		public boolean isColliding(Projectile pro){
			
			float distBetweenCenters;
			
			
			
			return false;
			
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
		
		public String toString(){
			return "Position: " + getPosition() + "\nRotation: " + getRotation();
		}
	
	
	
}
