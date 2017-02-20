(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('OrdenVentaDialogController', OrdenVentaDialogController);

    OrdenVentaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrdenVenta', 'Cotizacion', 'CotizacionFilterGenerated'];

    function OrdenVentaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrdenVenta, Cotizacion, CotizacionFilterGenerated) {
        var vm = this;

        vm.ordenVenta = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cotizacions = Cotizacion.query();

        $scope.date_code = new Date();

        $scope.cot_selected = JSON.parse(window.localStorage.getItem("current_cotizacion"));

        if($scope.cot_selected){
            vm.ordenVenta.cotizacion = $scope.cot_selected
        }

        $scope.cotizaciones = CotizacionFilterGenerated.query();

        $scope.addCotizacion = function (selected) {
            console.log('>>>>>>>'+selected.originalObject)
            vm.ordenVenta.cotizacion = selected.originalObject;
            // console.log('CURRENT GUIDE : '+ JSON.stringify(vm.guiaRemision));
        };

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ordenVenta.id !== null) {
                OrdenVenta.update(vm.ordenVenta, onSaveSuccess, onSaveError);
            } else {
                OrdenVenta.save(vm.ordenVenta, onSaveSuccess, onSaveError);
            }
            window.localStorage.removeItem('current_cotizacion');
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:ordenVentaUpdate', result);
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
