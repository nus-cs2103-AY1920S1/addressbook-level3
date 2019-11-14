#!/bin/bash

curl -s https://codecov.io/bash > .codecov
chmod +x .codecov
./.codecov

