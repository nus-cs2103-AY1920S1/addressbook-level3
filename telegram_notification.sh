#!/bin/sh

# Reference: https://testdriven.io/blog/getting-telegram-notifications-from-travis-ci/
# More Travis environment variables which we can use in the notification:
#       https://docs.travis-ci.com/user/environment-variables/#default-environment-variables

# Get the token from Travis environment vars and build the bot URL:
BOT_URL="https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendMessage"

# Set formatting for the message. Can be either "Markdown" or "HTML"
PARSE_MODE="HTML"

# Use built-in Travis variables to check if all previous steps passed:
if [ $TRAVIS_TEST_RESULT -ne 0 ]; then
    build_status="failed"
else
    build_status="succeeded"
fi

# Define send message function. parse_mode can be changed to
# HTML, depending on how you want to format your message:
send_msg () {
    curl -s -X POST ${BOT_URL} -d chat_id=$TELEGRAM_CHAT_ID \
        -d text="$1" -d parse_mode=${PARSE_MODE}
}

# Send message to the bot with some pertinent details about the job
# Note that for Markdown, you need to escape any backtick (inline-code)
# characters, since they're reserved in bash
send_msg "
-------------------------------------
Travis build #${TRAVIS_BUILD_NUMBER} <b>${build_status}!</b>
<code>Repository:  ${TRAVIS_REPO_SLUG}</code>
<code>Branch:      ${TRAVIS_BRANCH}</code>
<code>Author:      ${AUTHOR_NAME}</code>
<b>Commit Msg:</b>
${TRAVIS_COMMIT_MESSAGE}
<a href=&quot;${TRAVIS_JOB_WEB_URL}&quot;>View Job Log</a>
--------------------------------------
"
