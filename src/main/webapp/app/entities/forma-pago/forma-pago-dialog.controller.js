(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('FormaPagoDialogController', FormaPagoDialogController);

    FormaPagoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FormaPago'];

    function FormaPagoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FormaPago) {
        var vm = this;

        vm.formaPago = entity;
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
            if (vm.formaPago.id !== null) {
                FormaPago.update(vm.formaPago, onSaveSuccess, onSaveError);
            } else {
                FormaPago.save(vm.formaPago, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:formaPagoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
