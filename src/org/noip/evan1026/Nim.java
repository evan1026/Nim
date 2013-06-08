package org.noip.evan1026;

import javax.swing.JOptionPane;

import org.noip.evan1026.graphics.start;


public class Nim {

	public static void main(String[] args) {

		JOptionPane.showMessageDialog(null, "Welcome to Nim,\nmade by:\nEvan Allan & Brian Decker");

		JOptionPane.showMessageDialog(null, "Controls:\n"+
				"Q : to quit at any time\n"+
				"WASD or Arrow Keys:  camera movement\n" +
				"Space: Ascend\n"+
				"LShift: Descend\n"+
				"Left Mouse Button Push: Fire \"Begin Line\" Projectile\n"+
				"Left Mouse Button Release: Fire \"End Line\" Projectile\n" +
				"Middle Click: Toggle mouse being grabbed by the game window");



		start.begin(args);
	}
}
