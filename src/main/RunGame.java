package main;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import pieces.Piece;
import pieces.PieceBlue;
import pieces.PieceYellow;

public class RunGame extends JFrame implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	public static ArrayList<Piece> pieces; 
	private static int width = 512;
	private static int height = 512;
	private static Board board;
	private boolean placingPhase = true;
	private static int selPiece = 10;
	private static int turnID = 1;
	private static int x;
	private static int y;
	private boolean turnSucess = false;
	private int winCondition;
	private static int phase = 0;
	private static Button options;
	
	
	public RunGame(){
		try {
			setIconImage(ImageIO.read(RunGame.class.getResource("/resources/icon.png")));
		} catch (IOException e) {e.printStackTrace();}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Rota Version 1.0");
		setSize(width, height);
		board = new Board(width, height);
		add(board);
		board.addMouseListener(this);
	}
	
	public static void main(String[] args) {
		pieces = new ArrayList<Piece>(6);
		RunGame frame = new RunGame();
		frame.setVisible(true);
		board.updatePossiblePos(Piece.possiblePos);
		options = new Button(frame.getWidth(), frame.getHeight(), 450,0,500,12);
		while(true){
			//try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			
			//logic
			sendPiecePositions();
			Point a = MouseInfo.getPointerInfo().getLocation();
			Point b = frame.getLocation();
			x = (int) (a.getX() - b.getX());
			y = (int) (a.getY() - b.getY()) - 22;
			
			
			//render
			if(!(x < 0 && y< 0 && x > width && y > height)){
				board.updatePossiblePos(Piece.possiblePos);
				board.updateSelPiece(selPiece);
				board.updatePos(new Point(x,y));
				board.updateTurnID(turnID);
				board.setPhase(phase);
				board.repaint();
			}
			
		}
	}
	
	public static void sendPiecePositions(){
		board.updatePiecePos(pieces);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		echo("Click");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		echo("Press");
		int position = checkForBounding(board.posCoords);
		echo(position);
		turnSucess = false;
		if(position != -1){
			if(placingPhase){
				switch(turnID){
					case(1):
						playerPlacing(1, position);
						break;
					case(2):
						playerPlacing(2,position);
						break;
				}
						
			}else{
				mainTurn(position);
			}
		}
		if(turnSucess){
			turnID++;
			if(turnID > 2){turnID = 1;}
			if(pieces.size() >= 5){
				winCondition = WinConditions.didWin(pieces);
				if(winCondition != -1){
					phase = 1;
				}
			}
		}
		
	}

	private void mainTurn(int pos) {
		if(selPiece >= 0 && selPiece <= 6){
			if(!isPieceThere(pos) && isPossibleMove(pieces.get(selPiece).getPos(), pos)){
				Piece interPiece = pieces.get(selPiece);
				interPiece.setPos(pos);
				pieces.set(selPiece, interPiece);
				selPiece = -1;
				turnSucess = true;
			} else{
				if(pos == pieces.get(selPiece).getPos()){
					selPiece = 10;
				}
			}
		}else{
			if(isPieceThere(pos) && isPlayerColor(pos)){
				selPiece = getIndexOfPiece(pos);
			}
		}
		
	}

	private boolean isPossibleMove(int selPos, int nPos) {
		for(int possiblePos: Piece.possiblePos[selPos]){
			if(possiblePos == nPos){
				return true;
			}
		}
		return false;
	}

	private int getIndexOfPiece(int pos) {
		int i = 0;
		for(Piece piece: pieces){
			if(piece.getPos() == pos){
				return i;
			}
			i++;
		}
		return -1;
	}

	private boolean isPlayerColor(int pos) {
		for(Piece piece: pieces){
			if(piece.getPieceID() == turnID && piece.getPos() == pos){return true;}
		}
		return false;
	}

	private void playerPlacing(int i, int position) {
		if(i == 1){
			if(!isPieceThere(position)){
				pieces.add(new PieceBlue(position));
				echo("piece placed successfully");
				turnSucess = true;
				if(pieces.size() >= 6){
					placingPhase = false;
				}
			}
		}
		if(i ==2){
			if(!isPieceThere(position)){
				pieces.add(new PieceYellow(position));
				echo("piece placed successfully");
				turnSucess = true;
				if(pieces.size() >= 6){
					placingPhase = false;
				}
			}
		}
		
	}

	private boolean isPieceThere(int pos) {
		for(Piece piece: pieces){
			if(piece.getPos() == pos){
				return true;
			}
		}
		return false;
	}

	private int checkForBounding(float[][] posCoords) {
		if(phase  == 1){
			resetGame();
			return -1;
		}
		int sw = getWidth() / width;
		int sh = getHeight() / height;
		if(x >= options.bounds.get(0) * sw  && x <= options.bounds.get(2) * sw
				&& y >= options.bounds.get(1) * sh && y <= options.bounds.get(3) * sh){
			echo("Clicked Options");
			if(phase == 0){
				phase = -1;
			}else{
				if(phase == -1){
					phase = 0;
				}
			}
			
			return -1;
		}
		sw = getWidth() /8;
		sh = getHeight() / 8;
		
		for(int i = 0; i <= 8; i++){
			if(x >= posCoords[i][0] * sw - 30 && x <= posCoords[i][0] * sw + 30
					&& y >= posCoords[i][1] * sh - 45 && y <= posCoords[i][1] * sh + 15){
				return i;
				}
		}
		
		return -1;
	}

	

	@Override
	public void mouseReleased(MouseEvent e) {
		echo("Release");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		echo("Entered");		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		echo("Exited");
	}
	
	
	public void echo(Object s){
		//System.out.println(s);
	}
	
	
	
	private void resetGame() {
		pieces.clear();
		placingPhase = true;
		selPiece = 10;
		turnID = 1;
		turnSucess = false;
		phase = 0;
		board.reset();
		
		echo("reset Vars");
	}

}
