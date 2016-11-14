(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .controller('TipoUnidadDialogController', TipoUnidadDialogController);

    TipoUnidadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoUnidad'];

    function TipoUnidadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoUnidad) {
        var vm = this;

        vm.tipoUnidad = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoUnidad.id !== null) {
                TipoUnidad.update(vm.tipoUnidad, onSaveSuccess, onSaveError);
            } else {
                TipoUnidad.save(vm.tipoUnidad, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmtcargaApp:tipoUnidadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
