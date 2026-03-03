#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"

if [[ -z "${CENTRAL_TOKEN_USERNAME:-}" || -z "${CENTRAL_TOKEN_PASSWORD:-}" ]]; then
  echo "Fehler: Bitte CENTRAL_TOKEN_USERNAME und CENTRAL_TOKEN_PASSWORD setzen." >&2
  exit 2
fi

exec mvn \
  -s "$ROOT_DIR/maven/settings-central.xml" \
  -gs "$ROOT_DIR/maven/settings-global-empty.xml" \
  -Dmaven.repo.local="$ROOT_DIR/.m2-central/repository" \
  "$@"
