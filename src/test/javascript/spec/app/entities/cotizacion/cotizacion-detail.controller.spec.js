'use strict';

describe('Controller Tests', function() {

    describe('Cotizacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCotizacion, MockCliente, MockServicio, MockTipoUnidad;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCotizacion = jasmine.createSpy('MockCotizacion');
            MockCliente = jasmine.createSpy('MockCliente');
            MockServicio = jasmine.createSpy('MockServicio');
            MockTipoUnidad = jasmine.createSpy('MockTipoUnidad');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Cotizacion': MockCotizacion,
                'Cliente': MockCliente,
                'Servicio': MockServicio,
                'TipoUnidad': MockTipoUnidad
            };
            createController = function() {
                $injector.get('$controller')("CotizacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hmtcargaApp:cotizacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
