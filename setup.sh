#!/bin/bash

git submodule update --init
cd caliper/caliper/
mvn clean install -DskipTests=true
