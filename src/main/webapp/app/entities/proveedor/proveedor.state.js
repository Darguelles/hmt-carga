(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('proveedor', {
            parent: 'entity',
            url: '/proveedor?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Proveedors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proveedor/proveedors.html',
                    controller: 'ProveedorController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('proveedor-detail', {
            parent: 'entity',
            url: '/proveedor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Proveedor'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proveedor/proveedor-detail.html',
                    controller: 'ProveedorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Proveedor', function($stateParams, Proveedor) {
                    return Proveedor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'proveedor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('proveedor-detail.edit', {
            parent: 'proveedor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proveedor/proveedor-dialog.html',
                    controller: 'ProveedorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Proveedor', function(Proveedor) {
                            return Proveedor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proveedor.new', {
            parent: 'proveedor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proveedor/proveedor-dialog.html',
                    controller: 'ProveedorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ruc: null,
                                nombre: null,
                                telefono: null,
                                direccion: null,
                                contacto: null,
                                email: null,
                                banco: null,
                                numeroCuenta: null,
                                numeroCuentaInterbancario: null,
                                numeroCuentaDetraccion: null,
                                matpel: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('proveedor', null, { reload: 'proveedor' });
                }, function() {
                    $state.go('proveedor');
                });
            }]
        })
        .state('proveedor.edit', {
            parent: 'proveedor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proveedor/proveedor-dialog.html',
                    controller: 'ProveedorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Proveedor', function(Proveedor) {
                            return Proveedor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proveedor', null, { reload: 'proveedor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proveedor.delete', {
            parent: 'proveedor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proveedor/proveedor-delete-dialog.html',
                    controller: 'ProveedorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Proveedor', function(Proveedor) {
                            return Proveedor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proveedor', null, { reload: 'proveedor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
