import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Color;


import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;


public class Projectile {
	
	private Point3D position;
	
	private Point3D velocity;

	private Rotation rotation;
	
	
	private static float radius = 0.025f; 
	
	private static int rowVertices = 20;
	
	private static int colVertices = 20;
	
	private Color color;
	
	private static int drawStyle = GLU.GLU_LINE; 
	
	private boolean spinning = true;
	
	
	private double speedConstant = 2.0;
	
	
	public Projectile(){
		
		this(new Point3D(0,0,0), new Rotation(0, -1, -1), new Point3D(0,0,0), Color.WHITE, true);
		
	}
	
	public Projectile(Point3D position, Rotation rotation, Point3D velocities, Color color, boolean spinning){
		this.position = new Point3D(position.x,position.y,position.z);
		this.color = color;
		this.spinning = spinning;
		this.rotation = new Rotation(rotation.pitch, rotation.yaw, rotation.roll);
		this.velocity = new Point3D(velocities.x, velocities.y, velocities.z);
	}
	
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
	
	public void update(){
		
		position.x += velocity.x * speedConstant;
		
		position.y += velocity.y * speedConstant;
		
		position.z += velocity.z * speedConstant;
		
	
		
		if (spinning){
			if (rotation.pitch != -1) 
				rotation.pitch = (rotation.pitch + 10) % 360;
			if (rotation.yaw != -1)
				rotation.yaw = (rotation.yaw + 10) % 360;
			if (rotation.roll != -1)
				rotation.roll = (rotation.roll + 10) % 360;
		}
		
	}
	
	public void setSpinning(boolean spinning){
		this.spinning = spinning;
	}
	
	public Point3D getLocation(){
		return position;
	}
	public void setLocation(Point3D location){
		this.position = location;
	}
	
	
	public Point3D getVelocity(){
		return velocity;
	}
	
	public void setVelocity(Point3D velocities){
		this.velocity = velocities;
	}
	
	public void setRotation(Rotation rotation){
		this.rotation = rotation;
	}
	
	
	
}
