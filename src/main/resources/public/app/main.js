var inEvent = angular.module( 'inEvent', ['ngTouch', 'ngAnimate', 'geolocation', 'checklist-model'] );


inEvent.factory( 'EventFactory', [ '$http', 'geolocation', function( $http, geolocation ) {
  return {

    events: [],

    getEvents: function(i, excats) {
      var _this = this;

      geolocation.getLocation().then(function(data){
         var coords = {lat:data.coords.latitude, long:data.coords.longitude};

//        var eventData = $http.get('http://' + location.hostname + ((location.port == 80 || 0 || undefined || null) ? '' : (':' + location.port)) + '/api/events/get_relevant?lat=' + coords.lat + '&lon=' + coords.long + '&query_set=' + i + '&excluded_cats=' + excats.join());
        var eventData = $http.get('events.json');
        eventData.then( function( result ) {

          angular.forEach(result.data, function( value, key ) {

            _this.events.push({
              'data': result.data[0]
            });
          });
        });
        eventData.success(function() {

        });
        eventData.error(function(data, status, headers, config) {
          console.log(status);
        });


      });

    }

  }

}]);




inEvent.controller('mainController', [ '$scope', '$timeout', 'EventFactory', function( $scope, $timeout, EventFactory ) {

  $scope.categories = [
    "Sports",
    "Live Music",
    "Exhibitions and Shows",
    "Food and Wine",
    "Performing Arts",
    "Markets",
    "Art and Exhibitions",
    "Community",
    "Gardens and Agriculture",
    "Lifestyle",
    "Festivals and Celebrations",
    "Shopping",
    "Eventbrite"
  ];
  $scope.categoryModel = {
    excats: ["Sports",
      "Live Music",
      "Exhibitions and Shows",
      "Food and Wine",
      "Performing Arts",
      "Markets",
      "Art and Exhibitions",
      "Community",
      "Gardens and Agriculture",
      "Lifestyle",
      "Festivals and Celebrations",
      "Shopping",
      "Eventbrite"]
  };
  $scope.excats = function() {
    return _.difference($scope.categories, $scope.categoryModel.excats);
  };

  $scope.drawer = false;

  $scope.drawerOpen = function() {
    $scope.drawer = true;
  };

  $scope.drawerClose = function() {
    $scope.drawer = false;
  };

  $scope.drawerToggle = function() {
    $scope.drawer = !$scope.drawer;
  };

  $scope.i = 0;
  $scope.reject = function() {
    if( !$scope.expanded ) {
      $scope.i++;
      $scope.action = 'reject';
      $timeout(function () {
        $scope.action = undefined;
      }, 500);

      EventFactory.getEvents($scope.i, $scope.excats());
      $scope.events = EventFactory.events[i].data

    } else {
      $scope.action = '';
    }
  };

  $scope.save = function() {
    if( !$scope.expanded ) {
      $scope.action = 'accept';
      $timeout( function() {

        $scope.action = undefined;
      },500);

      $scope.expand();
    } else {
      $scope.action = '';
    }
  };


  $scope.expanded = false;
  $scope.expand = function() {
    $scope.expanded = !$scope.expanded;
    angular.element( document.getElementById('content') ).toggleClass('expand');
  };



  EventFactory.getEvents($scope.i, $scope.excats());
  $scope.events = EventFactory.events;


}]);

