package random;

import java.awt.Color;
import java.util.Vector;
/*
 * Used for testing
 * Not longer used in overall program
 * 
 * 
 * 
 */
public class ColorInterator {
	public Color c;
	Vector<Integer> rgb;
	
	public ColorInterator() {
		this.rgb = new Vector<Integer>(3);
		rgb.add(0);
		rgb.add(0);
		rgb.add(0);
		this.c = new Color(rgb.get(0),rgb.get(1),rgb.get(2));
		
	}
	public void iterate(){
		for(int i = 0; i <= 2; i++){
			rgb.set(i, rgb.get(i) + (int)(Math.random() * 3 - 1));
		}
		while(Math.max(Math.max(rgb.get(0), rgb.get(1)), rgb.get(2)) >= 255){
			int index = rgb.indexOf(Math.max(Math.max(rgb.get(0), rgb.get(1)), rgb.get(2)));
			rgb.set(index, 20);
		}
		System.out.println(rgb.get(0) + " " + rgb.get(1) + " "+ rgb.get(2));
	}
	public Color returnColor(){
		this.c = new Color(rgb.get(0), rgb.get(1), rgb.get(2));
		return c;
	}
}
