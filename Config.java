/*Team members:
 * Lihang Tian 975404
 * Mayank Sharma 936970
 * Yuanlong Zhang 772312
 * Date: 27 May 2019
 */
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Config class to provide global accessablity to configerations
 * @author JIMMY
 *
 */
public class Config {
	/*
	 * set the data model here:
	 * -"default" is the same initial value as in the NetLogo
	 * -"Exp1.1" mutation rate = 0.305
	 * -"Exp1.2" death rate = 0.15
	 * -"Exp1.3" Immigrate per day = 88
	 * -"Exp1.4" InitialPTR = 0.03
	 * -"Exp2.1" Cost_Giving = 1 & Gain_Receiving = 0
	 * -"Exp2.2" mutation rate = 0 & Cost_Giving = 0 & Gain_Receiving = 1
	 */
	private static String flag = "default";
	private static boolean extensionOn = false;
	private static Properties defaultProps = new Properties();
	
	//set properties 
	static {
		try {
			File yourFile = new File("config.properties");
			yourFile.createNewFile();
			FileInputStream in = new FileInputStream("config.properties");
	        defaultProps.load(in);
	        //property used by extension
	        defaultProps.put("CHILD_DISTANCE"			,"2");
	        
	       
	        switch (flag){
	        case "default":
	        	defaultProps.put("COST_GIVING"			,"0.01");
		        defaultProps.put("GAIN_RECEIVING"		,"0.03");
		        defaultProps.put("DEATH_RATE"			,"0.1");
		        defaultProps.put("MUTATION_RATE"		,"0.005");
		        defaultProps.put("INITIAL_PTR"			,"0.12");
		        defaultProps.put("IMMIGRATION_PER_DAY"	,"1");
		        defaultProps.put("ROUND_NUM"			,"100");
		        defaultProps.put("LATTICE_SIZE"			,"50");
		        defaultProps.put("CHANCE_COP_SAME"		,"0.5");
		        defaultProps.put("CHANCE_COP_DIFFERENT"	,"0.5");
	        	break;
	        
	        case "Exp1.1":
	        	defaultProps.put("COST_GIVING"			,"0.01");
		        defaultProps.put("GAIN_RECEIVING"		,"0.03");
		        defaultProps.put("DEATH_RATE"			,"0.15");
		        defaultProps.put("MUTATION_RATE"		,"0.305");
		        defaultProps.put("INITIAL_PTR"			,"0.12");
		        defaultProps.put("IMMIGRATION_PER_DAY"	,"1");
		        defaultProps.put("ROUND_NUM"			,"2000");
		        defaultProps.put("LATTICE_SIZE"			,"50");
		        defaultProps.put("CHANCE_COP_SAME"		,"0.5");
		        defaultProps.put("CHANCE_COP_DIFFERENT"	,"0.5");
	        	break;
	        	
	        case "Exp1.2":
	        	defaultProps.put("COST_GIVING"			,"0.01");
		        defaultProps.put("GAIN_RECEIVING"		,"0.03");
		        defaultProps.put("DEATH_RATE"			,"0.15");
		        defaultProps.put("MUTATION_RATE"		,"0.005");
		        defaultProps.put("INITIAL_PTR"			,"0.12");
		        defaultProps.put("IMMIGRATION_PER_DAY"	,"1");
		        defaultProps.put("ROUND_NUM"			,"2000");
		        defaultProps.put("LATTICE_SIZE"			,"50");
		        defaultProps.put("CHANCE_COP_SAME"		,"0.5");
		        defaultProps.put("CHANCE_COP_DIFFERENT"	,"0.5");
	        	break;
	 
	        case "Exp1.3":
	        	defaultProps.put("COST_GIVING"			,"0.01");
		        defaultProps.put("GAIN_RECEIVING"		,"0.03");
		        defaultProps.put("DEATH_RATE"			,"0.1");
		        defaultProps.put("MUTATION_RATE"		,"0.005");
		        defaultProps.put("INITIAL_PTR"			,"0.12");
		        defaultProps.put("IMMIGRATION_PER_DAY"	,"88");
		        defaultProps.put("ROUND_NUM"			,"2000");
		        defaultProps.put("LATTICE_SIZE"			,"50");
		        defaultProps.put("CHANCE_COP_SAME"		,"0.5");
		        defaultProps.put("CHANCE_COP_DIFFERENT"	,"0.5");
	        	break;
	        	
	        case "Exp1.4":
	        	defaultProps.put("COST_GIVING"			,"0.01");
		        defaultProps.put("GAIN_RECEIVING"		,"0.03");
		        defaultProps.put("DEATH_RATE"			,"0.1");
		        defaultProps.put("MUTATION_RATE"		,"0.005");
		        defaultProps.put("INITIAL_PTR"			,"0.03");
		        defaultProps.put("IMMIGRATION_PER_DAY"	,"1");
		        defaultProps.put("ROUND_NUM"			,"2000");
		        defaultProps.put("LATTICE_SIZE"			,"50");
		        defaultProps.put("CHANCE_COP_SAME"		,"0.5");
		        defaultProps.put("CHANCE_COP_DIFFERENT"	,"0.5");
	        	break;
	        
	        
	        	
	        case "Exp2.1":
	        	defaultProps.put("COST_GIVING"			,"1");
		        defaultProps.put("GAIN_RECEIVING"		,"0");
		        defaultProps.put("DEATH_RATE"			,"0.1");
		        defaultProps.put("MUTATION_RATE"		,"0.005");
		        defaultProps.put("INITIAL_PTR"			,"0.12");
		        defaultProps.put("IMMIGRATION_PER_DAY"	,"1");
		        defaultProps.put("ROUND_NUM"			,"2000");
		        defaultProps.put("LATTICE_SIZE"			,"50");
		        defaultProps.put("CHANCE_COP_SAME"		,"0.5");
		        defaultProps.put("CHANCE_COP_DIFFERENT"	,"0.5");
	        	break;
	        	
	        case "Exp2.2":
	        	defaultProps.put("COST_GIVING"			,"0");
		        defaultProps.put("GAIN_RECEIVING"		,"1");
		        defaultProps.put("DEATH_RATE"			,"0.1");
		        defaultProps.put("MUTATION_RATE"		,"0");
		        defaultProps.put("INITIAL_PTR"			,"0.12");
		        defaultProps.put("IMMIGRATION_PER_DAY"	,"1");
		        defaultProps.put("ROUND_NUM"			,"2000");
		        defaultProps.put("LATTICE_SIZE"			,"50");
		        defaultProps.put("CHANCE_COP_SAME"		,"0.5");
		        defaultProps.put("CHANCE_COP_DIFFERENT"	,"0.5");
	        	break;
	        	
	        
	        	
	        default: break;
	        }
	        
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	  }
	  
	  /**
	   * 
	   * @param key
	   * @return String
	   */
	  public static String getProperty(String key) {
	    return defaultProps.getProperty(key);
	  }

	/**
	 * 
	 * @return String 
	 */
	public static String getFlag() {
		return flag;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public static boolean isExtensionOn() {
		return extensionOn;
	}
	 
	  
	  
}
