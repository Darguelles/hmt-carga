(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('EventoDetailController', EventoDetailController);

    EventoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Evento'];

    function EventoDetailController($scope, $rootScope, $stateParams, previousState, entity, Evento) {
        var vm = this;

        vm.evento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:eventoUpdate', function(event, result) {
            vm.evento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
