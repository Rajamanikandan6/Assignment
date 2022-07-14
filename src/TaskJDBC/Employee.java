package TaskJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee 
{
	
	
	private String EMPLOYEE_ID ;
	
	private String FIRST_NAME;
	
	private String LAST_NAME;
	
	private String EMAIL ;
	
	private String PHONE_NUMBER;
	
	private String HIRE_DATE;
	
	private String JOB_ID;

	private String SALARY;

	private String COMMISSION_PCT;
	
	private String MANAGER_ID;
	
	private String DEPARTMENT_ID;
	
	public int failed = 0;
	
	public int all_failed = 0;
	
	public String[] employee_failed_arr = new String[11]; 
	
	
	public static Matcher getmatcherObj(String regex,String data) {
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(data);
		return matcher;
	}
	
	
	public static String validateEmail(String s) {
		
		if(s == null)
			s = "-1";
		
		String resp = "";
		String emilRex = "^[A-Z0-9._%+-]+@$";
		String emailRex1 = "^[A-Z0-9._%+-]+@[A-Z0-9]+\\.[A-Z]{2,6}$";
		String emailRex2 = "^(?=.{1,50}$)[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Matcher matcher = getmatcherObj(emilRex,s);
		System.out.println(s);
		if(matcher.matches())
		{
			return  "not valid";
	
		}else {
			matcher = getmatcherObj(emailRex1,s);
			if(matcher.matches())
			{
				resp = s;
				
			}else {
				resp = s.concat("@abc.com");
			
			}
			matcher = getmatcherObj(emailRex2,resp);
			if(matcher.matches())
			{
				return resp;
				
			}else {
				resp = s;
				return  "not valid";
			}
			
		}
		
	}
	
	
	
	
	public String getEMPLOYEE_ID() {
		return EMPLOYEE_ID;
	}

	public void setEMPLOYEE_ID(String eMPLOYEE_ID) {
		EMPLOYEE_ID = eMPLOYEE_ID;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		
		String emailRes = Employee.validateEmail(eMAIL);
		  
		  
		  if(emailRes == "not valid") {
			  failed = 1;
			  EMAIL = "";
		  }else { 
			  EMAIL = emailRes;
			  
			  }
	}

	public String getPHONE_NUMBER() {
		return PHONE_NUMBER;
	}

	public void setPHONE_NUMBER(String pHONE_NUMBER) {
		if(pHONE_NUMBER == null)
			pHONE_NUMBER = "111";  // random invalied number
		
		if(!pHONE_NUMBER.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")){
			failed = 1;
			PHONE_NUMBER = "";
		}else
		PHONE_NUMBER = pHONE_NUMBER;
	}

	public String getHIRE_DATE() {
		return HIRE_DATE;
	}

	public void setHIRE_DATE(String hIRE_DATE) {
		Date date = null;
        try {
        	date = new SimpleDateFormat("dd-MM-yyyy").parse(hIRE_DATE);
			 String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);
			 HIRE_DATE = newstring;
        } catch (ParseException e) {
			failed = 1;
			HIRE_DATE = "";
		}
	}

	public String getJOB_ID() {
		return JOB_ID;
	}

	public void setJOB_ID(String jOB_ID) throws ClassNotFoundException, SQLException {
		DbConnection db = new DbConnection();
		Connection con = db.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from employee_job where job_id =?");
		ps.setString(1,jOB_ID);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next() == false) {
			failed = 1;
			JOB_ID = "";
		}else
		JOB_ID = jOB_ID;
	}

	public String getSALARY() {
		return SALARY;
	}

	public void setSALARY(String sALARY) {
		String Salary = "^[1-9]+[0-9]*$";
		if(sALARY == null)
			sALARY = "-1";
		
		Matcher matcher = getmatcherObj(Salary,sALARY);
		
		if(!matcher.matches()) {
			failed = 1;
			SALARY = "";
		}else
		SALARY = sALARY;
	}

	public String getCOMMISSION_PCT() {
		return COMMISSION_PCT;
	}

	public void setCOMMISSION_PCT(String cOMMISSION_PCT) {
		if(cOMMISSION_PCT == null)
			cOMMISSION_PCT = "-1";
		if(!cOMMISSION_PCT.matches("^\\d+\\.\\d+")) {
			COMMISSION_PCT = "";
        }else
		COMMISSION_PCT = cOMMISSION_PCT;
	}

	public String getMANAGER_ID() {
		return MANAGER_ID;
	}

	public void setMANAGER_ID(String mANAGER_ID) {
		if(mANAGER_ID == null)
			mANAGER_ID = "-1";
		 if(!mANAGER_ID.matches("^[1-9]+[0-9]*$")) {
			 MANAGER_ID = "100";
         }else
		MANAGER_ID = mANAGER_ID;
	}

	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}

	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}
	
	
	
	
}
