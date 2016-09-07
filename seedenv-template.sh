#!/usr/bin/env bash

curl -X PUT -d <feed db user> <consul host>:8500/v1/kv/devcenter/dc1/FEED_DB_USER
curl -X PUT -d <db password> <consul host>:8500/v1/kv/devcenter/dc1/FEED_DB_PASSWORD
curl -X PUT -d <db host or ip address> <consul host>:8500/v1/kv/devcenter/dc1/FEED_DB_HOST
curl -X PUT -d <db port> <consul host>:8500/v1/kv/devcenter/dc1/FEED_DB_PORT
curl -X PUT -d <oracle db service name> <consul host>:8500/v1/kv/devcenter/dc1/FEED_DB_SVC
curl -X PUT -d 1 <consul host>:8500/v1/kv/devcenter/dc1/ES_PUBLISH_EVENTS
curl -X PUT -d <link host:port> <consul host>:8500/v1/kv/devcenter/dc1/FEED_LINK_URL_BASE
