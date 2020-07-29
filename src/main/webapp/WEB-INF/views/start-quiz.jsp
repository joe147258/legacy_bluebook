<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Create a Quiz</title>
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
    <div class="modal fade" id="dontLeave" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Warning</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Now you have started please finish the quiz or your score will be recorded as 0. <br />
                    You can ignore this message if its a public quiz or you're revising.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="lateBox" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Past Due Date</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    This test should be for revision. Your score will be recorded but not shown to the teacher.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="completed" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Quiz Already Submitted</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    You previously submitted this quiz on ${timeCompleted}.
                    You may still continue for revision but your score will not be updated.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
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
                <div class="collapse navbar-collapse" id="navbarSupportedContent"
                    style="color: white; margin-left: 15%;">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="/">Home</a>
                        </li>
                        <li class="nav-item active dropdown ">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Quizzes
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" onclick="location.href='/create-quiz';"
                                    style="cursor: pointer;">Create a
                                    quiz!</a>
                                <a class="dropdown-item" onclick="location.href='/all-quizzes';"
                                    style="cursor: pointer;">View All
                                    Quizzes</a>
                            </div>
                        </li>
                        <li class="nav-item active dropdown ">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Classes
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" onclick="location.href='/create-class';"
                                    style="cursor: pointer;">Create a
                                    class</a>
                                <a class="dropdown-item" onclick="location.href='/view-classes';"
                                    style="cursor: pointer;">View your
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
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    ${username}
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <a class="dropdown-item" onclick="location.href='/view-profile?id=${userId}'"
                                        style="cursor: pointer;">Your profile</a>
                                    <a class="dropdown-item" onclick="location.href='/view-classes';"
                                        style="cursor: pointer;">View your
                                        Quizzes</a>
                                    <a class="dropdown-item" onclick="location.href='/logout/';"
                                        style="cursor: pointer;">Log out</a>
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
                <div class="container quiz-wrapper">

                    <h5 style="text-align: center;">${quizName}</h5>
                    <div class="border-top my-3"></div>
                    <div class="spinner-border big-spinner" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                    <div id="inner-wrapper">
                        <div style="text-align: center; height: 70px;">
                            <h3 id="ques"></h3>
                            <h6 id="qNum"></h6>
                        </div>


                        <div id="multi-wrapper">
                            <div class="card text-dark bg-light mb-3 multiAns">
                                <div class="card-body">
                                    <p class="card-text " id="box1">Some quick example text to build on the card title
                                        and make up the bulk of the card's content.</p>
                                </div>
                            </div>
                            <div class="card text-dark bg-light mb-3 multiAns">
                                <div class="card-body">
                                    <p class="card-text" id="box2">Some quick example text to build on the card title
                                        and make up the bulk of the card's content.</p>
                                </div>
                            </div>
                            <div class="card text-dark bg-light mb-3 multiAns">
                                <div class="card-body">
                                    <p class="card-text" id="box3">Some quick example text to build on the card title
                                        and make up the bulk of the card's content.</p>
                                </div>
                            </div>
                            <div class="card text-dark bg-light mb-3 multiAns">
                                <div class="card-body">
                                    <p class="card-text" id="box4">Some quick example text to build on the card title
                                        and make up the bulk of the card's content.</p>
                                </div>
                            </div>

                            <div class="input-group mb-3" id="inputAns" style="display: none; padding-top: 15%;">
                                <input type="text" class="form-control" id="inputText" placeholder="Input Answer">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" type="button"
                                        id="inputSubmit">Submit</button>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="border-top my-3"></div>
                    <div style="margin: auto; text-align: center;">
                        <div class="btn-group btn-group-justified"
                            style="width:60%; text-align: center; margin-top: 2%; " role="group"
                            aria-label="Basic example">
                            <button id="prev" type="button" class="btn btn-primary"
                                style="border-right: 1px solid lightgray; width: 50%;" onclick="prevQuestion()">Previous
                                Question</button>

                            <button id="next" type="button" class="btn btn-primary"
                                style="border-left: 1px solid lightgray; width: 50%;" onclick="nextQuestion()">Next
                                Question</button>
                        </div>
                        <div style="width: 60%; margin: auto; margin-top: 1%; visibility: hidden;">
                            <button id="submit" type="button" class="btn btn-primary btn-block"
                                onclick="location.href='/submit-quiz?quizId=' + '${quizId}';">Submit Quiz</button>
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
    integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
    integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous">
</script>
<script>
    $(document).ready(function () {
        $("#qNum").html(questionNo + 1 + " / " + "${quizSize}")
        $("#prev").attr("disabled", true);
        $("#prev").css("cursor", "not-allowed");
        getQuestion(0);
        if (questionNo + 1 == "${quizSize}") {
            $("#next").attr("disabled", true);
            $("#next").css("cursor", "not-allowed");
            $("#submit").css("visibility", "visible");
        }
        var late = "${late}";
        if (late == 'true') {
            
            $('#lateBox').modal('toggle');
        }

        var completed = "${completed}";
        if (completed == 'true' ) {
            $('#completed').modal('toggle');

        } else {
            $("#dontLeave").modal('toggle');
            $(window).bind('beforeunload', function () {
                return 'If you leave you will be given a score of 0. You cannot resubmit.';
            });
        }
    })
    var clickAllowed = true;
    var questionNo = 0;

    function getQuestion(qNo) {
        var quizId = parseInt("${quizId}");
        $("#inner-wrapper").css("visibility", "hidden");
        $(".big-spinner").show();
        $.ajax({
            type: "GET",
            url: "/getQues?quizId=" + quizId + "&qNo=" + qNo,
            error: function () {
                alert("an error occured");
            },
            success: function (data) {
                $(".big-spinner").hide();
                $("#inner-wrapper").css("visibility", "visible");
                var ammount = data.ammount;
                $("#ques").html(data.question);
                if (data.type == "multi") {
                    $("#inputAns").hide();
                    var answers;
                    if (ammount == 2) answers = [data.box0, data.box1];
                    if (ammount == 3) answers = [data.box0, data.box1, data.box2];
                    if (ammount == 4) answers = [data.box0, data.box1, data.box2, data.box3];
                    console.log(answers);
                    if (data.answered == false) answers = shuffle(answers);
                    $("#box1").parent().parent().show();
                    $("#box2").parent().parent().show();
                    $("#box3").parent().parent().show();
                    $("#box4").parent().parent().show();
                    $("#box1").html(answers[0]);
                    $("#box2").html(answers[1]);
                    $("#box3").html(answers[2]);
                    $("#box4").html(answers[3]);
                    if (ammount == 2) {
                        $("#box3").parent().parent().hide();
                        $("#box4").parent().parent().hide();
                    }
                    if (ammount == 3) {
                        $("#box4").parent().parent().hide();
                    }

                } else if (data.type == "boolean") {
                    $("#box1").parent().parent().show();
                    $("#box2").parent().parent().show();
                    $("#box1").html("True");
                    $("#box2").html("False");
                    $("#box3").parent().parent().hide();
                    $("#box4").parent().parent().hide();
                    $("#inputAns").hide();
                } else if (data.type == "input") {

                    $("#inputAns").show();
                    $("#box1").parent().parent().hide();
                    $("#box2").parent().parent().hide();
                    $("#box3").parent().parent().hide();
                    $("#box4").parent().parent().hide();

                } else {
                    alert("error, wrong info from server");
                }

                if (data.answered == true) {
                    console.log(data.answered);
                    $(".multiAns").css("cursor", "not-allowed");
                    clickAllowed = false;
                    if (data.correct == true) {
                        if (data.type != "input") {
                            for (var i = 1; i < 5; i++) {
                                if ($("#box" + i).html() == data.correctAns) {
                                    $("#box" + i).parent().parent().removeClass("bg-light");
                                    $("#box" + i).parent().parent().removeClass("bg-danger");
                                    $("#box" + i).parent().parent().addClass("bg-success");
                                    $("#box" + i).append(
                                        '<div class="alert alert-success" style="position: absolute; margin: auto; left: 0; right: 0; width: 60%; margin-top: 5%;" role="alert">Correct</div>'
                                        );
                                }
                            }
                        } else {
                            //logic for input & correct ans
                            $("#inputText").attr("readonly", false);
                            $("#inputText").val(data.userAns);
                            $("#inputText").attr("readonly", true);
                            $("#inputSubmit").attr("disabled", true);
                            $("#inputSubmit").removeClass("bg-light");
                            $("#inputSubmit").removeClass("btn-outline-secondary");
                            $("#inputSubmit").removeClass("bg-danger");
                            $("#inputSubmit").addClass("bg-success");
                            $("#inputSubmit").css("cursor", "not-allowed");
                            $("#inputSubmit").html("Correct");
                        }
                    } else {
                        if (data.type != "input") {
                            for (var i = 1; i < 5; i++) {
                                if ($("#box" + i).html() == data.userAns) {
                                    $("#box" + i).parent().parent().removeClass("bg-light");
                                    $("#box" + i).parent().parent().removeClass("bg-success");
                                    $("#box" + i).parent().parent().addClass("bg-danger");
                                    $("#box" + i).append(
                                        '<div class="alert alert-danger" style="position: absolute; margin: auto; left: 0; right: 0; width: 60%; margin-top: 5%;" role="alert">Incorrect</div>'
                                        );
                                }
                            }
                        } else {
                            $("#inputText").attr("readonly", false);
                            $("#inputText").val(data.userAns);
                            $("#inputText").attr("readonly", true);
                            $("#inputSubmit").attr("disabled", true);
                            $("#inputSubmit").removeClass("btn-outline-secondary");
                            $("#inputSubmit").removeClass("bg-light");
                            $("#inputSubmit").removeClass("bg-success");
                            $("#inputSubmit").addClass("bg-danger");
                            $("#inputSubmit").html("Incorrect");
                            $("#inputSubmit").css("cursor", "not-allowed");
                        }
                    }
                } else {
                    $(".multiAns").css("cursor", "pointer");
                    clickAllowed = true;
                    $("#inputText").val("");
                    $("#inputText").attr("readonly", false);
                    $("#inputSubmit").attr("disabled", false);
                    $("#inputSubmit").removeClass("bg-success");
                    $("#inputSubmit").removeClass("bg-danger");
                    $("#inputSubmit").addClass("btn-outline-secondary");
                    $("#inputSubmit").addClass("bg-light");
                    $("#inputSubmit").css("cursor", "pointer");
                    $("#inputSubmit").html("Submit");


                }
            }

        })
    }

    function nextQuestion() {
        $(".multiAns").removeClass("bg-success");
        $(".multiAns").addClass("bg-light");
        questionNo++;
        $("#qNum").html(questionNo + 1 + " / " + "${quizSize}")
        getQuestion(questionNo);
        if (questionNo + 1 == "${quizSize}") {
            $("#next").attr("disabled", true);
            $("#next").css("cursor", "not-allowed");
            $("#submit").css("visibility", "visible");
        } else {
            $("#next").attr("disabled", false);
            $("#next").css("cursor", "pointer");
            $("#submit").css("visibility", "hidden");
        }
        if (questionNo == 0) {
            $("#prev").attr("disabled", true);
            $("#prev").css("cursor", "not-allowed");
        } else {
            $("#prev").attr("disabled", false);
            $("#prev").css("cursor", "pointer");

        }


    }

    function prevQuestion() {
        $(".multiAns").removeClass("bg-success");
        $(".multiAns").addClass("bg-light");
        questionNo--;
        $("#qNum").html(questionNo + 1 + " / " + "${quizSize}")
        getQuestion(questionNo);
        if (questionNo + 1 == "${quizSize}") {
            $("#next").attr("disabled", true);
            $("#next").css("cursor", "not-allowed");
            $("#submit").css("visibility", "visible");
        } else {
            $("#next").attr("disabled", false);
            $("#next").css("cursor", "pointer");
            $("#submit").css("visibility", "hidden");
        }
        if (questionNo == 0) {
            $("#prev").attr("disabled", true);
            $("#prev").css("cursor", "not-allowed");
        } else {
            $("#prev").attr("disabled", false);
            $("#prev").css("cursor", "pointer");
        }
    }
    $("#inputSubmit").click(function () {
        userAnswer = $("#inputText").val(); // allow multiple input questions on a page
        var quizId = parseInt("${quizId}");
        $("#inputSubmit").removeClass("btn-outline-secondary");
        $("#inputSubmit").removeClass("bg-light");
        $("#inputSubmit").html(
            '  <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> <span class="sr-only">Loading...</span>'
            );
        $("#inputSubmit").attr("disabled", true);
        $("#inputSubmit").removeClass("btn-primary");
        $("#inputSubmit").addClass("btn-warning");
        $("#inputText").attr("readonly", true);
        $.ajax({
            type: "POST",
            url: "/check-answer?ans=" + userAnswer + "&qNo=" + questionNo + "&quizId=" + quizId,
            success: function (data) {
                if (data.ret == true) {
                    $("#inputSubmit").removeClass("btn-warning");
                    $("#inputSubmit").removeClass("bg-light");
                    $("#inputSubmit").addClass("btn-success");
                    $("#inputSubmit").html('Correct!');
                    $("#inputSubmit").css("cursor", "not-allowed");
                } else {
                    $("#inputSubmit").removeClass("btn-warning");
                    $("#inputSubmit").removeClass("bg-light");
                    $("#inputSubmit").addClass("btn-danger");
                    $("#inputSubmit").html('Incorrect!');
                    $("#inputSubmit").css("cursor", "not-allowed");
                }
            }
        })

    })

    $(".multiAns").click(function (event) {
        if (clickAllowed == true) {
            var userAnswer = $("#" + event.target.id).html();
            $(".multiAns").css("cursor", "not-allowed");
            var id = event.target.id;
            var quizId = parseInt("${quizId}");
            clickAllowed = false;
            $.ajax({
                type: "POST",
                url: "/check-answer?ans=" + userAnswer + "&qNo=" + questionNo + "&quizId=" + quizId,
                success: function (data) {
                    $("#" + id).parent().parent().removeClass("bg-light");
                    if (data.ret == true) {
                        $("#" + id).parent().parent().addClass("bg-success");
                        $("#" + id).append(
                            '<div class="alert alert-success" style="position: absolute; margin: auto; left: 0; right: 0; width: 60%; margin-top: 5%;" role="alert">Correct</div>'
                            );
                    } else {
                        $("#" + id).parent().parent().addClass("bg-danger");
                        $("#" + id).append(
                            '<div class="alert alert-danger" style="position: absolute; margin: auto; left: 0; right: 0; width: 60%; margin-top: 5%;" role="alert">Incorrect</div>'
                            );
                    }
                }
            })
        } else {
            alert("Question has been answered");
        }


    });





    function shuffle(a) {
        var j, x, i;
        for (i = a.length - 1; i > 0; i--) {
            j = Math.floor(Math.random() * (i + 1));
            x = a[i];
            a[i] = a[j];
            a[j] = x;
        }
        return a;
    }
</script>

</html>