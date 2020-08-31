#!/bin/bash
# using Git bash on Windows

set -euo pipefail

WORKING_PATH=$(dirname "$0")
ROOT_PATH=$WORKING_PATH/../..
SERVICES_PATH=$ROOT_PATH/kube/services
VARS_PATH=$ROOT_PATH/kube/vars

# run from the rootpath - which has the .sops.yaml file?
cd $ROOT_PATH

declare -a SERVICES=("postgresql")

for i in "${SERVICES[@]}"; do
  helm secrets upgrade -i $i $SERVICES_PATH/$i -f $VARS_PATH/$i/secrets.yaml
done
