import Revision

def call(commit, status, context, description='elifeGithubCommitStatus step', targetUrl='') {
    def revision = new Revision(commit)
    if (revision.isBranch()) {
        echo "No commit status to update for branch ${revision.toString()}"
    } else {
        sh "commit=${commit} status=${status} context=${context} description='${description}' target_url='${targetUrl}' /usr/local/jenkins-scripts/notify_github_commit_status.sh"
    }
}
