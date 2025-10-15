package com.ukm.ksr.logistics.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Sistem Pengelolaan Logistik UKM KSR API",
        description = "API untuk sistem pengelolaan logistik dan pengurus harian markas UKM KSR yang mencakup manajemen barang, peminjaman, jadwal jaga medis, dan dashboard statistik.",
        version = "1.0.0",
        contact = @Contact(
            name = "UKM KSR Development Team",
            email = "dev@ukmksr.ac.id",
            url = "https://ukmksr.ac.id"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Development Server"),
        @Server(url = "https://api.ukmksr.ac.id", description = "Production Server")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT Bearer Token Authentication",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}