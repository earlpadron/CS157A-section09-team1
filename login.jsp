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
    <title>Login</title>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    </head>
  <body>
  <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Login</i>
    </div>
    <br>
    <div class="center">
    <% 
     String db = "cs157a"; //change to db name
        String user; // assumes database name is the same as username
          user = "root";
        String password = "brian";
        try {
            java.sql.Connection con;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password); //change cs157a to db name
            Statement stmt = con.createStatement();
            
           %>
            <form method='post' action ='hotelLogin' >
           
            Username <br>
            <input type= 'text' name ="username"> <br>
           
           	Password <br>
           	<input type= 'text' name = "password"> <br>
           	
           	<br>
           	<div class="bcenter"><input type = 'submit' value = 'Login' name = 'loginButton'> <br>
           	<br>
            <a href="register.jsp"><button>Register</button></a> 
            </div>
            </form>
            
            <%
            	stmt.close();
                con.close();
            }
        	
        	catch(SQLException e) { 
                out.println("SQLException caught: " + e.getMessage()); 
        	}%></div>
  </body>
</html>
