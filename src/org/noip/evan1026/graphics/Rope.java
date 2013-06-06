package org.noip.evan1026.graphics;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Rope {
	
	private Projectile[] attachedProjectiles;
	
	private boolean solidified = false;
	
	public Rope(Projectile proj1, Projectile proj2){
		attachedProjectiles = new Projectile[2];
		attachedProjectiles[0] = proj1;
		attachedProjectiles[1] = proj2;
	}
	
	
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
	
	
	public Projectile[] getAttachedProjectiles(){
		return attachedProjectiles;
	}
	
	
	public boolean lostProjectile(){
		for (int i = 0; i < attachedProjectiles.length; i++){
			if (attachedProjectiles[i].isOutOfZone() || attachedProjectiles[i].isToBeDestroyed()) return true; 
		}
		return false;
	}
	
	public void setSolidified(boolean solidified){
		this.solidified = solidified;
	}
	
	public boolean isSolidified(){
		return solidified;
	}
	
	
}
