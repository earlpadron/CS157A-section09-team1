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
    <title>Register</title>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    </head>
  <body>
  <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Register</i>
    </div>
    <br>
    <div class="center">
    
            <form method='post' action ='roomService' >
           
            Room Number <br>
            <input type= 'text' name ="roomNumber"> <br>
           
           	Request <br>
           	<input type= 'text' name = "req"> <br>
           	
           	Price <br>
           	<input type= 'text' name = "pr"> <br>
       	
           	<br>
           	<div class="bcenter"><input type = 'submit' value = 'Request' name = 'requestButton'> </div><br>
           	<br>
           	</form>
            <div class="bcenter"><a href="roomServiceList.jsp"><button>Back</button></a> </div> </div>
  </body>
</html>
