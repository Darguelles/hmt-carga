(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('CotizacionFilter', CotizacionFilter);

    CotizacionFilter.$inject = ['$resource', 'DateUtils'];

    function CotizacionFilter ($resource, DateUtils) {
        var resourceUrl =  'api/cotizacionsByEstadoApproved';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
        });
    }
})();

