$(".btn").click(function () {
    if (this.id == "btn1") {
        $(btn1).addClass("active");
        $(btn2).removeClass("active");
        $("#addStudent").show();
        $("#searchStudent").hide();
    } else if (this.id == "btn2") {
        $(btn1).removeClass("active");
        $(btn2).addClass("active");
        $("#addStudent").hide();
        $("#searchStudent").show();
    } else if (this.id == "btn3") {
        $(btn3).addClass("active");
        $(btn4).removeClass("active");
        $("#left-col-1").show();
        $("#left-col-2").hide();
    } else if (this.id == "btn4") {
        $(btn3).removeClass("active");
        $(btn4).addClass("active");
        $("#left-col-1").hide();
        $("#left-col-2").show();
    }
})




//some form options that allow users to press enter
var latestForm;
$("#addStudent :input").keyup(function () {
    latestForm = "add";
});

$("#addAnn :input").keyup(function () {
    latestForm = "ann";
});

$("#searchStudent :input").keyup(function () {
    latestForm = "search";
});
$("#createQuiz :input").keyup(function () {
    latestForm = "create";
});





$("#select-tabs").change(function () {

    if ($("#select-tabs").val() == "active") {
        $("#active-tab").show();
        $("#inactive-tab").hide();
        $("#create-tab").hide();
        $("#ann-tab").hide();
        $("#sched-quizzes").hide();
    } else if ($("#select-tabs").val() == "inactive") {
        $("#active-tab").hide();
        $("#inactive-tab").show();
        $("#create-tab").hide();
        $("#ann-tab").hide();
        $("#sched-quizzes").hide();
    } else if ($("#select-tabs").val() == "create") {
        $("#active-tab").hide();
        $("#inactive-tab").hide();
        $("#create-tab").show();
        $("#ann-tab").hide();
        $("#sched-quizzes").hide();
    } else if ($("#select-tabs").val() == "ann") {
        $("#active-tab").hide();
        $("#inactive-tab").hide();
        $("#create-tab").hide();
        $("#ann-tab").show();
        $("#sched-quizzes").hide();
    } else if ($("#select-tabs").val() == "sched") {
        $("#active-tab").hide();
        $("#inactive-tab").hide();
        $("#create-tab").hide();
        $("#ann-tab").hide();
        $("#sched-quizzes").show();
    }

});

//changes how enter works with forms
$(document).ready(function () {
    $(".alert").css("display", "none");
    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            if (latestForm == "add") {
                addStudent();
            } else if (latestForm == "ann") {
                makeAnnoucement();
            } else if (latestForm == "search") {
                searchStudent();
            } else if (latestForm == "create") {
                createQuiz();
            }
            return false;
        }
    });

});

function refreshStudents() {
    $("#search-result-container").hide();
    $("#stu-list").show();
}

function searchStudent() {
    var searchValue = encodeURIComponent($("#student-search").val());
    $("#stu-list").hide();
    $("#searchLoad").show();
    $("#search-result-container").hide();
    $.ajax({
        type: "POST",
        url: "/search-students?value=" + searchValue + "&classId=" + classId,
        success: function (data) {
            $("#search-result-container").load(" #search-result-container > *")
            $("#search-result-container").show();
            $("#stu-list").hide();
            $("#searchLoad").hide();
        }
    })
}





function addStudent() {
    var username = encodeURIComponent($("#student-add").val());

    $.ajax({
        type: "POST",
        url: "/addStudent/" + username + "/" + classId,
        success: function (data) {
            if (data.ret == true) {
                $("#stu-list").load(" #stu-list > *");
                $('#added').fadeToggle();
                setTimeout(function () {
                    $("#added").fadeOut(400);
                }, 3000)
            } else {
                alert(data.res);
            }

        }
    })

}

function remStudent(uid) {
    $.ajax({
        type: "POST",
        url: "/remStudent/" + uid + "/" + classId,
        success: function (data) {
            $("#stu-list").load(" #stu-list > *");
            $('#remove').fadeToggle();
            setTimeout(function () {
                $("#remove").fadeOut(400);
            }, 3000)
        }
    })

}

function makeAnnoucement() {
    var AnnTitle = encodeURIComponent($("#annTitle").val());
    var AnnBody = encodeURIComponent($("#annContent").val());
    $.ajax({
        type: "POST",
        url: "/addAnnouncement?annTitle=" + AnnTitle + "&annBody=" + AnnBody + "&classId=" + classId,
        success: function (data) {
            $("#announcement-container").load(" #announcement-container > *");
            $("#annTitle").val("");
            $("#annContent").val("");
        },
        error: function() {
            alert("An error occured. Please check your internet connection.");
        }
    })
}
var tempId;
function editAnn(id){
    tempId = id;
    $.ajax({
        type: "GET",
        url: "/get-announcement?annId=" + id,
        success: function (data) {
            $('#editAnnTitle').val(data[0]);
            $('#editAnnContent').val(data[1]);
            $('#editAnn').modal('toggle');
        }
    })
}

function editAnnSave(){
    $.ajax({
        type: "POST",
        url: "/edit-announcement?annTitle=" + encodeURIComponent($('#editAnnTitle').val()) 
        + "&annContent=" + encodeURIComponent($('#editAnnContent').val()) + "&annId=" + tempId,
        success: function (data) {
            $('#editAnn').modal('toggle');
            $("#announcement-container").load(" #announcement-container > *");
        },
        error: function(data) {
            alert("An error occured. Please check your connection.")
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
var delId;
function confirm(id){
    delId= id;
    $("#confirmDel").modal('toggle');
}
function delAnn(){
    $.ajax({
        type: "GET",
        url: "/del-announcement?annId=" + delId,
        success: function (data) {
            $("#announcement-container").load(" #announcement-container > *");
            $('#confirmDel').modal('toggle');
        }
    })
}

var changeDueId;

function openChangeDue(id){
    changeDueId = id;
    $("#changedue").modal('toggle');
}
function confChangeDue(){

    var date = encodeURIComponent($("#dueDate1").val());
    var time = encodeURIComponent($("#dueTime1").val());

    $.ajax({
        type: "GET",
        url: "/change-due?quizId=" + changeDueId + "&dueDate=" + date + "&dueTime=" + time,
        success: function (data) {
            $("#changedue").modal('toggle');
            $("#active-tab").load(" #active-tab > *");
            $("#sched-quizzes").load(" #sched-quizzes > *");
        },
        error: function(){
            alert("An Error occured, please check your connection");
        }
    })
}
var rescheduleId;

function openReschedule(id){
    rescheduleId = id;
    $("#reschedule-modal").modal('toggle');
}
var schedVis;

function selectBoxReSched(val) {
    schedVis = val;
}

function confReschedule(){

    var date = encodeURIComponent($("#dueDateRes").val());
    var time = encodeURIComponent($("#dueTimeRes").val());
    

    $.ajax({
        type: "GET",
        url: "/reschedule?quizId=" + rescheduleId + "&dueDate=" + date + "&dueTime=" + time + "&schedVis=" + schedVis,
        success: function (data) {
            $("#reschedule-modal").modal('toggle');
            $("#sched-quizzes").load(" #sched-quizzes > *");
        },
        error: function(data){
            alert("An Error Occured. Please check your connection.")
        }
    })
}
var scheduleId;

function openSchedule(id){
    scheduleId = id;
    $("#schedule-modal").modal('toggle');
}
var schedVis1;

function selectBoxSched(val) {
    schedVis1 = val;
}

function confSchedule(){

    var date = encodeURIComponent($("#dueDateSched").val());
    var time = encodeURIComponent($("#dueTimeSched").val());
    

    $.ajax({
        type: "GET",
        url: "/schedule?quizId=" + scheduleId + "&dueDate=" + date + "&dueTime=" + time + "&schedVis=" + schedVis1,
        success: function (data) {
            $("#schedule-modal").modal('toggle');
            $("#sched-quizzes").load(" #sched-quizzes > *");
        },
        error: function(){
            alert("An error occured. Please check your connection.")
        }
    })
}
var quizIdTemp;
function toggleVis(id){
    $("#private-modal").modal("toggle");
    quizIdTemp=id;
}
function confirmToggleVis(){
    $.ajax({
        type: "GET",
        url: "/toggle-quiz?quizId=" + quizIdTemp,
        success: function (data) {
            $("#private-modal").modal('toggle');
            $("#sched-quizzes").load(" #sched-quizzes > *");
            $("#active-tab").load(" #active-tab > *");
        },
        error: function(){
            alert("An error occured. Please check your connection.")
        }
    })
}

function showStuAverageChart(sid){
    $("#StuAverageChart").modal("toggle");
    $.ajax({
        type: "POST",
        url: "/get-stu-results/" + sid + "/" + classId,
        success: function (data) {
            if(data != null){
                loadStuAverageChart(data.results, data.average, data.label);
                $("#stu-average-title").html(data.name + "'s Chart");
                $("#stu-average-avg").html(data.studentAverage + "%");
                if(data.studentAverage < 50){
                    $("#stu-average-avg").removeClass("table-success");
                    $("#stu-average-avg").removeClass("table-warning");
                    $("#stu-average-avg").addClass("table-danger");
                } else if (data.studentAverage >= 50 && data.studentAverage < 70){
                    $("#stu-average-avg").removeClass("table-success");
                    $("#stu-average-avg").removeClass("table-danger");
                    $("#stu-average-avg").addClass("table-warning");
                } else if(data.studentAverage >=70){
                    $("#stu-average-avg").removeClass("table-danger");
                    $("#stu-average-avg").removeClass("table-warning");
                    $("#stu-average-avg").addClass("table-success");
                }
                
                if(data.percentChange < 0){
                    $("#stu-average-percent").html(data.percentChange + "%");
                    $("#stu-average-percent").removeClass("table-success");
                    $("#stu-average-percent").addClass("table-danger");
                } else {
                    $("#stu-average-percent").html("+" + data.percentChange + "%");
                    $("#stu-average-percent").removeClass("table-danger");
                    $("#stu-average-percent").addClass("table-success");
                }
                console.log(data);

            }
            
        },
        error: function(){
        
        }
    })
}
var chartAvg;
function loadStuAverageChart(mainData, average, label){
    console.log("here");
    var ctx = document.getElementById('myChart').getContext('2d');
    chartAvg = new Chart(ctx, {
        // The type of chart we want to create
        type: 'line',
    
        // The data for our dataset
        data: {
            labels: label,
            
            datasets: [
                {
                    label: 'Test Results',
                    borderColor: 'rgb(255, 99, 132)',
                    data: mainData,
                    
                },
                {
                    label: 'Class Average Result',
                    borderColor: 'rgb(255, 255, 102)',
                    data: average

                    
                }
        ]
        },
    
        // Configuration options go here
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        suggestMin: 0,
                        suggestedMax: 100,
                        beginAtZero: true
                        
                    }

                }]
            }
        }
    });
} 
$('#StuAverageChart').on('hidden.bs.modal', function () {
    chartAvg.destroy();
});

function preloadPie(qid){
    $("#pieModal").modal('toggle');
    $.ajax({
        type: "GET",
        url: "/get-averages?qid=" + qid, 
        success: function (data) {
            loadPieChart(data.arr, ["Excellent", "Satisfactory", "Inadequate"]);
            $("#pieChart-title").html(data.title);
        },        
        error: function(){
            alert("An error occured. Please check your connection.")
        }
    })

    
}

var pieChart;
function loadPieChart(mainData, label){
    var ctx = document.getElementById('pieChart').getContext('2d');
    pieChart = new Chart(ctx, {
        // The type of chart we want to create
        type: 'pie',
    
        // The data for our dataset
        data: {
            labels: label,
            datasets: [
                {
                    label: 'Test Results',
                    backgroundColor: ["#c3e6cb", "#fff3cd", "#f8d7da"],
                    data: mainData,
                    
                }
        ]
        },
    
        // Configuration options go here
        options: {
        }
    });
} 

$('#pieModal').on('hidden.bs.modal', function () {
    pieChart.destroy();
});

function delQuiz(id){
    $.ajax({
        type: "GET",
        url: "/delete-quiz?qid=" + id, 
        success: function (data) {
            $("#sched-quizzes").load(" #sched-quizzes > *");
            $("#active-tab").load(" #active-tab > *");
            $("#inactive-tab").load(" #inactive-tab > *");
        }
    })
}
function completedEdit(id){
    $("#completed-edit-modal").modal('toggle');
    $("#completed-edit-yes").removeAttr("href");
    $("#completed-edit-yes").attr("href", "/quiz-edit-question/" + id);
  
  }