(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('GuiaRemisionDetailController', GuiaRemisionDetailController);

    GuiaRemisionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GuiaRemision', 'Cotizacion', 'Proveedor', 'Transporte', 'Factura'];

    function GuiaRemisionDetailController($scope, $rootScope, $stateParams, previousState, entity, GuiaRemision, Cotizacion, Proveedor, Transporte, Factura) {
        var vm = this;

        vm.guiaRemision = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:guiaRemisionUpdate', function(event, result) {
            vm.guiaRemision = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
