package com.logistic.hexagonal.application.service;

import com.logistic.hexagonal.application.dto.PaqueteRegistroRequest;
import com.logistic.hexagonal.infrastructure.adapters.ingesta.LectorDeArchivosEstrategy;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelLectorAdapter implements LectorDeArchivosEstrategy {

    // 1. Le enseñamos a responder a qué extensión soporta
    @Override
    public boolean soporta(String extension) {
        return extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls");
    }

    // 2. Este es tu MISMO código de antes, solo le pusimos el @Override encima
    @Override
    public List<PaqueteRegistroRequest> extraer(MultipartFile file) {
        List<PaqueteRegistroRequest> paquetes = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                PaqueteRegistroRequest request = new PaqueteRegistroRequest();
                request.setTrackingId(row.getCell(0).getStringCellValue());
                request.setCategoria(row.getCell(1).getStringCellValue());
                request.setPeso(row.getCell(2).getNumericCellValue());

                paquetes.add(request);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo Excel: " + e.getMessage());
        }

        return paquetes;
    }
}