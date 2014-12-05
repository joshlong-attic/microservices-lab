#!/bin/bash
ROUTE=${ROUTE:-localhost:8080}
curl -X POST ${ROUTE}/mstine/likes/1
curl -X POST ${ROUTE}/mstine/likes/2
curl -X POST ${ROUTE}/starbuxman/likes/2
curl -X POST ${ROUTE}/starbuxman/likes/4
curl -X POST ${ROUTE}/starbuxman/likes/5
curl -X POST ${ROUTE}/littleidea/likes/2
curl -X POST ${ROUTE}/littleidea/likes/3
curl -X POST ${ROUTE}/littleidea/likes/5
