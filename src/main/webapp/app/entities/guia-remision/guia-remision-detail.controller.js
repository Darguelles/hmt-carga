(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('GuiaRemisionDetailController', GuiaRemisionDetailController);

    GuiaRemisionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GuiaRemision', 'Cotizacion', 'Proveedor', 'Transporte'];

    function GuiaRemisionDetailController($scope, $rootScope, $stateParams, previousState, entity, GuiaRemision, Cotizacion, Proveedor, Transporte) {
        var vm = this;

        vm.guiaRemision = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:guiaRemisionUpdate', function(event, result) {
            vm.guiaRemision = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
