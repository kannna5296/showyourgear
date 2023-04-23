#!/usr/bin/env bash
set -
if awslocal s3api head-bucket --bucket "localbucket" 2>/dev/null; then
  :
else
  awslocal s3 mb s3://localbucket
  awslocal s3 cp /tmp/init-data s3://localbucket/ --recursive
fi
set +