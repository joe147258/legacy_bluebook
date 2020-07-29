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
    <!-- start modals -->
    <div class="modal fade" id="editAnn" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <input class="form-control" id="editAnnTitle" type="text" style="width: 80%;"
              placeholder="Announcement Title">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <textarea id="editAnnContent" class="form-control m-3" rows="3" style="resize: none; width: 75%;"
            placeholder="Contents of Announcement"></textarea>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" onclick="editAnnSave()">Save changes</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
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
    <div class="modal fade" id="confirmDel" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Are you sure you want to delete this announcement?</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" onclick="delAnn()">Delete</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Go Back</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="viewCode" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <h3 class="modal-title" style="text-align: center; margin: 10%; font-size: 80px; letter-spacing: 15px;">
            ${classCode}</h3>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="changedue" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Set New Due Date: </h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="form-group" style="width: 80%; margin: auto; margin-top: 2%">
            <div class="input-group mb-2">
              <input type="date" class="form-control" class="datepicker" id="dueDate1" />
              <input type="time" class="form-control" id="dueTime1" />
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" onclick="confChangeDue()">Change Due Date</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Go Back</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="reschedule-modal" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Reschedule Quiz</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body" style="text-align: center;">
            <div id="scheduler-div">
              <h5>When For?</h4>
                <div class="form-group" style="width: 80%; margin: auto; margin-top: 2%">
                  <div class="input-group mb-2">
                    <input type="date" class="form-control" class="datepicker" id="dueDateRes" />
                    <input type="time" class="form-control" id="dueTimeRes" />
                  </div>
                </div>
                <h5>Who For?</h4>
                  <div class="btn-group btn-group-toggle" data-toggle="buttons">
                    <label class="btn btn-outline-primary active">
                      <input type="radio" name="options" checked onclick="selectBoxReSched('class')">
                      Class Only
                    </label>
                    <label class="btn btn-outline-primary">
                      <input type="radio" name="options" onclick="selectBoxReSched('everyone')"> Everyone
                    </label>
                  </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
            <button onclick="confReschedule()" type="button" class="btn btn-primary">Rescedule</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="schedule-modal" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Schedule Quiz</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body" style="text-align: center;">
            <div id="scheduler-div">
              <h5>When For?</h4>
                <div class="form-group" style="width: 80%; margin: auto; margin-top: 2%">
                  <div class="input-group mb-2">
                    <input type="date" class="form-control" class="datepicker" id="dueDateSched" />
                    <input type="time" class="form-control" id="dueTimeSched" />
                  </div>
                </div>
                <h5>Who For?</h4>
                  <div class="btn-group btn-group-toggle" data-toggle="buttons">
                    <label class="btn btn-outline-primary active">
                      <input type="radio" name="options" checked onclick="selectBoxSched('class')">
                      Class Only
                    </label>
                    <label class="btn btn-outline-primary">
                      <input type="radio" name="options" onclick="selectBoxSched('everyone')"> Everyone
                    </label>
                  </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
            <button onclick="confSchedule()" type="button" class="btn btn-primary">Rescedule</button>
          </div>
        </div>
      </div>
    </div>
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
    <div class="modal fade" id="StuAverageChart" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="stu-average-title">Your Chart</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <canvas id="myChart"></canvas>
          <div style="text-align: center; margin: auto;">
            <table class="table table-light">
              <tbody>
                <tr>
                  <td>Current Average:</td>
                  <td class="table-success" id="stu-average-avg">65%</td>
                </tr>
                <tr>
                  <td>Change In Average:</td>
                  <td class="table-danger" id="stu-average-percent">-45%</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="pieModal" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="pieChart-title">Your Chart</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <canvas id="pieChart"></canvas>

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
    <!-- end modals -->
    <div class="container">
      <div class="row test">
        <div class="col-sm left-col">
          <h3>${name}</h3>
          <div class="border-top my-3"></div>
          <div style="height: 491px;">
            <select id="select-tabs" class="custom-select custom-select-md mb-0">
              <option value="active">Active Quizzes</option>
              <option value="inactive">Previous Quizzes</option>
              <option value="sched">Private / Scheduled Quizzes</option>
              <option value="create">Create Quiz</option>
              <option value="ann">Announcements</option>
            </select>
            <hr>
            <div id="active-tab" class="overflow-auto" style="height: 90%;">
              <c:forEach items="${activeQuizzes}" var="quiz">
                <div class="list-group-item" style="width: 90%;  margin: auto;">
                  <div class="media" style="text-align: left;">
                    <img src="../../resources/images/def-quiz.png" width="50px" height="50px" class="mr-3">

                    <div class="media-body" style="margin: auto;">
                      <h5>${quiz.getName()}</h5>
                      <p class="m-0">Due: ${quiz.getDueDate()}</p>
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
                    <span style="margin: auto;"><i class="material-icons"
                        onclick="preloadPie('${quiz.getId()}')">pie_chart</i></span>
                    <span style="margin: auto;"><i class="material-icons"
                        onclick="openChangeDue('${quiz.getId()}')">assignment_returned</i></span> <!-- due -->
                    <a style="margin: auto;" onclick="toggleVis('${quiz.getId()}')"><i
                        class="material-icons">visibility_off</i></a>
                    <a style="margin: auto;" onclick="delQuiz('${quiz.getId()}')"><i
                        class="material-icons">delete_forever</i></a>
                  </div>

                </div>
              </c:forEach>
              ${noQuizzes}
            </div>
            <div id="inactive-tab" class="overflow-auto" style="display: none; height: 90%;">
              <c:forEach items="${inactiveQuizzes}" var="quiz">
                <div class="list-group-item" style="width: 90%;  margin: auto;">
                  <div class="media" style="text-align: left;">
                    <img src="../../resources/images/def-quiz.png" width="50px" height="50px" class="mr-3">

                    <div class="media-body" style="margin: auto;">
                      <h5>${quiz.getName()}</h5>
                      <p class="m-0">Due on: ${quiz.getDueDate()}</p>
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
                    <span style="margin: auto;"><i class="material-icons"
                        onclick="preloadPie('${quiz.getId()}')">pie_chart</i></span>
                    <a style="margin: auto;" onclick="toggleVis('${quiz.getId()}')"><i
                        class="material-icons">visibility_off</i></a>
                    <a style="margin: auto;" onclick="delQuiz('${quiz.getId()}')"><i
                      class="material-icons">delete_forever</i></a>
                  </div>

                </div>
              </c:forEach>
              ${noQuizzes}
            </div>

            <div id="create-tab" class="overflow-auto" style="display: none; height: 90%;">
              <form id="createQuiz" action="/create-new-quiz" method="POST">
                <input name="classId" value="${classId}" style="display: none;" />
                <div class="form-group">
                  <div class="input-group mb-2">
                    <input type="text" class="form-control" placeholder="Quiz Name" name="name" required />
                  </div>
                </div>
                <div class="form-group">
                  <div class="input-group mb-2">
                    <input type="number" class="form-control" placeholder="Number of Questions" name="questionNo"
                      required />
                    <div class="input-group-append">
                      <span class="input-group-text">
                        <i class="material-icons" data-toggle="tooltip" data-placement="top"
                          title="This can be changed later on">info</i>
                      </span>
                    </div>

                  </div>
                </div>
                <div class="form-group">
                  <div class="input-group mb-2">
                    <input type="date" class="form-control" class="datepicker" name="dueDate" required />
                    <input type="time" class="form-control" name="dueTime" required />
                  </div>
                </div>
                <div class="text-center">
                  <button type="submit" class="btn btn-primary btn-block">Create</button>
                </div>



              </form>
            </div>
            <div id="ann-tab" style="display: none;">
              <form id="addAnn">
                <div class="form-group">
                  <div class="input-group mb-0">
                    <input id="annTitle" type="text" class="form-control" placeholder="Announcement Title">
                  </div>
                </div>
                <div class="form-group">
                  <div class="input-group mb-0">
                    <textarea id="annContent" class="form-control" rows="2" style="resize: none; width: 100%;"
                      placeholder="Contents of Announcement"></textarea>
                  </div>
                </div>
                <div class="text-center">
                  <button class="btn btn-primary btn-block" type="button" onclick="makeAnnoucement()">Announce</button>
                </div>
              </form>
              <hr>
              <h5 style="text-align: left;">Past Announcements</h5>
              <div id="announcement-container" class="overflow-auto" style="height: 160px; ">
                <ul class="list-group">
                  <c:forEach items="${ann}" var="ann">
                    <li class="list-group-item list-group-item studentClassAnn">
                      <span style="float: right;">
                        <span id="expand">
                          <i class="material-icons" onclick="editAnn('${ann.getId()}')">edit</i>
                          <i class="material-icons" onclick="openAnn('${ann.getId()}')">open_in_new</i>
                          <i class="material-icons" onclick="confirm('${ann.getId()}')">delete</i>
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
            </div>
            <div id="sched-quizzes" class="overflow-auto" style="display: none;">
              <c:forEach items="${scheduledQuizzes}" var="quiz">
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
                    <span style="margin: auto;"><i class="material-icons"
                        onclick="openChangeDue('${quiz.getId()}')">assignment_returned</i></span> <!-- due -->
                    <span style="margin: auto;"><i onclick="toggleVis('${quiz.getId()}')"
                        class="material-icons">visibility</i>
                      <c:choose>
                        <c:when test="${quiz.getScheduled() == false}">
                          <a style="margin: auto;" onclick="openSchedule('${quiz.getId()}')" )"><i
                              class="material-icons">
                              add_alarm</i>
                          </a>
                        </c:when>
                        <c:otherwise>
                          <a style="margin: auto;" onclick="openReschedule('${quiz.getId()}')" )><i
                              class="material-icons">
                              access_alarms</i>
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </span>
                    <a style="margin: auto;" onclick="delQuiz('${quiz.getId()}')"><i
                      class="material-icons">delete_forever</i></a>
                  </div>

                </div>
              </c:forEach>
              ${noQuizzes}
            </div>
          </div>

          <h4 style="display: inline-block" class="text-muted">View Join Code </h4>
          <i data-toggle="modal" data-target="#viewCode" class="material-icons"
            style="margin-left: 2%; position: absolute; line-height: 35px;">open_in_new</i>
        </div>

        <div class="col-sm right-col">
          <h3>Students</h3>
          <div class="border-top my-3"></div>
          <div class="student-wrapper overflow-auto">
            <div id="added" class="alert alert-success" role="alert">
              Succesfully Added
            </div>
            <div id="remove" class="alert alert-success" role="alert">
              Succesfully Removed
            </div>
            <div id="stu-list">
              <c:forEach items="${classStudents}" var="classStudents">
                <div style="width: 50%;  margin: auto;">
                  <div class="media" style="text-align: left;">
                    <img src="../../resources/images/defuser.png" width="50px" height="50px" class="mr-3">
                    <div class="media-body">
                      <h5 class="mt-0">${classStudents.getUsername()}</h5>
                      ${classStudents.getFirstName()}
                    </div>
                    <span style="margin: auto;">
                      <i class="material-icons" data-toggle="tooltip" data-placement="top"
                        title="Analyse Student Results"
                        onclick="showStuAverageChart('${classStudents.getId()}')">assessment</i>
                    </span>
                    <a style="float: right; margin: auto;" href="/friends-page?message=${classStudents.getUsername()}"><i
                      class="material-icons">message</i></a>
                    <span style="margin: auto;">
                      <i class="material-icons" data-toggle="tooltip" data-placement="top"
                        title="Remove this student from the class"
                        onclick="remStudent('${classStudents.getId()}')">delete</i>
                    </span>
                  </div>
                </div>
              </c:forEach>
              ${noStu}
            </div>
            <div class="student-wrapper overflow-auto" id="search-result-container" style="display: none;">
              <c:forEach items="${searchResults}" var="classStudents">
                <div style="width: 50%;  margin: auto;">
                  <div class="media" style="text-align: left;">
                    <img src="../../resources/images/defuser.png" width="50px" height="50px" class="mr-3">
                    <div class="media-body">
                      <h5 class="mt-0">${classStudents.getUsername()}</h5>
                      ${classStudents.getFirstName()}
                    </div>
                    <span style="margin: auto;">
                      <i class="material-icons" data-toggle="tooltip" data-placement="top"
                        title="Analyse Student Results">assessment</i>
                    </span>
                    <span style="margin: auto;">
                      <i class="material-icons" data-toggle="tooltip" data-placement="top"
                        title="Remove this student from the class"
                        onclick="remStudent('${classStudents.getId()}')">delete</i>
                    </span>
                  </div>
                </div>
              </c:forEach>

            </div>
            <div id="searchLoad" class="spinner-border" style="width: 4rem; height: 4rem; display: none;" role="status">
              <span class="sr-only">Loading...</span>
            </div>
          </div>

          <div class="border-top my-3"></div>
          <div class="btn-group btn-group-justified" style="width:100%;">
            <button type="button" id="btn1" class="btn btn-outline-primary active toggle">Add</button>
            <button type="button" id="btn2" class="btn btn-outline-primary toggle">Filter</button>
          </div>
          <form id="addStudent" style="margin-top: 1.5%;">
            <div class="form-group">
              <div class="input-group mb-3">
                <input id="student-add" type="text" class="form-control" placeholder="Student's username">
                <div class="input-group-append">
                  <button class="btn btn-outline-primary" type="button" onclick="addStudent()">Add</button>
                </div>
              </div>
            </div>
          </form>
          <form id="searchStudent" style="margin-top: 1.5%; display: none;">
            <div class="form-group">
              <div class="input-group mb-3">
                <input id="student-search" type="text" class="form-control" placeholder="Student's username">
                <div class="input-group-append">
                  <button class="btn btn-outline-primary" type="button" onclick="refreshStudents()">Reset</button>
                </div>
                <div class="input-group-append">
                  <button class="btn btn-outline-primary" type="button" onclick="searchStudent()">Filter</button>
                </div>
              </div>
            </div>
          </form>
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
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
<script>
  var classId = parseInt("${classId}");
  var ownerId = parseInt("${teacherId}");
</script>
<script src="../../resources/js/teacher-page.js"></script>

</html>