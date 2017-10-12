
/**
 * Download Server JSON files as ZIP Code is available on this site.
 * 
 * https://angular-ui.github.io/bootstrap/#!#datepickerPopup
 * https://angular-ui.github.io/bootstrap/assets/app.js
 */
/* global FastClick, smoothScroll */
angular.module('ui.bootstrap.demo', ['ui.bootstrap', 'plunker', 'ngTouch', 'ngAnimate', 'ngSanitize'], function($httpProvider){
  if (!!window.FastClick) {
    FastClick.attach(document.body);
  }
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
}).run(['$location', function($location){
  //Allows us to navigate to the correct element on initialization
  if ($location.path() !== '' && $location.path() !== '/') {
    smoothScroll(document.getElementById($location.path().substring(1)), 500, function(el) {
      location.replace('#' + el.id);
    });
  }
}]).factory('buildFilesService', function ($http, $q) {

  var moduleMap;
  var rawFiles;

  return {
    getModuleMap: getModuleMap,
    getRawFiles: getRawFiles,
    get: function () {
      return $q.all({
        moduleMap: getModuleMap(),
        rawFiles: getRawFiles()
      });
    }
  };

  function getModuleMap() {
    return moduleMap ? $q.when(moduleMap) : $http.get('assets/module-mapping.json')
      .then(function (result) {
        moduleMap = result.data;
        return moduleMap;
      });
  }

  function getRawFiles() {
    return rawFiles ? $q.when(rawFiles) : $http.get('assets/raw-files.json')
      .then(function (result) {
        rawFiles = result.data;
        return rawFiles;
      });
  }

})
.controller('MainCtrl', MainCtrl)
.controller('SelectModulesCtrl', SelectModulesCtrl)
.controller('DownloadCtrl', DownloadCtrl);

function MainCtrl($scope, $http, $document, $uibModal, orderByFilter) {
  // Grab old version docs
  $http.get('/bootstrap/versions-mapping.json')
    .then(function(result) {
      $scope.oldDocs = result.data;
    });

  $scope.showBuildModal = function() {
    var modalInstance = $uibModal.open({
      templateUrl: 'buildModal.html',
      controller: 'SelectModulesCtrl',
      resolve: {
        modules: function(buildFilesService) {
          return buildFilesService.getModuleMap()
            .then(function (moduleMap) {
              return Object.keys(moduleMap);
            });
        }
      }
    });
  };

  $scope.showDownloadModal = function() {
    var modalInstance = $uibModal.open({
      templateUrl: 'downloadModal.html',
      controller: 'DownloadCtrl'
    });
  };
}