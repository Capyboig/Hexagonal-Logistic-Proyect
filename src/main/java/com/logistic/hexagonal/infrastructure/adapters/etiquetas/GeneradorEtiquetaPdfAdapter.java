package com.logistic.hexagonal.infrastructure.adapters.etiquetas; // Ajusta tu paquete

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.logistic.hexagonal.domain.model.Paquete;
import com.logistic.hexagonal.domain.port.GeneradorEtiquetaPort;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class GeneradorEtiquetaPdfAdapter implements GeneradorEtiquetaPort {

    @Override
    public byte[] generarEtiquetaPdf(Paquete paquete) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);


            String datosQr = "{\"idPaquete\": " + paquete.getId() + "}";
            BufferedImage qrImage = generarImagenQr(datosQr);
            PDImageXObject pdQrImage = LosslessFactory.createFromImage(document, qrImage);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                contentStream.newLineAtOffset(20, PDRectangle.A6.getHeight() - 40);
                contentStream.showText("Logística Express - Albarán");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(20, PDRectangle.A6.getHeight() - 70);
                contentStream.showText("ID Paquete: " + paquete.getId());
                contentStream.endText();

                contentStream.drawImage(pdQrImage, 50, PDRectangle.A6.getHeight() - 280, 200, 200);
            }

            document.save(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error crítico generando el PDF del paquete: " + paquete.getId(), e);
        }
    }

    private BufferedImage generarImagenQr(String datos) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(datos, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}