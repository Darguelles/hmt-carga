(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('transporte', {
            parent: 'entity',
            url: '/transporte?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Transportes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transporte/transportes.html',
                    controller: 'TransporteController',
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
        .state('transporte-detail', {
            parent: 'entity',
            url: '/transporte/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Transporte'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transporte/transporte-detail.html',
                    controller: 'TransporteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Transporte', function($stateParams, Transporte) {
                    return Transporte.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'transporte',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('transporte-detail.edit', {
            parent: 'transporte-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transporte/transporte-dialog.html',
                    controller: 'TransporteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Transporte', function(Transporte) {
                            return Transporte.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transporte.new', {
            parent: 'transporte',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transporte/transporte-dialog.html',
                    controller: 'TransporteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                marca: null,
                                tracto: null,
                                carreta: null,
                                placaTracto: null,
                                placaCarreta: null,
                                largoCarreta: null,
                                anchoCarreta: null,
                                altoCarreta: null,
                                cargaUtil: null,
                                registroMatpel: null,
                                gps: null,
                                anoFabricacion: null,
                                unidadPropia: null,
                                kilometraje: null,
                                fechaRevisionTecnica: null,
                                soat: null,
                                fechaVencimientoSoat: null,
                                modelo: null,
                                nombreConductor: null,
                                licenciaConductor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('transporte', null, { reload: 'transporte' });
                }, function() {
                    $state.go('transporte');
                });
            }]
        })
        .state('transporte.edit', {
            parent: 'transporte',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transporte/transporte-dialog.html',
                    controller: 'TransporteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Transporte', function(Transporte) {
                            return Transporte.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transporte', null, { reload: 'transporte' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transporte.delete', {
            parent: 'transporte',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transporte/transporte-delete-dialog.html',
                    controller: 'TransporteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Transporte', function(Transporte) {
                            return Transporte.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transporte', null, { reload: 'transporte' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
