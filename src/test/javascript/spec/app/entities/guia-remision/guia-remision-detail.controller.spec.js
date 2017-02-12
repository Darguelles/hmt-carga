'use strict';

describe('Controller Tests', function() {

    describe('GuiaRemision Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGuiaRemision, MockCotizacion, MockProveedor, MockTransporte, MockFactura;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGuiaRemision = jasmine.createSpy('MockGuiaRemision');
            MockCotizacion = jasmine.createSpy('MockCotizacion');
            MockProveedor = jasmine.createSpy('MockProveedor');
            MockTransporte = jasmine.createSpy('MockTransporte');
            MockFactura = jasmine.createSpy('MockFactura');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'GuiaRemision': MockGuiaRemision,
                'Cotizacion': MockCotizacion,
                'Proveedor': MockProveedor,
                'Transporte': MockTransporte,
                'Factura': MockFactura
            };
            createController = function() {
                $injector.get('$controller')("GuiaRemisionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hmtcargaApp:guiaRemisionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
