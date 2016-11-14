(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('Transporte', Transporte);

    Transporte.$inject = ['$resource'];

    function Transporte ($resource) {
        var resourceUrl =  'api/transportes/:id';

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
