<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
  <style>
    .selectClass {
      display: inline-block;
      width: 200px;
      height: 100px;
    }
  </style>
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
    <div class="modal fade" id="viewAnn" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h6 id="viewAnnTitle" class="modal-title">Modal title</h6>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div id="viewAnnContent" class="modal-body">
            <p>Modal body text goes here.</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="confirmQuizStart" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Are you sure you want to start the quiz?</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <p>Quiz Name: <span id="modal-quiz-name"></span></p>
            <p>There are <span id="modal-quiz-amm"></span> questions</p>
            <p>This will be recorded as your attempt.</p>
          </div>
          <div class="modal-footer">
            <a id="startQuizButton"><button type="button" class="btn btn-primary">Play</button></a>
            <button type="button" class="btn btn-secondary"  data-dismiss="modal">Cancel</button>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row test">
        <div class="col-sm left-col">
          <h3 id="welcome">Your Classes, ${firstName}</h3>
          <div class="border-top my-3"></div>
          <div id="class-holder" class="overflow-auto" style="height: 505px;">
            <c:forEach items="${userClasses}" var="c">
              <div class="card customCard">
                <div class="card-body">
                  <h5 class="card-title overflow-hidden">${c.getName()}</h5>
                  <h6 class="card-subtitle mb-2 text-muted">${c.getConvenor()}</h6>
                  <p class="card-text overflow-hidden">${c.getDesc()}</p>
                  <a href="/student-class/${c.getId()}" class="card-link">Class Page</a>
                </div>
              </div>
            </c:forEach>
            <div class="customCard overflow-auto" style="text-align: center;">
              <div class="card-title">
                <h4>Try Some Public Quizzes:</h4>
              </div>
              <div class="card-body">
                <c:forEach items="${quizzes}" var="quiz">
                  <div class="list-group-item" style="width: 90%;  margin: auto;">
                    <div class="media" style="text-align: left;">
                      <img src="../../resources/images/def-quiz.png" width="50px" height="50px" class="mr-3">
                      <div class="media-body" style="margin: auto;">
                        <h5>${quiz.getName()}</h5>
                      </div>
                      <a style="margin: auto;" href="/start-quiz?quizId=${quiz.getId()}""><i
                          class=" material-icons">play_arrow</i></a>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm right-col">
          <h3>Notifications</h3>
          <div class="border-top my-3"></div>
          <ul class="list-group overflow-auto" style="height: 320px;" id="notifHolder">
            <c:forEach items="${notifications}" var="n">
              <li class="list-group-item" class="overflow-hidden" style="min-width: 90%; max-width: 100%;  margin: auto; margin-top: 0px; margin-bottom: 3px;">
                <div class="media" style="text-align: left;">
                  <img src="../../resources/icons/notif.png" width="30px" height="30px" class="mr-3">
                  <div class="media-body" style="margin: auto; ">
                    <h5>${n.getContents()}</h5>
                  </div>
                  <c:choose>
                    <c:when test="${n.getType().equals('announcement')}">
                      <a style="margin: auto;" onclick="openAnn('${n.getAnnId()}')"><i
                        class=" material-icons">visibility</i></a>
                    </c:when>
                    <c:when test="${n.getType().equals('quiz')}">
                      <a style="margin: auto;" data-toggle="modal" data-target="#confirmQuizStart" onclick="confirmQuiz('${n.getQuizId()}', '${n.getQuizName()}', '${n.getQuizQuestionAmount()}')"><i
                        class=" material-icons">play_circle_outline</i></a>
                    </c:when>
                    <c:when test="${n.getType().equals('request')}">
                      <a style="margin: auto;" href="/friends-page?request=1"><i
                        class=" material-icons">person_add</i></a>
                    </c:when>
                    <c:when test="${n.getType().equals('badStu')}">
                      <a style="margin: auto;" href="/friends-page?message=${n.getAttachedUsername()}"><i
                        class=" material-icons">message</i></a>
                    </c:when>
                    <c:when test="${n.getType().equals('msg')}">
                      <a style="margin: auto;" href="/friends-page"><i
                        class=" material-icons">message</i></a>
                    </c:when>
                  </c:choose>
                  <i class="material-icons" onclick="deleteNotif('${n.getId()}')">delete</i>
                </div>

              </li>
            </c:forEach>

          </ul>
          <ul class="list-group" style="margin-top: 5%; width: 98%;">
            <li class="list-group-item list-group-item">
              <h3 style="text-align: left;">Looking to join a class? Enter a code:</h3>
              <div style=" word-wrap: break-word; max-width: 90%;">
                <div class="input-group mb-3">
                  <input id="joinClassInp" type="text" class="form-control" placeholder="Enter Class Code">
                  <div class="input-group-append">
                    <button class="btn btn-secondary" type="button" onclick="joinClass()">Join</button>
                  </div>
                </div>
              </div>
            </li>
          </ul>
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
<script>
  var classAmount = parseInt("${classes}");
  var firstName = "${firstName}";
</script>
<script src="../../resources/js/indexScript.js"></script>


</html>