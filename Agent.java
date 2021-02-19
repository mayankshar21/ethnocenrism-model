/*Team members:
 * Lihang Tian 975404
 * Mayank Sharma 936970
 * Yuanlong Zhang 772312
 * Date: 27 May 2019
 */
import java.awt.Point;
import java.util.Random;

/**
 * The Agent class is designed to represent each decision making unit in the simulation
 */
public class Agent {
	
	/**
	 * Initially set to 4 color. Assuming we have 4 groups of agents
	 * @author JIMMY
	 */
	public enum Color {
		BLUE,
		RED,
		GREEN,
		YELLOW
	};
	
	
	/**
	 * A shape to indicate the state of the agent:
	 * Circle: DO Cooperate with the same color
	 *  -(FULL-altruists: Cooperate with a different color, EMPTY- ethnocentrics: Do not)
	 * Square: Do Not cooperate with the same color
	 *   -(FULL-cosmopolitans: Cooperate with a different color, EMPTY- egoists: Do not)
	 * @author JIMMY
	 */
	public enum Shape {
		EMPTY_CIRCLE,
		FULL_CIRCLE,
		EMPTY_SQUARE,
		FULL_SQUARE
	};
	
	//Shape represents the strategy 
	private Shape shape;
	//Color represents the groups of the agents
	private Color color;
	
	private Point coordinates;
	//will the agent coop with the same color agent
	private boolean coopWithSame;
	//will the agent coop with the different color agent
	private boolean coopWithDifferent;
	//the indication of how much changce the agent can produce an offspring
	private double PTR;
	
	/**
	 * generate a new agent on a given coordinate
	 * @param x
	 * @param y
	 */
	public Agent(int x, int y) {
		super();
		
		this.coordinates = new Point(x,y);
		PTR = Double.parseDouble(Config.getProperty("INITIAL_PTR"));
		
		//assign random values except the coordinates
		Random rand = new Random();
		color = Color.values()[rand.nextInt(Color.values().length)];
		
		//Assign the property based on the chance defined
		coopWithSame = (rand.nextDouble() <= Double.parseDouble(Config.getProperty("CHANCE_COP_SAME")));
		coopWithDifferent = (rand.nextDouble() <= Double.parseDouble(Config.getProperty("CHANCE_COP_DIFFERENT")));
		
		//decide the shape based on two cooperation strategies 
		if (coopWithSame && coopWithDifferent) {
			shape = Shape.FULL_CIRCLE;
		} else if (coopWithSame && !coopWithDifferent) {
			shape = Shape.EMPTY_CIRCLE;
		} else if (!coopWithSame && coopWithDifferent) {
			shape = Shape.FULL_SQUARE;
		} else if (!coopWithSame && !coopWithDifferent) {
			shape = Shape.EMPTY_SQUARE;
		}
		
	}
	
	//Copy a existing agent to the given location
	public Agent (Agent agent, int x, int y) {
		super();
		this.coordinates = new Point(x,y);
		PTR = Double.parseDouble(Config.getProperty("INITIAL_PTR"));
		
		//Inherit values from the parent 
		coopWithSame = agent.coopWithSame;
		coopWithDifferent = agent.coopWithDifferent;
		shape = agent.getShape();
		color = agent.getColor();
		
	}
	
	/**
	 * Return the result of whether the agent willing to work with the given agent
	 * @param agentB
	 * @return returns true if it willing to coop, false otherwise
	 */
	public boolean canCooperate(Agent agentB) {
		Color colorB = agentB.getColor();
		
		switch (shape) {
			//filled in circle (altruist): coop with anyone
			case FULL_CIRCLE:
				return true;
				
			//filled in square (cosmopolitan): coop only with different color
			case FULL_SQUARE:
				return (!color.equals(colorB));
				
			//empty circle (ethnocentric) : coop with only same color
			case EMPTY_CIRCLE:
				return (color.equals(colorB));
				
			//empty square (egoist): does not coop with anyone
			case EMPTY_SQUARE:
				return false;
		}
		return false;
	}
	
	/**
	 * Donate some resources to others, reduce the holding PTR value by cost_giving
	 * @return true if have enough balance to donate and have donated, false otherwise
	 */
	public boolean donate() {
		double cost_giving = Double.parseDouble(Config.getProperty("COST_GIVING"));
		if (PTR >= cost_giving) {
			PTR -=cost_giving;
			return true;
		}
		return false;
	}
	
	/**
	 * receive donation from others, increase the holding PTR value
	 */
	public void receive() {
		PTR += Double.parseDouble(Config.getProperty("GAIN_RECEIVING"));
	}
	
	/**
	 * 
	 * @return Shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * 
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 
	 * @return Point
	 */
	public Point getCoordinates() {
		return coordinates;
	}

	/**
	 * 
	 * @return double
	 */
	public double getPTR() {
		return PTR;
	}
	
	/**
	 * reset the PTR to the initial value in config file
	 */
	public void resetPTR() {
		PTR = Double.parseDouble(Config.getProperty("INITIAL_PTR"));
	}
	
	/**
	 * if the cooperation is not success, refund the cost
	 */
	public void refund() {
		PTR += Double.parseDouble(Config.getProperty("COST_GIVING"));
	}
	/**
	 * Shows the details of a agent
	 */
	@Override
	public String toString() {
		return "Agent("+ coordinates.getX() +"," + coordinates.getY() +
                 ")" + ","+ color + "," +coopWithSame+","
				+coopWithDifferent+","+ shape;
	}
	
	
}
