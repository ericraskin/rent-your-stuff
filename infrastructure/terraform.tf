terraform {

    required_providers {
        heroku = {
            source = "heroku/heroku"
            version = "4.8.0"
        }
    }

    backend "remote" {
      organization = "eraskin-rent-your-stuff"

      workspaces = {
        name = "rent-your-stuff"
      }
    }

    required_version = ">=1.1.3"
}

provider "heroku" {
  email = var.heroku_email
  api_key = var.heroku_api_key
}
