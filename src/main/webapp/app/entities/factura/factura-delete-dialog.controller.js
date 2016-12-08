(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('FacturaDeleteController',FacturaDeleteController);

    FacturaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Factura'];

    function FacturaDeleteController($uibModalInstance, entity, Factura) {
        var vm = this;

        vm.factura = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Factura.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
