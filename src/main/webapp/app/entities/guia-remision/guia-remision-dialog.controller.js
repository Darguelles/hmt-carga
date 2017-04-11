(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('GuiaRemisionDialogController', GuiaRemisionDialogController);

    GuiaRemisionDialogController.$inject = ['$location','$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GuiaRemision', 'Cotizacion', 'Proveedor', 'Transporte', 'Factura', 'CotizacionFilter'];

    function GuiaRemisionDialogController ($location, $timeout, $scope, $stateParams, $uibModalInstance, entity, GuiaRemision, Cotizacion, Proveedor, Transporte, Factura, CotizacionFilter) {
        var vm = this;

        vm.guiaRemision = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cotizacions = Cotizacion.query();
        vm.cotizacionsPendientes = CotizacionFilter.query();
        vm.proveedors = Proveedor.query();
        vm.transportes = Transporte.query();
        vm.facturas = Factura.query();

        $scope.addCotizacion = function (selected) {
            vm.guiaRemision.cotizacion = selected.originalObject;
            console.log('CURRENT GUIDE : '+ JSON.stringify(vm.guiaRemision));
        };

        $scope.date_code = new Date();

        $scope.getPdf = function(id){
            window.open('/api/guia-remisions/pdf/'+id);
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        $scope.filterByTipoUnidad = function (cotActual) {
            return function (item) {
                if (item.tipoUnidad.id == cotActual)
                {
                    return true;
                }
                return false;
            };
        };

        //Abrir modal nueva factura
        $scope.goToFacturaDialog = function () {
            $location.path('/factura/new');
            window.localStorage.setItem("current_guia_remision", JSON.stringify(vm.guiaRemision));
        }

        $scope.cotizaciones = CotizacionFilter.query();
        //


        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {

            if(!vm.guiaRemision.cotizacion){
                return false;
            }

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
            $scope.getPdf(vm.guiaRemision.codigo)
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
