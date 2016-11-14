(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('ServicioDetailController', ServicioDetailController);

    ServicioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Servicio'];

    function ServicioDetailController($scope, $rootScope, $stateParams, previousState, entity, Servicio) {
        var vm = this;

        vm.servicio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:servicioUpdate', function(event, result) {
            vm.servicio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
