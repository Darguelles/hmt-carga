(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-unidad', {
            parent: 'entity',
            url: '/tipo-unidad?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoUnidads'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-unidad/tipo-unidads.html',
                    controller: 'TipoUnidadController',
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
        .state('tipo-unidad-detail', {
            parent: 'entity',
            url: '/tipo-unidad/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoUnidad'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-unidad/tipo-unidad-detail.html',
                    controller: 'TipoUnidadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TipoUnidad', function($stateParams, TipoUnidad) {
                    return TipoUnidad.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-unidad',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-unidad-detail.edit', {
            parent: 'tipo-unidad-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-unidad/tipo-unidad-dialog.html',
                    controller: 'TipoUnidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoUnidad', function(TipoUnidad) {
                            return TipoUnidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-unidad.new', {
            parent: 'tipo-unidad',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-unidad/tipo-unidad-dialog.html',
                    controller: 'TipoUnidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                configuracion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-unidad', null, { reload: 'tipo-unidad' });
                }, function() {
                    $state.go('tipo-unidad');
                });
            }]
        })
        .state('tipo-unidad.edit', {
            parent: 'tipo-unidad',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-unidad/tipo-unidad-dialog.html',
                    controller: 'TipoUnidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoUnidad', function(TipoUnidad) {
                            return TipoUnidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-unidad', null, { reload: 'tipo-unidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-unidad.delete', {
            parent: 'tipo-unidad',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-unidad/tipo-unidad-delete-dialog.html',
                    controller: 'TipoUnidadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoUnidad', function(TipoUnidad) {
                            return TipoUnidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-unidad', null, { reload: 'tipo-unidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
