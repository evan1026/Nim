package org.noip.evan1026.graphics;

public class Point3D {
	
    /**
     * The x coordinate of the point.
     */
	public float x;
	
	/**
	 * The y coordinate of the point.
	 */
	public float y;
	
	/**
	 * The z coordinate of the point.
	 */
	public float z;
	
	/**
	 * Calls the other constructor with 3 zeros as parameters.
	 */
	public Point3D(){
		this(0,0,0);
	}
	
	/**
	 * 
	 * @param x The x coordinate of the point.
	 * @param y The y coordinate of the point.
	 * @param z The z coordinate of the point.
	 */
	public Point3D(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString(){
		return "(" + x + ", " + y + ", " + z + ")"; 
	}
}
