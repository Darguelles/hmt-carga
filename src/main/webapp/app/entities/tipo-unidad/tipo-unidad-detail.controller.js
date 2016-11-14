(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('TipoUnidadDetailController', TipoUnidadDetailController);

    TipoUnidadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoUnidad'];

    function TipoUnidadDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoUnidad) {
        var vm = this;

        vm.tipoUnidad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:tipoUnidadUpdate', function(event, result) {
            vm.tipoUnidad = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
