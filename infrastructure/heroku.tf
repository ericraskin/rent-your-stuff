resource "heroku_app" "eraskin-rent-your-stuff-staging" {
  name = "eraskin-rent-your-stuff-staging"
  region = "us"

  buildpacks = [
    "heroku/gradle"
  ]

}