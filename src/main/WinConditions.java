package main;

import java.util.ArrayList;

import pieces.Piece;

public class WinConditions {
	/*		   /1\
	 * 		/2	|	3\
	 * 	   4--	0	--5
	 * 		\6	|	7/
	 * 		   \8/
	 */
	static int[] transList1 = {1,3,5,7,8,6,4,2};
	//						   0,1,2,3,4,5,6,7
	static ArrayList<Integer> posWrap = new ArrayList<Integer>();
	static int winPlayer;
	
	public static int didWin(ArrayList<Piece> pieces){
		winPlayer = -1;
		for(int i: transList1){
			posWrap.add(i);
		}
		if(checkCircle(pieces) || checkAcross(pieces)){
			return winPlayer;
		}
		
		
		
		return winPlayer;
	}

	private static boolean checkAcross(ArrayList<Piece> pieces) {
		ArrayList<Integer> checkList = new ArrayList<Integer>();
		for(int k = 1; k <= 2; k++){
			for(Piece piece: pieces){
				if(piece.getPieceID() == k){
					checkList.add(piece.getPos());
				}
			}
			if(checkList.size() == 3){
				if(checkList.get(0) + checkList.get(1) + checkList.get(2) == 9 && !(checkList.contains(1) && checkList.contains(2) && checkList.contains(6)) && !(checkList.contains(1) && checkList.contains(3) && checkList.contains(5))){
					winPlayer = k;
					//System.out.println("Win by across");
					return true;
				}
			}
			checkList.clear();
		}
		return false;
	}

	private static boolean checkCircle(ArrayList<Piece> pieces) {
		ArrayList<Integer> checkList = new ArrayList<Integer>();
		for(int k = 1; k <= 2; k++){
			for(Piece piece: pieces){
				if(piece.getPieceID() == k && piece.getPos() != 0){
					checkList.add(piece.getPos());
				}else{
					if(piece.getPos() != 0 && piece.getPieceID() == k){
						checkList.clear();
						break;
					}
				}
			}
			
			if(checkList.size() == 3){
				//System.out.println(posWrap.indexOf(checkList.get(0)) + posWrap.indexOf(checkList.get(1)) + posWrap.indexOf(checkList.get(2)) - Math.min(Math.min(posWrap.indexOf(checkList.get(0)),posWrap.indexOf(checkList.get(1))), posWrap.indexOf(checkList.get(2))));
				int min = Math.min(Math.min(posWrap.indexOf(checkList.get(0)),posWrap.indexOf(checkList.get(1))), posWrap.indexOf(checkList.get(2)));
				if(posWrap.indexOf(checkList.get(0)) - min + posWrap.indexOf(checkList.get(1)) - min + posWrap.indexOf(checkList.get(2)) - min  == 3 || 
						(checkList.indexOf(1) != -1 && checkList.indexOf(2) != -1) && 
						(checkList.indexOf(4) != -1 || checkList.indexOf(3) != -1)){
					winPlayer = k;
					//System.out.println("Win through circle");
					return true;
				}
			}
			checkList.clear();
			}
		return false;
	}
	public int getWinValue(){
		return winPlayer;
		
	}
}
