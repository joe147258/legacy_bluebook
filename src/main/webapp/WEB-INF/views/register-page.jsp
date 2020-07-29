<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <title>Bluebook</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta name="description" content="Dissertation Project - Bluebook - interactive classrooms with teachers!" />
  <meta name="Joseph Phillips"
    content="Dissertation Project - Bluebook - An interactive classrooms with interactive teachers!" />
  <!-- css -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
    integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <style>
    .navbar-light .navbar-nav .active>.nav-link,
    .navbar-light .navbar-nav .nav-link.active,
    .navbar-light .navbar-nav .nav-link.show,
    .navbar-light .navbar-nav .show>.nav-link {
      color: white;
    }

    .navbar-light .navbar-nav .nav-link {
      color: white;
    }

    .form-signup {
      width: 100%;
      max-width: 330px;
      padding: 15px;
      margin: 0 auto;
    }

    .form-signup {
      box-shadow: 0 2px 4px 0 grey;
      margin-top: 2%;
    }

    input {
      margin-top: 5px;
    }
  </style>
</head>
<%
String message = "";
int err = 0;
String err_msg = request.getParameter("error");
if(err_msg!= null) err = Integer.parseInt(err_msg);
if(err!=0){
  if(err == 1){
    message = "<br/><div class='alert alert-danger'>Passwords must match</div>";
  } else if(err == 2) {
    message = "<br/><div class='alert alert-danger'>Username is taken!</div>";
  } else if (err == 3) {
    message = "<br/><div class='alert alert-danger'>Email is taken!</div>";
  } else if (err == 4) {
    message = "<br/><div class='alert alert-warning'>Please fill all fields.</div>";
  }
}
%>

<body>
  <div class="text-center form-signup">
    <img class="mb-1" src="../../resources/images/bluebook.png">
    <h2 class="h3 mb-3 font-weight-normal">Register</h2>
    <form action="/register" method="POST">
      <div>
        <input class="form-control" name="username" placeholder="Username" id="username-input" required/>
      </div>

      <div>
        <input class="form-control" name="firstName" placeholder="First Name" required/>
      </div>

      <div>
        <input class="form-control" name="lastName" placeholder="Last Name" required/>
      </div>

      <div>
        <input class="form-control" type="email" name="email" placeholder="Email" id="email-input" required/>
      </div>

      <div>
        <input class="form-control" type="password" name="password" placeholder="Password" required/>
      </div>

      <div>
        <input class="form-control" type="password" name="matchPassword" placeholder="Repeat Password" required/>
      </div>
      <br />
      <button class="btn btn-lg btn-primary btn-block" type="submit" id="submitbutton">Sign up</button>
      <%=message%>
      <div class="alert alert-danger" style="margin-top: 1%; display: none;" id="UsernameError" >Username Taken</div>
      <div class="alert alert-danger" style="margin-top: 1%; display: none;" id="emailError" >Email Taken</div>
      <p class="mt-1 mb-1 text-muted">Already on bluebook? <a href="/sign-in"> Sign In</a></p>
      <p class="mt-3 mb-3 text-muted">Joseph Phillips <br />University of Leicester,<br />Department of Informatics</p>
      <img class="mb-4" src="../../resources/images/uol.jpg" width="188" height="50">
    </form>
    
  </div>


</body>  
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
  integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
  integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script>
  $(document).ready(function () {
    $(window).keydown(function (event) {
      if (event.keyCode == 13) {
        event.preventDefault();
        if ($("#password").val() == $("#password1").val()) {
          RegisterUser();
        } else {
          alert("Passwords must match");
        }
        return false;
      }
    });
  });

  function RegisterUser() {
    var firstName = $("#firstName").val();
    var username = $("#username").val();
    var password = $("#password").val();
    console.log(firstName);
    console.log(username);
    console.log(passowrd);
    alert("gone through!");

  }
  $("#username-input").change(function () { 
    var user =  $("#username-input").val();
    $.ajax({
      type: "POST",
      url: "/check-user/" + user,
      success: function (data) {
        if (data.ret == "exists") {
          $("#UsernameError").show();
          $("#submitbutton").attr("disabled", true);
        } else {
          $("#UsernameError").hide();
          $("#submitbutton").attr("disabled", false);
        }
      }
    })

  });

  $("#email-input").change(function () { 
    var email =  $("#email-input").val();
    console.log("here");
    $.ajax({
      type: "POST",
      url: "/check-email/" + email,
      success: function (data) {
        if (data.ret == "exists") {
          $("#emailError").show();
          $("#submitbutton").attr("disabled", true);
        } else {
          $("#emailError").hide();
          $("#submitbutton").attr("disabled", false);
        }
      }
    })

  });
</script>

</html>