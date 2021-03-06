def call(branch='develop')
{
    elifePipeline {
        def commit
        stage 'Checkout', {
            git branch: branch, url: 'git://github.com/elifesciences/loris.git'
            commit = elifeGitRevision()
        }

        stage 'Ci', {
            lock('iiif--ci') {
                builderDeployRevision 'iiif--ci', commit
            }
        }

        stage 'End2end', {
            lock('end2end') {
                lock('iiif--end2end') {
                    builderDeployRevision 'iiif--end2end', commit
                }
            }
        }

        stage 'Continuumtest', {
            lock('continuumtest') {
                lock('iiif--continuumtest') {
                    builderDeployRevision 'iiif--continuumtest', commit
                }
            }
        }

        stage 'Prod', {
            lock('iiif--prod') {
                builderDeployRevision 'iiif--prod', commit
            }
        }
    }
}
