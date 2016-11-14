(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('ServicioDeleteController',ServicioDeleteController);

    ServicioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Servicio'];

    function ServicioDeleteController($uibModalInstance, entity, Servicio) {
        var vm = this;

        vm.servicio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Servicio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
