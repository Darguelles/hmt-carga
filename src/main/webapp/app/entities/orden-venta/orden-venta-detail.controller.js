(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('OrdenVentaDetailController', OrdenVentaDetailController);

    OrdenVentaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrdenVenta', 'Cotizacion'];

    function OrdenVentaDetailController($scope, $rootScope, $stateParams, previousState, entity, OrdenVenta, Cotizacion) {
        var vm = this;

        vm.ordenVenta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:ordenVentaUpdate', function(event, result) {
            vm.ordenVenta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
