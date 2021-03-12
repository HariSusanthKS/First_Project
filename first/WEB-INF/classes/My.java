import javax.servlet.*;  
import javax.servlet.http.*;  
import java.io.*; 
import java.util.*; 
import java.sql.*;
import javax.xml.parsers.*;
import org.w3c.dom.*; 
import java.text.SimpleDateFormat;  
import java.sql.Date; 
import java.sql.Time; 
import org.hibernate.*;  
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
import org.json.simple.*;
import com.google.gson.*;

public class My extends HttpServlet
{ 
	// declaring static variables 
	public static String db_adapter;
	public  static StandardServiceRegistry ssr=null;
	public  static Metadata meta=null;	
	public  static SessionFactory factory=null; 
	public void init() throws ServletException 
	{
		try
		{
			// Read conf file to find which database we are going to use 			
			BufferedReader br = new 
			BufferedReader(new FileReader("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\first\\WEB-INF\\conf.txt"));
			String s;
			s=br.readLine();
			db_adapter=s.substring(11,16);
			br.close();			
			// database configure file connection initiated  
			if(db_adapter.equals("mssql"))
			{
				ssr = new StandardServiceRegistryBuilder().configure("mssqlcreatehibernate.cfg.xml").build();
			}
			else if(db_adapter.equals("mysql"))
			{
				ssr = new StandardServiceRegistryBuilder().configure("mysqlcreatehibernate.cfg.xml").build();
			}
			else if(db_adapter.equals("postr"))
			{
				ssr = new StandardServiceRegistryBuilder().configure("postrgescreatehibernate.cfg.xml").build();
			}
			meta = new MetadataSources(ssr).getMetadataBuilder().build();
			factory = meta.getSessionFactoryBuilder().build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException  
	{
		res.setContentType("application/json");
		PrintWriter p=res.getWriter();  
		//getting values
		String first_name = req.getParameter("first_name");
		String last_name=req.getParameter("last_name"); 
		String father_name=req.getParameter("father_name");		
		String date=req.getParameter("dob");
		Date dob=Date.valueOf(date);   				
		String email=req.getParameter("email"); 
		int phone_no=Integer.parseInt(req.getParameter("phone_no"));
		String mother_name=req.getParameter("mother_name");		
		String address=req.getParameter("address");
		
		try 
		{	
			//code to save the given data in table and retrive the database and display
			Session session = factory.openSession();
			Transaction t = session.beginTransaction(); 					  
			Employee e1=new Employee();  	
				e1.setFirstName(first_name);
				e1.setLastName(last_name);
				e1.setEmail(email);
				e1.setPhno(phone_no);
				e1.setMotherName(mother_name);
				e1.setFatherName(father_name);
				e1.setAddress(address);
				e1.setDOB(dob);							
			session.save(e1);				
			t.commit();	
			List li=session.createQuery("from Employee").list();
			// convert your list to json
			String jsonStr = new Gson().toJson(li);
			p.print(jsonStr);
			System.out.println("successfully Printed saved");  
			session.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}   	
	}
	public void destroy() {
		factory.close();
    }
}


