var inEvent = angular.module( 'inEvent', ['ngTouch', 'ngAnimate', 'geolocation'] );


inEvent.factory( 'EventFactory', [ '$scope', '$http', 'geolocation', function( $scope, $http, geolocation ) {
  return {

    events: [],

    getEvents: function() {
      var _this = this;

      geolocation.getLocation().then(function(data){
        $scope.coords = {lat:data.coords.latitude, long:data.coords.longitude};

        var eventData = $http.get('http://' + location.hostname + ((location.port == 80 || 0 || undefined || null) ? '' : (':' + location.port)) + '/api/events/get_relevant?lat=' + $scope.coords.lat + '&lon=' + $scope.coords.long + '&query_set=' + $scope.index);
        eventData.then( function( result ) {
          angular.forEach(result.data, function( value, key ) {

            console.log(value.venue.name);
            console.log(_this);

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

  $scope.index = 0;
  $scope.reject = function() {
    $scope.action = 'reject';
    $timeout( function() {
      $scope.action = undefined;
    },500);

    if( $scope.expanded ) $scope.expand();

    EventFactory.getEvents();
    $scope.events = EventFactory.events;


    $scope.index ++;
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



  EventFactory.getEvents();
  $scope.events = EventFactory.events;


}]);


//inEvent.directive( 'overlay', [ function( $animate ) {
//  return {
//    link: function( scope, element, attrs ) {
//
//      $animate.enter(element, parent, after, {
//        before : function() { }, //before the DOM operation
//        after : function() { }, //just after the DOM operation
//        close : function(wasCancelled) { } //when the animation is complete
//      });
//
//    }
//  }
//}]);


inEvent.directive( 'swiper', [ '$swipe', function( $swipe ) {
  return {
    link: function( scope, element, attrs ) {


//      $swipe.bind( element, {
//        start: function (coords, event) {
//          startX = coords.x;
//          startY = coords.y;
//        },
//        move: function (coords, event) {
//
//          direction = ( startX < coords.x + 5 ) ? 'right' : 'left';
//          drag = ( Math.abs( startY - coords.y ) < 50 );
//
//          if( drag ) {
//            element[0].style.left = startX - coords.x + 'px';
//            console.log(startX + ' ' + coords.x);
//          }
//
//        },
//        end: function (coords, event) {
//          console.log('end');
//          element[0].style.left = '0px';
//        }
//
//      });

    }
  }
}]);