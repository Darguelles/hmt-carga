(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('CotizacionFilterGenerated', CotizacionFilterGenerated);

    CotizacionFilterGenerated.$inject = ['$resource', 'DateUtils'];

    function CotizacionFilterGenerated ($resource, DateUtils) {
        var resourceUrl =  'api/cotizacionsByEstadoGenerated';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
        });
    }
})();

