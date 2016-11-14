(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('CondicionPago', CondicionPago);

    CondicionPago.$inject = ['$resource'];

    function CondicionPago ($resource) {
        var resourceUrl =  'api/condicion-pagos/:id';

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
