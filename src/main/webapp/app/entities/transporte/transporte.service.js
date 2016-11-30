(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('Transporte', Transporte);

    Transporte.$inject = ['$resource', 'DateUtils'];

    function Transporte ($resource, DateUtils) {
        var resourceUrl =  'api/transportes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaRevisionTecnica = DateUtils.convertLocalDateFromServer(data.fechaRevisionTecnica);
                        data.fechaVencimientoSoat = DateUtils.convertLocalDateFromServer(data.fechaVencimientoSoat);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaRevisionTecnica = DateUtils.convertLocalDateToServer(copy.fechaRevisionTecnica);
                    copy.fechaVencimientoSoat = DateUtils.convertLocalDateToServer(copy.fechaVencimientoSoat);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaRevisionTecnica = DateUtils.convertLocalDateToServer(copy.fechaRevisionTecnica);
                    copy.fechaVencimientoSoat = DateUtils.convertLocalDateToServer(copy.fechaVencimientoSoat);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
