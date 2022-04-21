resource "heroku_app" "eraskin-rent-your-stuff-staging" {
  name = "eraskin-rent-your-stuff-staging"
  region = "us"

  config_vars = {
    FOOBAR = "baz"
  }

  buildpacks = {
    "heroku/gradle"
  }

}