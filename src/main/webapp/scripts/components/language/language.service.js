(function(angular) {
	'use strict';
	angular.module('fimepedApp').factory(
			'Language',
			function($q, $http, $translate, LANGUAGES) {
				return {
					getCurrent : function() {
						var deferred = $q.defer(), language = $translate
								.storage().get('NG_TRANSLATE_LANG_KEY');

						if (angular.isUndefined(language)) {
							language = 'es';
						}

						deferred.resolve(language);
						return deferred.promise;
					},
					getAll : function() {
						var deferred = $q.defer();
						deferred.resolve(LANGUAGES);
						return deferred.promise;
					}
				};
			}).constant('LANGUAGES', [ 'es', 'en'
	// JHipster will add new languages here
	]);
	/*
	 * Languages codes are ISO_639-1 codes, see
	 * http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes They are written in
	 * English to avoid character encoding issues (not a perfect solution)
	 */
}(window.angular));