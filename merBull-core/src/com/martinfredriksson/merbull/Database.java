package com.martinfredriksson.merbull;

import java.sql.*;
import javax.sql.*;

public class Database {
	Connection conn = null;
	
	public Database() {
		try
	       {
			   //pls no dropperino dropTable();
	           String url = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_255e532429611b1";
	           Class.forName ("com.mysql.jdbc.Driver");
	           conn = DriverManager.getConnection (url,"bd9fedae0c295a","b0243d5d");
	           System.out.println ("Database connection established");
	           
	           String myTable = "CREATE TABLE IF NOT EXISTS Scoreboard (" 
	                   + "id INT(64) NOT NULL AUTO_INCREMENT,"  
	                   + "name VARCHAR(30),"
	                   + "level INT(30)," 
	                   + "bounces INT(30),"  
	                   + "time FLOAT,"
	                   + "weighted_score FLOAT,"
	                   + "PRIMARY KEY (`id`))";  
	               try {
	                   Class.forName("com.mysql.jdbc.Driver");
	                   Statement st = conn.createStatement();

	                   st.execute(myTable);
	                   System.out.println("Table Created");
	               }
	               catch (SQLException e ) {
	                   System.out.println("An error has occurred on Table Creation");
	                   System.out.println(e);
	               }
	               catch (ClassNotFoundException e) {
	                   System.out.println("An Mysql drivers were not found");
	               }
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();

	       }
	}
	
	public boolean addEntry(String name, int bounces, float time) {
		try {
			Statement st = (Statement) conn.createStatement(); 
			//tmp level var
			int level = 1;
			
			time = (float)Math.round(time * 100) / 100;
		     st.executeUpdate("INSERT INTO Scoreboard(name, level, bounces, time, weighted_score) " +
		     "VALUES ('" + name + "', '"+ level + "', '" + (Level.getMaxBounces()-bounces) + "', '" + time + "', '" + (bounces*100)/time + "')");
		     
		     return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void closeConn() {
		if (conn != null)
        {
            try
            {
                conn.close ();
                System.out.println ("Database connection terminated");
            }
            catch (Exception e) { /* ignore close errors */ }
        }
	}
	
	public Scoreboard getTop10(int level) {
		Scoreboard sb = new Scoreboard();
		
		try {
			Statement st = (Statement) conn.createStatement(); 

		     ResultSet rs = st.executeQuery("SELECT * FROM Scoreboard WHERE level=level ORDER BY weighted_score DESC LIMIT 10;");
		     
		     
		     while(rs.next()) { 
		    	 String name = rs.getString("name");
		    	 int bounces = rs.getInt("bounces");
		    	 float time = rs.getFloat("time");
		    	 float weighted_score = rs.getFloat("weighted_score");
		    	 
		    	 sb.row();
		    	 sb.add(name);
		    	 sb.add(Integer.toString(bounces));
		    	 sb.add(Float.toString(time));
		    	 sb.add(Float.toString(weighted_score));
		    	 
		    	}
		     
		}
		catch (Exception e) {
			e.printStackTrace();
			sb.row();
			sb.add("No Entries.");
		}
		
		return sb;
	}
	
	public void dropTable() {
		try
	       {

	           String url = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_255e532429611b1";
	           Class.forName ("com.mysql.jdbc.Driver");
	           conn = DriverManager.getConnection (url,"bd9fedae0c295a","b0243d5d");
	           System.out.println ("Database connection established");
	           
	           String myTable = "DROP TABLE SCOREBOARD";  
	               try {
	                   Class.forName("com.mysql.jdbc.Driver");
	                   Statement st = conn.createStatement();

	                   st.execute(myTable);
	                   System.out.println("Table Removed");
	               }
	               catch (SQLException e ) {
	                   System.out.println("An error has occurred on Table removing");
	                   System.out.println(e);
	               }
	               catch (ClassNotFoundException e) {
	                   System.out.println("An Mysql drivers were not found");
	               }
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();

	       }
	}
	
}
