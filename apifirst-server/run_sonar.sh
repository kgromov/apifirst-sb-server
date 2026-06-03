#!/bin/bash

export $(grep -v '^#' .env | grep -v '^$' | xargs) && mvn sonar:sonar