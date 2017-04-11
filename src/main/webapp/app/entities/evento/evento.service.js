(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('Evento', Evento);

    Evento.$inject = ['$resource', 'DateUtils'];

    function Evento ($resource, DateUtils) {
        var resourceUrl =  'api/eventos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaEmisionFactura = DateUtils.convertLocalDateFromServer(data.fechaEmisionFactura);
                        data.fechaVencimientoFactura = DateUtils.convertLocalDateFromServer(data.fechaVencimientoFactura);
                        data.fechaEntrega = DateUtils.convertLocalDateFromServer(data.fechaEntrega);
                        data.fechaEmisionLetra = DateUtils.convertLocalDateFromServer(data.fechaEmisionLetra);
                        data.vencimientoPago = DateUtils.convertLocalDateFromServer(data.vencimientoPago);
                        data.fechaSalida = DateUtils.convertLocalDateFromServer(data.fechaSalida);
                        data.fechaAdelanto1 = DateUtils.convertLocalDateFromServer(data.fechaAdelanto1);
                        data.fechaAdelanto2 = DateUtils.convertLocalDateFromServer(data.fechaAdelanto2);
                        data.fechaAdelanto3 = DateUtils.convertLocalDateFromServer(data.fechaAdelanto3);
                        data.fechaAdelanto4 = DateUtils.convertLocalDateFromServer(data.fechaAdelanto4);
                        data.fechaPagoSaldo = DateUtils.convertLocalDateFromServer(data.fechaPagoSaldo);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaEmisionFactura = DateUtils.convertLocalDateToServer(copy.fechaEmisionFactura);
                    copy.fechaVencimientoFactura = DateUtils.convertLocalDateToServer(copy.fechaVencimientoFactura);
                    copy.fechaEntrega = DateUtils.convertLocalDateToServer(copy.fechaEntrega);
                    copy.fechaEmisionLetra = DateUtils.convertLocalDateToServer(copy.fechaEmisionLetra);
                    copy.vencimientoPago = DateUtils.convertLocalDateToServer(copy.vencimientoPago);
                    copy.fechaSalida = DateUtils.convertLocalDateToServer(copy.fechaSalida);
                    copy.fechaAdelanto1 = DateUtils.convertLocalDateToServer(copy.fechaAdelanto1);
                    copy.fechaAdelanto2 = DateUtils.convertLocalDateToServer(copy.fechaAdelanto2);
                    copy.fechaAdelanto3 = DateUtils.convertLocalDateToServer(copy.fechaAdelanto3);
                    copy.fechaAdelanto4 = DateUtils.convertLocalDateToServer(copy.fechaAdelanto4);
                    copy.fechaPagoSaldo = DateUtils.convertLocalDateToServer(copy.fechaPagoSaldo);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaEmisionFactura = DateUtils.convertLocalDateToServer(copy.fechaEmisionFactura);
                    copy.fechaVencimientoFactura = DateUtils.convertLocalDateToServer(copy.fechaVencimientoFactura);
                    copy.fechaEntrega = DateUtils.convertLocalDateToServer(copy.fechaEntrega);
                    copy.fechaEmisionLetra = DateUtils.convertLocalDateToServer(copy.fechaEmisionLetra);
                    copy.vencimientoPago = DateUtils.convertLocalDateToServer(copy.vencimientoPago);
                    copy.fechaSalida = DateUtils.convertLocalDateToServer(copy.fechaSalida);
                    copy.fechaAdelanto1 = DateUtils.convertLocalDateToServer(copy.fechaAdelanto1);
                    copy.fechaAdelanto2 = DateUtils.convertLocalDateToServer(copy.fechaAdelanto2);
                    copy.fechaAdelanto3 = DateUtils.convertLocalDateToServer(copy.fechaAdelanto3);
                    copy.fechaAdelanto4 = DateUtils.convertLocalDateToServer(copy.fechaAdelanto4);
                    copy.fechaPagoSaldo = DateUtils.convertLocalDateToServer(copy.fechaPagoSaldo);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
