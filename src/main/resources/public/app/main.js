var inEvent = angular.module( 'inEvent', ['ngTouch', 'ngAnimate', 'geolocation', 'checklist-model'] );


inEvent.factory( 'EventFactory', [ '$http', 'geolocation', function( $http, geolocation ) {
  return {

    events: [],

    getEvents: function(i, excats) {
      var _this = this;

      geolocation.getLocation().then(function(data){
         var coords = {lat:data.coords.latitude, long:data.coords.longitude};

        var eventData = $http.get('http://' + location.hostname + ((location.port == 80 || 0 || undefined || null) ? '' : (':' + location.port)) + '/api/events/get_relevant?lat=' + coords.lat + '&lon=' + coords.long + '&query_set=' + i + '&excluded_cats=' + excats.join());
//        var eventData = $http.get('events.json');
        eventData.then( function( result ) {

          angular.forEach(result.data, function( value, key ) {

            _this.events.push({
              'title': value.title,
              'description': value.description,
              'date': value.eventDate,

              'type': value.type,
              'category': value.category,
              'tags': value.tag,

              'images': value.imageURLs,

              'priceKnown': value.priceKnown,
              'isFree': value.isFree,
              'price': value.price,

              'venue': value.venue,
              'location': value.location,

              'train': value.nearestTrain,
              'tram': value.nearestTram,
              'bus': value.nearestBus,

              'link': value.link, //events victoria - use as second preference
              'url': value.url, //event website - use as first preference
              'email': value.contactEmail,
              'phone': value.contactPhone
            });
          });
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
    "Food & Wine",
    "Performing Arts",
    "Markets",
    "Art & Exhibitions",
    "Community",
    "Gardens & Agriculture",
    "Lifestyle",
    "Festivals and Celebrations",
    "Shopping"
  ];
  $scope.categoryModel = {
    excats: ["Sports",
      "Live Music",
      "Exhibitions and Shows",
      "Food & Wine",
      "Performing Arts",
      "Markets",
      "Art & Exhibitions",
      "Community",
      "Gardens & Agriculture",
      "Lifestyle",
      "Festivals and Celebrations",
      "Shopping"]
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
    $scope.i ++;
    $scope.action = 'reject';
    $timeout( function() {
      $scope.action = undefined;
    },500);

    if( $scope.expanded ) $scope.expand();

    console.log($scope.categoryModel);

    EventFactory.getEvents($scope.i, $scope.excats());
    $scope.events = EventFactory.events;

  };

  $scope.save = function() {
    if( !$scope.expanded ) {
      $scope.action = 'accept';
      $timeout( function() {
        $scope.action = undefined;
      },500);

      $scope.expand();
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

