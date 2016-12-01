(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('OrdenVentaDeleteController',OrdenVentaDeleteController);

    OrdenVentaDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrdenVenta'];

    function OrdenVentaDeleteController($uibModalInstance, entity, OrdenVenta) {
        var vm = this;

        vm.ordenVenta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrdenVenta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
