import org.apache.log4j.Logger;

public class log4jTask {

	static Logger logger = Logger.getLogger(log4jTask.class.getName());
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 logger.info("Info Log");
	      logger.warn("Warning Log");
	      logger.error("error Log");
	      logger.fatal("Severe Log");
	}

}
