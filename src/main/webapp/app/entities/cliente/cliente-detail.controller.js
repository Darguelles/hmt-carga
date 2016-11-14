(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('ClienteDetailController', ClienteDetailController);

    ClienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cliente', 'FormaPago', 'CondicionPago'];

    function ClienteDetailController($scope, $rootScope, $stateParams, previousState, entity, Cliente, FormaPago, CondicionPago) {
        var vm = this;

        vm.cliente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:clienteUpdate', function(event, result) {
            vm.cliente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
