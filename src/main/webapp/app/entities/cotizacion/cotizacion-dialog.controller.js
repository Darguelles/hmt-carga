(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('CotizacionDialogController', CotizacionDialogController);

    CotizacionDialogController.$inject = ['$location', '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cotizacion', 'Cliente', 'Servicio', 'TipoUnidad'];

    function CotizacionDialogController ($location, $timeout, $scope, $stateParams, $uibModalInstance, entity, Cotizacion, Cliente, Servicio, TipoUnidad) {
        var vm = this;

        vm.cotizacion = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clientes = Cliente.query();
        vm.servicios = Servicio.query();
        vm.tipounidads = TipoUnidad.query();

        $scope.date_code = new Date();

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

        $scope.currency = [{ name: "Soles", id: 1 }, { name: "Dolares", id: 2 }];
        $scope.selectedCurrency = vm.cotizacion.moneda;

        //Abrir modal nueva orden de venta
        $scope.goToOrdenVentaDialog = function () {
            $location.path('/orden-venta/new');
            window.localStorage.setItem("current_cotizacion", JSON.stringify(vm.cotizacion));
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
