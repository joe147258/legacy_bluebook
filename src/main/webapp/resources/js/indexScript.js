$(document).ready(function() {
  if(classAmount == 0){
    $("#class1").hide();
    $("#class2").hide();
    $("#welcome").html("Welcome, " + firstName);
    $("#publicQuizzes").show();
  } else if(classAmount == 1){
    $("#welcome").html("Welcome, " + firstName);
    $("#class2").hide();
    $("#publicQuizzes").show();
  } else {
    $("#class1").show();
    $("#class2").show();
  }

})


function joinClass(){
  var joinVal =  encodeURIComponent($("#joinClassInp").val());
  $.ajax({
    type: "POST",
    url: "/join-class/" + joinVal,
    success: function(data) {
      $("#class-holder").load(" #class-holder > *");
      if(data != "success") {
        alert(data);
      }
    }
})
}

function openAnn(id){
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

function confirmQuiz(id, name, ammount){
  $("#startQuizButton").attr("href", "/start-quiz?quizId=" + id);
  $("#modal-quiz-name").html(name);
  $("#modal-quiz-amm").html(ammount);
}
function deleteNotif(id){
  $.ajax({
    type: "GET",
    url: "/delete-notif?id=" + id,
    success: function (data) {
      $("#notifHolder").load(" #notifHolder > *");
    }
})
}
