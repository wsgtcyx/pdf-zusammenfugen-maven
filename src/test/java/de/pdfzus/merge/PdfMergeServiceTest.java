package de.pdfzus.merge;

import de.pdfzus.cli.PdfZusammenfuegenCli;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PdfMergeServiceTest {

  @TempDir
  Path tempDir;

  @Test
  void merge_shouldCreateMergedPdfWithCorrectPageCount() throws IOException {
    Path first = createSinglePagePdf("eins.pdf");
    Path second = createSinglePagePdf("zwei.pdf");
    Path output = tempDir.resolve("merged.pdf");

    new PdfMergeService().merge(Arrays.asList(first, second), output);

    assertTrue(output.toFile().exists(), "Die Ausgabedatei wurde nicht erzeugt.");

    try (PDDocument document = PDDocument.load(output.toFile())) {
      assertEquals(2, document.getNumberOfPages(), "Die zusammengefuegte PDF muss zwei Seiten enthalten.");
    }
  }

  @Test
  void merge_shouldFailWhenInputIsMissing() {
    Path missing = tempDir.resolve("fehlt.pdf");
    Path output = tempDir.resolve("merged.pdf");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new PdfMergeService().merge(Collections.singletonList(missing), output)
    );

    assertTrue(exception.getMessage().contains("Eingabedatei nicht gefunden"));
  }

  @Test
  void merge_shouldFailWhenInputListIsEmpty() {
    Path output = tempDir.resolve("merged.pdf");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new PdfMergeService().merge(Collections.<Path>emptyList(), output)
    );

    assertTrue(exception.getMessage().contains("Mindestens eine Eingabe-PDF"));
  }

  @Test
  void cli_shouldPrintHelpWithHelpFlag() {
    ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
    ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();

    int exitCode = PdfZusammenfuegenCli.run(
        new String[] {"--help"},
        new PrintStream(outBuffer),
        new PrintStream(errBuffer)
    );

    assertEquals(0, exitCode);
    assertTrue(outBuffer.toString().contains("pdf-zusammenfuegen --output"));
  }

  private Path createSinglePagePdf(String fileName) throws IOException {
    Path file = tempDir.resolve(fileName);

    try (PDDocument document = new PDDocument()) {
      document.addPage(new PDPage());
      document.save(file.toFile());
    }

    return file;
  }
}
