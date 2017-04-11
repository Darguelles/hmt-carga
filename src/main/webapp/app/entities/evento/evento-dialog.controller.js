(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('EventoDialogController', EventoDialogController);

    EventoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Evento'];

    function EventoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Evento) {
        var vm = this;

        vm.evento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $scope.calcularClienteTotal = function(){
            vm.evento.clienteTotal = vm.evento.clienteMonto - vm.evento.clienteAdelanto;
        }

        $scope.calcularProveedorTotal = function(){
            vm.evento.total = vm.evento.montoServicio - (vm.evento.adelanto1+vm.evento.adelanto2+vm.evento.adelanto3+vm.evento.adelanto4);
        }


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.evento.id !== null) {
                Evento.update(vm.evento, onSaveSuccess, onSaveError);
            } else {
                Evento.save(vm.evento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:eventoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaEmisionFactura = false;
        vm.datePickerOpenStatus.fechaVencimientoFactura = false;
        vm.datePickerOpenStatus.fechaEntrega = false;
        vm.datePickerOpenStatus.fechaEmisionLetra = false;
        vm.datePickerOpenStatus.vencimientoPago = false;
        vm.datePickerOpenStatus.fechaSalida = false;
        vm.datePickerOpenStatus.fechaAdelanto1 = false;
        vm.datePickerOpenStatus.fechaAdelanto2 = false;
        vm.datePickerOpenStatus.fechaAdelanto3 = false;
        vm.datePickerOpenStatus.fechaAdelanto4 = false;
        vm.datePickerOpenStatus.fechaPagoSaldo = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
