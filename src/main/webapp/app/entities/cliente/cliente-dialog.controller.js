(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('ClienteDialogController', ClienteDialogController);

    ClienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cliente', 'FormaPago', 'CondicionPago'];

    function ClienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cliente, FormaPago, CondicionPago) {
        var vm = this;

        vm.cliente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.formapagos = FormaPago.query();
        vm.condicionpagos = CondicionPago.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cliente.id !== null) {
                Cliente.update(vm.cliente, onSaveSuccess, onSaveError);
            } else {
                Cliente.save(vm.cliente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:clienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
