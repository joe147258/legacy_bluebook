

function hideKey(){
      $("#classKey").hide();
}
function showKey(){
    $("#classKey").animate({
        height: "90",
      }, 500 );
      $("#classKey").show();
}

function showChart(id){
    $("#ChartModal").modal("toggle");
    $.ajax({
        type: "POST",
        url: "/get-results/" + id,
        success: function (data) {
            if(data!=null){
                loadStuAverageChart(data.results, data.average, data.label);
                $("#chart-title").html(data.className);
                $("#averageClass").html(data.studentAverage + "%");
                if(data.studentAverage < 50){
                    $("#averageClass").removeClass("table-success");
                    $("#averageClass").removeClass("table-warning");
                    $("#averageClass").addClass("table-danger");
                } else if (data.studentAverage >= 50 && data.studentAverage < 70){
                    $("#averageClass").removeClass("table-success");
                    $("#averageClass").removeClass("table-danger");
                    $("#averageClass").addClass("table-warning");
                } else if(data.studentAverage >=70){
                    $("#averageClass").removeClass("table-danger");
                    $("#averageClass").removeClass("table-warning");
                    $("#averageClass").addClass("table-success");
                }

                if(data.percentChange < 0){
                    $("#percentChange").html(data.percentChange + "%");
                    $("#percentChange").removeClass("table-success")
                    $("#percentChange").addClass("table-danger")
                } else {
                    $("#percentChange").html("+" + data.percentChange + "%");
                    $("#percentChange").removeClass("table-danger")
                    $("#percentChange").addClass("table-success")
                }
                

            } else {
                alert("Something went wrong!");
            }
        }
    })
}
var chart;
function loadStuAverageChart(mainData, average, label){
    var ctx = document.getElementById('myChart').getContext('2d');
    chart = new Chart(ctx, {
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
function deleteResult(id){
    $.ajax({
        type: "POST",
        url: "/detete-result/" + id,
        success: function (data) {
            $("#results-container").load(" #results-container > *");
            $("#allRes").load(" #allRes > *");
        }
    })
}
$('#ChartModal').on('hidden.bs.modal', function () {
    chart.destroy();
});

