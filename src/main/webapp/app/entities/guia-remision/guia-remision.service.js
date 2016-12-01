(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('GuiaRemision', GuiaRemision);

    GuiaRemision.$inject = ['$resource', 'DateUtils'];

    function GuiaRemision ($resource, DateUtils) {
        var resourceUrl =  'api/guia-remisions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaEmision = DateUtils.convertLocalDateFromServer(data.fechaEmision);
                        data.fechaTraslado = DateUtils.convertLocalDateFromServer(data.fechaTraslado);
                        data.fechaIngreso = DateUtils.convertDateTimeFromServer(data.fechaIngreso);
                        data.fechaSalida = DateUtils.convertDateTimeFromServer(data.fechaSalida);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaEmision = DateUtils.convertLocalDateToServer(copy.fechaEmision);
                    copy.fechaTraslado = DateUtils.convertLocalDateToServer(copy.fechaTraslado);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaEmision = DateUtils.convertLocalDateToServer(copy.fechaEmision);
                    copy.fechaTraslado = DateUtils.convertLocalDateToServer(copy.fechaTraslado);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
