<%@ page import="java.sql.*"%>
<html>
  <head>
  <style>
  .b1{float:right;
  }
  p1{text-align: left;
  	font-size:125%;
  }
  body{
  	margin:0; padding:0;
  }
  .p2Div{text-align: center;
  	margin-top: 10px;
  	margin-bottom: 10px;
  }
  .center{margin:0;
  	position: absolute;
  	top: 50%;
  	left: 50%;
  	-ms-transform: translate(-50%, -50%);
  	transform: translate(-50%, -50%);
  }
  .bcenter{
  	text-align: center;
  }
  table{border: 1px solid black;
  }
  td{border: 1px solid black;
  	height: 25px;
  	width: 100px;
  	text-align: center;
  	vertical-align: bottom;
  }
  
  </style>
    <title>Manage Rooms</title>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    </head>
  <body>
  <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Manage Rooms</i>
    </div>
    <a href="login.jsp"><button>Log Out</button></a>
    <br>
    <div class="center">
    <p1>List of Rooms: </p1>
    <% 
     String db = "cs157a"; //change to db name
        String user; // assumes database name is the same as username
          user = "root";
        String password = "brian";
        try {
        	String username = (String)request.getSession().getAttribute("username");
            java.sql.Connection con;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password); //change cs157a to db name
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM proj1test.rooms WHERE availability = 'reserved'"); //change to schema name.table name
            %>
            <form method='post' action ='roomManagement'>
            <table>
      		<thead>
      			<tr>
      				<td>Room Number</td>
        			<td>Room Type</td>
        			<td>Room Availability</td>
        			<td>Room Price</td>
   				</tr>
            </thead>
            <tbody>
            <%while(rs.next())
            {
            	%>
            	<tr>
            		<td><%=rs.getInt("roomNumber") %></td> 
            		<td><%=rs.getString("roomType") %></td>
            		<td><%=rs.getString("availability") %></td>
            		<td>$<%=rs.getInt("price") %></td>
            	</tr>
            	<%}%>
            	</tbody>
            </table><br>
            Room: <select name = "reserveRoom">
            <%
            rs = stmt.executeQuery("SELECT * FROM proj1test.rooms WHERE availability = 'reserved'");
            while(rs.next())
            {
            	%>
            	 <option><%=rs.getInt("roomNumber")%></option>
            <%}%>
            </select>
            <input type = "submit" value = "Set availability">
            <br>
            </form>
            <div class="bcenter"><a href="HotelEmployeeHomepage.jsp"><button>Back</button></a></div>
            <%
        		rs.close();
                stmt.close();
                con.close();
            }
        	
        	catch(SQLException e) { 
                out.println("SQLException caught: " + e.getMessage()); 
        	}%></div>
  </body>
</html>
