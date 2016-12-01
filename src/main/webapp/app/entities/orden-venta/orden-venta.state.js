(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('orden-venta', {
            parent: 'entity',
            url: '/orden-venta?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrdenVentas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orden-venta/orden-ventas.html',
                    controller: 'OrdenVentaController',
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
        .state('orden-venta-detail', {
            parent: 'entity',
            url: '/orden-venta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrdenVenta'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orden-venta/orden-venta-detail.html',
                    controller: 'OrdenVentaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OrdenVenta', function($stateParams, OrdenVenta) {
                    return OrdenVenta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'orden-venta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('orden-venta-detail.edit', {
            parent: 'orden-venta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-venta/orden-venta-dialog.html',
                    controller: 'OrdenVentaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrdenVenta', function(OrdenVenta) {
                            return OrdenVenta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orden-venta.new', {
            parent: 'orden-venta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-venta/orden-venta-dialog.html',
                    controller: 'OrdenVentaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('orden-venta', null, { reload: 'orden-venta' });
                }, function() {
                    $state.go('orden-venta');
                });
            }]
        })
        .state('orden-venta.edit', {
            parent: 'orden-venta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-venta/orden-venta-dialog.html',
                    controller: 'OrdenVentaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrdenVenta', function(OrdenVenta) {
                            return OrdenVenta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orden-venta', null, { reload: 'orden-venta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orden-venta.delete', {
            parent: 'orden-venta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-venta/orden-venta-delete-dialog.html',
                    controller: 'OrdenVentaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrdenVenta', function(OrdenVenta) {
                            return OrdenVenta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orden-venta', null, { reload: 'orden-venta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
