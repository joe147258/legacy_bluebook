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
    .student-wrapper {
      height: 85%;
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
            <h5 id="viewAnnTitle" class="modal-title">Modal title</h5>
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
    <div class="container">
      <div class="row class-container">
        <div class="col-sm left-col">
          <h3>${name}</h3>
          <div class="border-top my-3"></div>
          <div class="btn-group btn-group-justified" style="width:100%;" role="group" aria-label="Basic example">
            <button type="button" id="btn1" class="btn btn-outline-primary active toggle">Announcements</button>
            <button type="button" id="btn2" class="btn btn-outline-primary toggle">Work</button>
            <button type="button" id="btn3" class="btn btn-outline-primary toggle">Module Information</button>
          </div>
          <div class="overflow-auto" style="height: 70%">
            <div id="AnnouncementHub">
              <ul class="list-group">
                <c:forEach items="${ann}" var="ann">
                  <li class="list-group-item list-group-item studentClassAnn">
                    <span style="float: right;">
                      <span id="expand">
                        <i class="material-icons" onclick="openAnn('${ann.getId()}')">open_in_new</i>
                      </span>
                      ${ann.getDate()}</span>
                    <h3>${ann.getTitle()}</h3>
                    <div style=" word-wrap: break-word; max-width: 75%;">
                      ${ann.getContents()}
                    </div>
                  </li>
                </c:forEach>
                ${noAnn}
              </ul>
            </div>
            <div id="ModuleInfo" style="display: none; text-align: left;">
              <h5>${name}</h5>
              <h6 class="form-text text-muted">Taught by: ${teacherName}. </h6>
              <p>${desc}</p>
              <h5>perhaps a little edit page that allows people to use their own html</h5>
            </div>
            <div id="quizContainer" style="display: none;">
              <select id="select-tabs" class="custom-select custom-select-md mb-0">
                <option value="active">Active Quizzes</option>
                <option value="inactive">Previous Quizzes</option>
              </select>
              <hr>
              <div id="WorkDue">
                <c:forEach items="${activeQuizzes}" var="quiz">
                  <div class="list-group-item" style="width: 90%;  margin: auto;">
                    <div class="media" style="text-align: left;">
                      <img src="../../resources/images/def-quiz.png" width="50px" height="50px" class="mr-3">
                      <div class="media-body" style="margin: auto;">
                        <h5>${quiz.getName()}</h5>
                        <p class="m-0">Due: ${quiz.getDueDate()}</p>
                      </div>
                      <a style="margin: auto;" href="/start-quiz?quizId=${quiz.getId()}""><i
                          class=" material-icons">play_arrow</i></a>
                    </div>

                  </div>
                </c:forEach>
              </div>

              <div id="prevQuizzes" style="display: none;">
                <c:forEach items="${inactiveQuizzes}" var="quiz">
                  <div class="list-group-item" style="width: 90%;  margin: auto;">
                    <div class="media" style="text-align: left;">
                      <img src="../../resources/images/def-quiz.png" width="50px" height="50px" class="mr-3">
                      <div class="media-body" style="margin: auto;">
                        <h5>${quiz.getName()}</h5>
                        <p class="m-0">${quiz.getDueDate()}</p>
                      </div>
                      <a style="margin: auto;" href="/start-quiz?quizId=${quiz.getId()}"><i
                          class=" material-icons">play_arrow</i></a>
                    </div>

                  </div>
                </c:forEach>
              </div>
            </div>

          </div>
          <div class="border-top my-3"></div>
        </div>

        <div class="col-sm right-col">
          
          <h3>Teacher</h3>
          <div class="student-wrapper overflow-auto">
            <div style="width: 50%;  margin: auto;">
              <div class="media" style="text-align: left;">
                <img src="../../resources/images/teacherpic.png" width="50px" height="50px" class="mr-3" alt="...">
                <div class="media-body">
                  <h5 class="mt-0">${teacherName}</h5>
                  ${teacher.getUsername()}

                </div>
                <a style="float: right; margin: auto;" href="/friends-page?message=${teacher.getUsername()}"><i
                    class="material-icons">message</i></a>
              </div>
            </div>
            <hr>
            <h3>Students</h3>
            
            <c:forEach items="${classStudents}" var="classStudents">
              <div style="width: 50%;  margin: auto;">
                <div class="media" style="text-align: left;">
                  <img src="../../resources/images/defuser.png" width="50px" height="50px" class="mr-3" alt="...">
                  <div class="media-body">
                    <h5 class="mt-0">${classStudents.getFirstName()}</h5>
                    ${classStudents.getUsername()}

                  </div>
                  <a style="float: right; margin: auto;" href="/friends-page?message=${classStudents.getUsername()}"><i
                      class="material-icons">message</i></a>
                </div>
              </div>
            </c:forEach>
            ${noStu}
          </div>
          <div class="btn-group btn-block" role="group">

            <button class="btn btn-danger" onclick="leaveClass('${userId}')" style="width: 50%;">Leave Class</button>
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
<script>
  $(".btn").click(function () {
    if (this.id == "btn1") {
      $(btn1).addClass("active");
      $(btn2).removeClass("active");
      $(btn3).removeClass("active");
      $("#AnnouncementHub").show();
      $("#quizContainer").hide();
      $("#ModuleInfo").hide();
    } else if (this.id == "btn2") {
      $(btn1).removeClass("active");
      $(btn2).addClass("active");
      $(btn3).removeClass("active");
      $("#AnnouncementHub").hide();;
      $("#quizContainer").show();
      $("#ModuleInfo").hide();
    } else if (this.id == "btn3") {
      $(btn1).removeClass("active");
      $(btn2).removeClass("active");
      $(btn3).addClass("active");
      $("#AnnouncementHub").hide();
      $("#quizContainer").hide();
      $("#ModuleInfo").show();
    }
  })

  function openAnn(id) {
    $.ajax({
      type: "GET",
      url: "/get-announcement?annId=" + id,
      success: function (data) {
        $('#viewAnnTitle').html(data[0]);
        $('#viewAnnContent').html(data[1]);
        $('#viewAnn').modal('toggle');
      }
    })
  }
  $("#select-tabs").change(function () {

    if ($("#select-tabs").val() == "active") {
      $("#WorkDue").show();
      $("#prevQuizzes").hide();
    } else if ($("#select-tabs").val() == "inactive") {
      $("#WorkDue").hide();
      $("#prevQuizzes").show();

    }

  });

  function leaveClass(uid){
    var cid = '${classId}';
    $.ajax({
        type: "POST",
        url: "/remStudent/" + uid + "/" + cid,
        success: function (data) {
          window.location.replace("/");
        }
    })
  }
</script>

</html>