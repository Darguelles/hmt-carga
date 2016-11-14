(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('CondicionPagoDialogController', CondicionPagoDialogController);

    CondicionPagoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CondicionPago'];

    function CondicionPagoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CondicionPago) {
        var vm = this;

        vm.condicionPago = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.condicionPago.id !== null) {
                CondicionPago.update(vm.condicionPago, onSaveSuccess, onSaveError);
            } else {
                CondicionPago.save(vm.condicionPago, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:condicionPagoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
