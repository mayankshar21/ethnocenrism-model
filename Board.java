/*Team members:
 * Lihang Tian 975404
 * Mayank Sharma 936970
 * Yuanlong Zhang 772312
 * Date: 27 May 2019
 */
import java.awt.Point;

/**
 * This class serves as game board, contains the functions to count the agent numbers
 * @author JIMMY
 *
 */
public class Board {
	
	private int size;
	private Agent[][] board ;
	
	
	public Board() {
		super();
		
		this.size = Integer.parseInt(Config.getProperty("LATTICE_SIZE"));
		
		//set the default values to null
		board = new Agent[size][size];
		
	}
	
	/**
	 * return the agent that sit in the point if exist, otherwise return null
	 * @param col
	 * @param row
	 * @return
	 */
	public Agent getPoint(int col, int row) {
		return board[row][col];
	}
	
	/**
	 * Count the number of Altruisists agents, looking for is "FULL_CIRCLE" shape 
	 * through the board 
	 * @return
	 */
	public int getAltruistsNumber() {
		int num = 0;
		//checking each cells
		for (Agent[] row : board) {
			for (Agent col: row) {
				if (col != null && col.getShape() == Agent.Shape.FULL_CIRCLE) {
					num++;
				}
			}
		}
		
		return num;
	}
	
	/**
	 *  Count the number of Ethnocentrics agents, looking for is "EMPTY_CIRCLE" shape 
	 * through the board 
	 * @return
	 */
	public int getEthnocentricsNumber() {
		int num = 0;
		//checking each cells
		for (Agent[] row : board) {
			for (Agent col: row) {
				if (col != null && col.getShape() == Agent.Shape.EMPTY_CIRCLE) 
					num++;
			}
		}
		return num;
	}
	
	/**
	 * Count the number of Cosmopolitan agents, looking for is "FULL_SQUARE" shape 
	 * through the board 
	 * @return
	 */
	public int getCosmopolitanNumber() {
		int num = 0;
		//checking each cells
		for (Agent[] row : board) {
			for (Agent col: row) {
				if (col != null && col.getShape() == Agent.Shape.FULL_SQUARE) 
					num++;
			}
		}
		return num;
	}
	
	/**
	 * Count the number of Egoist agents, looking for is "EMPTY_SQUARE" shape 
	 * through the board 
	 * @return
	 */
	public int getEgoistNumber() {
		int num = 0;
		//checking each cells
		for (Agent[] row : board) {
			for (Agent col: row) {
				if (col != null && col.getShape() == Agent.Shape.EMPTY_SQUARE) 
					num++;
			}
		}
		return num;
	}
	
	/**
	 * Return if there is more free cell on the board
	 * @return
	 */
	public boolean isFull() {
		//checking each cells
		for (Agent[] row : board) {
			for (Agent col: row) {
				if (col == null) 
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 
	 * @return Agent[][]
	 */
	public Agent[][] getGameBoard() {
		return board;
	}
	
	
	/**
	 * Add agent to special board cell 
	 * <p><b>Usage</b>: Board.addAgent(agent)
	 * @param point
	 * @param agent
	 */
	public void addAgent(Agent agent) {
		Point p = agent.getCoordinates();
		board[(int) p.getY()][(int) p.getX()] = agent;
	}
	
	/**
	 * remove the existing agent from the board
	 * @param point
	 */
	public void removeAgent(Point point) {
		board[(int) point.getY()][(int) point.getX()] = null;
	}
	
	/**
	 * Count the number of existing agent on the board
	 * @return
	 */
	public int agentNums() {
		int count = 0;
		for (Agent[] row : board) {
			for (Agent col: row) {
				if(col != null)
					count++;
			}
		}
		return count;
	}
	
	/**
	 * This function review the agents details inside the board
	 */
	@Override
	public String toString() {
		String str = "";
		for (Agent[] row : board) {
			for (Agent col: row) {
				str += col + "\t";
			}
			str += "\n";
		}
		return str;
	}
	
	/**
	 * This function only show the agents in shape 
	 * @return
	 */
	public String printBoard() {
		String str = "";
		for (Agent[] row : board) {
			for (Agent col: row) {
				if (col == null) {
					str += "  " + "\t";
				}else {
					switch (col.getShape()) {
					case EMPTY_CIRCLE:
						str += "ET" + "\t";
						break;
					case EMPTY_SQUARE:
						str += "EG" + "\t";
						break;
					case FULL_CIRCLE:
						str += "AL" + "\t";
						break;
					case FULL_SQUARE:
						str += "CO" + "\t";
						break;
					}
				}
				
			}
			str += "\n";
		}
		return str;
	}
	
}
