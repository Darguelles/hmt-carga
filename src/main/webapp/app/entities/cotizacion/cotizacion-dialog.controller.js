(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('CotizacionDialogController', CotizacionDialogController);

    CotizacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cotizacion', 'Cliente', 'TipoServicio', 'TipoUnidad'];

    function CotizacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cotizacion, Cliente, TipoServicio, TipoUnidad) {
        var vm = this;

        vm.cotizacion = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clientes = Cliente.query();
        vm.tiposervicios = TipoServicio.query();
        vm.tipounidads = TipoUnidad.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cotizacion.id !== null) {
                Cotizacion.update(vm.cotizacion, onSaveSuccess, onSaveError);
            } else {
                Cotizacion.save(vm.cotizacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:cotizacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
