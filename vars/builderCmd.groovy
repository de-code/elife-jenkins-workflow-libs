def backslash(character, string) {
    return string.replaceAll(character, '\\' + character)
}

def backslashQuotes(string) {
    def closedQuote = "'"
    def quote = "\\\\'"
    def openQuote = "'"
    return string.replaceAll(/'/, closedQuote + quote + openQuote)
}

def call(stackname, cmd) {
    cmd = backslash('=', cmd)
    cmd = backslash(',', cmd)
    cmd = backslashQuotes(cmd)
    def shellCmd = "${env.BUILDER_PATH}bldr 'cmd:${stackname},${cmd}'"
    print "About to execute: ${shellCmd}"
    sh shellCmd 
}
