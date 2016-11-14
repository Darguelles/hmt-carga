(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('TransporteDetailController', TransporteDetailController);

    TransporteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Transporte', 'TipoUnidad'];

    function TransporteDetailController($scope, $rootScope, $stateParams, previousState, entity, Transporte, TipoUnidad) {
        var vm = this;

        vm.transporte = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:transporteUpdate', function(event, result) {
            vm.transporte = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
