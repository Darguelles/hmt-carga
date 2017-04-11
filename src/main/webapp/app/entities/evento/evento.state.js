(function() {
    'use strict';

    angular
        .module('hmtcargaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('evento', {
            parent: 'entity',
            url: '/evento?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Eventos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evento/eventos.html',
                    controller: 'EventoController',
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
        .state('evento-detail', {
            parent: 'entity',
            url: '/evento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Evento'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evento/evento-detail.html',
                    controller: 'EventoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Evento', function($stateParams, Evento) {
                    return Evento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'evento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('evento-detail.edit', {
            parent: 'evento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evento/evento-dialog.html',
                    controller: 'EventoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Evento', function(Evento) {
                            return Evento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evento.new', {
            parent: 'evento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evento/evento-dialog.html',
                    controller: 'EventoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numeroOrdenVenta: null,
                                clienteGuia: null,
                                clienteFactura: null,
                                clienteRuc: null,
                                clienteRazonSocial: null,
                                clientePeso: null,
                                clienteMonto: null,
                                clienteTotal: null,
                                clienteAdelanto: null,
                                fechaEmisionFactura: null,
                                fechaVencimientoFactura: null,
                                plazo: null,
                                fechaEntrega: null,
                                letra: null,
                                estadoLetra: null,
                                fechaEmisionLetra: null,
                                plazoPago: null,
                                vencimientoPago: null,
                                saldoCobrar: null,
                                montoDetraccion: null,
                                clienteEstado: null,
                                proveedorGuia: null,
                                proveedorFactura: null,
                                proveedorRuc: null,
                                proveedorRazonSocial: null,
                                materialTransporte: null,
                                fechaSalida: null,
                                montoServicio: null,
                                total: null,
                                adelanto1: null,
                                fechaAdelanto1: null,
                                adelanto2: null,
                                fechaAdelanto2: null,
                                adelanto3: null,
                                fechaAdelanto3: null,
                                adelanto4: null,
                                fechaAdelanto4: null,
                                fechaPagoSaldo: null,
                                proveedorMontoDetraccion: null,
                                proveedorComisionDetraccion: null,
                                proveedorTotalPagar: null,
                                proveedorEstado: null,
                                compraFactura: null,
                                compraProveedor: null,
                                compraProducto: null,
                                compraTotal: null,
                                compraTotalIgv: null,
                                compraEstado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('evento', null, { reload: 'evento' });
                }, function() {
                    $state.go('evento');
                });
            }]
        })
        .state('evento.edit', {
            parent: 'evento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evento/evento-dialog.html',
                    controller: 'EventoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Evento', function(Evento) {
                            return Evento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evento', null, { reload: 'evento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evento.delete', {
            parent: 'evento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evento/evento-delete-dialog.html',
                    controller: 'EventoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Evento', function(Evento) {
                            return Evento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evento', null, { reload: 'evento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
