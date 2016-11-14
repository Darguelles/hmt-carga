(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('condicion-pago', {
            parent: 'entity',
            url: '/condicion-pago?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CondicionPagos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/condicion-pago/condicion-pagos.html',
                    controller: 'CondicionPagoController',
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
        .state('condicion-pago-detail', {
            parent: 'entity',
            url: '/condicion-pago/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CondicionPago'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/condicion-pago/condicion-pago-detail.html',
                    controller: 'CondicionPagoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CondicionPago', function($stateParams, CondicionPago) {
                    return CondicionPago.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'condicion-pago',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('condicion-pago-detail.edit', {
            parent: 'condicion-pago-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/condicion-pago/condicion-pago-dialog.html',
                    controller: 'CondicionPagoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CondicionPago', function(CondicionPago) {
                            return CondicionPago.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('condicion-pago.new', {
            parent: 'condicion-pago',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/condicion-pago/condicion-pago-dialog.html',
                    controller: 'CondicionPagoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('condicion-pago', null, { reload: 'condicion-pago' });
                }, function() {
                    $state.go('condicion-pago');
                });
            }]
        })
        .state('condicion-pago.edit', {
            parent: 'condicion-pago',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/condicion-pago/condicion-pago-dialog.html',
                    controller: 'CondicionPagoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CondicionPago', function(CondicionPago) {
                            return CondicionPago.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('condicion-pago', null, { reload: 'condicion-pago' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('condicion-pago.delete', {
            parent: 'condicion-pago',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/condicion-pago/condicion-pago-delete-dialog.html',
                    controller: 'CondicionPagoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CondicionPago', function(CondicionPago) {
                            return CondicionPago.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('condicion-pago', null, { reload: 'condicion-pago' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
