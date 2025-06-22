package com.jofaze.backendjofaze;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.jofaze.backendjofaze.model.Administrador;
import com.jofaze.backendjofaze.model.Cliente;
import com.jofaze.backendjofaze.model.Region;
import com.jofaze.backendjofaze.model.Comuna;
import com.jofaze.backendjofaze.model.TipoEstablecimiento;
import com.jofaze.backendjofaze.model.Establecimiento;
import com.jofaze.backendjofaze.repository.AdministradorRepository;
import com.jofaze.backendjofaze.repository.ClienteRepository;
import com.jofaze.backendjofaze.repository.RegionRepository;
import com.jofaze.backendjofaze.repository.ComunaRepository;
import com.jofaze.backendjofaze.repository.TipoEstablecimientoRepository;
import com.jofaze.backendjofaze.repository.EstablecimientoRepository;

import net.datafaker.Faker;

import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ComunaRepository comunaRepository;
    @Autowired
    private TipoEstablecimientoRepository tipoEstablecimientoRepository;
    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        // Crear regiones
        for (int i = 0; i < 3; i++) {
            Region region = new Region();
            region.setNombre(faker.address().state());
            regionRepository.save(region);
        }

        // Crear comunas
        var regiones = regionRepository.findAll();
        for (int i = 0; i < 5; i++) {
            Comuna comuna = new Comuna();
            comuna.setNombre(faker.address().cityName());
            comuna.setRegion(regiones.get(random.nextInt(regiones.size())));
            comunaRepository.save(comuna);
        }

        // Crear tipos de establecimiento
        for (int i = 0; i < 3; i++) {
            TipoEstablecimiento tipo = new TipoEstablecimiento();
            tipo.setDescripcion(faker.company().industry());
            tipoEstablecimientoRepository.save(tipo);
        }

        // Crear establecimientos
        var comunas = comunaRepository.findAll();
        var tipos = tipoEstablecimientoRepository.findAll();
        for (int i = 0; i < 5; i++) {
            Establecimiento est = new Establecimiento();
            est.setNombre(faker.company().name());
            est.setDireccion(faker.address().streetAddress());
            est.setComuna(comunas.get(random.nextInt(comunas.size())));
            est.setTipoEstablecimiento(tipos.get(random.nextInt(tipos.size())));
            est.setEmail(faker.internet().emailAddress());                              
            est.setTelefono(faker.phoneNumber().cellPhone()); 
            establecimientoRepository.save(est);
        }

        // Crear administradores
        var establecimientos = establecimientoRepository.findAll();
        for (int i = 0; i < 3; i++) {
            Administrador admin = new Administrador();
            admin.setNombre(faker.name().fullName());
            admin.setCargo(faker.job().position());
            admin.setEmail(faker.internet().emailAddress());
            admin.setTelefono(faker.phoneNumber().cellPhone());
            admin.setEstablecimiento(establecimientos.get(random.nextInt(establecimientos.size())));
            administradorRepository.save(admin);
        }

        // Crear clientes
        for (int i = 0; i < 10; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombre(faker.name().firstName());
            cliente.setApellido(faker.name().lastName());
            cliente.setTelefono(faker.phoneNumber().cellPhone());
            cliente.setEmail(faker.internet().emailAddress()); // <-- Añadir esta línea
            clienteRepository.save(cliente);
        }
    }
}