(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('Factura', Factura);

    Factura.$inject = ['$resource'];

    function Factura ($resource) {
        var resourceUrl =  'api/facturas/:id';

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
