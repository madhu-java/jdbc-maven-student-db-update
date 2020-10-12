package org.madhu.JdbcDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
    	StudentDao dao = new StudentDao();
    	dao.connect();
    	Student s =dao.getStudent(2);
    	System.out.println(s.sname);
    	Student s1 = new Student();
    	s1.rollno = 4;
    	s1.sname = "rama";
    	dao.addStudent(s1);
    	dao.deleteStudent(4);
    	
    }
}
class StudentDao{
	Connection con = null;
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url ="jdbc:mysql://localhost:3306/abd";
			String username = "root";
			String pword = "root";
			try {
				con = DriverManager.getConnection(url,username,pword);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void deleteStudent(int rollno) {
		String query = "delete from student1 where rollno = "+rollno;
		
		try {
			Statement st = con.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addStudent(Student s) {
	String query ="insert into student1 values(?,?)";
	try {
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, s.rollno);
		pst.setString(2,s.sname);
		pst.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	public Student getStudent(int rollno) {
//		String url ="jdbc:mysql://localhost:3306/abd";
//		String username = "root";
//		String pword = "root";
		Student s = new Student();
		s.rollno = rollno;
		String query = "select * from student1 where rollno="+rollno;
		try {
		//	con = DriverManager.getConnection(url,username,pword);
			Statement st = con.createStatement();
			ResultSet rs= st.executeQuery(query);	
			rs.next();
			String sname = rs.getString(2);
			s.sname=sname;
			
			return s;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
class Student{
	int rollno;
	String sname;
}