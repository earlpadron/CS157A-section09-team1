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
    <title>Room Service</title>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    </head>
  <body>
  <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Room Service</i>
    </div>
    <br>
    <a href="login.jsp"><button>Log Out</button></a>
    <div class="center">
    <p1>Room Service Menu: </p1>
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM proj1test.roomservice;");
            int accID = (int) request.getSession().getAttribute("accountID");
            %>
            <form method='post' action ='roomService' >
            <table>
      		<thead>
      			<tr>
        			<td>Request</td>
        			<td>Price</td>
   				</tr>
            </thead>
            <tbody>
            	<tr>
            		<td>Breakfast</td>          
            		<td>$10</td>
            	</tr>
            	<tr>
            		<td>Brunch</td>          
            		<td>$12</td>
            	</tr>
            	<tr>
            		<td>Lunch</td>          
            		<td>$20</td>
            	</tr>
            	<tr>
            		<td>Dinner</td>          
            		<td>$35</td>
            	</tr>
            	<tr>
            		<td>Snack</td>          
            		<td>$7</td>
            	</tr>
            	</tbody>
            </table><br>
            <select name = "request">
            <option>Breakfast</option>
            <option>Brunch</option>
            <option>Lunch</option>
            <option>Dinner</option>
            <option>Snack</option>
            </select>
            
          	<input type = "submit" value = "Request">
            <br>
           </form>
            <div class="bcenter"><a href="HotelHomepage.jsp"><button>Back</button></a></div>
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
