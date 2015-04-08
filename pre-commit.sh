#!/bin/bash

IDENTITY_NOT_TEST_ONE=`cat src/test/resources/*.properties | grep 'cloudtag.identity=' | grep -v 'ABCXXXXXXXXXXXXX' | wc -l`

if [ "$IDENTITY_NOT_TEST_ONE" -ne 0 ]; then
    echo "Please check identies in properties files! This pre-commit hook is to prevent you from committing your identity to public repo! The only accepted value is 'cloudtag.identity=ABCXXXXXXXXXXXXX'."
    exit 1
fi


CREDENTIAL_NOT_TEST_ONE=`cat src/test/resources/*.properties | grep 'cloudtag.credential=' | grep -v 'DEFYYYYYYYYYYY' | wc -l`

if [ "$CREDENTIAL_NOT_TEST_ONE" -ne 0 ]; then
    echo "Please check credentials in properties files! This pre-commit hook is to prevent you from committing your credential to public repo! The only accepted value is 'cloudtag.credential=DEFYYYYYYYYYYY'"
    exit 1
fi
