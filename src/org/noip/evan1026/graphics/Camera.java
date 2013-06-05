package org.noip.evan1026.graphics;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

public class Camera {

	private Point3D position;

	private Rotation rotation;

	private boolean movable = true;
	private float speed = 0.05f;


	private float yFOV;
	private float aspectRatio;
	private float zNear;
	private float zFar;


	private static final float pitchChange = 1f;
	private static final float yawChange = 1f;



	public Camera(Point3D position, Rotation rotation, float yFOV, float aspRatio, float zNear, float zFar){

		this.position = new Point3D(position.x, position.y, position.z);

		this.rotation = new Rotation(rotation.pitch, rotation.yaw, rotation.roll);

		this.yFOV = yFOV;
		this.aspectRatio = aspRatio;
		this.zNear = zNear;
		this.zFar = zFar;
	}

	public void initPerspective(){

		glPushAttrib(GL_TRANSFORM_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(yFOV, aspectRatio, zNear, zFar);
		glPopAttrib();


	}


	public void doTranslations(){

		glPushAttrib(GL_TRANSFORM_BIT);
		glMatrixMode(GL_MODELVIEW);
		glRotatef(rotation.pitch, 1, 0, 0);
		glRotatef(rotation.yaw, 0, 1, 0);
		glRotatef(rotation.roll, 0, 0, 1);
		glTranslatef(position.x, position.y, position.z); //x y z of camera		
		glPopAttrib();

	}






	public void doKeyboardControl(){

		if (this.isMovable()){ //can the camera move around, or should it just look?

			boolean left = Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT);
			boolean right = Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT);

			boolean ascend = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
			boolean descend = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

			boolean forward = Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP);
			boolean backward = Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);


			position.y += (ascend) ? -speed : (descend) ? speed: 0;
			//position.x += (left) ? speed : (right) ? -speed : 0;

			if (forward){
				position.x += ( -speed *  degSin(getRotation().yaw));

				position.z += (speed * degCos(getRotation().yaw));
			}
			else if (backward){
				position.x -= ( -speed *  degSin(getRotation().yaw));

				position.z -= (speed * degCos(getRotation().yaw));
			}
			if (left){
				position.x += ( -speed *  degSin(getRotation().yaw - 90));

				position.z += (speed * degCos(getRotation().yaw - 90));
			}
			else if  (right){
				position.x += ( -speed *  degSin(getRotation().yaw + 90));

				position.z += (speed * degCos(getRotation().yaw + 90));
			}

			//position.z += (forward) ? speed : (backward) ? -speed : 0;			

		}


	}

	public void doMouseControl(){

		if (Mouse.isGrabbed()){


			//handle camera looking around
			float dX = Mouse.getDX() * 0.1f;
			float dY = Mouse.getDY() * 0.1f;

			//pitch
			if (rotation.pitch - dY >= -90 && rotation.pitch - dY <= 90) {
				rotation.pitch += -dY;
			}

			rotation.yaw = (rotation.yaw + dX) % 360;

			rotation.roll = (rotation.roll + Mouse.getDWheel() / 120.0f) % 360;

		}


	}


	public Point3D getPosition(){
		return position;
	}

	public void setPositon(Point3D position){
		this.position = new Point3D(position.x, position.y, position.z);
	}

	public Rotation getRotation(){
		return rotation;
	}

	public void setRotation(Rotation rotation){
		this.rotation = new Rotation(rotation.pitch, rotation.yaw, rotation.roll);
	}

	public boolean isMovable() {
		return movable;
	}


	public void setMovable(boolean movable) {
		this.movable = movable;
	}


	public float getSpeed() {
		return speed;
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}


	public float getyFOV() {
		return yFOV;
	}


	public void setyFOV(float yFOV) {
		this.yFOV = yFOV;
	}


	public float getAspectRatio() {
		return aspectRatio;
	}


	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}


	public float getzNear() {
		return zNear;
	}


	public void setzNear(float zNear) {
		this.zNear = zNear;
	}


	public float getzFar() {
		return zFar;
	}


	public void setzFar(float zFar) {
		this.zFar = zFar;
	}

	private static double degSin(double degrees){
		return Math.sin(Math.toRadians(degrees));
	}

	private static double degCos(double degrees){
		return Math.cos(Math.toRadians(degrees));
	}

	private static double degTan(double degrees){
		return Math.tan(Math.toRadians(degrees));
	}



}
