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

            if(value.ineventCategory === 'Markets')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/markets.jpg')";
            if(value.ineventCategory === 'Eventbrite')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/eventbrite.jpg')";
            if(value.ineventCategory === 'Sports')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/sports.jpg')";
            if(value.ineventCategory === 'Live Music')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/live_music.jpg')";
            if(value.ineventCategory === 'Exhibitions and Shows')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/art_and_exhibitions.jpg')";
            if(value.ineventCategory === 'Food and Wine')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/food_and_wine.jpg')";
            if(value.ineventCategory === 'Performing Arts')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/performing_arts.jpg')";
            if(value.ineventCategory === 'Art and Exhibitions')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/art_and_exhibitions.jpg')";
            if(value.ineventCategory === 'Community')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/community.jpg')";
            if(value.ineventCategory === 'Gardens and Agriculture')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/gardens_and_agriculture.jpg')";
            if(value.ineventCategory === 'Lifestyle')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/lifestyle.jpg')";
            if(value.ineventCategory === 'Festivals and Celebrations')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/festivals_and_celebrations.jpg')";
            if(value.ineventCategory === 'Shopping')
              document.getElementById('contentTop').style.backgroundImage = "url('/images/stock_photos/shopping.jpg')";

            _this.events.push({
              'title': value.title,
              'description': value.description,
              'date': value.eventDate,

              'type': value.type,
              'ineventCategory': value.ineventCategory,
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
    excats: ["Live Music",
      "Lifestyle",
      "Food and Wine"]
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
      $scope.events = EventFactory.events;

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
