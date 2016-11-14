(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('CondicionPagoDetailController', CondicionPagoDetailController);

    CondicionPagoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CondicionPago'];

    function CondicionPagoDetailController($scope, $rootScope, $stateParams, previousState, entity, CondicionPago) {
        var vm = this;

        vm.condicionPago = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:condicionPagoUpdate', function(event, result) {
            vm.condicionPago = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
