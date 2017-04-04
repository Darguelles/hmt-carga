(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('guia-remision', {
            parent: 'entity',
            url: '/guia-remision?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GuiaRemisions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/guia-remision/guia-remisions.html',
                    controller: 'GuiaRemisionController',
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
        .state('guia-remision-detail', {
            parent: 'entity',
            url: '/guia-remision/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GuiaRemision'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/guia-remision/guia-remision-detail.html',
                    controller: 'GuiaRemisionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GuiaRemision', function($stateParams, GuiaRemision) {
                    return GuiaRemision.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'guia-remision',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('guia-remision-detail.edit', {
            parent: 'guia-remision-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/guia-remision/guia-remision-dialog.html',
                    controller: 'GuiaRemisionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GuiaRemision', function(GuiaRemision) {
                            return GuiaRemision.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('guia-remision.new', {
            parent: 'guia-remision',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/guia-remision/guia-remision-dialog.html',
                    controller: 'GuiaRemisionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codigo: null,
                                fechaEmision: null,
                                fechaTraslado: null,
                                cantidad: null,
                                peso: null,
                                unidadMedida: null,
                                costoMinimo: null,
                                fechaIngreso: null,
                                fechaSalida: null,
                                observaciones: null,
                                descripcion: null,
                                origenDatos: null,
                                facturada: null,
                                guiaRemisionRemitente: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('guia-remision', null, { reload: 'guia-remision' });
                }, function() {
                    $state.go('guia-remision');
                });
            }]
        })
        .state('guia-remision.edit', {
            parent: 'guia-remision',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/guia-remision/guia-remision-dialog.html',
                    controller: 'GuiaRemisionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GuiaRemision', function(GuiaRemision) {
                            return GuiaRemision.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('guia-remision', null, { reload: 'guia-remision' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('guia-remision.delete', {
            parent: 'guia-remision',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/guia-remision/guia-remision-delete-dialog.html',
                    controller: 'GuiaRemisionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GuiaRemision', function(GuiaRemision) {
                            return GuiaRemision.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('guia-remision', null, { reload: 'guia-remision' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
