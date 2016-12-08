(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('Factura', Factura);

    Factura.$inject = ['$resource', 'DateUtils'];

    function Factura ($resource, DateUtils) {
        var resourceUrl =  'api/facturas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
