(function() {
    'use strict';
    angular
        .module('hmtcargaApp')
        .factory('FormaPago', FormaPago);

    FormaPago.$inject = ['$resource'];

    function FormaPago ($resource) {
        var resourceUrl =  'api/forma-pagos/:id';

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
