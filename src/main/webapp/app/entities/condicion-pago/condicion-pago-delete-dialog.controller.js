(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('CondicionPagoDeleteController',CondicionPagoDeleteController);

    CondicionPagoDeleteController.$inject = ['$uibModalInstance', 'entity', 'CondicionPago'];

    function CondicionPagoDeleteController($uibModalInstance, entity, CondicionPago) {
        var vm = this;

        vm.condicionPago = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CondicionPago.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
