package com.daw.services;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.CuentaCrudRepository;
import com.daw.persistence.crud.DetalleCrudRepository;
import com.daw.persistence.crud.InformeCrudRepository;
import com.daw.persistence.entities.Cuenta;
import com.daw.persistence.entities.Detalle;
import com.daw.persistence.entities.Informe;
import com.daw.persistence.entities.MetodoPago;
import com.daw.services.dto.CuentaDto;
import com.daw.services.dto.ProductoInformeDto;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Chunk;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


@Service
public class InformeService {
	@Autowired CuentaService cuentaService;
	@Autowired
	private CuentaCrudRepository cuentaCrudRepository;
	@Autowired
	private DetalleCrudRepository detalleCrudRepository;
	@Autowired
	private InformeCrudRepository informeCrudRepository;
	
	public List<Informe> findAll(){
		return this.informeCrudRepository.findAll();
	}
	
	public Optional<Informe> findById(int idInforme){
		return this.informeCrudRepository.findById(idInforme);
	}
	
	public boolean existsInforme(int idInforme) {
		return this.informeCrudRepository.existsById(idInforme);
	}
	
	public Informe create(Informe informe) {
		return this.informeCrudRepository.save(informe);
	}
	
	public Informe update(Informe informe) {
		return this.informeCrudRepository.save(informe);
	}
	
	public boolean deleteInforme(int idInforme) {
		boolean result = false;
		if(this.informeCrudRepository.existsById(idInforme)) {
			this.informeCrudRepository.deleteById(idInforme);
			result = true;
		}
		
		return result;
	}
	
	public byte[] generarInforme(LocalDate fechaInforme) throws Exception {
	    List<CuentaDto> cuentasCerradas = cuentaService.getCuentasCerradasHoy();

	    Map<Integer, ProductoInformeDto> resumenProductos = new HashMap<>();
	    double totalGeneral = 0.0;
	    Map<MetodoPago, Double> totalPorMetodoPago = new HashMap<>();
	    Map<MetodoPago, Integer> cantidadVentasPorMetodoPago = new HashMap<>();
	    for (CuentaDto cuenta : cuentasCerradas) {
	        if (cuenta.getTotal() != null && cuenta.getTotal() != 0.0) {
	            totalGeneral += cuenta.getTotal();
	        }
	        MetodoPago metodoPago = cuenta.getMetodoPago();

	        totalPorMetodoPago.put(metodoPago, totalPorMetodoPago.getOrDefault(metodoPago, 0.0) + cuenta.getTotal());
	        cantidadVentasPorMetodoPago.put(metodoPago, cantidadVentasPorMetodoPago.getOrDefault(metodoPago, 0) + 1);

	        List<Detalle> detalles = detalleCrudRepository.findByIdCuenta(cuenta.getId());
	        for (Detalle d : detalles) {
	            Integer idProducto = d.getIdProducto();
	            ProductoInformeDto dto = resumenProductos.computeIfAbsent(idProducto, k -> {
	                ProductoInformeDto p = new ProductoInformeDto();
	                p.setNombre(d.getProducto().getNombre());
	                p.setPrecio(d.getProducto().getPrecio());
	                p.setCantidad(0);
	                p.setSubtotal(0.0);
	                return p;
	            });
	            dto.setCantidad(dto.getCantidad() + d.getCantidad());
	            dto.setSubtotal(dto.getSubtotal() + d.getSubtotal());
	        }
	    }
	    List<ProductoInformeDto> productosVendidos = new ArrayList<>(resumenProductos.values());
	    productosVendidos.sort(Comparator.comparing(ProductoInformeDto::getNombre));

	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Document document = new Document();
	    PdfWriter.getInstance(document, baos);

	    document.open();

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    String fechaFormateada = fechaInforme.format(formatter);

	    document.add(new Paragraph("INFORME DE VENTAS", FontFactory.getFont(FontFactory.TIMES_ROMAN, 16)));
	    document.add(new Paragraph("Fecha: " + fechaFormateada));
	    document.add(Chunk.NEWLINE);

	    PdfPTable tabla = new PdfPTable(4);
	    tabla.setWidthPercentage(100);
	    tabla.addCell("Producto");
	    tabla.addCell("Precio");
	    tabla.addCell("Cantidad");
	    tabla.addCell("Subtotal");

	    for (ProductoInformeDto p : productosVendidos) {
	        tabla.addCell(p.getNombre());
	        tabla.addCell(String.format("%.2f", p.getPrecio()));
	        tabla.addCell(String.valueOf(p.getCantidad()));
	        tabla.addCell(String.format("%.2f", p.getSubtotal()));
	    }
	    document.add(tabla);

	    document.add(Chunk.NEWLINE);

	    document.add(new Paragraph("TOTAL: " + String.format("%.2f", totalGeneral), FontFactory.getFont(FontFactory.TIMES_ROMAN, 16)));

	    document.close();
	    return baos.toByteArray();
	}


	
}
