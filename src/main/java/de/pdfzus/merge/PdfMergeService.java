package de.pdfzus.merge;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Service fuer das lokale Zusammenfuegen mehrerer PDF-Dateien.
 */
public final class PdfMergeService {

  /**
   * Fuegt mehrere PDF-Dateien in eine Zieldatei zusammen.
   *
   * @param inputFiles Liste der Eingabe-PDFs
   * @param outputFile Ziel-PDF
   */
  public void merge(List<Path> inputFiles, Path outputFile) {
    if (inputFiles == null || inputFiles.isEmpty()) {
      throw new IllegalArgumentException("Mindestens eine Eingabe-PDF ist erforderlich.");
    }
    if (outputFile == null) {
      throw new IllegalArgumentException("Die Ausgabedatei darf nicht null sein.");
    }

    for (Path input : inputFiles) {
      if (input == null) {
        throw new IllegalArgumentException("Eine Eingabedatei ist null.");
      }
      if (!Files.exists(input) || !Files.isRegularFile(input)) {
        throw new IllegalArgumentException("Eingabedatei nicht gefunden: " + input);
      }
    }

    try {
      Path parent = outputFile.toAbsolutePath().getParent();
      if (parent != null) {
        Files.createDirectories(parent);
      }

      PDFMergerUtility merger = new PDFMergerUtility();
      for (Path input : inputFiles) {
        merger.addSource(input.toFile());
      }

      merger.setDestinationFileName(outputFile.toAbsolutePath().toString());
      merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    } catch (IOException exception) {
      throw new IllegalStateException("PDF-Zusammenfuegen fehlgeschlagen: " + exception.getMessage(), exception);
    }
  }
}
