app.config([ '$httpProvider', function($httpProvider) {
	$httpProvider.defaults.headers.common = {
		'X-CSRF-TOKEN' : csrf_token
	};
} ]);
app.service('$newsService', function($http, $q) {
	var SEARCH_NEWS_URI = ctxPath + '/news/list';

	this.searchNews = function(searchDTO) {
		var deferred = $q.defer();
		$http.post(SEARCH_NEWS_URI, searchDTO).then(function(response) {
			deferred.resolve(response.data);
		}, function(err, code) {
			deferred.reject(err);
		});
		return deferred.promise;
	}

});

app.service('$albumService', function($http, $q) {
	var SEARCH_ALBUM_URI = ctxPath + '/album/list';

	this.searchAlbum = function(searchDTO) {
		var deferred = $q.defer();
		$http.post(SEARCH_ALBUM_URI, searchDTO).then(function(response) {
			deferred.resolve(response.data);
		}, function(err, code) {
			deferred.reject(err);
		});
		return deferred.promise;
	}

});
