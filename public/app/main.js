var inEvent = angular.module( 'inEvent', [] );

inEvent.directive( 'test', [ function() {
  return {
    replace: true,
    template: '<span>hello</span>',
    link: function( scope, element, attrs ) {
      console.log(element);
    }
  }
}]);