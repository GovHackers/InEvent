var inEvent = angular.module( 'inEvent', [] );


inEvent.controller('mainController', function( $scope ) {

  $scope.drawer = false;

  $scope.drawerOpen = function() {
    $scope.drawer = true;
  };

  $scope.drawerClose = function() {
    $scope.drawer = false;
  };

  $scope.drawerToggle = function() {
    $scope.drawer = !$scope.drawer;
  }

});


inEvent.directive( 'test', [ function() {
  return {
    replace: true,
    template: '<span>hello</span>',
    link: function( scope, element, attrs ) {
      console.log(element);
    }
  }
}]);