(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('FacturaDetailController', FacturaDetailController);

    FacturaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Factura', 'Cliente', 'Servicio', 'GuiaRemision'];

    function FacturaDetailController($scope, $rootScope, $stateParams, previousState, entity, Factura, Cliente, Servicio, GuiaRemision) {
        var vm = this;

        vm.factura = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:facturaUpdate', function(event, result) {
            vm.factura = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
