#!/usr/bin/env sh

# ======================================================================================================================
#
# owg - Wrapper script meant to make it easier to call the latest release version of the offline-wallet-generator. This
#       script is a convenience feature for the user to avoid "java -jar" commands.
#
# ======================================================================================================================

java -jar `dirname "$0"`/../lib/offline-wallet-generator-latest.jar $@
