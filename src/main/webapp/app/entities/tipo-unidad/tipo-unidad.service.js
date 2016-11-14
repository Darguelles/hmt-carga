(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('TipoUnidad', TipoUnidad);

    TipoUnidad.$inject = ['$resource'];

    function TipoUnidad ($resource) {
        var resourceUrl =  'api/tipo-unidads/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
