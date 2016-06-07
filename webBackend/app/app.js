'use strict';

// Declare app level module which depends on views, and components
angular.module('carousel-q', [
  'ngRoute',
  'carousel-q.view1',
  'carousel-q.view2',
  'carousel-q.version'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');

  $routeProvider.otherwise({redirectTo: '/view1'});
}]);