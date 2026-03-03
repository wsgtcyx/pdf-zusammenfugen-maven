# Publishing-Leitfaden (Maven Central)

Dieser Leitfaden beschreibt den manuellen Erst-Release von `de.pdfzus:pdf-zusammenfuegen-core`.

## 1. Voraussetzungen

- Java und Maven installiert
- GPG installiert (`brew install gnupg`)
- Konto auf [central.sonatype.com](https://central.sonatype.com)
- Verifiziertes Namespace `de.pdfzus` (DNS-TXT fuer `pdfzus.de`)

## 2. GPG einrichten

```bash
gpg --gen-key
gpg --list-keys --keyid-format LONG
gpg --keyserver keyserver.ubuntu.com --send-keys <DEIN_KEY_ID>
```

## 3. Sonatype Token in Maven hinterlegen

`~/.m2/settings.xml`:

```xml
<settings>
  <servers>
    <server>
      <id>central</id>
      <username><!-- token username --></username>
      <password><!-- token password --></password>
    </server>
  </servers>
</settings>
```

## 4. Vor dem Release pruefen

```bash
mvn test
mvn -Prelease verify
```

Erwartete Artefakte in `target/`:

- `pdf-zusammenfuegen-core-0.1.0.jar`
- `pdf-zusammenfuegen-core-0.1.0-sources.jar`
- `pdf-zusammenfuegen-core-0.1.0-javadoc.jar`
- jeweilige `.asc` Signaturen

## 5. Erstes manuelles Publishing

```bash
mvn -Prelease deploy
```

Danach in Sonatype Central unter `Deployments`:

1. Validierung pruefen
2. Manuell auf **Publish** klicken

## 6. Backlink-Checks

Nach erfolgreicher Synchronisierung kontrollieren:

- POM-URL zeigt auf `https://pdfzus.de/`
- README enthaelt den Link `[PDF zusammenfuegen online](https://pdfzus.de/)`
- GitHub-Repository-Homepage ist `https://pdfzus.de/`
