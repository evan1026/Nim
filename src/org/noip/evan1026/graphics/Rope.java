package org.noip.evan1026.graphics;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Rope {
	
	private Projectile[] attachedProjectiles;
	
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
	}
	
	/**
	 * Does some magic OpenGL stuff to draw the rope to the screen.
	 */
	public void draw(){
		
		if (lostProjectile()) return;
		
		Point3D p1 = attachedProjectiles[0].getPosition();
		Point3D p2 = attachedProjectiles[1].getPosition();
		
		glLineWidth(0.5f);
		glBegin(GL_LINES);
		if (solidified){
			glColor3f(0f, 1f, 1f);
		}else{
			glColor3f(1f, 0f, 0f);
		}
		
		glVertex3f(-p1.x, -p1.y, -p1.z);
		glVertex3f(-p2.x, -p2.y, -p2.z);
		
		glEnd();
		
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
