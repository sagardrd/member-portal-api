package com.jaws.app.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PdfGeneratorUtil {

    public byte[] generateAttendancePdf(List<Map<String, Object>> data, Long eventId, String eventName) {

         if (eventName == null) {
            eventName = "Event " + eventId;
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Attendance Report for Event: " + eventName)
                    .setFontSize(16)
                    .setBold()
                    .setMarginBottom(20));

            float[] columnWidths = {30, 120, 250};
            Table table = new Table(UnitValue.createPointArray(columnWidths));

            table.addHeaderCell(createCell("Sr. No.", true));
            table.addHeaderCell(createCell("Membership No.", true));
            table.addHeaderCell(createCell("Name", true));

            int serial = 1;
            for (Map<String, Object> row : data) {
                table.addCell(createCell(String.valueOf(serial++), false));
                table.addCell(createCell(
                        row.getOrDefault("Membership_No", "").toString(), false));
                table.addCell(createCell(
                        row.getOrDefault("Member_Name", "").toString(), false));
            }

            document.add(table);
            document.close();

            log.info("PDF generated successfully for eventId={}", eventId);
            return baos.toByteArray();
            } catch (Exception e) {
            log.error("Error generating PDF for eventId={}", eventId, e);
            throw new RuntimeException("Failed to generate PDF");
        }
    }

    private Cell createCell(String text, boolean isHeader) {
        Cell cell = new Cell().add(new Paragraph(text)).setPadding(5);
        if (isHeader) {
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            cell.setBold();
        }
        return cell;
    }
}