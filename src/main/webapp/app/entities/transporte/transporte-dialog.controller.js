(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('TransporteDialogController', TransporteDialogController);

    TransporteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Transporte', 'TipoUnidad'];

    function TransporteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Transporte, TipoUnidad) {
        var vm = this;

        vm.transporte = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.tipounidads = TipoUnidad.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.transporte.id !== null) {
                Transporte.update(vm.transporte, onSaveSuccess, onSaveError);
            } else {
                Transporte.save(vm.transporte, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:transporteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaRevisionTecnica = false;
        vm.datePickerOpenStatus.fechaVencimientoSoat = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
