package proyectointegrador.Main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import proyectointegrador.Entities.DestinoEnum;
import proyectointegrador.Entities.EscrituraNotarial;
import proyectointegrador.Entities.Propiedad;
import proyectointegrador.Service.EscrituraNotarialService;
import proyectointegrador.Service.PropiedadService;

/**
 * Menú de consola para operaciones CRUD
 */
public class AppMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final PropiedadService propiedadService = new PropiedadService();
    private final EscrituraNotarialService escrituraService = new EscrituraNotarialService();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine().trim().toUpperCase();

            try {
                switch (opcion) {
                    case "1" -> menuPropiedad();
                    case "2" -> menuEscritura();
                    case "S" -> {
                        continuar = false;
                        System.out.println("\n¡Hasta luego!");
                    }
                    default -> System.out.println("\n❌ Opción inválida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        SISTEMA DE GESTIÓN DE PROPIEDADES");
        System.out.println("=".repeat(50));
        System.out.println("1. Gestionar Propiedades");
        System.out.println("2. Gestionar Escrituras Notariales");
        System.out.println("S. Salir");
        System.out.print("\nSeleccione una opción: ");
    }

    private void menuPropiedad() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("        MENÚ DE PROPIEDADES");
            System.out.println("-".repeat(50));
            System.out.println("1. Crear Propiedad");
            System.out.println("2. Buscar Propiedad por ID");
            System.out.println("3. Buscar Propiedad por Padrón Catastral");
            System.out.println("4. Listar Todas las Propiedades");
            System.out.println("5. Actualizar Propiedad");
            System.out.println("6. Eliminar Propiedad (lógico)");
            System.out.println("V. Volver al menú principal");
            System.out.print("\nSeleccione una opción: ");

            String opcion = scanner.nextLine().trim().toUpperCase();

            try {
                switch (opcion) {
                    case "1" -> crearPropiedad();
                    case "2" -> buscarPropiedadPorId();
                    case "3" -> buscarPropiedadPorPadron();
                    case "4" -> listarPropiedades();
                    case "5" -> actualizarPropiedad();
                    case "6" -> eliminarPropiedad();
                    case "V" -> continuar = false;
                    default -> System.out.println("\n❌ Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage());
            }
        }
    }

    private void menuEscritura() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("        MENÚ DE ESCRITURAS NOTARIALES");
            System.out.println("-".repeat(50));
            System.out.println("1. Crear Escritura Notarial");
            System.out.println("2. Buscar Escritura por ID");
            System.out.println("3. Buscar Escritura por Número");
            System.out.println("4. Listar Todas las Escrituras");
            System.out.println("5. Actualizar Escritura");
            System.out.println("6. Eliminar Escritura (lógico)");
            System.out.println("V. Volver al menú principal");
            System.out.print("\nSeleccione una opción: ");

            String opcion = scanner.nextLine().trim().toUpperCase();

            try {
                switch (opcion) {
                    case "1" -> crearEscritura();
                    case "2" -> buscarEscrituraPorId();
                    case "3" -> buscarEscrituraPorNumero();
                    case "4" -> listarEscrituras();
                    case "5" -> actualizarEscritura();
                    case "6" -> eliminarEscritura();
                    case "V" -> continuar = false;
                    default -> System.out.println("\n❌ Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage());
            }
        }
    }

    private void crearPropiedad() {
        System.out.println("\n--- CREAR PROPIEDAD ---");
        
        Propiedad p = new Propiedad();
        
        System.out.print("Padrón Catastral: ");
        String padron = scanner.nextLine().trim().toUpperCase();
        if (padron.isEmpty()) {
            System.out.println("❌ El padrón catastral es obligatorio.");
            return;
        }
        p.setPadronCatastral(padron);

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine().trim();
        if (direccion.isEmpty()) {
            System.out.println("❌ La dirección es obligatoria.");
            return;
        }
        p.setDireccion(direccion);

        System.out.print("Superficie (m²): ");
        String superficieStr = scanner.nextLine().trim();
        try {
            BigDecimal superficie = new BigDecimal(superficieStr);
            p.setSuperficieM2(superficie);
        } catch (NumberFormatException e) {
            System.out.println("❌ La superficie debe ser un número válido.");
            return;
        }

        System.out.print("Destino (RES/COM): ");
        String destinoStr = scanner.nextLine().trim().toUpperCase();
        try {
            p.setDestino(DestinoEnum.valueOf(destinoStr));
        } catch (IllegalArgumentException e) {
            System.out.println("❌ El destino debe ser RES o COM.");
            return;
        }

        System.out.print("Antigüedad (años): ");
        String antiguedadStr = scanner.nextLine().trim();
        try {
            p.setAntiguedad(Integer.parseInt(antiguedadStr));
        } catch (NumberFormatException e) {
            System.out.println("❌ La antigüedad debe ser un número entero.");
            return;
        }

        System.out.print("¿Desea asociar una escritura notarial? (S/N): ");
        String asociar = scanner.nextLine().trim().toUpperCase();
        if ("S".equals(asociar)) {
            System.out.print("ID de la escritura existente (o 0 para crear nueva): ");
            String escrituraIdStr = scanner.nextLine().trim();
            try {
                Long escrituraId = Long.parseLong(escrituraIdStr);
                if (escrituraId == 0) {
                    EscrituraNotarial nuevaEscritura = leerDatosEscritura();
                    if (nuevaEscritura != null) {
                        try {
                            nuevaEscritura = escrituraService.insertar(nuevaEscritura);
                            p.setEscrituraNotarial(nuevaEscritura);
                        } catch (Exception ex) {
                            System.out.println("❌ Error al crear escritura: " + ex.getMessage());
                            return;
                        }
                    }
                } else {
                    try {
                        EscrituraNotarial escritura = escrituraService.getById(escrituraId);
                        if (escritura != null) {
                            p.setEscrituraNotarial(escritura);
                        } else {
                            System.out.println("❌ No se encontró la escritura con ID: " + escrituraId);
                            return;
                        }
                    } catch (Exception ex) {
                        System.out.println("❌ Error al buscar escritura: " + ex.getMessage());
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ ID inválido.");
                return;
            }
        }

        try {
            Propiedad creada = propiedadService.insertar(p);
            System.out.println("\n✅ Propiedad creada exitosamente con ID: " + creada.getId());
        } catch (Exception e) {
            System.out.println("\n❌ Error al crear propiedad: " + e.getMessage());
        }
    }

    private void buscarPropiedadPorId() {
        System.out.println("\n--- BUSCAR PROPIEDAD POR ID ---");
        System.out.print("Ingrese el ID: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            Long id = Long.parseLong(idStr);
            Propiedad p = propiedadService.getById(id);
            if (p != null) {
                mostrarPropiedad(p);
            } else {
                System.out.println("\n❌ No se encontró la propiedad con ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido.");
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void buscarPropiedadPorPadron() {
        System.out.println("\n--- BUSCAR PROPIEDAD POR PADRÓN CATASTRAL ---");
        System.out.print("Ingrese el padrón catastral: ");
        String padron = scanner.nextLine().trim().toUpperCase();
        
        try {
            Propiedad p = propiedadService.buscarPorPadronCatastral(padron);
            if (p != null) {
                mostrarPropiedad(p);
            } else {
                System.out.println("\n❌ No se encontró la propiedad con padrón: " + padron);
            }
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void listarPropiedades() {
        System.out.println("\n--- LISTADO DE PROPIEDADES ---");
        try {
            List<Propiedad> propiedades = propiedadService.getAll();
            if (propiedades.isEmpty()) {
                System.out.println("\nNo hay propiedades registradas.");
            } else {
                propiedades.forEach(this::mostrarPropiedad);
            }
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void actualizarPropiedad() {
        System.out.println("\n--- ACTUALIZAR PROPIEDAD ---");
        System.out.print("Ingrese el ID de la propiedad a actualizar: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            Long id = Long.parseLong(idStr);
            Propiedad p = propiedadService.getById(id);
            if (p == null) {
                System.out.println("\n❌ No se encontró la propiedad con ID: " + id);
                return;
            }

            System.out.println("\nPropiedad actual:");
            mostrarPropiedad(p);
            System.out.println("\nIngrese los nuevos datos (presione Enter para mantener el valor actual):");

            System.out.print("Padrón Catastral [" + p.getPadronCatastral() + "]: ");
            String padron = scanner.nextLine().trim().toUpperCase();
            if (!padron.isEmpty()) {
                p.setPadronCatastral(padron);
            }

            System.out.print("Dirección [" + p.getDireccion() + "]: ");
            String direccion = scanner.nextLine().trim();
            if (!direccion.isEmpty()) {
                p.setDireccion(direccion);
            }

            System.out.print("Superficie (m²) [" + p.getSuperficieM2() + "]: ");
            String superficieStr = scanner.nextLine().trim();
            if (!superficieStr.isEmpty()) {
                try {
                    p.setSuperficieM2(new BigDecimal(superficieStr));
                } catch (NumberFormatException e) {
                    System.out.println("❌ Superficie inválida, se mantiene el valor actual.");
                }
            }

            System.out.print("Destino (RES/COM) [" + p.getDestino() + "]: ");
            String destinoStr = scanner.nextLine().trim().toUpperCase();
            if (!destinoStr.isEmpty()) {
                try {
                    p.setDestino(DestinoEnum.valueOf(destinoStr));
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Destino inválido, se mantiene el valor actual.");
                }
            }

            System.out.print("Antigüedad (años) [" + p.getAntiguedad() + "]: ");
            String antiguedadStr = scanner.nextLine().trim();
            if (!antiguedadStr.isEmpty()) {
                try {
                    p.setAntiguedad(Integer.parseInt(antiguedadStr));
                } catch (NumberFormatException e) {
                    System.out.println("❌ Antigüedad inválida, se mantiene el valor actual.");
                }
            }

            propiedadService.actualizar(p);
            System.out.println("\n✅ Propiedad actualizada exitosamente.");

        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido.");
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void eliminarPropiedad() {
        System.out.println("\n--- ELIMINAR PROPIEDAD ---");
        System.out.print("Ingrese el ID de la propiedad a eliminar: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            Long id = Long.parseLong(idStr);
            System.out.print("¿Está seguro de eliminar la propiedad con ID " + id + "? (S/N): ");
            String confirmar = scanner.nextLine().trim().toUpperCase();
            if ("S".equals(confirmar)) {
                propiedadService.eliminar(id);
                System.out.println("\n✅ Propiedad eliminada exitosamente.");
            } else {
                System.out.println("\nOperación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido.");
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void crearEscritura() {
        System.out.println("\n--- CREAR ESCRITURA NOTARIAL ---");
        
        EscrituraNotarial e = leerDatosEscritura();
        if (e != null) {
            try {
                EscrituraNotarial creada = escrituraService.insertar(e);
                System.out.println("\n✅ Escritura creada exitosamente con ID: " + creada.getId());
            } catch (Exception ex) {
                System.out.println("\n❌ Error al crear escritura: " + ex.getMessage());
            }
        }
    }

    private EscrituraNotarial leerDatosEscritura() {
        EscrituraNotarial e = new EscrituraNotarial();

        System.out.print("Número de Escritura: ");
        String nro = scanner.nextLine().trim().toUpperCase();
        if (nro.isEmpty()) {
            System.out.println("❌ El número de escritura es obligatorio.");
            return null;
        }
        e.setNroEscritura(nro);

        System.out.print("Fecha (yyyy-MM-dd): ");
        String fechaStr = scanner.nextLine().trim();
        try {
            e.setFecha(LocalDate.parse(fechaStr, dateFormatter));
        } catch (DateTimeParseException ex) {
            System.out.println("❌ Fecha inválida. Formato esperado: yyyy-MM-dd");
            return null;
        }

        System.out.print("Notaría: ");
        String notaria = scanner.nextLine().trim();
        if (notaria.isEmpty()) {
            System.out.println("❌ La notaría es obligatoria.");
            return null;
        }
        e.setNotaria(notaria);

        System.out.print("Tomo: ");
        String tomo = scanner.nextLine().trim();
        e.setTomo(tomo.isEmpty() ? null : tomo);

        System.out.print("Folio: ");
        String folio = scanner.nextLine().trim();
        e.setFolio(folio.isEmpty() ? null : folio);

        System.out.print("Observaciones: ");
        String obs = scanner.nextLine().trim();
        e.setObservaciones(obs.isEmpty() ? null : obs);

        return e;
    }

    private void buscarEscrituraPorId() {
        System.out.println("\n--- BUSCAR ESCRITURA POR ID ---");
        System.out.print("Ingrese el ID: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            Long id = Long.parseLong(idStr);
            EscrituraNotarial e = escrituraService.getById(id);
            if (e != null) {
                mostrarEscritura(e);
            } else {
                System.out.println("\n❌ No se encontró la escritura con ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido.");
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void buscarEscrituraPorNumero() {
        System.out.println("\n--- BUSCAR ESCRITURA POR NÚMERO ---");
        System.out.print("Ingrese el número de escritura: ");
        String nro = scanner.nextLine().trim().toUpperCase();
        
        try {
            EscrituraNotarial e = escrituraService.buscarPorNroEscritura(nro);
            if (e != null) {
                mostrarEscritura(e);
            } else {
                System.out.println("\n❌ No se encontró la escritura con número: " + nro);
            }
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void listarEscrituras() {
        System.out.println("\n--- LISTADO DE ESCRITURAS NOTARIALES ---");
        try {
            List<EscrituraNotarial> escrituras = escrituraService.getAll();
            if (escrituras.isEmpty()) {
                System.out.println("\nNo hay escrituras registradas.");
            } else {
                escrituras.forEach(this::mostrarEscritura);
            }
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void actualizarEscritura() {
        System.out.println("\n--- ACTUALIZAR ESCRITURA ---");
        System.out.print("Ingrese el ID de la escritura a actualizar: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            Long id = Long.parseLong(idStr);
            EscrituraNotarial e = escrituraService.getById(id);
            if (e == null) {
                System.out.println("\n❌ No se encontró la escritura con ID: " + id);
                return;
            }

            System.out.println("\nEscritura actual:");
            mostrarEscritura(e);
            System.out.println("\nIngrese los nuevos datos (presione Enter para mantener el valor actual):");

            System.out.print("Número de Escritura [" + e.getNroEscritura() + "]: ");
            String nro = scanner.nextLine().trim().toUpperCase();
            if (!nro.isEmpty()) {
                e.setNroEscritura(nro);
            }

            System.out.print("Fecha (yyyy-MM-dd) [" + e.getFecha() + "]: ");
            String fechaStr = scanner.nextLine().trim();
            if (!fechaStr.isEmpty()) {
                try {
                    e.setFecha(LocalDate.parse(fechaStr, dateFormatter));
                } catch (DateTimeParseException ex) {
                    System.out.println("❌ Fecha inválida, se mantiene el valor actual.");
                }
            }

            System.out.print("Notaría [" + e.getNotaria() + "]: ");
            String notaria = scanner.nextLine().trim();
            if (!notaria.isEmpty()) {
                e.setNotaria(notaria);
            }

            System.out.print("Tomo [" + (e.getTomo() != null ? e.getTomo() : "") + "]: ");
            String tomo = scanner.nextLine().trim();
            if (!tomo.isEmpty()) {
                e.setTomo(tomo);
            }

            System.out.print("Folio [" + (e.getFolio() != null ? e.getFolio() : "") + "]: ");
            String folio = scanner.nextLine().trim();
            if (!folio.isEmpty()) {
                e.setFolio(folio);
            }

            System.out.print("Observaciones [" + (e.getObservaciones() != null ? e.getObservaciones() : "") + "]: ");
            String obs = scanner.nextLine().trim();
            if (!obs.isEmpty()) {
                e.setObservaciones(obs);
            }

            escrituraService.actualizar(e);
            System.out.println("\n✅ Escritura actualizada exitosamente.");

        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido.");
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void eliminarEscritura() {
        System.out.println("\n--- ELIMINAR ESCRITURA ---");
        System.out.print("Ingrese el ID de la escritura a eliminar: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            Long id = Long.parseLong(idStr);
            System.out.print("¿Está seguro de eliminar la escritura con ID " + id + "? (S/N): ");
            String confirmar = scanner.nextLine().trim().toUpperCase();
            if ("S".equals(confirmar)) {
                escrituraService.eliminar(id);
                System.out.println("\n✅ Escritura eliminada exitosamente.");
            } else {
                System.out.println("\nOperación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido.");
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private void mostrarPropiedad(Propiedad p) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ID: " + p.getId());
        System.out.println("Padrón Catastral: " + p.getPadronCatastral());
        System.out.println("Dirección: " + p.getDireccion());
        System.out.println("Superficie: " + p.getSuperficieM2() + " m²");
        System.out.println("Destino: " + p.getDestino());
        System.out.println("Antigüedad: " + p.getAntiguedad() + " años");
        if (p.getEscrituraNotarial() != null) {
            System.out.println("Escritura Notarial ID: " + p.getEscrituraNotarial().getId());
            System.out.println("  - Número: " + p.getEscrituraNotarial().getNroEscritura());
            System.out.println("  - Fecha: " + p.getEscrituraNotarial().getFecha());
            System.out.println("  - Notaría: " + p.getEscrituraNotarial().getNotaria());
        } else {
            System.out.println("Escritura Notarial: No asociada");
        }
        System.out.println("=".repeat(60));
    }

    private void mostrarEscritura(EscrituraNotarial e) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ID: " + e.getId());
        System.out.println("Número de Escritura: " + e.getNroEscritura());
        System.out.println("Fecha: " + e.getFecha());
        System.out.println("Notaría: " + e.getNotaria());
        System.out.println("Tomo: " + (e.getTomo() != null ? e.getTomo() : "N/A"));
        System.out.println("Folio: " + (e.getFolio() != null ? e.getFolio() : "N/A"));
        System.out.println("Observaciones: " + (e.getObservaciones() != null ? e.getObservaciones() : "N/A"));
        System.out.println("=".repeat(60));
    }
}

