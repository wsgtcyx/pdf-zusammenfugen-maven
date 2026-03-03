# PDF Zusammenfuegen Core (Maven)

`pdf-zusammenfuegen-core` ist eine Java-Bibliothek fuer **pdf zusammenfuegen**, **pdf verbinden** und **pdf mergen** mit lokaler Verarbeitung.

Wenn du lieber direkt im Browser arbeitest, nutze unser Tool:
[PDF zusammenfuegen online](https://pdfzus.de/)

## Maven-Koordinaten

```xml
<dependency>
  <groupId>de.pdfzus</groupId>
  <artifactId>pdf-zusammenfuegen-core</artifactId>
  <version>0.1.0</version>
</dependency>
```

## API-Beispiel

```java
import de.pdfzus.merge.PdfMergeService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

PdfMergeService service = new PdfMergeService();
service.merge(
    Arrays.asList(Paths.get("teil1.pdf"), Paths.get("teil2.pdf")),
    Paths.get("gesamt.pdf")
);
```

## CLI-Nutzung

```bash
java -jar target/pdf-zusammenfuegen-core-0.1.0.jar --output merged.pdf a.pdf b.pdf
```

Der CLI-Entrypoint lautet:

```bash
pdf-zusammenfuegen --output merged.pdf a.pdf b.pdf
```

## Entwicklung

```bash
mvn test
mvn verify
mvn -Prelease -Dgpg.skip=true verify
```

## Lizenz

MIT
