(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('CotizacionDeleteController',CotizacionDeleteController);

    CotizacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cotizacion'];

    function CotizacionDeleteController($uibModalInstance, entity, Cotizacion) {
        var vm = this;

        vm.cotizacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cotizacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
