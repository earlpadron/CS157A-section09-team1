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
<title> Register </title>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>
<body>
 <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Hotel Management System</i>
    </div>
<form method='post' action='HotelRegister.php' enctype='multipart/form-data'>
<pre> 
Username
<input type='text' name='username'>
Password
<input type = 'password' name = 'password'>
Repeat Password
<input type = 'password' name = 'repeatedPassword'>

<input type = 'submit' value = 'Back' name = 'backButton'>
<input type = 'submit' value = 'Register' name = 'registerButton'>
<pre></form></body></html>
_END;

require_once '157projlogin.php';
$conn = new mysqli($hn, $un, $pw, $db);
if ($conn->connect_error) die (mysql_fatal_error());

if (isset($_POST['username']) && isset($_POST['password']) && isset($_POST['repeatedPassword']) && isset($_POST['registerButton']))
{
	$username = $_POST['username'];
	$password = $_POST['password'];
	$repeatedPassword = $_POST['repeatedPassword'];

	$username = sanitizeString($username);
	$password = sanitizeString($password);
	$repeatedPassword = sanitizeString($repeatedPassword);
	
	if (isEmpty($username))
	{
		echo "Please enter a username. <br>";
	}
	else if (isEmpty($password))
	{
		echo "Please enter a password. <br>";
	}
	else if (isEmpty($repeatedPassword))
	{
		echo "Please repeate the password. <br>";
	}
	else if ($password != $repeatedPassword)
	{
		echo "The passwords do not match. <br>";
	}
	else
	{
		$query = "INSERT INTO account(username, password) VALUES ('$username', '$password')";
		$result = $conn->query($query);
		if(!$result) die(mysql_fatal_error());
		header('location: HotelLogin.php');
	}
}

if (isset($_POST['backButton']))
{
	header('location: HotelLogin.php');
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
