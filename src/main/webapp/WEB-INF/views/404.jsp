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
  <link rel="stylesheet" href="../../resources/style/stylesheet.css" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body>
  <div id="wrapper">
    <!-- start header -->
    <header>
      <div class="container">
        <div class="row">
          <div class="span4">
            <div class="logo">
              <a href="/"><img src="../../resources/images/bluebook.png" alt="" class="logo" /></a>
              <h1>An Interactive Learning Enviroment. </h1>
            </div>
          </div>
        </div>
      </div>
      <nav class="navbar navbar-expand-lg navbar-light bg-primary">
        <div class="collapse navbar-collapse" id="navbarSupportedContent" style="color: white; margin-left: 15%;">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item">
              <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item active dropdown ">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
                Quizzes
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" onclick="location.href='/create-quiz';" style="cursor: pointer;">Create a
                  quiz!</a>
                <a class="dropdown-item" onclick="location.href='/all-quizzes';" style="cursor: pointer;">View All
                  Quizzes</a>
              </div>
            </li>
            <li class="nav-item active dropdown ">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
                Classes
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" onclick="location.href='/create-class';" style="cursor: pointer;">Create a
                  class</a>
                <a class="dropdown-item" onclick="location.href='/view-classes';" style="cursor: pointer;">View your
                  classes</a>
              </div>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/friends-page">Social (${socialNumber})</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Versus</a>
            </li>
          </ul>
          <span style="margin-right: 25%;">
            <ul class="navbar-nav mr-auto">
              <li class="nav-item active dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                  aria-haspopup="true" aria-expanded="false">
                  ${username}
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <a class="dropdown-item" onclick="location.href='/view-profile?id=${userId}'"
                    style="cursor: pointer;">Your profile</a>
                  <a class="dropdown-item" onclick="location.href='/view-classes';" style="cursor: pointer;">View your
                    Quizzes</a>
                  <a class="dropdown-item" onclick="location.href='/logout/';" style="cursor: pointer;">Log out</a>
                </div>
              </li>
            </ul>
          </span>
        </div>
      </nav>
    </header>
    <!-- end header -->
    <div class="container">
      <div class="row test">
        <div class="container" style="text-align: center;">
            <h1 class="font-weight-bold">404</h1>
            <i class="material-icons" style="font-size: 100px;">report_problem</i>
            <h3>Page Not Found</h3>
            <h5>Sorry! But that page doesn't seem to exist.</h5>
            <br>
            
            <div style="width: 50%; margin: auto;">
              <a class="btn btn-primary btn-block" href="/">Return To Index</a>
            </div>
            
        </div>

      </div>
    </div>
  </div>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
  integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
  integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</html>