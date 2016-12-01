(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('GuiaRemisionDialogController', GuiaRemisionDialogController);

    GuiaRemisionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GuiaRemision', 'Cotizacion', 'Proveedor', 'Transporte'];

    function GuiaRemisionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GuiaRemision, Cotizacion, Proveedor, Transporte) {
        var vm = this;

        vm.guiaRemision = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cotizacions = Cotizacion.query();
        vm.proveedors = Proveedor.query();
        vm.transportes = Transporte.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.guiaRemision.id !== null) {
                GuiaRemision.update(vm.guiaRemision, onSaveSuccess, onSaveError);
            } else {
                GuiaRemision.save(vm.guiaRemision, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:guiaRemisionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaEmision = false;
        vm.datePickerOpenStatus.fechaTraslado = false;
        vm.datePickerOpenStatus.fechaIngreso = false;
        vm.datePickerOpenStatus.fechaSalida = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
