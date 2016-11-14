(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('FormaPagoDetailController', FormaPagoDetailController);

    FormaPagoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FormaPago'];

    function FormaPagoDetailController($scope, $rootScope, $stateParams, previousState, entity, FormaPago) {
        var vm = this;

        vm.formaPago = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmtcargaApp:formaPagoUpdate', function(event, result) {
            vm.formaPago = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
