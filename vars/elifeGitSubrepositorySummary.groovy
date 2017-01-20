def call(folder) {
    def commit = sh(script: "cd ${folder}; git rev-parse HEAD", returnStdout: true).trim()
    def commitMessage = sh(script: "cd ${folder}; git log -1 --pretty='%B' | head -n 1", returnStdout: true).trim()
    return "${commit}: ${commitMessage}"
}
