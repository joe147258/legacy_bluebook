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
		<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 id="viewAnnTitle" class="modal-title">What would you like to do?</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" style="text-align: center;">
						<h3>Visibility Options</h3>
						<div id="whofor" class="btn-group btn-group-toggle" data-toggle="buttons">
							<label class="btn btn-outline-primary active">
								<input type="radio" name="options" id="option1" name="class"
									onclick="selectBox('class')"> Class Only
							</label>
							<label class="btn btn-outline-primary">
								<input type="radio" name="options" id="option2" name="everyone" checked
									onclick="selectBox('everyone')"> Everyone
							</label>
							<label class="btn btn-outline-primary">
								<input type="radio" name="options" id="option3" value="private"
									onclick="selectBox('private')"> Just You
							</label>
							<label class="btn btn-outline-primary">
								<input type="radio" name="options" id="option3" value="schedule"
									onclick="selectBox('schedule')"> Schedule
							</label>
						</div>
						<hr>
						<div id="scheduler-div" class="hiddenDiv">
							<h5>When For?</h4>
								<div class="form-group" style="width: 80%; margin: auto; margin-top: 2%">
									<div class="input-group mb-2">
										<input type="date" class="form-control" class="datepicker" id="dueDate"
											disabled />
										<input type="time" class="form-control" id="dueTime" disabled />
									</div>
								</div>
								<h5>Who For?</h4>
									<div class="btn-group btn-group-toggle" data-toggle="buttons">
										<label class="btn btn-outline-primary active">
											<input id="classonlySched" type="radio" name="options" checked
												onclick="selectBoxSched('class')" disabled>
											Class Only
										</label>
										<label class="btn btn-outline-primary">
											<input type="radio" id="everyoneSched" name="options"
												onclick="selectBoxSched('everyone')" disabled> Everyone
										</label>
									</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
						<button onclick="finish()" type="button" class="btn btn-primary">Publish</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="confirmModalPub" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 id="viewAnnTitle" class="modal-title">What would you like to do?</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" style="text-align: center;">
						<h3>Visibility Options</h3>
						<div class="btn-group btn-group-toggle" data-toggle="buttons">
							<label class="btn btn-outline-primary active">
								<input type="radio" name="options" id="option2" name="everyone"
									onclick="selectBox('everyone')" checked> Everyone
							</label>
							<label class="btn btn-outline-primary">
								<input type="radio" name="options" id="option3" value="private"
									onclick="selectBox('private')"> Just You
							</label>
							
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
						<button
							onclick="location.replace('/submit-pub-quiz/${quizId}?visibilityOption=' + visibilityOption);"
							type="button" class="btn btn-primary" >Publish</button>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row test">
				<div class="row justify-content-center overflow-auto" id="ques-wrapper">

				</div>

				<div class="btn-group btn-group-justified" style="width:100%; margin-top: 2%;" role="group"
					aria-label="Basic example">

					<c:choose>
						<c:when test="${isPublic == true}">
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#confirmModalPub" style="border-left: 1px solid lightgray;" id="confirmPublishQuiz2">Publish
								Quiz</button>
						</c:when>
						<c:when test="${toEdit == true}">
							<button type="button" class="btn btn-primary" onclick="window.location.href = '/'"
								style="border-left: 1px solid lightgray;" >Publish Quiz</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#confirmModal" style="border-left: 1px solid lightgray;" id="confirmPublishQuiz1">Publish
								Quiz</button>
						</c:otherwise>
					</c:choose>

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
	var visibilityOption = "everyone";
	var schedVisibility = "class"


	function selectBox(val) {
		visibilityOption = val;
		if (visibilityOption == "schedule") {
			$("#scheduler-div").removeClass("hiddenDiv");
			$("#dueDate").removeAttr("disabled");
			$("#dueTime").removeAttr("disabled");
			$("#classonlySched").removeAttr("disabled");
			$("#everyoneSched").removeAttr("disabled");
		} else if (!$("#scheduler-div").hasClass("hiddenDiv")) {
			$("#scheduler-div").addClass("hiddenDiv");
			$("#dueDate").attr("disabled", true);
			$("#dueTime").attr("disabled", true);
			$("#classonlySched").attr("disabled", true);
			$("#everyoneSched").attr("disabled", true);
		}
	}

	function selectBoxSched(val) {
		schedVisibility = val;
	}

	function finish() {
		if (visibilityOption == "schedule") {
			var dueDate = $("#dueDate").val();
			var dueTime = $("#dueTime").val();
			dueDate += " " + dueTime;
			dueDate += " " + dueTime;
			location.replace("/submit-quiz/${quizId}?dateString=" + dueDate + "&visibilityOption=" + visibilityOption +
				"&schedVis=" + schedVisibility);
		} else location.replace("/submit-quiz/${quizId}?visibilityOption=" + visibilityOption);



	}


	$(document).ready(function () {

		var qAmount = parseInt("${qAmount}");
		for (var i = 1; i <= qAmount; i++) { // so question numbers look normal to humans
			$("#ques-wrapper").append(
				'<div style="flex: 100%; max-width: 30%; display:inline-block; margin: 15px;"><form><h5 class="card-title" style="text-align: center;">Question ' +
				i +
				'</h5><div class="card" style="height: 410px; width: 321px; "><div class="card-body p-3"><div class="input-group mb-3"><div class="input-group-prepend"><label class="input-group-text" >Type</label></div><select class="custom-select qtype" id="' +
				i +
				'-select01"><option value = "multi" selected>Multiple Choice Question</option><option value ="boolean">True / False Question</option><option value ="input">Input Question</option></select></div><div id="' +
				i +
				'inner-form-wrapper"><div class="form-group"> <textarea style="max-height:80px; width: 100%; min-height:38px; height: 38px;" class="form-control" placeholder="Question" id="' +
				i +
				'qString"></textarea> </div><div class="form-group"><div class="input-group mb-2"><input type="text" class="form-control" placeholder="Correct Answer" id="' +
				i +
				'corAns"/></div></div><div class="form-group"><div class="input-group mb-2">                                        <input type="text" class="form-control" placeholder="Incorrect Answer" id="' +
				i +
				'inCor1"/>                                     </div>                                </div>                                <div class="form-group">                                    <div class="input-group mb-2">                                        <input type="text" class="form-control" placeholder="Incorrect Answer" id="' +
				i +
				'inCor2"/>                                     </div>                                </div>                                <div class="form-group">                                    <div class="input-group mb-2">                                        <input type="text" class="form-control" placeholder="Incorrect Answer" id="' +
				i +
				'inCor3"/>                                     </div>                                </div>                                </div>                                <div id="' +
				i +
				'inner-form-wrapper1" style="display: none;">                                    <div class="form-group"><textarea style="max-height:80px; width: 100%; min-height:38px; height: 38px;" class="form-control" placeholder="Question" id="' +
				i +
				'qString1"></textarea>                                    </div>                                    <div class="form-group">                                        <select class="custom-select" id="' +
				i +
				'booleanQues">                                            <option value="True">True</option>                                            <option value="False">False</option>                                          </select>                                    </div>                                                                    </div>                                <div id="' +
				i +
				'inner-form-wrapper2" style="display: none;">                                    <div class="form-group">                                        <textarea style="max-height:80px; width: 100%; min-height:38px; height: 38px;" class="form-control" placeholder="Question" id="' +
				i +
				'qString2"></textarea>                                    </div>                                    <div class="input-group mb-2">                                        <input type="text" class="form-control" placeholder="The Answer" id="' +
				i +
				'inputQues"/>                                     </div>                                    <div class="form-group">                                       </div>                                </div>                                  <div class="text-center">                                    <div class="btn-group btn-group-justified" style="width:100%; bottom: 0px;position: absolute;left: 0; height: 38px;">                                        <button type="button" id="create' +
				i + '" class="btn btn-primary toggle" onclick="saveQuestion(' + i +
				')">Save</button>                                        <button type="button" id="edit' + i +
				'" class="btn btn-outline-warning toggle" onclick="editQuestion(' + i +
				')">Edit</button>                                    </div>                                  </div>                                  </div>                            </div>                        </form>                    </div>'
			);
			$("#edit" + i).attr("disabled", true);
			$("#edit" + i).css("cursor", "not-allowed");

		}

		var toEdit = "${toEdit}";

		if (toEdit == true) {
			var qTypesArr = "${questionTypes}".split(",");
			var qStringArr = "${questionStrings}".split(",");
			var qAnswerArr = "${questionAnswers}".split(",");


			for (var i = 1; i <= qAmount; i++) {

				$("#" + i + "qString").val(qStringArr[i - 1]);
				$("#" + i + "qString1").val(qStringArr[i - 1]);
				$("#" + i + "qString2").val(qStringArr[i - 1]);
				$("#" + i + "corAns").val(qAnswerArr[i - 1]);


				if (qTypesArr[i - 1] == "InputQuestion") {
					$("#" + i + "inputQues").val(qAnswerArr[i - 1]);
					$("#" + i + "-select01").val("input");
					$("#" + i + "inner-form-wrapper").hide();
					$("#" + i + "inner-form-wrapper1").hide();
					$("#" + i + "inner-form-wrapper2").show();
				} else if (qTypesArr[i - 1] == "BoolQuestion") {
					$("#" + i + "booleanQues").val(qAnswerArr[i - 1]);
					$("#" + i + "-select01").val("boolean");
					$("#" + i + "inner-form-wrapper").hide();
					$("#" + i + "inner-form-wrapper1").show();
					$("#" + i + "inner-form-wrapper2").hide();
				} else if (qTypesArr[i - 1] == "MultiChoiceQuestion") {

				}

			}
			getIncorrect();
		} else {
			$("#confirmPublishQuiz1").attr("disabled", true);
			$("#confirmPublishQuiz2").attr("disabled", true);
			$("#confirmPublishQuiz2").css("cursor", "not-allowed");
			$("#confirmPublishQuiz1").css("cursor", "not-allowed");
		}


		$(".qtype").change(function (event) {
			var quesId = event.target.id;
			var id = quesId.substr(0, quesId.indexOf('-'));
			console.log(id);
			if ($("#" + quesId).val() === "multi") {
				$("#" + id + "inner-form-wrapper").show();
				$("#" + id + "inner-form-wrapper1").hide();
				$("#" + id + "inner-form-wrapper2").hide();
			} else if ($("#" + quesId).val() === "boolean") {
				$("#" + id + "inner-form-wrapper").hide();
				$("#" + id + "inner-form-wrapper1").show();
				$("#" + id + "inner-form-wrapper2").hide();
			} else if ($("#" + quesId).val() === "input") {
				$("#" + id + "inner-form-wrapper").hide();
				$("#" + id + "inner-form-wrapper1").hide();
				$("#" + id + "inner-form-wrapper2").show();
			}
		})
	})

	function allowPublish(){
		$("#confirmPublishQuiz1").removeAttr("disabled");
		$("#confirmPublishQuiz2").removeAttr("disabled");
		$("#confirmPublishQuiz2").css("cursor", "pointer");
		$("#confirmPublishQuiz1").css("cursor", "pointer");
	}

	var editSave = "${editSave}";
	var	savedQuestions = 0;
	function saveQuestion(qNo) {
		$(".btn").attr("disabled", true);
		$(".btn").css("cursor", "not-allowed");
		var quizId = parseInt("${quizId}");
		var type = $("#" + qNo + "-select01").val();
		var questionString = "";
		var URLtoSend = "/save-question/" + qNo + "/" + quizId;
		if (type === "multi") {
			questionString = encodeURIComponent($("#" + qNo + "qString").val());
			URLtoSend += "?type=" + type + "&questionString=" + questionString + "&correctAns=" + encodeURIComponent($("#" + qNo + "corAns")
				.val()) + "&incor1=" + encodeURIComponent($("#" + qNo + "inCor1").val()) + "&incor2=" + encodeURIComponent($("#" + qNo + "inCor2").val()) +
				"&incor3=" + encodeURIComponent($("#" + qNo + "inCor3").val());
		} else if (type === "boolean") {
			questionString = encodeURIComponent($("#" + qNo + "qString1").val());
			URLtoSend += "?type=" + type + "&questionString=" + questionString + "&correctAns=" + encodeURIComponent($("#" + qNo +
				"booleanQues").val());
		} else if (type === "input") {
			questionString = encodeURIComponent($("#" + qNo + "qString2").val());
			URLtoSend += "?type=" + type + "&questionString=" + questionString + "&correctAns=" + encodeURIComponent($("#" + qNo +
				"inputQues").val());
		}
		URLtoSend += "&edit=" + editSave;
		$("#create" + +qNo).html(
			'  <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> <span class="sr-only">Loading...</span>'
		);
		$("#create" + +qNo).attr("disabled", true);
		$("#create" + +qNo).removeClass("btn-primary");
		$("#create" + +qNo).addClass("btn-warning");

		if (URLtoSend != null) {
			$.ajax({
				type: "GET",
				url: URLtoSend,
				success: function (data) {
					$(".btn").attr("disabled", false);
					$(".btn").css("cursor", "pointer");
					console.log(data.ret);
					$("#create" + +qNo).html('Saved');
					$("#create" + +qNo).removeClass("btn-warning");
					$("#create" + +qNo).addClass("btn-success");
					$("#create" + +qNo).css("cursor", "not-allowed");
					$("#edit" + +qNo).attr("disabled", false);
					$("#edit" + +qNo).css("cursor", "pointer");
					$("#" + qNo + "qString").attr("readonly", true)
					$("#" + qNo + "corAns").attr("readonly", true)
					$("#" + qNo + "qString1").attr("readonly", true)
					$("#" + qNo + "booleanQues").attr("disabled", true)
					$("#" + qNo + "qString2").attr("readonly", true)
					$("#" + qNo + "inputQues").attr("readonly", true)

					$("#" + qNo + "inCor1").attr("readonly", true)
					$("#" + qNo + "inCor2").attr("readonly", true)
					$("#" + qNo + "inCor3").attr("readonly", true)
					$("#" + qNo + "-select01").attr("disabled", true);
					editSave = false;
					savedQuestions++;
					if(savedQuestions >= '${qAmount}') {
						allowPublish();
					}
				},
				error: function () {

				}
			})
		}

	}

	function editQuestion(qNo) {
		editSave = true;
		$("#" + qNo + "qString").attr("readonly", false)
		$("#" + qNo + "corAns").attr("readonly", false)
		$("#" + qNo + "qString1").attr("readonly", false)
		$("#" + qNo + "booleanQues").attr("disabled", false)
		$("#" + qNo + "qString2").attr("readonly", false)
		$("#" + qNo + "inputQues").attr("readonly", false)

		$("#" + qNo + "inCor1").attr("readonly", false)
		$("#" + qNo + "inCor2").attr("readonly", false)
		$("#" + qNo + "inCor3").attr("readonly", false)
		$("#" + qNo + "-select01").attr("disabled", false);
		$("#create" + +qNo).html('Save');
		$("#create" + +qNo).removeClass("btn-success");
		$("#create" + +qNo).addClass("btn-primary");
		$("#create" + +qNo).css("cursor", "pointer");
		$("#edit" + +qNo).attr("disabled", true);
		$("#edit" + +qNo).css("cursor", "not-allowed");

	}

	function getIncorrect() {
		var qAmount = encodeURIComponent(parseInt("${qAmount}"));
		for (var i = 0; i < qAmount; i++) {
			$.ajax({
				type: "GET",
				url: "/get-incorrect-answers?quizId=${quizId}&quesNo=" + i,
				success: function (data) {
					$("#" + (parseInt(data[0]) + 1) + "inCor1").val(data[1])
					$("#" + (parseInt(data[0]) + 1) + "inCor2").val(data[2])
					$("#" + (parseInt(data[0]) + 1) + "inCor3").val(data[3])

				},
				error: function () {

				}
			})
		}

	}
</script>

</html>