package org.noip.evan1026.graphics;

public class Rotation {
	public float pitch, yaw, roll;
	
	public Rotation(){
		this(0,0,0);
	}
	
	public Rotation(float pitch, float yaw, float roll){
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}
	
	public String toString(){
		return "Pitch: " + pitch + "\tYaw: " + yaw + "\tRoll: " + roll;
	}
}
