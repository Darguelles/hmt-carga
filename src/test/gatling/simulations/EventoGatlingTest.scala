import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Evento entity.
 */
class EventoGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "X-XSRF-TOKEN" -> "${xsrf_token}"
    )

    val scn = scenario("Test the Evento entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authentication")
        .headers(headers_http_authenticated)
        .formParam("j_username", "admin")
        .formParam("j_password", "admin")
        .formParam("remember-me", "true")
        .formParam("submit", "Login")
        .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all eventos")
            .get("/api/eventos")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new evento")
            .post("/api/eventos")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "numeroOrdenVenta":"SAMPLE_TEXT", "clienteGuia":"SAMPLE_TEXT", "clienteFactura":"SAMPLE_TEXT", "clienteRuc":"SAMPLE_TEXT", "clienteRazonSocial":"SAMPLE_TEXT", "clientePeso":"SAMPLE_TEXT", "clienteMonto":null, "clienteTotal":null, "clienteAdelanto":null, "fechaEmisionFactura":"2020-01-01T00:00:00.000Z", "fechaVencimientoFactura":"2020-01-01T00:00:00.000Z", "plazo":"SAMPLE_TEXT", "fechaEntrega":"2020-01-01T00:00:00.000Z", "letra":"SAMPLE_TEXT", "estadoLetra":"SAMPLE_TEXT", "fechaEmisionLetra":"2020-01-01T00:00:00.000Z", "plazoPago":"SAMPLE_TEXT", "vencimientoPago":"2020-01-01T00:00:00.000Z", "saldoCobrar":null, "montoDetraccion":null, "clienteEstado":"SAMPLE_TEXT", "proveedorGuia":"SAMPLE_TEXT", "proveedorFactura":"SAMPLE_TEXT", "proveedorRuc":"SAMPLE_TEXT", "proveedorRazonSocial":"SAMPLE_TEXT", "materialTransporte":"SAMPLE_TEXT", "fechaSalida":"2020-01-01T00:00:00.000Z", "montoServicio":null, "total":null, "adelanto1":null, "fechaAdelanto1":"2020-01-01T00:00:00.000Z", "adelanto2":null, "fechaAdelanto2":"2020-01-01T00:00:00.000Z", "adelanto3":null, "fechaAdelanto3":"2020-01-01T00:00:00.000Z", "adelanto4":null, "fechaAdelanto4":"2020-01-01T00:00:00.000Z", "fechaPagoSaldo":"2020-01-01T00:00:00.000Z", "proveedorMontoDetraccion":null, "proveedorComisionDetraccion":null, "proveedorTotalPagar":null, "proveedorEstado":"SAMPLE_TEXT", "compraFactura":"SAMPLE_TEXT", "compraProveedor":"SAMPLE_TEXT", "compraProducto":"SAMPLE_TEXT", "compraTotal":null, "compraTotalIgv":null, "compraEstado":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_evento_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created evento")
                .get("${new_evento_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created evento")
            .delete("${new_evento_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
