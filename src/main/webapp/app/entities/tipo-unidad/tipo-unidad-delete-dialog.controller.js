(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('TipoUnidadDeleteController',TipoUnidadDeleteController);

    TipoUnidadDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoUnidad'];

    function TipoUnidadDeleteController($uibModalInstance, entity, TipoUnidad) {
        var vm = this;

        vm.tipoUnidad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoUnidad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
