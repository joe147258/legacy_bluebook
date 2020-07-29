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
                <div class="container">
                    <div>
                        Type of Question:<br />
                        <select id="selectBox" name="type" class="form-control customSelect">
                            <option value="multi" label="Multiple Choice Question" />
                            <option value="bool" label="True / False Question" />
                            <option value="input" label="Input Question" />
                        </select>
                    </div><br />
                    <button class="btn btn-primary" type="button" disabled style="display: none; margin-bottom: 2%;"
                        id="loaderIcon">
                        <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>

                    </button>
                    <form id="multiChoiceForm">
                        <div>
                            Question:<br />
                            <input type="text" placeholder="Enter the question" class="newQuizIn" id="QuestionString" />
                        </div>
                        <div>
                            Correct Answer:
                            <input type="text" placeholder="Enter the correct answer" class="newQuizIn"
                                id="correctAns" />
                        </div>
                        <div>
                            False Answer:
                            <input type="text" placeholder="Enter a dummy answer" class="newQuizIn" id="falseAns0" />
                        </div>
                        <div>
                            False Answer:
                            <input type="text" placeholder="Enter a dummy answer" class="newQuizIn" id="falseAns1" />
                        </div>
                        <div>
                            False Answer:
                            <input type="text" placeholder="Enter a dummy answer" class="newQuizIn" id="falseAns2" />
                        </div>
                        <input path="type" value="multi" class="inputType" />
                    </form>
                    <form id="boolForm" style="display: none;">

                        <div class="newQuizNameHolder">
                            Question:<br />
                            <input type="text" id="boolQuesString" placeholder="Enter the question" class="newQuizIn" />
                        </div>


                        <div class="newQuizNameHolder">
                            Correct Answer:<br />
                            <select id="boolAns" class="form-control customSelect">
                                <option value="True" label="True" />
                                <option value="False" label="False" />
                            </select>
                        </div>

                        <input path="type" display="none" value="bool" class="inputType" />
                        <br />
                    </form>
                    <form id="inputForm" style="display: none;">

                        <div class="newQuizNameHolder">
                            Question:<br />
                            <input type="text" id="inputQuesString" placeholder="Enter the question"
                                class="newQuizIn" />
                        </div>


                        <div class="newQuizNameHolder">
                            Type the correct answer:<br />
                            <input type="text" id="inputAns" placeholder="Type the correct answer here"
                                class="newQuizIn" />
                        </div>

                        <input path="type" display="none" value="input" class="inputType" />
                        <br />
                    </form> <br />
                    <button class="btn btn-primary ajaxBack">Back</button>
                    <button class="btn btn-primary ajaxNext" id="next">Next</button>
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
    function clearInp() {
        $(".newQuizIn").val("");
        console.log("Input cleared!");
    }

    var type = "multi";
    $(".customSelect").change(function () {
        type = $("#selectBox option:selected").val();
        if (type === "bool") {
            $("#boolForm").show();
            $("#multiChoiceForm").hide();
            $("#inputForm").hide();
            type = "bool";
        }
        if (type === "multi") {
            $("#multiChoiceForm").show();
            $("#boolForm").hide();
            $("#inputForm").hide();
            type = "multi";
        }
        if (type === "input") {
            $("#inputForm").show();
            $("#multiChoiceForm").hide();
            $("#boolForm").hide();
            type = "input";
        }

    })

    $('.ajaxNext').click(function () {
        $("#boolForm").hide();
        $("#multiChoiceForm").hide();
        $("#inputForm").hide();
        $("#loaderIcon").show();
        var url = "/addQues?";
        var question;
        var correctAns;
        var falseAns0;
        var falseAns1;
        var falseAns2;
        var select;
        $("#next").attr('disabled', true);
        $("#next").css('cursor', 'wait');
        if (type === "multi") {

            question = encodeURIComponent($("#QuestionString").val());
            correctAns = encodeURIComponent($("#correctAns").val());
            falseAns0 = encodeURIComponent($("#falseAns0").val());
            falseAns1 = encodeURIComponent($("#falseAns1").val()); 
            falseAns2 = encodeURIComponent($("#falseAns2").val());
            select = $("#selectBox").val();
            url = url + "question=" + question + "&correctAns=" + correctAns + "&falseAns0=" +
                falseAns0 + "&falseAns1=" + falseAns1 + "&falseAns2=" + falseAns2 + "&select=" + select;

        } else if (type === "bool") {

            question = encodeURIComponent($("#boolQuesString").val());
            correctAns = encodeURIComponent($("#boolAns").val());
            select = $("#selectBox").val();
            url = url + "question=" + question + "&correctAns=" + correctAns + "&falseAns0=" +
                falseAns0 + "&falseAns1=" + falseAns1 + "&falseAns2=" + falseAns2 + "&select=" + select;

        } else if (type === "input") {

            question = encodeURIComponent($("#inputQuesString").val());
            correctAns = encodeURIComponent($("#inputAns").val());
            select = $("#selectBox").val();
            url = url + "question=" + question + "&correctAns=" + correctAns + "&falseAns0=" +
                falseAns0 + "&falseAns1=" + falseAns1 + "&falseAns2=" + falseAns2 + "&select=" + select;

        }

        $.ajax({
            type: "POST",
            url: url,
            success: function (data) {
                $("#next").attr('disabled', false);
                $("#next").css('cursor', 'auto');
                $("#loaderIcon").hide();
                if (type === "bool") {
                    $("#boolForm").show();
                    $("#multiChoiceForm").hide();
                    $("#inputForm").hide();
                    type = "bool";
                }
                if (type === "multi") {
                    $("#multiChoiceForm").show();
                    $("#boolForm").hide();
                    $("#inputForm").hide();
                    type = "multi";
                }
                if (type === "input") {
                    $("#inputForm").show();
                    $("#multiChoiceForm").hide();
                    $("#boolForm").hide();
                    type = "input";
                }
                clearInp();
                if (data.ret == true) {
                    window.location.replace("/");
                }

            }
        })
    })

    $('.ajaxBack').click(function () {
        $.ajax({
            type: "POST",
            url: "/backQues",
            success: function (data) {
                if (data.type == "mcq") {
                    $("#multiChoiceForm").show();
                    $("#boolForm").hide();
                    $("#inputForm").hide();
                    $("#QuestionString").val(data.quesString);
                    $("#correctAns").val(data.correctAns);
                    $("#falseAns0").val(data.ans0);
                    $("#falseAns1").val(data.ans1);
                    $("#falseAns2").val(data.ans2);
                    type = "multi";
                } else if (data.type == "bq") {
                    $("#boolQuesString").val(data.quesString);
                    $("#boolForm").show();
                    $("#multiChoiceForm").hide();
                    $("#inputForm").hide();
                    type = "bool";
                } else if (data.type == "iq") {
                    $("#inputForm").show();
                    $("#multiChoiceForm").hide();
                    $("#boolForm").hide();
                    $("#inputQuesString").val(data.quesString);
                    type = "input";
                } else {
                    alert(data.type);
                    console.log("HERE!!");
                }

            }
        })
    })
</script>

</html>