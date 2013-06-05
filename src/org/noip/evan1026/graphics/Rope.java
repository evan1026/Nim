package org.noip.evan1026.graphics;

import java.util.ArrayList;

public class Rope {
	
	private Projectile[] attachedProjectiles;
	
	public Rope(Projectile proj1, Projectile proj2){
		attachedProjectiles = new Projectile[2];
		attachedProjectiles[0] = proj1;
		attachedProjectiles[1] = proj2;
	}
	
}
