(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('FormaPagoDeleteController',FormaPagoDeleteController);

    FormaPagoDeleteController.$inject = ['$uibModalInstance', 'entity', 'FormaPago'];

    function FormaPagoDeleteController($uibModalInstance, entity, FormaPago) {
        var vm = this;

        vm.formaPago = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FormaPago.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
