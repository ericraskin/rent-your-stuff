terraform {

  required_providers {
    heroku = {
      source  = "heroku/heroku"
      version = "~> 5.0"
    }

    herokux = {
      source  = "davidji99/herokux"
      version = "0.33.0"
    }
  }

  backend "remote" {
    organization = "eraskin-rent-your-stuff"

    workspaces {
      name = "rent-your-stuff"
    }
  }

  required_version = ">=1.1.3"
}

provider "heroku" {
  email   = var.HEROKU_EMAIL
  api_key = var.HEROKU_API_KEY
}

provider "herokux" {
  api_key = var.HEROKU_API_KEY
}