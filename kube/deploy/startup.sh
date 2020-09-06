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

# optional values override
OPTIONAL_VALUES_OVERRIDE=""

for i in "${SERVICES[@]}"; do
  if [[ -f $VARS_PATH/$i/values.yaml ]]; then
    OPTIONAL_VALUES_OVERRIDE="-f $VARS_PATH/$i/values.yaml"
  fi
  helm secrets upgrade -i $i $SERVICES_PATH/$i $OPTIONAL_VALUES_OVERRIDE -f $VARS_PATH/$i/secrets.yaml
done
