#angular-touch.js
Fixes for issue [#5732](https://github.com/angular/angular.js/issues/5732) and [#5721](https://github.com/angular/angular.js/issues/5721)

###Touchend / mouseup listeners bound to window instead of element
- User can now swipe off the element and still trigger a swipe
  * performing a swipe on a drawer that only has a visual width of 50px while closed is now possible

###Improved tolerances for ng-swipe-left & ng-swipe-right
- User doesn't have to perform a ridiculous perfectly straight line in order to trigger a swipe
- Increased vertical discrepancy to account for longer swipes