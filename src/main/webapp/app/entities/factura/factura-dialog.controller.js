(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('FacturaDialogController', FacturaDialogController);

    FacturaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Factura', 'Cliente', 'Servicio', 'GuiaRemision'];

    function FacturaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Factura, Cliente, Servicio, GuiaRemision) {
        var vm = this;

        vm.factura = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clientes = Cliente.query();
        vm.servicios = Servicio.query();
        vm.guiaremisions = GuiaRemision.query();

        $scope.guia_selected = JSON.parse(window.localStorage.getItem("current_guia_remision"));

        $scope.precioBase = function () {
            vm.factura.precioBase = vm.factura.cantidad * vm.factura.precioUnitario;
            vm.factura.igv = vm.factura.precioBase * 0.18;
            vm.factura.descuento = vm.factura.cliente.descuento;
            vm.factura.tipoDescuento = vm.factura.cliente.tipoDescuento;
            if(vm.factura.cliente.tipoDescuento == 'porcentaje'){
                var precio = vm.factura.precioBase + vm.factura.igv;
                var valorPorcentaje = (precio * vm.factura.cliente.descuento)/100;
                vm.factura.precioTotal = precio - valorPorcentaje;
            } else {
                vm.factura.precioTotal = (vm.factura.precioBase + vm.factura.igv) - vm.factura.cliente.descuento;
            }
            window.localStorage.removeItem('current_guia_remision');
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.factura.id !== null) {
                Factura.update(vm.factura, onSaveSuccess, onSaveError);
            } else {
                Factura.save(vm.factura, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:facturaUpdate', result);
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
