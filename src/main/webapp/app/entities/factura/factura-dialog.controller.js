(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('FacturaDialogController', FacturaDialogController);

    FacturaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Factura', 'Cliente', 'Servicio'];

    function FacturaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Factura, Cliente, Servicio) {
        var vm = this;

        vm.factura = entity;
        vm.clear = clear;
        vm.save = save;
        vm.clientes = Cliente.query();
        vm.servicios = Servicio.query();

        $scope.precioBase = function () {
            vm.factura.precioBase = vm.factura.cantidad * vm.factura.precioUnitario;
            vm.factura.igv = vm.factura.precioBase * 0.18;
            vm.factura.precioTotal = (vm.factura.precioBase + vm.factura.igv) - vm.factura.cliente.descuento;
        }

        $scope.igv = function () {

        }

        $scope.precioTotal = function (precioBase, igv, descuento) {
            var precioigv = precioBase + igv;
            if(descuento==0){
                return precioigv;
            }
            return precioigv - descuento;
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


    }
})();
