
app.controller("newsController", function ($scope, $newsService, $window, $rootScope) {
  $scope.newsList = [];
  $scope.noData = false;
  $scope.loading = true;
  $scope.hasMore = false;
  $scope.searchDTO = {
    start: 0,
    length: 6,
    categoryId: ""
  };

  var loadData = function () {
    $scope.loading = true;
    $newsService.searchNews($scope.searchDTO).then(
      function (data) {
        $scope.newsList = $scope.newsList.concat(data);
        if ($scope.newsList.length == 0) {
          $scope.noData = true;
        } else {
          $scope.noData = false;
        }
        if (data.length == $scope.searchDTO.length) {
          $scope.hasMore = true;
        } else {
          $scope.hasMore = false;
        }
        $scope.loading = false;
      },
      function (error) {
        $scope.loading = false;
      }
    );
  };
  $scope.loadMore = function () {
    $scope.searchDTO.start += $scope.searchDTO.length;
    loadData();
  };

  $scope.filterByCategory = function () {
    $scope.newsList = [];
    $scope.searchDTO.start = 0;
    loadData();
  };
  loadData();
});

app.controller("albumController", function ($scope, $albumService, $window) {
  $scope.albumList = [];
  $scope.noData = false;
  $scope.loading = true;
  $scope.hasMore = false;
  $scope.searchDTO = {
    start: 0,
    length: 6
  };

  var loadData = function () {
    $scope.loading = true;
    $albumService.searchAlbum($scope.searchDTO).then(
      function (data) {
        $scope.albumList = $scope.albumList.concat(data);
        if ($scope.albumList.length == 0) {
          $scope.noData = true;
        } else {
          $scope.noData = false;
        }
        if (data.length == $scope.searchDTO.length) {
          $scope.hasMore = true;
        } else {
          $scope.hasMore = false;
        }
        $scope.loading = false;
      },
      function (error) {
        $scope.loading = false;
      }
    );
  };
  $scope.loadMore = function () {
    $scope.searchDTO.start += $scope.searchDTO.length;
    loadData();
  };
  // search
  $scope.search = function () {
    $scope.albumList = [];
    $scope.searchDTO.page = 1;
    loadData();
  };
  loadData();
});
