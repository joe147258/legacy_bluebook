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
                  ${user.getUsername()}
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
    <div class="modal fade" id="allRes" tabindex="-1">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">All Results</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div style="margin: 10px; max-height: 620px;" class="overflow-auto">
            <c:forEach items="${results}" var="results">
              <div class="list-group-item" style="width: 90%;  margin: auto;">
                <div class="media" style="text-align: left;">
                  <img src="../../resources/images/def-quiz.png" width="80px" height="80px" class="mr-3">
                  <div class="media-body" style="margin: auto;">
                    <h5>${results.getQuizName()}</h5>
                    <p class="m-0">Completed on: ${results.getDateString()}</p>
                    <p class="m-0">You scored: ${results.getUserScore()} / ${results.getQuestionAmount()}
                    </p>
                  </div>
                  <c:if test="${results.getImportant() == false}">
                    <a style="margin: auto;" onclick="deleteResult('${results.getId()}')"><i class="material-icons">delete</i></a>
                  </c:if>
                </div>
              </div>
            </c:forEach>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="settingsModal" tabindex="-1">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Settings</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div style="margin: 10px; max-height: 620px;" class="overflow-auto">
            <div class="btn-group btn-block" role="group">
              <button class="btn btn-secondary" onclick="showKey()">Show Key</button>
              <button class="btn btn-secondary" onclick="hideKey()">Hide Key</button>
            </div>

          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="ChartModal" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="chart-title">Your Chart</h5>
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
                  <td class="table-success" id="averageClass">65%</td>
                </tr>
                <tr>
                  <td>Change In Average:</td>
                  <td class="table-danger" id="percentChange">-45%</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row test">

        <div style="display: inline-block; width: 50%;">
          <div>
            <table style="width: 100%;">
              <tr>
                <td style="width: 50%;">
                  <img class="rounded float-left" width="60" height="60" src="../../resources/images/defuser.png"
                    alt="Generic placeholder image" />
                  <h3 style="margin: 0px; margin-left: 65px;">${user.getFirstName()} ${user.getLastName()}</h3>
                  <span style="margin-left: 10px"> ${user.getUsername()}</span><br />
                </td>

              </tr>
            </table>
          </div>
          <h3 style="text-align: center;">Recent Results</h3>
          <hr class="m-4">
          <div class="overflow-auto" style="height: 325px; " id="results-container">

            <ul class="list-group">
              <c:forEach items="${recentResults}" var="results">
                <div class="list-group-item" style="width: 90%;  margin: auto;">
                  <div class="media" style="text-align: left;">
                    <img src="../../resources/images/def-quiz.png" width="80px" height="80px" class="mr-3">
                    <div class="media-body" style="margin: auto;">
                      <h5>${results.getQuizName()}</h5>
                      <p class="m-0">Completed on: ${results.getDateString()}</p>
                      <p class="m-0">You scored: ${results.getUserScore()} / ${results.getQuestionAmount()}
                      </p>
                    </div>
                    <a style="margin: auto;" href=""><i
                      class="material-icons">feedback</i></a>
                    <c:if test="${results.getImportant() == false}">
                      <a style="margin: auto;" onclick="deleteResult('${results.getId()}')"><i class="material-icons">delete</i></a>
                    </c:if>
                  </div>
                </div>
              </c:forEach>
            </ul>

          </div>
          <button class="btn btn-primary btn-block" style="width: 70%; margin: auto; margin-top: 1%;"
            data-toggle="modal" data-target="#allRes">View All Results</button>
        </div>

        <div style="display: inline-block; width: 50%; text-align: center; position: relative;">
          <h3 style="margin-right: 1%; margin-right: 1%;">Class Standings </h3>
          <hr>
          <div id="classKey" style="display: block; text-align: center; width: 80%; margin: auto; position: relative;">
            <i class="material-icons" style="position: absolute; left: 10px; top: 20%; cursor:help;"
              data-toggle="tooltip" data-placement="top"
              title="Key for how you're doing in class. Hover over each box for more info">info_outlined</i>
            <div class="alert alert-success" style="width: 25%; display: inline-block; cursor:help;"
              data-toggle="tooltip" data-placement="top" title="Average Grade is >= 70%">Excellent</div>
            <div class="alert alert-warning" style="width: 25%; display: inline-block; cursor:help;"
              data-toggle="tooltip" data-placement="top" title="Average Grade is >= 50%">Satisfactory</div>
            <div class="alert alert-danger" style="width: 25%; display: inline-block; cursor:help;"
              data-toggle="tooltip" data-placement="top" title="Average Grade is < 50%">Inadequate</div>
            <i class="material-icons" style="position: absolute; right: 10px; top: 20%;" onclick="hideKey()">close</i>
          </div>
          <ul class="list-group" style="width: 80%; margin: auto;">
            <c:forEach items="${user.getClassAverages()}" var="c">
              <c:choose>
                <c:when test="${c.getAverage() >= 70.0}">
                  <li class="list-group-item list-group-item-success" role="alert" data-toggle="tooltip"
                    data-placement="top" title="Average Grade: ${Math.round(c.getAverage())}%">
                    <span style="cursor: default;">${c.getC().getName()}</span><i class="material-icons"
                      style="float: right;" onclick='showChart("${c.getId()}")'>insert_chart</i>
                  </li>
                </c:when>
                <c:when test="${c.getAverage() >= 50.0}">
                  <li class="list-group-item list-group-item-warning" role="alert" data-toggle="tooltip"
                    data-placement="top" title="Average Grade: ${Math.round(c.getAverage())}%">
                    <span style="cursor: default;">${c.getC().getName()}</span><i class="material-icons"
                      style="float: right;" onclick='showChart("${c.getId()}")'>insert_chart</i>
                  </li>
                </c:when>
                <c:otherwise>
                  <li class="list-group-item list-group-item-danger" role="alert" data-toggle="tooltip"
                    data-placement="top" title="Average Grade: ${Math.round(c.getAverage())}%">
                    <span style="cursor: default;">${c.getC().getName()}</span><i class="material-icons"
                      style="float: right;" onclick='showChart("${c.getId()}")'>insert_chart</i>
                  </li>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </ul>

          <a data-toggle="modal" data-target="#settingsModal">
            <i class="material-icons text-muted" style="right: 10px; bottom: 10px; position: absolute;"
              data-toggle="tooltip" data-placement="top"
              title="This allows you to show / edit the Colour Key">settings</i>
          </a>
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
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
<script src="../../resources/js/my-profile.js"></script>



</html>