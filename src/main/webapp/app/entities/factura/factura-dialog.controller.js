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
        vm.calcularPrefioFinal = calcularPrefioFinal;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clientes = Cliente.query();
        vm.servicios = Servicio.query();

        vm.guiaremisions = GuiaRemisionFilter.query();

        vm.guiasSeleccionadas = [];

        $scope.guiaremisions = GuiaRemisionFilter.query();

        $scope.addGuiaToArray = function (selected) {

            if(!selected.originalObject.id){
                console.log('Invalid')
                return;
            }
            if(existeGuiaInCurrentArray(selected.originalObject.id)){
                console.log('Already added')
                return;
            }else{
                vm.guiasSeleccionadas.push(selected.originalObject);
            }
            vm.factura.cliente = vm.guiasSeleccionadas[0].cotizacion.cliente;
            vm.factura.descuento = vm.guiasSeleccionadas[0].cotizacion.cliente.descuento;
            vm.factura.tipoDescuento = vm.guiasSeleccionadas[0].cotizacion.cliente.tipoDescuento;
            calcularPrefioFinal();
        };

        $scope.removeGuiaFromArray = function (selected) {
            removeFromArray(vm.guiasSeleccionadas, 'id', selected)
            console.log('CURRENT ARRAY : '+JSON.stringify(vm.guiasSeleccionadas))
            calcularPrefioFinal();
        };

        function removeFromArray(arr, attr, value){
            var i = arr.length;
            while(i--){
                if( arr[i]
                    && arr[i].hasOwnProperty(attr)
                    && (arguments.length > 2 && arr[i][attr] === value ) ){

                    arr.splice(i,1);

                }
            }
            return arr;
        }


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

//      TRAER GUIA ALMACENADA EN LOCAL STORAGE
        $scope.guia_selected = JSON.parse(window.localStorage.getItem("current_guia_remision"));

        if($scope.guia_selected!= null){
            var guia_actual = $scope.guia_selected
            vm.guiasSeleccionadas.push(guia_actual);
            vm.factura.cliente = guia_actual.cotizacion.cliente;
            vm.factura.precioBase += guia_actual.cotizacion.precio
//          REMOVE FROM LOCAL STORAGE
            window.localStorage.removeItem('current_guia_remision');
            calcularPrefioFinal();
        }

        function calcularPrefioFinal() {

            if(!vm.guiasSeleccionadas){
                console.log('RETORNO')
                return;
            }
            vm.factura.precioBase = 0.0;
            var precioAcumulado = 0;
            vm.guiasSeleccionadas.forEach(function (item) {
                precioAcumulado+=item.cotizacion.precio;
            })
            vm.factura.precioBase += precioAcumulado;
            console.log('RETORNO  1 '+vm.factura.precioBase)
            vm.factura.precioBase += ((vm.factura.cantidad * vm.factura.precioUnitario) || 0.0)
            console.log('RETORNO  2 '+vm.factura.precioBase)
            vm.factura.precioBase += (vm.factura.precio || 0.0);
            console.log('RETORNO  3 '+vm.factura.precioBase)

            // if(vm.factura.precio){
            //     console.log('PRECIO : '+vm.factura.precio)
            //     vm.factura.precioBase += (vm.factura.precio)
            // }


            vm.factura.igv = vm.factura.precioBase * 0.18;
            console.log('IGV BASE : '+vm.factura.igv)
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
