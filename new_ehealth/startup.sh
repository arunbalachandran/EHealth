#!/bin/bash
# using Git bash on Windows

# Script used to start the backend app ###########
set -euo pipefail

WORKING_PATH=$(dirname "$0")

python ${WORKING_PATH}/backend.py