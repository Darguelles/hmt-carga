(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cotizacion', {
            parent: 'entity',
            url: '/cotizacion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cotizacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cotizacion/cotizacions.html',
                    controller: 'CotizacionController',
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
        .state('cotizacion-detail', {
            parent: 'entity',
            url: '/cotizacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cotizacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cotizacion/cotizacion-detail.html',
                    controller: 'CotizacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Cotizacion', function($stateParams, Cotizacion) {
                    return Cotizacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cotizacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cotizacion-detail.edit', {
            parent: 'cotizacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cotizacion/cotizacion-dialog.html',
                    controller: 'CotizacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cotizacion', function(Cotizacion) {
                            return Cotizacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cotizacion.new', {
            parent: 'cotizacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cotizacion/cotizacion-dialog.html',
                    controller: 'CotizacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                origen: null,
                                destino: null,
                                mercaderia: null,
                                precio: null,
                                moneda: null,
                                estado: null,
                                nombreReceptor: null,
                                cargoReceptor: null,
                                condiciones: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cotizacion', null, { reload: 'cotizacion' });
                }, function() {
                    $state.go('cotizacion');
                });
            }]
        })
        .state('cotizacion.edit', {
            parent: 'cotizacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cotizacion/cotizacion-dialog.html',
                    controller: 'CotizacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cotizacion', function(Cotizacion) {
                            return Cotizacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cotizacion', null, { reload: 'cotizacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cotizacion.delete', {
            parent: 'cotizacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cotizacion/cotizacion-delete-dialog.html',
                    controller: 'CotizacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cotizacion', function(Cotizacion) {
                            return Cotizacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cotizacion', null, { reload: 'cotizacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
