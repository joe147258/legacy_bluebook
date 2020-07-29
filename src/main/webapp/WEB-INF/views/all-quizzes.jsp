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
  <style>
    .card-img,
    .card-img-bottom,
    .card-img-top {
      -ms-flex-negative: 0;
      width: auto;
      margin: auto;
    }
  </style>
</head>
<div class="modal fade" id="private-modal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Toggle Quiz Visbility</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" style="text-align: center;">
        Are you sure you want to toggle this quiz's visbility? This can be changed.
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
        <button onclick="confirmToggleVis()" type="button" class="btn btn-primary">Yes</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="completed-edit-modal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Confirm Edit</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" style="text-align: left;">
        A user has already completed this quiz. Are you sure you want to edit it? Their mark will stay the same.
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
        <a id="completed-edit-yes" class="btn btn-primary" >Yes</a>
      </div>
    </div>
  </div>
</div>
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
        <div class="col-sm left-col" id="pubQuizzes">
          <h3>Public Quizzes</h3>
          <div class="border-top my-3"></div>
          <c:forEach items="${quizList}" var="quiz">
            <div class="list-group-item" style="width: 90%;  margin: auto;">
              <div class="media" style="text-align: left;">
                <img src="../../resources/images/def-quiz.png" width="50px" height="50px" class="mr-3">
                <div class="media-body" style="margin: auto;">
                  <h5>${quiz.getName()}</h5>
                </div>
                <a style="margin: auto;" href="/start-quiz?quizId=${quiz.getId()}"><i
                    class=" material-icons">play_arrow</i></a>
              </div>
            </div>
          </c:forEach>
        </div>
        <div class="col-sm right-col">
          <h3>Your Quizzes</h3>
          <div class="border-top my-3"></div>
          <div id="listofquizzes">
            <c:forEach items="${privateQuizList}" var="quiz">
              <div class="list-group-item" style="width: 90%;  margin: auto;">
                <div class="media" style="text-align: left;">
                  <img src="../../resources/images/def-quiz.png" width="50px" height="50px" class="mr-3">

                  <div class="media-body" style="margin: auto;">
                    <h5>${quiz.getName()}</h5>
                    <p class="m-0">Due on: ${quiz.getDueDate()}</p>
                    <p class="m-0">Scheduled for: ${quiz.getScheduledFor()}</p>
                  </div>
                  <c:choose>
                    <c:when test="${quiz.getCompleted() == false}">
                      <a style="margin: auto;" href="/quiz-edit-question/${quiz.getId()}"><i
                        class="material-icons">edit</i></a>
                    </c:when>
                    <c:otherwise>
                      <a style="margin: auto;" onclick="completedEdit('${quiz.getId()}')"><i
                        class="material-icons">edit</i></a>
                    </c:otherwise>
                  </c:choose>

                  <span style="margin: auto;" onclick="toggleVis('${quiz.getId()}')"><i class="material-icons">

                      <c:choose>
                        <c:when test="${quiz.getHidden() == false}">
                          visibility_off
                        </c:when>
                        <c:otherwise>
                          visibility
                        </c:otherwise>
                      </c:choose>
                    </i>

                  </span>
                <a style="margin: auto;" onclick="delQuiz('${quiz.getId()}')"><i
                  class="material-icons">delete_forever</i></a>
                </div>

              </div>
            </c:forEach>
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
  var quizIdTemp;

  function toggleVis(id) {
    $("#private-modal").modal("toggle");
    quizIdTemp = id;
  }

  function confirmToggleVis() {
    $.ajax({
      type: "GET",
      url: "/toggle-quiz?quizId=" + quizIdTemp,
      success: function (data) {
        $("#private-modal").modal('toggle');
        $("#listofquizzes").load(" #listofquizzes > *");
        $("#pubQuizzes").load(" #pubQuizzes > *");
      }
    })
  }

  function delQuiz(id){
    $.ajax({
        type: "GET",
        url: "/delete-quiz?qid=" + id, 
        success: function (data) {
            $("#listofquizzes").load(" #listofquizzes > *");
            $("#pubQuizzes").load(" #pubQuizzes > *");
        }
    })
}
function completedEdit(id){
  $("#completed-edit-modal").modal('toggle');
  $("#completed-edit-yes").removeAttr("href");
  $("#completed-edit-yes").attr("href", "/quiz-edit-question/" + id);

}

</script>

</html>