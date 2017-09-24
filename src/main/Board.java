package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import pieces.Piece;

public class Board extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private static Font myFont = new Font("Symbol", Font.PLAIN, 15);
	private int phase = 0;
	private int[][] possiblePositions;
	/*		   /1\
	 * 		/2	|	3\
	 * 	   4--	0	--5
	 * 		\6	|	7/
	 * 		   \8/
	 */
	private Point point;
	public float[][] posCoords = {
			{4,4},//0
			{4,2},//1
			{4 - 1.41421f,4 - 1.41421f},//2
			{4 + 1.41421f,4 - 1.41421f},//3
			{2,4},//4
			{6,4},//5
			{4 - 1.41421f,4 + 1.41421f},//6
			{4 + 1.41421f,4 + 1.41421f},//7
			{4,6}//8
			};
	ArrayList<Color> colors = new ArrayList<>();
	private int RADIUS = 30;
	private ArrayList<Piece> pieceList;
	private int selPiece;
	private int turnID;
	private Image image = null;
	private Image logo = null;
	
	public Board(int w, int h) {
		setSize(w, h);
		colors.add(Color.black);
		colors.add(Color.blue);
		colors.add(Color.red);
		try {
			this.logo  = ImageIO.read(Board.class.getResource("/resources/LogoTest.png"));
			this.image = ImageIO.read(Board.class.getResource("/resources/rome.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void paint(Graphics g){
		g.setFont(myFont);
		g.drawImage(image, 0, 0, 
				getWidth(), getHeight(), 
				0, 0, 
				900, 
				596, 
				this);
		switch(phase){
		case(-1):
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			phasePaintOptions(g);
			g.setColor(Color.BLACK);
			g.drawString("Return", 455, 15);
			break;
		case(0):
			phasePaint1(g);
			g.setColor(Color.BLACK);
			g.drawString("Rules", 455, 15);
			break;
		case(1):
			phasePaint2(g);
			break;
			
		}
		if(phase != 0){
			g.setColor(Color.black);
		} else{
			g.setColor(Color.white);
		}
		
		//g.drawString("Mouse Coords: x=" + ((int) point.getX() - 1 ) + " y=" + ((int)point.getY() - 1), 0, getHeight() - 30);
		g.drawString("Game Made by Ludus Lux", 0, getHeight());
		g.drawImage(logo, (int)(getWidth() - logo.getWidth(this) * 1/10), 
				(int)(getHeight() - logo.getHeight(this) *  1/10), getWidth(), getHeight(), 
				0, 0, logo.getWidth(this), logo.getHeight(this), this);
		
	}
	private void phasePaintOptions(Graphics g) {
		g.setColor(Color.BLACK);
		try {
			Scanner s;
			s = new Scanner(Board.class.getResource("/resources/instructions.txt").openStream());
			ArrayList<String> list = new ArrayList<String>();
			while(s.hasNextLine()){
				list.add(s.nextLine());
			}
			s.close();
			int horizontalPos = 12;
			for(String str: list){
				g.drawString(str, 5, horizontalPos);
				horizontalPos += 15;
			}
			
			
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void phasePaint2(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawString("Game Over", getWidth()/2 - 30, getHeight()/2 - 20);
		g.drawString("The Winner: Player " + WinConditions.winPlayer, getWidth()/2 - 30, getHeight()/2);
		g.drawString("Click to Restart", getWidth()/2 - 30, getHeight()/2 + 20);
	}
	
	
	
	public void phasePaint1(Graphics g){
		g.setColor(Color.white);
		//g.fillRect(0, 0, getWidth(), getHeight());
		int sw = getWidth()/8;
		int sh = getHeight()/8;
		if(possiblePositions.length >= 0){
			for(int i = 0; i <= 8; i++){
				for(int spot : possiblePositions[i]){
					g.setColor(Color.black);
					g.drawLine((int)(posCoords[i][0] *sw), (int) (posCoords[i][1] *sh), 
							(int) (posCoords[spot][0] *sw), (int) (posCoords[spot][1]*sh));
					g.setColor(Color.green);
					g.drawOval((int) (posCoords[i][0]*sw),(int) (posCoords[i][1]*sh), 2, 2);
				}
			}
		}
		
		
		
		for(float[] list : posCoords){
			g.setColor(Color.black);
			g.drawOval((int) (sw * list[0] - RADIUS), (int) (sh *list[1] - RADIUS), RADIUS*2, RADIUS*2);
			g.setColor(Color.black);
			g.fillOval((int) (sw * list[0] - RADIUS), (int) (sh *list[1] - RADIUS), RADIUS*2, RADIUS*2);
		}
		
		for(int i = 0; i <= 8; i++){
			for(Piece piece: pieceList){
				if(piece.getPos() == i){
					g.setColor(colors.get(piece.getPieceID()));
					g.fillOval((int) (sw * posCoords[i][0] - RADIUS), (int) (sh * posCoords[i][1] - RADIUS), RADIUS*2, RADIUS*2);
				}
			}
		}
		if(selPiece >=0 && selPiece <= 6){
			g.setColor(Color.CYAN);
			g.drawOval((int) (sw * posCoords[pieceList.get(selPiece).getPos()][0] - RADIUS), 
					(int) (sh * posCoords[pieceList.get(selPiece).getPos()][1] - RADIUS), RADIUS*2, RADIUS*2);
			g.drawOval((int) (sw * posCoords[pieceList.get(selPiece).getPos()][0] - (RADIUS-1)), 
					(int) (sh * posCoords[pieceList.get(selPiece).getPos()][1] - (RADIUS-1)), (RADIUS-1)*2, (RADIUS-1)*2);
		}
		g.setColor(Color.CYAN);
		g.drawRect((int)point.getX() - 1, (int)point.getY() - 1, 2, 2);
		g.setColor(Color.BLACK);
		if(turnID == 1){
			g.setColor(Color.blue);
			g.drawString("Blue Player's Turn", 10, 15);
			
		}
		if(turnID == 2){
			g.setColor(Color.red);
			g.drawString("Red Player's Turn", 10, 15);
			
		}
		g.fillOval(10, 30, 30, 30);
		
		
		
	}
	public void updatePos(Point a){
		this.point = a;
	}
	public void updateSelPiece(int selPiece){
		this.selPiece = selPiece;
	}

	public void updatePiecePos(ArrayList<Piece> list) {
		this.pieceList = list;
	}

	public void updatePossiblePos(int[][] list) {
		this.possiblePositions = list;
	}
	public float[][] getPosCoords(){
		return posCoords;
	}
	public void setPhase(int i) {
		phase = i;
	}
	public void reset() {
		phase = 0;
		
		
	}
	public void updateTurnID(int turnID) {
		this.turnID = turnID;
		
	}
}
