(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('GuiaRemisionDeleteController',GuiaRemisionDeleteController);

    GuiaRemisionDeleteController.$inject = ['$uibModalInstance', 'entity', 'GuiaRemision'];

    function GuiaRemisionDeleteController($uibModalInstance, entity, GuiaRemision) {
        var vm = this;

        vm.guiaRemision = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GuiaRemision.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
