(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('FacturaDialogController', FacturaDialogController);

    FacturaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Factura', 'Cliente', 'Servicio', 'GuiaRemisionFilter'];

    function FacturaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Factura, Cliente, Servicio, GuiaRemisionFilter) {
        var vm = this;

        vm.factura = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clientes = Cliente.query();
        vm.servicios = Servicio.query();

        vm.guiaremisions = GuiaRemisionFilter.query();

        vm.guiasSeleccionadas = [];

        $scope.guiaremisions = GuiaRemisionFilter.query();

        $scope.addGuiaToArray = function (selected) {

            console.log('ORIGINAL OBJ '+selected.originalObject)
            console.log('FACTURA OBJ '+JSON.stringify(vm.factura))
            if(existeGuiaInCurrentArray(selected.originalObject.id)){
                console.log('Already added')
            }else{
                vm.guiasSeleccionadas.push(selected.originalObject);
            }
            console.log('CURRENT GUIDE : '+ JSON.stringify(vm.guiasSeleccionadas));
        };

        function existeGuiaInCurrentArray(currId) {
            var exist = false;
            vm.guiasSeleccionadas.forEach(function (item) {
                if(item.id == currId){
                    exist = true;
                }else{
                    exist = false;
                }
            })
            return exist;
        }

        $scope.date_code = new Date();

        $scope.guia_selected = JSON.parse(window.localStorage.getItem("current_guia_remision"));

        $scope.precioBase = function () {
            vm.factura.precioBase = (vm.factura.cantidad * vm.factura.precioUnitario) + (vm.factura.precio || 0.0);
            vm.factura.igv = vm.factura.precioBase * 0.18;
            if(vm.factura.tipoDescuento == 'porcentaje'){
                var precio = vm.factura.precioBase + vm.factura.igv ;
                var valorPorcentaje = (precio * vm.factura.descuento)/100;
                vm.factura.precioTotal = precio - valorPorcentaje;
            } else {
                vm.factura.precioTotal = (vm.factura.precioBase + vm.factura.igv) - vm.factura.descuento;
            }
            // if(vm.factura.precio){
            //     vm.factura.precioTotal = vm.factura.precioTotal + vm.factura.precio
            // }
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
                vm.factura.listaGuias = vm.guiasSeleccionadas;
                console.log('FACTURA TO SAVE : '+JSON.stringify(vm.factura))
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
