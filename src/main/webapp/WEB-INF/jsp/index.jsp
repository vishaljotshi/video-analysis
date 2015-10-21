
<!DOCTYPE html>
<meta charset="utf-8">
<html ng-app='nvd3TestApp'>

<head>

<title>Angular.js nvd3.js Pie Chart Directive</title>

<script src="/PatientVideoAnalysis/resources/js/angular.js"></script>
<script charset="utf-8" src="/PatientVideoAnalysis/resources/js/d3.js"></script>
<script src="/PatientVideoAnalysis/resources/js/nv.d3.js"></script>
<script
  src="/PatientVideoAnalysis/resources/js/angularjs-nvd3-directives.js"></script>
<link rel="stylesheet"
  href="/PatientVideoAnalysis/resources/css/nv.d3.css" />
<link rel="stylesheet"
  href="/PatientVideoAnalysis/resources/css/style.css" />
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
  var app = angular.module("nvd3TestApp", [ 'nvd3ChartDirectives' ]);

  function ExampleCtrl($scope, $http) {
    $scope.mostPercentage = 40;
    $scope.leastPercentage=100;
    $scope.heading="Patient video analysis report";
    $scope.sortType="watch_count";
    $scope.sortReverse  = false;
    
    
    //elements
    $scope.arr={};
    
    
    $scope.arr['mostPopularChart']=true;
    $scope.arr['leastPopularChart']=false;
    $scope.arr['languagePopularChart']=false;
    $scope.arr['morningPopularChart']=false;
    $scope.arr['eveningPopularChart']=false;
    $scope.arr['nightPopularChart']=false;
    $scope.arr['trafficChart']=false;
    $scope.arr['patientBarChart']=false;
    $scope.arr['customPopularChart']=false;
    $scope.arr['videoTable']=false;
    $scope.arr['optimisedVideoLength']=false;
    
    
    
    //show and hide div`s function
     $scope.display = function(id){
       for (var property in $scope.arr) {
            if ($scope.arr.hasOwnProperty(property)) {
              $scope.arr[property]=false;
            }
        }
       $scope.arr[id]="true";
         };
    
    
    //convert data to array function
    $scope.getDataArray = function(data) {
      i = 0;
      j = [];
      for (obj in data) {
        j[i] = [ i, data[obj].watch_count ];
        i++;
      }
      return j;
    }
    
    //get all videos
     $http.get('videoProgress').success(function(data) {
      $scope.videoData = data;
    });
 
   //get all LANGUAGES
     $http.get('languages').success(function(data) {
      $scope.languages = data;
    });
   
   	//optimised video length
     $http.get('optimised-length').success(function(data) {
         $scope.optimisedVideo = data;
         
       });
 
 
    //get request for most popular videos
    $http.get('most-popular/'+ $scope.mostPercentage).success(function(data) {
      $scope.popularVideoData = data;
      $scope.popularBarData = [ {
        "key" : "Most popular",
        "values" : $scope.getDataArray(data)
      } ];

    }).error(function() {
        alert("Could not connect to the server");
    });

    //get the least popular videos
    $http.get('least-popular/'+ $scope.leastPercentage).success(function(data) {
      $scope.leastPopularVideoData = data;
      $scope.leastPopularBarData = [ {
        "key" : "Least popular",
        "values" : $scope.getDataArray(data)
      } ];
    });
    
    
    //Morning video data loading http://localhost:8080/PatientVideoAnalysis/videos/popular/?startTime=00&endTime=24
    $http.get('popular/?startTime=00&endTime=12').success(function(data) {
      $scope.morningVideo = data;
      $scope.morningBarData = [ {
        "key" : "Popular in morning",
        "values" : $scope.getDataArray(data)
      } ];
    });
    
    //Evening video data loading http://localhost:8080/PatientVideoAnalysis/videos/popular/?startTime=00&endTime=24
    $http.get('popular/?startTime=12&endTime=18').success(function(data) {
      $scope.eveningVideo = data;
      $scope.eveningBarData = [ {
        "key" : "Popular in evening",
        "values" : $scope.getDataArray(data)
      } ];
    });
    
    //Night video data loading http://localhost:8080/PatientVideoAnalysis/videos/popular/?startTime=00&endTime=24
    $http.get('popular/?startTime=18&endTime=23').success(function(data) {
      $scope.nightVideo = data;
      $scope.nightBarData = [ {
        "key" : "Popular in night",
        "values" : $scope.getDataArray(data)
      } ];
    });
    
    
    
    //Traffic analysis chart
    $http.get('traffic/').success(function(data) {
      
      $scope.trafficLineData = [ {
        "key" : "Traffic results",
        "values" : $scope.formatTime(data)
      } ];
    });
    
    //format time function for traffic analysis
    $scope.formatTime = function(data) {
      i = 0;
      j = [];
      for (obj in data) {
        j[i] = [ data[obj].time_hour, data[obj].watch_count ];
        i++;
      }
      return j;
    }
    //traffic functions X & Y
    
     $scope.xFunctionTraffic = function(){
             return function(d) {
                 return d[0];
             }
         };

         $scope.yFunctionTraffic = function(){
             return function(d) {
                 return d[1];
             }
         }
    //end of traffic analysis part
    
    //graph updater functions
    $scope.updateGraph1 = function(per) {
      $http.get('most-popular/'+ per).success(function(data) {
        $scope.popularVideoData = data;
        $scope.popularBarData = [ {
          "key" : "Most popular",
          "values" : $scope.getDataArray(data)
        } ];
       
        
      }).error(function() {
          alert("could not connect to server");
      });
    }
    
    $scope.updateGraph2 = function(maxPer) {
    $http.get('least-popular/'+ maxPer).success(function(data) {
    	var nullData=[[]];
    	var outputArray= $scope.getDataArray(data);
    	if(outputArray.length==0)
    		outputArray=nullData;
    		
    	$scope.leastPopularVideoData = data;
        $scope.leastPopularBarData = [ {
          "key" : "Least popular",
          "values" : outputArray
        } ];

      }).error(function() {
          alert("could not connect to server");
      });
    }
    
    $scope.updatePatientVideoData = function() {
      $http.get('most-watched/userid/'+$scope.userId).success(function(data) {
    	  var nullData=[[]];
      	var outputArray= $scope.getDataArray(data);
      	if(outputArray.length==0)
      		outputArray=nullData;
      	
        $scope.patientPopularData = data;
        $scope.patientBarData = [ {
          "key" : "Patient Bar data",
          "values" : outputArray
        } ];
      }).error(function() {
          alert("could not connect to server");
      });
      }
    //Update custom popular function
    $scope.updatecustomPopular = function(startTime,endTime) {
    $http.get('popular/?startTime='+startTime+'&endTime='+endTime).success(function(data) {
    	var nullData=[[]];
    	var outputArray= $scope.getDataArray(data);
    	if(outputArray.length==0)
    		outputArray=nullData;
    	
      $scope.customTime = data;
      $scope.customTimeBar = [ {
        "key" : "Popular in evening",
        "values" : outputArray
      } ];
    }).error(function() {
        alert("could not connect to server");
    });
    }
    
    $scope.xFunction = function() {
      return function(d) {
        return d.common_key;
      };
    }

    $scope.yFunction = function() {
      return function(d) {
        return d.watch_count;
      };

    }
    //tick format functions
    $scope.xAxisTickFormatFunction = function(){
        return function(d){
          
            return $scope.popularVideoData[d].common_key;
            
            
        }
    }
    $scope.lxAxisTickFormatFunction = function(){
        return function(d){
      
            return $scope.leastPopularVideoData[d].common_key;
            
            
        }
    }
    
    $scope.xAxisIntervalFunction = function(data){
        return function(d){
          
            return data[d].common_key;
            
            
        }
    }
    
    
    
    
    
    
    $scope.barToolTipFunction = function(){
      return function(key, x, y, e, graph) {
          return  '<h4>' + x + '</h4>' +
                '<p>Watch count is ' +  y + '</p>'
      }
    }
    
    $scope.updateLanguagePopular = function() {
        $http.get('popular/'+ $scope.languageOption).success(function(data) {
            $scope.languagePopular = data;
            $scope.languagePopularBarData = [ {
              "key" : "Most popular by language",
              "values" : $scope.getDataArray(data)
            } ];
           
            
          }).error(function() {
              alert("could not connect to server");
          });
            
        
      }

    $scope.lineToolTipFunction = function(){
      return function(key, x, y, e, graph) {
          return  '<h4>At ' + x + ':00 hours </h4>' +
                '<p>' +  y  + ' videos watched</p>'
      }
    }

    
    $scope.toolTipContentFunction = function() {
      return function(key, x, y, e, graph) {

        return '<h3>' + key + '</h2><p>Watch count is ' + x
            + '</p><p>Average watched :' + y.point.Average_watched
            + ' %</p>';
      }
    }
    
  }
</script>

</head>

<body>

	<div class="container-fluid" ng-controller="ExampleCtrl">
		<nav class="navbar navbar-inverse">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Patient Video Analysis</a>
			</div>
		</nav>
		<div class="row">
			<div class="col-md-2">

				<div class="panel-group">
					<div class="panel panel-info">
						<div class="panel-heading">Video analysis</div>
						<div class="panel-body">
							<a href="#" class='list-group-item'
								ng-click="display('mostPopularChart')">Top 5 popular videos</a>
							<a href="#" class='list-group-item'
								ng-click="display('leastPopularChart')">Least 5 popular
								videos</a>
							<a href="#" class='list-group-item'
								ng-click="display('languagePopularChart')">Language Specific popular videos</a>
						</div>
					</div>
				</div>

				<div class="panel-group">
					<div class="panel panel-info">
						<div class="panel-heading">Best suited videos</div>
						<div class="panel-body">
							<div class="list-group">
								<a href="#" class='list-group-item'
									ng-click="display('morningPopularChart')">Popular videos in
									Morning</a> <a href="#" class='list-group-item'
									ng-click="display('eveningPopularChart')">Popular videos in
									evening</a> <a href="#" class='list-group-item'
									ng-click="display('nightPopularChart')">Popular videos at
									Night</a> <a href="#" class='list-group-item'
									ng-click="display('customPopularChart')">Popular videos by
									custom time</a>
							</div>
						</div>
					</div>
				</div>

				<div class="panel-group">
					<div class="panel panel-info">
						<div class="panel-heading">Traffic analysis</div>
						<div class="panel-body">
							<div class="list-group">
								<a href="#" class='list-group-item'
									ng-click="display('trafficChart')">Video traffic</a> <a
									href="#" class='list-group-item'
									ng-click="display('patientBarChart')">Videos watched by
									patient</a>
							</div>
						</div>
					</div>
				</div>

				<div class="panel-group">
					<div class="panel panel-info">
						<div class="panel-heading">Meta Data</div>
						<div class="panel-body">
							<div class="list-group">
								<a href="#" class='list-group-item'
									ng-click="display('videoTable')">All video List</a> <a href="#"
									class='list-group-item' ng-click="display('optimisedVideoLength')">Most optimised video length</a>
							</div>
						</div>
					</div>
				</div>


			</div>
			<div class="col-md-10">
				<div class="panel panel-info">
					<div class="panel-heading">{{heading}}</div>

					<!--1st panel  -->

					<div class="panel-body" ng-show="arr['mostPopularChart']">
						<h2 class="panel-heading">Top 5 Popular video chart</h2>

						<div class="col-sm-4">
							<div class="input-group">
								<input type="number" min="0" max="100"
									placeholder="Enter Average percentage" class="form-control"
									ng-model="per"> <span class="input-group-btn"> <input
									class="btn btn-default" type="button"
									ng-click="updateGraph1(per)" value="View">
								</span>
							</div>
							<!-- /input-group -->
						</div>
						<div class="col-sm-4"></div>
						<!-- /.col-lg-6 -->

						<div class="col-sm-12">
							<div class="panel panel-info col-md-6">

								<nvd3-pie-chart id="exampleId" data="popularVideoData"
									x="xFunction()" y="yFunction()" width="550" height="550"
									showLabels="true" pieLabelsOutside="true" showValues="true"
									labelType="percent" labelThreshold="0.05"
									tooltipContent="toolTipContentFunction()" showLegend="true"
									legendPosition="bottom" tooltips="true"> <svg></svg>
								</nvd3-pie-chart>
							</div>
							<div class="panel panel-info col-md-6">

								<nvd3-multi-bar-chart data="popularBarData" id="barChart"
									width="550" height="550"
									xAxisTickFormat="xAxisTickFormatFunction()" showXAxis="true"
									showYAxis="true" tooltips="true" noData="No data to display"
									staggeredLabels="true" tooltipcontent="barToolTipFunction()">
								<svg></svg> </nvd3-multi-bar-chart>

							</div>
						</div>
					</div>

					<!--2nd panel  -->
					<div class="panel-body" ng-show="arr['leastPopularChart']">
						<h2 class="panel-heading">5 Least popular video chart</h2>

						<div class="col-sm-4">
							<div class="input-group">
								<input type="number" min="0" max="100"
									placeholder="Enter Average percentage" class="form-control"
									ng-model="maxPer"> <span class="input-group-btn">
									<input class="btn btn-default" type="button"
									ng-click="updateGraph2(maxPer)" value="View">
								</span>
							</div>
							<!-- /input-group -->
						</div>
						<div class="col-sm-4"></div>
						<!-- /.col-lg-6 -->

						<div class="col-sm-12">

							<div class="panel panel-info col-md-6">

								<nvd3-pie-chart id="leastPopular" data="leastPopularVideoData"
									x="xFunction()" y="yFunction()" width="550" height="550"
									showLabels="true" pieLabelsOutside="true" showValues="true"
									labelType="percent" labelThreshold="0.05"
									tooltipContent="toolTipContentFunction()" showLegend="true"
									legendPosition="bottom" tooltips="true"> <svg></svg>
								</nvd3-pie-chart>
							</div>
							<div class="panel panel-info col-md-6">

								<nvd3-multi-bar-chart data="leastPopularBarData"
									id="leastPopularbarChart" width="550" height="550"
									noData="No data to display"
									xAxisTickFormat="lxAxisTickFormatFunction()" showXAxis="true"
									showYAxis="true" tooltips="true"
									tooltipcontent="barToolTipFunction()"> <svg></svg>
								</nvd3-multi-bar-chart>

							</div>
						</div>
					</div>
					
					
					<!-- popularity by language  -->
					
					
					
					<div class="panel-body" ng-show="arr['languagePopularChart']">
						<h2 class="panel-heading">Select a language to view popular videos</h2>

						<div class="col-sm-4">
							<div class="input-group">
								<select class="form-control" ng-model="languageOption" ng-change="updateLanguagePopular()" ng-options="lang.language as lang.language for lang in languages"></select>
							</div>
							<!-- /input-group -->
						</div>
						<div class="col-sm-4"></div>
						<!-- /.col-lg-6 -->

						<div class="col-sm-12">

							<div class="panel panel-info col-md-6">

								<nvd3-pie-chart id="languagePopular" data="languagePopular"
									x="xFunction()" y="yFunction()" width="550" height="550"
									showLabels="true" pieLabelsOutside="true" showValues="true"
									labelType="percent" labelThreshold="0.05"
									tooltipContent="toolTipContentFunction()" showLegend="true"
									legendPosition="bottom" tooltips="true"> <svg></svg>
								</nvd3-pie-chart>
							</div>
							<div class="panel panel-info col-md-6">

								<nvd3-multi-bar-chart data="languagePopularBarData"
									id="languagePopularbarChart" width="550" height="550"
									noData="No data to display"
									xAxisTickFormat="xAxisIntervalFunction(languagePopular)" showXAxis="true"
									showYAxis="true" tooltips="true"
									tooltipcontent="barToolTipFunction()"> <svg></svg>
								</nvd3-multi-bar-chart>

							</div>
						</div>
					</div>

					<!-- 3rd panel  -->

					<div class="panel-body" ng-show="arr['morningPopularChart']">
						<h2 class="panel-heading">Popular videos during morning(12am-12pm)</h2>
						<div class="panel panel-info col-md-6">
							<nvd3-pie-chart id="popularMorning" data="morningVideo"
								x="xFunction()" y="yFunction()" width="550" height="550"
								showLabels="true" pieLabelsOutside="true" showValues="true"
								labelType="percent" labelThreshold="0.05"
								tooltipContent="toolTipContentFunction()" showLegend="true"
								legendPosition="bottom" tooltips="true"> <svg></svg>
							</nvd3-pie-chart>
						</div>
						<div class="panel panel-info col-md-6">

							<nvd3-multi-bar-chart data="morningBarData"
								id="barPopularMorning" width="550" height="550"
								xAxisTickFormat="xAxisIntervalFunction(morningVideo)"
								showXAxis="true" showYAxis="true" tooltips="true"
								tooltipcontent="barToolTipFunction()"> <svg></svg>
							</nvd3-multi-bar-chart>

						</div>
					</div>


					<!-- 4th panel  -->

					<div class="panel-body" ng-show="arr['eveningPopularChart']">
						<h2 class="panel-heading">Popular videos during evening(12pm-6pm)</h2>
						<div class="panel panel-info col-md-6">

							<nvd3-pie-chart id="popularevening" data="eveningVideo"
								x="xFunction()" y="yFunction()" width="450" height="450"
								showLabels="true" pieLabelsOutside="true" showValues="true"
								labelType="percent" labelThreshold="0.05"
								tooltipContent="toolTipContentFunction()" showLegend="true"
								legendPosition="bottom" tooltips="true"> <svg></svg>
							</nvd3-pie-chart>
						</div>
						<div class="panel panel-info col-md-6">

							<nvd3-multi-bar-chart data="eveningBarData"
								id="barPopularevening" width="550" height="550"
								xAxisTickFormat="xAxisIntervalFunction(eveningVideo)"
								showXAxis="true" showYAxis="true" tooltips="true"
								tooltipcontent="barToolTipFunction()"> <svg></svg>
							</nvd3-multi-bar-chart>

						</div>
					</div>

					<!-- 5th panel  -->



					<div class="panel-body" ng-show="arr['nightPopularChart']">
						<h2 class="panel-heading">Popular videos during night(6pm-12am)</h2>

						<div class="panel panel-info col-md-6">

							<nvd3-pie-chart id="popularNight" data="nightVideo"
								x="xFunction()" y="yFunction()" width="450" height="450"
								showLabels="true" pieLabelsOutside="true" showValues="true"
								labelType="percent" labelThreshold="0.05"
								tooltipContent="toolTipContentFunction()" showLegend="true"
								legendPosition="bottom" tooltips="true"> <svg></svg>
							</nvd3-pie-chart>
						</div>
						<div class="panel panel-info col-md-6">

							<nvd3-multi-bar-chart data="nightBarData" id="barPopularNight"
								width="550" height="550"
								xAxisTickFormat="xAxisIntervalFunction(nightVideo)"
								showXAxis="true" showYAxis="true" tooltips="true"
								tooltipcontent="barToolTipFunction()"> <svg></svg>
							</nvd3-multi-bar-chart>

						</div>
					</div>





					<!-- Custom panel chart  -->





					<div class="panel-body" ng-show="arr['customPopularChart']">
						<h2 class="panel-heading">Customized Popular videos list</h2>

						<div class="col-sm-10">
							<div class="form-inline">
								<label for="inp1">View popular videos between(24 hour clock format) </label> <input
									type="number" min="0" max="23" id="inp1"
									placeholder="Starting hours" class="form-control"
									ng-model="startTime"> <label for="inp2"> and </label> <input
									type="number" min="0" max="23" id="inp2"
									placeholder="Ending hours" class="form-control"
									ng-model="endTime"> <input
									class="btn btn-default form-control" type="button"
									ng-click="updatecustomPopular(startTime,endTime)" value="View">

							</div>
							<!-- /input-group -->
						</div>
						<div class="col-sm-2"></div>
						<!-- /.col-lg-6 -->

						<div class="col-sm-12">
							<div class="panel panel-info col-md-6">

								<nvd3-pie-chart id="customTime" data="customTime"
									x="xFunction()" y="yFunction()" width="450" height="450"
									showLabels="true" pieLabelsOutside="true" showValues="true"
									labelType="percent" labelThreshold="0.05"
									tooltipContent="toolTipContentFunction()" showLegend="true"
									legendPosition="bottom" tooltips="true"> <svg></svg>
								</nvd3-pie-chart>
							</div>
							<div class="panel panel-info col-md-6">

								<nvd3-multi-bar-chart data="customTimeBar" id="customTimeBar"
									width="550" height="550"
									xAxisTickFormat="xAxisIntervalFunction(customTime)"
									showXAxis="true" showYAxis="true" tooltips="true"
									staggeredLabels="true" tooltipcontent="barToolTipFunction()">
								<svg></svg> </nvd3-multi-bar-chart>

							</div>
						</div>
					</div>

					<!--6TH panel (traffic)  -->

					<div class="panel-body" ng-show="arr['trafficChart']">
						<h2 class="panel-heading">24 hours video traffic analysis</h2>
						<div class="panel panel-info col-md-10 svg-class">


							<nvd3-line-chart data="trafficLineData" showXAxis="true"
								showYAxis="true" width="600" height="500" tooltips="true"
								interactive="true" tooltipcontent="lineToolTipFunction()"
								isArea="true"> </nvd3-line-chart>

						</div>
					</div>
					<!-- 7th panel  -->
					<div class="panel-body" ng-show="arr['patientBarChart']">
						<h2 class="panel-heading">Patient watched video count tracker
						</h2>
						<div class="panel panel-info col-sm-12 svg-class">
							<div class="col-sm-6">
								<div class="input-group">
									<input type="number" placeholder="Enter User Id"
										class="form-control" ng-model="userId"> <span
										class="input-group-btn"> <input class="btn btn-default"
										type="button" ng-click="updatePatientVideoData()" value="View">
									</span>
								</div>
								<!-- /input-group -->
							</div>
							<div class="col-sm-4"></div>
							<!-- /.col-lg-6 -->
							<div class="col-sm-12">
								<nvd3-multi-bar-chart data="patientBarData" id="patientVideo"
									width="550" height="550"
									xAxisTickFormat="xAxisIntervalFunction(patientPopularData)"
									showXAxis="true" showYAxis="true" tooltips="true"
									tooltipcontent="barToolTipFunction()"> <svg></svg>
								</nvd3-multi-bar-chart>
							</div>
						</div>
					</div>

					<!--8Th panel  -->

					<div class="panel-body" ng-show="arr['videoTable']">
						<h2 class="panel-heading">Videos currently in progress</h2>
						<div class="panel panel-info col-sm-8">
							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<td><a href="#" ng-click="sortType = 'common_key'; sortReverse = !sortReverse">Common Key
										 <span ng-show="sortType == 'common_key' && !sortReverse" class="fa fa-caret-down"></span>
								        <span ng-show="sortType == 'common_key' && sortReverse" class="fa fa-caret-up"></span></a></td>

										<td><a href="#" ng-click="sortType = 'watch_count'; sortReverse = !sortReverse">Watched count
										<span ng-show="sortType == 'watch_count'" class="fa fa-caret-down"></span></a></td>
										<td><a href="#" ng-click="sortType = 'Average_watched'; sortReverse = !sortReverse">Average watched
										<span ng-show="sortType == 'Average_watched'" class="fa fa-caret-down"></span></td>
									</tr>
								</thead>
								<tbody>
								 <tr ng-repeat="obj in videoData | orderBy:sortType:sortReverse" >
								  <td>{{ obj.common_key }}</td>
										<td>{{ obj.watch_count }}</td>
										<td>{{ obj.Average_watched }}</td>
									</tr>

								</tbody>
							</table>
						</div>
					</div>
					
					<!--Optimised video length  -->
					<div class="panel-body" ng-show="arr['optimisedVideoLength']">
						<h2 class="panel-heading">Most optimised video length is {{ optimisedVideo[0].optimised_length }}</h2>
						</div>
						

				</div>

			</div>
		</div>
	</div>
</body>

</html>
