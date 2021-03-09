package pkg;
import java.sql.*;
import java.sql.DriverManager;


public class Item {
	
	public Connection connect()
	{
		Connection con = null;

		try{
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/dta","root@", "");
			System.out.println("Successfully connected");
			}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("null");
			}

		System.out.println("nothing");
		return con;
		
	}
	
	public String insertItem(String code, String name, String price, String desc)
	{
	 String output = "";
	try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
	 return "Error while connecting to the database";
	 }
	 // create a prepared statement
	 String query = " insert into items(itemID,itemCode,itemName,itemPrice,itemDesc)"+ " values (? ,? ,? ,? ,? )";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, code);
	 preparedStmt.setString(3, name);
	 preparedStmt.setDouble(4, Double.parseDouble(price));
	 preparedStmt.setString(5, desc); 
	
	//execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Inserted successfully";
	 }
	catch (Exception e)
	 {
	 output = "Error while inserting";
	 System.err.println(e.getMessage());
	 }
	return output;
	}
	
	
	public String readItems()
	{
	 String output = "";
	try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
	 return "Error while connecting to the database for reading.";
	 }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Item code</th><th>Item name</th>"
	 + "<th>Item price</th><th>Item description</th>"
	 + "<th>Update</th><th>Remove</th></tr>";
	 
	 String query = "select * from items";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String itemID = Integer.toString(rs.getInt("itemID"));
	 String itemCode = rs.getString("itemCode");
	 String itemName = rs.getString("itemName");
	 String itemPrice = Double.toString(rs.getDouble("itemPrice"));
	 String itemDesc = rs.getString("itemDesc");
	 


	 output += "<tr><td>" + itemCode + "</td>";
	 output += "<td>" + itemName + "</td>";
	 output += "<td>" + itemPrice + "</td>"; 
	 output += "<td>" + itemDesc + "</td>";
	 
	 output += "<td><form method='post' action='Items.jsp'>"+
				 "<input name='btnUpdate' type='submit' value='Update'>"+
				 "<input name='action' value='select' type='hidden'>"+
				 "<input name='itemID' type='hidden' value='" + itemID + "'>" +
				 "</form></td>"+
				 
				 "<td><form method='post' action='Items.jsp'>"+
				 "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"+
				 "<input name='itemID' type='hidden' value='" + itemID + "'>"+ 
				 "<input name='action' value='remove' type='hidden'>"+
				 "</form></td></tr>";
	 }
	 con.close();
	 
	 output += "</table>";
	 }
	catch (Exception e)
	 {
	 output = "Error while reading the items.";
	 System.err.println(e.getMessage());
	 }
	return output;
	}

	//update Items
		public String updateItem(int id,String code, String name, String price, String desc) throws SQLException {
			
			String output = "";
			
			try
			{
				Connection con = connect();
				
				if (con == null)
				{
					return "Connection issue";
				}
				
				// create a sql statement
				String query = "update items set itemCode='"+code+"', itemName='"+name+"', itemPrice='"+price+"', itemDesc='"+desc+"'" + "where itemID='"+id+"'";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, id);
				preparedStmt.setString(2, code);
				preparedStmt.setString(3, name);
				preparedStmt.setDouble(4, Double.parseDouble(price));
				preparedStmt.setString(5, desc);
				
				//execute the statement
				preparedStmt.execute();
				con.close();
				
				output = "Updated the details successfully";
			}
			catch (Exception e)
			{
				output = "Details are not successfully updated";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		
		//Update
		public String updateItems(int id, String code, String name, String price, String desc)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database";
				}

				// create a prepared statement
				String query = "update items set `itemCode`=?,`itemName`=?,`itemPrice`=?,`itemDesc`=? where `itemID`=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, code);
				preparedStmt.setString(2, name);
				preparedStmt.setDouble(3, Double.parseDouble(price));
				preparedStmt.setString(4, desc);
				preparedStmt.setInt(5, id);

				//execute the statement
				preparedStmt.executeUpdate();
				con.close();
				output = "Item " + id + " Updated successfully";
			}
			catch (Exception e)
			{
				output = "Error while updating";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		
		//read item detail
		public String read_Item_detail(int id)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Connection issue";
				}

				String query = "select * from items where `itemID`=?;";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1, id);
				ResultSet rs = preparedStmt.executeQuery();

				
				if (rs.next())
				{
					String itemID = Integer.toString(rs.getInt("itemID"));
					String itemCode = rs.getString("itemCode");
					String itemName = rs.getString("itemName");
					String itemPrice = Double.toString(rs.getDouble("itemPrice"));
					String itemDesc = rs.getString("itemDesc");

					output += "<form method='post' action='Items.jsp'> "+
							 "Item code: <input name='itemCode' type='text' value='"+ itemCode +"'><br>" +
							 "Item name: <input name='itemName' type='text' value='"+ itemName +"'><br> "+
							 "Item price: <input name='itemPrice' type='text' value='"+ itemPrice +"'><br> "+
							 "Item description: <input name='itemDesc' type='text' value='"+ itemDesc +"'><br> "+
							 "<input name='action' value='update' type='hidden'> "+
							 "<input name='itemID' value='"+ itemID +"' type='hidden'> "+
							 "<input name='btnSubmit1' type='submit' value='Update Item "+ id +"'> "+
							 "</form> <br>"+
							 "<a href='Items.jsp'>Cancel Updating</a>";
				}

				con.close();
			}
			catch (Exception e)
			{
				output = "Details reading was not successful.\nGo back to <a href='Items.jsp'>Items.jsp</a>";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		public String delete_Item(int id)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Connection issue";
				}

				// create a sql statement
				String query = "delete from items where itemID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1, id);

				//execute the statement
				preparedStmt.executeUpdate();
				con.close();
				output = "Item " + id + " Deleted successfully";
			}
			catch (Exception e)
			{
				output = " Deleting was not successfully";
				System.err.println(e.getMessage());
			}
			return output;
		}
	

		

	
	


}
