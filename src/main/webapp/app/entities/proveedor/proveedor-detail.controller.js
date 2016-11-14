(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('ProveedorDetailController', ProveedorDetailController);

    ProveedorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Proveedor'];

    function ProveedorDetailController($scope, $rootScope, $stateParams, previousState, entity, Proveedor) {
        var vm = this;

        vm.proveedor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:proveedorUpdate', function(event, result) {
            vm.proveedor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
