package org.noip.evan1026.graphics;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

public class Rope {

	private Projectile[] attachedProjectiles;

	private Color color;
	
	private boolean solidified = false;

	/**
	 * Initializes the rope, which is simply defined by the two {@link Projectile}s that it's attached to.
	 * @param proj1 The first {@link Projectile} the rope is attached to.
	 * @param proj2 The second {@link Projectile} the rope is attached to.
	 */
	public Rope(Projectile proj1, Projectile proj2){
		attachedProjectiles = new Projectile[2];
		attachedProjectiles[0] = proj1;
		attachedProjectiles[1] = proj2;
		color = proj1.getColor();
	}
	
	/**
	 * Does some magic OpenGL stuff to draw the rope to the screen.
	 */
	public void draw(){

		if (lostProjectile()) return;

		Point3D p1 = attachedProjectiles[0].getPosition();
		Point3D p2 = attachedProjectiles[1].getPosition();

		Point3D p = new Point3D(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
		Point3D z = new Point3D(0,0,1);
		
		Point3D t = new Point3D(z.y * p.z - p.y * z.z, z.z * p.x - p.z * z.x, z.x * p.y - p.x * z.y);
		
		double dotProd = z.x * p.x + z.y * p.y + z.z * p.z;
		float angle = (float) Math.acos(dotProd / 3f);
		
		
		float distBetweenProjs = (float) Math.sqrt(p.x * p.x + p.y*p.y + p.z*p.z);
		
		if (solidified){
			glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
		}else{ //dimmer color
			glColor3f((color.getRed() + 150)/255.0f, (color.getGreen() + 150)/255.0f, (color.getBlue() + 150)/255.0f);
		}
		
		
		glPushMatrix();
		glTranslatef(-p1.x, -p1.y,  -p1.z); //center of base of cylinder at proj1 point
		
		glRotatef(angle, t.x, t.y, t.z);
		
		Cylinder patCyl = new Cylinder();
		patCyl.setDrawStyle(GLU.GLU_LINE);
		patCyl.draw(0.1f, 0.1f, distBetweenProjs, 20, 20); //basically sets the radius, and number of rows/columns of
		//vertices that make up the circle
		glPopMatrix(); //remove any translations made


//		glLineWidth(0.5f);
//		glBegin(GL_LINES);
//		
//
//
//
//
//		glVertex3f(-p1.x, -p1.y, -p1.z);
//		glVertex3f(-p2.x, -p2.y, -p2.z);
//
//		glEnd();

	}

	/**
	 * 
	 * @return The two {@link Projectile}s that define the rope.
	 */
	public Projectile[] getAttachedProjectiles(){
		return attachedProjectiles;
	}

	/**
	 * 
	 * @return True if either {@link Projectile} is out of bounds or has been marked to be destroyed.
	 */
	public boolean lostProjectile(){
		for (int i = 0; i < attachedProjectiles.length; i++){
			if (attachedProjectiles[i].isOutOfZone() || attachedProjectiles[i].isToBeDestroyed()) return true; 
		}
		return false;
	}

	/**
	 * 
	 * @param solidified Whether or not the rope is "solidified." It is considered solidified when it is connecting two {@link Projectile}s that are both on valid spheres to be connected.
	 */
	public void setSolidified(boolean solidified){
		this.solidified = solidified;
	}

	/**
	 * 
	 * @return Whether or not the rope is "solidified." See setSolidified for a further explaination.
	 */
	public boolean isSolidified(){
		return solidified;
	}
	


}
