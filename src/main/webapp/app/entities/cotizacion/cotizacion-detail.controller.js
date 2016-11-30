(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('CotizacionDetailController', CotizacionDetailController);

    CotizacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cotizacion', 'Cliente', 'Servicio', 'TipoUnidad'];

    function CotizacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Cotizacion, Cliente, Servicio, TipoUnidad) {
        var vm = this;

        vm.cotizacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:cotizacionUpdate', function(event, result) {
            vm.cotizacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
