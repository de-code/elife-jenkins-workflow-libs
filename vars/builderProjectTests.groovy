def retrieveArtifacts(stackname, testArtifacts) {
    def localTestArtifacts = []
    echo "Looking for test artifacts: ${testArtifacts}"
    for (int i = 0; i < testArtifacts.size(); i++) {
        def remoteTestArtifact = testArtifacts.get(i)
        def slash = remoteTestArtifact.lastIndexOf('/')
        def basename = remoteTestArtifact[slash+1..-1]
        def localTestArtifact = "${env.BUILD_TAG}.${basename}"
        localTestArtifacts << localTestArtifact
        builderTestArtifact localTestArtifact, stackname, remoteTestArtifact, true
    }
    echo "Retrieved test artifacts: ${localTestArtifacts}"
    for (int i = 0; i < localTestArtifacts.size(); i++) {
        def localTestArtifact = localTestArtifacts.get(i)
        // TODO: really not necessary?
        // by bubbling up the original exception we may not need to check these manually
        /*
        if (fileExists(localTestArtifact)) {
            elifeVerifyJunitXml localTestArtifact
        }
        */
    }
}

def call(stackname, folder, testArtifacts=[], order=['project', 'smoke']) {
    for (int i = 0; i < order.size(); i++) {
        if (order.get(i) == 'smoke') {
            builderSmokeTests stackname, folder
        } else if (order.get(i) == 'project') {
            def projectTestsCmd = "cd ${folder}; ./project_tests.sh"
            try {
                builderCmd stackname, projectTestsCmd
            } finally {
                retrieveArtifacts stackname, testArtifacts
            }
        } else {
            error("You requested to run '${order.get(i)}' tests, but the only allowed values are 'smoke' and 'project'.");
        }
    }
}
