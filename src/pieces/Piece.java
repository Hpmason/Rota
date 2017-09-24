package pieces;

public class Piece {
	public static int[][] possiblePos = 
			{
					{1,2,3,4,5,6,7,8},//0
					{0,2,3},//1
					{0,1,4},//2
					{0,1,5},//3
					{0,2,6},//4
					{0,3,7},//5
					{0,4,8},//6
					{0,5,8},//7
					{0,6,7} //8
			};
	private int curPos;
/*		   /1\
 * 		/2	|	3\
 * 	   4--	0	--5
 * 		\6	|	7/
 * 		   \8/
 */
	protected int pieceID;
	public Piece(int position) {
		this.curPos= position;
		this.pieceID = 0;
	}
	public boolean isPossibleMove(int pos){
		for(int i = 0; i < possiblePos[curPos].length; i++){
			if(possiblePos[curPos][i] == pos){
				return true;
			}
		}
		
		return false;
	}
	
	public int getPos(){
		return curPos;	
	}
	public int[][] getPossiblePos(){
		return possiblePos;
	}
	public int getPieceID(){
		return pieceID;
	}
	public void setPos(int nPos){
		this.curPos = nPos;
	}
}
