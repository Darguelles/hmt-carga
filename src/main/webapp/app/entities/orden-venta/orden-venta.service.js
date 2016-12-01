(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('OrdenVenta', OrdenVenta);

    OrdenVenta.$inject = ['$resource', 'DateUtils'];

    function OrdenVenta ($resource, DateUtils) {
        var resourceUrl =  'api/orden-ventas/:id';

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
