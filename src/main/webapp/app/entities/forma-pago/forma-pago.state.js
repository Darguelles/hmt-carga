(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('forma-pago', {
            parent: 'entity',
            url: '/forma-pago?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FormaPagos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/forma-pago/forma-pagos.html',
                    controller: 'FormaPagoController',
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
        .state('forma-pago-detail', {
            parent: 'entity',
            url: '/forma-pago/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FormaPago'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/forma-pago/forma-pago-detail.html',
                    controller: 'FormaPagoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FormaPago', function($stateParams, FormaPago) {
                    return FormaPago.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'forma-pago',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('forma-pago-detail.edit', {
            parent: 'forma-pago-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forma-pago/forma-pago-dialog.html',
                    controller: 'FormaPagoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FormaPago', function(FormaPago) {
                            return FormaPago.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('forma-pago.new', {
            parent: 'forma-pago',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forma-pago/forma-pago-dialog.html',
                    controller: 'FormaPagoDialogController',
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
                    $state.go('forma-pago', null, { reload: 'forma-pago' });
                }, function() {
                    $state.go('forma-pago');
                });
            }]
        })
        .state('forma-pago.edit', {
            parent: 'forma-pago',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forma-pago/forma-pago-dialog.html',
                    controller: 'FormaPagoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FormaPago', function(FormaPago) {
                            return FormaPago.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('forma-pago', null, { reload: 'forma-pago' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('forma-pago.delete', {
            parent: 'forma-pago',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forma-pago/forma-pago-delete-dialog.html',
                    controller: 'FormaPagoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FormaPago', function(FormaPago) {
                            return FormaPago.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('forma-pago', null, { reload: 'forma-pago' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
