(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('GuiaRemisionFilter', GuiaRemisionFilter);

    GuiaRemisionFilter.$inject = ['$resource', 'DateUtils'];

    function GuiaRemisionFilter ($resource, DateUtils) {
        var resourceUrl =  'api/pendingGuides';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
        });
    }
})();

