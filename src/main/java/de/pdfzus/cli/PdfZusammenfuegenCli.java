package de.pdfzus.cli;

import de.pdfzus.merge.PdfMergeService;

import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Einfaches CLI fuer das lokale Zusammenfuegen von PDFs.
 */
public final class PdfZusammenfuegenCli {

  private PdfZusammenfuegenCli() {
  }

  public static void main(String[] args) {
    int exitCode = run(args, System.out, System.err);
    System.exit(exitCode);
  }

  public static int run(String[] args, PrintStream out, PrintStream err) {
    if (args == null || args.length == 0) {
      err.println("Fehler: Keine Argumente angegeben.");
      printUsage(err);
      return 2;
    }

    if (containsHelp(args)) {
      printUsage(out);
      return 0;
    }

    Path output = null;
    List<Path> inputs = new ArrayList<Path>();

    for (int index = 0; index < args.length; index++) {
      String arg = args[index];
      if ("--output".equals(arg) || "-o".equals(arg)) {
        if (index + 1 >= args.length) {
          err.println("Fehler: Ausgabe-Datei fuer --output fehlt.");
          printUsage(err);
          return 2;
        }
        output = Paths.get(args[++index]);
      } else if (arg.startsWith("-")) {
        err.println("Fehler: Unbekanntes Argument: " + arg);
        printUsage(err);
        return 2;
      } else {
        inputs.add(Paths.get(arg));
      }
    }

    if (output == null) {
      err.println("Fehler: Bitte --output <datei.pdf> angeben.");
      printUsage(err);
      return 2;
    }

    if (inputs.isEmpty()) {
      err.println("Fehler: Es wurden keine Eingabe-PDFs uebergeben.");
      printUsage(err);
      return 2;
    }

    try {
      new PdfMergeService().merge(inputs, output);
      out.println("Erfolg: PDF wurde erstellt: " + output.toAbsolutePath());
      return 0;
    } catch (RuntimeException exception) {
      err.println("Fehler: " + exception.getMessage());
      return 1;
    }
  }

  private static boolean containsHelp(String[] args) {
    for (String arg : args) {
      if ("--help".equals(arg) || "-h".equals(arg)) {
        return true;
      }
    }
    return false;
  }

  private static void printUsage(PrintStream stream) {
    stream.println("Verwendung:");
    stream.println("  pdf-zusammenfuegen --output merged.pdf a.pdf b.pdf [c.pdf ...]");
    stream.println();
    stream.println("Optionen:");
    stream.println("  -o, --output   Ziel-Datei (PDF)");
    stream.println("  -h, --help     Hilfe anzeigen");
  }
}
