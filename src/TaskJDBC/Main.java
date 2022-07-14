package TaskJDBC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Arrays;



public class Main {
	
	public static int findIndex(String arr[], String t)
    {
				String carName = t;
				int index = -1;
				for (int i=0;i<arr.length;i++) {
				    if (arr[i].equals(carName)) {
				        index = i;
				        break;
				}
				}
				return index;
				 
    }
	

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		
		DbConnection db = new DbConnection();
		
		String path = "..\\DbExercise\\employee.csv";
		
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		
		String data;
		
		
		br.readLine();
		
		String sql = "INSERT INTO employees (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER,HIRE_DATE,JOB_ID,SALARY,COMMISSION_PCT,MANAGER_ID,DEPARTMENT_ID) VALUES (?, ?, ?, ?, ?,?,?,?,?,?,?)";
		String sql1 = "INSERT INTO employee_failed (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER,HIRE_DATE,JOB_ID,SALARY,COMMISSION_PCT,MANAGER_ID,DEPARTMENT_ID) VALUES (?, ?, ?, ?, ?,?,?,?,?,?,?)";
		Connection con = db.getConnection();
		
		PreparedStatement statement = con.prepareStatement(sql);
		
		PreparedStatement failed_state = con.prepareStatement(sql1);
		
		String[] emp_coloum = {"EMPLOYEE_ID","FIRST_NAME","LAST_NAME","EMAIL","PHONE_NUMBER","HIRE_DATE","JOB_ID","SALARY","COMMISSION_PCT","MANAGER_ID","DEPARTMENT_ID"};
		
		
		Map<String , String> employee = new HashMap<String , String>();
		
		Map<String , String> emp_failed = new HashMap<String , String>();
		
		Employee emp = new Employee();
		
		while((data = br.readLine()) != null) {
			int all_failed = 0;
			emp.failed = 0 ;
			
			employee.clear();
			emp_failed.clear();
			
			String[] employee_init = data.split("[,]",0);
			
			for(int i=0;i<employee_init.length;i++) {
				employee.put(emp_coloum[i], employee_init[i]);
			}
			
			for(int l=0;l<emp_coloum.length;l++) {
				if(!employee.containsKey(emp_coloum[l])) {
					employee.put(emp_coloum[l],null);
				}
			}
			
			
			if(employee.get("LAST_NAME") == "" || employee.get("EMAIL") == "" || employee.get("PHONE_NUMBER") == "" || employee.get("HIRE_DATE") == "" || employee.get("SALARY") == "" ) {
				all_failed = 1;
			}
			
			
			
			emp.setEMPLOYEE_ID(employee.get("EMPLOYEE_ID"));
			emp.setLAST_NAME(employee.get("LAST_NAME"));
			emp.setFIRST_NAME(employee.get("FIRST_NAME"));
			emp.setEMAIL(employee.get("EMAIL"));
			emp.setPHONE_NUMBER(employee.get("PHONE_NUMBER"));
			emp.setHIRE_DATE(employee.get("HIRE_DATE"));
			emp.setJOB_ID(employee.get("JOB_ID"));
			emp.setSALARY(employee.get("SALARY"));
			emp.setCOMMISSION_PCT(employee.get("COMMISSION_PCT"));
			emp.setMANAGER_ID(employee.get("MANAGER_ID"));
			emp.setDEPARTMENT_ID(employee.get("DEPARTMENT_ID"));
			
			
			
			
			//blank validation
			if(employee.get("EMPLOYEE_ID") == "") {
				break;
			}else {
				emp_failed.put("EMPLOYEE_ID",employee.get("EMPLOYEE_ID"));
			}
			
			
			if(emp.getEMAIL() != "") {
				employee.replace("EMAIL", emp.getEMAIL()); 
			}else {
				emp_failed.put("EMAIL",employee.get("EMAIL"));
				employee.replace("EMAIL", ""); 
			}
			
			
			if(emp.getPHONE_NUMBER() == "") {
				emp_failed.put("PHONE_NUMBER",employee.get("PHONE_NUMBER"));
				employee.replace("PHONE_NUMBER", ""); 
			}
			
			if(emp.getJOB_ID() == "") {
				emp_failed.put("JOB_ID",employee.get("JOB_ID"));
				employee.replace("JOB_ID", ""); 
			}
			
			if(emp.getSALARY() == "") {
				emp_failed.put("SALARY",null);
				employee.replace("SALARY", null); 
			}
			
			if(emp.getHIRE_DATE() != "") {
				employee.replace("HIRE_DATE", emp.getHIRE_DATE()); 
			}else {
				emp_failed.put("HIRE_DATE",null);
				employee.replace("HIRE_DATE", null); 
			}
			
			if(emp.getCOMMISSION_PCT() == "") {
				employee.replace("COMMISSION_PCT", null); 
			}
			
			if(emp.getMANAGER_ID() != "") {
				employee.replace("MANAGER_ID", emp.getMANAGER_ID()); 
			}
			
			
			if(all_failed ==1)
			{
				 emp_failed.putAll(employee);
			}
			
			
			for(int l=0;l<emp_coloum.length;l++) {
				if(!emp_failed.containsKey(emp_coloum[l])) {
					emp_failed.put(emp_coloum[l],null);
				}
			}
            
			
			if(all_failed == 0 && (emp.failed == 0 || emp.failed == 1) ) {
				for (Map.Entry<String, String> res :
		             employee.entrySet()) {
					
					int F_Index = Main.findIndex(emp_coloum,res.getKey());
					if(F_Index >= 0) {
						statement.setString(F_Index+1,res.getValue());
					}
//					 System.out.println(res.getKey() +" "+res.getValue());
		        }
           
            
            statement.addBatch();
			}
			
			
            
            if(emp.failed == 1 || all_failed == 1)
            {
            	
				for (Map.Entry<String, String> res1 :
					emp_failed.entrySet()) {
					int newIndex = Main.findIndex(emp_coloum,res1.getKey());
					if(newIndex >= 0) {
						failed_state.setString(newIndex+1,res1.getValue());
					}
		        }
            
            failed_state.addBatch();
            
            
            
			}
			
		}
		
		statement.executeBatch();
		failed_state.executeBatch();
		con.commit();
		System.out.println("Data are preocessed successfully");
		
		
		
		Scanner sc = new Scanner(System.in);
		 System.out.println("do you want employee insertion report (Yes|No)");
		String a = sc.nextLine();
		 String b = "yes";
		if(a.equals(b)) {
			DatatoCSV Dcsv = new DatatoCSV();
			Dcsv.CreateReport();
			System.out.println("report created successfully");
			
		}else {
			System.out.println("thanks ,bye!!!");
		}
		
		
		con.close();	
		
		
	}

}
