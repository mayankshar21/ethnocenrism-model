/*Team members:
 * Lihang Tian 975404
 * Mayank Sharma 936970
 * Yuanlong Zhang 772312
 * Date: 27 May 2019
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Simulator {

	private static Board board = new Board();
	private static Random rand = new Random(System.currentTimeMillis());
	
	
	public static void main(String[] args) {
		//updating rules
		int i = 0;
		while (i< Integer.parseInt(Config.getProperty("ROUND_NUM"))){
			
			agentSpamming();
			agentInteraction();
			
			//if it use another version with the extension
			if (Config.isExtensionOn()) {
				agentReproduceII();
			}else {
				agentReproduce();
			}
			
			agentDeath();
			
			//printResult(i);
			writeResult(i);
			i++;
		}
		System.out.print("Simulation finished, result generated in to file!\n");
		
	}
	
	/**
	 * write data to a file that named in result_"flag name".txt
	 * @param i
	 */
	private static void writeResult(int i) {
		String content = printResult(i);
		String fileName = "result_"+Config.getFlag()+".txt";
		
		//if it is start of new experiment, remove the original result
		if (i == 0) {
			try {
				Files.deleteIfExists(Paths.get(fileName));
			} catch (IOException e) {e.printStackTrace();}
		}
		
		//write the content to the file
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fileName, true));
			out.print(content);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This Method is mean to print out the result of each round to console 
	 * @param i
	 */
	private static String printResult(int i) {
		String str = "";
		//show the round number
		str +="Round "+ i + ":----";
		
		//show the board
		//str +=board.printBoard();
		
		//show the agent number
		//str += String.format("AltruistsNumber: %d\tEgoistNumber: %d\tCosmopolitanNumber: %d\t\tEthnocentricsNumber: %d\t\n"
		//		,board.getAltruistsNumber(),board.getEgoistNumber(), board.getCosmopolitanNumber(),board.getEthnocentricsNumber());
		
		//Show the percentage of the string
		str += String.format("Altruists Ratio: %.3f\tEgoist Ratio: %.3f\tCosmopolitan Ratio: %.3f\tEthnocentrics Ratio: %.3f\t\n"
				,(double)board.getAltruistsNumber()/(double) board.agentNums()
				,(double)board.getEgoistNumber()/(double) board.agentNums()
				,(double)board.getCosmopolitanNumber()/(double) board.agentNums()
				,(double)board.getEthnocentricsNumber()/(double) board.agentNums());
		//System.out.printf(str);
		return str;
	}

	/**
	 * <p>Up to IMMIGRANTS-PER-DAY, new agents appear in random locations with random traits.</p>
	 * <p><b>1.</b>Create ImmigrationRate new agents with random traits (strategy and tag).</p>
	 * <b>2.</b>Place the new agent(s), one at a time, each in a random empty site on the lattice. 
	 */
	private static void agentSpamming() {
		int spamming = Integer.parseInt(Config.getProperty("IMMIGRATION_PER_DAY"));
		int col,row;
		//create "Immigration per day" number of new agents
		outerloop:
		while (spamming > 0) {
			//if no more space on the board, the no new agents this round.
			if (board.isFull()) {break outerloop;}
			
			//create the new agent in a random empty space 
			col = rand.nextInt(board.getSize());
			row = rand.nextInt(board.getSize());
			if (board.getPoint(col, row) == null) {
				board.addAgent(new Agent(col, row));
				spamming--;
			}
		}
	}
	
	/**
	 * give a chance for all agent, to check if they will to donate
	 */
	private static void agentInteraction() {
		//reset PTR
		for (Agent[] row: board.getGameBoard()) {
			for (Agent agent: row) {
				if (agent != null) {agent.resetPTR();}
			}
		}
		
		List<Agent> agentList = getShuffledAgentList();
		
		//looking at neighbors, if they satisfy the agent's coop policy, then donate resource to them
		int col,row;
		for (Agent agent: agentList) {
			col = agent.getCoordinates().x;
			row = agent.getCoordinates().y;
			//for LEFT neighbor
			if ((col-1 >= 0) && (board.getPoint(col-1, row) != null)) 				{donateA2B(agent, board.getPoint(col-1, row));}
			//for RIGHT neighbor
			if ((col+1 < board.getSize()) && (board.getPoint(col+1, row) != null)) 	{donateA2B(agent, board.getPoint(col+1, row));}
			//for TOP neighbor
			if ((row-1 >= 0) && (board.getPoint(col, row-1) != null)) 				{donateA2B(agent, board.getPoint(col, row-1));}
			//for DOWN neighbor
			if ((row+1 < board.getSize()) && (board.getPoint(col, row+1) != null)) 	{donateA2B(agent, board.getPoint(col, row+1));}
		}
	}
	
	

	/**
	 * get shuffled agent list of existing agents
	 * @return List<Agent>
	 */
	private static List<Agent> getShuffledAgentList() {
		//list all existing agent
		List<Agent> agentList = new ArrayList<Agent>();
		for (Agent[] row: board.getGameBoard()) {
			for (Agent agent: row) {
				if (agent != null)
					agentList.add(agent);
			}
		}
				
		//shuffle the list
		Collections.shuffle(agentList);
		return agentList;
	}
	
	
	private static void donateA2B(Agent agentA, Agent agentB) {
		//if A willing to work with B
		if (agentA.canCooperate(agentB)) {
			if(agentA.donate()) {agentB.receive();}
		}
	}
	
	/**
	 * give chance for each agent to produce a new offSpring
	 */
	private static void agentReproduce() {
		List<Agent> agentList = getShuffledAgentList();
		
		int col,row;
		for (Agent agent: agentList) {
			//if it can born
			if (rand.nextDouble() < agent.getPTR()) {
				col = agent.getCoordinates().x;
				row = agent.getCoordinates().y;
				//checking for surrounding free space
				if ((col-1 >= 0) && (board.getPoint(col-1, row) == null)) 					{newOffSpring(col-1, row, agent);}
				else if ((col+1 < board.getSize()) && (board.getPoint(col+1, row) == null)) {newOffSpring(col+1, row, agent);}
				else if ((row-1 >= 0) && (board.getPoint(col, row-1) == null))				{newOffSpring(col, row-1, agent);}
				else if ((row+1 < board.getSize()) && (board.getPoint(col, row+1) == null)) {newOffSpring(col, row+1, agent);}
			}
		}
	}
	
	
	
	/**
	 * give chance for each agent to produce a new offSpring with extension version
	 */
	private static void agentReproduceII() {
		List<Agent> agentList = getShuffledAgentList();
		int randium = Integer.parseInt(Config.getProperty("CHILD_DISTANCE"));
		int col,row, newCol, newRow;
		int topLine,bottomLine,leftLine,rightLine;
		for (Agent agent: agentList) {
			//if it can born
			if (rand.nextDouble() < agent.getPTR()) {
				col = agent.getCoordinates().x;
				row = agent.getCoordinates().y;
				//check the boundry of the spamming child area
				leftLine 	= Math.max(0, col - randium);
				rightLine 	= Math.min(board.getSize()-1, col + randium);
				topLine 	= Math.max(0, row - randium);
				bottomLine 	= Math.min(board.getSize()-1, row + randium);
				
				
				//if no more space on the area, the no new agents this time.
				boolean hasSpace = false;
				outerloop1:
				for (int i = topLine; i<bottomLine; i++ ) {
					for (int j = leftLine; j<rightLine; j++ ) {
						if (board.getPoint(j, i) == null) {
							hasSpace = true;
							break outerloop1;
						}
					}
				}
				
				//randomly born the child in the area
				outerloop:
				while (hasSpace) {
					//create the new agent in a random empty space 
					newCol = rand.nextInt(rightLine - leftLine) + leftLine;
					newRow = rand.nextInt(bottomLine - topLine) + topLine;
					if (board.getPoint(newCol, newRow) == null) { 
						newOffSpring(newCol, newRow, agent);
						break outerloop;
					}
				}	
			}
		}
	}
	
	/**
	 * create a new offspring
	 * @param col
	 * @param row
	 * @param agent
	 * @throws NumberFormatException
	 */
	private static void newOffSpring(int col, int row, Agent agent) throws NumberFormatException {
		//if the offSpring of the agent mutate
		if(rand.nextDouble() < Double.parseDouble(Config.getProperty("MUTATION_RATE"))) {
			board.addAgent(new Agent(col, row));
		} else {
			board.addAgent(new Agent(agent, col, row));
		}
	}
	
	
	/**
	 * The agent has a DEATH-RATE chance of dying, making room for future offspring and immigrants.
	 * <pre>Each agent has a chance of dying (and being removed from the lattice) equal to DeathRate. </pre>
	 */
	private static void agentDeath() {
		for (Agent[] row: board.getGameBoard()) {
			for (Agent agent : row) {
				//if the agent hit the death chance, remove the agent from the board
				if (agent != null && (rand.nextDouble() < Double.parseDouble(Config.getProperty("DEATH_RATE")))) {
					board.removeAgent(agent.getCoordinates());
				}
			}
		}
	}
	
	

}