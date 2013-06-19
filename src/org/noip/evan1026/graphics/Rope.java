package org.noip.evan1026.graphics;

import java.awt.Color;
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
		color = proj2.getColor();
	}
	
	/**
	 * Does some magic OpenGL stuff to draw the rope to the screen.
	 */
	public void draw(){

		if (lostProjectile()) return;

		Point3D p1 = attachedProjectiles[0].getPosition();
		Point3D p2 = attachedProjectiles[1].getPosition();

		//Find the vector between them
		Point3D difference = new Point3D(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
		float distBetweenProjs = (float) Math.sqrt(difference.x * difference.x + difference.y*difference.y + difference.z*difference.z);
		difference.x /= distBetweenProjs; //normalizing the vector here
		difference.y /= distBetweenProjs; //same here
		difference.z /= distBetweenProjs; //and here
		
		//Find the vector representing how the cylinder normally faces
		Point3D defaultCylinderDirection = new Point3D(0,0,1);
		
		//Find the axis to rotate defaultCylinderDirection about to get to distBetweenProjs
		Point3D crossProduct = new Point3D(defaultCylinderDirection.y * difference.z - difference.y * defaultCylinderDirection.z, 
		                                   defaultCylinderDirection.z * difference.x - difference.z * defaultCylinderDirection.x, 
		                                   defaultCylinderDirection.x * difference.y - difference.x * defaultCylinderDirection.y);
		
		//Angle between vectors = acos(dot product of the vectors)
		double dotProd = defaultCylinderDirection.x * difference.x + 
		                 defaultCylinderDirection.y * difference.y + 
		                 defaultCylinderDirection.z * difference.z;
		float angle = (float) Math.acos(dotProd);
		
		//Make sure to convert to degrees
		angle = (float) (angle * 180 / Math.PI);

		if (solidified){
			glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
		}else{ //dimmer color
			glColor3f((color.getRed() + 150)/255.0f, (color.getGreen() + 150)/255.0f, (color.getBlue() + 150)/255.0f);
		}

		glPushMatrix();
		glTranslatef(-p1.x, -p1.y,  -p1.z); //center of base of cylinder at proj1 point
		
		//Rotate about the axis
		glRotatef(angle, crossProduct.x, crossProduct.y, crossProduct.z);
		
		Cylinder patCyl = new Cylinder();
		patCyl.setDrawStyle(GLU.GLU_LINE);
		patCyl.draw(Projectile.radius, Projectile.radius, distBetweenProjs, 20, 20); //basically sets the radius, and number of rows/columns of
		//vertices that make up the circle
		glPopMatrix(); //remove any translations made


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
	
	public void setColor(Color c){
		color = c;
	}
	
	public Color getColor(){
		return color;
	}


}
