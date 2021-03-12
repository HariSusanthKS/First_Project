 import javax.servlet.*;  
import javax.servlet.http.*;  
import java.io.*; 
import java.util.*; 
import java.sql.*;
import javax.xml.parsers.*;
import org.w3c.dom.*; 
import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
public class WithOrWithOut extends HttpServlet
{  
	public static String db_adapter;
	public static String tablename;
	public static StandardServiceRegistry ssr=null,ssr1=null;
	public static Metadata meta=null,meta1=null;
	public static SessionFactory factory=null ,factory1=null;
	public void init() throws ServletException 
	{
		try
		{
			// Read conf file to find which database we are going to use 	
			BufferedReader br = new BufferedReader(new FileReader("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\first\\WEB-INF\\conf.txt"));
			String s;
			s=br.readLine();
			db_adapter=s.substring(11,16);
			System.out.println("db_adapter :" +db_adapter);  
			br.close();
			// database file connection initiated  
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
		res.setContentType("text/html");
		PrintWriter p=res.getWriter();		
		
		// read the value from the radio button(with or without data)
		String w = req.getParameter("change");		
		// read the value from the radio button(database)
		String db1 = req.getParameter("db");
		
		if(db_adapter.equals(db1))
		{
			p.println("Already your are in the "+db_adapter+" Database Tomcat Restarted");
			WithOrWithOut.shutdown();				
		}
		else if(w.equals("without"))
		{
			p.println("you changed "+db_adapter+" database to "+db1+" And Restarted the Tomcat");
			WithOrWithOut.change(db1);
			WithOrWithOut.shutdown();
		}
		// Database that user like to change
		else{
			p.println("you changed "+db_adapter+" database to "+db1+" And Restarted the Tomcat");
			if(db1.equals("mssql"))
			{
				 ssr1 = new StandardServiceRegistryBuilder().configure("mssqlcreatehibernate.cfg.xml").build();
				
			}
			else if(db1.equals("mysql"))
			{
				 ssr1 = new StandardServiceRegistryBuilder().configure("mysqlcreatehibernate.cfg.xml").build();
			}
			else if(db1.equals("postr"))
			{
				 ssr1 = new StandardServiceRegistryBuilder().configure("postrgescreatehibernate.cfg.xml").build();
			}	
				meta1 = new MetadataSources(ssr1).getMetadataBuilder().build();
				factory1 = meta1.getSessionFactoryBuilder().build();			
			try
			{
				//code to retrive data from one database and move the data to the selected database 
				Session session = factory.openSession();
				Transaction t = session.beginTransaction(); 				
				Session session1 = factory1.openSession();
				Transaction t1 = session1.beginTransaction();					
				List li=session.createQuery("from Employee").list();
				Iterator it=li.iterator();										
				while(it.hasNext())
				{								
					Object o=(Object)it.next();
					Employee e=(Employee)o;										
					Employee e1=new Employee();  															
					e1.setFirstName(e.getFirstName());
					e1.setLastName( e.getLastName());
					e1.setEmail(e.getEmail());
					e1.setPhno(e.getPhno());
					e1.setMotherName(e.getMotherName());
					e1.setFatherName(e.getFatherName());
					e1.setAddress(e.getAddress());
					e1.setDOB(e.getDOB());								
					session1.save(e1);						
				}
				t.commit();	
				t1.commit();		
				session.close();
				session1.close(); 
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}		
			WithOrWithOut.change(db1);
			WithOrWithOut.shutdown();			
		}		
	}
	// change conf file to required database by the user Eg:(Mssql to Mysql)
	public static void change(String w)
	{		
		try
		{
			FileWriter writer = new FileWriter("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\first\\WEB-INF\\conf.txt");  
			BufferedWriter buffer = new BufferedWriter(writer);   	
			if(w.equals("mssql"))
			{
				buffer.write("DB_adaptor=mssql");  
			}
			else if(w.equals("postr"))
			{
				buffer.write("DB_adaptor=postr");  
			}
			else if(w.equals("mysql"))
			{
				buffer.write("DB_adaptor=mysql");  
			}
			buffer.close(); 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	//Function to restart the tomcat 	
	public static void shutdown()
	{
		try
		{
			//to stop 
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "shutdown.bat");
			File dir = new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\bin");
			pb.directory(dir);
			Process pr = pb.start();			
			//to start			
			ProcessBuilder p = new ProcessBuilder("cmd", "/c", "tomcat8");
			File dr = new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\bin");
			p.directory(dr);
			Process prs  = p.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
	}
	public void destroy() {
		factory1.close();
		factory.close();
    }
}