def call() {
    def commitFile = "${env.BUILD_TAG}.commit.txt"
    sh "git rev-parse HEAD > ${commitFile}"
    def commit = readFile commitFile
    sh "rm ${commitFile}"
    return commit
}
