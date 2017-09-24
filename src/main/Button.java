package main;

import java.util.Vector;

public class Button {
	
	Vector<Integer> bounds;
	
	public Button(int w, int h, int xbound1,int ybound1, int xbound2, int ybound2) {
		this.bounds = new Vector<Integer>();
		this.bounds.add(xbound1);
		this.bounds.add(ybound1);
		this.bounds.add(xbound2);
		this.bounds.add(ybound2);
	}
	public Vector<Integer> getBounds(){
		return bounds;
	}
}
