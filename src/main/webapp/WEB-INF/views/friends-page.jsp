<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<%
  String val=request.getParameter("request");
  String val1=request.getParameter("message");
  %>

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
        <div class="col-sm left-col">
          <div id="messages-box" style="width: 80%; margin: auto;">
            <h3>New Conversation</h3>
            <form id="message-form">
              <div class="input-group">
                <input type="text" id="message-rec" class="form-control" placeholder="Recipient's Username">
              </div>
              <div class="input-group" style="margin-top: 5px;">
                <textarea id="message-content" class="form-control" rows="3"
                  placeholder="Content of First Message"></textarea>
              </div>
              <button style="margin-top: 5px" class="btn btn-primary btn-block">Start New Conversation</button>
            </form>
          </div>
          <hr>
          <h3>Previous Conversations</h3>
          <div class="list-group overflow-auto" style="height: 300px;" id="convoBox">
            <c:forEach items="${convos}" var="convo">
              <div style="height: 110px;" class="list-group-item list-group-item-action">
                <span href="#" class="overflow-hidden" onclick="openConvo('${convo.getId()}')"
                  style="text-align: left;">
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1" style="max-width: 405px;">Conversation With
                      <c:forEach items="${convo.getMembers()}" var="mem" varStatus="loop">
                        ${mem.getUsername()}<c:if test="${!loop.last}">,</c:if>
                      </c:forEach>
                    </h5>
                    <small>${convo.getLatestMessage().getDateString()}</small>
                  </div>
                  <p class="mb-1">${convo.getLatestMessage().getContent()}</p>
                  <small style="float: left;">from ${convo.getLatestMessage().getSender().getUsername()}</small>
                  <i class="material-icons" style="position: absolute; right: 7.5%;">open_in_new</i>
                </span>
              </div>

              <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" id="convo${convo.getId()}">
                <div class="modal-dialog modal-lg">
                  <div class="modal-content" style="height: 500px; text-align: left; padding: 15px;">
                    <div id="chat-box${convo.getId()}" style="height: 400px;" class="overflow-auto chat">
                      <c:forEach items="${convo.getMessages()}" var="message" varStatus="loop">
                        <p class="m-0 font-weight-bold">
                          ${message.getSender().getUsername()}
                          <small>${message.getDateChat()}</small>
                        </p>

                        <p class="m-0">${message.getContent()}</p>
                        <br />
                      </c:forEach>
                    </div>
                    <div style="text-align: center;">
                      <div class="input-group"
                        style="position: absolute; bottom: 7px; width: 95%; left: 0px; right: 0px; margin: auto;">

                        <textarea class="form-control" style="max-height: 65px; min-height: 51px;" rows="3"
                          id="modalmsg${convo.getId()}" placeholder="Type your message here..."></textarea>
                        <div class="input-group-append">
                          <span class="input-group-text"><button class="btn btn-primary"
                              onclick="sendMessage('${convo.getId()}')">Send</button></span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

            </c:forEach>
          </div>
        </div>
        <div class="col-sm right-col">
          <div class="btn-group btn-group-toggle" data-toggle="buttons" style="width: 100%; margin-top: 5px;">
            <label class="btn btn-outline-primary">
              <input id="friends-tab" type="radio" onclick="toggleFriendTab('friends')" checked> Friends
            </label>
            <label class="btn btn-outline-primary">
              <input id="request-tab" type="radio" onclick="toggleFriendTab('requests')"> Requests (${numberOfRequests})
            </label>
          </div>
          <div id="friendsBox" style="height: 420px; margin-top: 10px;">
            <c:forEach items="${friends}" var="friend">
              <div style="width: 65%;  margin: auto;">
                <div class="media" style="text-align: left;">

                  <img src="../../resources/images/defuser.png" width="50px" height="50px" class="mr-3" alt="...">
                  <div class="media-body">
                    <h5 class="mt-0">${friend.getFirstName()}</h5>
                    ${friend.getUsername()}

                  </div>
                  <span style="float: right; margin: auto;"><i class="material-icons"
                      onclick="fillChatForm('${friend.getUsername()}')">message</i></span>
                  <span style="float: right; margin: auto;"><i class="material-icons"
                      onclick="removeFriend('${friend.getId()}')">delete</i></span>
                </div>
              </div>
            </c:forEach>
          </div>
          <div id="friendRequests" style="height: 420px; display: none; margin-top: 10px;">
            <c:forEach items="${friendReqs}" var="req">
              <div style="width: 65%;  margin: auto;">
                <div class="media" style="text-align: left;">

                  <img src="../../resources/images/defuser.png" width="50px" height="50px" class="mr-3" alt="...">
                  <div class="media-body">
                    New Friend Request From <a
                      href="/view-profile/${req.getSender().getUsername()}">${req.getSender().getUsername()}</a>

                  </div>
                  <div class="btn-group btn-group-sm" style="margin: auto;">
                    <button type="button" class="btn btn-primary"
                      onclick="acceptFriend('${req.getSender().getUsername()}')">Add</button>
                    <button type="button" class="btn btn-dark"
                      onclick="denyFriend('${req.getSender().getUsername()}')">&times;</button>
                  </div>


                </div>
              </div>
            </c:forEach>
          </div>
          <hr>
          <div class="input-group mb-3">
            <input id="addFriend" type="text" class="form-control" placeholder="Add a Friend By Username">
            <div class="input-group-append">
              <button class="btn btn-outline-primary" type="button" onclick="addFriend()">Add</button>
            </div>
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
  if ('<%=val%>' == 1) {

    toggleFriendTab("request");
    $("#friends-tab").removeAttr("checked");
    $("#request-tab").attr("checked", "checked");
    console.log("here");

  }

  if ('<%=val1%>' != "null") {
    fillChatForm('<%=val1%>');
  }

  function toggleFriendTab(val) {
    if (val == "friends") {
      $("#friendsBox").show();
      $("#friendRequests").hide();
    } else {
      $("#friendsBox").hide();
      $("#friendRequests").show();
    }

  }

  function openConvo(id) {
    $("#convo" + id).modal('toggle');

    setTimeout(function () {
      console.log("scroll time");
      var objDiv = document.getElementById("chat-box" + id);
      objDiv.scrollTop = objDiv.scrollHeight;
    }, 200);

  }

  $("#myModal").on("hidden.bs.modal", function () {
    // put your default event here
  });

  $("#message-form").submit(function (e) {
    e.preventDefault();
    $.ajax({
      url: '/new-conversation/' + encodeURIComponent($("#message-rec").val()) + "/" + encodeURIComponent($("#message-content").val()),
      type: 'post',
      success: function (data) {
        $("#convoBox").load(" #convoBox > * ")
      }
    })
  });

  function sendMessage(cid) {
    $.ajax({
      type: "GET",
      url: "/send-message/" + cid + "/" + encodeURIComponent($("#modalmsg" + cid).val()),
      success: function (data) {
        $("#chat-box" + cid).load(" #chat-box" + cid + " > *");
        setTimeout(function () {
          console.log("scroll time");
          var objDiv = document.getElementById("chat-box" + cid);
          objDiv.scrollTop = objDiv.scrollHeight;
        }, 200);
      },
      error: function () {

      }
    })
  }

  function acceptFriend(username) {
    $.ajax({
      type: "GET",
      url: "/accept-friend?sender=" + username,
      success: function (data) {
        $(".right-col").load(" .right-col > *");
        $("#friends-tab").removeAttr("checked");
        $("#friends-tab").attr("checked");
      },
      error: function () {

      }
    })
  }

  function denyFriend(username) {
    $.ajax({
      type: "GET",
      url: "/deny-friend?sender=" + username,
      success: function (data) {
        $(".right-col").load(" .right-col > *");
        $("#friends-tab").removeAttr("checked");
        $("#friends-tab").attr("checked");
      },
      error: function () {

      }
    })
  }

  function addFriend() {
    console.log("here");
    var username = $("#addFriend").val();
    $.ajax({
      type: "GET",
      url: "/send-request/" + username,
      success: function (data) {
        $("#addFriend").val("");

      },
      error: function () {

      }
    })
  }

  function removeFriend(id) {
    $.ajax({
      type: "GET",
      url: "/remove-friend/" + id,
      success: function (data) {
        $("#friendsBox").load(" #friendsBox > *");
        $("#friendRequests").load(" #friendRequests > *");

      },
      error: function () {

      }
    })
  }

  function fillChatForm(username) {
    $("#message-rec").val(username);
    $("#message-content").focus();
  }
</script>



</html>