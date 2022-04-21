resource "heroku_app" "eraskin-rys-staging" {
  name = "eraskin-rys-staging"
  region = "us"

  buildpacks = [
    "heroku/gradle"
  ]

}