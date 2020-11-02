<?php

echo <<<_END
<style>
.p2Div{text-align: center;
  	margin-top: 10px;
  	margin-bottom: 10px;
  }
body {text-align: center;}
</style>
<html>
<head>
<title> Login </title>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>
<body>
 <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Hotel Management System</i>
    </div>
<form method='post' action='HotelLogin.php' enctype='multipart/form-data'>
<pre> 
Username
<input type='text' name='username'>
Password
<input type = 'password' name = 'password'>

<input type = 'submit' value = 'Login' name = 'loginButton'>
<input type = 'submit' value = 'Register' name = 'registerButton'>
<pre></form></body></html>
_END;

require_once '157projlogin.php';
$conn = new mysqli($hn, $un, $pw, $db);
if ($conn->connect_error) die (mysql_fatal_error());

// Log in handling
if (isset($_POST['username']) && isset($_POST['password']) && isset($_POST['loginButton']))
{
	$username = $_POST['username'];
	$password = $_POST['password'];

	$username = sanitizeString($username);
	$password = sanitizeString($password);
	
	if (isEmpty($username))
	{
		echo "Please fill out a username. <br>";
	}
	else if (isEmpty($password))
	{
		echo "Please fill out a password. <br>";
	}
	else
	{
		$query = "SELECT * FROM account";
		$result = $conn->query($query);
		if(!$result) die(mysql_fatal_error());
	
		$rows = $result->num_rows;
	
		for($i = 0; $i < $rows; $i++)
		{	
			$output = $result->data_seek($i);
			$row = $result->fetch_array(MYSQLI_ASSOC);
		
			$uname = $row['username'];
			$pword = $row['password'];

			if (($username == $uname) && ($password == $pword))
			{
				session_start();
				header('location: http://localhost:8080/CS157A/HotelHomepage.jsp');
			}
		}
		die ("Incorrect username or password.<br>");
	}
}

// Register 
if (isset($_POST['registerButton']))
{
	header('location: HotelRegister.php');
}

$conn->close();

function isEmpty($string)
{
	$string = trim($string);
	
	if ($string != "")
	{
		return false;
	}
	
	return true;
}

function sanitizeString($var)
{
	$var = stripslashes($var);
	$var = strip_tags($var);
	$var = htmlentities($var);
	return $var;
}

function mysql_fatal_error()
{
	$image = 'https://cdn.discordapp.com/emojis/655219185894555648.png?v=1';
	$imageData = base64_encode(file_get_contents($image));
	echo '<img src="data:image/png;base64,'.$imageData.'">';
}
?>
