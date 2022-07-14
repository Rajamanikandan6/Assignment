package TaskJDBC;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatatoCSV {

	public void CreateReport() throws ClassNotFoundException, SQLException, IOException {
		DbConnection db = new DbConnection();
		
		String path1 ="C:\\Users\\rajamanikandanso\\eclipse-workspace\\DbExercise\\src\\report";
		String path2 =path1+"\\success_report.csv";
		
		File file1 = new File(path1);
        if (!file1.isDirectory()) {
           file1.mkdir();
        } 
        File csvFilePath = new File(path2); 
        
        
        String path3 ="C:\\Users\\rajamanikandanso\\eclipse-workspace\\DbExercise\\src\\report";
		String path4 =path3+"\\failed_report.csv";
		
		File file2 = new File(path3);
        if (!file2.isDirectory()) {
           file2.mkdir();
        } 
        File csvFilePathfailed = new File(path4);

		
		String sql = "select a.EMPLOYEE_ID,CONCAT(a.FIRST_NAME,' ',a.LAST_NAME) AS name,a.email,job_description,dept_desc,concat(b.FIRST_NAME,' ',b.LAST_NAME) AS manager_name from employees as a\r\n"
				+ "left join employee_job on a.job_id = employee_job.job_id\r\n"
				+ "left join departments on a.department_id = departments.dept_id\r\n"
				+ "left join employees as b on a.manager_id = b.employee_id";
		
		String failed_sql = "select a.EMPLOYEE_ID,CONCAT(a.FIRST_NAME,' ',a.LAST_NAME) AS name,CONCAT(c.FIRST_NAME,' ',c.LAST_NAME) AS Fname,a.email,c.email,job_description,dept_desc,concat(b.FIRST_NAME,' ',b.LAST_NAME) AS manager_name from employee_failed as a\r\n"
				+ "left join employees as c on a.employee_id = c.employee_id\r\n"
				+ "left join employee_job on c.job_id = employee_job.job_id\r\n"
				+ "left join departments on c.department_id = departments.dept_id\r\n"
				+ "left join employees as b on c.manager_id = b.employee_id";
		
		Connection con = db.getConnection();
		
		Statement statement = con.createStatement();
		
		Statement statement1 = con.createStatement();
        
        ResultSet result = statement.executeQuery(sql);
        
        ResultSet result1 = statement1.executeQuery(failed_sql);
        
         
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
        
        BufferedWriter fileWriter1 = new BufferedWriter(new FileWriter(csvFilePathfailed));
         
        // write header line containing column names       
        fileWriter.write("EMPLOYEE_ID,NAME,EMAIL,JOB_NAME,DEPARTMENT,MANAGER_NAME");
     // write header line containing column names       
        fileWriter1.write("EMPLOYEE_ID,NAME,EMAIL,JOB_NAME,DEPARTMENT,MANAGER_NAME");
         
        while (result.next()) {
        	String line = "";
        	String emp_id = result.getString("EMPLOYEE_ID");
            String name = result.getString("name");
            String email = result.getString("email");
            String job = result.getString("job_description");
            String dept = result.getString("dept_desc");
            String manager = result.getString("manager_name");
            
            if(manager == null) {
            	manager ="";
            }
            
            
             
            line = line.concat(emp_id).concat(",").concat(name).concat(",").concat(email).concat(",").concat(job).concat(",").concat(dept).concat(",").concat(manager);
             System.out.println(line);
            fileWriter.newLine();
            fileWriter.write(line);
	}
        
        while (result1.next()) {
        	String line = "";
        	String emp_id = result1.getString("EMPLOYEE_ID");
            String name = result1.getString("Fname");
            String email = result1.getString("email");
            String job = result1.getString("job_description");
            String dept = result1.getString("dept_desc");
            String manager = result1.getString("manager_name");
            
            if(manager == null) {
            	manager ="";
            }
            if(name == null) {
            	name ="";
            }
            if(email == null) {
            	email ="";
            }
            if(job == null) {
            	job ="";
            }
            if(dept == null) {
            	dept ="";
            }
            
            
            
             
            line = line.concat(emp_id).concat(",").concat(name).concat(",").concat(email).concat(",").concat(job).concat(",").concat(dept).concat(",").concat(manager);
             System.out.println(line);
            fileWriter1.newLine();
            fileWriter1.write(line);
	}
        
        statement.close();
        statement1.close();
        fileWriter.close();
        fileWriter1.close();
}
}
