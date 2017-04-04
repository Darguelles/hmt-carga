(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('factura', {
            parent: 'entity',
            url: '/factura?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Facturas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/factura/facturas.html',
                    controller: 'FacturaController',
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
        .state('factura-detail', {
            parent: 'entity',
            url: '/factura/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Factura'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/factura/factura-detail.html',
                    controller: 'FacturaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Factura', function($stateParams, Factura) {
                    return Factura.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'factura',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('factura-detail.edit', {
            parent: 'factura-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/factura/factura-dialog.html',
                    controller: 'FacturaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Factura', function(Factura) {
                            return Factura.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('factura.new', {
            parent: 'factura',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/factura/factura-dialog.html',
                    controller: 'FacturaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                precioUnitario: null,
                                cantidad: null,
                                precioBase: null,
                                igv: null,
                                precioTotal: null,
                                codigo: null,
                                descuento: null,
                                tipoDescuento: null,
                                fecha: null,
                                contingencia: null,
                                motivo: null,
                                precio: null,
                                numeroOrdenCompra: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('factura', null, { reload: 'factura' });
                }, function() {
                    $state.go('factura');
                });
            }]
        })
        .state('factura.edit', {
            parent: 'factura',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/factura/factura-dialog.html',
                    controller: 'FacturaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Factura', function(Factura) {
                            return Factura.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('factura', null, { reload: 'factura' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('factura.delete', {
            parent: 'factura',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/factura/factura-delete-dialog.html',
                    controller: 'FacturaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Factura', function(Factura) {
                            return Factura.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('factura', null, { reload: 'factura' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
