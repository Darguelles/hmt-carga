(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('TransporteDeleteController',TransporteDeleteController);

    TransporteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Transporte'];

    function TransporteDeleteController($uibModalInstance, entity, Transporte) {
        var vm = this;

        vm.transporte = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Transporte.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
