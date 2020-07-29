<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<%
String loginFail = "";
String err_str=request.getParameter("error");
if(err_str!=null){
  loginFail = "<br/><div class='alert alert-danger'>Incorrect username or password</div>";
}
%>
<head>
    <meta charset="utf-8">
    <title>Bluebook</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Dissertation Project - Bluebook - interactive classrooms with teachers!" />
    <meta name="Joseph Phillips" content="Dissertation Project - Bluebook - An interactive classrooms with interactive teachers!" />
      <!-- css -->
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <style>
    .navbar-light .navbar-nav .active>.nav-link, .navbar-light .navbar-nav .nav-link.active, .navbar-light .navbar-nav .nav-link.show, .navbar-light .navbar-nav .show>.nav-link {
    color: white;
    }

    .navbar-light .navbar-nav .nav-link {
    color: white;
    }
    .form-signin {
    width: 100%;
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
    }
    .form-signin{
      box-shadow: 0 2px 4px 0 grey;
      margin-top: 2%;
    }

    </style>
</head>

<body>
    <div class="text-center">
      <form class="form-signin" th:action="@{/login}" method="post">
        <img class="mb-1" src="../../resources/images/bluebook.png">
        <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
        <label for="inputUser" class="sr-only">Username</label>
        <input type="text" id="inputUser" class="form-control" placeholder="Username" name="username">
        <label for="inputPassword" class="sr-only">Password</label>
        <input style="margin-top: 5px;" type="password" id="inputPassword" class="form-control" placeholder="Password" name="password" >
        <div class="checkbox mb-3">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <%=loginFail%>
        <p class="mt-3 mb-3 text-muted">Don't have an account? <a href="/register-page">Sign up here!</a></p>
        <p class="mt-3 mb-3 text-muted">Joseph Phillips <br/>University of Leicester,<br/>Department of Informatics</p>
        <img class="mb-4" src="../../resources/images/uol.jpg" width="188" height="50">
      </form>
    </div>



</body>
  <script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
  </html>
