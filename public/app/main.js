var inEvent = angular.module( 'inEvent', ['ngTouch', 'ngAnimate'] );


inEvent.controller('mainController', function( $scope, $timeout ) {

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

  $scope.reject = function() {
    $scope.action = 'reject';
    $timeout( function() {
      $scope.action = undefined;
    },500);
  };

  $scope.save = function() {
    $scope.action = 'accept';
    $timeout( function() {
      $scope.action = undefined;
    },500);
  };

});


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
//          if( !drag ) this.end();
//
//          console.log(drag);
//
//          if (direction === 'left' && drag) angular.element( document.getElementById('yes') ).addClass('test')
//          else angular.element( document.getElementById('yes') ).removeClass('test')
//
//        },
//        end: function (coords, event) {
//          console.log('end');
//
//        }
//
//      });

    }
  }
}]);