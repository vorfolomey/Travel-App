<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TravelApp</title>
</head>
<body>

	<h1>Travel App</h1>
	<h4>Register Form</h4>
	
	<form action="registration" method="post">
    <table>
      <tr>
        <td>Username</td>
        <td><input type="text" name="username"></td>
      </tr>
        <tr>
            <td>First Name</td>
            <td><input type="text" name="firstname"></td>
        </tr>
        <tr>
            <td>Last Name</td>
            <td><input type="text" name="lastname"></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="email" name="email"></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password"></td>
        </tr>
      <tr>
        <td>Repeat password</td>
        <td><input type="password" name="passwordConfirm"></td>
      </tr>
      <tr>

        <td><button type="submit">Register</button></td>
      </tr>
    </table>
  </form>
  <br/>
</body>
</html>