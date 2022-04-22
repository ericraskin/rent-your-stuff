resource "heroku_app" "eraskin-rys-staging" {
  name = "eraskin-rys-staging"
  region = "us"

  buildpacks = [
    "heroku/gradle"
  ]
}

resource "heroku_addon" "eraskin-rys-staging-db" {
  app  = heroku_app.eraskin-rys-staging.id
  plan = "heroku-postgresql:hobby-dev"
}

/*

resource "heroku_pipeline" "eraskin-rys-pipeline" {
  name = "eraskin-rys-pipeline"
}

resource "heroku_pipeline_coupling" "staging_pipeline_coupling" {
  app      = heroku_app.eraskin-rys-staging.id
  pipeline = heroku_pipeline.eraskin-rys-pipeline.id
  stage    = "staging"
}

resource "herokux_pipeline_github_integration" "pipeline_integration" {
  pipeline_id = heroku_pipeline.eraskin-rys-pipeline.id
  org_repo    = "ericraskin/rent-your-stuff"
}

resource "herokux_app_github_integration" "eraskin-rys-gh-integration" {
  app_id      = heroku_app.eraskin-rys-staging.uuid
  branch      = "main"
  auto_deploy = true
  wait_for_ci = true
  depends_on  = [herokux_pipeline_github_integration.pipeline_integration]
}

*/
